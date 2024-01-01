package fr.iutfbleau.chauveau.ngwalang.thuret.excel;
public class ArbreBinaire {
    Noeud racine;

    public ArbreBinaire() {
        this.racine = null;
    }

    // Méthode pour insérer un nouvel élément dans l'arbre
    private Noeud insererNoeud(Noeud racine, String[] expression, int index) {
        if (index < expression.length) {
            String valeur = expression[index];

            if (estOperateur(valeur)) {
                racine = new Noeud(valeur);
                racine.gauche = insererNoeud(racine.gauche, expression, index + 1);
                racine.droit = insererNoeud(racine.droit, expression, index + 2);
            } else {
                racine = new Noeud(valeur);
            }
        }

        return racine;
    }

    public void inserer(String expression) {
        String[] elements = expression.split(" ");
        racine = insererNoeud(racine, elements, 0);
    }

    //détermine si la valeur est un opérateur
    private boolean estOperateur(String valeur) {
        return valeur.equals("+") || valeur.equals("-") || valeur.equals("*") || valeur.equals("/");
    }

    //calcule le résultat du calcul dans l'arbre
    public double calculer() {
        return calculer(racine);
    }

    // Méthode récursive pour calculer le résultat dans un sous-arbre
    private double calculer(Noeud racine) {
        if (racine == null) {
            return 0.0;
        }

        if (!estOperateur(racine.valeur)) {
            return Double.parseDouble(racine.valeur);
        }
        double gauche = calculer(racine.gauche);
        double droit = calculer(racine.droit);

        switch (racine.valeur) {
            case "+":
                return gauche + droit;
            case "-":
                return gauche - droit;
            case "*":
                return gauche * droit;
            case "/":
                if (droit != 0) {
                    return gauche / droit;
                } else {
                    throw new ArithmeticException("Division par zéro");
                }
            default:
                throw new IllegalArgumentException("Opérateur non pris en charge : " + racine.valeur);
        }
    }

    private void afficherArbre(Noeud racine, String espace, String direction) {
        if (racine != null) {
            System.out.println(espace + direction + racine.valeur);
            afficherArbre(racine.gauche, espace + "│  ", "├─");
            afficherArbre(racine.droit, espace + "   ", "└─");
        }
    }

    public void afficherArbre() {
        afficherArbre(racine, "", "");
    }
}
