package fr.iutfbleau.chauveau.ngwalang.thuret.excel;
import java.awt.Color;

import javax.swing.*;

/**
 * Classe <code>ModelTableur</code> qui gère les données, leur insertion, leur modification ainsi que certaines mises à jour de la vue
 */
public class ModelTableur {
    private VueTableur vue;
    private Cellule[][] data;
    private int selectedRow, selectedColumn, lastSelectedRow, lastSelectedColumn;
    private ControllerTableur controller;
    private ReferenceCirculaire rc;

    /**
     * Constructeur d'un nouveau modèle
     * @param v la vue associée
     */
    public ModelTableur(VueTableur v){
        this.data = new Cellule[9][9];
        fillData();
        this.selectedRow = -1;
        this.selectedColumn = -1;
        this.vue = v;
        rc = new ReferenceCirculaire();
    }
    /**
     * Récupère la formule associée à une cellule
     * @param row représente la ligne de la cellule
     * @param col représente la colonne de la cellule
     * @return null ou la formule demandée
     */
    public String getFormule(int row, int col){
        return this.data[row][col].getformule();
    }
    /**
     * Permet d'associer une formule à une cellule
     * @param row représente la ligne de la cellule
     * @param col représente la colonne de la cellule
     */
    private void setFormule(int row, int col, String formule){
        this.data[row][col].setformule(formule);
    }

    /**
     * Permet de calculer la valeur à placer dans une cellule à partir de sa formule
     * @param row représente la ligne de la cellule concernée
     * @param col représente la colonne de la cellule concernée
     * @param formule représente la formule à partir de laquelle faire le calcul
     */
    public void calcul(int row, int col, String formule){
        setFormule(row, col, formule);
        this.data[row][col].getArbre().parcoursArbre(this.data[row][col], true);
        this.data[row][col].getArbre().inserer(formule);
        if (! rc.isLoop(row, col, data)){
            data[row][col].setetat(Etat.VIDE);
            if(this.data[row][col].getArbre().estCorrectForm(formule)){
                if(this.data[row][col].getArbre().calculableForm(formule)){
                    this.data[row][col].getArbre().parcoursArbre(this.data[row][col], false);
                    setCellValue(row, col, this.data[row][col].getArbre().calculer());
                    this.data[row][col].getArbre().setModel(this);
                    data[row][col].setetat(Etat.VALIDE_CALCULABLE);
                }
                else {
                    data[row][col].setetat(Etat.VALIDE_INCALCULABLE);
                    this.data[row][col].getArbre().setModel(this);
                }
            }
            else {
                data[row][col].setetat(Etat.INVALIDE);
                this.data[row][col].getArbre().setModel(this);
            }
            updateView();
            data[row][col].notifyCellObservers();
        }
        else {
           ErrState(); 
        }
    }

    /**
     * Permet d'associer à une cellule une valeur en fonction de sa formule
     * @param row représente la ligne de la cellule concernée
     * @param col représente la colonne de la cellule concernée
     * @param value représente la nouvelle valeur de la cellule
     */
    private void setCellValue(int row, int col, double value) {
        this.data[row][col].setvaleur(value);
    }

    /**
     * Récupère la valeur d'une cellule
     * @param row la ligne de la cellule concernée
     * @param col la colonne de la cellule concernée
     * @return la valeur parsée en String
     */
    public String getCellValue(int row, int col) {
        return Double.toString(this.data[row][col].getvaleur());
    }

    public Etat getCellState(int row, int col) {
        return this.data[row][col].getetat();
    }

    /**
     * Permet de définir la cellule sélectionnée
     * @param row la ligne de la cellule concernée
     * @param col la colonne de la cellule concernée
     */
    public void setSelectedCell(int row, int col) {
        this.selectedRow = row;
        this.selectedColumn = col;
        notifySelectionListeners();
    }

    /**
     * Permet de définir la ligne de la dernière cellule séléctionnée
     * @param row la ligne
     */
    public void setLastSelectedRow(int row){
        this.lastSelectedRow = row;
    }

    /**
     * Permet de définir la colonne de la dernière cellule séléctionnée
     * @param row la colonne
     */
    public void setLastSelectedColumn(int col){
        this.lastSelectedColumn = col;
    }

    /**
     * Permet de récupérer la ligne de la dernière cellule séléctionnée
     * @return la ligne
     */
    public int getLastSelectedRow(){
        return this.lastSelectedRow;
    }

    /**
     * Permet de récupérer la colonne de la dernière cellule séléctionnée
     * @return la colonne
     */
    public int getLastSelectedColumn(){
        return this.lastSelectedColumn;
    }

    /**
     * Permet de récupérer la ligne de la cellule séléctionnée actuellement
     * @return la ligne
     */
    public int getSelectedRow() {
        return this.selectedRow;
    }

    /**
     * Permet de récupérer la ligne de la colonne séléctionnée actuellement
     * @return la colonne
     */
    public int getSelectedColumn() {
        return this.selectedColumn;
    }

    /**
     * Ajoute un listener à selectionListener
     * @param listener le contrôleur à ajouter en tant que listener
     */
    public void addSelectionListener(ControllerTableur listener) {
        this.controller = listener;
    }

    /**
     * Renvoie le tableau de Cellule 
     * @return le tableau
     */
    public Cellule[][] getData(){
        return data;
    }

    /**
     * Permet d'ajouter un CellListener aux cellules pour détecter plus tard les clics
     * @param cells le tableau correspondant aux cellules de la vue
     */
    public void addCellListener(JLabel[][] cells){
        CellListener.setModel(this);
        CellListener.setVue(vue);
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                cells[i][j].addMouseListener(new CellListener(i, j));
            }
        }
    }

    /**
     * Prévient le selectionListener d'un changement d'Etat
     */
    private void notifySelectionListeners() {
        this.controller.onCellSelected(selectedRow, selectedColumn);
    }

    /**
     * Méthode interne servant à initialiser les cellules pour éviter le NullPointerException
     */
    private void fillData(){
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                this.data[i][j] = new Cellule(this);
            }
        }
    }

    /**
     * Permet d'actualiser la vue après une modification
     */
    public void updateView(){
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                if (data[i][j].getetat() == Etat.REFERENCE_CIRCULAIRE)
                    vue.getCellule(i, j).setText("ERREUR");
                else if (data[i][j].getetat() == Etat.VALIDE_CALCULABLE)
                    vue.getCellule(i, j).setText(getCellValue(i, j));
                else
                    updateVisualState(i, j);
            }
        }
    }

    public void updateVisualState(int row, int col){
        if (data[row][col].getetat() == Etat.VALIDE_INCALCULABLE){
            vue.getCellule(row, col).setBackground(Color.ORANGE);
        }
        else if (data[row][col].getetat() == Etat.INVALIDE)
            vue.getCellule(row, col).setBackground(Color.RED);
        else
            vue.getCellule(row, col).setBackground(null);
        vue.revalidate();
    }

    /**
     * Permet de mettre à jour les cellules ayant une référence circulaire
     */
    private void ErrState(){
        for (String cellule : rc.getLoopList()){
            data[rc.getRowNumber(cellule.charAt(1))][rc.getColNumber(cellule.charAt(0))].setetat(Etat.REFERENCE_CIRCULAIRE);
            vue.getCellule(rc.getRowNumber(cellule.charAt(1)), rc.getColNumber(cellule.charAt(0))).setText("ERREUR");
        }
    }
    /*public boolean isFormuleValide(int row, int col) {
        String formule = this.data[row][col].getformule();
        return this.data[row][col].getArbre().calculableFrom(formule);
    }
    
    public String getFormuleValidationMessage(int row, int col) {
        String formule = this.data[row][col].getformule();
        return this.data[row][col].getArbre().getFormuleValidationMessage(formule);
    }*/
}
