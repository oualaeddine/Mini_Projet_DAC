package system.models;

import system.enums.CellState;
import system.enums.CellType;
import system.enums.ClientType;
import ui.GraphicCar;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class Parking {
    private final int size;
    private final ParkingCell[][] cells;
    private ParkingCell sortie;

    public Parking(int size) {
        this.size = size;
        this.cells = new ParkingCell[size][size];
        sortie = new ParkingCell();
        sortie.setRow(size);
        sortie.setColumn(size);
    }

    public void addCellToParking(JPanel pan, int row, int column, CellType type) {
        ParkingCell parkingCell = new ParkingCell();
        parkingCell.setCellJPanel(pan);
        parkingCell.setColumn(column);
        parkingCell.setRow(row);
        parkingCell.setType(type);
        cells[row - 1][column - 1] = parkingCell;
    }


    private void setCar(GraphicCar car) {
        int x = car.getPosition().getRow();
        int y = car.getPosition().getColumn();
        int cellHeight = cells[x - 1][y - 1].getCellJPanel().getHeight();
        int cellWidth = cells[x - 1][y - 1].getCellJPanel().getHeight();
        if (x == size && y == size) return;
        GroupLayout testLayout = new GroupLayout(cells[x - 1][y - 1].getCellJPanel());
        cells[x - 1][y - 1].getCellJPanel().setLayout(testLayout);
//        cells[x - 1][y - 1].setState(CellState.OCCUPEE);
        car.setupIconSize(cellWidth, cellHeight);
        JLabel jLabel15 = car.getLabel();
        testLayout.setHorizontalGroup(
                testLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel15,
                                GroupLayout.PREFERRED_SIZE,
                                cellHeight,
                                javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        testLayout.setVerticalGroup(
                testLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel15,
                                GroupLayout.PREFERRED_SIZE,
                                cellHeight,
                                GroupLayout.PREFERRED_SIZE)
        );
    }

    private void setDefault(int x, int y) {
        // cells[x - 1][y - 1].setState(CellState.LIBRE);
        cells[x - 1][y - 1].getCellJPanel().removeAll();
        cells[x - 1][y - 1].getCellJPanel().repaint();
        cells[x - 1][y - 1].getCellJPanel().updateUI();
        if (cells[x - 1][y - 1].getState() == CellState.LIBRE)
            cells[x - 1][y - 1].getCellJPanel().setBackground(Color.GREEN);
        if (cells[x - 1][y - 1].getType() == CellType.ROAD)
            cells[x - 1][y - 1].getCellJPanel().setBackground(Color.decode("#303030"));
        if (cells[x - 1][y - 1].getType() == CellType.PARK)
            cells[x - 1][y - 1].getCellJPanel().setBackground(Color.gray);
        if (cells[x - 1][y - 1].getType() == CellType.HANDI)
            cells[x - 1][y - 1].getCellJPanel().setBackground(Color.pink);
    }

    private LinkedList<ParkingCell> findPath(ParkingCell targetParkingCell, ParkingCell departParkingCell) {
        LinkedList<ParkingCell> path = new LinkedList<>();
        int columnIndex = departParkingCell.getColumn() - 1;
        int startRowIndex = departParkingCell.getRow() - 1;
        int targetColumn = targetParkingCell.getPosition().getColumn();
        int targetRow = targetParkingCell.getPosition().getRow();
        //path.add(cells[1][0]);
        /*path.add(cells[1][1]);*/
        boolean found = false;
        //dans le cas ou la dernierre ligne n'est pas une route
        if (startRowIndex == size - 1) {
            path.add(departParkingCell);
            startRowIndex++;
        }
        //on parcoure les lignes jusqu'a celle qui contient la place vide
        int stop = targetRow - 1;
        int r = startRowIndex;
        for (int rowIndex = startRowIndex; rowIndex < stop; rowIndex++) {
            //si la ligne juste en dessous de mon pointeur n'est pas du type route
            //on enregistre les cases parcourus (elles seront notre chemin versla colone dont
            // on utilisera pour descendre a la ligne avant celle qui contient notre target)

            if (departParkingCell.getColumn() < getLastRoadColumn()) {
                while (columnIndex <= size - 1 || found) {
                    path.add(cells[rowIndex][columnIndex]);
                    if (cells[rowIndex + 1][columnIndex].getType() == CellType.ROAD ||
                            (cells[rowIndex + 1][columnIndex].getType() != CellType.ROAD &&
                                    cells[rowIndex + 1][columnIndex].getState() == CellState.LIBRE)) {
                        found = true;
                        break;
                    }
                    columnIndex++;
                }
            } else {
                while (columnIndex + 1 > getLastRoadColumn() || found) {
                    path.add(cells[rowIndex][columnIndex]);
                    if (cells[rowIndex + 1][columnIndex].getType() == CellType.ROAD) {
                        found = true;
                        break;
                    }
                    columnIndex--;
                }
            }
            path.add(cells[rowIndex][columnIndex]);
            r = rowIndex;
        }
        if (stop == size - 1) if (cells[size - 1][0].getType() == CellType.ROAD) {
            path.add(cells[r + 1][columnIndex]);
        }


        //on parcoure la ligne audessus de la ligne target jusqu'a a la colonne target
        while (columnIndex != targetColumn) {
            //si la colonne target est a gauche du pointeur on decremente sinn on incremente
            if (columnIndex < targetColumn)
                columnIndex++;
            else
                columnIndex--;
            if (stop == size - 1) if (cells[size - 1][0].getType() == CellType.ROAD) {
                path.add(cells[targetRow - 1][columnIndex - 1]);
            } else path.add(cells[targetRow - 2][columnIndex - 1]);
        }
        //on ajoute la case target
        path.add(cells[targetRow - 1][targetColumn - 1]);
        return path;
    }

    private int getLastRoadColumn() {
        int j = size - 1;
        while (cells[1][j].getType() == CellType.PARK) {
            j--;
        }
        System.out.println("lestColumnRoad = " + j);
        return j;
    }


    private ParkingCell findFreePlace(ClientType clientType) {
        for (int i = 0; i <= size - 1; i++) {
            for (int j = 0; j <= size - 1; j++) {
                //  System.out.println("cells[" + i + "][" + j + "]= " + cells[i][j].toString());
                if (cells[i][j].getType() == CellType.PARK && cells[i][j].getState() != CellState.OCCUPEE && clientType != ClientType.HANDICAP)
                    return cells[i][j];
                else if (cells[i][j].getType() == CellType.HANDI && cells[i][j].getState() != CellState.OCCUPEE && clientType == ClientType.HANDICAP)
                    return cells[i][j];

            }
        }
        // System.out.println("Parking.findFreePlace");
        return null;
    }


    public void prendrePlace(GraphicCar testCar) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                synchronized (this) {
                    ParkingCell freePlace = findFreePlace(testCar.getClient().getType());
                    if (freePlace == null) System.out.println("no free place left!");
                    else {
                        ParkingCell departParkingCell = new ParkingCell();
                        departParkingCell.setRow(1);
                        departParkingCell.setColumn(1);
                        occupy(freePlace);
                        deplacerVoitureSurPath(departParkingCell, freePlace, testCar, this);
//                        cells[freePlace.getRow() - 1][freePlace.getColumn() - 1].setState(CellState.OCCUPEE);
                        try {
                            this.wait(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        thread.start();
    }

    public void sortir(GraphicCar voiture) {
        ParkingCell current = new ParkingCell();
        current.setColumn(voiture.getPosition().getColumn());
        current.setRow(voiture.getPosition().getRow());
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (this) {
                    ParkingCell departParkingCell = new ParkingCell();
                    departParkingCell.setRow(voiture.getPosition().getRow());
                    departParkingCell.setColumn(voiture.getPosition().getColumn());
                    liberer(departParkingCell);
                    deplacerVoitureSurPath(departParkingCell, sortie, voiture, this);
                }
            }
        });
        thread.start();
    }

    private void liberer(ParkingCell cell) {
        System.out.println("liberer = [" + cell + "]time : " + System.currentTimeMillis());
        cells[cell.getRow() - 1][cell.getColumn() - 1].setState(CellState.LIBRE);
        cells[cell.getRow() - 1][cell.getColumn() - 1].getCellJPanel().setBackground(Color.green);
    }


    private void occupy(ParkingCell freePlace) {
        System.out.println("occupy = [" + freePlace + "] time : " + System.currentTimeMillis());
        cells[freePlace.getRow() - 1][freePlace.getColumn() - 1].setState(CellState.OCCUPEE);
        cells[freePlace.getRow() - 1][freePlace.getColumn() - 1].getCellJPanel().setBackground(Color.red);

    }

    private void deplacerVoitureSurPath(ParkingCell departParkingCell, ParkingCell destinationParkingCell, GraphicCar voiture, Runnable context) {
        boolean notFirst = false;
        int previousX = 1;
        int previousY = 1;
        for (ParkingCell cell : findPath(destinationParkingCell, departParkingCell)) {
            voiture.setPosition(cell.getPosition());
            setCar(voiture);
            try {
                context.wait(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (notFirst)
                setDefault(previousX, previousY);
            previousX = cell.getRow();
            previousY = cell.getColumn();
            notFirst = true;
        }
    }
}
