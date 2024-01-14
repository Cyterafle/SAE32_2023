package fr.iutfbleau.chauveau.ngwalang.thuret.excel;
import javax.swing.*;

public class ModelTableur {
    private VueTableur vue;
    private Cellule[][] data;
    private int selectedRow, selectedColumn, lastSelectedRow, lastSelectedColumn;
    private ControllerTableur controller;
    private ReferenceCirculaire rc;

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
        this.data[row][col].getArbre().inserer(formule);
        if (! rc.isLoop(row, col, data)){
            data[row][col].setetat(Etat.VIDE);    
            System.out.println(this.data[row][col].getArbre().calculableFrom(formule) + " model");    
            if(this.data[row][col].getArbre().calculableFrom(formule)){
                setCellValue(row, col, this.data[row][col].getArbre().calculer());
                this.data[row][col].getArbre().setModel(this);
            }
            else if (this.data[row][col].getArbre().calculableFrom(formule) ==  false){
                this.data[row][col].getArbre().calculer();
                this.data[row][col].getArbre().setModel(this);
            }
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
        this.controller = listener;
    }

    public Cellule[][] getData(){
        return data;
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
                this.data[i][j] = new Cellule(this);
            }
        }
    }

    public void updateView(){
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                if (data[i][j].getetat() == Etat.REFERENCE_CIRCULAIRE)
                    vue.getCellule(i, j).setText("ERREUR");
                else if (data[i][j].getetat() != Etat.VIDE)
                    vue.getCellule(i, j).setText(getCellValue(i, j));
            }
        }
    }

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
