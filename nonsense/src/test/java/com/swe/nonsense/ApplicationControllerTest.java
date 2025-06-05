package com.swe.nonsense;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.stream.Collectors;

public class ApplicationControllerTest {
    static ApplicationController applicationController;
    static String sentenceText;

    @BeforeAll
    static void setUp() {
        applicationController = new ApplicationController();
        sentenceText = "This is a test sentence.";
    }

    @Test
    void testGetSyntaxTreeFromString() {
        SyntaxTree syntaxTree = applicationController.getSyntaxTreeFromString(sentenceText);

        assertNotNull(syntaxTree, "Syntax tree should not be null");
        assertNotNull(syntaxTree.getRoot(), "Syntax tree root should not be null");
        assertNotNull(syntaxTree.getAllNodesInOrder(), "Syntax tree nodes should not be null");
        assertFalse(syntaxTree.getAllNodesInOrder().isEmpty(), "Syntax tree nodes should not be empty");
        for (SyntaxNode node : syntaxTree.getAllNodesInOrder()) {
            assertNotNull(node.getWord(), "Each syntax node should have a word");
            assertFalse(node.getWord().getText().isEmpty(), "Each word in the syntax node should not be empty");
        }
    }

    @Test
    void testGenerateNonSenseSentenceBasic() {
        Template template = new Template("This is a [adjective] [noun].");
        Tense tense = Tense.PRESENT;
        Sentence generated = applicationController.generateNonSenseSentence("test input", template, tense);

        assertNotNull(generated, "Generated sentence should not be null");
        List<Word> words = generated.getText();
        assertNotNull(words, "Generated sentence word list should not be null");
        assertFalse(words.isEmpty(), "Generated sentence word list should not be empty");
        assertEquals(5, words.size(), "Expected 5 words for the template");
    }

    @Test
    void testGenerateNonSenseSentenceNoPlaceholders() {
        Template template = new Template("This is a [adjective] [noun].");
        Tense tense = Tense.PRESENT;
        Sentence generated = applicationController.generateNonSenseSentence("test input", template, tense);
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
        Sentence generated = applicationController.generateNonSenseSentence("test input", template, tense);
        List<Word> words = generated.getText();

        // Placeholder positions: index 3 -> adjective, index 4 -> noun
        Word adj = words.get(3);
        Word noun = words.get(4);

        //Assert that it's type of Adjective and Noun
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
        assertThrows(IllegalArgumentException.class, () -> applicationController.addTermsToDictionaryFromInput(new Sentence("")));
    }
}
