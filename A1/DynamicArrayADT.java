/**
 * just like an Arraylist but will implement it ourselves
 * @param <J> the type of elements stored in the array
 */

public interface DynamicArrayADT<J>{

    //Group 1

    /**
     * Returns the element stored at a given index.
     * @param index the position
     * @return returns the element
     * @throws IndexOutOfBoundsException if index is invalid
     */
    J get(int index);

    /**
     * Updates the element at the given index.
     * 
     * @param index the position of the element
     * @param value the new value
     * @return returns the old value at that index (null if previously unset)
     * @throws IndexOutOfBoundsException if index is invalid
     */
    J set(int index, J value);

    /**
     * Returns number of elements in the array.
     * @return returns number of elements
     */
    int size();


    //Group 2

    /**
     * This adds elements into the specfific index, moving the rest of the elements to the right.
     * @param index the position to add the element
     * @param value element to add
     * @throws IndexOutOfBoundsException if index is invalid
     */

    void add(int index, J value); 

    /**
     * Adds an element to the end of the array.
     * @param value element to insert 
     */

    void add(J value); //adds to the end of the array


    /**
     * This removes the element at the specified index, shifting subsequent elements left.
     * @param index the position of the element to remove
     * @return returns the removed element
     * @throws IndexOutOfBoundsException if index is invalid
     */
    J remove(int index); //removes the element at the index and shifts everything to the left

    //Group 3

    /**
     * Puts another DynamicArray at the end of this one. To make a new DynamicArray that's a combination of the two. 
     * @param other the other DynamicArray to append
     * @return returns a new DynamicArray that is the concatenation of this one and other
     */
    DynamicArrayADT<J> append(DynamicArrayADT<J> other); //returns a new DynamicArray that is the concatenation of this and other

    /**
     * Inserts all the elements of another DynamicArray into this one at the specified index.
     * @param other the other DynamicArray that will be inserted
     * @param index the position to insert the other DynamicArray
     * @return returns new DynamicArray that is this one with other inserted at index
     */
    DynamicArrayADT<J> insert(int index, DynamicArrayADT<J> other); 

    /**
     * Returns a sublist/copy of this DynamicArray from start, [fromIndex, to end toIndex) (exclusive).
     * @param fromIndex, inclusive start
     * @param toIndex, exclusive end
     * @return returns a new DynamicArray that is a copy of the elements from fromIndex to toIndex-1
     * @throws IndexOutOfBoundsException if either index is invalid
     */
    DynamicArrayADT<J> sublist(int fromIndex, int toIndex); 

    /**
     * Returns a new DynamicArray with the elements of the sublist from start to end (exclusive) removed.
     * @param fromIndex, inclusive start
     * @param toIndex, exclusive end
     * @return returns a new DynamicArray with that specific range removed
     * @throws IndexOutOfBoundsException if either index is invalid
     */
    DynamicArrayADT<J> delete(int fromIndex, int toIndex); 

    /**
     * Returns a new DynamicArray containing the elements from one index up to just before another, [fromIndex, toIndex)
     * @param fromIndex, inclusive start
     * @param toIndex, exclusive end
     * @return returns a new DynamicArray with that range extracted
     * @throws IndexOutOfBoundsException if either index is invalid
     */
    DynamicArrayADT<J> extract(int fromIndex, int toIndex);

    //Kudos-Group 4

    /**
     * Returns the lowest valid index for this collection, which is usually 0.
     * @return  returns the lowest valid index for this collection
     */
    default int lowIndex() {
        return 0;
    }

    /**
     * Returns the highest valid index for this collection, which is usually size() - 1.
     * @return returns the highest valid index for this collection.
     */

    default int highIndex() {
        return size() - 1;
    }

    /**
     * Returns true if a given index is within the valid range.
     * @param index the index to check
     * @return returns a boolean value indicating whether a particular index is valid for purposes of the `get` method
     */
    default boolean indexInRange(int index){
        return index >= lowIndex() && index <= highIndex();
    }
    
}