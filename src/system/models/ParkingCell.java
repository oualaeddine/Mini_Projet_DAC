package system.models;

import system.enums.CellState;
import system.enums.CellType;

import javax.swing.*;

public class ParkingCell {
    private int column;
    private int row;
    private JPanel cellJPanel;
    private CellType type;
    private CellState state;
    private Position position;

    public CellState getState() {
        return state;
    }

    public void setState(CellState state) {
        this.state = state;
    }

    public ParkingCell() {
    }

    public JPanel getCellJPanel() {
        return cellJPanel;
    }

    public void setCellJPanel(JPanel cellJPanel) {
        this.cellJPanel = cellJPanel;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public CellType getType() {
        return type;
    }

    public void setType(CellType type) {
        this.type = type;
    }

    public Position getPosition() {
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
}
