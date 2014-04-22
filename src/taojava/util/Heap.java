package taojava.util;

import java.io.PrintWriter;

import java.util.Arrays;
import java.util.Comparator;

/**
 * A simple implementation of the heap data structure.  Reveals some
 * aspects of the heap implementation so that other classes can use
 * it (e.g., for heap sort).
 *
 * @author Samuel A. Rebelsky
 * @author Your Name Here
 */
public class Heap<T>
{
  // +-------+-----------------------------------------------------------
  // | Notes |
  // +-------+

  /*
   * When we refer to an array (or a subarray) of size size as a "heap", 
   * we mean that it has the heap property.
   *   For all i, 0 <= i < size:
   *     if (left(i) < size)
   *       order.compare(heap[i], heap[left(i)]) >= 0.
   *     if (right(i) < size)
   *       order.compare(heap[i], heap[right(i)]) >= 0.
   */

  // +-----------+-------------------------------------------------------
  // | Constants |
  // +-----------+

  /**
   * The default initial capacity of the heap.
   */
  static final int DEFAULT_INITIAL_CAPACITY = 32;

  // +-----------------------+-------------------------------------------
  // | Public Static Methods |
  // +-----------------------+

  /**
   * Restore the heap property by propagating up from pos.
   *
   * @pre
   *   pos >= 0
   * @pre
   *   The first pos elements of heap have the heap property.
   * @pre
   *   heap[pos] can be compared to other values using the comparator
   * @post
   *   The first pos+1 elements of heap have the heap property.
   */
  public static <T> void swapUp(T[] heap, int pos, Comparator<T> order)
  {
    int p; // Index of the parent

    // Keep swapping up until we reach the top or find a larger parent
    while ((pos > 0) && (order.compare(heap[pos], heap[p = parent(pos)]) > 0))
      {
        // Swap with the parent
        swap(heap, pos, p);
        // And continue upward
        pos = p;
      } // while
  } // swapUp

  /**
   * Restore the heap property by propagating down from pos.
   *
   * @pre
   *   pos < size
   * @pre
   *   The first pos elements of heap have the heap proprety.
   * @pre
   *   All indices i, pos < i < size, have the heap property
   * @post
   *   The first size elements of heap have the heap property.
   */
  public static <T> void swapDown(T[] heap, int size, int pos,
                                  Comparator<T> order)
  {
    int l = left(pos); // Index of the left child.
    int r = right(pos); // Index of the right child;

    if (l >= size)
      {
        // If there's no left child, we're done
        return;
      }
    else if (r >= size)
      {
        // If there's no right child, we still need to check
        // the left child.
        if (order.compare(heap[pos], heap[l]) < 0)
          {
            swap(heap, pos, l);
            // We're done, since the left child must be a leaf
          } // if (heap[pos] < heap[l])
      }
    else
      {
        // We have both children.  Which one is bigger?
        if (order.compare(heap[l], heap[r]) >= 0)
          {
            // Left is bigger 
            if (order.compare(heap[pos], heap[l]) < 0)
              {
                swap(heap, pos, l);
                swapDown(heap, size, l, order);
              } // if heap[pos] < heap[l]
          }
        else
          {
            // Right is bigger
            if (order.compare(heap[pos], heap[r]) < 0)
              {
                swap(heap, pos, r);
                swapDown(heap, size, r, order);
              } // if heap[pos] < heap[r]
          } // right is bigger
      } // Two children
  } // swapDown

  // +----------------+--------------------------------------------------
  // | Static Helpers |
  // +----------------+

  /**
   * Get the index of the left child of the "node" at position pos.
   */
  static int left(int pos)
  {
    return 2 * pos + 1;
  } // left(int)

  /**
   * Get the index of the parent of the "node" at position pos.
   */
  static int parent(int pos)
  {
    return (pos - 1) / 2;
  } // parent(int)

  /**
   * Get the index of the right child of the "node" at position pos.
   */
  static int right(int pos)
  {
    return 2 * pos + 2;
  } // right(int)

  /**
   * Swap the values at positions i and j.
   */
  static <T> void swap(T[] vals, int i, int j)
  {
    T tmp = vals[i];
    vals[i] = vals[j];
    vals[j] = tmp;
  } // swap(T[], int, int)

  // +--------+----------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The values in the heap (plus some space for additional values).
   */
  T[] values;

  /**
   * The number of values stored in the heap.
   */
  int size;

  /**
   * The comparator used to order the values in the heap.
   */
  Comparator<T> order;

  // +--------------+----------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a heap with the specified initial capacity that uses the
   * given comparator to order values.
   */
  @SuppressWarnings("unchecked")
  public Heap(int capacity, Comparator<T> order)
  {
    this.values = (T[]) new Object[capacity];
    this.order = order;
    this.size = 0;
  } // Heap(int, Comparator<T>)

  /**
   * Create a heap with the default initial capacity that uses the
   * given comparator to order values.
   */
  public Heap(Comparator<T> order)
  {
    this(DEFAULT_INITIAL_CAPACITY, order);
  } // Heap(int, Comparator<T>)

  // +-----------+-------------------------------------------------------
  // | Observers |
  // +-----------+

  /**
   * Print the standard tree representation of this heap.
   */
  public void dump(PrintWriter pen)
  {
    dump(pen, 0, "");
  } // dump(PrintWriter)

  /**
   * Get and remove the largest value in the heap.
   */
  public T get()
    throws Exception
  {
    if (this.size == 0)
      {
        throw new Exception("empty");
      } // if (this.size == 0)
    T result = this.values[0];
    // Put the last value at the top
    this.values[0] = this.values[--this.size];
    // Restore the heap property
    Heap.swapDown(this.values, this.size, 0, this.order);
    // And we're done
    return result;
  } // get()

  /**
   * Peek at the largest value in the heap.
   */
  public T peek()
    throws Exception
  {
    if (this.size == 0)
      {
        throw new Exception("empty");
      } // if (this.size == 0)
    return this.values[0];
  } // peek()

  /**
   * Determine the number of values stored in the heap.
   */
  int size()
  {
    return this.size;
  } // size()

  // +----------+--------------------------------------------------------
  // | Mutators |
  // +----------+

  /**
   * Add a value.
   */
  public void put(T val)
  {
    // Make sure that there's enough room
    if (this.size == this.values.length)
      {
        this.values = Arrays.copyOf(this.values, this.values.length * 2);
      } // if there's not enough room
    // Add it at the end
    this.values[this.size++] = val;
    // And restore the heap property
    swapUp(this.values, this.size - 1, order);
  } // put

  // +---------+---------------------------------------------------------
  // | Helpers |
  // +---------+

  /**
   * Print the standard tree representation of this heap, starting
   * a position pos, indenting values by indent.
   */
  void dump(PrintWriter pen, int pos, String indent)
  {
    if (pos >= this.size)
      {
        // Whoops!  Ran out of tree
        pen.println(indent + "<>");
      }
    else
      {
        // Otherwise, we're still in the tree
        pen.println(indent + this.values[pos]);
        dump(pen, left(pos), indent + "    ");
        dump(pen, right(pos), indent + "    ");
      } // if within the tree
  } // dump(PrintWriter)

} // class Heap<T>
