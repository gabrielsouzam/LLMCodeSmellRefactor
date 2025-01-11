package org.example.studyregistry;

import java.time.LocalDateTime;
import java.util.List;

public class StudyObjective extends Registry{
    private String title;
    private String description;
    private String topic;
    private Integer practicedDays;
    private LocalDateTime startDate;
    private Double duration;
    private String objectiveInOneLine;
    private String objectiveFullDescription;

    public static class ObjectiveBuilderData {
        private final Integer id;
        private final Integer priority;
        private final Integer practicedDays;
        private final int day;
        private final int month;
        private final int year;

        public ObjectiveBuilderData(
                Integer id,
                Integer priority,
                Integer practicedDays,
                int day,
                int month,
                int year
        ) {
            this.id = id;
            this.priority = priority;
            this.practicedDays = practicedDays;
            this.day = day;
            this.month = month;
            this.year = year;
        }
    }

    public static class ObjectiveBuilderDetails {
        private final String name;
        private final String title;
        private final String description;
        private final String topic;
        private final String objectiveInOneLine;
        private final String objectiveFullDescription;
        private final String motivation;
        private final Double duration;
        private final boolean isActive;

        public ObjectiveBuilderDetails(
                String name,
                String title,
                String description,
                String topic,
                String objectiveInOneLine,
                String objectiveFullDescription,
                String motivation,
                Double duration,
                boolean isActive
        ) {
            this.name = name;
            this.title = title;
            this.description = description;
            this.topic = topic;
            this.objectiveInOneLine = objectiveInOneLine;
            this.objectiveFullDescription = objectiveFullDescription;
            this.motivation = motivation;
            this.duration = duration;
            this.isActive = isActive;
        }
    }

    public String getTitle() {
        return title;
    }

    public String getTopic() {
        return topic;
    }

    public Integer getPracticedDays() {
        return practicedDays;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public Double getDuration() {
        return duration;
    }

    public String getObjectiveInOneLine() {
        return objectiveInOneLine;
    }

    public String getObjectiveFullDescription() {
        return objectiveFullDescription;
    }

    public String getMotivation() {
        return motivation;
    }

    private String motivation;

    @Override
    public String toString(){
        return "StudyObjective [title:" + title + ", description:" + description + (topic != null ? ", topic:" + topic : "")
                + (practicedDays != null ? ", practicedDays:" + practicedDays : "") + (duration != null ? ", duration:" + duration : "")
                + (objectiveInOneLine != null ? ", objective summary:" + objectiveInOneLine : "") + (objectiveFullDescription != null ? ", objective full description:" + objectiveFullDescription : "")
                + (motivation != null ? ", motivation:" + motivation : "") + "]";
    }
    public StudyObjective(String title, String description) {
        this.title = title;
        this.description = description;
        this.name = title;
    }

    public void handleSetRegistry(Integer id, String name, Integer priority, boolean isActive){
        this.id=id;
        this.name=name;
        this.priority=priority;
        this.isActive=isActive;
    }

    public void handleSetTextualInfo(String title, String description, String topic,String objectiveInOneLine, String objectiveFullDescription, String motivation){
        this.title=title;
        this.description=description;
        this.topic=topic;
        this.objectiveInOneLine=objectiveInOneLine;
        this.objectiveFullDescription=objectiveFullDescription;
        this.motivation=motivation;
    }

    public void handleSetTime(Integer practicedDays, int day, int month, int year, Double duration){
        this.practicedDays=practicedDays;
        this.duration=duration;
        this.startDate= LocalDateTime.of(year, month, day, 0, 0);
    }

    public void handleSetObjective(ObjectiveBuilderData data, ObjectiveBuilderDetails details, boolean isActive) {
        // Delegate to helper methods with more specific parameters
        handleSetRegistry(data.id, details.name, data.priority, isActive);
        handleSetTextualInfo(details.title, details.description, details.topic, details.objectiveInOneLine, details.objectiveFullDescription, details.motivation);
        handleSetTime(data.practicedDays, data.day, data.month, data.year, details.duration);
    }

    public int handleSetObjectiveAdapter(List<Integer> intProperties, List<String> stringProperties, Double duration, boolean isActive) {
        if (intProperties.size() < 6 || stringProperties.size() < 7) {
            throw new IllegalArgumentException("Insufficient properties provided.");
        }

        ObjectiveBuilderData data = new ObjectiveBuilderData(
                intProperties.get(0), // id
                intProperties.get(1), // priority
                intProperties.get(2), // practicedDays
                intProperties.get(3), // day
                intProperties.get(4), // month
                intProperties.get(5)  // year
        );

        ObjectiveBuilderDetails details = new ObjectiveBuilderDetails(
                stringProperties.get(0), // name
                stringProperties.get(1), // title
                stringProperties.get(2), // description
                stringProperties.get(3), // topic
                stringProperties.get(4), // objectiveInOneLine
                stringProperties.get(5), // objectiveFullDescription
                stringProperties.get(6), // motivation
                // Add missing arguments here
                duration, // Example: Assuming duration is optional
                false  // Example: Assuming isActive is optional
        );

        handleSetObjective(data, details, isActive);
        return intProperties.get(0); // Return the ID
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}