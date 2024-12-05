package common.parser;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public final class IOUtils {

    private IOUtils() {
    }

    public static List<String> readLines(URL resource) throws ParsingException {
        return readLines(resource, Function.identity(), Optional::of);
    }

    public static List<String> readTrimmedLines(URL resource) throws ParsingException {
        return readLines(resource, String::trim, Optional::of);
    }

    public static <T> List<T> readLines(URL resource, Parser<T> parser) throws ParsingException {
        return readLines(resource, Function.identity(), parser);
    }

    public static <T> List<T> readTrimmedLines(URL resource, Parser<T> parser) throws ParsingException {
        return readLines(resource, String::trim, parser);
    }

    public static char[][] readCharMap(URL resource) {
        return readTrimmedLines(resource).stream().map(String::toCharArray).toArray(char[][]::new);
    }

    public static List<List<String>> splitSections(List<String> lines, int maxSections) {
        List<List<String>> result = new ArrayList<>();
        int index = 0;
        while (index < lines.size()) {
            var newSection = getNextSection(lines, index, result.size() < maxSections - 1);
            if (!newSection.content().isEmpty()) {
                result.add(newSection.content());
            }
            index = newSection.endIndex();
        }
        return result;
    }

    private static SectionResult getNextSection(List<String> lines, int startIndex, boolean stopOnEmptyLine) {
        int index = startIndex;
        boolean anyContent = false;
        List<String> newSection = new ArrayList<>();
        while (index < lines.size()) {
            var line = lines.get(index++);
            if (anyContent && line.isEmpty() && stopOnEmptyLine) {
                break;
            }
            anyContent |= !line.isEmpty();
            if (anyContent) {
                newSection.add(line);
            }
        }
        return new SectionResult(newSection, index);
    }

    private record SectionResult(List<String> content, int endIndex) {
    }

    private static <T> List<T> readLines(URL resource, Function<String, String> transformer, Parser<T> parser) throws ParsingException {
        try (Stream<String> lines = Files.lines(getFilePath(resource))) {
            return lines.map(transformer).map(parser::parse).flatMap(Optional::stream).toList();
        } catch (java.io.IOException e) {
            throw new ParsingException(e.getMessage(), e);
        }
    }

    private static Path getFilePath(URL resource) {
        if (resource == null) {
            throw new ParsingException("Missing file: " + resource);
        }
        try {
            return Paths.get(resource.toURI());
        } catch (URISyntaxException e) {
            throw new ParsingException(e.getMessage(), e);
        }
    }
}
