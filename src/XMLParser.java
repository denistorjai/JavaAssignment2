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

                // Process Stack and Queue for Errors
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

            if (tag.startsWith("</")) {
                // self closing tag ~ ignore
                System.out.println("Self closing tag" + tag);
                break;
            } else if (tag.endsWith("/>")) {
                // Handle ending tag
                System.out.println("Ending tag" + tag);
                handleEndingTag(tag);
            } else if (tag.startsWith("<")) {
                // Handle Starting tag
                System.out.println("Starting tag" + tag);
                handleStartingTag(tag);
            }

        }
    }

    // Process Stack and Queue for Errors
    public static void processErrors() {

        while (!myStack.isEmpty() && !myQueue.isEmpty()) {
            // Check if Stack is not empty, if true then enqueue Error Q
            if (!myStack.isEmpty()) {
                errorQ.enqueue(myStack.peek());
            }

            // Report error if one queue is empty while the other is not
            if ((errorQ.isEmpty() && !myStack.isEmpty()) || (!errorQ.isEmpty() && myStack.isEmpty())) {
                System.out.println("Error: " + myStack.peek());
                System.out.println("Error: " + errorQ.peek());
            }

            // Check if both queues are not empty, compare their top elements
            if (!errorQ.isEmpty() && !myStack.isEmpty()) {
                if (myStack.peek().equals(errorQ.peek())) {
                    System.out.println("Error: " + myStack.peek());
                    System.out.println("Error: " + errorQ.peek());
                    errorQ.dequeue();
                }
            } else {
                errorQ.dequeue();
                myStack.pop();
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
            String topErrorQ = errorQ.peek();
            String topStack = myStack.peek();

            // Tag matches head of errorQ
            if (topErrorQ.equals(tag)) {
                errorQ.dequeue();
            }

            // Tag matches with head of stack
            if (topStack.equals(tag)) {
                myStack.pop();
            }

            // Search Stack for Matching Start Tag
            if (myStack.contains(tag)) {

                boolean foundMatchingTag = false;

                // Loop and Pop Each item until matching tag is found
                while (!foundMatchingTag) {
                    // Get Top Tag
                    String currentTopTag = myStack.peek();

                    // If Top Tag == Tag, then set FoundMatchingTag to true
                    if (currentTopTag.equals(tag)) {
                        foundMatchingTag = true;
                    } else {
                        // Pop into Error Queue & Report as Error
                        System.out.println("Error: " + currentTopTag);
                        errorQ.enqueue(currentTopTag);
                        myStack.pop();
                    }

                }
            } else {
                // Pop into Extras queue
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
