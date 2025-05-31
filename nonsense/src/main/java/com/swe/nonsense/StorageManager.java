package com.swe.nonsense;

import java.util.ArrayList;

public class StorageManager {
    private String nounsFilePath;
    private String adjectivesFilePath;
    private String verbsFilePath;
    private String templatesFilePath;
    private String sentencesFilePath;

    //MODIFICAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
    public StorageManager() {
        this.nounsFilePath = null;
        this.adjectivesFilePath = null;
        this.verbsFilePath = null;
        this.templatesFilePath = null;
        this.sentencesFilePath = null;
    }

    //MODIFICAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
    public StorageManager(String nounsFilePath, String adjectivesFilePath, String verbsFilePath, String templatesFilePath, String sentencesFilePath) {
        this.nounsFilePath = nounsFilePath;
        this.adjectivesFilePath = adjectivesFilePath;
        this.verbsFilePath = verbsFilePath;
        this.templatesFilePath = templatesFilePath;
        this.sentencesFilePath = sentencesFilePath;
    }

    //MODIFICAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
    public WordsDictionary loadDictionary() {
        return null;
    }

    //MODIFICAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
    public void saveDictionary(WordsDictionary dictionary) {
        
    }

    //MODIFICAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
    public SentenceHistory loadhistory() {
        return null;
    }

    //MODIFICAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
    public void saveHistory(SentenceHistory history) {

    }
}