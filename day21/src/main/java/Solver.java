import common.parser.IOUtils;
import java.util.List;

public class Solver {
    private final String fileName;
    private List<String> codes;

    public Solver(String fileName) {
        this.fileName = fileName;
    }

    public Solver parseFile() {
        codes = IOUtils.readTrimmedLines(getClass().getResource(fileName));
        return this;
    }

    public long part1() {
        return calculateComplexities(2);
    }

    public long part2() {
        return calculateComplexities(25);
    }

    private long calculateComplexities(int numRobots) {
        var doorRobot = new DoorRobot();
        var calculator = new SequenceCalculator();
        long result = 0;
        for (var code : codes) {
            long shortestSequenceLength = Long.MAX_VALUE;
            for (var secondRobotSequence : doorRobot.findShortestSequences(code)) {
                long length = calculator.getSequenceLength(secondRobotSequence, numRobots);
                shortestSequenceLength = Math.min(shortestSequenceLength, length);
            }
            result += shortestSequenceLength * Long.parseLong(code.substring(0, code.length() - 1));
        }
        return result;
    }
}
