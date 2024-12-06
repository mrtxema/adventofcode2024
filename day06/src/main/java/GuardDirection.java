import common.movement.Direction;

import java.util.Arrays;
import java.util.Optional;

public enum GuardDirection {
    NORTH('^', Direction.NORTH),
    EAST('>', Direction.EAST),
    SOUTH('v', Direction.SOUTH),
    WEST('<', Direction.WEST);

    private final char symbol;
    private final Direction direction;

    GuardDirection(char symbol, Direction direction) {
        this.symbol = symbol;
        this.direction = direction;
    }

    public char getSymbol() {
        return symbol;
    }

    public Direction getDirection() {
        return direction;
    }

    public GuardDirection turnRight() {
        return switch (this) {
            case NORTH -> EAST;
            case EAST -> SOUTH;
            case SOUTH -> WEST;
            case WEST -> NORTH;
        };
    }

    public static Optional<GuardDirection> fromSymbol(char symbol) {
        return Arrays.stream(GuardDirection.values()).filter(gd -> gd.getSymbol() == symbol).findAny();
    }
}
