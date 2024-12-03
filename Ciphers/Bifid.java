package org.example;

import java.awt.Point;
import java.util.*;

public final class Bifid {

    private final char[][] grid;
    private final Map<Character, Point> coordinates = new HashMap<>();

    public Bifid(int n, String text) {
        if (text.length() != n * n) {
            throw new IllegalArgumentException("Incorrect length of text for the grid size.");
        }

        grid = new char[n][n];
        int index = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                char ch = text.charAt(index++);
                grid[row][col] = ch;
                coordinates.put(ch, new Point(row, col));
            }
        }

        if (n == 5) {
            coordinates.put('J', coordinates.get('I'));
        }
    }

    public String encrypt(String text) {
        List<Integer> rowOne = new ArrayList<>();
        List<Integer> rowTwo = new ArrayList<>();

        for (char ch : text.toCharArray()) {
            Point coordinate = coordinates.get(ch);
            rowOne.add(coordinate.x);
            rowTwo.add(coordinate.y);
        }

        rowOne.addAll(rowTwo);
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < rowOne.size(); i += 2) {
            result.append(grid[rowOne.get(i)][rowOne.get(i + 1)]);
        }

        return result.toString();
    }

    public String decrypt(String text) {
        List<Integer> rows = new ArrayList<>();

        for (char ch : text.toCharArray()) {
            Point coordinate = coordinates.get(ch);
            rows.add(coordinate.x);
            rows.add(coordinate.y);
        }

        int middle = rows.size() / 2;
        List<Integer> rowOne = rows.subList(0, middle);
        List<Integer> rowTwo = rows.subList(middle, rows.size());

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < middle; i++) {
            result.append(grid[rowOne.get(i)][rowTwo.get(i)]);
        }

        return result.toString();
    }

    public void display() {
        Arrays.stream(grid).forEach(row -> System.out.println(Arrays.toString(row)));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the plaintext message: ");
        String plaintext = scanner.nextLine().toUpperCase().replace(" ", "");

        System.out.print("Enter the grid size (e.g., 5 for 5x5): ");
        int gridSize = scanner.nextInt();
        scanner.nextLine();

        int expectedLength = gridSize * gridSize;
        String alphabet;
        do {
            System.out.print("Enter the alphabet for the Polybius square (length " + expectedLength + "): ");
            alphabet = scanner.nextLine().toUpperCase();
        } while (alphabet.length() != expectedLength);

        Bifid bifid = new Bifid(gridSize, alphabet);

        System.out.println("Using Polybius square:");
        bifid.display();

        String encodedText = bifid.encrypt(plaintext);
        String decodedText = bifid.decrypt(encodedText);

        System.out.println("Encoded: " + encodedText);
        System.out.println("Decoded: " + decodedText);

        scanner.close();
    }
}
