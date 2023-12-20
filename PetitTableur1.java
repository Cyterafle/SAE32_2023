import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PetitTableur1 extends JFrame {

    private JLabel[][] cellule;
    private JTextField champFormule;
    private PetitTableurModel model;
    private int lastSelectedRow = -1;
    private int lastSelectedColumn = -1;

    public PetitTableur1() {
        super("Excel de wish");

        model = new PetitTableurModel(9, 9);

        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        String[] colonne = {"", "A", "B", "C", "D", "E", "F", "G", "H", "I"};
        String[] ligne = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};

        cellule = new JLabel[9][9];

        JPanel petitTableur = new JPanel(new GridLayout(10, 10));

        for (String colonnes : colonne) {
            JLabel Colonne = new JLabel(colonnes, SwingConstants.CENTER);
            Colonne.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            petitTableur.add(Colonne);
        }

        for (int i = 0; i < 9; i++) {
            JLabel Ligne = new JLabel(ligne[i], SwingConstants.CENTER);
            Ligne.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            petitTableur.add(Ligne);

            for (int j = 0; j < 9; j++) {
                cellule[i][j] = new JLabel();
                cellule[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                cellule[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                cellule[i][j].setOpaque(true);
                cellule[i][j].addMouseListener(new EcouteurClicCellule(i, j));
                petitTableur.add(cellule[i][j]);
            }
        }

        champFormule = new JTextField();
        champFormule.addActionListener(new EcouteurActionFormule());
        champFormule.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (lastSelectedRow >= 0 && lastSelectedColumn >= 0) {
                    cellule[lastSelectedRow][lastSelectedColumn].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                }
            }
        });

        container.add(champFormule, BorderLayout.NORTH);
        container.add(petitTableur, BorderLayout.CENTER);

        model.addSelectionListener(new PetitTableurModel.SelectionListener() {
            @Override
            public void onCellSelected(int row, int col) {
                cellule[row][col].setBackground(Color.YELLOW);
                champFormule.setText(model.getCellValue(row, col));
                if (lastSelectedRow >= 0 && lastSelectedColumn >= 0 &&
                        (lastSelectedRow != row || lastSelectedColumn != col)) {
                    cellule[lastSelectedRow][lastSelectedColumn].setBackground(null);
                }
                lastSelectedRow = row;
                lastSelectedColumn = col;
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(750, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private class EcouteurClicCellule extends MouseAdapter {
        private int ligne;
        private int colonne;

        public EcouteurClicCellule(int ligne, int colonne) {
            this.ligne = ligne;
            this.colonne = colonne;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            model.setSelectedCell(ligne, colonne);
        }
    }

    private class EcouteurActionFormule implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int ligne = model.getSelectedRow();
            int colonne = model.getSelectedColumn();
            if (ligne >= 0 && colonne >= 0) {
                model.setCellValue(ligne, colonne, champFormule.getText());
                cellule[ligne][colonne].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }
        }
    }

    public static void main(String[] args) {
        new PetitTableur1();
    }
}
