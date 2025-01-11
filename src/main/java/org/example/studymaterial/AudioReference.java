package org.example.studymaterial;

import java.util.List;

public class AudioReference extends Reference {
    public enum AudioQuality {
        LOW, MEDIUM, HIGH, VERY_HIGH;
    }
    private AudioQuality audioQuality;

    public AudioReference(AudioQuality quality){
        this.audioQuality = quality;
    }

    public AudioQuality getAudioQuality() {
        return audioQuality;
    }

    public static AudioQuality audioQualityAdapter(String quality){
        return switch (quality.toLowerCase()) {
            case "low" -> AudioQuality.LOW;
            case "medium" -> AudioQuality.MEDIUM;
            case "high" -> AudioQuality.HIGH;
            case "very_high" -> AudioQuality.VERY_HIGH;
            default -> null;
        };
    }

    public void setAudioQuality(AudioQuality audioQuality) {
        this.audioQuality = audioQuality;
    }

    public record AudioEditDetails(
            String title,
            String description,
            String link,
            String accessRights,
            String license,
            String language,
            int rating,
            int viewCount,
            int shareCount
    ) {}

    public void editAudio(AudioQuality audioQuality, boolean isDownloadable, AudioEditDetails details) {
        setAudioProperties(audioQuality, isDownloadable);
        setDetails(details);
    }

    private void setAudioProperties(AudioQuality audioQuality, boolean isDownloadable) {
        this.setAudioQuality(audioQuality);
        this.setDownloadable(isDownloadable);
    }

    private void setDetails(AudioEditDetails details) {
        this.setTitle(details.title());
        this.setDescription(details.description());
        this.setLink(details.link());
        this.setAccessRights(details.accessRights());
        this.setLicense(details.license());
        this.setLanguage(details.language());
        this.setRating(details.rating());
        this.setViewCount(details.viewCount());
        this.setShareCount(details.shareCount());
    }


    public void editAudioAdapter(List<String> properties, List<Integer> intProperties, AudioQuality audioQuality, boolean isDownloadable) {
        AudioEditDetails details = new AudioEditDetails(
                properties.get(0), properties.get(1), properties.get(2),
                properties.get(3), properties.get(4), properties.get(5),
                intProperties.get(0), intProperties.get(1), intProperties.get(2)
        );
        editAudio(audioQuality, isDownloadable, details);
    }

     private void editVideoAttributes(int rating, String language, int viewCount, int shareCount,boolean isDownloadable){
         this.setRating(rating);
         this.setShareCount(shareCount);
         this.setViewCount(viewCount);
         this.setDownloadable(isDownloadable);
         this.setLanguage(language);
     }

     public void editBasic(String title, String description, String link){
         this.setTitle(title);
         this.setDescription(description);
         this.setLink(link);
     }

}
