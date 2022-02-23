/*
 * M415 2019-2020: additional algorithmic
 */
package TD3.interval;
/**
 * @author gh002742
 */
import java.util.Arrays;
import java.util.Comparator;

/**
 * Interval with integer endpoints
 * <p>
 * adapted from http://introcs.cs.princeton.edu/java/32class/Interval.java.html
 * <p>
 * nothing else at this URL is useful for this lab
 *
 * @author m6k415
 * @version 1.17
 */

public class Interval implements Comparable<Interval> {
    private final int start;
    private final int end;

    /**
     * @param start : starting time
     * @param end   : ending / finishing time
     */
    public Interval(int start, int end) {
        assert (start <= end);
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    /**
     * does b intersect a ?
     *
     * @param b : one given interval
     * @return : true if b intersects a, false otherwise
     * <p>
     * TODO : test with:
     * <p>
     * a or b empty returns false
     * <p>
     * b.end <= a.start returns false
     * <p>
     * b.start >= a.end returns false
     */
    public boolean intersects(Interval b) {
        Interval a = this;
        /*-
         *   [    a    ]
         *   []
         */
        if (b.start == b.end && b.start == a.start) {
            return false;
        }
        /*-
         * b is not empty
         *   [    a    ]
         *   [  b          ]
         *     [  b  ]
         */
        if (b.start < a.end && b.start >= a.start) {
            return true;
        }
        /*-
         * b is not empty and starts outside a
         *     [    a    ]
         *   [  b            ]
         */
        return a.start < b.end && a.start > b.start;
    }

    public String toString() {
        return String.format("[ %2d, %2d]", start, end);
    }

    /**
     * display interval with dashes [-------]
     * <p>
     * Usable only for small start / end values
     */
    public String display() {
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < start; i++) {
            final StringBuilder append = line.append(" ");
        }
        line.append("[");

        for (int i = 0; i < (end - start) - 1; i++) {
            line.append("-");
        }
        line.append("]");
        return line.toString();
    }

    /**
     * https://docs.oracle.com/javase/tutorial/collections/interfaces/order.html
     * <p>
     * https://docs.oracle.com/javase/8/docs/api/java/lang/Comparable.html
     * <p>
     * When implements Comparable, compareTo defines the natural comparison
     * method of the class. Lists (and arrays) of objects that implement this
     * interface can be sorted automatically by Collections.sort (and
     * Arrays.sort)
     */
    @Override
    public int compareTo(Interval arg) { // start
        if (this.start < arg.start)
            return -1; // less than
        else // equal to
            // greater than
            // less than
            if (this.start > arg.start)
                return 1; // greater than
            else return Integer.compare(this.end, arg.end); // equal to
    }

    /**
     * https://docs.oracle.com/javase/8/docs/api/java/util/Comparator.html
     * <p>
     * Comparators can be passed to a sort method (such as Collections.sort or
     * Arrays.sort) : allows to sort some objects in an order other than their
     * natural ordering.
     */
    public static Comparator<Interval> IntervalSizeComparator = (arg0, arg1) -> Integer.compare(arg0.end - arg0.start, arg1.end - arg1.start);

    public static Comparator<Interval> IntervalFinishComparator = new Comparator<>() {
        public int compare(Interval arg0, Interval arg1) {
            return Integer.compare(arg0.end, arg1.end);
        }
    };

    public static void main(String[] args) {

        Interval a = new Interval(15, 20);
        Interval b = new Interval(25, 30);
        Interval c = new Interval(10, 40);
        Interval d = new Interval(40, 50);
        Interval e = new Interval(50, 60);
        Interval f = new Interval(60, 60);
        Interval g = new Interval(0, 15);
        Interval h = new Interval(0, 0);

        Interval[] interval = {a, b, c, d, e, f, g, h};
        for (int i = 0; i < interval.length; i++) {
            System.out.println((char) (97 + i) + " = " + interval[i]);
        }

        System.out.println("    0123456789012345678901234567890123456789012345678901234567890123456789");
        System.out.println("g = " + g.display());
        System.out.println("a = " + a.display());
        System.out.println("b = " + b.display());
        System.out.println("c = " + c.display());
        System.out.println("d = " + d.display());
        System.out.println("e = " + e.display());
        System.out.println("f = " + f.display());
        System.out.println("g = " + g.display());
        System.out.println("h = " + h.display());

        System.out.println("b intersects a = " + b.intersects(a));
        System.out.println("a intersects b = " + a.intersects(b));
        System.out.println("a intersects c = " + a.intersects(c));
        System.out.println("a intersects d = " + a.intersects(d));
        System.out.println("b intersects c = " + b.intersects(c));
        System.out.println("b intersects d = " + b.intersects(d));
        System.out.println("c intersects d = " + c.intersects(d));
        System.out.println("d intersects e = " + d.intersects(e));
        System.out.println("e intersects f = " + e.intersects(f));
        System.out.println("e intersects e = " + e.intersects(e));
        System.out.println("g intersects h = " + g.intersects(h));
        System.out.println("h intersects g = " + h.intersects(g));

        Arrays.sort(interval);
        // Check order (start)
        System.out.println("          0123456789012345678901234567890123456789012345678901234567890123456789");
        for (Interval i : interval) {
            System.out.println(i + " " + i.display());
        }
    }
}