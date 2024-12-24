import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SolverTest {

    @Test
    void testPart1() {
        assertThat(initSolver("test.txt").part1()).isEqualTo(4);
        assertThat(initSolver("test2.txt").part1()).isEqualTo(2024);
    }

    private Solver initSolver(String fileName) {
        return new Solver(fileName).parseFile();
    }
}
