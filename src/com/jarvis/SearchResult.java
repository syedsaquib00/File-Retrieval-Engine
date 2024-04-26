package com.jarvis;

public class SearchResult {
    private String fileName;
    private int occurrences;

    public SearchResult(String fileName, int occurrences) {
        this.fileName = fileName;
        this.occurrences = occurrences;
    }

    public String getFileName() {
        return fileName;
    }

    public int getOccurrences() {
        return occurrences;
    }

}
