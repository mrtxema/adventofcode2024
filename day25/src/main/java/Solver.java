import common.parser.IOUtils;
import java.util.stream.IntStream;

public class Solver {
    private final String fileName;
    private final SchematicsParser schematicsParser;
    private Schematics schematics;

    public Solver(String fileName) {
        this.fileName = fileName;
        this.schematicsParser = new SchematicsParser();
    }

    public Solver parseFile() {
        schematics = schematicsParser.parse(IOUtils.readTrimmedLines(getClass().getResource(fileName)));
        return this;
    }

    public long part1() {
        long count = 0;
        for (var lock : schematics.locks()) {
            for (var key : schematics.keys()) {
                if (fit(lock, key)) {
                    count++;
                }
            }
        }
        return count;
    }

    private boolean fit(Lock lock, Key key) {
        return IntStream.range(0, lock.heights().size())
                .map(i -> lock.heights().get(i) + key.heights().get(i))
                .allMatch(sum -> sum <= 5);
    }
}
