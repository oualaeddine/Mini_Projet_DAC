/******************************************************************************
 *                                                                            *
 *  (C) Copyright ${year} Berrehal Ouala Eddine & Benghezal Ines.             *
 *  G2 L3 GL.                                                                 *
 *  Mini projet Module DAC.                                                   *
 *  Simulation d'un parking                                                   *
 *  avec gestion des concurrences en utilisant des sémaphores                 *
 *                                                                            *
 ******************************************************************************/
package system.models;

import system.enums.CellState;
import system.enums.CellType;
import system.enums.ClientType;
import system.enums.Direction;
import ui.GraphicCar;
import ui.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class Parking {
    private final int size;
    private final ParkingCell[][] cells;
    private final ParkingCell sortie;

    public Parking(int size) {
        this.size = size;
        this.cells = new ParkingCell[size][size];
        sortie = new ParkingCell();
        if (size % 2 == 0) {
            sortie.setRow(size - 1);
            sortie.setColumn(size);
        } else {
            sortie.setRow(size);
            sortie.setColumn(size);
        }
    }

    /**
     * cette methode ajoute un objet cell au parking
     */
    public void addCellToParking(JPanel pan, int row, int column, CellType type) {
        ParkingCell parkingCell = new ParkingCell();
        parkingCell.setCellJPanel(pan);
        parkingCell.setColumn(column);
        parkingCell.setRow(row);
        parkingCell.setType(type);
        cells[row - 1][column - 1] = parkingCell;
    }

    /**
     * cette methode occupe une case visuellement par une voiture
     */
    private void setCar(GraphicCar car) {
        int x = car.getPosition().getRow();
        int y = car.getPosition().getColumn();
        int cellHeight = cells[x - 1][y - 1].getCellJPanel().getHeight();
        int cellWidth = cells[x - 1][y - 1].getCellJPanel().getHeight();
        if (x == size && y == size && size % 2 != 0) return;
        GroupLayout testLayout = new GroupLayout(cells[x - 1][y - 1].getCellJPanel());
        cells[x - 1][y - 1].getCellJPanel().setLayout(testLayout);
//        cells[x - 1][y - 1].setState(CellState.OCCUPEE);
//        car.setupIconOrientation();
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

    /**
     * cette methode remet la case a son etat visuel initial avant d'etre occupée par une voiture
     */
    private void setDefault(int x, int y) {
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

    /**
     * cette methode retourne une liste de cellules(route) d'un chemin d'une case vers une autre
     */
    private LinkedList<ParkingCell> findPath(ParkingCell targetParkingCell, ParkingCell departParkingCell) {
        LinkedList<ParkingCell> path = new LinkedList<>();
        int columnIndex = departParkingCell.getColumn() - 1;
        int startRowIndex = departParkingCell.getRow() - 1;
        int targetColumn = targetParkingCell.getPosition().getColumn();
        int targetRow = targetParkingCell.getPosition().getRow();
        boolean found = false;
        //dans le cas ou la dernierre ligne n'est pas une route
        if (startRowIndex == size - 1) {
            path.add(departParkingCell);
            startRowIndex++;
        }
        if (startRowIndex == size - 1 && columnIndex == size - 1) {
            path.add(cells[size - 2][size - 1]);
            return path;
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
        return j;
    }

    /**
     * cette methode trouve la place vide la plus proche de l'entrée
     */
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
        return null;
    }

    /**
     * cette methode s'occupe de la recherche et du deplacement vers un place vide
     */
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

    /**
     * cette methode s'occupe de la recherche et du deplacement vers la sortie
     */
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

    /**
     * cette methode s'occupe de changer l'etat d'une place
     */
    private void liberer(ParkingCell cell) {
        cells[cell.getRow() - 1][cell.getColumn() - 1].setState(CellState.LIBRE);
        cells[cell.getRow() - 1][cell.getColumn() - 1].getCellJPanel().setBackground(Color.green);
        setDefault(cell.getRow(), cell.getColumn());
        try {
            switch (cells[cell.getRow() - 1][cell.getColumn() - 1].getType()) {
                case HANDI:
                    MainWindow.nbrPlacesHandiocc--;
                    break;
                case PARK:
                    MainWindow.nbrPlacesnormalocc--;
                    break;
            }
        } catch (Exception ignored) {
        }
    }

    /**
     * cette methode s'occupe de changer l'etat d'une place
     */
    private void occupy(ParkingCell freePlace) {
        cells[freePlace.getRow() - 1][freePlace.getColumn() - 1].setState(CellState.OCCUPEE);
        cells[freePlace.getRow() - 1][freePlace.getColumn() - 1].getCellJPanel().setBackground(Color.red);

        try {
            switch (cells[freePlace.getRow() - 1][freePlace.getColumn() - 1].getType()) {
                case HANDI:
                    MainWindow.nbrPlacesHandiocc++;
                    break;
                case PARK:
                    MainWindow.nbrPlacesnormalocc++;
                    break;
            }
        } catch (Exception ignored) {
        }
        MainWindow.updateView();
    }

    /**
     * cette methode s'occupe du deplacement d'une voiture sur un chemin de cases
     */
    private void deplacerVoitureSurPath(ParkingCell departParkingCell, ParkingCell destinationParkingCell, GraphicCar voiture, Runnable context) {
        boolean notFirst = false;
        int previousX = 1;
        int previousY = 1;
        for (ParkingCell cell : findPath(destinationParkingCell, departParkingCell)) {
            voiture.setPosition(cell.getPosition());
            if (cell.getPosition().equals(destinationParkingCell.getPosition()))
                setCarGarree(voiture);
            else
                setCar(voiture);
            try {
                context.wait(100);
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

    private void setCarGarree(GraphicCar car) {
        int x = car.getPosition().getRow();
        int y = car.getPosition().getColumn();
        int cellHeight = cells[x - 1][y - 1].getCellJPanel().getHeight();
        int cellWidth = cells[x - 1][y - 1].getCellJPanel().getHeight();
        if (x == size && y == size && size % 2 != 0) return;
        GroupLayout testLayout = new GroupLayout(cells[x - 1][y - 1].getCellJPanel());
        cells[x - 1][y - 1].getCellJPanel().setLayout(testLayout);
//        cells[x - 1][y - 1].setState(CellState.OCCUPEE);
        car.setupIconSize(cellWidth, cellHeight, Direction.NO);
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
}
