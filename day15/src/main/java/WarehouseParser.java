import common.movement.Direction;
import common.parser.CharGrid;
import common.parser.IOUtils;
import common.parser.StringUtils;
import java.util.List;
import java.util.stream.IntStream;

public class WarehouseParser {

    public Warehouse parse(List<String> lines) {
        var sections = IOUtils.splitSections(lines, 2);
        return new Warehouse(parseMap(sections.get(0)), parseMovements(sections.get(1)));
    }

    public Warehouse parseScaledUp(List<String> lines) {
        var sections = IOUtils.splitSections(lines, 2);
        return new Warehouse(parseScaledUpMap(sections.get(0)), parseMovements(sections.get(1)));
    }

    private CharGrid parseMap(List<String> lines) {
        return new CharGrid(lines.stream().map(String::toCharArray).toArray(char[][]::new));
    }

    private CharGrid parseScaledUpMap(List<String> lines) {
        return new CharGrid(lines.stream().map(this::scaleUpLine).map(String::toCharArray).toArray(char[][]::new));
    }

    private String scaleUpLine(String line) {
        StringBuilder result = new StringBuilder();
        IntStream.range(0, line.length()).mapToObj(line::charAt).map(this::scaleUp).forEach(result::append);
        return result.toString();
    }

    private String scaleUp(char c) {
        return switch (c) {
            case '#' -> "##";
            case 'O' -> "[]";
            case '.' -> "..";
            case '@' -> "@.";
            default -> throw new IllegalArgumentException("Unknown symbol: " + c);
        };
    }

    private List<Direction> parseMovements(List<String> lines) {
        return lines.stream().flatMap(StringUtils::characters).map(this::parseMovement).toList();
    }

    private Direction parseMovement(char symbol) {
        return switch (symbol) {
            case '^' -> Direction.NORTH;
            case '>' -> Direction.EAST;
            case 'v' -> Direction.SOUTH;
            case '<' -> Direction.WEST;
            default -> throw new IllegalArgumentException("Unexpected value: " + symbol);
        };
    }
}
