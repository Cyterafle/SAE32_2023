package fr.iutfbleau.chauveau.ngwalang.thuret.excel;

import java.util.*;

/**
 * Classe <code>Cellule</code> tirée du diagramme de classe fait par Côme
 * Thuret. Elle sert à matérialiser la cellule, stocker la valeur associée
 * , la formule ainsi son Etat lors de l'inscription d'une formule
 */
public class Cellule implements CellObserver {
    private Etat etat;
    private String formule;
    //private String valeurAlt;
    private double valeur;
    private ArbreBinaire arbre;
    private List<Cellule> observers;
    private ModelTableur model;
    /**
     * Constructeur pour une nouvelle cellule
     */
    public Cellule(ModelTableur m){
        observers = new ArrayList<>();
        this.etat = Etat.VIDE;
        this.arbre = new ArbreBinaire(m);
        model = m;
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
     * Renvoie l'Etat de la cellule
     * @return un état
    */
    public Etat getetat(){
        return this.etat;
    }
    /**
     * Renvoie la dernière formule connue
     * @return la formule
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
    /**
     * Getter pour l'arbre binaire
     * @return l'arbre binaire associé à la cellule
     * 
     */
    public ArbreBinaire getArbre(){
        return arbre;
    }
    /**
     * Modifie la valeur alternative de la cellule
     * @param valeur correspond à la nouvelle valeur
     */
    /*public void setvaleurAlt(String valeur){
        valeurAlt = valeur;
    }*/

    /**
     * Recupère la valeur alternative de la cellule
     * @return la dernière valeur connue pour l'attribut
     */
    /*public String getvaleurAlt(){
        return valeurAlt;
    }*/


    /**
     * Ajoute un observeur 
     * @param c la cellule à ajouter
     */
    public void addCellObserver(Cellule c){
        observers.add(c);
    }
    /**
     * Retire un observeur
     * @param c la cellule à retirer
     */
    public void removeCellObserver(Cellule c){
        observers.remove(c);
    }

    /**
     * Prévient les observeurs d'une modification sur l'objet observé
     */
    public void notifyCellObservers(){
        for (Cellule c : observers){
            c.update();
            if (! c.observers.isEmpty())
                c.notifyCellObservers();
        }
        model.updateView();
    }
    
    @Override
    public void update() {
        if (etat != Etat.REFERENCE_CIRCULAIRE)
            valeur = arbre.calculer();
    }
}
