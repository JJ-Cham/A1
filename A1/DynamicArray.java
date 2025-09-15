

public class DynamicArray<J> implements DynamicArrayADT<J> {
    private J[] data;
    private int size;

    /**
     * Doubles the capacity of the internal array when full.
     */
    //@SuppressWarnings("unchecked")
    private void resize() {
        int newCapacity = data.length == 0 ? 1 : data.length * 2;
        J[] newData = allocate(newCapacity);
        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
        }
        data = newData;
    }

    /**
     * Allocates a new array of the given length.
     * @param len the length of the new array
     * @return the new array
     */
    @SuppressWarnings("unchecked")
    private J[] allocate(int len) {
        return (J[]) new Object[len];
    }

    /**
     * Constructs an empty DynamicArray with the specified initial capacity.
     * @param initialCapacity the initial capacity of the array
     * @throws IllegalArgumentException if initialCapacity is negative
     */
    public DynamicArray(int initialCapacity){
        data = allocate(initialCapacity);
        size = 0;
    }

    /**
     * Constructs a DynamicArray as a copy of another DynamicArray.
     * @param other the DynamicArray to copy
     */
    public DynamicArray(DynamicArray<J> other){
        data = allocate(other.data.length);
        size = other.size;
        for (int i = 0; i < size; i++) {
            data[i] = other.data[i];
        }
    }

    /**
     * Returns the element at the specified index.
     * @param index the index of the element to retrieve
     * @return the element at the specified index
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= size)
     */
    @Override
    public J get(int index){
        if (index < 0 || index >= size){
            throw new IndexOutOfBoundsException("Index:"+ index + "is out of bounds.");
        }
        return data[index];
    }

    /**
     * Replaces the element at the specified index with the given value.
     * @param index the index of the element to replace
     * @param value the new value to store
     * @return the previous value at the specified index
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= size)
     */
    @Override
    public J set(int index, J value){
        if (index < 0 || index >= size){
            throw new IndexOutOfBoundsException("Index:"+ index + "is out of bounds.");
        }
        J oldValue = data[index];
        data[index] = value;
        return oldValue;
    }

    /**
     * Inserts the specified value at the given index, shifting subsequent elements to the right.
     * @param index the index at which to insert the value
     * @param value the value to insert
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index > size)
     */
    @Override 
    public void add(int index, J value){
        if (index < 0 || index > size){
            throw new IndexOutOfBoundsException("Index:"+ index + "is out of bounds.");
        }
        if (size == data.length){
            resize();
        }
        for (int i = size; i > index; i--){
            data[i] = data[i-1];
        }
        data[index] = value;
        size++;
    }

    /**
     * Appends the specified value to the end of the array.
     * @param value the value to append
     */
    @Override
    public void add(J value){
        if (size == data.length){
            resize();
        }
        data[size] = value;
        size++;
    }

    /**
     * Removes and returns the element at the specified index, shifting subsequent elements to the left.
     * @param index the index of the element to remove
     * @return the removed element
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= size)
     */
    @Override
    public J remove(int index){
        if (index < 0 || index >= size){
            throw new IndexOutOfBoundsException("Index:"+ index + "is out of bounds.");
        }
        J removedValue = data[index];
        for (int i = index; i < size - 1; i++){
            data[i] = data[i+1];
        }
        data[size - 1] = null; // Optional: clear the last element
        size--;
        return removedValue;
    }

    /**
     * Returns a new DynamicArray with elements from the current array excluding the range [fromIndex, toIndex).
     * @param fromIndex the starting index of the range to delete (inclusive)
     * @param toIndex the ending index of the range to delete (exclusive)
     * @return a new DynamicArray with the specified range removed
     * @throws IndexOutOfBoundsException if indices are invalid or out of range
     */
    @Override
    public DynamicArray<J> delete(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size || fromIndex >= toIndex) {
            throw new IndexOutOfBoundsException("Invalid fromIndex or toIndex.");
        }
        DynamicArray<J> newArray = new DynamicArray<J>(size - (toIndex - fromIndex));
        for (int i = 0; i < fromIndex; i++) {
            newArray.add(data[i]);
        }
        for (int i = toIndex; i < size; i++) {
            newArray.add(data[i]);
        }
        return newArray;
    }

    /**
     * Returns the number of elements currently stored in the array.
     * @return the size of the array
     */
    @Override
    public int size(){
        return size;
    }

    /**
     * Returns a new DynamicArray that is the result of appending another DynamicArray to the current one.
     * @param other the DynamicArray to append
     * @return a new DynamicArray containing all elements from both arrays
     */
    @Override
    public DynamicArray<J> append(DynamicArrayADT<J> other){
        DynamicArray<J> newArray = new DynamicArray<J>(this.size + other.size());
        for (int i = 0; i < this.size; i++){
            newArray.add(this.data[i]);
        }
        for (int i = 0; i < other.size(); i++){
            newArray.add(other.get(i));
        }
        return newArray;
    }


    /**
     * Returns a new DynamicArray with the elements of another array inserted at the specified index.
    * @param other the DynamicArray to insert
     * @param index the index at which to insert the other array
     * @return a new DynamicArray with the inserted elements
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index > size)
     */
    @Override
    public DynamicArray<J> insert(DynamicArrayADT<J> other, int index){
        if (index < 0 || index > size){
            throw new IndexOutOfBoundsException("Index:"+ index + "is out of bounds.");
        }
        DynamicArray<J> newArray = new DynamicArray<J>(this.size + other.size());
        for (int i = 0; i < index; i++){
            newArray.add(this.data[i]);
        }
        for (int i = 0; i < other.size(); i++){
            newArray.add(other.get(i));
        }
        for (int i = index; i < this.size; i++){
            newArray.add(this.data[i]);
        }
        return newArray;
    }

    /**
     * Returns a new DynamicArray containing elements in the range [fromIndex, toIndex).
     * @param fromIndex the starting index (inclusive)
     * @param toIndex the ending index (exclusive)
     * @return a new DynamicArray with the specified sublist
     * @throws IndexOutOfBoundsException if indices are invalid or out of range
     */
    @Override
    public DynamicArray<J> sublist(int fromIndex, int toIndex){
        if (fromIndex < 0 || toIndex > size || fromIndex >= toIndex){
            throw new IndexOutOfBoundsException("Invalid fromIndex or toIndex.");
        }
        DynamicArray<J> newArray = new DynamicArray<J>(toIndex - fromIndex);
        for (int i = fromIndex; i < toIndex; i++){
            newArray.add(this.data[i]);
        }
        return newArray;
    }

    @Override 
    public DynamicArray<J> extract(int fromIndex, int toIndex){
        if (fromIndex < 0 || toIndex > size || fromIndex >= toIndex){
            throw new IndexOutOfBoundsException("Invalid fromIndex or toIndex.");
        }
        DynamicArray<J> newArray = new DynamicArray<J>(toIndex - fromIndex);
        for (int i = fromIndex; i < toIndex; i++){
            newArray.add(this.data[i]);
        }
        // Remove the extracted elements from the original array
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
    public int lowIndex() {
        return 0;
    }

    @Override
    public int highIndex() {
        return size - 1;
    }

    @Override
    public boolean indexInRange(int index){
        return index >= lowIndex() && index <= highIndex();
    }

}










































































