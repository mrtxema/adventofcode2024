import common.parser.CharGrid;
import common.parser.IOUtils;
import java.util.Collection;

public class Solver {
    private final String fileName;
    private AntennaMap antennaMap;

    public Solver(String fileName) {
        this.fileName = fileName;
    }

    public Solver parseFile() {
        antennaMap = new AntennaMap(new CharGrid(IOUtils.readCharMap(getClass().getResource(fileName))));
        return this;
    }

    public long part1() {
        return countAntinodes(false);
    }

    public long part2() {
        return countAntinodes(true);
    }

    private long countAntinodes(boolean includeResonants) {
        return antennaMap.getAllAntennaPositionsByFrequency().values().stream()
                .map(positions -> antennaMap.findAntinodes(positions, includeResonants))
                .flatMap(Collection::stream)
                .distinct()
                .count();
    }
}
