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

import javax.swing.*;

@SuppressWarnings("unused")
class ParkingCell {
    private int column;
    private int row;
    private JPanel cellJPanel;
    private CellType type;
    private CellState state;
    private Position position;

    ParkingCell() {
    }

    CellState getState() {
        return state;
    }

    void setState(CellState state) {
        this.state = state;
    }

    JPanel getCellJPanel() {
        return cellJPanel;
    }

    void setCellJPanel(JPanel cellJPanel) {
        this.cellJPanel = cellJPanel;
    }

    int getColumn() {
        return column;
    }

    void setColumn(int column) {
        this.column = column;
    }

    int getRow() {
        return row;
    }

    void setRow(int row) {
        this.row = row;
    }

    CellType getType() {
        return type;
    }

    void setType(CellType type) {
        this.type = type;
    }

    Position getPosition() {
        return new Position(row, column);
    }

    @Override
    public String toString() {
        return "ParkingCell{" +
                "column=" + column +
                ", row=" + row +
                ", type=" + type +
                ", state=" + state +
                ", position=" + position +
                '}';
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
