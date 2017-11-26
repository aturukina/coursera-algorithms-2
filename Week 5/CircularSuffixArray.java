import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

/**
 * Created by aturukin on 11/26/2017.
 */
public class CircularSuffixArray {

    private final String s;
    private final CircularSuffix[] sortedSuffixes;

    private class CircularSuffix implements Comparable<CircularSuffix> {
        private final String input;
        private final int position;

        public CircularSuffix(String input, int position) {
            this.input = input;
            this.position = position;
        }

        public int length() {
            return input.length();
        }

        @Override
        public int compareTo(CircularSuffix other) {
            for (int i = 0; i < Math.min(other.length(), length()); i++) {
                if (charAt(i) > other.charAt(i)) {
                    return 1;
                }
                if (charAt(i) < other.charAt(i)) {
                    return -1;
                }
            }
            if (length() > other.length()) {
                return 1;
            }
            if (length() < other.length()) {
                return -1;
            }
            return 0;
        }

        public char charAt(int index) {
            return s.charAt((position + index) % s.length());
        }

        public int getPosition() {
            return position;
        }
    }
    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null) {
            throw new IllegalArgumentException();
        }
        this.s = s;
        sortedSuffixes = new CircularSuffix[s.length()];
        for (int i = 0; i < sortedSuffixes.length; i++) {
            sortedSuffixes[i] = new CircularSuffix(s, i);
        }
        Arrays.sort(sortedSuffixes);
    }

    // length of s
    public int length() {
        return s.length();
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i >= length()) {
            throw new IllegalArgumentException();
        }
        return sortedSuffixes[i].getPosition();
    }

    // unit testing (required)
    public static void main(String[] args) {
        CircularSuffixArray array = new CircularSuffixArray("ABRACADABRA!");
        StdOut.println(array.length());
        StdOut.println(array.index(0));
        StdOut.println(array.index(1));
        StdOut.println(array.index(2));
        StdOut.println(array.index(3));
        StdOut.println(array.index(4));
        StdOut.println(array.index(5));
        StdOut.println(array.index(6));
        StdOut.println(array.index(7));
        StdOut.println(array.index(8));
        StdOut.println(array.index(9));
        StdOut.println(array.index(10));
        StdOut.println(array.index(11));
    }
}
