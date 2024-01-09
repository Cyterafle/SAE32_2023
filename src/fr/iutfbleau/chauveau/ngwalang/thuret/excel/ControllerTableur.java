package fr.iutfbleau.chauveau.ngwalang.thuret.excel;
import java.awt.*;
import java.awt.event.*;

public class ControllerTableur implements SelectionListener, ActionListener{
    private ModelTableur model;
    private VueTableur vue;
    public ControllerTableur(){
        this.vue = new VueTableur();
        this.model = new ModelTableur(vue);
        this.vue.getChampFormule().addActionListener(this);
        this.model.addCellListener(vue.getCellTab());
        this.model.addSelectionListener(this);
    }

    @Override
            public void onCellSelected(int row, int col) {
                this.vue.getCellule(row, col).setBackground(Color.YELLOW);
                this.vue.getChampFormule().setText(this.model.getFormule(row, col));
                if (this.model.getLastSelectedRow() >= 0 && this.model.getSelectedColumn() >= 0 &&
                        (this.model.getLastSelectedRow() != row || this.model.getLastSelectedColumn() != col)) {
                    this.vue.getCellule(model.getLastSelectedRow(), this.model.getLastSelectedColumn()).setBackground(null);
                }
                this.model.setLastSelectedRow(row);
                this.model.setLastSelectedColumn(col);
            }
        
    @Override
        public void actionPerformed(ActionEvent e) {
            int ligne = this.model.getSelectedRow();
            int colonne = this.model.getSelectedColumn();
            if (ligne >= 0 && colonne >= 0) {
                String nouvelleFormule = this.vue.getChampFormule().getText();
                this.model.calcul(ligne, colonne, nouvelleFormule);
                this.vue.getCellule(ligne, colonne).setText(this.model.getCellValue(ligne, colonne));
                this.vue.getCellule(ligne, colonne).setBackground(null);
                this.vue.getChampFormule().setEnabled(false);
            }
        }
}
