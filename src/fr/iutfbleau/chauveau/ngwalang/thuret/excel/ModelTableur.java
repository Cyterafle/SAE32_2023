package fr.iutfbleau.chauveau.ngwalang.thuret.excel;
import javax.swing.*;
import java.util.*;

public class ModelTableur {
    private VueTableur vue;
    private Cellule[][] data;
    private int selectedRow, selectedColumn, lastSelectedRow, lastSelectedColumn;
    private ControllerTableur controller;

    public ModelTableur(VueTableur v){
        data = new Cellule[9][9];
        fillData();
        selectedRow = -1;
        selectedColumn = -1;
        vue = v;
    }
    /**
     * Récupère la formule associée à une cellule
     * @param row représente la ligne de la cellule
     * @param col représente la colonne de la cellule
     * @return null ou la formule demandée
     */
    public String getFormule(int row, int col){
        return data[row][col].getformule();
    }
    /**
     * Permet d'associer une formule à une cellule
     * @param row représente la ligne de la cellule
     * @param col représente la colonne de la cellule
     */
    public void setFormule(int row, int col, String formule){
        data[row][col].setformule(formule);
        data[row][col].setvaleur(row*col); //Histoire de tester le code
    }

    // Elle sera appelée par la méthode qui fait les calculs
    private void setCellValue(int row, int col, double value) {
        data[row][col].setvaleur(value);
    }

    public String getCellValue(int row, int col) {
        return Double.toString(data[row][col].getvaleur());
    }

    public void setSelectedCell(int row, int col) {
        selectedRow = row;
        selectedColumn = col;
        notifySelectionListeners();
    }

    public void setLastSelectedRow(int row){
        lastSelectedRow = row;
    }

    public void setLastSelectedColumn(int col){
        lastSelectedColumn = col;
    }

    public int getLastSelectedRow(){
        return lastSelectedRow;
    }

    public int getLastSelectedColumn(){
        return lastSelectedColumn;
    }

    public int getSelectedRow() {
        return selectedRow;
    }

    public int getSelectedColumn() {
        return selectedColumn;
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
            controller.onCellSelected(selectedRow, selectedColumn);
    }
    /**
     * Méthode interne servant à initialiser les cellules pour éviter le NullPointerException
     */
    private void fillData(){
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                data[i][j] = new Cellule();
            }
        }
    }
}
