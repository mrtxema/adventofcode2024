import common.movement.Direction;
import common.movement.Position;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class TopographicMap {
    private static final int TRAILHEAD_HEIGHT = 0;
    private static final int PEAK_HEIGHT = 9;
    private final int[][] heights;

    public TopographicMap(int[][] heights) {
        this.heights = heights;
    }

    public int getHeight(Position position) {
        return heights[position.y()][position.x()];
    }

    public boolean isValidPosition(Position position) {
        if (position.y() < 0 || position.y() >= heights.length) {
            return false;
        }
        return position.x() >= 0 && position.x() < heights[position.y()].length;
    }

    public List<Position> getTrailheads() {
        List<Position> result = new ArrayList<>();
        for (int y = 0; y < heights.length; y++) {
            for (int x = 0; x < heights[y].length; x++) {
                var position = new Position(x, y);
                if (getHeight(position) == TRAILHEAD_HEIGHT) {
                    result.add(position);
                }
            }
        }
        return result;
    }

    public int getTrailheadScore(Position trailhead) {
        return new HashSet<>(getReachedPeaks(trailhead)).size();
    }

    public int getTrailheadRating(Position trailhead) {
        return getReachedPeaks(trailhead).size();
    }

    private List<Position> getReachedPeaks(Position start) {
        int currentHeight = getHeight(start);
        if (currentHeight == PEAK_HEIGHT) {
            return List.of(start);
        }
        return Direction.straight().stream()
                .map(direction -> direction.move(start))
                .filter(this::isValidPosition)
                .filter(position -> getHeight(position) == currentHeight + 1)
                .map(this::getReachedPeaks)
                .flatMap(Collection::stream)
                .toList();
    }
}
