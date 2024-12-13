import common.movement.Position;
import java.util.List;

public class MachineSpecParser {

    public MachineSpec parse(List<String> specLines) {
        if (specLines.size() != 3) {
            throw new IllegalArgumentException("Invalid machine specs: " + specLines);
        }
        return new MachineSpec(parseMovement(specLines.get(0)), parseMovement(specLines.get(1)),
                parsePosition(specLines.get(2)));
    }

    private Movement parseMovement(String spec) {
        var coord = parseCoordinates(spec);
        return new Movement(coord.x(), coord.y());
    }

    private Position parsePosition(String spec) {
        var coord = parseCoordinates(spec);
        return new Position(coord.x(), coord.y());
    }

    private Coordinates parseCoordinates(String spec) {
        int colonIndex = spec.indexOf(':');
        String[] coordinatesSpecs = spec.substring(colonIndex + 1).trim().split(",", 2);
        return new Coordinates(parseCoordinate(coordinatesSpecs[0].trim()), parseCoordinate(coordinatesSpecs[1].trim()));
    }

    private int parseCoordinate(String spec) {
        int index = spec.charAt(1) == '=' ? 2 : 1;
        return Integer.parseInt(spec.substring(index));
    }

    private record Coordinates(int x, int y) {
    }
}
