package fr.iutfbleau.chauveau.ngwalang.thuret.excel;
import javax.swing.*;
import java.awt.*;


public class VueTableur extends JFrame {

    private JLabel[][] cellule;
    private JTextField champFormule;

    public VueTableur(){
        super("Excel de Wish");
        setLayout(new BorderLayout());
        JPanel petitTableur = new JPanel(new GridLayout(10, 10));
        String[] colonne = {"", "A", "B", "C", "D", "E", "F", "G", "H", "I"};
        String[] ligne = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};

        cellule = new JLabel[9][9];

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
                this.cellule[i][j] = new JLabel();
                this.cellule[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                this.cellule[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                this.cellule[i][j].setOpaque(true);
                petitTableur.add(this.cellule[i][j]);
            }
        }

        this.champFormule = new JTextField();
        this.champFormule.setEnabled(false);

        add(this.champFormule, BorderLayout.NORTH);
        add(petitTableur, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(750, 600);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    public JTextField getChampFormule(){
        return this.champFormule;
    }

    public JLabel getCellule(int row, int col){
        return this.cellule[row][col];
    }

    public JLabel[][] getCellTab(){
        return this.cellule;
    }
}