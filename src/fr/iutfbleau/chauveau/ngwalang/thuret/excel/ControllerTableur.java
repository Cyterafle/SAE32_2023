package fr.iutfbleau.chauveau.ngwalang.thuret.excel;
import java.awt.*;
import java.awt.event.*;

import javax.swing.JOptionPane;

public class ControllerTableur implements SelectionListener, ActionListener{
    private ModelTableur model;
    private VueTableur vue;

    /**
     * Constructeur de la classe ControllerTableur.
     * Initialise la vue et le modèle du tableur, ajoute les écouteurs nécessaires, et établit les liaisons entre eux.
     */
    public ControllerTableur(){
        this.vue = new VueTableur();
        this.model = new ModelTableur(vue);
        this.vue.getChampFormule().addActionListener(this);
        this.model.addCellListener(vue.getCellTab());
        this.model.addSelectionListener(this);
    }

    /**
     * Méthode invoquée lorsqu'une cellule est sélectionnée.
     * Met en surbrillance la cellule sélectionnée en jaune, affiche la formule dans le champ de formule,
     * et désélectionne la cellule précédemment sélectionnée.
     * @param row L'indice de la ligne de la cellule sélectionnée.
     * @param col L'indice de la colonne de la cellule sélectionnée.
     */
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
        
    /**
     * Méthode invoquée lorsqu'une action est effectuée, en particulier lorsque le champ de formule est modifié.
     * Met à jour la formule de la cellule sélectionnée dans le modèle, effectue le calcul, met à jour la valeur affichée
     * dans la cellule, et désactive le champ de formule.
     * @param e L'événement d'action déclenché, généralement associé au champ de formule.
     */
    @Override
        public void actionPerformed(ActionEvent e) {
                int ligne = this.model.getSelectedRow();
                int colonne = this.model.getSelectedColumn();
                if (! this.vue.getChampFormule().getText().equals("")) {
                    if (ligne >= 0 && colonne >= 0) {
                        String nouvelleFormule = this.vue.getChampFormule().getText();
                        this.model.calcul(ligne, colonne, nouvelleFormule);
                        model.updateView();
                        this.vue.getCellule(ligne, colonne).setBackground(null);
                        this.vue.getChampFormule().setEnabled(false);
                    }
                } else {
                    JOptionPane.showMessageDialog(vue,"La formule ne peut pas être vide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
        }
    }
