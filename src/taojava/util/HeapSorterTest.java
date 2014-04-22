package taojava.util;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import org.junit.Test;

/**
 * SamR's tests for Quicksort. Code based on earlier code from the sorting
 * laboratories.
 */
public class HeapSorterTest
{
  // +-----------+-------------------------------------------------------
  // | Constants |
  // +-----------+

  /**
   * The number of randomized arrays we build.
   */
  public static final int NUM_RANDOM_ARRAYS = 20;

  /**
   * The number of permutations we do per randomized array.
   */
  public static final int NUM_PERMUTATIONS = 50;

  /**
   * The minimum size of a random array.
   */
  public static final int MIN_RANDOM_ARRAY_SIZE = 10;

  /**
   * The maximum size of a random array.
   */
  public static final int MAX_RANDOM_ARRAY_SIZE = 128;

  // +---------------+---------------------------------------------------
  // | Static Fields |
  // +---------------+

  /**
   * A random number generator for use in permutations and such.
   */
  static Random generator = new Random();

  /**
   * A comparator for integers.
   */
  static Comparator<Integer> orderInts = new Comparator<Integer>()
    {
      public int compare(Integer left, Integer right)
      {
        return left.compareTo(right);
      } // compare(Integer, Integer)
    }; // new Comparator<Integer>

  // +-------+-----------------------------------------------------------
  // | Tests |
  // +-------+

  /**
   * Make sure that it works correctly on some already sorted arrays.
   */
  @Test
  public void testSorted()
  {
    // Empty array
    checkSort(new Integer[] {}, orderInts);
    // Singleton array
    checkSort(new Integer[] { 0 }, orderInts);
    // Two-element arrays
    checkSort(new Integer[] { 0, 1 }, orderInts);
    checkSort(new Integer[] { 0, 0 }, orderInts);
    // Three-element arrays
    checkSort(new Integer[] { 0, 0, 0 }, orderInts);
    checkSort(new Integer[] { 0, 0, 1 }, orderInts);
    checkSort(new Integer[] { 0, 1, 1 }, orderInts);
    // Four-element arrays
    checkSort(new Integer[] { 0, 0, 0, 0 }, orderInts);
    checkSort(new Integer[] { 0, 0, 0, 1 }, orderInts);
    checkSort(new Integer[] { 0, 0, 1, 1 }, orderInts);
    checkSort(new Integer[] { 0, 1, 1, 1 }, orderInts);
  } // testSorted()

  /**
   * Make sure that some larger, predesigned, arrays sort correctly.
   */
  @Test
  public void testVarious()
  {
    // Mostly the same value
    permutationTests(new Integer[] { 0, 0, 0, 0, 0, 0, 1, 2, 3, 4 }, orderInts,
                     20);
    // Only two different values
    permutationTests(new Integer[] { 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1,
                                    1, 1 }, orderInts, 20);

    // Three different values
    permutationTests(new Integer[] { 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 5, 5, 5, 5,
                                    5 }, orderInts, 20);
  } // testVarious()

  /**
   * Lots of randomized tests.
   */
  public void testRandomized()
  {
    for (int i = 0; i < NUM_RANDOM_ARRAYS; i++)
      {
        Integer[] sorted =
            randomSortedInts(1 + generator.nextInt(1 + MAX_RANDOM_ARRAY_SIZE
                                                   - MIN_RANDOM_ARRAY_SIZE));
        permutationTests(sorted, orderInts, NUM_PERMUTATIONS);
      } // for
  } // testRandomized

  // +-----------+-------------------------------------------------------
  // | Utilities |
  // +-----------+

  /**
   * Check the results of sorting an already sorted array.
   */
  public static <T> void checkSort(T[] sorted, Comparator<T> order)
  {
    checkSort(sorted, sorted.clone(), order);
  } // checkSort(T[])

  /**
   * Check the results of sorting.
   * 
   * @param unsorted
   *            the array to sort
   * @param expected
   *            the sorted version of that array
   * @param order
   *            the order in which elements should appear
   */
  public static <T> void checkSort(T[] unsorted, T[] expected,
                                   Comparator<T> order)
  {
    T[] result = unsorted.clone();
    HeapSorter.sort(result, order);
    if (!Arrays.equals(result, expected))
      {
        System.err.println("Original: " + Arrays.toString(unsorted));
        System.err.println("Result:   " + Arrays.toString(result));
        System.err.println("Expected: " + Arrays.toString(expected));
        fail("Did not sort correctly.");
      } // if not equal
  } // checkResults(T[], T[], T[])

  /**
   * "Randomly" permute an array in place.
   */
  public static <T> T[] permute(T[] values)
  {
    for (int i = 0; i < values.length; i++)
      {
        swap(values, i, generator.nextInt(values.length));
      } // for
    return values;
  } // permute(T)

  /**
   * Generate a "random" sorted array of integers of size n.
   */
  public static Integer[] randomSortedInts(int n)
  {
    if (n == 0)
      {
        return new Integer[0];
      }
    Integer[] values = new Integer[n];
    // Start with a negative number so that we have a mix
    values[0] = generator.nextInt(10) - n;
    // Add remaining values. We use a random increment between
    // 0 and 3 so that there are some duplicates and some gaps.
    for (int i = 1; i < n; i++)
      {
        values[i] = values[i - 1] + generator.nextInt(4);
      } // for
    return values;
  } // randomSortedInts

  /**
   * Swap two elements in an array.
   * 
   * @param values
   *            the array
   * @param i
   *            one of the indices
   * @param j
   *            another index
   * @pre 0 <= i,j < values.length
   * @pre a = values[i]
   * @pre b = values[j]
   * @post values[i] = b
   * @post values[j] = a
   */
  public static <T> void swap(T[] values, int i, int j)
  {
    T tmp = values[i];
    values[i] = values[j];
    values[j] = tmp;
  } // swap(T[], int, int)

  /**
   * A simple test using one permutation of an already sorted array.
   * 
   * @param, sorted A sorted array of values
   * @param, order The order used to generate that sorted array.
   */
  public static <T> void permutationTest(T[] sorted, Comparator<T> order)
  {
    // Create a permuted version of the array.
    T[] values = sorted.clone();
    permute(values);

    // Check the results.
    checkSort(values, sorted, order);
  } // permutationExperiment(PrintWriter, Sorter<T>, Comparator<T>

  /**
   * Do some number of permutation tests.
   */
  public static <T> void permutationTests(T[] sorted, Comparator<T> compare,
                                          int n)
  {
    for (int i = 0; i < n; i++)
      {
        permutationTest(sorted, compare);
      } // for
  } // permutationTests(T[], compare, int)

} // HeapSorterTest
