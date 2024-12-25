import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SolverTest {

    @Test
    void testPart1() {
        long result = initSolver().part1();
        assertThat(result).isEqualTo(3);
    }

    private Solver initSolver() {
        return new Solver("test.txt").parseFile();
    }
}
