package common.math;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class CombinationGenerator<T> implements Iterator<List<T>> {
    private final List<T> elementList;
    private final int[] state;

    public CombinationGenerator(int elementCount, List<T> elementList) {
        this.elementList = elementList;
        this.state = new int[elementCount];
    }

    @Override
    public boolean hasNext() {
        return state[0] < elementList.size();
    }

    @Override
    public List<T> next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more combinations");
        }
        var result = Arrays.stream(state).mapToObj(elementList::get).toList();
        prepareNextState(state.length - 1);
        return result;
    }

    private void prepareNextState(int index) {
        if (index == 0 || state[index] < elementList.size() - 1) {
            state[index]++;
        } else {
            state[index] = 0;
            prepareNextState(index - 1);
        }
    }
}
