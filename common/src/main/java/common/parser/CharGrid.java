package common.parser;

import common.movement.Position;

import java.util.List;
import java.util.Optional;

public class CharGrid {
    private final List<String> lines;
    private final int maxX;

    public CharGrid(List<String> lines) {
        this.lines = lines;
        this.maxX = lines.stream().mapToInt(String::length).max().orElse(0);
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return lines.size();
    }

    public Optional<Character> getChar(Position position) {
        if (position.y() < 0 || position.y() >= getMaxY()) {
            return Optional.empty();
        }
        var line = lines.get(position.y());
        if (position.x() < 0 || position.x() >= line.length()) {
            return Optional.empty();
        }
        return Optional.of(line.charAt(position.x()));
    }
}
