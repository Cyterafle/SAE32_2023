

public class Noeud {
    String valeur;
    Noeud gauche, droit;

    public Noeud(String valeur) {
        this.valeur = valeur;
        this.gauche = this.droit = null;
    }

    @Override
    public String toString() {
        return valeur;
    }
}
