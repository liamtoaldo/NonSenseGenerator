package com.swe.nonsense;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;

public class StorageManager {
    private String nounsFilePath;
    private String adjectivesFilePath;
    private String verbsFilePath;
    private String templatesFilePath;
    private String sentencesFilePath;

    //Costruttori
    public StorageManager() {
        this.nounsFilePath = "nouns.json"; // Questi nomi file verranno cercati alla radice del classpath
        this.adjectivesFilePath = "adjectives.json";
        this.verbsFilePath = "verbs.json";
        this.templatesFilePath = "templates.json";
        this.sentencesFilePath = "sentences.json";
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
                InputStream inputStream = new ClassPathResource(nounsFilePath).getInputStream();
                nouns = mapper.readValue(inputStream, new TypeReference<ArrayList<Noun>>(){});
                inputStream.close();
            }

            if (adjectivesFilePath != null) {
                InputStream inputStream = new ClassPathResource(adjectivesFilePath).getInputStream();
                adjectives = mapper.readValue(inputStream, new TypeReference<ArrayList<Adjective>>(){});
                inputStream.close();
            }

            if (verbsFilePath != null) {
                InputStream inputStream = new ClassPathResource(verbsFilePath).getInputStream();
                verbs = mapper.readValue(inputStream, new TypeReference<ArrayList<Verb>>(){});
                inputStream.close();
            }

            if (templatesFilePath != null) {
                InputStream inputStream = new ClassPathResource(templatesFilePath).getInputStream();
                templates = mapper.readValue(inputStream, new TypeReference<ArrayList<Template>>(){});
                inputStream.close();
            }         
        } catch (Exception e) {
            e.printStackTrace();
            // Considera una gestione dell'eccezione più specifica o il rilancio
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
                InputStream inputStream = new ClassPathResource(sentencesFilePath).getInputStream();
                sentences = mapper.readValue(inputStream, new TypeReference<ArrayList<Sentence>>(){});
                inputStream.close();
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
        // Stessa nota di saveDictionary riguardo al salvataggio dei file.
        try {
            if (sentencesFilePath != null) {
                mapper.writeValue(new File(sentencesFilePath), history.getSentences());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}