import common.math.MathUtils;
import common.movement.Direction;
import common.movement.Position;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RobotMap {
    private final List<Robot> robotList;
    private final int width;
    private final int height;
    private List<Robot> simulatedRobotList;

    public RobotMap(List<Robot> robotList, int width, int height) {
        this.robotList = robotList;
        this.width = width;
        this.height = height;
    }

    public void simulateMovements(int seconds) {
        simulatedRobotList = robotList.stream().map(robot -> simulateRobotMovement(robot, seconds)).toList();
    }

    private Robot simulateRobotMovement(Robot robot, int seconds) {
        var unboundedNewPosition = robot.velocity().move(robot.position(), seconds);
        var newPosition = new Position(
                MathUtils.mod(unboundedNewPosition.x(), width),
                MathUtils.mod(unboundedNewPosition.y(), height));
        return new Robot(newPosition, robot.velocity());
    }

    public List<Robot> getRobots() {
        return simulatedRobotList != null ? simulatedRobotList : robotList;
    }

    public List<Integer> countRobotsPerQuadrant() {
        var robotCountByQuadrant = getRobots().stream()
                .map(Robot::position)
                .collect(Collectors.groupingBy(this::getQuadrant))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().size()));
        return IntStream.rangeClosed(1, 4)
                .map(quadrant -> robotCountByQuadrant.getOrDefault(quadrant, 0))
                .boxed()
                .toList();
    }

    private int getQuadrant(Position position) {
        int quadrantWidth = width / 2;
        int quadrantHeight = height / 2;
        if (position.x() == quadrantWidth || position.y() == quadrantHeight) {
            return 0;
        }
        int quadrant = position.x() < quadrantWidth ? 1 : 2;
        return position.y() < quadrantHeight ? quadrant : quadrant + 2;
    }

    public int findEasterEggSeconds() {
        long maxContiguous = 0;
        int maxContiguousSeconds = 0;
        for (int i = 1; i <= width * height; i++) {
            simulateMovements(i);
            var positions = simulatedRobotList.stream().map(Robot::position).collect(Collectors.toSet());
            var contiguousPositions = countContiguousPositions(positions);
            if (contiguousPositions > maxContiguous) {
                maxContiguous = contiguousPositions;
                maxContiguousSeconds = i;
            }
        }
        return maxContiguousSeconds;
    }

    private long countContiguousPositions(Set<Position> positions) {
        return positions.stream()
                .mapToLong(position -> Direction.all().stream()
                        .map(d -> d.move(position))
                        .filter(positions::contains)
                        .count())
                .sum();
    }
}
