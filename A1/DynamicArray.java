
/**
 * A simple implementation of a dynamic array.
 * @param <J> the type of elements stored in the array
 */
public class DynamicArray<J> implements DynamicArrayADT<J> {
    private J[] data;
    private int size;

    /**
     * Constructs an empty DynamicArray with the specified initial capacity.
     * @param initialCapacity the initial capacity of the array
     * @throws ArrayIndexOutOfBoundsException if initialCapacity is negative
     */
    public DynamicArray(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new ArrayIndexOutOfBoundsException("Initial capacity cannot be negative.");
        }
        data = allocate(initialCapacity);
        size = 0;
    }

    /**
     * Constructs a DynamicArray as a deep copy of another DynamicArray.
     * @param other the DynamicArray to copy
     */
    public DynamicArray(DynamicArray<J> other) {
        data = allocate(other.data.length);
        size = other.size;
        for (int i = 0; i < size; i++) {
            data[i] = other.data[i];
        }
    }

    @SuppressWarnings("unchecked")
    private J[] allocate(int len) {
        return (J[]) new Object[len];
    }

    private void resize() {
        int newCapacity = data.length == 0 ? 1 : data.length * 2;
        J[] newData = allocate(newCapacity);
        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
        }
        data = newData;
    }

    @Override
    public J get(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException("Index: " + index + " is out of bounds.");
        }
        return data[index];
    }

    @Override
    public J set(int index, J value) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException("Index: " + index + " is out of bounds.");
        }
        J oldValue = data[index];
        data[index] = value;
        return oldValue;
    }

    @Override
    public void add(int index, J value) {
        if (index < 0 || index > size) {
            throw new ArrayIndexOutOfBoundsException("Index: " + index + " is out of bounds.");
        }
        if (size == data.length) {
            resize();
        }
        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
        }
        data[index] = value;
        size++;
    }

    @Override
    public void add(J value) {
        if (size == data.length) {
            resize();
        }
        data[size] = value;
        size++;
    }

    @Override
    public J remove(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException("Index: " + index + " is out of bounds.");
        }
        J removedValue = data[index];
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }
        data[size - 1] = null;
        size--;
        return removedValue;
    }

    @Override
    public DynamicArray<J> delete(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size || fromIndex > toIndex) {
            throw new ArrayIndexOutOfBoundsException("Invalid fromIndex or toIndex.");
        }
        DynamicArray<J> newArray = new DynamicArray<>(size - (toIndex - fromIndex));
        for (int i = 0; i < fromIndex; i++) {
            newArray.add(data[i]);
        }
        for (int i = toIndex; i < size; i++) {
            newArray.add(data[i]);
        }
        return newArray;
    }

    @Override
    public DynamicArray<J> append(DynamicArrayADT<J> other) {
        DynamicArray<J> newArray = new DynamicArray<>(this.size + other.size());
        for (int i = 0; i < this.size; i++) {
            newArray.add(this.data[i]);
        }
        for (int i = 0; i < other.size(); i++) {
            newArray.add(other.get(i));
        }
        return newArray;
    }

    @Override
    public DynamicArray<J> insert(int index, DynamicArrayADT<J> other) {
        if (index < 0 || index > size) {
            throw new ArrayIndexOutOfBoundsException("Index: " + index + " is out of bounds.");
        }
        DynamicArray<J> newArray = new DynamicArray<>(this.size + other.size());
        for (int i = 0; i < index; i++) {
            newArray.add(this.data[i]);
        }
        for (int i = 0; i < other.size(); i++) {
            newArray.add(other.get(i));
        }
        for (int i = index; i < this.size; i++) {
            newArray.add(this.data[i]);
        }
        return newArray;
    }

    @Override
    public DynamicArray<J> sublist(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size || fromIndex > toIndex) {
            throw new ArrayIndexOutOfBoundsException("Invalid fromIndex or toIndex.");
        }
        DynamicArray<J> newArray = new DynamicArray<>(toIndex - fromIndex);
        for (int i = fromIndex; i < toIndex; i++) {
            newArray.add(this.data[i]);
        }
        return newArray;
    }

    @Override
    public DynamicArray<J> extract(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size || fromIndex > toIndex) {
            throw new ArrayIndexOutOfBoundsException("Invalid fromIndex or toIndex.");
        }
        DynamicArray<J> newArray = new DynamicArray<>(toIndex - fromIndex);
        for (int i = fromIndex; i < toIndex; i++) {
            newArray.add(this.data[i]);
        }
        int numToRemove = toIndex - fromIndex;
        for (int i = fromIndex; i < size - numToRemove; i++) {
            data[i] = data[i + numToRemove];
        }
        for (int i = size - numToRemove; i < size; i++) {
            data[i] = null;
        }
        size -= numToRemove;
        return newArray;
    }

    @Override
    public DynamicArrayADT<J> splitPrefix(int index) {
        if (index < 0 || index > size) {
            throw new ArrayIndexOutOfBoundsException("Invalid index.");
        }
        DynamicArray<J> prefix = new DynamicArray<>(index);
        for (int i = 0; i < index; i++) {
            prefix.add(data[i]);
        }
        for (int i = index; i < size; i++) {
            data[i - index] = data[i];
        }
        for (int i = size - index; i < size; i++) {
            data[i] = null;
        }
        size -= index;
        return prefix;
    }

    @Override
    public DynamicArrayADT<J> splitSuffix(int index) {
        if (index < 0 || index > size) {
            throw new ArrayIndexOutOfBoundsException("Invalid index.");
        }
        DynamicArray<J> suffix = new DynamicArray<>(size - index);
        for (int i = index; i < size; i++) {
            suffix.add(data[i]);
            data[i] = null;
        }
        size = index;
        return suffix;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int lowIndex() {
        return 0;
    }

    @Override
    public int highIndex() {
        return size - 1;
    }

    @Override
    public boolean indexInRange(int index) {
        return index >= lowIndex() && index <= highIndex();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(data[i]);
            if (i < size - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}
