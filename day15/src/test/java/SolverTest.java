import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SolverTest {

    @Test
    void testPart1() {
        assertThat(initSolver("test.txt").part1()).isEqualTo(10092);
        assertThat(initSolver("test2.txt").part1()).isEqualTo(2028);
    }

    @Test
    void testPart2() {
        assertThat(initSolver("test.txt").part2()).isEqualTo(9021);
    }

    private Solver initSolver(String filename) {
        return new Solver(filename).parseFile();
    }
}
