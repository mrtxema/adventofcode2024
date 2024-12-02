import common.parser.SimpleParser;

import java.util.Arrays;

public class ReportParser extends SimpleParser<Report> {

    @Override
    protected Report nonNullParse(String line) {
        return new Report(Arrays.stream(line.split("\\s+")).map(Integer::valueOf).toList());
    }
}
