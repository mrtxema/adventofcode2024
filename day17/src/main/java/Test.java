public class Test {

    public static void main(String[] args) {
        run(65804993);
    }

    public static void run(long a) {
        long b, c;
        while (a != 0) {
            b = a & 0x7;
            b = b ^ 1;
            c = a >> b;
            b = b ^ 4;
            a = a >> 3;
            b = b ^ c;
            System.out.printf("%d,", b & 0x7);
        }
    }
}
