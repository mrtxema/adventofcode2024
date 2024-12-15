import common.movement.Direction;
import common.parser.CharGrid;
import java.util.List;

public class Warehouse {
    private final CharGrid map;
    private final List<Direction> movements;

    public Warehouse(CharGrid map, List<Direction> movements) {
        this.map = map;
        this.movements = movements;
    }

    public WarehouseState simulate() {
        var state = WarehouseState.from(map);
        for (Direction movement : movements) {
            state.moveRobot(movement);
        }
        return state;
    }
}
