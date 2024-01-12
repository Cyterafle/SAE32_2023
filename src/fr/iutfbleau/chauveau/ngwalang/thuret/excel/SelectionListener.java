package fr.iutfbleau.chauveau.ngwalang.thuret.excel;
public interface SelectionListener {
    /**
     * Méthode appelée lorsqu'une cellule est sélectionnée.
     *
     * @param row L'indice de la ligne de la cellule sélectionnée.
     * @param col L'indice de la colonne de la cellule sélectionnée.
     */
        void onCellSelected(int row, int col);
    }
