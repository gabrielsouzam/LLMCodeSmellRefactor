package org.example.studysearch;

import org.example.studycards.CardManager;
import org.example.studyplanner.HabitTracker;
import org.example.studyplanner.TodoTracker;
import org.example.studyregistry.StudyTaskManager;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SearchLog {
    private List<String> searchHistory;
    private Map<String, Integer> searchCount;
    private boolean isLocked;
    private Integer numUsages;
    private String logName;

    public SearchLog(String logName) {
        searchHistory = new ArrayList<>();
        searchCount = new HashMap<>();
        this.logName = logName;
        numUsages = 0;
        isLocked = false;
    }

    public List<String> searchInRegistry(String text){
        List<String> results = new ArrayList<>();
        results.addAll(CardManager.getCardManager().searchInCards(text));
        results.addAll(HabitTracker.getHabitTracker().searchInHabits(text));
        results.addAll(TodoTracker.getInstance().searchInTodos(text));
        results.addAll(StudyTaskManager.getStudyTaskManager().searchInRegistries(text));
        addSearchHistory(text);
        setNumUsages(getNumUsages() + 1);
        results.add("\nLogged in: " + this.getLogName());
        return results;
    }

    public void logSearch(String text) {
        addSearchHistory(text);
        setNumUsages(getNumUsages() + 1);
    }

    public void addSearchHistory(String searchHistory) {
        this.searchHistory.add(searchHistory);
    }
    public List<String> getSearchHistory() {
        return searchHistory;
    }
    public void setSearchHistory(List<String> searchHistory) {
        this.searchHistory = searchHistory;
    }
    public Map<String, Integer> getSearchCount() {
        return searchCount;
    }
    public void setSearchCount(Map<String, Integer> searchCount) {
        this.searchCount = searchCount;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public Integer getNumUsages() {
        return numUsages;
    }

    public void setNumUsages(Integer numUsages) {
        this.numUsages = numUsages;
    }

    public String getLogName() {
        return logName;
    }

    public void setLogName(String logName) {
        this.logName = logName;
    }

    public void addSearch(String searchTerm) {
        searchHistory.add(searchTerm);
        searchCount.put(searchTerm, searchCount.getOrDefault(searchTerm, 0) + 1);
    }

    public String getMostFrequentSearch() {
        String mostFrequent = "";
        int maxCount = 0;
        for (Map.Entry<String, Integer> entry : searchCount.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostFrequent = entry.getKey();
            }
        }
        return mostFrequent;
    }

    public void lockLog() {
        isLocked = true;
    }

    public void unlockLog() {
        isLocked = false;
    }

    public void incrementUsage() {
        numUsages++;
    }

    @Override
    public String toString() {
        return "SearchLog{" +
                "searchHistory=" + searchHistory +
                ", searchCount=" + searchCount +
                ", isLocked=" + isLocked +
                ", numUsages=" + numUsages +
                ", logName='" + logName + '\'' +
                '}';
    }

    public List<String> getMostRecentSearches(int n) {
        if (n > searchHistory.size()) {
            return new ArrayList<>(searchHistory);
        }
        return searchHistory.subList(searchHistory.size() - n, searchHistory.size());
    }

    public double getAverageSearchLength() {
        if (searchHistory.isEmpty()) {
            return 0;
        }
        int totalLength = searchHistory.stream().mapToInt(String::length).sum();
        return (double) totalLength / searchHistory.size();
    }

    public List<String> getSearchesByRegex(String regex) {
        Pattern pattern = Pattern.compile(regex);
        return searchHistory.stream()
                .filter(search -> pattern.matcher(search).find())
                .collect(Collectors.toList());
    }

    public void removeDuplicates() {
        Set<String> uniqueSearches = new HashSet<>(searchHistory);
        searchHistory.clear();
        searchHistory.addAll(uniqueSearches);
    }

    public List<String> recommendSearches(String searchTerm) {
        // Calcular a similaridade entre o termo de busca e todos os termos da história
        Map<String, Double> similarities = new HashMap<>();
        // ... implementar a lógica de cálculo de similaridade (e.g., usando TF-IDF)

        // Ordenar os termos por similaridade
        List<Map.Entry<String, Double>> sortedEntries = similarities.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toList());

        // Retornar os n termos mais similares
        int n = 5; // Número de recomendações
        List<String> recommendations = new ArrayList<>();
        for (int i = 0; i < n && i < sortedEntries.size(); i++) {
            recommendations.add(sortedEntries.get(i).getKey());
        }
        return recommendations;
    }

    public boolean hasSearchOccurred(String searchTerm) {
        return searchHistory.contains(searchTerm);
    }

    public int getUniqueSearches() {
        return searchCount.size();
    }

}
