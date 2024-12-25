import common.parser.IOUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

public class SchematicsParser {
    private static final String FILLED_ROW = "#####";
    private static final char EMPTY_CHAR = '.';

    public Schematics parse(List<String> lines) {
        List<Lock> locks = new ArrayList<>();
        List<Key> keys = new ArrayList<>();
        for (var section : IOUtils.splitSections(lines)) {
            if (section.get(0).equals(FILLED_ROW)) {
                locks.add(parseLock(section));
            } else if (section.get(section.size() - 1).equals(FILLED_ROW)) {
                keys.add(parseKey(section));
            } else {
                throw new IllegalArgumentException("Invalid schematic: " + section);
            }
        }
        return new Schematics(locks, keys);
    }

    private Lock parseLock(List<String> lines) {
        var heights = IntStream.range(0, 5).map(column -> findHeight(lines, column, 1, n -> n + 1)).boxed().toList();
        return new Lock(heights);
    }

    private Key parseKey(List<String> lines) {
        var heights = IntStream.range(0, 5).map(column -> findHeight(lines, column, 5, n -> n - 1)).boxed().toList();
        return new Key(heights);
    }

    private int findHeight(List<String> lines, int column, int start, IntUnaryOperator nextFunction) {
        int i = start;
        int height = 0;
        while (true) {
            if (lines.get(i).charAt(column) == EMPTY_CHAR) {
                return height;
            }
            height++;
            i = nextFunction.applyAsInt(i);
        }
    }
}
