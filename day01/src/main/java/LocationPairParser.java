import common.parser.SimpleParser;

public class LocationPairParser extends SimpleParser<LocationPair> {

    @Override
    protected LocationPair nonNullParse(String line) {
        String[] parts = line.split("\\s+", 2);
        return new LocationPair(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
    }
}
