import java.util.*;

public class ModelTableur {
    private String[][] data;
    private int selectedRow, selectedColumn, lastSelectedRow, lastSelectedColumn;
    private List<SelectionListener> selectionListeners;

    public ModelTableur(){
        data = new String[9][9];
        selectedRow = -1;
        selectedColumn = -1;
        selectionListeners = new ArrayList<>();
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

    public void addSelectionListener(SelectionListener listener) {
        selectionListeners.add(listener);
    }

    private void notifySelectionListeners() {
        for (SelectionListener listener : selectionListeners) {
            listener.onCellSelected(selectedRow, selectedColumn);
        }
    }

    public interface SelectionListener {
        void onCellSelected(int row, int col);
    }
}
