package fr.iutfbleau.chauveau.ngwalang.thuret.excel;
import java.util.*;

public class ReferenceCirculaire {
   private List<String> referencesParcourues, referencesAParcourir;
   public ReferenceCirculaire(){
        referencesAParcourir = new ArrayList<>();
        referencesParcourues = new ArrayList<>();
   }
   public boolean isLoop(int row, int col, Cellule[][] data){
        String letter = "A";
        String cellule = String.valueOf(letter.charAt(0) + row)+Integer.toString(col-1);
        System.out.println(cellule);
        referencesAParcourir.add(cellule);
        for (String c : referencesAParcourir){
            System.out.println("Row = " + getRowNumber(c.charAt(0)) + "Col = " + getColNumber(c.charAt(1)));
            parcoursArbre(data[getRowNumber(c.charAt(0))][getColNumber(c.charAt(1))].getArbre().getRacine());
        }
        return true;
   }
   private Noeud parcoursArbre(Noeud n){
        if (n.gauche == null && n.droit ==  null){
            return n;
        }
        else {
            if (n.gauche != null){
                System.out.println(n.toString());
                parcoursArbre(n.gauche);
            }
            if (n.droit != null){
                System.out.println(n.toString());
                parcoursArbre(n.gauche);
            }
        }
        return null;
   }

   private int getColNumber(char s){
    return s - 1;
   }

   private int getRowNumber(char s){
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