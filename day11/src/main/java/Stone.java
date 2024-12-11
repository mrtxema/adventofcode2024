import java.util.List;

public interface Stone {

    List<Stone> blink(StoneFactory stoneFactory);

    long count(StoneFactory stoneFactory);
}
