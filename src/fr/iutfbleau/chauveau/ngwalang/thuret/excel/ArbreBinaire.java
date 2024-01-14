package fr.iutfbleau.chauveau.ngwalang.thuret.excel;

/**
    * Classe <code>ArbreBinaire</code> tirée du diagramme de classe fait par Côme
    * Thuret. Elle sert à matérialiser un arbre de calcul, stocker les valeur de la formule
    * ainsi que l'insrtion de noeud et le calcul d'une formule.
    */
public class ArbreBinaire {
    private Noeud racine;
    private ModelTableur model;
    private int index;
    
    /**
     * Méthode d'instanciation 
     * @param mtab le modèle du tableur
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
        if (expression.length == 1){
            String valeur = expression[this.index];
            racine = new Noeud(valeur);
        }
        else if (this.index < expression.length) {
            String valeur = expression[this.index];

            racine = new Noeud(valeur);
            this.index = this.index + 1;
            racine.setGauche(insererNoeud(racine.getGauche(), expression));
            this.index = this.index + 1;
            racine.setDroit(insererNoeud(racine.getDroit(), expression));
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
    /**
     * Véeifie si le String donné en argument est un nombre
     * @param valeur le String à vérifier
     * @return true si oui - non autrement
     */
    private boolean estValeur(String valeur){
        try{
            Double.parseDouble(valeur);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }
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

    /**
     * Permet à récupérer la formule d'une cellule
     * @param cellName la cellule dont on veut la formule
     * @return la formule fraîchement récupérée
     */
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
            System.out.println(espace + direction + racine.toString());
            afficherArbre(racine.getGauche(), espace + "│  ", "├─");
            afficherArbre(racine.getDroit(), espace + "   ", "└─");
        }
    }

    /**
     * Permet de vérifier la véracité d'une cellule
     * @return true si oui - false sinon
     */
    public boolean estCorrectForm(){
        return estCorrectForm(racine);
    }

    /**
     * Permet de vérifier la véracité d'une cellule
     * @param racine le noeud dont on vérifie les informations
     * @return true si oui - false sinon
     */
    private boolean estCorrectForm(Noeud racine){

        if (racine == null){
            return false;
        }


        if (racine.getGauche() == null && racine.getDroit() == null){
            if (!estValeur(racine.toString()) && !estCellule(racine.toString())){
                return false;
            }
            if (estOperateur(racine.toString())){
                return false;
            }
        }
        else if (racine.getGauche() != null && racine.getDroit() != null){
            if ( !estOperateur(racine.toString())){
                return false;
            }
        }
        else{
            if (!estCorrectForm(racine.getGauche()) || !estCorrectForm(racine.getDroit())){
                return false;
            }
        }

        return true;
    }

    /**
     * Permet de vérifier si l'arbre est calculable
     * @param expression l'expression dont découle l'arbre
     * @return true si oui - false sinon
     */
    public boolean calculableForm(String expression){
        String[] elements = expression.split(" ");
        int acc = 0;
        String formVal = null;
        if (elements.length > 1){
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
                if ( !estCellule(ele) && !estOperateur(ele) && !estValeur(ele)){
                    return false;
                }
            }
            return true;
        }
        if (elements.length == 1){
            if (!estValeur(elements[0]) && ! estCellule(elements[0])){
                return false;
            }
        }
        return true;
        
        
    }

    /*public String getFormuleValidationMessage(String expression) {
        if (calculableForm(expression)) {
            return "Formule correcte mais incalculable";
        } else {
            return "Formule incorrecte";
        }
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
            return 1.0;
        }

        System.out.println(estCellule(racine.toString()) + " cellule");
        System.out.println(racine.toString());

    
        try {
            if (!estOperateur(racine.toString())) {
                if (estCellule(racine.toString())) {
                    System.out.print(getCellVal(racine.toString()));
                    return getCellVal(racine.toString());
                } 
                else if(estValeur(racine.toString())){
                    return Double.parseDouble(racine.toString());
                }
                else if(!estCellule(racine.toString()) || !estValeur(racine.toString())){
                    throw new IllegalArgumentException("Valeur inconnue : " + racine.toString()); 
                }
            }
    
            double gauche = calculer(racine.getGauche());
            double droit = calculer(racine.getDroit());
    
            switch (racine.toString()) {
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
                        System.err.println("Erreur : Division par zéro");
                        return 2.0;  // Vous pouvez également choisir de retourner Double.NaN pour indiquer une division par zéro
                    }
                default:
                    System.err.println("Erreur : Opérateur non pris en charge : " + racine.toString());
                    return 3.0;
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Erreur : " + e.getMessage());
            return 4.0;
        }
    }
    /**
     * Getter pour renvoyer la racine de l'arbre
     * @return la racine de l'arbre
     */
    public Noeud getRacine(){
        return racine;
    }

    public void parcoursArbre(Cellule c, boolean remove){
        parcoursArbre(racine, c, remove);
    }
    /**
     * Fonction pour parcourir un arbre à la recherche de cellules à observer
     * @param n le noeud dont il faut observer les enfants
     * @param c la cellule observatrice
     * @param remove booléen qui définit comment parler à l'observer : True j'enlève ma cellule de la liste, False je l'ajoute
     */
    private void parcoursArbre(Noeud n, Cellule c, boolean remove){
        try {
            if (n.getGauche() !=  null){
                parcoursArbre(n.getGauche(), c, remove);
            }
            if (n.getDroit() != null){
                parcoursArbre(n.getDroit(), c, remove);
            }
            if (estCellule(n.toString())){
                if (remove)
                    model.getData()[getRowNumber(n.toString().charAt(1))][getColNumber(n.toString().charAt(0))].removeCellObserver(c);
                else
                    model.getData()[getRowNumber(n.toString().charAt(1))][getColNumber(n.toString().charAt(0))].addCellObserver(c);
            }
        } catch (NullPointerException e) {
                System.out.println("No values");
        }
    }

    /**
     * A partir de la syntaxe cellule (A1, B2...) permet de retrouver la ligne
     * associée à cette dernière 
     * @param s le dernier caractère de la cellule
     * @return le numéro de ligne
     */
        public int getRowNumber(char s){
            return s - 49;
       }
    
       /**
         * A partir de la syntaxe cellule (A1, B2...) permet de retrouver la colonne
         * associée à cette dernière 
         * @param s le dernier caractère de la cellule
         * @return le numéro de ligne
         */
       public int getColNumber(char s){
        char j = 'A';
        for (int i = 0; i < 9; i++){
            if (j == s){
                return i;
            }
            j++;
        }
        return -1;
       }
}
