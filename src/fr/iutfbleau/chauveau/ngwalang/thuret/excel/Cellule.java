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
     * Constructeur alternatif qui crée une cellule en définissant l'état de la formule à vide
     */
    public Cellule(){
        this.etat = Etat.VIDE;
    }
    /**
     * Permet de définir l'Etat de la cellule
     * @param e représente l'etat que l'on veut appliquer
     */
    public void setetat(Etat e){
        this.etat = e;
    }
    /**
     * Récupère la formule donnée au tableau
     * @param f représente la formule du tableur
    */
    public void setformule(String f){
        this.formule = f;
    }
    /**
     * Récupère le résultat du calcul de la formule
     * @param value représente ce résultat
     */
    public void setvaleur(double value){
        this.valeur = value;
    }
    /**
     * Renvoie l'index de la colonne
     * @return un index
     */
    public int getcolIndex(){
        return this.colIndex;
    }
    /**
     * Renvoie l'index de la ligne
     * @return une ligne
     */
    public char getligIndex(){
        return this.ligIndex;
    }
    /**
     * Renvoie l'Etat de la cellule
     * @return un état
    */
    public Etat getetat(){
        return this.etat;
    }
    /**
     * Renvoie la dernière formule connue
     * @return une formule
     */
    public String getformule(){
        return this.formule;
    }
    /**
     * Renvoie le dernier résultat connu
     * @return valeur
     */
    public double getvaleur(){
        return this.valeur;
    }
}
