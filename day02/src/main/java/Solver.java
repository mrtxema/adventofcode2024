import common.parser.IOUtils;

import java.util.List;

public class Solver {
    private final String fileName;
    private final ReportParser reportParser;
    private List<Report> reports;

    public Solver(String fileName) {
        this.fileName = fileName;
        this.reportParser = new ReportParser();
    }

    public Solver parseFile() {
        reports = IOUtils.readTrimmedLines(getClass().getResource(fileName), reportParser);
        return this;
    }

    public long part1() {
        return reports.stream().filter(Report::isSafe).count();
    }

    public long part2() {
        return reports.stream().filter(Report::isSafeWithDampener).count();
    }
}
