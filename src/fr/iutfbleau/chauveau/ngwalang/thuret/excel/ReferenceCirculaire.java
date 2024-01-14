package fr.iutfbleau.chauveau.ngwalang.thuret.excel;
import java.util.*;
/**
 * La classe <code>Reference Circulaire</code> permettant d'éviter comme son nom l'indique
 * les reférences circulaires dans le tableur, elle surveille tout ce qui tourne autour
 * @author Arnaud NGWALA-NGWALA
 * @version 1.0
 */
public class ReferenceCirculaire {
   private Deque<String> referencesParcourues, referencesAParcourir;
   /**
    * Constructeur pour instancier la classe
    */
   public ReferenceCirculaire(){}
   /**
    * Classe appelée pour dire si la formule juste saisie initie une reférence cirulaire
    * @param row la ligne de la cellule dont on vérifie la formule
    * @param col la colonne de la cellule dont on vérifie la formule
    * @param data un tableau contenant l'ensemble des cellules
    * @return true s'il y a conflit, false sinon
    */
   public boolean isLoop(int row, int col, Cellule[][] data){
        referencesAParcourir = new ArrayDeque<>();
        referencesParcourues = new ArrayDeque<>();
        char r = 'A';
        r += col;
        String cellule = String.valueOf(r)+Integer.toString(row+1);
        referencesAParcourir.add(cellule);
        while (! referencesAParcourir.isEmpty()){
            String c = referencesAParcourir.pop();
            parcoursArbre(data[getRowNumber(c.charAt(1))][getColNumber(c.charAt(0))].getArbre().getRacine());
            if (referencesParcourues.contains(c)){
                return true;
            }
            referencesParcourues.add(c);
        }
        return false;
   }
   /**
    * Parcours l'arbre à la recherche de reférence de cellules
    * @param n un noeud de l'arbre pour pouvoir parcourir ses enfants
    */
   private void parcoursArbre(Noeud n){
    try {
        if (n.getGauche() !=  null){
            parcoursArbre(n.getGauche());
        }
        if (n.getDroit() != null){
            parcoursArbre(n.getDroit());
        }
        if (estCellule(n.toString())){
            referencesAParcourir.add(n.toString());
        }
    } catch (NullPointerException e) {
        System.err.println("Noeud vide");
    }
   }
   /**
    * Fork de la méthode de Côme pour vérifier si le Noeud est ou non une cellule
    * @param valeur la valeur du noeud dont on veut vérifier la présence d'une opérande type A1, B2...
    * @return true s'il y a cellule - false sinon
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

   /**
    * Permet de recupérer la pile correspondant aux cellules parcourues lors de la recherche d'une réference circulaire
    * @return la pile des cellules parcourues
    */
   public Deque<String> getLoopList(){
    return referencesParcourues;
   }
}