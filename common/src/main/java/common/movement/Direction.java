package common.movement;

import java.util.Arrays;
import java.util.List;

public enum Direction {
    NORTH(0, -1),
    NORTH_EAST(1, -1),
    EAST(1, 0),
    SOUTH_EAST(1, 1),
    SOUTH(0, 1),
    SOUTH_WEST(-1, 1),
    WEST(-1, 0),
    NORTH_WEST(-1, -1);

    private final int xOffset;
    private final int yOffset;

    Direction(int xOffset, int yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public Position move(Position start) {
        return move(start, 1);
    }

    public Position move(Position start, int steps) {
        return new Position(start.x() + xOffset * steps, start.y() + yOffset * steps);
    }

    boolean isStraight() {
        return xOffset == 0 || yOffset == 0;
    }

    public static List<Direction> all() {
        return Arrays.asList(Direction.values());
    }

    public static List<Direction> straight() {
        return Arrays.stream(Direction.values()).filter(Direction::isStraight).toList();
    }

    public static List<Direction> diagonal() {
        return Arrays.stream(Direction.values()).filter(d -> !d.isStraight()).toList();
    }
}
