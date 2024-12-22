import java.util.ArrayList;
import java.util.List;

public class SecretNumberGenerator {

    public PriceInfo generatePrices(long initialNumber, int count) {
        long number = initialNumber;
        List<Integer> prices = new ArrayList<>();
        List<Integer> diffs = new ArrayList<>();
        int lastPrice = (int) number % 10;
        prices.add(lastPrice);
        for (int i = 0; i < count; i++) {
            number = generateNext(number);
            int price = (int) number % 10;
            prices.add(price);
            diffs.add(price - lastPrice);
            lastPrice = price;
        }
        return new PriceInfo(prices, diffs);
    }

    public long generate(long initialNumber, int count) {
        long number = initialNumber;
        for (int i = 0; i < count; i++) {
            number = generateNext(number);
        }
        return number;
    }

    public long generateNext(long number) {
        number = prune(mix(number, number << 6));
        number = prune(mix(number, number >> 5));
        return prune(mix(number, number << 11));
    }

    private long mix(long secret, long n) {
        return secret ^ n;
    }

    private long prune(long n) {
        return n & 0xFFFFFF;
    }
}
