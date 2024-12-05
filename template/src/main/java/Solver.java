import common.parser.IOUtils;
import java.util.List;

public class Solver {
    private final String fileName;
    private final MyDataParser myDataParser;
    private List<MyData> myDataList;

    public Solver(String fileName) {
        this.fileName = fileName;
        this.myDataParser = new MyDataParser();
    }

    public Solver parseFile() {
        myDataList = IOUtils.readTrimmedLines(getClass().getResource(fileName), myDataParser);
        return this;
    }

    public long part1() {
        return -1;
    }

    public long part2() {
        return -1;
    }
}
