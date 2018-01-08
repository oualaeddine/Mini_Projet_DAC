package system.models;

import system.enums.Direction;

public class MovingCar extends Car {
    private Position position;
    private Direction direction;

    protected MovingCar() {
        super();
        this.position = new Position();
    }

    protected void setPosition(Position position) {
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
