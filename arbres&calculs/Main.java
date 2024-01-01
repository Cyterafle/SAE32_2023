public class Main {
    public static void main(String[] args) {
        ArbreBinaire arbre = new ArbreBinaire();

        arbre.inserer("+ 2.66 * 0.1 0.33");

        System.out.println("Affichage de l'arbre :");
        arbre.afficherArbre();

        System.out.println("Résultat du calcul : " + arbre.calculer());


    }
}
