package fr.iutfbleau.chauveau.ngwalang.thuret.excel;
/**
 * Enumération <code>Etat</code> provenant du diagramme de classes de Côme Thuret
 * permettant de connaître la validité ou non de la formule saisie
 */
public enum Etat {
    /**
     * Etat par défaut : la formule n'a pas été saisie
     */
    VIDE,
    /**
     * Lorsque la formule saisie est calculable
     */
    VALIDE_CALCULABLE,
    /**
     * Lorsque la formule ne peut pas être calculée
     * à cause d'un léger problème comme la mention d'une cellule
     * vide
     */
    VALIDE_INCALCULABLE,
    /**
     * Lorsque la formule saisie ne peut simplement pas être acceptée
     */
    INVALIDE,
    /**
     * Lorsque la cellule contient une reférence circulaire
     */
    REFERENCE_CIRCULAIRE;
}