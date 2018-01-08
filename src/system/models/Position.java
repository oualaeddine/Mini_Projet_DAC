package system.models;

public class Position {

    private int row, column;

    Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    Position() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;

        Position position = (Position) o;

        return row == position.row && column == position.column;
    }

    @Override
    public int hashCode() {
        int result = row;
        result = 31 * result + column;
        return result;
    }

    int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    @Override
    public String toString() {
        return "Position{" +
                "row=" + row +
                ", column=" + column +
                '}';
    }
}
