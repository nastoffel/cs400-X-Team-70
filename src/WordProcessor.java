import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * This class contains some utility helper methods
 * 
 * @author Jessie Steckling (jsteckling@cs.wisc.edu)
 */
public class WordProcessor {

    ArrayList<String> words;


    /**
     * Gets a Stream of words from the filepath.
     * 
     * The Stream should only contain trimmed, non-empty and UPPERCASE words.
     * 
     * @see <a href=
     *      "http://www.oracle.com/technetwork/articles/java/ma14-java-se-8-streams-2177646.html">java8
     *      stream blog</a>
     * 
     * @param filepath file path to the dictionary file
     * @return Stream<String> stream of words read from the filepath
     * @throws IOException exception resulting from accessing the filepath
     */
    public static Stream<String> getWordStream(String filepath)
                    throws IOException {
        /**
         * @see <a href=
         *      "https://docs.oracle.com/javase/8/docs/api/java/nio/file/Files.html">java.nio.file.Files</a>
         * @see <a href=
         *      "https://docs.oracle.com/javase/8/docs/api/java/nio/file/Paths.html">java.nio.file.Paths</a>
         * @see <a href=
         *      "https://docs.oracle.com/javase/8/docs/api/java/nio/file/Path.html">java.nio.file.Path</a>
         * @see <a href=
         *      "https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html">java.util.stream.Stream</a>
         * 
         *      class Files has a method lines() which accepts an interface Path object and produces
         *      a Stream<String> object via which one can read all the lines from a file as a
         *      Stream.
         * 
         *      class Paths has a method get() which accepts one or more strings (filepath), joins
         *      them if required and produces a interface Path object
         * 
         *      Combining these two methods: Files.lines(Paths.get(<string filepath>)) produces a
         *      Stream of lines read from the filepath
         * 
         *      Once this Stream of lines is available, you can use the powerful operations
         *      available for Stream objects to combine multiple pre-processing operations of each
         *      line in a single statement.
         * 
         *      Few of these features: 1. map( ) [changes a line to the result of the applied
         *      function. Mathematically, line = operation(line)] - trim all the lines - convert all
         *      the lines to UpperCase - example takes each of the lines one by one and apply the
         *      function toString on them as line.toString() and returns the Stream: streamOfLines =
         *      streamOfLines.map(String::toString)
         * 
         *      2. filter( ) [keeps only lines which satisfy the provided condition] - can be used
         *      to only keep non-empty lines and drop empty lines - example below removes all the
         *      lines from the Stream which do not equal the string "apple" and returns the Stream:
         *      streamOfLines = streamOfLines.filter(x -> x != "apple");
         * 
         *      3. collect( ) [collects all the lines into a java.util.List object] - can be used in
         *      the function which will invoke this method to convert Stream<String> of lines to
         *      List<String> of lines - example below collects all the elements of the Stream into a
         *      List and returns the List: List<String> listOfLines =
         *      streamOfLines.collect(Collectors::toList);
         * 
         *      Note: since map and filter return the updated Stream objects, they can chained
         *      together as: streamOfLines.map(...).filter(a -> ...).map(...) and so on
         */


        Stream<String> wordStream;

        try {
            wordStream = Files.lines(Paths.get(filepath));

             wordStream = wordStream.map(String::trim)
                        .filter(x -> x != null && !x.equals(""))
                        .map(String::toUpperCase); // makes all lines trimmed and uppercase. remove
                                                   // null or empty lines
        } catch (IOException e) { // file cannot be read
            e.printStackTrace();
            wordStream = Stream.empty();
        }

        wordStream.close();
        return wordStream;
    }

    /**
     * Adjacency between word1 and word2 is defined by: if the difference between word1 and word2 is
     * of 1 char replacement 1 char addition 1 char deletion then word1 and word2 are adjacent else
     * word1 and word2 are not adjacent
     * 
     * Note: if word1 is equal to word2, they are not adjacent //TODO fix this
     * 
     * @param word1 first word
     * @param word2 second word
     * @return true if word1 and word2 are adjacent else false
     */

    public static boolean isAdjacent(String word1, String word2) {

        if (word1.equals(word2))
            return false; // they are the same word so they are not adjacent

        System.out.println(word1 + ", " + word2);
        int aE = word1.length(); // Ending index of a
        int bE = word2.length();

        /*
         * if(Math.abs(aE-bE)>1) { System.out.println("    false: length more than 1 off"); return
         * false; }
         */

        int aP = 0; // pointer to character in a
        int bP = 0;

        boolean foundDifference = false; // tracks whether a difference has been found
        boolean iterate = true;

        while (iterate) {
            if (word1.charAt(aP) != word2.charAt(bP)) {
                System.out.println("  " + word1.charAt(aP) + "!="
                                + word2.charAt(bP));
                if (foundDifference) {
                    System.out.println("    false: second error");
                    return false;
                }
                foundDifference = true;

                if (word1.charAt(aP) == word2.charAt(bP + 1)) {
                    System.out.println("      error: addition in b");
                    bP++; // b has extra
                } else if (word1.charAt(aP + 1) == word2.charAt(bP)) {
                    System.out.println("      error: addition in a");
                    aP++; // a has extra
                } else if (word1.charAt(aP + 1) == word2.charAt(bP + 1)) { // substitution
                    System.out.println("      error: substitution");
                    aP++;
                    bP++;
                }
            }
            aP++;
            bP++;

            if (aP == aE && bP == bE) {
                System.out.println("    true");
                return true;
            } else if (aP == aE || bP == bE) {
                System.out.println("    " + (!foundDifference));
                return (!foundDifference);
            }

        }

        return false; // this line of code should never be reached because result is returned upon
                      // while loop termination
    }

}
