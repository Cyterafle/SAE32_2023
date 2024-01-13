package fr.iutfbleau.chauveau.ngwalang.thuret.excel;
import java.awt.event.*;

public class CellListener extends MouseAdapter {
        private int ligne;
        private int colonne;
        private static ModelTableur model;
        private static VueTableur vue;

        /**
        * Classe <code>CellListener</code>
        * Elle sert la création d'un objet de type CellListener et stocke les coordonnés d'une cellule.
        * @param ligne , int représentant la ligne sur laquel est la cellule.
        * @param colonne , int représentant la colonne sur laquel est la cellule. 
        */
        public CellListener(int ligne, int colonne) {
            this.ligne = ligne;
            this.colonne = colonne;
        }

        /**
        * Permet de mettre à jour les données du modèle dans la cellule.
        * @param m , objet de type ModelTableur représentant la version du modèle à jour.
        */
        public static void setModel(ModelTableur m){
            model = m;
        }

        /**
        * Permet de mettre à jour les données de la vue dans la cellule.
        * @param v , objet de type VueTableur représentant la version de la vue à jour.
        */
        public static void setVue(VueTableur v){
            vue = v;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            model.setSelectedCell(ligne, colonne);
            vue.getChampFormule().setEnabled(true);
        }
}
