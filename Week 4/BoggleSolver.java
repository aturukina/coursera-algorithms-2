import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class BoggleSolver {

    private final Dictionary dictionary;

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        this.dictionary = new Dictionary(dictionary);
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        Set<String> result = new HashSet<>();
        for (int i = 0; i < board.rows(); i++)
            for (int j = 0; j < board.cols(); j++) {
                Cell firstCell = new Cell(i, j);
                String firstLetter = getLetter(board, firstCell);
                Dictionary.Prefix firstPrefix = dictionary.getEmptyPrefix().resolve(firstLetter);
                if (firstPrefix != null) {
                    Queue<BoardEl> boardEls = new LinkedList<>();
                    boardEls.add(new BoardEl(firstPrefix, board.rows(), board.cols(), firstCell, firstLetter));
                    while (!boardEls.isEmpty()) {
                        BoardEl boardEl = boardEls.remove();
                        String word = boardEl.getWord();
                        Dictionary.Prefix prefix = boardEl.getPrefix();
                        if (prefix.isWord() && getScore(word) > 0) {
                            result.add(word);
                        }
                        boardEl.getUnvisitedNeighbours().forEach((cell) -> {
                            String letter = getLetter(board, cell);
                            Dictionary.Prefix nextPrefix = boardEl.getPrefix().resolve(letter);
                            if (nextPrefix != null) {
                                boardEls.add(new BoardEl(nextPrefix, boardEl, cell, word + letter));
                            }
                        });
                    }
                }
            }
        return result;
    }

    private String getLetter(BoggleBoard board, Cell cell) {
        char c = board.getLetter(cell.getRow(), cell.getCol());
        if (c == 'Q') {
            return "QU";
        }
        return String.valueOf(c);
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        return dictionary.contains(word) ? getScore(word) : 0;
    }

    private int getScore(String word) {
        int length = word.length();
        if (length < 3 ) {
            return 0;
        }
        if (length < 5) {
            return 1;
        }
        if (length < 6) {
            return 2;
        }
        if (length < 7) {
            return 3;
        }
        if (length < 8) {
            return 5;
        }
        return 11;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }

}