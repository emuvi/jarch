package com.vidlus.jarch.mage;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

import com.vidlus.jarch.data.Pair;

public class WizArray {
    
    private WizArray() {
    }

    /**
     * Checks if the given value is an array-like object.
     * Supports arrays, Lists, Sets, and Maps.
     *
     * @param value the value to check
     * @return true if value is an array, List, Set, or Map; false otherwise
     */
    public static boolean is(Object value) {
        if (value == null) return false;
        return value.getClass().isArray()
            || WizLang.isChildOf(value.getClass(), List.class)
            || WizLang.isChildOf(value.getClass(), Set.class)
            || WizLang.isChildOf(value.getClass(), Map.class);
    }

    /**
     * Converts array-like objects to an Object array.
     * Handles arrays, Lists, Sets, and Maps (Maps are converted to Pair arrays).
     *
     * @param value the value to convert (array, List, Set, or Map)
     * @return an Object array representation of the value, or null if value is null
     * @throws IllegalArgumentException if the value cannot be converted to Object[]
     */
    public static Object[] get(Object value) {
        if (value == null) return null;
        if (value.getClass().isArray()) {
            return (Object[]) value;
        }
        if (WizLang.isChildOf(value.getClass(), List.class)) {
            return List.class.cast(value).toArray();
        }
        if (WizLang.isChildOf(value.getClass(), Set.class)) {
            return Set.class.cast(value).toArray();
        }
        if (WizLang.isChildOf(value.getClass(), Map.class)) {
            Map<?, ?> map = Map.class.cast(value);
            Object[] result = new Object[map.size()];
            int i = 0;
            for (var entry : map.entrySet()) {
                result[i++] = new Pair<>(entry.getKey(), entry.getValue());
            }
            return result;
        }
        throw new IllegalArgumentException("Could not convert to Object[] the value of class: " + value.getClass().getName());
    }

    /**
     * Checks if a value exists in the given array.
     *
     * @param <T> the type of elements
     * @param value the value to search for
     * @param onArray the array to search in
     * @return true if value is found in the array; false otherwise
     */
    public static <T> boolean has(T value, T... onArray) {
        if (onArray != null) {
            for (Object daMatriz : onArray) {
                if (Objects.equals(value, daMatriz)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if all values in the first array exist in the second array.
     *
     * @param <T> the type of elements
     * @param values the array of values to search for
     * @param onArray the array to search in
     * @return true if all values are found in the array; false otherwise
     */
    public static <T> boolean has(T[] values, T[] onArray) {
        if (values == null || onArray == null) {
            return false;
        }
        var founds = new boolean[values.length];
        for (Object item : onArray) {
            for (int i = 0; i < values.length; i++) {
                if (Objects.equals(values[i], item)) {
                    founds[i] = true;
                }
            }
        }
        for (var found : founds) {
            if (!found) {
                return false;
            }
        }
        return true; 
    }

    /**
     * Checks if any value from the first array exists in the second array.
     *
     * @param <T> the type of elements
     * @param values the array of values to search for
     * @param onArray the array to search in
     * @return true if at least one value is found in the array; false otherwise
     */
    public static <T> boolean hasAny(T[] values, T[] onArray) {
        if (values == null || onArray == null) {
            return false;
        }
        for (Object item : onArray) {
            for (int i = 0; i < values.length; i++) {
                if (Objects.equals(values[i], item)) {
                    return true;
                }
            }
        }
        return false; 
    }

    /**
     * Checks if a boolean value exists in the given array.
     *
     * @param value the value to search for
     * @param onArray the array to search in
     * @return true if value is found in the array; false otherwise
     */
    public static boolean has(boolean value, boolean... onArray) {
        if (onArray != null) {
            for (boolean daMatriz : onArray) {
                if (daMatriz == value) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if a byte value exists in the given array.
     *
     * @param value the value to search for
     * @param onArray the array to search in
     * @return true if value is found in the array; false otherwise
     */
    public static boolean has(byte value, byte... onArray) {
        if (onArray != null) {
            for (byte daMatriz : onArray) {
                if (daMatriz == value) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if a short value exists in the given array.
     *
     * @param value the value to search for
     * @param onArray the array to search in
     * @return true if value is found in the array; false otherwise
     */
    public static boolean has(short value, short... onArray) {
        if (onArray != null) {
            for (short daMatriz : onArray) {
                if (daMatriz == value) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if a char value exists in the given array.
     *
     * @param value the value to search for
     * @param onArray the array to search in
     * @return true if value is found in the array; false otherwise
     */
    public static boolean has(char value, char... onArray) {
        if (onArray != null) {
            for (char daMatriz : onArray) {
                if (daMatriz == value) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if an int value exists in the given array.
     *
     * @param value the value to search for
     * @param onArray the array to search in
     * @return true if value is found in the array; false otherwise
     */
    public static boolean has(int value, int... onArray) {
        if (onArray != null) {
            for (int daMatriz : onArray) {
                if (daMatriz == value) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if a long value exists in the given array.
     *
     * @param value the value to search for
     * @param onArray the array to search in
     * @return true if value is found in the array; false otherwise
     */
    public static boolean has(long value, long... onArray) {
        if (onArray != null) {
            for (long daMatriz : onArray) {
                if (daMatriz == value) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if a float value exists in the given array.
     * Uses Float.compare() for accurate floating-point comparison.
     *
     * @param value the value to search for
     * @param onArray the array to search in
     * @return true if value is found in the array; false otherwise
     */
    public static boolean has(float value, float... onArray) {
        if (onArray != null) {
            for (float daMatriz : onArray) {
                if (Float.compare(daMatriz, value) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if a double value exists in the given array.
     * Uses Double.compare() for accurate floating-point comparison.
     *
     * @param value the value to search for
     * @param onArray the array to search in
     * @return true if value is found in the array; false otherwise
     */
    public static boolean has(double value, double... onArray) {
        if (onArray != null) {
            for (double daMatriz : onArray) {
                if (Double.compare(daMatriz, value) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Inserts a value at the specified index in the array.
     * Shifts elements at and after the index to the right.
     *
     * @param <T> the type of elements
     * @param index the position at which to insert the value
     * @param value the value to insert
     * @param onArray the original array
     * @return a new array with the value inserted, or null if the original array is null
     */
    @SuppressWarnings("all")
    public static <T> T[] insert(int index, T value, T... onArray) {
        if (onArray == null) {
            return null;
        }
        var result = Arrays.copyOf(onArray, Math.max(onArray.length + 1, index + 1));
        result[index] = value;
        for (var i = index; i < onArray.length; i++) {
            result[i + 1] = onArray[i];
        }
        return result;
    }

    /**
     * Filters out null elements from the given array.
     * Returns a new array containing only non-null elements.
     *
     * @param <T> the type of elements
     * @param elements the array to filter
     * @return a new array with null elements removed, or null if input is null
     */
    @SuppressWarnings("all")
    public static <T> T[] getNotNull(T... elements) {
        if (elements == null) {
            return null;
        }
        List<T> list = new ArrayList<>();
        for (T element : elements) {
            if (element != null) {
                list.add(element);
            }
        }
        var result = Arrays.copyOf(elements, list.size());
        for (var i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }

    /**
     * Creates a new array of the specified size filled with the given value.
     *
     * @param <T> the type of elements
     * @param clazz the class of the element type
     * @param value the value to fill the array with
     * @param size the size of the array to create
     * @return a new array of specified size filled with the value
     */
    @SuppressWarnings("all")
    public static <T> T[] make(Class<T> clazz, T value, int size) {
        var result = (T[]) Array.newInstance(clazz, size);
        Arrays.fill(result, value);
        return result;
    }

    /**
     * Finds the index of the first occurrence of a value in the array.
     *
     * @param <T> the type of elements
     * @param value the value to search for
     * @param onArray the array to search in
     * @return the index of the first occurrence, or -1 if not found
     */
    public static <T> int indexOf(T value, T... onArray) {
        return indexOf(value, 0, onArray);
    }

    /**
     * Finds the index of the first occurrence of a value in the array, starting from a specified index.
     *
     * @param <T> the type of elements
     * @param value the value to search for
     * @param startIndex the index to start searching from
     * @param onArray the array to search in
     * @return the index of the first occurrence, or -1 if not found
     */
    public static <T> int indexOf(T value, int startIndex, T... onArray) {
        if (onArray == null) {
            return -1;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        for (int i = startIndex; i < onArray.length; i++) {
            if (Objects.equals(value, onArray[i])) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Finds the index of the last occurrence of a value in the array.
     *
     * @param <T> the type of elements
     * @param value the value to search for
     * @param onArray the array to search in
     * @return the index of the last occurrence, or -1 if not found
     */
    public static <T> int lastIndexOf(T value, T... onArray) {
        if (onArray == null) {
            return -1;
        }
        for (int i = onArray.length - 1; i >= 0; i--) {
            if (Objects.equals(value, onArray[i])) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Checks if an array is empty (null or zero length).
     *
     * @param <T> the type of elements
     * @param array the array to check
     * @return true if array is null or empty; false otherwise
     */
    public static <T> boolean isEmpty(T... array) {
        return array == null || array.length == 0;
    }

    /**
     * Checks if an array is not empty.
     *
     * @param <T> the type of elements
     * @param array the array to check
     * @return true if array is not null and not empty; false otherwise
     */
    public static <T> boolean isNotEmpty(T... array) {
        return !isEmpty(array);
    }

    /**
     * Adds an element to the end of an array.
     *
     * @param <T> the type of elements
     * @param array the original array
     * @param element the element to add
     * @return a new array with the element added, or null if the original array is null
     */
    @SuppressWarnings("all")
    public static <T> T[] add(T[] array, T element) {
        if (array == null) {
            return null;
        }
        T[] result = Arrays.copyOf(array, array.length + 1);
        result[array.length] = element;
        return result;
    }

    /**
     * Removes an element at the specified index from the array.
     *
     * @param <T> the type of elements
     * @param index the index of the element to remove
     * @param onArray the array to remove from
     * @return a new array with the element removed, or the original array if index is invalid
     */
    @SuppressWarnings("all")
    public static <T> T[] remove(int index, T... onArray) {
        if (onArray == null || index < 0 || index >= onArray.length) {
            return onArray;
        }
        T[] result = Arrays.copyOf(onArray, onArray.length - 1);
        System.arraycopy(onArray, 0, result, 0, index);
        if (index < onArray.length - 1) {
            System.arraycopy(onArray, index + 1, result, index, onArray.length - index - 1);
        }
        return result;
    }
    
    /**
     * Removes the first occurrence of a value from the array.
     *
     * @param <T> the type of elements
     * @param value the value to remove
     * @param onArray the array to remove from
     * @return a new array with the first occurrence of the value removed, or the original array if not found
     */
    @SuppressWarnings("all")
    public static <T> T[] removeElement(T value, T... onArray) {
        int index = indexOf(value, onArray);
        if (index == -1) {
            return onArray;
        }
        return remove(index, onArray);
    }

    /**
     * Concatenates two arrays into a single array.
     *
     * @param <T> the type of elements
     * @param array1 the first array
     * @param array2 the second array
     * @return a new array with elements from both arrays, or null if both are null
     */
    @SuppressWarnings("all")
    public static <T> T[] concat(T[] array1, T... array2) {
        if (array1 == null) {
            return array2 != null ? array2.clone() : null;
        }
        if (array2 == null) {
            return array1.clone();
        }
        T[] result = Arrays.copyOf(array1, array1.length + array2.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
        return result;
    }

    /**
     * Reverses the order of elements in an array.
     *
     * @param <T> the type of elements
     * @param array the array to reverse
     * @return a new array with elements in reverse order, or null if input is null
     */
    @SuppressWarnings("all")
    public static <T> T[] reverse(T... array) {
        if (array == null) {
            return null;
        }
        T[] result = array.clone();
        int i = 0;
        int j = result.length - 1;
        while (j > i) {
            T tmp = result[j];
            result[j] = result[i];
            result[i] = tmp;
            j--;
            i++;
        }
        return result;
    }

    /**
     * Extracts a subarray from the specified start (inclusive) to end (exclusive) indices.
     *
     * @param <T> the type of elements
     * @param array the array to extract from
     * @param startIndexInclusive the start index (inclusive)
     * @param endIndexExclusive the end index (exclusive)
     * @return a new array containing elements from start to end, or null if input is null
     */
    @SuppressWarnings("all")
    public static <T> T[] subArray(T[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array.length) {
            endIndexExclusive = array.length;
        }
        int newSize = endIndexExclusive - startIndexInclusive;
        if (newSize <= 0) {
            return Arrays.copyOf(array, 0);
        }
        return Arrays.copyOfRange(array, startIndexInclusive, endIndexExclusive);
    }

    /**
     * Joins array elements into a string with the specified separator.
     *
     * @param <T> the type of elements
     * @param array the array to join
     * @param separator the separator string
     * @return a string with joined elements, or null if input array is null
     */
    public static <T> String join(T[] array, String separator) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return "";
        }
        if (separator == null) {
            separator = "";
        }
        StringBuilder buf = new StringBuilder();
        buf.append(array[0]);
        for (int i = 1; i < array.length; i++) {
            buf.append(separator).append(array[i]);
        }
        return buf.toString();
    }

    /**
     * Filters array elements based on a predicate.
     * Returns a new array containing only elements that match the predicate.
     *
     * @param <T> the type of elements
     * @param array the array to filter
     * @param predicate the filtering condition
     * @return a new array with filtered elements, or null if input is null
     */
    @SuppressWarnings("all")
    public static <T> T[] filter(T[] array, Predicate<T> predicate) {
        if (array == null || predicate == null) {
            return null;
        }
        List<T> list = new ArrayList<>();
        for (T item : array) {
            if (predicate.test(item)) {
                list.add(item);
            }
        }
        T[] result = Arrays.copyOf(array, list.size());
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }

    /**
     * Maps array elements to a different type using a transformation function.
     * Returns a new array of the target type with transformed elements.
     *
     * @param <T> the type of input elements
     * @param <R> the type of output elements
     * @param array the array to map
     * @param mapper the transformation function
     * @param clazz the class of the output element type
     * @return a new array with mapped elements, or null if input is null
     */
    @SuppressWarnings("all")
    public static <T, R> R[] map(T[] array, Function<T, R> mapper, Class<R> clazz) {
        if (array == null || mapper == null || clazz == null) {
            return null;
        }
        R[] result = (R[]) Array.newInstance(clazz, array.length);
        for (int i = 0; i < array.length; i++) {
            result[i] = mapper.apply(array[i]);
        }
        return result;
    }

    /**
     * Returns the first element in the array that matches the predicate.
     *
     * @param <T> the type of elements
     * @param array the array to search
     * @param predicate the matching condition
     * @return the first matching element, or null if no match is found
     */
    public static <T> T first(T[] array, Predicate<T> predicate) {
        if (array == null || predicate == null) {
            return null;
        }
        for (T item : array) {
            if (predicate.test(item)) {
                return item;
            }
        }
        return null;
    }

    /**
     * Checks if any element in the array matches the predicate.
     *
     * @param <T> the type of elements
     * @param array the array to check
     * @param predicate the matching condition
     * @return true if at least one element matches; false otherwise
     */
    public static <T> boolean anyMatch(T[] array, Predicate<T> predicate) {
        if (array == null || predicate == null) {
            return false;
        }
        for (T item : array) {
            if (predicate.test(item)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if all elements in the array match the predicate.
     *
     * @param <T> the type of elements
     * @param array the array to check
     * @param predicate the matching condition
     * @return true if all elements match; false otherwise (or if array is empty)
     */
    public static <T> boolean allMatch(T[] array, Predicate<T> predicate) {
        if (array == null || predicate == null) {
            return false;
        }
        if (array.length == 0) {
            return true;
        }
        for (T item : array) {
            if (!predicate.test(item)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if no elements in the array match the predicate.
     *
     * @param <T> the type of elements
     * @param array the array to check
     * @param predicate the matching condition
     * @return true if no elements match; false otherwise
     */
    public static <T> boolean noneMatch(T[] array, Predicate<T> predicate) {
        return !anyMatch(array, predicate);
    }

    /**
     * Removes duplicate elements from the array.
     * Preserves insertion order using a LinkedHashSet.
     *
     * @param <T> the type of elements
     * @param array the array to process
     * @return a new array with duplicate elements removed, or null if input is null
     */
    @SuppressWarnings("all")
    public static <T> T[] distinct(T[] array) {
        if (array == null) {
            return null;
        }
        Set<T> set = new LinkedHashSet<>(Arrays.asList(array));
        T[] result = Arrays.copyOf(array, set.size());
        int i = 0;
        for (T item : set) {
            result[i++] = item;
        }
        return result;
    }

    /**
     * Swaps two elements at the specified indices in the array.
     *
     * @param <T> the type of elements
     * @param array the array containing the elements to swap
     * @param i the index of the first element
     * @param j the index of the second element
     */
    public static <T> void swap(T[] array, int i, int j) {
        if (array == null || i < 0 || j < 0 || i >= array.length || j >= array.length) {
            return;
        }
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    /**
     * Shuffles the array in random order using Fisher-Yates algorithm.
     *
     * @param <T> the type of elements
     * @param array the array to shuffle
     */
    public static <T> void shuffle(T[] array) {
        if (array == null) {
            return;
        }
        Random rnd = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            swap(array, i, index);
        }
    }

    /**
     * Sorts the array using the provided comparator.
     *
     * @param <T> the type of elements
     * @param array the array to sort
     * @param comparator the comparator to use for sorting
     */
    public static <T> void sort(T[] array, Comparator<? super T> comparator) {
        if (array == null) {
            return;
        }
        Arrays.sort(array, comparator);
    }

    /**
     * Finds the minimum element in the array using the provided comparator.
     *
     * @param <T> the type of elements
     * @param array the array to search
     * @param comparator the comparator to use for comparison
     * @return the minimum element, or null if array is null or empty
     */
    public static <T> T min(T[] array, Comparator<? super T> comparator) {
        if (array == null || array.length == 0) {
            return null;
        }
        T min = array[0];
        for (int i = 1; i < array.length; i++) {
            if (comparator.compare(array[i], min) < 0) {
                min = array[i];
            }
        }
        return min;
    }

    /**
     * Finds the maximum element in the array using the provided comparator.
     *
     * @param <T> the type of elements
     * @param array the array to search
     * @param comparator the comparator to use for comparison
     * @return the maximum element, or null if array is null or empty
     */
    public static <T> T max(T[] array, Comparator<? super T> comparator) {
        if (array == null || array.length == 0) {
            return null;
        }
        T max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (comparator.compare(array[i], max) > 0) {
                max = array[i];
            }
        }
        return max;
    }

    /**
     * Counts the number of elements in the array that match the predicate.
     *
     * @param <T> the type of elements
     * @param array the array to count
     * @param predicate the matching condition
     * @return the number of elements matching the predicate
     */
    public static <T> int count(T[] array, Predicate<T> predicate) {
        if (array == null || predicate == null) {
            return 0;
        }
        int c = 0;
        for (T item : array) {
            if (predicate.test(item)) {
                c++;
            }
        }
        return c;
    }

    /**
     * Flattens a 2D array into a 1D array.
     * Concatenates all sub-arrays into a single array.
     *
     * @param <T> the type of elements
     * @param array the 2D array to flatten
     * @return a new 1D array containing all elements, or null if input is null
     */
    @SuppressWarnings("all")
    public static <T> T[] flatten(T[][] array) {
        if (array == null) {
            return null;
        }
        int size = 0;
        T[] firstNonNull = null;
        for (T[] sub : array) {
            if (sub != null) {
                size += sub.length;
                if (firstNonNull == null) {
                    firstNonNull = sub;
                }
            }
        }
        if (firstNonNull == null) {
            return null;
        }
        T[] result = Arrays.copyOf(firstNonNull, size);
        int destPos = 0;
        for (T[] sub : array) {
            if (sub != null) {
                System.arraycopy(sub, 0, result, destPos, sub.length);
                destPos += sub.length;
            }
        }
        return result;
    }
}
