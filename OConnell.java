
//Patrick O'Connell
import java.util.Scanner;

public class OConnell {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
         String input;
         System.out.println("Enter a String");
         input = scan.nextLine();
        System.out.println("Input: " + input);
        String key = generateRandomKey(4);
        System.out.println("Key: " + key);

        // Encrypting the first two characters
        String encrypted = encrypt(input.substring(0, 2), key);
        System.out.println("Encrypted output (first two characters): " + encrypted);

        // Decrypting the first two characters
        String decrypted = decrypt(encrypted, key);
        System.out.println("Decrypted output (first two characters): " + decrypted);

        // Encrypting the entire input string
        String fullEncrypted = encrypt(input, key);
        System.out.println("Encrypted output (full string): " + fullEncrypted);

        // Decrypting the entire input string
        String fullDecrypted = decrypt(fullEncrypted, key);
        System.out.println("Decrypted output (full string): " + fullDecrypted);
    }

    public static String encrypt(String plaintext, String key) {
        StringBuilder ciphertext = new StringBuilder();

        for (char character : plaintext.toCharArray()) {
            String binary = String.format("%8s", Integer.toBinaryString(character))
                    .replace(' ', '0');
            String encrypted = performFeistelRound(binary, key);
            ciphertext.append(encrypted);
        }

        return ciphertext.toString();
    }

    public static String decrypt(String ciphertext, String key) {
        StringBuilder plaintext = new StringBuilder();

        for (int i = 0; i < ciphertext.length(); i += 8) {
            String block = ciphertext.substring(i, i + 8);
            String decrypted = performFeistelRound(block, key);
            int ascii = Integer.parseInt(decrypted, 2);
            plaintext.append((char) ascii);
        }

        return plaintext.toString();
    }

    public static String performFeistelRound(String binary, String key) {
        String leftHalf = binary.substring(0, 4);
        String rightHalf = binary.substring(4, 8);

        String newLeftHalf = rightHalf;
        String feistelOutput = calculateFeistelFunction(rightHalf, key);
        String newRightHalf = performXOR(leftHalf, feistelOutput);

        return newRightHalf + newLeftHalf;
    }

    public static String calculateFeistelFunction(String rightHalf, String key) {
        int r = Integer.parseInt(rightHalf, 2);
        int k = Integer.parseInt(key, 2);
        int result = (2 * (int) Math.pow(r, k)) % 16;
        return String.format("%4s", Integer.toBinaryString(result)).replace(' ', '0');
    }

    public static String performXOR(String a, String b) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < a.length(); i++) {
            char bitA = a.charAt(i);
            char bitB = b.charAt(i);
            char xorBit = (bitA != bitB) ? '1' : '0';
            result.append(xorBit);
        }

        return result.toString();
    }

    public static String generateRandomKey(int length) {
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomBit = (int) (Math.random() * 2);
            key.append(randomBit);
        }
        return key.toString();
    }
}
