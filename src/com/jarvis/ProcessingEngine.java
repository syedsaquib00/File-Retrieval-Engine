package com.jarvis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class ProcessingEngine {
    private IndexStore indexStore;

    public ProcessingEngine(IndexStore indexStore) {
        this.indexStore = indexStore;
    }

    public void index(String datasetPath) {
    	long startTime = System.currentTimeMillis(); 
        File[] files = new File(datasetPath).listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    indexFile(file);
                }
            }
        }
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        System.out.println("Wall time for indexing: " + elapsedTime + " milliseconds");
    }
    
    
    private void indexFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split("\\s+");
                for (String word : words) {
                    String term = word.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
                    int currentFrequency = indexStore.lookup(term).getOrDefault(file, 0);
                    indexStore.update(term, file, currentFrequency + 1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    
    public List<SearchResult> search(String query) {
    	long startTime = System.currentTimeMillis();
        List<String> terms = parseQuery(query);
        Map<File, Integer> fileOccurrences = new HashMap<>();

        for (String term : terms) {
            Map<File, Integer> termOccurrences = indexStore.lookup(term);
            for (Map.Entry<File, Integer> entry : termOccurrences.entrySet()) {
                File file = entry.getKey();
                int frequency = entry.getValue();
                int currentFrequency = fileOccurrences.getOrDefault(file, 0);
                boolean allTermsPresent = true;
                for (String queryTerm : terms) {
                    if (!indexStore.lookup(queryTerm).containsKey(file)) {
                        allTermsPresent = false;
                        break;
                    }
                }
                if (allTermsPresent) {
                    fileOccurrences.put(file, currentFrequency + frequency);
                }
            }
        }

        List<Map.Entry<File, Integer>> sortedResults = new ArrayList<>(fileOccurrences.entrySet());
        sortedResults.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        List<SearchResult> searchResults = new ArrayList<>();
        for (Map.Entry<File, Integer> entry : sortedResults) {
            File file = entry.getKey();
            int occurrences = entry.getValue();
            searchResults.add(new SearchResult(file.getName(), occurrences));
        }

        long endTime = System.currentTimeMillis(); 
        long elapsedTime = endTime - startTime;
        System.out.println("Wall time for search: " + elapsedTime + " milliseconds");
        return searchResults.size() > 10 ? searchResults.subList(0, 10) : searchResults;
    }
    
    private List<String> parseQuery(String query) {
        String[] terms = query.split("\\s+AND\\s+");
        return Arrays.asList(terms);
    }

}
