import java.awt.*;
import java.awt.event.*;

public class ControllerTableur implements SelectionListener, ActionListener{
    private ModelTableur model;
    private VueTableur vue;
    public ControllerTableur(){
        model = new ModelTableur();
        vue = new VueTableur();
        vue.getChampFormule().addActionListener(this);
        CellListener.setModel(model);
        CellListener.setVue(vue);
    }

    @Override
            public void onCellSelected(int row, int col) {
                vue.getCellule(row, col).setBackground(Color.YELLOW);
                vue.getChampFormule().setText(model.getCellValue(row, col));
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
                model.setCellValue(ligne, colonne, nouvelleFormule);
                vue.getCellule(ligne, colonne).setText(nouvelleFormule);
                vue.getCellule(ligne, colonne).setBackground(null);
                vue.getChampFormule().setEnabled(false);
            }
        }

    public interface SelectionListener {
        void onCellSelected(int row, int col);
    }
}