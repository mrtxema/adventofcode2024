import java.util.List;

public record BasicStone(long value) implements Stone {

    @Override
    public List<Stone> blink(StoneFactory stoneFactory) {
        if (value == 0) {
            return List.of(stoneFactory.create(1));
        }
        var valueStr = String.valueOf(value);
        if (valueStr.length() % 2 == 0) {
            return List.of(
                    stoneFactory.create(Long.parseLong(valueStr.substring(0, valueStr.length() / 2))),
                    stoneFactory.create(Long.parseLong(valueStr.substring(valueStr.length() / 2))));
        }
        return List.of(stoneFactory.create(value * 2024));
    }

    @Override
    public long count(StoneFactory stoneFactory) {
        return 1;
    }
}
