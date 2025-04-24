package org.example.todoapp.controller;

import org.example.todoapp.model.ToDo;
import org.example.todoapp.service.ToDoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


// // ✅Database file is stored at - C:\Users\<YourUsername>\todo-db.mv.db

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/todo")
public class ToDoController {


    //@Autowired
    private ToDoService toDoService;
    public ToDoController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    // use ResponseEntity<T> to wrap the response and adhere to REST advisory
    // and for control ove status codes


    @PostMapping("/create") //http://localhost:8080/todo/create
    public ResponseEntity<ToDo> createToDo(@RequestBody ToDo toDo) {
        // Basic validation example (you'd typically use validation annotations)
        if (toDo.getTitle() == null || toDo.getTitle().isBlank()) {
            // In a real app, return a structured error object, not just a string
            return ResponseEntity.badRequest().build(); // Or .body("Title is required")
        }

        ToDo newtoDo = toDoService.saveToDo(toDo);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath() // Gets base path (http://yourhost)
                .path("/todo/{id}")      // Appends resource path
                .buildAndExpand(toDo.getId()) // Fills in the id
                .toUri();
        return ResponseEntity.created(location).body(toDo); // ✅
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToDo> getToDo(@PathVariable long id) {
        ToDo toDo = toDoService.getToDo(id);
        try{
            return ResponseEntity.ok(toDo); // ✅
        } catch (Exception e){
            return ResponseEntity.notFound().build(); //❌
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<ToDo> updateToDo(@PathVariable long id, @RequestBody ToDo toDo) {
        // Add validation for toDoDetails if necessary
        if (toDo.getTitle() == null || toDo.getTitle().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        try{
            ToDo updatedToDo = toDoService.updateToDo(id, toDo);
            return ResponseEntity.ok(toDo);    // ✅
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteToDo(@PathVariable long id) {
        try {
            toDoService.deleteToDo(id);
            return ResponseEntity.noContent().build(); // ✅ 204 No Content - Standard for successful DELETE
        }catch (Exception e){
            return ResponseEntity.notFound().build(); // ❌ 404 Not Found
        }
    }
    @GetMapping("/getall") //http://localhost:8080/todo/getall
    public ResponseEntity<List<ToDo>> getAllToDo() {
        List<ToDo> toDo = toDoService.getAllToDo();
        try{
            return ResponseEntity.ok(toDo); // ✅
        } catch (Exception e){
            return ResponseEntity.notFound().build(); //❌
        }
    }

}



/*
    ToDo newtoDo = toDoService.saveToDo(toDo);
    URI location = ServletUriComponentsBuilder
            .fromCurrentContextPath() // Gets base path (http://yourhost)
            .path("/todo/{id}")      // Appends resource path
            .buildAndExpand(createdToDo.getId()) // Fills in the id
            .toUri();
    return ResponseEntity.created(location).body(createdToDo);

🏁
URI location = ServletUriComponentsBuilder // This line starts the process of building a java.net.URI object using Spring's ServletUriComponentsBuilder.

Purpose: The goal is to construct the URL that uniquely identifies the newly created ToDo resource.
This URL will be used in the Location header of the HTTP response.

ServletUriComponentsBuilder: This is a helper class provided by Spring MVC that makes it easier to build URIs dynamically,
especially ones relative to the current request's context (like the server address, port, and application context path).
It helps avoid manual string concatenation and potential encoding issues.

.fromCurrentContextPath() //This method initializes the builder with the base URI of the current application context.
It typically includes the scheme (http or https), hostname (localhost, yourdomain.com),
port (8080), and any application context path (if your app isn't deployed at the root).
For example, it might resolve to http://localhost:8080 or https://myapp.com/api.
It doesn't include the controller or method-specific path yet.



.path("/todo/{id}") // This appends the specific path segment that leads to a single ToDo resource within your API.

/todo: This should match the @RequestMapping value on your ToDoController.

/{id}: This is a path variable template. It acts as a placeholder for the actual ID of the resource. At this point, the builder conceptually holds something like http://localhost:8080/todo/{id}.



.buildAndExpand(createdToDo.getId())

What it does: This is where the placeholder {id} is replaced with the actual ID of the newly created ToDo item.

createdToDo.getId(): It retrieves the ID from the createdToDo object (the one returned by the service in step 1).

Substitution: The builder takes the path template (/todo/{id}) and substitutes {id} with the value obtained (e.g., if the ID is 123, the path becomes /todo/123).

Build: It constructs the final URI components based on the template and the provided values.



.toUri()

What it does: This method converts the fully constructed and expanded components into a standard java.net.URI object.

Result: The location variable now holds a URI object representing the unique address of the newly created resource, for example, http://localhost:8080/todo/123.



return ResponseEntity.created(location).body(createdToDo);

What it does: This line constructs and returns the final ResponseEntity object, which Spring MVC will use to generate the HTTP response sent back to the client.

ResponseEntity.created(location): This is a static factory method on ResponseEntity.

It sets the HTTP status code to 201 Created.
This is the standard HTTP status code indicating that a new resource has been successfully created as a result of the request.

It automatically adds the Location HTTP header to the response, setting its value to the URI stored in the location variable (e.g., Location: http://localhost:8080/todo/123). This header tells the client where to find the resource they just created.

.body(createdToDo): This method sets the HTTP response body. Spring will typically serialize the createdToDo object (including its ID and other details) into JSON (or XML, depending on configuration and request headers) and include it in the response. It's common practice to return the representation of the newly created resource in the body of a 201 Created response.

return ...;: The controller method returns the fully configured ResponseEntity.


 */