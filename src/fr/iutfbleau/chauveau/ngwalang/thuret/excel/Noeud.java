package fr.iutfbleau.chauveau.ngwalang.thuret.excel;

public class Noeud {
    private String valeur;
    private Noeud gauche, droit;

    public Noeud(String valeur) {
        this.valeur = valeur;
        this.gauche = this.droit = null;
    }

    @Override
    public String toString() {
        return this.valeur;
    }

    public Noeud getGauche(){
        return gauche;
    }

    public Noeud getDroit(){
        return droit;
    }

    public void setGauche(Noeud n){
        gauche = n;
    }

    public void setDroit(Noeud n){
        droit = n;
    }
}
