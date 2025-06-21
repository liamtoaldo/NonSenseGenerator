package com.swe.nonsense;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.core.io.ClassPathResource;

/**
 * Class that manages the storage of words and templates from dictionary, history or JSON files to dictionary, history or JSON files
 */
public class StorageManager {
    private final Path storageDir;
    private String nounsFilePath;
    private String adjectivesFilePath;
    private String verbsFilePath;
    private String templatesFilePath;
    private String sentencesFilePath;

    /**
     * Default constructor that initializes file paths to an external directory
     * in the user's home folder. If the files don't exist, they are copied
     * from the classpath resources.
     */
    public StorageManager() {
        // Define an external storage directory (e.g., ~/.NonSenseGenerator/data)
        this.storageDir = Paths.get(System.getProperty("user.home"), ".NonSenseGenerator", "data");
        try {
            // Create the directory if it doesn't exist
            Files.createDirectories(storageDir);
        } catch (IOException e) {
            throw new RuntimeException("Could not create storage directory", e);
        }
        
        // Initialize paths and copy default files if they don't exist
        this.nounsFilePath = initializeDataFile("nouns.json");
        this.adjectivesFilePath = initializeDataFile("adjectives.json");
        this.verbsFilePath = initializeDataFile("verbs.json");
        this.templatesFilePath = initializeDataFile("templates.json");
        this.sentencesFilePath = initializeDataFile("sentences.json");

        // Load data into memory right after initialization
        loadDictionary();
        loadHistory();
    }

    /**
     * Constructor that initializes the file paths to the specified values in the parameters.
     * This is useful for testing with specific file paths.
     *
     * @param nounsFilePath       Path to the nouns JSON file
     * @param adjectivesFilePath  Path to the adjectives JSON file
     * @param verbsFilePath       Path to the verbs JSON file
     * @param templatesFilePath   Path to the templates JSON file
     * @param sentencesFilePath   Path to the sentences JSON file
     */
    public StorageManager(String nounsFilePath, String adjectivesFilePath, String verbsFilePath, String templatesFilePath, String sentencesFilePath) {
        this.storageDir = null; // Not used in this constructor
        this.nounsFilePath = nounsFilePath;
        this.adjectivesFilePath = adjectivesFilePath;
        this.verbsFilePath = verbsFilePath;
        this.templatesFilePath = templatesFilePath;
        this.sentencesFilePath = sentencesFilePath;
    }

    /**
     * Checks if a data file exists in the storage directory. If not, copies it from the classpath.
     * 
     * @param fileName The name of the file (e.g., "nouns.json")
     * @return The absolute path to the data file as a String.
     */
    private String initializeDataFile(String fileName) {
        Path filePath = storageDir.resolve(fileName);
        if (!Files.exists(filePath)) {
            try (InputStream resourceStream = new ClassPathResource(fileName).getInputStream()) {
                Files.copy(resourceStream, filePath);
            } catch (IOException e) {
                System.err.println("Warning: Could not copy default file: " + fileName);
                // Continue execution, the file will be created on the first save.
            }
        }
        return filePath.toString();
    }

    //Carica i dati dal file JSON e li salva nel dizionario
    /**
     * Method that loads the dictionary from JSON files' contents by reading from the specified file paths.
     * It reads nouns, adjectives, verbs, and templates from their respective JSON files and saves them into the dictionary.
     * 
     * @return the dictionary with all the words and templates loaded from the JSON files
     */
    public WordsDictionary loadDictionary() {
        WordsDictionary dictionary = WordsDictionary.getInstance();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        ArrayList<Noun> nouns = new ArrayList<>();
        ArrayList<Adjective> adjectives = new ArrayList<>();
        ArrayList<Verb> verbs = new ArrayList<>();
        ArrayList<Template> templates = new ArrayList<>();
        ArrayList<Word> words = new ArrayList<>();

        try {
            if (nounsFilePath != null && new File(nounsFilePath).exists()) {
                nouns = mapper.readValue(new File(nounsFilePath), new TypeReference<ArrayList<Noun>>(){});
            }

            if (adjectivesFilePath != null && new File(adjectivesFilePath).exists()) {
                adjectives = mapper.readValue(new File(adjectivesFilePath), new TypeReference<ArrayList<Adjective>>(){});
            }

            if (verbsFilePath != null && new File(verbsFilePath).exists()) {
                verbs = mapper.readValue(new File(verbsFilePath), new TypeReference<ArrayList<Verb>>(){});
            }

            if (templatesFilePath != null && new File(templatesFilePath).exists()) {
                templates = mapper.readValue(new File(templatesFilePath), new TypeReference<ArrayList<Template>>(){});
            }         
        } catch (IOException e) {
            throw new RuntimeException("Failed to load dictionary files. Check JSON format.", e);
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
        
        //Salva i templates
        dictionary.saveTemplates(templates);
        return dictionary;
    }

    //Salva i dati del dizionario nel file JSON
    /**
     * Method that saves the dictionary terms into JSON files by writing into their respective file paths
     * 
     * @param dictionary The dictionary containing all the words and templates to be saved
     */
    public void saveDictionary(WordsDictionary dictionary) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        try {
            if (nounsFilePath != null) {
                mapper.writerWithDefaultPrettyPrinter().writeValue(new File(nounsFilePath), dictionary.getNouns());
            }
            if (adjectivesFilePath != null) {
                mapper.writerWithDefaultPrettyPrinter().writeValue(new File(adjectivesFilePath), dictionary.getAdjectives());
            }
            if (verbsFilePath != null) {
                mapper.writerWithDefaultPrettyPrinter().writeValue(new File(verbsFilePath), dictionary.getVerbs());
            }
            if (templatesFilePath != null) {
                mapper.writerWithDefaultPrettyPrinter().writeValue(new File(templatesFilePath), dictionary.getTemplates());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Carica i dati dal file JSON e li salva nella sentencehistory
    /**
     * Method that loads the history from JSON files' contents by reading from the specified file path.
     * It reads sentences from the sentences JSON file and saves them into the sentence history.
     * 
     * @return The history with all the sentences loaded from the JSON file
     */
    public SentenceHistory loadHistory() {
        SentenceHistory history = SentenceHistory.getInstance();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        ArrayList<Sentence> sentences = new ArrayList<>();

        try {
            if (sentencesFilePath != null && new File(sentencesFilePath).exists()) {
                sentences = mapper.readValue(new File(sentencesFilePath), new TypeReference<ArrayList<Sentence>>(){});
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load history file. Check JSON format.", e);
        }

        //Salva le sentences
        for (Sentence sentence : sentences) {
            history.save(sentence);
        }
        return history;
    }

    //Salva i dati della sentence history nel file JSON
    /**
     * Method that saves the sentences from the history into a JSON file by writing into their respective file path
     * 
     * @param history The history containing all the sentences to be saved
     */
    public void saveHistory(SentenceHistory history) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        try {
            if (sentencesFilePath != null) {
                mapper.writerWithDefaultPrettyPrinter().writeValue(new File(sentencesFilePath), history.getSentences());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}