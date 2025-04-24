package org.example.todoapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "todo")
@Data                   // @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor,toString(), equals(), hashCode()
@NoArgsConstructor      // Generates a constructor with no parameters.
@AllArgsConstructor     // Generates a constructor with one parameter for each field in the class.

public class ToDo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // // Use database's identity column (e.g., AUTO_INCREMENT)
    private long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 100)
    private String description;

    @Column(nullable = false)
    private boolean completed = false; // Default value at entity level


    // Getters and Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
