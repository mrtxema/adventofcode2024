import common.movement.Direction;
import common.movement.Position;
import common.parser.CharGrid;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class WarehouseState {
    private final Map<Position, MapItem> items;
    private Position robotPosition;

    public WarehouseState(Map<Position, MapItem> items, Position robotPosition) {
        this.items = items;
        this.robotPosition = robotPosition;
    }

    public static WarehouseState from(CharGrid map) {
        Map<Position, MapItem> items = map.getAllPositions()
                .collect(Collectors.toMap(
                        Function.identity(),
                        p -> MapItem.fromSymbol(map.getChar(p)
                                .orElseThrow(() -> new IllegalStateException("Missing position: " + p)))));
        Position robot = items.entrySet().stream()
                .filter(entry -> entry.getValue() == MapItem.ROBOT)
                .map(Map.Entry::getKey)
                .findAny()
                .orElseThrow(() -> new IllegalStateException("Missing robot"));
        return new WarehouseState(items, robot);
    }

    public void moveRobot(Direction movement) {
        var position = Set.of(robotPosition);
        if (canBeMoved(position, movement)) {
            move(position, movement);
            robotPosition = movement.move(robotPosition);
        }
    }

    private boolean canBeMoved(Set<Position> positions, Direction direction) {
        for (Position position : positions) {
            var newPos = direction.move(position);
            var newPosItem = items.get(newPos);
            boolean result = switch (newPosItem) {
                case ROBOT -> throw new IllegalStateException("Too many robots");
                case WALL -> false;
                case SMALL_BOX, LEFT_BOX, RIGHT_BOX -> {
                    var fullBox = newPosItem.getFullBoxPositions(newPos);
                    yield positions.containsAll(fullBox) || canBeMoved(fullBox, direction);
                }
                case EMPTY -> true;
            };
            if (!result) {
                return false;
            }
        }
        return true;
    }

    private void move(Set<Position> positionsArg, Direction direction) {
        var positions = positionsArg.stream().sorted(getComparator(direction)).toList();
        for (Position position : positions) {
            var newPos = direction.move(position);
            var newPosItem = items.get(newPos);
            if (newPosItem.isBox()) {
                var fullBox = newPosItem.getFullBoxPositions(newPos);
                if (!positionsArg.containsAll(fullBox)) {
                    move(newPosItem.getFullBoxPositions(newPos), direction);
                }
            }
            items.put(newPos, items.get(position));
            items.put(position, MapItem.EMPTY);
        }
    }

    private Comparator<Position> getComparator(Direction direction) {
        return switch (direction) {
            case NORTH -> Comparator.comparing(Position::y);
            case EAST -> Comparator.comparing(Position::x).reversed();
            case SOUTH -> Comparator.comparing(Position::y).reversed();
            case WEST -> Comparator.comparing(Position::x);
            default -> throw new IllegalArgumentException("Invalid direction: " + direction);
        };
    }

    public Set<Position> getBoxesPositions() {
        var targets = EnumSet.of(MapItem.SMALL_BOX, MapItem.LEFT_BOX);
        return items.entrySet().stream()
                .filter(entry -> targets.contains(entry.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }
}
