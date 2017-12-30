package system.models;

import system.enums.CellType;

import javax.swing.*;
import java.awt.*;

public class Parking {
    private final int size;
    private final ParkingCell[][] cells;

    public Parking(int size) {
        this.size = size;
        this.cells = new ParkingCell[size][size];
    }

    public void addCellToParking(JPanel pan, int row, int column, CellType type) {
        ParkingCell parkingCell = new ParkingCell();
        parkingCell.setCellJPanel(pan);
        parkingCell.setColumn(column);
        parkingCell.setRow(row);
        parkingCell.setType(type);
        cells[row - 1][column - 1] = parkingCell;
    }

    public void setCar(int x, int y) {
        JLabel jLabel15 = new JLabel();
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        ImageIcon yourImage = new javax.swing.ImageIcon(getClass().getResource("/images/automobile.png"));
        ImageIcon scaledImg = new javax.swing.ImageIcon( yourImage.getImage().getScaledInstance(cells[x - 1][y - 1].getCellJPanel().getWidth(),cells[x - 1][y - 1].getCellJPanel().getHeight(), Image.SCALE_FAST));
        jLabel15.setIcon(scaledImg); // NOI18N
        jLabel15.setIconTextGap(1);
        //  jLabel15.setMinimumSize(new java.awt.Dimension(50, 50));
        //jLabel15.setPreferredSize(new java.awt.Dimension(50, 50));

        javax.swing.GroupLayout testLayout = new javax.swing.GroupLayout(cells[x - 1][y - 1].getCellJPanel());
        cells[x - 1][y - 1].getCellJPanel().setLayout(testLayout);
        testLayout.setHorizontalGroup(
                testLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, cells[x - 1][y - 1].getCellJPanel().getHeight(), javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        testLayout.setVerticalGroup(
                testLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, cells[x - 1][y - 1].getCellJPanel().getHeight(), javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }

    public void setDefault(int x, int y) {
        cells[x - 1][y - 1].getCellJPanel().removeAll();
        if (cells[x - 1][y - 1].getType() == CellType.ROAD)
            cells[x - 1][y - 1].getCellJPanel().setBackground(Color.decode("#303030"));
        else
            cells[x - 1][y - 1].getCellJPanel().setBackground(Color.gray);
    }
}
