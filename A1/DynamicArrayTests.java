import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DynamicArrayTests {

    private DynamicArray<Character> a1;
    private DynamicArray<Character> a2;
    private DynamicArray<Character> empty;
    private DynamicArray<Character> s;

    @Before
    public void setUp() {
        a1 = stringToArray("abcdef");
        a2 = stringToArray("wxyz");
        empty = stringToArray("");
        s = stringToArray("s");
    }

    // Helper: builds array from string
    private DynamicArray<Character> stringToArray(String s) {
        DynamicArray<Character> result = new DynamicArray<>(s.length());
        for (int i = 0; i < s.length(); i++) {
            result.add(i, s.charAt(i));
        }
        return result;
    }

    // Helper: check array contents against string
    private void compareToString(DynamicArrayADT<Character> arr, String s) {
        assertEquals("Size mismatch", s.length(), arr.size());
        for (int i = 0; i < s.length(); i++) {
            assertEquals("Mismatch at index " + i, 
                         Character.valueOf(s.charAt(i)), arr.get(i));
        }
    }

    // ~*~ Append Tests ~*~
    @Test
    public void testAppendStandard() {
        compareToString(a1.append(a2), "abcdefwxyz");
        compareToString(a2.append(a1), "wxyzabcdef");
    }

    @Test
    public void testAppendSelf() {
        compareToString(a1.append(a1), "abcdefabcdef");
        compareToString(a2.append(a2), "wxyzwxyz");
    }

    @Test
    public void testAppendEmpty() {
        compareToString(a1.append(empty), "abcdef");
        compareToString(empty.append(a1), "abcdef");
        compareToString(empty.append(empty), "");
    }

    // ~*~ Insert Array Tests ~*~
    @Test
    public void testInsertArrayMiddle() {
        compareToString(a1.insert(3, a2), "abcwxyzdef");
    }

    @Test
    public void testInsertArrayFront() {
        compareToString(a1.insert(0, a2), "wxyzabcdef");
    }

    @Test
    public void testInsertArrayEnd() {
        compareToString(a1.insert(a1.size(), a2), "abcdefwxyz");
    }

    @Test
    public void testInsertArrayEmpty() {
        compareToString(a1.insert(3, empty), "abcdef");
        compareToString(empty.insert(0, empty), "");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testInsertArrayInvalidLow() {
        a1.insert(-1, a2);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testInsertArrayInvalidHigh() {
        a1.insert(a1.size() + 1, a2);
    }

    // ~*~ Split Tests ~*~
    @Test
    public void testSplitSuffixStandard() {
        compareToString(a1.splitSuffix(3), "def");
        compareToString(a1, "abc"); // original left side remains
    }

    @Test
    public void testSplitPrefixStandard() {
        compareToString(a1.splitPrefix(3), "abc");
        compareToString(a1, "def"); // original right side remains
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testSplitSuffixInvalid() {
        a1.splitSuffix(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testSplitPrefixInvalid() {
        a1.splitPrefix(a1.size() + 1);
    }

    // ~*~ Other Operations ~*~
    @Test
    public void testGetAndSet() {
        DynamicArray<String> arr = new DynamicArray<>(3);
        arr.add("oval");
        arr.add("circle");

        assertEquals("oval", arr.get(0));
        assertEquals("circle", arr.set(1, "square"));
        assertEquals("square", arr.get(1));
    }

    @Test
    public void testAddAndRemove() {
        DynamicArray<Integer> arr = new DynamicArray<>(3);
        arr.add(0, 1);
        arr.add(1, 2);
        arr.add(2, 3);
        arr.add(1, 4); // [1, 4, 2, 3]

        assertEquals(4, arr.size());
        assertEquals((Integer) 4, arr.get(1));
        assertEquals((Integer) 2, arr.remove(2)); // [1, 4, 3]
        assertEquals(3, arr.size());
        assertEquals((Integer) 3, arr.get(2));
    }

    @Test
    public void testSublist() {
        DynamicArray<String> arr = new DynamicArray<>(5);
        arr.add("csc");
        arr.add("dept");
        arr.add("is");
        arr.add("cool");

        DynamicArrayADT<String> sub = arr.sublist(1, 3);
        assertEquals(2, sub.size());
        assertEquals("dept", sub.get(0));
        assertEquals("is", sub.get(1));
    }

    @Test
    public void testDelete() {
        DynamicArray<String> arr = new DynamicArray<>(5);
        arr.add("csc");
        arr.add("dept");
        arr.add("is");
        arr.add("cool");

        arr.delete(1, 3); // should leave [csc, cool]
        assertEquals(2, arr.size());
        assertEquals("csc", arr.get(0));
        assertEquals("cool", arr.get(1));
    }
}

