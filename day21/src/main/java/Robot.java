import common.movement.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Robot {
    private final Map<Character, Position> buttons;
    private final Position forbidden;
    private final Position start;

    public Robot(Map<Character, Position> buttons, Position forbidden) {
        this.buttons = buttons;
        this.forbidden = forbidden;
        this.start = buttons.get('A');
    }

    public List<String> findShortestSequences(String code) {
        List<String> result = new ArrayList<>();
        var position = start;
        for (int i = 0; i < code.length(); i++) {
            var targetPosition = buttons.get(code.charAt(i));
            var movements = findMovements(position, targetPosition);
            if (result.isEmpty()) {
                result.addAll(movements);
            } else {
                result = merge(result, movements);
            }
            position = targetPosition;
        }
        return result;
    }

    private List<String> merge(List<String> previous, List<String> movs) {
        List<String> allResults = new ArrayList<>();
        for (var prev : previous) {
            for (var m : movs) {
                allResults.add(prev + m);
            }
        }
        return allResults;
    }

    private List<String> findMovements(Position p1, Position p2) {
        int xDiff = p2.x() - p1.x();
        var xSymbol = xDiff > 0 ? ">" : "<";
        var xStr = xSymbol.repeat(Math.abs(xDiff));

        int yDiff = p2.y() - p1.y();
        var ySymbol = yDiff > 0 ? "v" : "^";
        var yStr = ySymbol.repeat(Math.abs(yDiff));

        if (xStr.isEmpty() || yStr.isEmpty()) {
            return List.of(xStr + yStr + "A");
        }
        if (p1.x() == forbidden.x() && p2.y() == forbidden.y()) {
            return List.of(xStr + yStr + "A");
        }
        if (p1.y() == forbidden.y() && p2.x() == forbidden.x()) {
            return List.of(yStr + xStr + "A");
        }
        return List.of(xStr + yStr + "A", yStr + xStr + "A");
    }
}
