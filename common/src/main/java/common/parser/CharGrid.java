package common.parser;

import common.movement.Position;
import java.util.Arrays;
import java.util.Optional;

public class CharGrid {
    private final char[][] chars;
    private final int maxX;

    public CharGrid(char[][] chars) {
        this.chars = chars;
        this.maxX = Arrays.stream(chars).mapToInt(row -> row.length).max().orElse(0);
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return chars.length;
    }

    public Optional<Character> getChar(Position position) {
        if (!isInBounds(position)) {
            return Optional.empty();
        }
        return Optional.of(chars[position.y()][position.x()]);
    }

    public boolean isInBounds(Position position) {
        if (position.y() < 0 || position.y() >= getMaxY()) {
            return false;
        }
        return position.x() >= 0 && position.x() < chars[position.y()].length;
    }
}
