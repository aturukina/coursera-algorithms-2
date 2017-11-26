import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.LinkedList;

public class MoveToFront {

    private static final int R = 256;

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        LinkedList<Character> sequence = initSequence();
        while (!BinaryStdIn.isEmpty()) {
            char read = BinaryStdIn.readChar();
            int index = sequence.indexOf(read);
            sequence.remove(index);
            BinaryStdOut.write((char) index);
            sequence.addFirst(read);
        }
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        LinkedList<Character> sequence = initSequence();
        while (!BinaryStdIn.isEmpty()) {
            int index = BinaryStdIn.readChar();
            char character = sequence.remove(index);
            BinaryStdOut.write(character);
            sequence.addFirst(character);
        }
        BinaryStdOut.close();
    }

    private static LinkedList<Character> initSequence() {
        LinkedList<Character> sequence = new LinkedList<>();
        for (short i = 0; i < R; i++) {
            sequence.add((char) i);
        }
        return sequence;
    }

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args) {
        String operation = args[0];
        if ("-".equals(operation)) {
            encode();
        }
        if ("+".equals(operation)) {
            decode();
        }
    }
}