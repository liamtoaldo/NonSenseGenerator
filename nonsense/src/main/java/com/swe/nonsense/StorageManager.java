package com.swe.nonsense;

import java.io.File;
import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StorageManager {
    private String nounsFilePath;
    private String adjectivesFilePath;
    private String verbsFilePath;
    private String templatesFilePath;
    private String sentencesFilePath;

    //Costruttori
    public StorageManager() {
        this.nounsFilePath = null;
        this.adjectivesFilePath = null;
        this.verbsFilePath = null;
        this.templatesFilePath = null;
        this.sentencesFilePath = null;
    }

    public StorageManager(String nounsFilePath, String adjectivesFilePath, String verbsFilePath, String templatesFilePath, String sentencesFilePath) {
        this.nounsFilePath = nounsFilePath;
        this.adjectivesFilePath = adjectivesFilePath;
        this.verbsFilePath = verbsFilePath;
        this.templatesFilePath = templatesFilePath;
        this.sentencesFilePath = sentencesFilePath;
    }

    //Carica i dati dal file JSON e li salva nel dizionario
    public WordsDictionary loadDictionary() {
        WordsDictionary dictionary = WordsDictionary.getInstance();
        ObjectMapper mapper = new ObjectMapper();

        ArrayList<Noun> nouns = new ArrayList<>();
        ArrayList<Adjective> adjectives = new ArrayList<>();
        ArrayList<Verb> verbs = new ArrayList<>();
        ArrayList<Template> templates = new ArrayList<>();
        ArrayList<Word> words = new ArrayList<>();

        try {
            if (nounsFilePath != null) {
                nouns = mapper.readValue(new File(nounsFilePath), new TypeReference<ArrayList<Noun>>(){});
            }

            if (adjectivesFilePath != null) {
                adjectives = mapper.readValue(new File(adjectivesFilePath), new TypeReference<ArrayList<Adjective>>(){});
            }

            if (verbsFilePath != null) {
                verbs = mapper.readValue(new File(verbsFilePath), new TypeReference<ArrayList<Verb>>(){});
            }

            if (templatesFilePath != null) {
                templates = mapper.readValue(new File(templatesFilePath), new TypeReference<ArrayList<Template>>(){});
            }         
        } catch (Exception e) {
            e.printStackTrace();
            //return null;
        }

        for (Noun noun : nouns) {
            words.add((Word) noun);
        }
        for (Adjective adjective : adjectives) {
            words.add((Word) adjective);
        }
        for (Verb verb : verbs) {
            words.add((Word) verb);
        }
        //Salva le words
        dictionary.saveTerms(words);
        
        //Salva i template
        dictionary.saveTemplates(templates);
        return dictionary;
    }

    //Salva i dati del dizionario nel file JSON
    public void saveDictionary(WordsDictionary dictionary) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            if (nounsFilePath != null) {
                mapper.writeValue(new File(nounsFilePath), dictionary.getNouns());
            }
            if (adjectivesFilePath != null) {
                mapper.writeValue(new File(adjectivesFilePath), dictionary.getAdjectives());
            }
            if (verbsFilePath != null) {
                mapper.writeValue(new File(verbsFilePath), dictionary.getVerbs());
            }
            if (templatesFilePath != null) {
                mapper.writeValue(new File(templatesFilePath), dictionary.getTemplates());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Carica i dati dal file JSON e li salva nella sentencehistory
    public SentenceHistory loadHistory() {
        SentenceHistory history = SentenceHistory.getInstance();
        ObjectMapper mapper = new ObjectMapper();

        ArrayList<Sentence> sentences = new ArrayList<>();

        try {
            if (sentencesFilePath != null) {
                sentences = mapper.readValue(new File(sentencesFilePath), new TypeReference<ArrayList<Sentence>>(){});
            }
        } catch (Exception e) {
            e.printStackTrace();
            //return null;
        }

        //Salva le sentences
        for (Sentence sentence : sentences) {
            history.save(sentence);
        }
        return history;
    }

    //Salva i dati della sentencehistory nel file JSON
    public void saveHistory(SentenceHistory history) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            if (sentencesFilePath != null) {
                mapper.writeValue(new File(sentencesFilePath), history.getSentences());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}