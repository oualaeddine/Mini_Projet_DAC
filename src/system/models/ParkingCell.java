package system.models;

import system.enums.CellType;

import javax.swing.*;

public class ParkingCell {
    private int column;
    private int row;
    private JPanel cellJPanel;
    private CellType type;

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
}
