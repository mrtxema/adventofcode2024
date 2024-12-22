import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SolverTest {

    @Test
    void testPart1() {
        long result = initSolver("test.txt").part1();
        assertThat(result).isEqualTo(37327623);
    }

    @Test
    void testPart2() {
        long result = initSolver("test2.txt").part2();
        assertThat(result).isEqualTo(23);
    }

    private Solver initSolver(String fileName) {
        return new Solver(fileName).parseFile();
    }
}
