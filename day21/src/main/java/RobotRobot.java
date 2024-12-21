import common.movement.Position;
import java.util.Map;

public class RobotRobot extends Robot {

    public RobotRobot() {
        super(Map.of(
                '^', new Position(1, 0),
                'A', new Position(2, 0),
                '<', new Position(0, 1),
                'v', new Position(1, 1),
                '>', new Position(2, 1)),
                new Position(0, 0));
    }
}
