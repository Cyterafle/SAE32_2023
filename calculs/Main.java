
// Classe principale pour l'exemple
public class Main {
    public static void main(String[] args) {
        // Création des constantes
        Expression constant1 = new Constante(2.66);
        Expression constant2 = new Constante(1.1);
        Expression constant3 = new Constante(0.33);

        // Création des opérations
        Expression multiplication = new Multiplication(constant2, constant3);
        Expression addition = new Addition(constant1, multiplication);

        // Évaluation de l'expression
        double result = addition.evaluate();

        // Affichage du résultat
        System.out.println("Résultat : " + result);
    }
}