import common.parser.SimpleParser;
import common.parser.StringUtils;

import java.util.Arrays;

public class EquationParser extends SimpleParser<Equation> {

    @Override
    protected Equation nonNullParse(String line) {
        var split = StringUtils.split(line, ":", true);
        var operands = Arrays.stream(split.tail().split(" ")).map(Long::valueOf).toList();
        return new Equation(Long.parseLong(split.head()), operands);
    }
}
