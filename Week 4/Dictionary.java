public class Dictionary {

    private static final int RADIX = 26;
    private final Prefix root = new Prefix();

    public static class Prefix {

        private final Prefix[] next = new Prefix[RADIX];
        private boolean word;

        private Prefix append(char c, boolean word) {
            assertAZ(c);
            if (next[index(c)] == null) {
                next[index(c)] = new Prefix();
            }
            next[index(c)].word = next[index(c)].word || word;
            return next[index(c)];
        }

        public Prefix resolve(String s) {
            Prefix prefix = this;
            for (int i = 0; i < s.length(); i++) {
                prefix = prefix.next[index(s.charAt(i))];
                if (prefix == null) {
                    break;
                }
            }
            return prefix;
        }

        public boolean isWord() {
            return word;
        }

        private void assertAZ(char c) {
            if (index(c) < 0 || index(c) >= RADIX) {
                throw new IllegalArgumentException();
            }
        }

        private int index(char c) {
            return c - 'A';
        }
    }

    public Dictionary(String[] dictionary) {
        for (String word : dictionary) {
            Prefix node = root;
            for (int i = 0; i < word.length() - 1; i++) {
                node = node.append(word.charAt(i), false);
            }
            node.append(word.charAt(word.length() - 1), true);
        }
    }

    public Prefix getEmptyPrefix() {
        return root;
    }

    public boolean contains(String word) {
        Prefix prefix = getEmptyPrefix().resolve(word);
        if (prefix != null) {
            return prefix.isWord();
        }
        return false;
    }

}
