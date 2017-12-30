package system.models;

import system.enums.CellState;
import system.enums.CellType;
import system.enums.Direction;
import system.utils.ImageUtils;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Parking {
    private final int size;
    private final ParkingCell[][] cells;
    private String[] vehicules = {
            "/images/buses.png",
            "/images/by1.png.png",
            "/images/by2.png",
            "/images/by3.png",
            "/images/by4.png",
            "/images/car1.png",
            "/images/car2.png",
            "/images/car3.png",
            "/images/car4.png",
            "/images/other1.png",
            "/images/other2.png",
            "/images/other3.png",
            "/images/other5.png",
            "/images/truck1.png",
            "/images/truck2.png",
            "/images/truck3.png",
            "/images/truck4.png",
    };

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
        ImageIcon yourImage = new javax.swing.ImageIcon(getClass().getResource(getRandomVehicle()));
        ImageIcon scaledImg = new javax.swing.ImageIcon(yourImage.getImage().getScaledInstance(cells[x - 1][y - 1].getCellJPanel().getWidth(), cells[x - 1][y - 1].getCellJPanel().getHeight(), Image.SCALE_FAST));
        jLabel15.setIcon(ImageUtils.rotateImg(scaledImg, Direction.DOWN)); // NOI18N
        jLabel15.setIconTextGap(1);
        //  jLabel15.setMinimumSize(new java.awt.Dimension(50, 50));
        //jLabel15.setPreferredSize(new java.awt.Dimension(50, 50));
        javax.swing.GroupLayout testLayout = new javax.swing.GroupLayout(cells[x - 1][y - 1].getCellJPanel());
        cells[x - 1][y - 1].getCellJPanel().setLayout(testLayout);
        cells[x - 1][y - 1].setState(CellState.OCCUPEE);
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
        cells[x - 1][y - 1].setState(CellState.LIBRE);

        cells[x - 1][y - 1].getCellJPanel().removeAll();
        cells[x - 1][y - 1].getCellJPanel().repaint();
        cells[x - 1][y - 1].getCellJPanel().updateUI();
        if (cells[x - 1][y - 1].getType() == CellType.ROAD)
            cells[x - 1][y - 1].getCellJPanel().setBackground(Color.decode("#303030"));
        else
            cells[x - 1][y - 1].getCellJPanel().setBackground(Color.gray);
    }

//    public ParkingCell[] findPath(ParkingCell parkingCell){
//
//        for (int i = size; i > 0; i--) {
//            for (int j = size; j < 0; j--) {
//                if(cells[i][j].getState()== CellState.OCCUPEE && cells[i][j].getType()!=CellType.ROAD){
//
//                }
//            }
//        }
//        return null;
//    }


    public ParkingCell findFreePlace() {
        for (int i = 0; i <= size - 1; i++) {
            for (int j = 0; j <= size - 1; j++) {
                System.out.println("cells[" + i + "][" + j + "]= " + cells[i][j].toString());
                if (cells[i][j].getType() == CellType.PARK && cells[i][j].getState() != CellState.OCCUPEE)
                    return cells[i][j];
            }
        }
        System.out.println("Parking.findFreePlace");
        return null;
    }

    public String getRandomVehicle() {
        int i = new Random().nextInt(vehicules.length);
        return vehicules[i];
    }
}
