package com.swe.nonsense;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StorageManagerTest {

    private StorageManager storageManager;
    private WordsDictionary wordsDictionary;
    private SentenceHistory sentenceHistory;

    private File nounsFilePath;
    private File adjectivesFilePath;
    private File verbsFilePath;
    private File templatesFilePath;
    private File sentencesFilePath;
    
    //Inizializza i file temporanei per i test
    @BeforeEach
    public void setUp() {
        nounsFilePath = new File("nouns_temp.json");
        adjectivesFilePath = new File("adjectives_temp.json");
        verbsFilePath = new File("verbs_temp.json");
        templatesFilePath = new File("templates_temp.json");
        sentencesFilePath = new File("sentences_temp.json");

        String nounsFileContent = "[{\"value\":\"cat\"},{\"value\":\"dog\"}]";
        String adjectivesFileContent = "[{\"value\":\"big\"},{\"value\":\"small\"}]";
        String verbsFileContent = "[{\"value\":\"jumps\"},{\"value\":\"runs\"}]";
        String templatesFileContent = "[{\"value\":\"The {adjective} {noun} {verb} quickly.\"}]";
        String sentencesFileContent = "[{\"value\":\"The small cat jumps quickly.\"}]";

        try {
            Files.writeString(nounsFilePath.toPath(), nounsFileContent);
            Files.writeString(adjectivesFilePath.toPath(), adjectivesFileContent);
            Files.writeString(verbsFilePath.toPath(), verbsFileContent);
            Files.writeString(templatesFilePath.toPath(), templatesFileContent);
            Files.writeString(sentencesFilePath.toPath(), sentencesFileContent);
        } catch (Exception e) {
            e.printStackTrace();
        }

        storageManager = new StorageManager(nounsFilePath.getAbsolutePath(), adjectivesFilePath.getAbsolutePath(), verbsFilePath.getAbsolutePath(), templatesFilePath.getAbsolutePath(), sentencesFilePath.getAbsolutePath());
        wordsDictionary = WordsDictionary.getInstance();
        sentenceHistory = SentenceHistory.getInstance();
    }
    
    //Rimuove i file temporanei dopo il test
    @AfterEach
    public void cleanUp() {
        if (nounsFilePath.exists()) {
            nounsFilePath.delete();
        }
        if (adjectivesFilePath.exists()) {
            adjectivesFilePath.delete();
        }
        if (verbsFilePath.exists()) {
            verbsFilePath.delete();
        }
        if (templatesFilePath.exists()) {
            templatesFilePath.delete();
        }
        if (sentencesFilePath.exists()) {
            sentencesFilePath.delete();
        }
    }

    @Test
    void testLoadDictionary() {
        //This method should return a new instance of WordsDictionary
        //For the sake of this example, we will return a new instance directly.
        wordsDictionary.clearAllData();
        wordsDictionary = storageManager.loadDictionary();
        assertNotNull(wordsDictionary.getNouns(), "loadDictionary should contain nouns");
        assertNotNull(wordsDictionary.getAdjectives(), "loadDictionary should contain adjectives");
        assertNotNull(wordsDictionary.getVerbs(), "loadDictionary should contain verbs");
        assertNotNull(wordsDictionary.getTemplates(), "loadDictionary should contain templates");
    }

    @Test
    void testSaveDictionary() {
        try {
            Files.writeString(nounsFilePath.toPath(), "[]");
            Files.writeString(adjectivesFilePath.toPath(), "[]");
            Files.writeString(verbsFilePath.toPath(), "[]");
            Files.writeString(templatesFilePath.toPath(), "[]");
            Files.writeString(sentencesFilePath.toPath(), "[]");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        storageManager.saveDictionary(wordsDictionary);

        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Noun> nouns;
        ArrayList<Adjective> adjectives;
        ArrayList<Verb> verbs;
        ArrayList<Template> templates;

        try {
            nouns = mapper.readValue(nounsFilePath, new TypeReference<ArrayList<Noun>>(){});
            adjectives = mapper.readValue(adjectivesFilePath, new TypeReference<ArrayList<Adjective>>(){});
            verbs = mapper.readValue(verbsFilePath, new TypeReference<ArrayList<Verb>>(){});
            templates = mapper.readValue(templatesFilePath, new TypeReference<ArrayList<Template>>(){});
        } catch (Exception e) {
            e.printStackTrace();
            nouns = new ArrayList<>();
            adjectives = new ArrayList<>();
            verbs = new ArrayList<>();
            templates = new ArrayList<>();
        }
        assert wordsDictionary.getNouns().equals(nouns) : "Nouns in file should match nouns in dictionary";
        assert wordsDictionary.getAdjectives().equals(adjectives) : "Adjectives in file should match adjectives in dictionary";
        assert wordsDictionary.getVerbs().equals(verbs) : "Verbs in file should match verbs in dictionary";
        assert wordsDictionary.getTemplates().equals(templates) : "Templates in file should match templates in dictionary";
    }

    @Test
    void testLoadHistory() {
        sentenceHistory = storageManager.loadHistory();
        assert sentenceHistory.getSentences() != null : "loadSentences should contain sentences";
    }

    @Test
    void testSaveHistory() {
        try {
            Files.writeString(sentencesFilePath.toPath(), "[]");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        storageManager.saveHistory(sentenceHistory);

        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Sentence> sentences;

        try {
            sentences = mapper.readValue(sentencesFilePath, new TypeReference<ArrayList<Sentence>>(){});
        } catch (Exception e) {
            e.printStackTrace();
            sentences = new ArrayList<>();
        }
        assert sentenceHistory.getSentences().equals(sentences) : "Sentences in file should match sentences in history";
    }
}