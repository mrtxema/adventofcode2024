import java.util.HashMap;
import java.util.Map;

public class SequenceCalculator {
    private final Map<SequenceKey, Long> sequenceCache = new HashMap<>();
    private final Map<ButtonPair, String> paths = buildPathMap();

    private Map<ButtonPair, String> buildPathMap() {
        Map<ButtonPair, String> result = new HashMap<>();
        result.put(new ButtonPair('<', '^'), ">^A");
        result.put(new ButtonPair('^', '<'), "v<A");
        result.put(new ButtonPair('<', 'v'), ">A");
        result.put(new ButtonPair('v', '<'), "<A");
        result.put(new ButtonPair('<', '>'), ">>A");
        result.put(new ButtonPair('>', '<'), "<<A");
        result.put(new ButtonPair('<', 'A'), ">>^A");
        result.put(new ButtonPair('A', '<'), "v<<A");
        result.put(new ButtonPair('^', 'v'), "vA");
        result.put(new ButtonPair('v', '^'), "^A");
        result.put(new ButtonPair('^', '>'), "v>A");
        result.put(new ButtonPair('>', '^'), "<^A");
        result.put(new ButtonPair('^', 'A'), ">A");
        result.put(new ButtonPair('A', '^'), "<A");
        result.put(new ButtonPair('v', '>'), ">A");
        result.put(new ButtonPair('>', 'v'), "<A");
        result.put(new ButtonPair('v', 'A'), "^>A");
        result.put(new ButtonPair('A', 'v'), "<vA");
        result.put(new ButtonPair('>', 'A'), "^A");
        result.put(new ButtonPair('A', '>'), "vA");
        return result;
    }

    public long getSequenceLength(String targetSequence, int depth) {
        SequenceKey key = new SequenceKey(targetSequence, depth);
        if (sequenceCache.containsKey(key)) {
            return sequenceCache.get(key);
        }

        long length = 0;
        if (depth == 0) {
            length = targetSequence.length();
        } else {
            char current = 'A';
            for (char next : targetSequence.toCharArray()) {
                long len = getMoveCount(current, next, depth);
                current = next;
                length += len;
            }
        }

        sequenceCache.put(key, length);
        return length;
    }

    private long getMoveCount(char current, char next, int depth) {
        if (current == next) {
            return 1;
        }
        String newSequence = paths.get(new ButtonPair(current, next));
        return getSequenceLength(newSequence, depth - 1);
    }

    private record SequenceKey(String sequence, int depth) {
    }

    private record ButtonPair(char first, char second) {
    }
}
