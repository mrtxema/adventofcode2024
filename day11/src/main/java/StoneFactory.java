import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StoneFactory {
    private final Map<Long, CachedStone> cachedStones = new ConcurrentHashMap<>();

    public Stone create(long value) {
        CachedStone cachedStone = cachedStones.computeIfAbsent(value, CachedStone::new);
        return new OptimizedStone(cachedStone);
    }
}
