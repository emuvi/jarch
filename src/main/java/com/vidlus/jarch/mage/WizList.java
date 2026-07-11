package com.vidlus.jarch.mage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class WizList {

    private WizList() {}

    /**
     * Creates an ArrayList from the given varargs.
     */
    @SafeVarargs
    public static <T> List<T> of(T... items) {
        var list = new ArrayList<T>();
        if (items != null) {
            Collections.addAll(list, items);
        }
        return list;
    }

    /**
     * Checks if a list is null or empty.
     */
    public static boolean isEmpty(List<?> list) {
        return list == null || list.isEmpty();
    }

    /**
     * Checks if a list is not null and not empty.
     */
    public static boolean isNotEmpty(List<?> list) {
        return !isEmpty(list);
    }

    /**
     * Safely gets the first element of a list, returning null if empty.
     */
    public static <T> T first(List<T> list) {
        return isNotEmpty(list) ? list.get(0) : null;
    }

    /**
     * Safely gets the last element of a list, returning null if empty.
     */
    public static <T> T last(List<T> list) {
        return isNotEmpty(list) ? list.get(list.size() - 1) : null;
    }

    /**
     * Safely gets an element at the specified index, returning null if out of bounds or list is null.
     */
    public static <T> T get(List<T> list, int index) {
        if (isEmpty(list) || index < 0 || index >= list.size()) {
            return null;
        }
        return list.get(index);
    }

    /**
     * Inserts a value at the specified index. If the list is smaller than the index,
     * it pads the list with nulls up to the index.
     */
    public static <T> List<T> insert(int index, T value) {
        return insert(index, value, null, null);
    }

    /**
     * Inserts a value at the specified index in the given list. If the list is smaller,
     * it pads the list with nulls up to the index.
     */
    public static <T> List<T> insert(int index, T value, List<T> onList) {
        return insert(index, value, onList, null);
    }
    
    /**
     * Inserts a value at the specified index in the given list. If the list is smaller,
     * it pads the list with 'withDefault' up to the index.
     */
    public static <T> List<T> insert(int index, T value, List<T> onList, T withDefault) {
        if (onList == null) {
            onList = new ArrayList<>();
        }
        while (onList.size() <= index) {
            onList.add(withDefault);
        }
        onList.set(index, value);
        return onList;
    }

    /**
     * Filters a list based on a predicate and returns a new list.
     */
    public static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
        if (isEmpty(list) || predicate == null) {
            return new ArrayList<>();
        }
        return list.stream().filter(predicate).collect(Collectors.toList());
    }

    /**
     * Maps elements of a list using a mapping function and returns a new list.
     */
    public static <T, R> List<R> map(List<T> list, Function<T, R> mapper) {
        if (isEmpty(list) || mapper == null) {
            return new ArrayList<>();
        }
        return list.stream().map(mapper).collect(Collectors.toList());
    }

    /**
     * Joins the elements of a list into a single string using a delimiter.
     */
    public static <T> String join(List<T> list, String delimiter) {
        if (isEmpty(list)) {
            return "";
        }
        return list.stream()
                   .map(String::valueOf)
                   .collect(Collectors.joining(delimiter != null ? delimiter : ""));
    }

    /**
     * Reverses a list, returning a new list without modifying the original.
     */
    public static <T> List<T> reverse(List<T> list) {
        if (isEmpty(list)) {
            return new ArrayList<>();
        }
        var reversed = new ArrayList<>(list);
        Collections.reverse(reversed);
        return reversed;
    }

    /**
     * Sorts a list using the provided comparator, returning a new list without modifying the original.
     */
    public static <T> List<T> sort(List<T> list, Comparator<T> comparator) {
        if (isEmpty(list)) {
            return new ArrayList<>();
        }
        var sorted = new ArrayList<>(list);
        if (comparator != null) {
            sorted.sort(comparator);
        }
        return sorted;
    }

    /**
     * Removes duplicates from a list while preserving insertion order, returning a new list.
     */
    public static <T> List<T> unique(List<T> list) {
        if (isEmpty(list)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(new LinkedHashSet<>(list));
    }

    /**
     * Partitions a list into a list of sublists, each of the given size.
     */
    public static <T> List<List<T>> partition(List<T> list, int size) {
        var partitions = new ArrayList<List<T>>();
        if (isEmpty(list) || size <= 0) {
            return partitions;
        }
        for (int i = 0; i < list.size(); i += size) {
            partitions.add(new ArrayList<>(list.subList(i, Math.min(i + size, list.size()))));
        }
        return partitions;
    }
    
}
