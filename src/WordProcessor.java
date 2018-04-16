import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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


        Stream<String> wordStream = Files.lines(Paths.get(filepath));

        wordStream = wordStream.map(String::trim)
                        .filter(x -> x != null && !x.equals(""))
                        .map(String::toUpperCase); // makes all lines trimmed and uppercase. remove
                                                   // null or empty lines

        return wordStream;
    }

    /**
     * Adjacency between word1 and word2 is defined by: if the difference between word1 and word2 is
     * of 1 char replacement 1 char addition 1 char deletion then word1 and word2 are adjacent else
     * word1 and word2 are not adjacent
     * 
     * Note: if word1 is equal to word2, they are not adjacent 
     * 
     * @param word1 first word
     * @param word2 second word
     * @return true if word1 and word2 are adjacent else false
     */

    public static boolean isAdjacent(String word1, String word2) {

        if (word1.equals(word2))
            return false; // they are the same word so they are not adjacent

        int aE = word1.length(); // Ending index of a
        int bE = word2.length();

        if(Math.abs(aE-bE)>1) return false;
        

        int aP = 0; // pointer to character in a
        int bP = 0;

        boolean foundDifference = false; // tracks whether a difference has been found
        boolean iterate = true;
        String filter = "GIMLETS";
        if (word1.equals(filter)) {
            System.out.println("                                                    : " + word1 + ", " + word2);
        }

        while (iterate) { // loop is exited with return statements (below) when an ending character is reached
            if (word1.charAt(aP) != word2.charAt(bP)) { // a difference is detected
                if (foundDifference) {  // a previous difference had been detcted, meaning this is the second difference.
                    if (word1.equals(filter)) 
                        System.out.println("    false: second error: " + word1 + ", " + word2);
                    return false;
                }
                foundDifference = true;
                

                if (word2.length() > bP + 1 && word1.charAt(aP) == word2
                                .charAt(bP + 1)) { // addition in b, advance b's charcater pointer
                    if (word1.equals(filter))  
                        System.out.println("      error: addition in b: " + word1.charAt(aP) + ", " + word2.charAt(bP)  + ", " + word1 + ", " + word2);
                    bP++; 
                } else if (word1.length() > aP + 1 && word1
                                .charAt(aP + 1) == word2.charAt(bP)) { // addition in a, advance a's character pointer
                    if (word1.equals(filter)) 
                        System.out.println("      error: addition in a: " + word1.charAt(aP) + ", " + word2.charAt(bP) + ", " + word1 + ", " + word2);
                    aP++; 
                } else { // substitution, advance both pointers
                    aP++;
                    bP++;
                } 
            } else { // no difference in the current character, advance both pointers.
                aP++;
                bP++;
            }

            if (aP == aE && bP == bE) { // 0 or 1 errors detected and every character has been
                                        // checked.
                return true;
            } else if (aP == aE || bP == bE) { // the end of one word has been reached and words are
                                               // different lengths. Return true only if no previous
                                               // errors have been found.
                return (!foundDifference);
            }

        }

        return false; // this line of code should never be reached because result is returned upon
                      // while loop termination
    }

}
