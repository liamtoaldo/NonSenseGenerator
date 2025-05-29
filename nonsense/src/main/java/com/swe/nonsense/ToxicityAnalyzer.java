package com.swe.nonsense;

import java.util.ArrayList;

public class ToxicityAnalyzer {
    private APIClient apiClient;

    // Costruttore
    public ToxicityAnalyzer(APIClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Analizza la tossicità di una frase.
     * 
     * @param sentence La frase da analizzare.
     * @return Il risultato dell'analisi, contenente il punteggio della relativa
     *         categoria di tossicità.
     */
    public ModerationResult analyzeToxicity(Sentence sentence) {
        // Simulazione di una chiamata all'API per l'analisi della tossicità, mancante
        // implementazione quindi metto un esempio
        ModerationResult result = new ModerationResult();
        ArrayList<ModerationCategory> categories = new ArrayList<>();
        for (Word word : sentence.getText()) {
            ModerationCategory category = new ModerationCategory("toxicity", 0.5);
            categories.add(category);
        }
        result.setCategories(categories);
        return result;
    }

    /**
     * Verifica se una frase è tossica.
     * 
     * @param sentence La frase da verificare.
     * @return true se la frase è tossica, false altrimenti.
     */
    public boolean isToxic(Sentence sentence) {
        ModerationResult result = analyzeToxicity(sentence);
        for (ModerationCategory category : result.getCategories()) {
            if (category.getName().equals("toxicity") && category.getConfidence() > 0.5) {
                return true;
            }
        }
        return false;
    }

}
