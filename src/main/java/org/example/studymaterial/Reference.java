package org.example.studymaterial;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

// Enum for access levels
enum AccessLevel {
    PUBLIC, PRIVATE, RESTRICTED, UNKNOWN
}

public abstract class Reference {
    private String title;
    private String description;
    private String link;
    private String accessRights;
    private String license;
    private boolean isDownloadable;
    private int rating;
    private String language;
    private int viewCount;
    private int downloadCount;
    private int shareCount;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public String getAccessRights() {
        return accessRights;
    }

    public void setAccessRights(String accessRights) {
        this.accessRights = accessRights;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public boolean getIsDownloadable() {
        return isDownloadable;
    }

    public void setDownloadable(boolean downloadable) {
        isDownloadable = downloadable;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
    }

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }

    // Função para verificar se o recurso está disponível para download
    public boolean isAvailableForDownload() {
        return this.isDownloadable;
    }

    // Função para incrementar a contagem de visualizações
    public void incrementViewCount() {
        this.viewCount++;
    }

    // Função para incrementar a contagem de downloads
    public void incrementDownloadCount() {
        if (this.isDownloadable) {
            this.downloadCount++;
        }
    }

    // Função para incrementar a contagem de compartilhamentos
    public void incrementShareCount() {
        this.shareCount++;
    }

    public boolean isPopular() {
        // Considera um recurso popular se o número de visualizações e compartilhamentos for alto
        int popularityThreshold = 100; // Ajuste este valor conforme necessário
        return viewCount > popularityThreshold && shareCount > popularityThreshold / 2;
    }

    public boolean isRelevantToTopic(String topic) {
        // Implementação simplificada, considerando a presença do tema no título ou descrição
        return title.toLowerCase().contains(topic.toLowerCase()) ||
                description.toLowerCase().contains(topic.toLowerCase());
    }

    public String getBriefDescription() {
        // Retorna as primeiras 50 palavras da descrição
        String[] words = description.split("\\s+");
        return String.join(" ", Arrays.copyOfRange(words, 0, Math.min(words.length, 50)));
    }

    public double calculatePopularityScore() {
        // Combine viewCount, downloadCount, and shareCount into a single score
        // Normalize each count to prevent bias from a single metric
        double viewCountNormalized = (double) viewCount / 1000; // Example normalization
        double downloadCountNormalized = (double) downloadCount / 100;
        double shareCountNormalized = (double) shareCount / 50;
        return viewCountNormalized + downloadCountNormalized + shareCountNormalized;
    }

    public AccessLevel getAccessLevel() {
        if (accessRights.equals("Public")) {
            return AccessLevel.PUBLIC;
        } else if (accessRights.equals("Private")) {
            return AccessLevel.PRIVATE;
        } else if (accessRights.equals("Restricted")) {
            return AccessLevel.RESTRICTED;
        } else {
            return AccessLevel.UNKNOWN;
        }
    }

    public boolean hasCopyrightRestrictions() {
        // Check if the license implies copyright restrictions
        List<String> restrictiveLicenses = Arrays.asList("All Rights Reserved", "Copyright Protected");
        return restrictiveLicenses.contains(license);
    }

    public boolean isCompatibleWithLanguage(String targetLanguage) {
        // Check if the resource is in the target language or has a translation available
        return language.equals(targetLanguage); // Simple check, can be more sophisticated
    }

    public String getInferredResourceType() {
        // Infer resource type based on title, link, and keywords
        if (link.endsWith(".pdf")) {
            return "Document";
        } else if (link.endsWith(".mp4") || link.endsWith(".avi")) {
            return "Video";
        } else if (title.contains("Presentation") || link.endsWith(".pptx")) {
            return "Presentation";
        } else {
            return "Unknown";
        }
    }

    public boolean isOpenAccess() {
        // Verifica se o recurso possui uma licença aberta (ex: Creative Commons)
        List<String> openLicenses = Arrays.asList("CC BY", "CC BY-SA", "Public Domain");
        return openLicenses.contains(license);
    }

    public String getPrimaryLanguage() {
        // Retorna a linguagem principal do recurso
        return language;
    }

    public String classifyContentType() {
        // Classifica o recurso com base no título e descrição
        if (title.contains("tutorial") || description.contains("how to")) {
            return "Tutorial";
        } else if (title.contains("paper") || description.contains("research")) {
            return "Research Paper";
        } else {
            return "Other";
        }
    }

    public double calculateEngagementScore() {
        // Combina visualizações, downloads e compartilhamentos em uma única pontuação
        return viewCount * 0.5 + downloadCount * 0.3 + shareCount * 0.2;
    }

    public boolean isMobileFriendly() {
        // Verifica se o link aponta para um site responsivo ou se o formato do arquivo é compatível com dispositivos móveis
        // (Implementação simplificada)
        return link.contains("m.") || link.endsWith(".pdf") || link.endsWith(".epub");
    }

    public boolean isOfflineAccessible() {
        return isDownloadable;
    }

    public String getShortTitle() {
        // Retorna os primeiros 50 caracteres do título
        return title.substring(0, Math.min(title.length(), 50));
    }

    public boolean isRelevantToQuery(String query) {
        // Implementação simplificada: verifica se a consulta está contida no título ou descrição
        return title.toLowerCase().contains(query.toLowerCase()) ||
                description.toLowerCase().contains(query.toLowerCase());
    }

    public boolean containsMultimedia() {
        // Implementação simplificada: verifica se o título ou descrição contém palavras-chave relacionadas a mídia
        return title.toLowerCase().contains("video") || title.toLowerCase().contains("imagem") ||
                description.toLowerCase().contains("video") || description.toLowerCase().contains("imagem");
    }

    public List<String> extractKeywords() {
        // Implementação simplificada: divide o título e a descrição em palavras e retorna as primeiras N
        String text = title + " " + description;
        String[] words = text.split("\\s+");
        return Arrays.asList(words).subList(0, Math.min(words.length, 5));
    }


    public boolean isViral() {
        // Define um critério arbitrário para um recurso ser considerado viral (ajuste conforme necessário)
        return shareCount > 1000 && viewCount > 10000;
    }

    public String getShortTitle(int numberOfWords) {
        String[] words = title.split("\\s+");
        return String.join(" ", Arrays.copyOfRange(words, 0, Math.min(numberOfWords, words.length)));
    }

    public boolean isTutorial() {
        return title.toLowerCase().contains("tutorial") || description.toLowerCase().contains("tutorial") ||
                title.toLowerCase().contains("como") || description.toLowerCase().contains("como");
    }

    public boolean isSuitableForAllAudiences() {
        // Verifica se o título e descrição não contêm palavras ou frases que possam ser consideradas ofensivas ou inadequadas
        // (Implementação simplificada, pode ser aprimorada com uma lista de palavras proibidas ou um classificador de texto)
        return !title.toLowerCase().contains("ofensivo") && !description.toLowerCase().contains("ofensivo");
    }

    public String generateUniqueId() {
        // Combina partes do título e link para criar um identificador único (pode ser aprimorado com um algoritmo de hash)
        return title.substring(0, 5) + "_" + link.hashCode();
    }


}