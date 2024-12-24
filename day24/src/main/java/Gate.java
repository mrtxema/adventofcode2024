import java.util.Map;

public record Gate(String input1, String input2, Operator operator, String output) {

    public int resolve(Map<String, Integer> values) {
        return operator.resolve(values.get(input1), values.get(input2));
    }
}
