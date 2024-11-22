import implementations.MyArrayList;
import implementations.MyQueue;
import implementations.MyStack;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class XMLParser {

    // Create Stack and Queue Classes
    static MyStack<String> myStack = new MyStack<>();

    // Error Queue
    static MyQueue<String> errorQ = new MyQueue<>();
    static MyQueue<String> extrasQ = new MyQueue<>();

    // Main Function
    public static void main(String[] args) {
        // Get File Path
        if (args.length == 0) {
            System.out.println("Usage: java XMLParser <filepath>");
            System.exit(1);
        }
        String filepath = args[0];

        // Try & Catch
        try {
            File file = new File(filepath);
            if (file.exists()) {
                MyArrayList<String> lines = new MyArrayList<>();

                // Use FileReader and BufferedReader
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    lines.add(line);
                }

                bufferedReader.close();
                fileReader.close();

                // Parse each line
                for (int i = 0; i < lines.size(); i++) {
                    LineParse(lines.get(i));
                }

                processErrors();

            } else {
                System.out.println("File does not exist");
            }
        } catch (IOException error) {
            System.err.println(error.getMessage());
        }
    }

    // Line by Line Parser
    public static void LineParse(String line) {
        while (!line.isEmpty()) {

            // Start & End Indexes
            int start = line.indexOf('<');
            int end = line.indexOf('>');

            // Stop if no valid tag found
            if (start == -1 || end == -1) {
                break;
            }

            // Get tag
            String tag = line.substring(start, end + 1).trim();
            line = line.substring(end + 1);  // Remove processed part of the line

            // Raw Tag for parsing
            String RawTag = extractTagName(tag);

            if (tag.endsWith("/>") || tag.startsWith("<?")) {
                // self closing tag ~ ignore
                continue;
            } else if (tag.startsWith("</")) {
                // Handle ending tag
                handleEndingTag(RawTag);
            } else if (tag.startsWith("<")) {
                // Handle Starting tag
                handleStartingTag(RawTag);
            }

        }
    }

    // Process Stack and Queue for Errors
    public static void processErrors() {

        MyQueue<String> ErrorsFound = new MyQueue<>();

        // If no Error, Print No Errors Found
        if (errorQ.isEmpty() && extrasQ.isEmpty()) {
            System.out.println("No errors found");
        } else {
            System.out.println("======= ERROR LOG =======");
        }

        // Push stack
        while (!myStack.isEmpty()) {
            errorQ.enqueue(myStack.pop());
        }

        // IF EITHER QUEUE IS EMPTY BUT NOT BOTH, REPORT EACH E INTO BOTH QUEUES AS ERROR
        while ( (errorQ.isEmpty() && !extrasQ.isEmpty()) || (!errorQ.isEmpty() && extrasQ.isEmpty()) ) {
            System.out.println(errorQ.dequeue());
            System.out.println(extrasQ.dequeue());
        }

        // Find duplicate tags in error queue
        while (!errorQ.isEmpty()) {
            String CurrentTopStack = errorQ.peek();
            errorQ.dequeue();
            if (!errorQ.isEmpty()) {
                String NextTopStack = errorQ.peek();
                if (!CurrentTopStack.equals(NextTopStack)) {
                    ErrorsFound.enqueue(CurrentTopStack);
                }
            }
        }

        for (int i = ErrorsFound.size() - 1; i >= 0; i--) {
            String CurrentTopStack = extrasQ.peek();
            String CurrentTopErrorStack = ErrorsFound.peek();
            if (CurrentTopStack.equals(CurrentTopErrorStack)) {
                extrasQ.dequeue();
                ErrorsFound.dequeue();
            }
        }

        while (!ErrorsFound.isEmpty()) {
            String CurrentTopStack = ErrorsFound.peek();
            System.out.println("<" + CurrentTopStack + ">");
            ErrorsFound.dequeue();
        }

        while (!extrasQ.isEmpty()) {
            String CurrentTopStack = extrasQ.peek();
            System.out.println("<" + CurrentTopStack + ">");
            extrasQ.dequeue();
        }

    }

    // Handle Ending Tag Logic
    public static void handleEndingTag(String tag) {

        // if Stack is empty then add tag to queue because no matching starting tag
        if (myStack.isEmpty()) {
            errorQ.enqueue(tag);
            return;
        }

        // If Ending Tag matches with Top of My Stack = (Starting Tag & Ending Tag) then clear
        String TopStartingTag = myStack.peek();

        // If it matches then pop and return
        if (TopStartingTag.equals(tag)) {
            myStack.pop();
            return;
        }

        // if ending tag matches head of error queue then dequeue and ignore
        if (!errorQ.isEmpty() && errorQ.peek().equals(tag)) {
            errorQ.dequeue();
            return;
        }

        // If Stack has starting tag, then remove top until it matches, if not matches then add to error
        if (myStack.contains(tag)) {
            while(!myStack.peek().equals(tag)) {
                errorQ.enqueue(tag);
                myStack.pop();
            }
        } else {
            extrasQ.enqueue(tag);
        }

    }

    // Handle Starting Tag Logic
    public static void handleStartingTag(String tag) {
        // Starting Tag ~ Push on Stack
        myStack.push(tag);
    }

    // Unformatted tag
    private static String extractTagName(String tag) {
        if (tag.startsWith("</")) {
            return tag.substring(2, tag.length() - 1).split(" ")[0];
        } else if (tag.endsWith("/>")) {
            return tag.substring(1, tag.length() - 2).split(" ")[0];
        } else if (tag.startsWith("<")) {
            return tag.substring(1, tag.length() - 1).split(" ")[0];
        }
        return tag;
    }

}
