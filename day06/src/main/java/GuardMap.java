import common.movement.Position;
import common.parser.CharGrid;

public class GuardMap {
    private static final char OBSTRUCTION = '#';
    private final CharGrid grid;
    private Position newObstaclePosition;

    public GuardMap(CharGrid grid) {
        this.grid = grid;
    }

    public void setNewObstaclePosition(Position newObstaclePosition) {
        this.newObstaclePosition = newObstaclePosition;
    }

    public GuardState findGuardState() {
        for (int y = 0; y < grid.getMaxY(); y++) {
            for (int x = 0; x < grid.getMaxX(); x++) {
                var position = new Position(x, y);
                var optDirection = grid.getChar(position).flatMap(GuardDirection::fromSymbol);
                if (optDirection.isPresent()) {
                    return new GuardState(position, optDirection.get());
                }
            }
        }
        throw new IllegalStateException("Missing guard position");
    }

    public boolean isInsideLimits(GuardState state) {
        var p = state.position();
        return p.x() >= 0 && p.x() < grid.getMaxX() && p.y() >= 0 && p.y() < grid.getMaxY();
    }

    public GuardState getNextState(GuardState state) {
        var nextPosition = state.direction().getDirection().move(state.position());
        if (isObstruction(nextPosition)) {
            return getNextState(new GuardState(state.position(), state.direction().turnRight()));
        }
        return new GuardState(nextPosition, state.direction());
    }

    private boolean isObstruction(Position position) {
        return position.equals(newObstaclePosition) || grid.getChar(position).filter(c -> c == OBSTRUCTION).isPresent();
    }
}
