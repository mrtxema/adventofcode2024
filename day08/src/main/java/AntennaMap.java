import common.movement.Position;
import common.parser.CharGrid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AntennaMap {
    private static final char EMPTY = '.';
    private final CharGrid charGrid;

    public AntennaMap(CharGrid charGrid) {
        this.charGrid = charGrid;
    }

    public Map<Character, List<Position>> getAllAntennaPositionsByFrequency() {
        Map<Character, List<Position>> result = new HashMap<>();
        for (int y = 0; y < charGrid.getMaxY(); y++) {
            for (int x = 0; x < charGrid.getMaxX(); x++) {
                var position = new Position(x, y);
                charGrid.getChar(position)
                        .filter(c -> c != EMPTY)
                        .ifPresent(c -> result.computeIfAbsent(c, ignore -> new ArrayList<>()).add(position));
            }
        }
        return result;
    }

    public Set<Position> findAntinodes(List<Position> antennaPositions, boolean includeResonants) {
        Set<Position> result = new HashSet<>();
        for (int i = 0; i < antennaPositions.size() - 1; i++) {
            var firstAntenna = antennaPositions.get(i);
            for (int j = i + 1; j < antennaPositions.size(); j++) {
                var secondAntenna = antennaPositions.get(j);
                result.addAll(findAntinodes(firstAntenna, secondAntenna, includeResonants));
            }
        }
        return result;
    }

    private Set<Position> findAntinodes(Position firstAntenna, Position secondAntenna, boolean includeResonants) {
        int xDiff = secondAntenna.x() - firstAntenna.x();
        int yDiff = secondAntenna.y() - firstAntenna.y();
        Set<Position> result = new HashSet<>();
        result.addAll(findAntinodes(secondAntenna, xDiff, yDiff, includeResonants));
        result.addAll(findAntinodes(firstAntenna, -xDiff, -yDiff, includeResonants));
        return result;
    }

    private Set<Position> findAntinodes(Position initial, int xDiff, int yDiff, boolean includeResonants) {
        Set<Position> result = new HashSet<>();
        if (includeResonants) {
            result.add(initial);
        }
        var position = new Position(initial.x() + xDiff, initial.y() + yDiff);
        while (charGrid.isInBounds(position)) {
            result.add(position);
            if (!includeResonants) {
                break;
            }
            position = new Position(position.x() + xDiff, position.y() + yDiff);
        }
        return result;
    }
}
