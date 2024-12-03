import common.parser.SimpleParser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class InstructionListParser extends SimpleParser<List<Instruction>> {
    private final Pattern mulParser = Pattern.compile("mul\\([0-9]{1,3},[0-9]{1,3}\\)|do\\(\\)|don't\\(\\)");

    @Override
    protected List<Instruction> nonNullParse(String line) {
        var matcher = mulParser.matcher(line);
        List<Instruction> result = new ArrayList<>();
        while (matcher.find()) {
            result.add(parseInstruction(matcher.group()));
        }
        return result;
    }

    private Instruction parseInstruction(String spec) {
        return switch (spec.substring(0, 3)) {
            case "mul" -> parseMultiplication(spec);
            case "do(" -> new Enable();
            case "don" -> new Disable();
            default -> throw new IllegalArgumentException("Unknown instruction: " + spec);
        };
    }

    private Multiplication parseMultiplication(String spec) {
        var parts = spec.substring(4, spec.length() - 1).split(",", 2);
        return new Multiplication(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
    }
}
