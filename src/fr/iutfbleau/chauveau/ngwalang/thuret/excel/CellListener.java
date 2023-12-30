package fr.iutfbleau.chauveau.ngwalang.thuret.excel;
import java.awt.event.*;

public class CellListener extends MouseAdapter {
        private int ligne;
        private int colonne;
        private static ModelTableur model;
        private static VueTableur vue;

        public CellListener(int ligne, int colonne) {
            this.ligne = ligne;
            this.colonne = colonne;
        }

        public static void setModel(ModelTableur m){
            model = m;
        }

        public static void setVue(VueTableur v){
            vue = v;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            model.setSelectedCell(ligne, colonne);
            vue.getChampFormule().setEnabled(true);
        }
}
