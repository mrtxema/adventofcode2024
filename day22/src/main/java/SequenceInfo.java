import java.util.List;

public record SequenceInfo(List<Integer> sequence, long monkeys) {
    public static final SequenceInfo EMPTY = new SequenceInfo(List.of(), 0);

    public static SequenceInfo merge(SequenceInfo s1, SequenceInfo s2) {
        if (!s1.sequence().equals(s2.sequence()) && s1 != EMPTY && s2 != EMPTY) {
            throw new IllegalArgumentException("Sequences mismatch: %s, %s".formatted(s1, s2));
        }
        return new SequenceInfo(s1.sequence().isEmpty() ? s2.sequence() : s1.sequence(), s1.monkeys() + s2.monkeys());
    }
}
