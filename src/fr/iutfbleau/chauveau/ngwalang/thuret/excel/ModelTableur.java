package fr.iutfbleau.chauveau.ngwalang.thuret.excel;
import javax.swing.*;

public class ModelTableur {
    private VueTableur vue;
    private Cellule[][] data;
    private int selectedRow, selectedColumn, lastSelectedRow, lastSelectedColumn;
    private ControllerTableur controller;
    private ArbreBinaire arbre;

    public ModelTableur(VueTableur v){
        this.data = new Cellule[9][9];
        this.arbre = new ArbreBinaire(this);
        fillData();
        this.selectedRow = -1;
        this.selectedColumn = -1;
        this.vue = v;
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
        System.out.println(formule);
        System.out.println(formule.length());
        this.arbre.inserer(formule);
        setCellValue(row, col, arbre.calculer());
        this.arbre.setModel(this);
        this.arbre.afficherArbre();
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

    public String getCellValue(int row, int col) {
        return Double.toString(this.data[row][col].getvaleur());
    }

    public void setSelectedCell(int row, int col) {
        this.selectedRow = row;
        this.selectedColumn = col;
        notifySelectionListeners();
    }

    public void setLastSelectedRow(int row){
        this.lastSelectedRow = row;
    }

    public void setLastSelectedColumn(int col){
        this.lastSelectedColumn = col;
    }

    public int getLastSelectedRow(){
        return this.lastSelectedRow;
    }

    public int getLastSelectedColumn(){
        return this.lastSelectedColumn;
    }

    public int getSelectedRow() {
        return this.selectedRow;
    }

    public int getSelectedColumn() {
        return this.selectedColumn;
    }

    public void addSelectionListener(ControllerTableur listener) {
        controller = listener;
    }

    public void addCellListener(JLabel[][] cells){
        CellListener.setModel(this);
        CellListener.setVue(vue);
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                cells[i][j].addMouseListener(new CellListener(i, j));
            }
        }
    }

    private void notifySelectionListeners() {
        this.controller.onCellSelected(selectedRow, selectedColumn);
    }
    /**
     * Méthode interne servant à initialiser les cellules pour éviter le NullPointerException
     */
    private void fillData(){
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                this.data[i][j] = new Cellule();
            }
        }
    }
}
