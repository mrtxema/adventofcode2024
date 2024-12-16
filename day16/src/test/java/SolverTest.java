import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SolverTest {

    @Test
    void testPart1() {
        assertThat(initSolver("test.txt").part1()).isEqualTo(7036);
        assertThat(initSolver("test2.txt").part1()).isEqualTo(11048);
    }

    @Test
    void testPart2() {
        assertThat(initSolver("test.txt").part2()).isEqualTo(45);
        assertThat(initSolver("test2.txt").part2()).isEqualTo(64);
    }

    private Solver initSolver(String fileName) {
        return new Solver(fileName).parseFile();
    }
}
