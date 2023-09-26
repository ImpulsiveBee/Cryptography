//Patrick O'Connell
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PlayfairCipher {

    private static char[][] myMatrix;

    private static char[][] Key(int x, int y, char initial) {
        char[][] result = new char[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                result[i][j] = initial;
            }
        }
        return result;
    }

    private static int[] Pre(char c) {
        int[] loc = new int[2];
        if (c == 'J') {
            c = 'I';
        }
        for (int i = 0; i < myMatrix.length; i++) {
            for (int j = 0; j < myMatrix[i].length; j++) {
                if (c == myMatrix[i][j]) {
                    loc[0] = i;
                    loc[1] = j;
                    return loc;
                }
            }
        }
        return loc;
    }

    private static void Enc() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter MSG: ");
        String msg = scanner.nextLine();
        msg = msg.toUpperCase().replaceAll(" ", "");

        StringBuilder modifiedMsg = new StringBuilder(msg);
        for (int i = 0; i < modifiedMsg.length() - 1; i += 2) {
            if (modifiedMsg.charAt(i) == modifiedMsg.charAt(i + 1)) {
                modifiedMsg.insert(i + 1, 'X');
            }
        }
        if (modifiedMsg.length() % 2 != 0) {
            modifiedMsg.append('X');
        }

        System.out.print("CIPHER TEXT: ");
        int i = 0;
        while (i < modifiedMsg.length()) {
            int[] loc = Pre(modifiedMsg.charAt(i));
            int[] loc1 = Pre(modifiedMsg.charAt(i + 1));

            if (loc[1] == loc1[1]) {
                System.out.print(myMatrix[(loc[0] + 1) % 5][loc[1]]);
                System.out.print(myMatrix[(loc1[0] + 1) % 5][loc1[1]]);
            } else if (loc[0] == loc1[0]) {
                System.out.print(myMatrix[loc[0]][(loc[1] + 1) % 5]);
                System.out.print(myMatrix[loc1[0]][(loc1[1] + 1) % 5]);
            } else {
                System.out.print(myMatrix[loc[0]][loc1[1]]);
                System.out.print(myMatrix[loc1[0]][loc[1]]);
            }

            i += 2;
            System.out.print(" ");
        }
    }

    private static void Dec() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter CIPHER TEXT: ");
        String msg = scanner.nextLine();
        msg = msg.toUpperCase().replaceAll(" ", "");

        System.out.print("PLAIN TEXT: ");
        int i = 0;
        while (i < msg.length()) {
            int[] loc = Pre(msg.charAt(i));
            int[] loc1 = Pre(msg.charAt(i + 1));

            if (loc[1] == loc1[1]) {
                System.out.print(myMatrix[(loc[0] - 1 + 5) % 5][loc[1]]);
                System.out.print(myMatrix[(loc1[0] - 1 + 5) % 5][loc1[1]]);
            } else if (loc[0] == loc1[0]) {
                System.out.print(myMatrix[loc[0]][(loc[1] - 1 + 5) % 5]);
                System.out.print(myMatrix[loc1[0]][(loc1[1] - 1 + 5) % 5]);
            } else {
                System.out.print(myMatrix[loc[0]][loc1[1]]);
                System.out.print(myMatrix[loc1[0]][loc[1]]);
            }

            i += 2;
            System.out.print(" ");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        myMatrix = Key(5, 5, '0');
        List<Character> result = new ArrayList<>();

        System.out.print("Enter key: ");
        String key = scanner.nextLine();
        key = key.replace(" ", "").toUpperCase();

        for (char c : key.toCharArray()) {
            if (!result.contains(c)) {
                if (c == 'J') {
                    result.add('I');
                } else {
                    result.add(c);
                }
            }
        }

        int flag = 0;
        for (int i = 65; i <= 90; i++) {
            char ch = (char) i;
            if (!result.contains(ch)) {
                if (i == 73 && !result.contains((char) 74)) {
                    result.add('I');
                    flag = 1;
                } else if ((flag == 0 && i == 73) || i == 74) {
                    // Do nothing
                } else {
                    result.add(ch);
                }
            }
        }

        int k = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                myMatrix[i][j] = result.get(k);
                k++;
            }
        }

        while (true) {
            System.out.println("\nChoices:");
            System.out.println("1. Encryption");
            System.out.println("2. Decryption");
            System.out.println("3. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> Enc();
                case 2 -> Dec();
                case 3 -> {
                    scanner.close();
                    System.exit(0);
                }
                default -> System.out.println("Choose correct choice");
            }
        }
    }
}
