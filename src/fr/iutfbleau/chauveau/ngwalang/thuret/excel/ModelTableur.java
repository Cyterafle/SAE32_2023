package fr.iutfbleau.chauveau.ngwalang.thuret.excel;
import javax.swing.*;
import java.util.*;

public class ModelTableur {
    private VueTableur vue;
    private String[][] data;
    private int selectedRow, selectedColumn, lastSelectedRow, lastSelectedColumn;
    private ControllerTableur controller;

    public ModelTableur(VueTableur v){
        data = new String[9][9];
        selectedRow = -1;
        selectedColumn = -1;
        vue = v;
    }

    public String getCellValue(int row, int col) {
        return data[row][col];
    }

    public void setCellValue(int row, int col, String value) {
        data[row][col] = value;
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

    public interface SelectionListener {
        void onCellSelected(int row, int col);
    }
}
