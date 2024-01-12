package fr.iutfbleau.chauveau.ngwalang.thuret.excel;
public class ArbreBinaire {
    private Noeud racine;
    private ModelTableur model;
    private int index;

    /**
    * Classe <code>ArbreBinaire</code> tirée du diagramme de classe fait par Côme
    * Thuret. Elle sert à matérialiser un arbre de calcul, stocker les valeur de la formule
    * ainsi que l'insrtion de noeud et le calcul d'une formule.
    */
    public ArbreBinaire(ModelTableur mtab) {
        this.racine = null;
        this.model = mtab;
    }

    /**
     * Permet de définir l'Etat de la cellule
     * @return le noeud racine.
     * @param racine représente la racine du l'abre ou du sous arbre à créer.
     * @param expression représente la formule sous forme d'un tableau de string.
     */
    private Noeud insererNoeud(Noeud racine, String[] expression) {
        if (this.index < expression.length) {
            String valeur = expression[this.index];

            if (estOperateur(valeur)) {
                racine = new Noeud(valeur);
                this.index = this.index + 1;
                racine.gauche = insererNoeud(racine.gauche, expression);
                this.index = this.index + 1;
                racine.droit = insererNoeud(racine.droit, expression);
            } else {
                racine = new Noeud(valeur);
            }
        }

        return racine;
    }

    /**
     * Permet l'exécution de la méthode insererNoeud()
     * @param expression représente la formule sous forme d'un string.
     */
    public void inserer(String expression) {
        this.index = 0;
        String[] elements = expression.split(" ");
        racine = insererNoeud(racine, elements);
    }

    /**
     * Permet de savoir si une string donnée est un opérateur
     * @return un bouléen, true si c'est un opérateur, false si non.
     * @param valeur  représente la string à vérifier.
     */
    private boolean estOperateur(String valeur) {
        return valeur.equals("+") || valeur.equals("-") || valeur.equals("*") || valeur.equals("/");
    }

    /**
     * Permet de savoir si une string donnée est une cellule
     * @return un bouléen, true si c'est une cellule, false si non.
     * @param valeur  représente la string à vérifier.
     */
    public boolean estCellule(String valeur) {
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

    /**
     * Permet de récuperer la valeur associer à une cellule.
     * @return un double correspondant à la valeur de la cellule.
     * @param cellName  représente le nom de la cellule.
     */
    private double getCellVal(String cellName){
        char[] alpha = new char[9];
        char[] num = new char[9];
        char[] element = cellName.toCharArray();
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

    private String getCellForm(String cellName){
        char[] alpha = new char[9];
        char[] num = new char[9];
        char[] element = cellName.toCharArray();
        String formule = null;
        for (int t = 0; t < 9; t++){
            alpha[t] = (char) ('A'+ t );
            num[t] = (char) ('1'+ t);

        }

        for (int i = 0; i < 9 ; i++){
            if (element[0] == alpha[i]){
                for (int j = 0 ; j < 9 ; j++){
                    if (element[1] == num[j]){
                        formule = this.model.getFormule(j, i);
                    }
                }
            }
        }
        return formule;
    }

    /**
     * Permet de metre à jour le modèle stocké dans l'objet ArbreBinaire
     * @param model  représente le nouveau model à mettre à jour.
     */
    public void setModel(ModelTableur model) {
        this.model = model;
    }

    /**
     * Permet l'exécution de la méthode afficherArbre(Noeud racine, String espace, String direction).
     */
    public void afficherArbre() {
        afficherArbre(racine, "", "");
    }
    
    /**
     * Permet d'afficher l'arbre dans le terminal
     * @param racine représente la racine de l'arbre.
     * @param espace repésente les espaces apparent dans le terminal.
     * @param direction correspond aux différentes dirrection que peux predre l'arbre. 
     */
    private void afficherArbre(Noeud racine, String espace, String direction) {
        if (racine != null) {
            System.out.println(espace + direction + racine.valeur);
            afficherArbre(racine.gauche, espace + "│  ", "├─");
            afficherArbre(racine.droit, espace + "   ", "└─");
        }
    }

    public boolean calculableFrom(String expression){
        return calculableForm(expression);
    }

    private boolean calculableForm(String expression){
        String[] elements = expression.split(" ");
        int acc = 0;
        String formVal = null;
        for( String ele : elements){
            if (estCellule(ele)){
                formVal = getCellForm(ele);
                if ( formVal == null){
                    acc += 1;
                }
            }
            if ( acc > 0){
                return false;
            }
        }
        return true;
    }

    /**
     * Permet d'exécuter la methode calculer
     * @return un double qui correspond au résultat du calcul.
     */
    public double calculer() {
        return calculer(racine);
    }

    /**
     * Permet d'effectuer un calcul stocké dans un arbre
     * @return un double qui correspond au résultat du calcul.
     * @param racine correspond à la racine de l'arbre de calcul.
     */
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
