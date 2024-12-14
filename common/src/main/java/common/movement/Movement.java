package common.movement;

public record Movement(int deltaX, int deltaY) {

    public Position move(Position start) {
        return move(start, 1);
    }

    public Position move(Position start, int steps) {
        return new Position(start.x() + deltaX * steps, start.y() + deltaY * steps);
    }
}
