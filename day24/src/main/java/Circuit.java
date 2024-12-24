import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public record Circuit(Map<String, Integer> initialValues, Map<String, Gate> gates) {

    public long resolve(String wirePrefix) {
        Map<String, Integer> values = new HashMap<>(initialValues);
        List<String> pendingWires = new ArrayList<>(gates.keySet().stream()
                .filter(wire -> wire.startsWith(wirePrefix))
                .toList());
        while (!pendingWires.isEmpty()) {
            String wire = pendingWires.remove(pendingWires.size() - 1);
            resolveGate(gates.get(wire), values);
        }
        return BitUtils.buildLong(values, wirePrefix);
    }

    private void resolveGate(Gate gate, Map<String, Integer> values) {
        if (!values.containsKey(gate.input1())) {
            resolveGate(gates.get(gate.input1()), values);
        }
        if (!values.containsKey(gate.input2())) {
            resolveGate(gates.get(gate.input2()), values);
        }
        values.put(gate.output(), gate.resolve(values));
    }

    public String findFirstOutputFrom(String wire) {
        var parents = gates.values().stream().filter(it -> it.input1().equals(wire) || it.input2().equals(wire)).toList();

        var validOutput = parents.stream().filter(it -> it.output().startsWith("z")).findAny().orElse(null);
        if (validOutput == null) {
            return parents.stream()
                    .map(it -> findFirstOutputFrom(it.output()))
                    .filter(Objects::nonNull)
                    .findFirst()
                    .orElse(null);
        }

        var previousOutputNumber = Integer.parseInt(validOutput.output().substring(1)) - 1;
        return "z%02d".formatted(previousOutputNumber);
    }
}
