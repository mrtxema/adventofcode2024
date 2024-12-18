import common.movement.Position;
import common.parser.SimpleParser;

public class PositionParser extends SimpleParser<Position> {

    @Override
    protected Position nonNullParse(String line) {
        var parts = line.split(",", 2);
        return new Position(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
    }
}
