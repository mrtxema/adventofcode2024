import common.parser.IOUtils;
import java.util.Arrays;
import java.util.List;

public class Solver {
    private final String fileName;
    private final StoneFactory stoneFactory;
    private List<Long> stones;

    public Solver(String fileName) {
        this.fileName = fileName;
        this.stoneFactory = new StoneFactory();
    }

    public Solver parseFile() {
        stones = Arrays.stream(IOUtils.readTrimmedLines(getClass().getResource(fileName)).get(0).split(" "))
                .map(Long::valueOf)
                .toList();
        return this;
    }

    public long part1() {
        var arrangement = new StoneArrangement(stones.stream().map(stoneFactory::create).toList());
        return blink(arrangement, 25).countStones(stoneFactory);
    }

    public long part2() {
        var arrangement = new StoneArrangement(stones.stream().map(stoneFactory::create).toList());
        return blink(arrangement, 75).countStones(stoneFactory);
    }

    private StoneArrangement blink(StoneArrangement arrangement, int times) {
        var arr = arrangement;
        for (int i = 0; i < times; i++) {
            arr = arr.blink(stoneFactory);
        }
        return arr;
    }
}
