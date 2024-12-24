import common.parser.SimpleParser;

public class NetworkConnectionParser extends SimpleParser<NetworkConnection> {

    @Override
    protected NetworkConnection nonNullParse(String line) {
        var parts = line.split("-", 2);
        return new NetworkConnection(parts[0], parts[1]);
    }
}
