import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.*;

public class BurrowsWheeler {

    private static final int R = 256;

    // apply Burrows-Wheeler transform, reading from standard input and writing to standard output
    public static void transform() {
        String s = BinaryStdIn.readString();
        CircularSuffixArray array = new CircularSuffixArray(s);
        int first = 0;
        char[] transformed = new char[array.length()];
        for (int i = 0; i < array.length(); i++) {
            if (array.index(i)  == 0) {
                first = i;
            }
            transformed[i] = s.charAt((array.index(i) - 1 + array.length()) % array.length());
        }
        BinaryStdOut.write(first);
        for (int i = 0; i < transformed.length; i++) {
            BinaryStdOut.write(transformed[i]);
        }
        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler inverse transform, reading from standard input and writing to standard output
    public static void inverseTransform() {
        int first = BinaryStdIn.readInt();
        char[] lastColumn = BinaryStdIn.readString().toCharArray();
        char[] firstColumn = sort(lastColumn);

        Map<Character, Collection<Integer>> positions = new HashMap<>();
        for (int i = 0; i < lastColumn.length; i++) {
            positions.computeIfAbsent(lastColumn[i], c -> new ArrayList<>()).add(i);
        }

        Map<Character, Iterator<Integer>> iterators = new HashMap<>();
        int[] next = new int[firstColumn.length];
        for (int i = 0; i < firstColumn.length; i++) {
            Iterator<Integer> iterator = iterators.computeIfAbsent(firstColumn[i], c -> positions.get(c).iterator());
            next[i] = iterator.next();
        }

        for (int i = 0; i < firstColumn.length; i++) {
            BinaryStdOut.write(firstColumn[first]);
            first = next[first];
        }
        BinaryStdOut.close();
    }

    private static char[] sort(char[] input) {
        int[] occurrences = new int[R];
        for (int i = 0; i < input.length; i++) {
            occurrences[input[i]]++;
        }
        char[] output = new char[input.length];
        int index = 0;
        for (int i = 0; i < occurrences.length; i++) {
            Arrays.fill(output, index, index + occurrences[i], (char) i);
            index += occurrences[i];
        }
        return output;
    }

    // if args[0] is '-', apply Burrows-Wheeler transform
    // if args[0] is '+', apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        String operation = args[0];
        if ("-".equals(operation)) {
            transform();
        }
        if ("+".equals(operation)) {
            inverseTransform();
        }
    }
}