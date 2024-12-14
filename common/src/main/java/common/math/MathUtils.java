package common.math;

import java.math.BigInteger;
import java.util.OptionalInt;

public final class MathUtils {

    private MathUtils() {
    }

    public static long lcm(long la, long lb) {
        BigInteger a = BigInteger.valueOf(la);
        BigInteger b = BigInteger.valueOf(lb);
        return a.multiply(b.divide(b.gcd(a))).longValue();
    }

    public static int mod(int n, int modulo) {
        int result = n % modulo;
        return result < 0 ? modulo + result : result;
    }

    public static OptionalInt modInverse(int nArg, int moduloArg) {
        if (moduloArg == 1) {
            return OptionalInt.empty();
        }
        int modulo = moduloArg;
        int n = mod(nArg, modulo);
        int y = 0;
        int x = 1;
        while (n > 1) {
            int q = n / modulo;
            int t = modulo;
            modulo = n % modulo;
            n = t;
            t = y;
            y = x - q * y;
            x = t;
        }
        var result = mod(x, moduloArg);
        return result == 0 ? OptionalInt.empty() : OptionalInt.of(result);
    }
}
