package org.example.studyregistry;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Objects;

public class Task extends Registry{
    private String title;
    private String description;
    private String author;
    private LocalDateTime date;

    public Task(String title, String description, String author, LocalDateTime date) {
        this.title = title;
        this.name = title;
        this.description = description;
        this.author = author;
        this.date = date;
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
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public boolean isOverdue() {
        // Assumindo que a data de conclusão é comparada com a data atual
        LocalDateTime now = LocalDateTime.now();
        return date.isBefore(now);
    }

    public String getFormattedCreationDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return date.format(formatter);
    }

    public boolean containsKeyword(String keyword) {
        return title.toLowerCase().contains(keyword.toLowerCase()) ||
                description.toLowerCase().contains(keyword.toLowerCase());
    }

    public String getSummary() {
        return "Título: " + title + "\nDescrição: " + description + "\nAutor: " + author + "\nData: " + date;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(title, task.title) &&
                Objects.equals(description, task.description) &&
                Objects.equals(author, task.author) &&
                Objects.equals(date, task.date);
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", author='" + author + '\'' +
                ", date=" + date +
                '}';
    }

    public boolean wasCreatedInYear(int year) {
        return date.getYear() == year;
    }

    public boolean wasCreatedInMonth(int month) {
        return date.getMonthValue() == month;
    }

    public long getAgeInDays() {
        return Duration.between(date, LocalDateTime.now()).toDays();
    }

    public boolean wasCreatedOnSameDayAs(Task otherTask) {
        return date.toLocalDate().equals(otherTask.date.toLocalDate());
    }

    public int getWordCountInDescription() {
        return description.split("\\s+").length;
    }

    public boolean containsPhrase(String phrase) {
        return description.contains(phrase);
    }

    public String getFirstNWords(int n) {
        String[] words = description.split("\\s+");
        return String.join(" ", Arrays.copyOf(words, Math.min(n, words.length)));
    }

    public boolean wasCreatedBy(String authorName) {
        return author.equalsIgnoreCase(authorName);
    }



}
