package com.swe.nonsense;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class StorageManagerTest {

    private StorageManager storageManager;
    private WordsDictionary wordsDictionary;
    private SentenceHistory sentenceHistory;

    // JUnit gestirà la creazione e la pulizia di questa cartella temporanea
    @TempDir
    Path tempDir;

    private String nounsFilePath;
    private String adjectivesFilePath;
    private String verbsFilePath;
    private String templatesFilePath;
    private String sentencesFilePath;
    
    //Inizializza i file temporanei per i test
    @BeforeEach
    public void setUp() throws IOException {
        // JSON corretto: doppi apici e nomi delle proprietà ("text", "template") corretti.
        String nounsFileContent = "[{\"text\":\"cat\"},{\"text\":\"dog\"}]";
        String adjectivesFileContent = "[{\"text\":\"big\"},{\"text\":\"small\"}]";
        String verbsFileContent = "[{\"text\":\"jumps\"},{\"text\":\"runs\"}]";
        String templatesFileContent = "[{\"template\":\"The {adjective} {noun} {verb} quickly.\"}]";
        // Per Sentence, la proprietà "text" deve essere una lista di oggetti Word.
        String sentencesFileContent = "[{\"text\":[{\"text\":\"The\"},{\"text\":\"small\"},{\"text\":\"cat\"},{\"text\":\"jumps\"},{\"text\":\"quickly\"}],\"timestamp\":\"2025-06-21T12:30:00\"}]";

        nounsFilePath = tempDir.resolve("nouns_temp.json").toString();
        adjectivesFilePath = tempDir.resolve("adjectives_temp.json").toString();
        verbsFilePath = tempDir.resolve("verbs_temp.json").toString();
        templatesFilePath = tempDir.resolve("templates_temp.json").toString();
        sentencesFilePath = tempDir.resolve("sentences_temp.json").toString();

        Files.writeString(Path.of(nounsFilePath), nounsFileContent);
        Files.writeString(Path.of(adjectivesFilePath), adjectivesFileContent);
        Files.writeString(Path.of(verbsFilePath), verbsFileContent);
        Files.writeString(Path.of(templatesFilePath), templatesFileContent);
        Files.writeString(Path.of(sentencesFilePath), sentencesFileContent);

        storageManager = new StorageManager(nounsFilePath, adjectivesFilePath, verbsFilePath, templatesFilePath, sentencesFilePath);
        
        // Pulisce i dati dai singleton prima di ogni test per garantire l'isolamento
        wordsDictionary = WordsDictionary.getInstance();
        wordsDictionary.clearAllData();
        sentenceHistory = SentenceHistory.getInstance();
        sentenceHistory.clearData();
    }
    
    //Rimuove i file temporanei dopo il test
    @AfterEach
    public void cleanUp() {
        File nounsFile = new File(nounsFilePath);
        File adjectivesFile = new File(adjectivesFilePath);
        File verbsFile = new File(verbsFilePath);
        File templatesFile = new File(templatesFilePath);
        File sentencesFile = new File(sentencesFilePath);

        if (nounsFile.exists()) {
            nounsFile.delete();
        }
        if (adjectivesFile.exists()) {
            adjectivesFile.delete();
        }
        if (verbsFile.exists()) {
            verbsFile.delete();
        }
        if (templatesFile.exists()) {
            templatesFile.delete();
        }
        if (sentencesFile.exists()) {
            sentencesFile.delete();
        }
    }

    @Test
    void testLoadDictionary() {
        // Azione
        storageManager.loadDictionary();

        // Asserzioni
        assertEquals(2, wordsDictionary.getNouns().size(), "Dovrebbero essere caricati 2 sostantivi");
        assertEquals("cat", wordsDictionary.getNouns().get(0).getText());

        assertEquals(2, wordsDictionary.getAdjectives().size(), "Dovrebbero essere caricati 2 aggettivi");
        assertEquals("big", wordsDictionary.getAdjectives().get(0).getText());

        assertEquals(2, wordsDictionary.getVerbs().size(), "Dovrebbero essere caricati 2 verbi");
        assertEquals("jumps", wordsDictionary.getVerbs().get(0).getText());

        assertEquals(1, wordsDictionary.getTemplates().size(), "Dovrebbe essere caricato 1 template");
        assertEquals("The {adjective} {noun} {verb} quickly.", wordsDictionary.getTemplates().get(0).getTemplate());
    }

    @Test
    void testSaveDictionary() throws IOException {
        // Preparazione: Carica i dati iniziali nel dizionario
        storageManager.loadDictionary();
        
        // Azione: Salva il dizionario
        storageManager.saveDictionary(wordsDictionary);

        // Asserzione: Rilegge i file e verifica il contenuto
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<Noun> nouns = mapper.readValue(new File(nounsFilePath), new TypeReference<ArrayList<Noun>>(){});
        assertEquals(2, nouns.size());
        assertEquals("cat", nouns.get(0).getText());

        List<Adjective> adjectives = mapper.readValue(new File(adjectivesFilePath), new TypeReference<ArrayList<Adjective>>(){});
        assertEquals(2, adjectives.size());
        assertEquals("big", adjectives.get(0).getText());
    }

    @Test
    void testLoadHistory() {
        // Azione
        storageManager.loadHistory();

        // Asserzione
        assertEquals(1, sentenceHistory.getSentences().size(), "Dovrebbe essere caricata 1 frase");
        assertEquals("The small cat jumps quickly", sentenceHistory.getSentences().get(0).toString());
    }

    @Test
    void testSaveHistory() throws IOException {
        // Preparazione: Carica la cronologia iniziale
        storageManager.loadHistory();

        // Azione: Salva la cronologia
        storageManager.saveHistory(sentenceHistory);

        // Asserzione: Rilegge il file e verifica il contenuto
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule()); // Necessario per leggere LocalDateTime
        mapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<Sentence> sentences = mapper.readValue(new File(sentencesFilePath), new TypeReference<ArrayList<Sentence>>(){});
        
        assertEquals(1, sentences.size());
        assertEquals("The small cat jumps quickly", sentences.get(0).toString());
    }
}