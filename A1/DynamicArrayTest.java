import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for the DynamicArray class.
 * Each test uses fresh instances to avoid shared state issues.
 */
public class DynamicArrayTest {

    // Helper: builds array from string
    public DynamicArray<Character> stringToArray(String s) {
        DynamicArray<Character> result = new DynamicArray<>(s.length());
        for (int i = 0; i < s.length(); i++) {
            result.add(s.charAt(i));
        }
        return result;
    }

    // Compare size
    public void compareSize(DynamicArrayADT<Character> arr, String s) {
        assertEquals(s.length(), arr.size());
    }

    // Compare contents
    public void compareToString(DynamicArrayADT<Character> arr, String s) {
        compareSize(arr, s);
        for (int i = 0; i < arr.size(); i++) {
            assertEquals("Mismatch at index " + i, Character.valueOf(s.charAt(i)), arr.get(i));
        }
    }

    // ~*~ Constructor ~*~
    @Test
    public void testConstructor() {
        DynamicArray<Character> arr = new DynamicArray<>(5);
        assertEquals(0, arr.size());
    }

    // ~*~ Insert ~*~
    @Test
    public void testInsert() {
        compareToString(stringToArray("abcdef").insert(3, stringToArray("wxyz")), "abcwxyzdef");
        compareToString(stringToArray("abcdef").insert(0, stringToArray("wxyz")), "wxyzabcdef");
        compareToString(stringToArray("abcdef").insert(6, stringToArray("wxyz")), "abcdefwxyz");
        compareToString(stringToArray("abcdef").insert(3, stringToArray("")), "abcdef");
        compareToString(stringToArray("wxyz").insert(2, stringToArray("wxyz")), "wxwxyzyz");
        compareToString(stringToArray("").insert(0, stringToArray("")), "");

        assertThrows(IndexOutOfBoundsException.class, () -> stringToArray("abcdef").insert(-1, stringToArray("wxyz")));
        assertThrows(IndexOutOfBoundsException.class, () -> stringToArray("abcdef").insert(7, stringToArray("wxyz")));
    }

    // ~*~ Delete ~*~
    @Test
    public void testDelete() {
        compareToString(stringToArray("abcdef").delete(2, 4), "abef");
        compareToString(stringToArray("abcdef").delete(0, 2), "cdef");
        compareToString(stringToArray("abcdef").delete(4, 6), "abcd");
        compareToString(stringToArray("abcdef").delete(0, 6), "");
        compareToString(stringToArray("abcdef").delete(3, 3), "abcdef");
        compareToString(stringToArray("").delete(0, 0), "");

        assertThrows(IndexOutOfBoundsException.class, () -> stringToArray("abcdef").delete(-1, 3));
        assertThrows(IndexOutOfBoundsException.class, () -> stringToArray("abcdef").delete(0, 7));
        assertThrows(IndexOutOfBoundsException.class, () -> stringToArray("abcdef").delete(7, 8));
        assertThrows(IndexOutOfBoundsException.class, () -> stringToArray("abcdef").delete(0, -2));
        assertThrows(IndexOutOfBoundsException.class, () -> stringToArray("abcdef").delete(4, 2));
    }

    // ~*~ Remove ~*~
    @Test
    public void testRemove() {
        DynamicArray<Character> arr1 = stringToArray("abcd");
        assertEquals(Character.valueOf('c'), arr1.remove(2));
        compareToString(arr1, "abd");

        DynamicArray<Character> arr2 = stringToArray("abcd");
        assertEquals(Character.valueOf('a'), arr2.remove(0));
        compareToString(arr2, "bcd");

        DynamicArray<Character> arr3 = stringToArray("abcd");
        assertEquals(Character.valueOf('d'), arr3.remove(3));
        compareToString(arr3, "abc");

        DynamicArray<Character> arr4 = stringToArray("x");
        assertEquals(Character.valueOf('x'), arr4.remove(0));
        compareToString(arr4, "");

        assertThrows(IndexOutOfBoundsException.class, () -> stringToArray("abcdef").remove(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> stringToArray("abcdef").remove(6));
        assertThrows(IndexOutOfBoundsException.class, () -> stringToArray("abcdef").remove(100));
    }

    // ~*~ Extract ~*~
    @Test
    public void testExtract() {
        compareToString(stringToArray("abcdef").extract(2, 5), "cde");
        compareToString(stringToArray("abcdef").extract(0, 3), "abc");
        compareToString(stringToArray("abcdef").extract(3, 6), "def");
        compareToString(stringToArray("abcdef").extract(0, 6), "abcdef");
        compareToString(stringToArray("abcdef").extract(2, 2), "");

        assertThrows(IndexOutOfBoundsException.class, () -> stringToArray("abcdef").extract(5, 2));
    }

    // ~*~ Append ~*~
    @Test
    public void testAppend() {
        compareToString(stringToArray("abcdef").append(stringToArray("wxyz")), "abcdefwxyz");
    }

    // ~*~ ToString ~*~
    @Test
    public void testToStringFormat() {
        assertEquals("[a, b, c, d, e, f]", stringToArray("abcdef").toString());
        assertEquals("[]", stringToArray("").toString());
    }

    // ~*~ SplitSuffix ~*~
    @Test
    public void testSplitSuffix() {
        compareToString(stringToArray("abcdef").splitSuffix(3), "def");
        compareToString(stringToArray("abcdef").splitSuffix(0), "abcdef");
        compareToString(stringToArray("abcdef").splitSuffix(6), "");

        assertThrows(IndexOutOfBoundsException.class, () -> stringToArray("abcdef").splitSuffix(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> stringToArray("abcdef").splitSuffix(7));
    }

    // ~*~ SplitPrefix ~*~
    @Test
    public void testSplitPrefix() {
        DynamicArray<Character> arr1 = stringToArray("abcdef");
        DynamicArrayADT<Character> prefix1 = arr1.splitPrefix(3);
        compareToString(prefix1, "abc");

        DynamicArray<Character> arr2 = stringToArray("abcdef");
        DynamicArrayADT<Character> prefix2 = arr2.splitPrefix(0);
        compareToString(prefix2, "");

        DynamicArray<Character> arr3 = stringToArray("abcdef");
        DynamicArrayADT<Character> prefix3 = arr3.splitPrefix(6);
        compareToString(prefix3, "abcdef");

        assertThrows(IndexOutOfBoundsException.class, () -> stringToArray("abcdef").splitPrefix(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> stringToArray("abcdef").splitPrefix(7));
    }
}
