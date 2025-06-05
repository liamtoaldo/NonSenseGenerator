package com.swe.nonsense;

import java.util.ArrayList;

public class SyntaxTree {
    private SyntaxNode root;

    public SyntaxTree() {
        this.root = null;
    }

    public SyntaxTree(SyntaxNode root) {
        this.root = root;
    }

    public SyntaxNode getRoot() {
        return root;
    }

    public void setRoot(SyntaxNode root) {
        this.root = root;
    }

    /**
     * Restituisce una lista di tutti i nodi dell'albero di sintassi
     * in un ordine di attraversamento in pre-ordine (radice, poi figli ricorsivamente).
     * Se i figli di ogni nodo sono memorizzati nell'ordine in cui appaiono nella frase originale,
     * questo attraversamento produrrà i nodi in un ordine strutturato e leggibile.
     *
     * @return Una lista di SyntaxNode. Se l'albero è vuoto, restituisce una lista vuota.
     */
    public ArrayList<SyntaxNode> getAllNodesInOrder() {
        ArrayList<SyntaxNode> orderedNodes = new ArrayList<>();
        if (this.root != null) {
            collectNodesPreOrder(this.root, orderedNodes);
        }
        return orderedNodes;
    }

    /**
     * Metodo helper ricorsivo per collezionare i nodi in pre-ordine.
     * @param node Il nodo corrente da visitare.
     * @param collectedNodes La lista in cui accumulare i nodi visitati.
     */
    private void collectNodesPreOrder(SyntaxNode node, ArrayList<SyntaxNode> collectedNodes) {
        if (node == null) {
            return;
        }
        
        collectedNodes.add(node); 
        
        if (node.getChildren() != null) { 
            for (SyntaxNode child : node.getChildren()) {
                collectNodesPreOrder(child, collectedNodes); 
            }
        }
    }
}
