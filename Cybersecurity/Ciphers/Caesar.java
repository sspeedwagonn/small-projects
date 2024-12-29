package org.example;

import java.util.Scanner;

public class Caesar {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the plaintext message: ");
        String plaintext = scanner.nextLine();

        int offset;
        do {
            System.out.print("Enter the offset (1-25): ");
            while (!scanner.hasNextInt()) {
                System.out.print("Please enter a valid integer offset (1-25): ");
                scanner.next();
            }
            offset = scanner.nextInt();
        } while (offset < 1 || offset > 25);

        String encodedText = encode(plaintext, offset);
        String decodedText = decode(encodedText, offset);

        System.out.println("Encoded: " + encodedText);
        System.out.println("Decoded: " + decodedText);

        scanner.close();
    }

    public static String decode(String ciphertext, int offset) {
        return encode(ciphertext, 26 - offset);
    }

    public static String encode(String text, int offset) {
        offset = normalizeOffset(offset);
        StringBuilder result = new StringBuilder();

        for (char character : text.toCharArray()) {
            if (Character.isLetter(character)) {
                char base = Character.isUpperCase(character) ? 'A' : 'a';
                result.append((char) (base + (character - base + offset) % 26));
            } else {
                result.append(character);
            }
        }

        return result.toString();
    }

    private static int normalizeOffset(int offset) {
        return (offset % 26 + 26) % 26;
    }
}
