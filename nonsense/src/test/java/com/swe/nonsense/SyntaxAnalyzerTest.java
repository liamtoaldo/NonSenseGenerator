package com.swe.nonsense;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SyntaxAnalyzerTest {

    private SyntaxAnalyzer analyzer;
    private SyntaxTree syntaxTree;
    private SyntaxNode root;

    @BeforeEach
    void setUp() {
        analyzer = new SyntaxAnalyzer();
        Sentence sentence = new Sentence(
                "Google, headquartered in Mountain View, unveiled the new Android phone at the Consumer Electronic Show.");
        /*
         * The resulting syntax tree should look like this:
         * unveiled (ROOT)
         * ├── Google (NSUBJ)
         * │   └── headquartered (VMOD)
         * │       └── in (PREP)
         * │           └── View (POBJ)
         * │               └── Mountain (NN)
         * ├── phone (DOBJ)
         * │   ├── the (DET)
         * │   ├── new (AMOD)
         * │   └── Android (NN)
         * └── at (PREP)
         *    └── Show (POBJ)
         *        ├── the (DET)
         *        ├── Consumer (NN)
         *        └── Electronic (NN)
         */
        syntaxTree = analyzer.analyzeSyntax(sentence);
        assertNotNull(syntaxTree, "Syntax tree should not be null (setUp).");
        assertNotNull(syntaxTree.getRoots(), "Roots of syntax tree should not be null (setUp).");
        assertFalse(syntaxTree.getRoots().isEmpty(),
                "Roots of syntax tree should not be empty (setUp).");
        // For this test, we assume the first root is the main one
        assertEquals(1, syntaxTree.getRoots().size(), "There should be exactly one root node (setUp).");
        root = syntaxTree.getRoots().get(0); // Assuming the first root is the main one
        assertNotNull(root, "Root node should not be null (setUp).");
    }

    /**
     * Restituisce una lista di figli di un nodo genitore, escludendo quelli con
     * etichetta di dipendenza "P" (punteggiatura).
     */
    private ArrayList<SyntaxNode> getNonPunctuationChildren(SyntaxNode parent) {
        if (parent == null || parent.getChildren() == null) {
            return new ArrayList<>();
        }
        return parent.getChildren().stream()
                .filter(child -> !"P".equals(child.getDependencyLabel()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Metodo helper per trovare un nodo figlio specifico per parola ed etichetta,
     * ignorando la punteggiatura.
     */
    private SyntaxNode findSignificantChildByWordAndLabel(SyntaxNode parent, String wordText, String depLabel) {
        if (parent == null)
            return null;
        return getNonPunctuationChildren(parent).stream()
                .filter(child -> child.getWord().getText().equals(wordText)
                        && child.getDependencyLabel().equals(depLabel))
                .findFirst()
                .orElse(null);
    }

    @Test
    void testRootNodeProperties() {
        assertEquals("unveiled", root.getWord().getText());
        assertEquals("ROOT", root.getDependencyLabel());
        // Figli significativi di "unveiled": Google (NSUBJ), phone (DOBJ), at (PREP)
        assertEquals(3, getNonPunctuationChildren(root).size(), "Root node should have 3 significant children.");
    }

    @Test
    void testRootChildrenExistenceAndTypes() {
        assertNotNull(findSignificantChildByWordAndLabel(root, "Google", "NSUBJ"),
                "Child 'Google (NSUBJ)' of root missing.");
        assertNotNull(findSignificantChildByWordAndLabel(root, "phone", "DOBJ"),
                "Child 'phone (DOBJ)' of root missing.");
        assertNotNull(findSignificantChildByWordAndLabel(root, "at", "PREP"),
                "Child 'at (PREP)' of root missing.");
    }

    @Test
    void testGoogleNodeStructure() {
        SyntaxNode googleNode = findSignificantChildByWordAndLabel(root, "Google", "NSUBJ");
        assertNotNull(googleNode);
        // Figlio significativo di "Google": headquartered (VMOD)
        assertEquals(1, getNonPunctuationChildren(googleNode).size(),
                "'Google' should have 1 significant child.");
        SyntaxNode hqNode = findSignificantChildByWordAndLabel(googleNode, "headquartered", "VMOD");
        assertNotNull(hqNode, "Child 'headquartered (VMOD)' of 'Google' missing.");
        // Figlio significativo di "headquartered": in (PREP)
        assertEquals(1, getNonPunctuationChildren(hqNode).size(),
                "'headquartered' should have 1 significant child.");
        SyntaxNode inNode = findSignificantChildByWordAndLabel(hqNode, "in", "PREP");
        assertNotNull(inNode, "Child 'in (PREP)' of 'headquartered' missing.");
    }

    @Test
    void testLocationSubTreeStructure() { // Testa il ramo "in Mountain View"
        SyntaxNode googleNode = findSignificantChildByWordAndLabel(root, "Google", "NSUBJ");
        SyntaxNode hqNode = findSignificantChildByWordAndLabel(googleNode, "headquartered", "VMOD");
        SyntaxNode inNode = findSignificantChildByWordAndLabel(hqNode, "in", "PREP");
        assertNotNull(inNode);
        // Figlio significativo di "in": View (POBJ)
        assertEquals(1, getNonPunctuationChildren(inNode).size(), "'in' should have 1 significant child.");
        SyntaxNode viewNode = findSignificantChildByWordAndLabel(inNode, "View", "POBJ");
        assertNotNull(viewNode, "Child 'View (POBJ)' of 'in' missing.");
        // Figlio significativo di "View": Mountain (NN)
        assertEquals(1, getNonPunctuationChildren(viewNode).size(), "'View' should have 1 significant child.");
        SyntaxNode mountainNode = findSignificantChildByWordAndLabel(viewNode, "Mountain", "NN");
        assertNotNull(mountainNode, "Child 'Mountain (NN)' of 'View' missing.");
        assertTrue(getNonPunctuationChildren(mountainNode).isEmpty(),
                "'Mountain' should not have significant children.");
    }

    @Test
    void testPhoneNodeStructureAndChildren() {
        SyntaxNode phoneNode = findSignificantChildByWordAndLabel(root, "phone", "DOBJ");
        assertNotNull(phoneNode);
        // Figli significativi di "phone": the (DET), new (AMOD), Android (NN)
        assertEquals(3, getNonPunctuationChildren(phoneNode).size(), "'phone' should have 3 significant children.");
        assertNotNull(findSignificantChildByWordAndLabel(phoneNode, "the", "DET"));
        assertNotNull(findSignificantChildByWordAndLabel(phoneNode, "new", "AMOD"));
        assertNotNull(findSignificantChildByWordAndLabel(phoneNode, "Android", "NN"));

        assertTrue(getNonPunctuationChildren(findSignificantChildByWordAndLabel(phoneNode, "the", "DET")).isEmpty());
        assertTrue(getNonPunctuationChildren(findSignificantChildByWordAndLabel(phoneNode, "new", "AMOD")).isEmpty());
        assertTrue(getNonPunctuationChildren(findSignificantChildByWordAndLabel(phoneNode, "Android", "NN")).isEmpty());
    }

    @Test
    void testEventSubTreeStructure() { // Testa il ramo "at the Consumer Electronic Show"
        SyntaxNode atNode = findSignificantChildByWordAndLabel(root, "at", "PREP");
        assertNotNull(atNode);
        // Figlio significativo di "at": Show (POBJ)
        assertEquals(1, getNonPunctuationChildren(atNode).size(), "'at' should have 1 significant child.");
        SyntaxNode showNode = findSignificantChildByWordAndLabel(atNode, "Show", "POBJ");
        assertNotNull(showNode, "Child 'Show (POBJ)' of 'at' missing.");
        // Figli significativi di "Show": the (DET), Consumer (NN), Electronic (NN)
        assertEquals(3, getNonPunctuationChildren(showNode).size(), "'Show' should have 3 significant children.");
        assertNotNull(findSignificantChildByWordAndLabel(showNode, "the", "DET"));
        assertNotNull(findSignificantChildByWordAndLabel(showNode, "Consumer", "NN"));
        assertNotNull(findSignificantChildByWordAndLabel(showNode, "Electronic", "NN"));

        assertTrue(getNonPunctuationChildren(findSignificantChildByWordAndLabel(showNode, "the", "DET")).isEmpty());
        assertTrue(getNonPunctuationChildren(findSignificantChildByWordAndLabel(showNode, "Consumer", "NN")).isEmpty());
        assertTrue(
                getNonPunctuationChildren(findSignificantChildByWordAndLabel(showNode, "Electronic", "NN")).isEmpty());
    }

    @Test
    void testEmptySyntaxTree() {
        SyntaxAnalyzer emptyAnalyzer = new SyntaxAnalyzer();
        Sentence emptySentence = new Sentence("");
        assertThrows(IllegalArgumentException.class, () -> {
            emptyAnalyzer.analyzeSyntax(emptySentence);
        }, "Analyzing an empty sentence should throw an exception.");
    }

}
