import common.movement.Position;
import common.parser.CharGrid;
import common.parser.IOUtils;

import java.util.HashSet;
import java.util.Set;

public class Solver {
    private final String fileName;
    private GuardMap map;

    public Solver(String fileName) {
        this.fileName = fileName;
    }

    public Solver parseFile() {
        map = new GuardMap(new CharGrid(IOUtils.readCharMap(getClass().getResource(fileName))));
        return this;
    }

    public long part1() {
        return findVisitedPositions(map.findGuardState()).size();
    }

    public long part2() {
        var initialState = map.findGuardState();
        var visitedPositions = findVisitedPositions(initialState);
        visitedPositions.remove(initialState.position());
        return visitedPositions.stream().filter(p -> isLoopPosition(initialState, p)).count();
    }

    private Set<Position> findVisitedPositions(GuardState initialState) {
        var state = initialState;
        Set<Position> visitedPositions = new HashSet<>();
        while (map.isInsideLimits(state)) {
            visitedPositions.add(state.position());
            state = map.getNextState(state);
        }
        return visitedPositions;
    }

    private boolean isLoopPosition(GuardState initialState, Position newObstaclePosition) {
        map.setNewObstaclePosition(newObstaclePosition);
        var state = initialState;
        Set<GuardState> visitedStates = new HashSet<>();
        while (visitedStates.add(state) && map.isInsideLimits(state)) {
            state = map.getNextState(state);
        }
        return map.isInsideLimits(state);
    }
}
