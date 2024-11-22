import implementations.MyArrayList;
import implementations.MyQueue;
import implementations.MyStack;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class XMLParser {

    // Create Stack and Queue Classes
    static MyStack<String> myStack = new MyStack<>();
    static MyQueue<String> myQueue = new MyQueue<>();

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
            // Get File, if Exists, Read Lines and Store in MyArrayList
            File file = new File(filepath);
            if (file.exists()) {

                // My Array List & Scanner
                MyArrayList<String> lines = new MyArrayList<>();
                Scanner scanner = new Scanner(file);

                while (scanner.hasNextLine()) {
                    lines.add(scanner.nextLine());
                }
                scanner.close();

                // Loop Through Data & Pass into Line Parse Function
                for (int i = 0; i < lines.size(); i++) {
                    LineParse(lines.get(i));
                }

                while (!myStack.isEmpty() && !myQueue.isEmpty() ) {

                    // Check if Stack is not empty, if true then enqueue Error Q
                    if (!myStack.isEmpty()) {
                        errorQ.enqueue(myStack.peek());
                    }

                    // if either queue is empty but not both then report each element in both queues as error
                    if ( (errorQ.isEmpty() & !myStack.isEmpty()) || (!errorQ.isEmpty() & myStack.isEmpty()) ) {
                        // Report as error both queues
                        System.out.println("Error:" + myStack.peek() );
                        System.out.println("Error:" + errorQ.peek() );
                    }

                    // if both queues are not empty, peek both queues, and dequeue and report as Error if they don't match
                    if (!errorQ.isEmpty() & !myStack.isEmpty() ) {
                        if (myStack.peek().equals(errorQ.peek())) {
                            System.out.println("Error:" + myStack.peek() );
                            System.out.println("Error:" + errorQ.peek() );
                            errorQ.dequeue();
                        }
                    } else {
                        errorQ.dequeue();
                        myStack.pop();
                    }

                }

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

            // Stop if Line doesn't exist
            if (start == -1 || end == -1) {
                break;
            }

            // Get tag
            String tag = line.substring(start + 1, end);
            line = line.substring(end + 1);

            // Determine Tag Type
            switch (tag) {
                case "</":
                    // Handle Self Closing Tag ~ Ignore
                    break;
                case "/>":
                    // Handle Ending Tag
                    handleEndingTag(tag);
                    break;
                case "<":
                    // Handle Starting Tag
                    handleStartingTag(tag);
                    break;
            }

        }
    }

    // Handle Ending Tag Logic
    public static void handleEndingTag(String tag) {

        // Check if Stack is Empty
        if (myStack.isEmpty()) {
            // Unmatched Closing Tag, enqueue into error queue
            errorQ.enqueue(tag);
        } else {

            // Get Top of Error Q and Top of Stack
            String TopErrorQStack = errorQ.peek();
            String TopStack = myStack.peek();

            // Tag matches head of errorQ
            if (TopErrorQStack.equals(tag)) {
                errorQ.dequeue();
            }

            // Tag matches with head of stack
            if (TopStack.equals(tag)) {
                myStack.pop();
            }

            // Search Stack for Matching Start Tag
            String MatchingTag = String.valueOf(myStack.contains(tag));

            // Matching Tag Found
            if (MatchingTag.equals(tag)) {

                boolean FoundMatchingTag = false;

                // Loop and Pop Each item into
                while (!FoundMatchingTag) {

                    // Get Top Tag
                    String CurrentTopTag = myStack.peek();

                    // If Top Tag == Tag then set Found Matching to true
                    if (CurrentTopTag.equals(tag)) {
                        FoundMatchingTag = true;
                    } else {
                        // Pop into Error Queue & Report as Error
                        System.out.println("Error:" + CurrentTopTag );
                        errorQ.enqueue(CurrentTopTag);
                        myStack.pop();
                    }

                }

            } else {
                // pop into Extras queue
                extrasQ.enqueue(tag);
            }

        }

    }

    // Handle Starting Tag Logic
    public static void handleStartingTag(String tag) {
        // Starting Tag ~ Push on Stack
        myStack.push(tag);
    }

}
