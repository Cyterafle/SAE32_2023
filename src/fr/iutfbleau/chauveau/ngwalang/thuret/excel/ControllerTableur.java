package fr.iutfbleau.chauveau.ngwalang.thuret.excel;
import java.awt.*;
import java.awt.event.*;

public class ControllerTableur implements SelectionListener, ActionListener{
    protected ModelTableur model;
    private VueTableur vue;
    public ControllerTableur(){
        vue = new VueTableur();
        model = new ModelTableur(vue);
        vue.getChampFormule().addActionListener(this);
        model.addCellListener(vue.getCellTab());
        model.addSelectionListener(this);
    }

    @Override
            public void onCellSelected(int row, int col) {
                vue.getCellule(row, col).setBackground(Color.YELLOW);
                vue.getChampFormule().setText(model.getFormule(row, col));
                if (model.getLastSelectedRow() >= 0 && model.getSelectedColumn() >= 0 &&
                        (model.getLastSelectedRow() != row || model.getLastSelectedColumn() != col)) {
                    vue.getCellule(model.getLastSelectedRow(), model.getLastSelectedColumn()).setBackground(null);
                }
                model.setLastSelectedRow(row);
                model.setLastSelectedColumn(col);
            }
        
    @Override
        public void actionPerformed(ActionEvent e) {
            int ligne = model.getSelectedRow();
            int colonne = model.getSelectedColumn();
            if (ligne >= 0 && colonne >= 0) {
                String nouvelleFormule = vue.getChampFormule().getText();
                model.calcul(ligne, colonne, nouvelleFormule);
                vue.getCellule(ligne, colonne).setText(model.getCellValue(ligne, colonne));
                vue.getCellule(ligne, colonne).setBackground(null);
                vue.getChampFormule().setEnabled(false);
            }
        }
}
