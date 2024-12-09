import java.util.Arrays;
import java.util.stream.IntStream;

public record DiskMap(int[] map) {
    private static final int FREE = -1;

    public static DiskMap fromDenseMap(String denseMap) {
        int maxLen = denseMap.length() * 9;
        int[] map = new int[maxLen];
        Arrays.fill(map, FREE);
        int denseIndex = 0;
        int mapIndex = 0;
        int fileIndex = 0;
        while (denseIndex < denseMap.length()) {
            int fileSize = denseMap.charAt(denseIndex++) - '0';
            mapIndex = writeFile(map, fileIndex++, mapIndex, fileSize);
            if (denseIndex < denseMap.length()) {
                int freeSize = denseMap.charAt(denseIndex++) - '0';
                mapIndex += freeSize;
            }
        }
        return new DiskMap(map);
    }

    private static int writeFile(int[] map, int fileId, int mapIndex, int fileSize) {
        for (int i = mapIndex; i < mapIndex + fileSize; i++) {
            map[i] = fileId;
        }
        return mapIndex + fileSize;
    }

    public void compactBlocks() {
        int left = 0;
        int right = map.length - 1;
        while (left < right) {
            while (map[right] == FREE && right > 0) right--;
            while (map[left] != FREE && left < map.length - 1) left++;
            if (left < right) {
                map[left] = map[right];
                map[right] = FREE;
            }
        }
    }

    public void compactFiles() {
        int left = 0;
        int right = map.length - 1;
        while (left < right) {
            while (map[right] == FREE && right > 0) right--;
            while (map[left] != FREE && left < map.length - 1) left++;
            if (left < right) {
                int fileStart = right;
                while (fileStart > 0 && map[fileStart-1] == map[right]) fileStart--;
                int fileSize = right - fileStart + 1;
                int freeBlockStart = findFreeBlock(fileSize, left, fileStart);
                if (freeBlockStart != -1) {
                    for (int i = 0; i < fileSize; i++) {
                        map[freeBlockStart + i] = map[fileStart + i];
                        map[fileStart + i] = FREE;
                    }
                }
                right = fileStart - 1;
            }
        }
    }

    private int findFreeBlock(int size, int startIndex, int endIndex) {
        int index = startIndex;
        while (index < endIndex) {
            if (IntStream.range(index, index + size).allMatch(i -> map[i] == FREE)) {
                return index;
            }
            while (map[index] == FREE && index < endIndex) index++;
            while (map[index] != FREE && index < endIndex) index++;
        }
        return -1;
    }

    public long checksum() {
        return IntStream.range(0, map.length).filter(i -> map[i] != FREE).mapToLong(i -> (long) i * map[i]).sum();
    }
}
