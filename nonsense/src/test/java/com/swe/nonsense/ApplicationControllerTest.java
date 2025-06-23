package com.swe.nonsense;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ApplicationControllerTest {
    ApplicationController applicationController;
    String sentenceText;

    // JUnit gestirà la creazione e la pulizia di questa cartella temporanea
    @TempDir
    static Path tempDir;

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
        String templatesFileContent = "[{\"template\":\"The [ADJECTIVE] [NOUN] [VERB] quickly.\"}]";
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

        applicationController = new ApplicationController(nounsFilePath, adjectivesFilePath, verbsFilePath, templatesFilePath, sentencesFilePath);

        sentenceText = "This is a test sentence.";
    }

    @Test
    void testGetSyntaxTreeFromString() {
        SyntaxTree syntaxTree = applicationController.getSyntaxTreeFromString(sentenceText);

        assertNotNull(syntaxTree, "Syntax tree should not be null");
        assertNotNull(syntaxTree.getRoots(), "Syntax tree roots should not be null");
        assertFalse(syntaxTree.getRoots().isEmpty(), "Syntax tree roots should not be empty");
        assertNotNull(syntaxTree.getAllNodes(), "Syntax tree nodes should not be null");
        assertFalse(syntaxTree.getAllNodes().isEmpty(), "Syntax tree nodes should not be empty");
        for (SyntaxNode node : syntaxTree.getAllNodes()) {
            assertNotNull(node.getWord(), "Each syntax node should have a word");
            assertFalse(node.getWord().getText().isEmpty(), "Each word in the syntax node should not be empty");
        }
    }

    @Test
    void testGenerateNonSenseSentenceBasic() {
        Template template = new Template("This is a [adjective] [noun].");
        Tense tense = Tense.PRESENT;
        Sentence sentenceText = applicationController.convertStringToSentence("test cat is good");
        applicationController.addTermsToDictionaryFromInput(sentenceText);
        Sentence generated = applicationController.generateNonSenseSentence("test cat is good", template, tense);
        assertNotNull(generated, "Generated sentence should not be null");
        List<Word> words = generated.getText();
        assertNotNull(words, "Generated sentence word list should not be null");
        assertFalse(words.isEmpty(), "Generated sentence word list should not be empty");
        assertEquals(6, words.size(), "Expected 6 words for the template");
    }

    @Test
    void testGenerateNonSenseSentenceNoPlaceholders() {
        Template template = new Template("This is a [adjective] [noun].");
        Tense tense = Tense.PRESENT;
        Sentence sentenceText = applicationController.convertStringToSentence("test cat is good");
        applicationController.addTermsToDictionaryFromInput(sentenceText);
        Sentence generated = applicationController.generateNonSenseSentence("test cat is good", template, tense);
        List<Word> words = generated.getText();

        String text = words.stream()
                .map(Word::getText)
                .collect(Collectors.joining(" "));
        assertTrue(text.startsWith("This is a"), "Generated sentence should start with 'This is a'");
        // Ensure no placeholder remains
        for (Word w : words) {
            String wtext = w.getText();
            assertFalse(wtext.contains("[") || wtext.contains("]"), "No placeholder brackets should remain");
        }
    }

    @Test
    void testGenerateNonSenseSentencePlaceholderReplacementTypes() {
        Template template = new Template("This is a [adjective] [noun].");
        Tense tense = Tense.PRESENT;
        Sentence sentenceText = applicationController.convertStringToSentence("test cat is good");
        applicationController.addTermsToDictionaryFromInput(sentenceText);
        Sentence generated = applicationController.generateNonSenseSentence("test cat is good", template, tense);

        // Placeholder positions: index 3 -> adjective, index 4 -> noun
        Word adj = generated.getText().get(3);
        Word noun = generated.getText().get(4);

        // Assert that it's type of Adjective and Noun
        assertTrue(adj instanceof Adjective, "Word at index 3 should be an Adjective");
        assertTrue(noun instanceof Noun, "Word at index 4 should be a Noun");
    }

    @Test
    void testGetSentenceToxicity() {
        Sentence good = new Sentence("Hello world");
        ModerationResult result = applicationController.getSentenceToxicity(good);

        assertNotNull(result, "ModerationResult should not be null");
    }

    @Test
    void testGetSentenceToxicityInvalid() {
        assertThrows(IllegalArgumentException.class, () -> applicationController.getSentenceToxicity(null));
        assertThrows(IllegalArgumentException.class, () -> applicationController.getSentenceToxicity(new Sentence("")));
    }

    @Test
    void testSaveAndGetLastSentences() {
        Sentence one = new Sentence("Unique sentence one");
        applicationController.saveGeneratedSentence(one);

        List<Sentence> history = applicationController.getLastSentences(1);
        assertNotNull(history, "History list should not be null");
        assertEquals(1, history.size(), "Should return exactly one sentence");
    }

    @Test
    void testGetLastSentencesInvalid() {
        assertThrows(IllegalArgumentException.class, () -> applicationController.getLastSentences(0));
        assertThrows(IllegalArgumentException.class, () -> applicationController.getLastSentences(-1));
    }

    @Test
    void testAddTermsToDictionaryFromInput() {
        Sentence input = new Sentence("Some random test words");
        assertDoesNotThrow(() -> applicationController.addTermsToDictionaryFromInput(input));
    }

    @Test
    void testAddTermsToDictionaryFromInputInvalid() {
        assertThrows(IllegalArgumentException.class, () -> applicationController.addTermsToDictionaryFromInput(null));
        assertThrows(IllegalArgumentException.class,
                () -> applicationController.addTermsToDictionaryFromInput(new Sentence("")));
    }

    @Test
    void testGetTemplates() {
        List<Template> templates = applicationController.getTemplates();
        assertNotNull(templates, "Templates list should not be null");
        assertFalse(templates.isEmpty(), "Templates list should not be empty");
        assertEquals(1, templates.size(), "Should contain one template");
        assertEquals("The [ADJECTIVE] [NOUN] [VERB] quickly.", templates.get(0).getTemplate(),
                "Template text should match the expected value");
    }

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

        // Pulisce i dati dai singleton dopo ogni test per garantire l'isolamento
        WordsDictionary.getInstance().clearAllData();
        SentenceHistory.getInstance().clearData();
    }
}
