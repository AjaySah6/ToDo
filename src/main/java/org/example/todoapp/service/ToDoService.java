package org.example.todoapp.service;

import org.example.todoapp.exceptions.IdNotFoundException;
import org.example.todoapp.model.ToDo;
import org.example.todoapp.repository.ToDoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ToDoService {

    private ToDoRepository toDoRepository;
    public ToDoService(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    public ToDo saveToDo(ToDo toDo) {
        return toDoRepository.save(toDo);
    }

    public ToDo getToDo(Long id) throws IdNotFoundException {
        try {
            Optional<ToDo> existingToDo = Optional.of(toDoRepository.findById(id).get());
            if (existingToDo.isPresent()) {
                return existingToDo.get();
            }
        } catch (IdNotFoundException e){
            throw new IdNotFoundException("ID not found, enter a valid ID.");
        }
        return null;
    }

    public List<ToDo> getAllToDo(){
        return toDoRepository.findAll();
    }

    public ToDo updateToDo(Long id, ToDo updatedToDo) throws IdNotFoundException {
        ToDo existingToDo;
        try {                                                                       // put all the logic in try block
            Optional<ToDo> toDo = toDoRepository.findById(id);
            if (toDo.isPresent()) {
                existingToDo = toDo.get();
                existingToDo.setTitle(updatedToDo.getTitle());
                existingToDo.setDescription(updatedToDo.getDescription());
                existingToDo.setCompleted(updatedToDo.isCompleted());
                return toDoRepository.save(existingToDo);
            }
        } catch(IdNotFoundException e) {
            throw new IdNotFoundException("ID not found, enter a valid ID.");
        }
        return null;
    }

    public String deleteToDo(Long id) throws IdNotFoundException {
        try{
            Optional<ToDo> toDoOptional = toDoRepository.findById(id);
            if (toDoOptional.isPresent()) {
                toDoRepository.deleteById(id);
                return "ToDo with ID " + id + " was successfully deleted!";
            }
        }catch(IdNotFoundException e) {
            throw new IdNotFoundException("ID not found, enter a valid ID.");
        }
        return null;
    }

}
