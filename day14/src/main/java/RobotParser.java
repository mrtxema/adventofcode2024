import common.movement.Movement;
import common.movement.Position;
import common.parser.SimpleParser;

public class RobotParser extends SimpleParser<Robot> {

    @Override
    protected Robot nonNullParse(String line) {
        var parts = line.split(" ", 2);
        return new Robot(parsePosition(parts[0]), parseVelocity(parts[1]));
    }

    private Position parsePosition(String spec) {
        var coord = parseCoordinates(spec);
        return new Position(coord.x(), coord.y());
    }

    private Movement parseVelocity(String spec) {
        var coord = parseCoordinates(spec);
        return new Movement(coord.x(), coord.y());
    }

    private Coordinates parseCoordinates(String spec) {
        var coordSpecs = spec.trim().substring(2).split(",", 2);
        return new Coordinates(Integer.parseInt(coordSpecs[0]), Integer.parseInt(coordSpecs[1]));
    }

    private record Coordinates(int x, int y) {
    }
}
