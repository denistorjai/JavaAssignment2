import implementations.MyArrayList;
import implementations.MyQueue;
import implementations.MyStack;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

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

        // If no Error, Print No Errors Found
        if (errorQ.isEmpty() && extrasQ.isEmpty()) {
            System.out.println("No errors found");
        }

        printStack(myStack);

        // Check if Stack is not empty == leftover starting tags, put them into error queue
        while (!myStack.isEmpty()) {
            String remainingTag = myStack.pop();
            errorQ.enqueue(remainingTag);
            System.out.println("Error: " + remainingTag);
        }

        // check if error q or extras q is empty.
        if (!errorQ.isEmpty() && extrasQ.isEmpty()) {
            // Report each error in errorQ as error
            while (!errorQ.isEmpty()) {
                String tag = errorQ.dequeue();
                System.out.println("Error: " + tag);
            }
        } else if (errorQ.isEmpty() && !extrasQ.isEmpty()) {
            // Report each extra tag in extrasQ as error
            while (!extrasQ.isEmpty()) {
                String tag = extrasQ.dequeue();
                System.out.println("Error: " + tag);
            }
        }

        // If both queues are not empty then peek and compare
        while (!errorQ.isEmpty() && !extrasQ.isEmpty()) {

            // tags
            String errorTag = errorQ.peek();
            String extraTag = extrasQ.peek();

            // If they don't match, dequeue from errorQ and report as error
            if (!errorTag.equals(extraTag)) {
                errorTag = errorQ.dequeue();
                System.out.println("Error: " + errorTag);
            } else {
                // If they match, dequeue from both and continue
                errorQ.dequeue();
                extrasQ.dequeue();
            }
        }

        // if there are elements still left in errorQ then report and dequeue.
        while (!errorQ.isEmpty()) {
            String tag = errorQ.dequeue();
            System.out.println("Error: " + tag);
        }

        // if there are still elements in extra q then dequeue and report.
        while (!extrasQ.isEmpty()) {
            String tag = extrasQ.dequeue();
            System.out.println("Error: " + tag);
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
            while(myStack.peek().equals(tag)) {
                errorQ.enqueue(tag);
                myStack.pop();
                System.out.println("Error: " + tag);
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

    // Temporary Stack Printer
    public static void printStack(MyStack<String> stack) {

        MyStack<String> tempStack = new MyStack<>();

        while (!stack.isEmpty()) {
            String element = stack.pop();
            System.out.println(element);
            tempStack.push(element);  // Save it to restore later
        }

        // Restore the original stack
        while (!tempStack.isEmpty()) {
            stack.push(tempStack.pop());
        }

    }


}
