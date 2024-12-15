import common.movement.Direction;
import common.movement.Position;
import java.util.Arrays;
import java.util.Set;

public enum MapItem {
    ROBOT('@', false),
    WALL('#', false),
    SMALL_BOX('O', true),
    LEFT_BOX('[', true),
    RIGHT_BOX(']', true),
    EMPTY('.', false);

    private final char symbol;
    private final boolean box;

    MapItem(char symbol, boolean box) {
        this.symbol = symbol;
        this.box = box;
    }

    public static MapItem fromSymbol(char symbol) {
        return Arrays.stream(values())
                .filter(i -> i.symbol == symbol).findAny()
                .orElseThrow(() -> new IllegalArgumentException("Unknown symbol: " + symbol));
    }

    public boolean isBox() {
        return box;
    }

    public Set<Position> getFullBoxPositions(Position position) {
        return switch (this) {
            case SMALL_BOX -> Set.of(position);
            case LEFT_BOX -> Set.of(position, Direction.EAST.move(position));
            case RIGHT_BOX -> Set.of(Direction.WEST.move(position), position);
            default -> throw new IllegalStateException("Not a box: " + this);
        };
    }
}
