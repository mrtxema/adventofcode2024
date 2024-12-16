package common.parser;

import common.movement.Position;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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

    public Stream<Position> getAllPositions() {
        return IntStream.range(0, getMaxY()).boxed()
                .flatMap(y -> IntStream.range(0, getMaxX()).mapToObj(x -> new Position(x, y)))
                .filter(this::isInBounds);
    }

    public Stream<Position> findPositions(char symbol) {
        return getAllPositions().filter(p -> getChar(p).filter(c -> c == symbol).isPresent());
    }

    public Optional<Position> findPosition(char symbol) {
        return findPositions(symbol).findAny();
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
