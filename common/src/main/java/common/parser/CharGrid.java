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
        if (position.y() < 0 || position.y() >= getMaxY()) {
            return Optional.empty();
        }
        var row = chars[position.y()];
        if (position.x() < 0 || position.x() >= row.length) {
            return Optional.empty();
        }
        return Optional.of(row[position.x()]);
    }
}
