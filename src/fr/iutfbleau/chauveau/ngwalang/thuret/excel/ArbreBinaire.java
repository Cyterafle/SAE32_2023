package fr.iutfbleau.chauveau.ngwalang.thuret.excel;
public class ArbreBinaire {
    private Noeud racine;
    private ModelTableur model;

    public ArbreBinaire(ModelTableur mtab) {
        this.racine = null;
        this.model = mtab;
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

    private boolean estCellule(String valeur) {
        char[] alpha = new char[9];
        char[] num = new char[9];
        char[] element = valeur.toCharArray();
        for (int i = 0; i < 9; i++){
            alpha[i] = (char) ('A'+ i );
            num[i] = (char) ('1'+ i);
        }

        for (char eleA : alpha){
            if (eleA == element[0]){
                for (char eleN : num){
                    if (eleN == element[1]){
                        return true;
                    }
                }
                return false;
            }
        }
        return false;
    }

    private double getCellVal(String valeur){
        char[] alpha = new char[9];
        char[] num = new char[9];
        char[] element = valeur.toCharArray();
        double val = 0.0;
        for (int t = 0; t < 9; t++){
            alpha[t] = (char) ('A'+ t );
            num[t] = (char) ('1'+ t);

        }

        for (int i = 0; i < 9 ; i++){
            if (element[0] == alpha[i]){
                for (int j = 0 ; j < 9 ; j++){
                    if (element[1] == num[j]){
                        val = Double.parseDouble(this.model.getCellValue(j, i));
                    }
                }
            }
        }
        return val;
    }

    public void setModel(ModelTableur model) {
        this.model = model;
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

        else if (!estOperateur(racine.valeur)) {
            if (estCellule(racine.valeur)) {
                return getCellVal(racine.valeur);
            } else {
                return Double.parseDouble(racine.valeur);
            }
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
}
