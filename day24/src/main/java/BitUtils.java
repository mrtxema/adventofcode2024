import java.util.HashMap;
import java.util.Map;

public final class BitUtils {

    private BitUtils() {
    }

    public static Map<String,Integer> getBitValues(long valueArg, String prefix) {
        var result = new HashMap<String, Integer>();
        long value = valueArg;
        for (int i = 0; i <= 44; i++) {
            result.put("%s%02d".formatted(prefix, i), (int) value & 0x1);
            value = value >> 1;
        }
        return result;
    }

    public static long buildLong(Map<String, Integer> bits, String prefix) {
        return bits.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(prefix))
                .sorted(Map.Entry.<String, Integer>comparingByKey().reversed())
                .map(Map.Entry::getValue)
                .mapToLong(n -> n)
                .reduce(0L, (result, n) -> (result << 1) | n);
    }
}
