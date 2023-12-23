/**
 * L-System Visualizer

This Java project is an implementation of an L-System (Lindenmayer System) 
visualizer, a mathematical formalism used to model the growth processes of 
biological systems, particularly plant development. The L-System consists of 
an initial axiom and a set of production rules that iteratively replace symbols 
in the axiom according to predefined patterns.

Key Components:

Node and LinkedQueue Classes:
Node represents a single element in a linked list used to implement the queue.
LinkedQueue is a queue data structure that utilizes a linked list.

L-System Simulation Method:

The lSystemSimulation method performs the simulation of the L-System.
It takes parameters such as the initial angle, starting pattern, 
expansion rules, and the number of iterations.
Utilizes a queue-based approach to efficiently apply expansion rules and 
generate the final sequence.

Main Method:

The main method serves as the entry point for the program.
It prompts the user to input parameters such as the angle, starting pattern,
expansion rules, and the number of iterations.
The expansion rules are read and stored in a map.
The L-System simulation is then executed, and the resulting sequence is 
displayed.

How to Use:

Run the program.
Enter the initial angle for the L-System.
Input the starting pattern (axiom) for the L-System.
Specify the number of production rules to define.
For each rule, enter the symbol and its replacement pattern.
Enter the number of iterations for the L-System simulation.
The program will display the resulting sequence after the specified number 
of iterations.

Example Usage:

Suppose you want to model the growth of a plant using an L-System. 
You can input the initial angle, starting pattern, rules for branching, 
and the number of iterations. The program will then generate and display the 
resulting sequence, providing a visual representation of the simulated plant structure.

This project is a versatile tool for experimenting with different L-Systems 
and observing the diverse patterns that can emerge through the application of 
iterative production rules.

 */

package lindenmayerSystemVisualizer;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LSystemVisualizerApp 
{
// Node class for the linked list used to implement the queue
private static class Node 
{
    char data;
    Node next;

    public Node(char data) 
    {
        this.data = data;
        this.next = null;
    }
}

// LinkedQueue class for the queue implementation
private static class LinkedQueue 
{
    private Node front;
    private Node rear;

    public LinkedQueue() 
    {
        this.front = null;
        this.rear = null;
    }

    public boolean isEmpty() 
    {
        return front == null;
    }

    public void enqueue(char data) 
    {
        Node newNode = new Node(data);
        if (isEmpty()) 
        {
            front = rear = newNode;
        } 
        else 
        {
            rear.next = newNode;
            rear = newNode;
        }
    }

    public char dequeue() 
    {
        if (isEmpty()) 
        {
            return '\0'; 
            // Placeholder for empty queue
        }
        char data = front.data;
        front = front.next;
        if (front == null) 
        {
            rear = null;
        }
        return data;
    }
}

// Method to perform L-system simulation
public static String lSystemSimulation(double angle, String startPattern, Map<Character, String> expansionRules, int numExpansions) 
{
    LinkedQueue queue = new LinkedQueue(); 
    // Initialize the linked queue
    StringBuilder result = new StringBuilder(); 
    // StringBuilder to store the result

    // Add start pattern to the queue
    for (char c : startPattern.toCharArray()) 
    {
        queue.enqueue(c);
    }

    // Perform expansions for the specified number of times
    while (numExpansions > 0) 
    {
        LinkedQueue tempQueue = new LinkedQueue(); 
        // Temporary queue for the next iteration

        // Process each character in the queue
        while (!queue.isEmpty()) 
        {
            char currentChar = queue.dequeue();

            if (Character.isAlphabetic(currentChar)) 
            {
                // If it's an alphabetic character, apply expansion rules
                String expansion = expansionRules.getOrDefault(currentChar, String.valueOf(currentChar));
                for (char c : expansion.toCharArray()) 
                {
                    tempQueue.enqueue(c);
                }
            } 
            else 
            {
                // Non-alphabetic character, add to the result directly
                tempQueue.enqueue(currentChar);
            }
        }

        queue = tempQueue; 
        // Update the queue for the next iteration
        numExpansions--;
    }

    // Build the final result string
    while (!queue.isEmpty()) 
    {
        result.append(queue.dequeue());
    }

    return result.toString();
}

// Main method
public static void main(String[] args) 
{
    Scanner scanner = new Scanner(System.in);

    // Read angle
    System.out.print("Enter angle: ");
    double angle = scanner.nextDouble();
    scanner.nextLine(); // Consume newline

    // Read starting pattern
    System.out.print("Enter starting pattern: ");
    String startPattern = scanner.nextLine();

    // Read number of rules
    System.out.print("Enter the number of rules: ");
    int numRules = scanner.nextInt();
    scanner.nextLine(); // Consume newline

    // Read rules and populate the expansionRules map
    Map<Character, String> expansionRules = new HashMap<>();
    for (int i = 1; i <= numRules; i++) 
    {
        System.out.print("Enter Rule " + i + ": ");
        String rule = scanner.nextLine();
        char key = rule.charAt(0); // The first symbol before the implied arrow
        expansionRules.put(key, rule.substring(1)); // Rest of the symbols after the implied arrow
    }

    // Read number of expansions
    System.out.print("Enter the number of expansions: ");
    int numExpansions = scanner.nextInt();

    scanner.close();

    // Perform L-system simulation
    String finalString = lSystemSimulation(angle, startPattern, expansionRules, numExpansions);

    // Print the resulting characters after the specified number of expansions
    System.out.println("Resulting characters after " + numExpansions + " expansions: " + finalString);
}
}


