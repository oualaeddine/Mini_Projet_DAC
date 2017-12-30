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
        if (position.getX() < MainWindow.getParkingSize())
            position.setX(position.getX() - 1);
    }

    @Override
    public void goRight() {
        direction = RIGHT;
        if (position.getX() < MainWindow.getParkingSize())
            position.setX(position.getX() + 1);
    }

    @Override
    public void goUp() {
        direction = UP;
        if (position.getY() < MainWindow.getParkingSize())
            position.setY(position.getY() - 1);
    }

    @Override
    public void goDown() {
        direction = DOWN;
        if (position.getY() < MainWindow.getParkingSize())
            position.setY(position.getY() + 1);
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
}
