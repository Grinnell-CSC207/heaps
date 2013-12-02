package taojava.util;

import java.io.PrintWriter;

import java.util.Comparator;
import java.util.Random;

/**
 * Some fun experiments with heaps.
 */
public class HeapExpt {

    // +-----------+-------------------------------------------------------
    // | Constants |
    // +-----------+

    /**
     * A comparator for integers.
     */
    static Comparator<Integer> orderInts = new Comparator<Integer>() {
        public int compare(Integer left, Integer right) {
            return left.compareTo(right);
        } // compare(Integer, Integer)
    }; // new Comparator<Integer>

    /**
     * A comparator for strings.
     */
    static Comparator<String> orderStrings = new Comparator<String>() {
        public int compare(String left, String right) {
            return left.compareTo(right);
        } // compare(String, String)
    }; // new Comparator<String>

    // +-------------+-----------------------------------------------------
    // | Experiments |
    // +-------------+

    /**
     * A fairly verbose experiment.  Add and remove some values, looking at the
     * heap after each step.
     */
    public static void expt0(PrintWriter pen) {
        Heap<String> strings = new Heap<String>(4, orderStrings);
        add(pen, strings, new String[] { "c", "a", "b", "c", "x", "d", "c" });
        remove(pen, strings, 4);
        add(pen, strings, new String[] { "w", "x", "y", "w", "x", "y" });
        remove(pen, strings, 4);
    } // expt0(PrintWriter)

    /**
     * A bit less verbose than the first.  More values, more fun.
     */
    public static void expt1(PrintWriter pen) {
        Heap<String> strings = new Heap<String>(4, orderStrings);
        add(null, strings, new String[] { "twas", "brillig", "and", "the",
               "slithy", "toves", "did", "gyre", "and", "gimble", "in",
               "the", "wabe" });
        pen.println("Added lots of values");
        strings.dump(pen);
        pen.println("Removing six values");
        remove(null, strings, 6);
        strings.dump(pen);
    } // expt1(PrintWriter)

    /**
     * A somewhat more randomized experiment.
     */
    public static void expt2(PrintWriter pen) {
        Heap<Integer> ints = new Heap<Integer>(4, orderInts);
        Random generator = new Random();
        for (int i = 0; i < 20; i++) {
            ints.put(generator.nextInt(10));
        } // for
        pen.println("After adding twenty random values.");
        ints.dump(pen);
        remove(null, ints, 8);
        pen.println("After removing ten values.");
        ints.dump(pen);
    } // expt2(PrintWriter)

    // +---------+---------------------------------------------------------
    // | Helpers |
    // +---------+

    /**
     * Add all of the values in values to heap. 
     *
     * If pen is not null, shows each step.
     */
    public static <T> void add(PrintWriter pen, Heap<T> heap, T[] values) {
        for (T v : values) {
            if (pen != null) { 
                pen.println("put[" + v + "]"); 
            } // if (pen != null)
            heap.put(v);
            if (pen != null) {
                heap.dump(pen);
                pen.println();
            } // if (pen != null)
        } // for
    } // add(Heap<T>, T[])

    /**
     * Remove n values from the heap, showing the heap at each stage.
     */
    public static <T> void remove(PrintWriter pen, Heap<T> heap, int n) {
        for (int i = 0; i < n; i++) {
            if (pen != null) {
                pen.print("get() => ");
                try {
                    pen.println(heap.get());
                } catch (Exception e) {
                    pen.println("FAILED: " + e);
                } // try/catch
                heap.dump(pen);
                pen.println();
            } else {
                try {
                    heap.get();
                } catch (Exception e) {
                    System.err.println("Could not get b/c " + e);
                } // try/catch
            } // if there's no pen
        } // for
    } // remove
     
    // +------+------------------------------------------------------------
    // | Main |
    // +------+

    public static void main(String[] args) {
        PrintWriter pen = new PrintWriter(System.out, true);
        expt2(pen);
        pen.close();
    } // main(String[])

} // class HeapExpt
