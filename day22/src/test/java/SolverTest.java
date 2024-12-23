import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SolverTest {

    @Test
    void testPart1() {
        assertThat(initSolver("test.txt").part1()).isEqualTo(37327623);
        assertThat(initSolver("input.txt").part1()).isEqualTo(18525593556L);
    }

    @Test
    void testPart2() {
        assertThat(initSolver("test2.txt").part2()).isEqualTo(23);
        assertThat(initSolver("input.txt").part2()).isEqualTo(2089);
    }

    private Solver initSolver(String fileName) {
        return new Solver(fileName).parseFile();
    }
}
