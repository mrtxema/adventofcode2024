import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SolverTest {

    @Test
    void testPart1() {
        long result = initSolver().part1();
        assertThat(result).isEqualTo(2);
    }

    @Test
    void testPart2() {
        long result = initSolver().part2();
        assertThat(result).isEqualTo(4);
    }

    private Solver initSolver() {
        return new Solver("test.txt").parseFile();
    }
}
