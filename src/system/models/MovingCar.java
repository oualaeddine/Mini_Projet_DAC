package system.models;

import system.enums.Direction;
import system.interfaces.Moving;
import ui.MainWindow;

import static system.enums.Direction.*;

public class MovingCar extends Car implements Moving {
    private Position position;
    private Direction direction;

    public MovingCar() {
        this.position = new Position();
    }

    @Override
    public void goLeft() {
        direction = LEFT;
        if (position.getRow() < MainWindow.getParkingSize())
            position.setRow(position.getRow() - 1);
    }

    @Override
    public void goRight() {
        direction = RIGHT;
        if (position.getRow() < MainWindow.getParkingSize())
            position.setRow(position.getRow() + 1);
    }

    @Override
    public void goUp() {
        direction = UP;
        if (position.getColumn() < MainWindow.getParkingSize())
            position.setColumn(position.getColumn() - 1);
    }

    @Override
    public void goDown() {
        direction = DOWN;
        if (position.getColumn() < MainWindow.getParkingSize())
            position.setColumn(position.getColumn() + 1);
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "MovingCar{" +
                "position=" + position +
                ", direction=" + direction +
                "} " + super.toString();
    }
}
