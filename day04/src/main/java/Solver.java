import common.movement.Direction;
import common.movement.Position;
import common.parser.CharGrid;
import common.parser.IOUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Solver {
    private final String fileName;
    private List<String> lines;

    public Solver(String fileName) {
        this.fileName = fileName;
    }

    public Solver parseFile() {
        lines = IOUtils.readTrimmedLines(getClass().getResource(fileName));
        return this;
    }

    public long part1() {
        var grid = new CharGrid(lines);
        return countWordOccurrences(grid, "XMAS");
    }

    public long part2() {
        var grid = new CharGrid(lines);
        return countCrossedMassOccurrences(grid);
    }

    public int countWordOccurrences(CharGrid grid, String word) {
        int occurrences = 0;
        for (int y = 0; y < grid.getMaxY(); y++) {
            for (int x = 0; x < grid.getMaxX(); x++) {
                for (Direction direction : Direction.all()) {
                    var gridWord = extractWord(grid, new Position(x, y), direction, word.length());
                    if (gridWord.equals(word)) {
                        occurrences++;
                    }
                }
            }
        }
        return occurrences;
    }

    private String extractWord(CharGrid grid, Position initialPosition, Direction direction, int length) {
        StringBuilder word = new StringBuilder();
        var position = initialPosition;
        while (word.length() < length) {
            var nextChar = grid.getChar(position);
            if (nextChar.isEmpty()) {
                break;
            }
            word.append(nextChar.get());
            position = direction.move(position);
        }
        return word.toString();
    }

    public int countCrossedMassOccurrences(CharGrid grid) {
        int occurrences = 0;
        for (int y = 0; y < grid.getMaxY(); y++) {
            for (int x = 0; x < grid.getMaxX(); x++) {
                if (isCrossMass(grid, new Position(x, y))) {
                    occurrences++;
                }
            }
        }
        return occurrences;
    }

    private boolean isCrossMass(CharGrid grid, Position position) {
        var nw = grid.getChar(Direction.NORTH_WEST.move(position));
        var ne = grid.getChar(Direction.NORTH_EAST.move(position));
        var sw = grid.getChar(Direction.SOUTH_WEST.move(position));
        var se = grid.getChar(Direction.SOUTH_EAST.move(position));
        var ct = grid.getChar(position);
        if (Stream.of(nw, ne, sw, se, ct).anyMatch(Optional::isEmpty)) {
            return false;
        }
        if (ct.get() != 'A' || Stream.of(nw, ne, sw, se).map(Optional::get).anyMatch(c -> c != 'M' && c != 'S')) {
            return false;
        }
        return nw.get() != se.get() && ne.get() != sw.get();
    }
}
