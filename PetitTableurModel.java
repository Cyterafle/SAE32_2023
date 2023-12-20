import java.util.ArrayList;
import java.util.List;

public class PetitTableurModel {
    private String[][] data;
    private int selectedRow;
    private int selectedColumn;
    private List<SelectionListener> selectionListeners;

    public PetitTableurModel(int rows, int cols) {
        data = new String[rows][cols];
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
