import common.parser.CharGrid;
import common.parser.IOUtils;

public class Solver {
    private final String fileName;
    private final int minPicoSeconds;
    private RaceTrack raceTrack;

    public Solver(String fileName, int minPicoSeconds) {
        this.fileName = fileName;
        this.minPicoSeconds = minPicoSeconds;
    }

    public Solver parseFile() {
        raceTrack = new RaceTrack(new CharGrid(IOUtils.readCharMap(getClass().getResource(fileName))));
        return this;
    }

    public long part1() {
        return raceTrack.countCheats(minPicoSeconds, 2);
    }

    public long part2() {
        return raceTrack.countCheats(minPicoSeconds, 20);
    }
}
