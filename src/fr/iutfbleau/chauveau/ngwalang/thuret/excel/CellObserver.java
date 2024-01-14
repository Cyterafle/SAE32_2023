package fr.iutfbleau.chauveau.ngwalang.thuret.excel;


/**
 * Interface <code>CellObserver</code> permettant d'observer les changements de valeur d'une cellule
 */
public interface CellObserver {
    /**
     * Méthode appelée en cas de mise à jour
     */
    public void update();
}
