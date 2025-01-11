package org.example.controllers;

import org.example.studymaterial.AudioReference;
import org.example.studymaterial.Reference;
import org.example.studymaterial.TextReference;
import org.example.studymaterial.VideoReference;
import org.example.studyregistry.*;

import java.time.LocalDateTime;
import java.util.*;

import static org.example.controllers.MainController.getInput;
import static org.example.controllers.MainController.validateInput;

public class StudyRegistryController {
    StudyTaskManager studyTaskManager = StudyTaskManager.getStudyTaskManager();
    StudyMaterial studyMaterial = StudyMaterial.getStudyMaterial();
    private Map<String, Runnable> actions = new HashMap<>();

    public StudyRegistryController() {
        assignActions();
    }

    void assignActions(){
        actions.put("1", this::handleAddStudyTask);
        actions.put("2", this::handleAddStudyGoal);
        actions.put("3", this::handleAddStudyMaterial);
        actions.put("4", this::handleAddStudyObjective);
        actions.put("5", this::handleAddStudyPlan);
        actions.put("6", this::handleSetUpWeek);
        actions.put("7", this::handleGetWeekResponsibilities);
    }

    private void handleMethodHeader(String header){
        System.out.println("~~~~" + header + "~~~~\n");
    }

    private Task getStudyTaskInfo(){
        System.out.println("Type the following info: title, description, author \n");
        String title = getInput();
        String description = getInput();
        String author = getInput();
        return new Task(title, description, author, LocalDateTime.now());
    }

    private void handleAddStudyTask(){
        Task task = getStudyTaskInfo();
        studyTaskManager.addRegistry(task);
    }

    private void handleSetObjective(StudyObjective objective) {
        handleMethodHeader("(Study Objective Edit)");

        StudyObjective.ObjectiveBuilderData data = collectObjectiveData();
        StudyObjective.ObjectiveBuilderDetails details = collectObjectiveDetails();
        boolean isActive = getBooleanInput("Is Active (true/false)");

        objective.handleSetObjective(data, details, isActive);
    }

    private StudyObjective.ObjectiveBuilderData collectObjectiveData() {
        Integer id = getIntInput("ID");
        Integer priority = getIntInput("Priority");
        Integer practicedDays = getIntInput("Practiced Days");
        int day = getIntInput("Day");
        int month = getIntInput("Month");
        int year = getIntInput("Year");
        return new StudyObjective.ObjectiveBuilderData(id, priority, practicedDays, day, month, year);
    }

    private StudyObjective.ObjectiveBuilderDetails collectObjectiveDetails() {
        String name = getInput();
        String title = getInput();
        String description = getInput();
        String topic = getInput();
        String objectiveInOneLine = getInput();
        String objectiveFullDescription = getInput();
        String motivation = getInput();
        Double duration = getDoubleInput("Duration");
        return new StudyObjective.ObjectiveBuilderDetails(name, title, description, topic, objectiveInOneLine, objectiveFullDescription, motivation, duration, false);
    }
    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt + ": ");
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter an integer.");
            }
        }
    }

    private double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt + ": ");
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private boolean getBooleanInput(String prompt) {
        while (true) {
            System.out.print(prompt + ": ");
            String input = scanner.nextLine().toLowerCase();
            if (input.equals("true")) {
                return true;
            } else if (input.equals("false")) {
                return false;
            } else {
                System.out.println("Invalid input. Please enter 'true' or 'false'.");
            }
        }
    }

    private StudyObjective getStudyObjectiveInfo(){
        handleMethodHeader("(Study Objective Creation)");
        System.out.println("Type the following info: title, description \n");
        String title = getInput();
        String description = getInput();
        StudyObjective studyObjective = new StudyObjective(title, description);
        handleSetObjective(studyObjective);
        studyTaskManager.addRegistry(studyObjective);
        return studyObjective;
    }

    private StudyPlan getStudyPlanInfo(){
        handleMethodHeader("(Study Plan Creation)");
        System.out.println("Type the following info: name \n");
        String name = getInput();
        StudyObjective studyObjective = getStudyObjectiveInfo();
        StudyPlan plan = new StudyPlan(name, studyObjective,  new ArrayList<>());
        studyTaskManager.addRegistry(plan);
        return plan;
    }
    private String getUserInput(String prompt) {
        System.out.print(prompt + ": ");
        return scanner.nextLine();
    }

    // Assuming you have a Scanner object
    private Scanner scanner = new Scanner(System.in);



    private void handleSetSteps(StudyPlan studyPlan) {
        handleMethodHeader("(Study Plan Edit)");

        // Agora utilize o método fromUserInput da classe StepDetails
        StudyPlan.StepDetails stepDetails = new StudyPlan.StepDetails().fromUserInput(scanner);

        LocalDateTime createdAT = LocalDateTime.now();
        stepDetails.setStartDate(createdAT);
        stepDetails.setEndDate(createdAT.plusDays(Long.parseLong(getUserInput("Enter number of days to end"))));

        studyPlan.assignSteps(stepDetails);
    }

    private StudyGoal getStudyGoalInfo(){
        handleMethodHeader("(Study Goal Creation)");
        System.out.println("Type the following info: name \n");
        String name = getInput();
        StudyPlan studyPlan = getStudyPlanInfo();
        handleSetSteps(studyPlan);
        StudyObjective studyObjective = studyPlan.getObjective();
        return new StudyGoal(name, studyObjective, studyPlan);
    }

    private void handleAddStudyGoal(){
        StudyGoal goal = getStudyGoalInfo();
        studyTaskManager.addRegistry(goal);
    }

    private void editAudio(AudioReference audioReference){
        handleMethodHeader("(Audio Edit)");
        System.out.println("Type the following info:  AudioReference. AudioQuality audioQuality, boolean isDownloadable, " +
                "String title, String description, String link, String accessRights, String license, String language, int rating, " +
                "int viewCount, int shareCount \n");
        AudioReference.AudioQuality quality =AudioReference.audioQualityAdapter(getInput());
        boolean isDownloadable = Boolean.parseBoolean(getInput());
        AudioReference.AudioEditDetails details = new AudioReference.AudioEditDetails(
                getInput(), // title
                getInput(), // description
                getInput(), // link
                getInput(), // accessRights
                getInput(), // license
                getInput(), // language
                Integer.parseInt(getInput()), // rating
                Integer.parseInt(getInput()), // viewCount
                Integer.parseInt(getInput()) // shareCount
        );

        audioReference.editAudio(quality, isDownloadable, details);
    }

    private AudioReference addAudioReference(){
        handleMethodHeader("(Audio Reference Creation)");
        System.out.println("Type the following info: Audio Quality ( LOW | MEDIUM | HIGH | VERY_HIGH) \n");
        AudioReference audioReference = new AudioReference(AudioReference.audioQualityAdapter(getInput()));
        editAudio(audioReference);
        return audioReference;
    }

    private VideoReference addVideoReference(){
        handleMethodHeader("(Video Reference Creation)");
        System.out.println("Type the following info: boolean isAvailable, String title, " +
                "String description, String resolution, String frameRate, String videoFormat, String accessRights \n");
        return new VideoReference(Boolean.parseBoolean(getInput()), getInput(), getInput(), getInput(), getInput(),
                getInput(), getInput());
    }

    private TextReference addTextReference(){
        handleMethodHeader("(Text Reference Creation)");
        System.out.println("Type the following info:  String title, String language, int wordCount, String format, String accessRights \n");
        return new TextReference(getInput(), getInput(), Integer.parseInt(getInput()), getInput(),
                getInput());
    }

    private Reference addStudyMaterial(){
        handleMethodHeader("(Study Material Creation)");
        System.out.println("Type the following info: ( AUDIO | VIDEO | TEXT ) \n");
        String type = getInput();
        return switch (type.toLowerCase()) {
            case "audio" -> addAudioReference();
            case "video" -> addVideoReference();
            case "text" -> addTextReference();
            default -> null;
        };
    }

    private void handleAddStudyMaterial(){
        Reference reference = addStudyMaterial();
        if(reference != null){
            studyMaterial.addReference(reference);
        }
    }

    private void handleAddStudyObjective(){
        getStudyObjectiveInfo();
    }

    private void handleAddStudyPlan(){
        getStudyPlanInfo();
        System.out.println("Study Plan Added");
    }

    private void getWeekInfo(){
        System.out.println("(Study Task Manager Week Set Up) Type the following info: String planName, String objectiveTitle, " +
                "String objectiveDescription, String materialTopic, String materialFormat, String goal, String reminderTitle, " +
                "String reminderDescription, String mainTaskTitle, String mainHabit, String mainCardStudy");
        studyTaskManager.setUpWeek(getInput(), getInput(), getInput(), getInput(), getInput(), getInput(), getInput(), getInput(),
                getInput(), getInput(), getInput());
    }

    private void handleSetUpWeek(){
        getWeekInfo();
    }

    private void handleGetWeekResponsibilities(){
        List<String> responsibilities = studyTaskManager.getWeekResponsibilities();
        System.out.println(String.join(", ", responsibilities));
    }

    public void handleRegistryInput(){
        try{
            while(true){
                controllerOptions();
                String response = validateInput(actions);
                if(response == null) {return;}
                actions.get(response).run();
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void controllerOptions(){
        System.out.println("""
                0 - return
                1 - add study task
                2 - add study goal
                3 - add study material (audio, video, text)
                4 - add study objective
                5 - add study plan
                6 - set up week
                7 - get week responsibilities
               """);
    }
}

