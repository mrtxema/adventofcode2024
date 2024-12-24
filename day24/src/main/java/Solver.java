import common.parser.IOUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Solver {
    private final String fileName;
    private final CircuitParser circuitParser;
    private Circuit circuit;

    public Solver(String fileName) {
        this.fileName = fileName;
        this.circuitParser = new CircuitParser();
    }

    public Solver parseFile() {
        circuit = circuitParser.parse(IOUtils.readTrimmedLines(getClass().getResource(fileName)));
        return this;
    }

    public long part1() {
        return circuit.resolve("z");
    }

    public String part2() {
        return findWireSwaps().stream().sorted().collect(Collectors.joining(","));
    }

    private long testFixedCircuit(long x, long y, List<WireSwap> swaps) {
        var initialValues = new HashMap<String, Integer>();
        initialValues.putAll(BitUtils.getBitValues(x, "x"));
        initialValues.putAll(BitUtils.getBitValues(y, "y"));
        var gates = new HashMap<>(circuit.gates());
        for (var swap : swaps) {
            var gate1 = gates.remove(swap.wire1());
            var gate2 = gates.remove(swap.wire2());
            var gate1Fixed = new Gate(gate1.input1(), gate1.input2(), gate1.operator(), gate2.output());
            var gate2Fixed = new Gate(gate2.input1(), gate2.input2(), gate2.operator(), gate1.output());
            gates.put(gate1Fixed.output(), gate1Fixed);
            gates.put(gate2Fixed.output(), gate2Fixed);
        }
        return new Circuit(initialValues, gates).resolve("z");
    }

    private List<String> findWireSwaps() {
        var invalidEndWires = circuit.gates().values().stream()
                .filter(g -> g.output().startsWith("z") && !g.output().equals("z45") && g.operator() != Operator.XOR)
                .toList();

        var invalidMidWires = circuit.gates().values().stream()
                .filter(g -> !g.output().startsWith("z") && g.operator() == Operator.XOR &&
                        !g.input1().startsWith("x") && !g.input1().startsWith("y") &&
                        !g.input2().startsWith("x") && !g.input2().startsWith("y"))
                .toList();

        var swaps = invalidMidWires.stream().map(wire -> {
            var toSwitch = invalidEndWires.stream().filter(it -> it.output().equals(circuit.findFirstOutputFrom(wire.output()))).findFirst().orElseThrow();
            return new WireSwap(wire.output(), toSwitch.output());
        }).toList();
        long x = ThreadLocalRandom.current().nextLong(0xFFFFFFFFFL);
        long y = ThreadLocalRandom.current().nextLong(0xFFFFFFFFFL);
        var diffFromActual = (x + y) ^ testFixedCircuit(x, y, swaps);
        var zeroBits = "%02d".formatted(Long.numberOfTrailingZeros(diffFromActual));
        var invalidCarryWires = circuit.gates().values().stream()
                .filter(g -> g.input1().endsWith(zeroBits) && g.input2().endsWith(zeroBits))
                .toList();

        var result = new ArrayList<String>();
        result.addAll(invalidEndWires.stream().map(Gate::output).toList());
        result.addAll(invalidMidWires.stream().map(Gate::output).toList());
        result.addAll(invalidCarryWires.stream().map(Gate::output).toList());
        return result;
    }

    record WireSwap(String wire1, String wire2) {
    }
}
