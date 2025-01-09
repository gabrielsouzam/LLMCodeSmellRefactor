package org.example.studyplanner;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.Objects;

public class ToDo implements PlannerMaterial{
    private Integer id;
    private String title;
    private String description;
    private int priority;
    private boolean completed;

    public ToDo(Integer id, String title, String description, int priority) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    @Override
    public String toString() {
        return MessageFormat.format("[(Priority:{3}) ToDo {0}: {1}, {2}]", id, title, description, priority);
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void markAsCompleted() {
        this.completed = true;
    }

    public boolean isActive() {
        return !completed;
    }

    public String getImportance() {
        if (priority == 1) {
            return "Baixa";
        } else if (priority == 2) {
            return "Média";
        } else {
            return "Alta";
        }
    }

    public int compareTo(ToDo other) {
        return Integer.compare(this.priority, other.priority);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ToDo todo = (ToDo) o;
        return id.equals(todo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public int compareByTitle(ToDo other) {
        return this.title.compareTo(other.title);
    }

    public boolean belongsToCategory(String category) {
        // Assumindo que a descrição contenha informações sobre a categoria
        return description.contains(category);
    }

    public String getSummary() {
        return title + " (" + priority + ")";
    }

    // Assumindo que o título contenha o nome do projeto
    public boolean isRelatedToProject(String projectName) {
        return title.contains(projectName);
    }
}
