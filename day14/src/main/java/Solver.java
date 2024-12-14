import common.parser.IOUtils;
import java.util.List;

public class Solver {
    private final String fileName;
    private final int width;
    private final int height;
    private final RobotParser robotParser;
    private List<Robot> robotList;

    public Solver(String fileName, int width, int height) {
        this.fileName = fileName;
        this.width = width;
        this.height = height;
        this.robotParser = new RobotParser();
    }

    public Solver parseFile() {
        robotList = IOUtils.readTrimmedLines(getClass().getResource(fileName), robotParser);
        return this;
    }

    public long part1() {
        var robotMap = getRobotMap();
        robotMap.simulateMovements(100);
        return robotMap.countRobotsPerQuadrant().stream().reduce((a, b) -> a * b).orElse(0);
    }

    public long part2() {
        return getRobotMap().findEasterEggSeconds();
    }

    public RobotMap getRobotMap() {
        return new RobotMap(robotList, width, height);
    }
}
