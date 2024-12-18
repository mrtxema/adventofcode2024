import java.util.List;

public record ExecutionSpec(long registerA, long registerB, long registerC, List<Integer> program) {
}
