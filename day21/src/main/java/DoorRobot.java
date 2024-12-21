import common.movement.Position;
import java.util.HashMap;
import java.util.Map;

public class DoorRobot extends Robot {

    public DoorRobot() {
        super(buildButtonsMap(), new Position(0, 3));
    }

    private static Map<Character, Position> buildButtonsMap() {
        Map<Character, Position> result = new HashMap<>(Map.of(
                '7', new Position(0, 0),
                '8', new Position(1, 0),
                '9', new Position(2, 0),
                '4', new Position(0, 1),
                '5', new Position(1, 1),
                '6', new Position(2, 1),
                '1', new Position(0, 2),
                '2', new Position(1, 2),
                '3', new Position(2, 2)));
        result.putAll(Map.of(
                '0', new Position(1, 3),
                'A', new Position(2, 3)));
        return result;
    }
}
