import java.util.*;
import java.io.*;

public class CountKeywords {
    public static void main(String[] args) throws Exception {
        try (Scanner input = new Scanner(System.in)) {
            System.out.print("Enter a Java source file: ");
            String filename = input.nextLine();

            File file = new File(filename);
            if (file.exists()) {
                System.out.println("The number of keywords in " + filename
                        + " is " + countKeywords(file));
            } else {
                System.out.println("File " + filename + " does not exist");
            }
        }
    }

    public static int countKeywords(File file) throws Exception {
        // Array of all Java keywords + true, false, and null
        String[] keywordString = { "abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class",
                "const", "continue", "default", "do", "double", "else", "enum", "extends", "for", "final", "finally",
                "float", "goto", "if", "implements", "import", "instanceof", "int", "interface", "long", "native",
                "new", "package", "private", "protected", "public", "return", "short", "static", "strictfp", "super",
                "switch", "synchronized", "this", "throw", "throws", "transient", "try", "void", "volatile", "while",
                "true", "false", "null" };

        Set<String> keywordSet = new HashSet<>(Arrays.asList(keywordString));
        int count = 0;
        boolean inComment = false;
        boolean inString = false;

        try (Scanner input = new Scanner(file)) {
            while (input.hasNext()) {
                String word = input.next();

                // Check if the word is within a comment or string
                if (word.startsWith("/*")) {
                    inComment = true;
                }

                if (word.endsWith("*/")) {
                    inComment = false;
                }

                if (inComment || inString) {
                    continue;
                }

                if (word.startsWith("\"")) {
                    inString = true;
                }

                if (word.endsWith("\"") && !word.endsWith("\\\"")) {
                    inString = false;
                }

                // Remove quotes and escape characters from strings
                if (inString) {
                    word = word.replaceAll("^\"|\"$", "").replaceAll("\\\\\"", "");
                }

                if (keywordSet.contains(word))
                    count++;
            }
        }

        return count;
    }
}
