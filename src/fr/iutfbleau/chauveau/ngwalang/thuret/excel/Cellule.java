package fr.iutfbleau.chauveau.ngwalang.thuret.excel;
/**
 * Classe <code>Cellule</code> tirée du diagramme de classe fait par Côme
 * Thuret. Elle sert à matérialiser la cellule, stocker la valeur associée
 * , la formule ainsi son Etat lors de l'inscription d'une formule
 */
public class Cellule {
    private int colIndex;
    private char ligIndex;
    private Etat etat;
    private String formule;
    private double valeur;
    /**
     * Constructeur pour une nouvelle cellule
     * @param col sert à définir la colonne
     * @param ligne sert à définir la ligne
     */
    public Cellule(int col, char ligne){
        this.colIndex = col;
        this.ligIndex = ligne;
    }
    /**
     * Permet de définir l'Etat de la cellule
     * @param e représente l'etat que l'on veut appliquer
     */
    public void setetat(Etat e){
        etat = e;
    }
    /**
     * Récupère la formule donnée au tableau
     * @param f représente la formule du tableur
    */
    public void setformule(String f){
        formule = f;
    }
    /**
     * Récupère le résultat du calcul de la formule
     * @param value représente ce résultat
     */
    public void setvaleur(double value){
        valeur = value;
    }
    /**
     * Renvoie l'index de la colonne
     * @return un index
     */
    public int getcolIndex(){
        return colIndex;
    }
    /**
     * Renvoie l'index de la ligne
     * @return une ligne
     */
    public char getligIndex(){
        return ligIndex;
    }
    /**
     * Renvoie l'Etat de la cellule
     * @return un état
    */
    public Etat getetat(){
        return etat;
    }
    /**
     * Renvoie la dernière formule connue
     * @return une formule
     */
    public String getformule(){
        return formule;
    }
    /**
     * Renvoie le dernier résultat connu
     * @return valeur
     */
    public double getvaleur(){
        return valeur;
    }
}
