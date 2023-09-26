import java.math.BigInteger;
import java.util.*;

public class Assignment3 {

    private static final int MIN_PRIME_NUMBER = 100;
    private static final int MAX_PRIME_NUMBER = 1000;

    public static char[] stringToChar(String input) {
        char[] text = new char[input.length()];
        for (int i = 0; i < input.length(); i++) {
            text[i] = input.charAt(i);
        }
        return text;
    }
    //==========================================================================================================================

    public static String charToString(List<Character> input) {
        String output = "";
        for (Character character : input) {
            output = output.concat(String.valueOf(character));
        }
        return output;
    }
    //==========================================================================================================================

    public static int[] charToASCII(List<Character> input) {
        int[] ascii = new int[input.size()];
        for (int i = 0; i < ascii.length; i++) {
            ascii[i] = (int) input.get(i);
        }
        return ascii;
    }
    //==========================================================================================================================

    public static char[] ASCIItoChar(int[] ascii) {
        char[] output = new char[ascii.length];
        for (int i = 0; i < ascii.length; i++) {
            output[i] = (char) ascii[i];
        }
        return output;
    }
    //==========================================================================================================================

    public static int[] ASCIItoBinary(int[] ascii) {
        int[] binary = new int[ascii.length];
        for (int i = 0; i < binary.length; i++) {
            String bString = "";
            while (ascii[i] > 0) {
                int r = ascii[i] % 2;
                ascii[i] /= 2;
                bString = r + bString;
                binary[i] = Integer.parseInt(bString);
            }
        }
        return binary;
    }
    //==========================================================================================================================

    public static int[] BinaryToASCII(int[] binary) {
        char nextChar;
        int[] ascii = new int[binary.length];
        for (int i = 0; i < ascii.length; i++) {
            String num = String.valueOf(binary[i]);
            int dec_value = 0;
            int base = 1;
            int len = num.length();
            for (int j = len - 1; j >= 0; j--) {
                if (num.charAt(j) == '1') {
                    dec_value += base;
                }
                base = base * 2;
            }
            ascii[i] = dec_value;
        }

        return ascii;
    }
    private static boolean isPrime(int numberCheck) {
        if (numberCheck <= 1) {
            return false;
        }
        for (int i = 2; i * i <= numberCheck; i++) {
            if (numberCheck % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static KeyPair keyGen() {
        Random random = new Random();
        int pValue = generatePrime(random);
        int qValue = generatePrime(random);

        BigInteger m = BigInteger.valueOf(pValue).multiply(BigInteger.valueOf(qValue));
        BigInteger phiM = BigInteger.valueOf(pValue - 1).multiply(BigInteger.valueOf(qValue - 1));
        BigInteger r = generateCoprime(phiM, random);
        BigInteger f = r.modInverse(phiM);
        PublicKey publicKey = new PublicKey(r, m);
        PrivateKey privateKey = new PrivateKey(f, m);
        return new KeyPair(publicKey, privateKey);
    }

    public static List<BigInteger> encrypt(String message, PublicKey publicKey) {
        List<BigInteger> encryptedMessage = new ArrayList<>();
        for (char c : message.toCharArray()) {
            BigInteger m = BigInteger.valueOf(c);
            BigInteger crypted = m.modPow(publicKey.getR(), publicKey.getM());
            encryptedMessage.add(crypted);
        }
        return encryptedMessage;
    }

    public static String decrypt(List<BigInteger> encryptedMessage, PrivateKey privateKey) {
        StringBuilder decryptedMessage = new StringBuilder();
        for (BigInteger c : encryptedMessage) {
            BigInteger decrypted = c.modPow(privateKey.getF(), privateKey.getM());
            decryptedMessage.append((char) decrypted.intValue());
        }
        return decryptedMessage.toString();
    }

    private static int generatePrime(Random random) {
        while (true) {
            int prime = random.nextInt(MAX_PRIME_NUMBER - MIN_PRIME_NUMBER + 1) + MIN_PRIME_NUMBER;
            if (isPrime(prime)) {
                return prime;
            }
        }
    }


    private static BigInteger generateCoprime(BigInteger phiN, Random random) {
        BigInteger r;
        do {
            r = new BigInteger(phiN.bitLength(), random);
        } while (r.compareTo(BigInteger.ONE) <= 0 || r.compareTo(phiN) >= 0 || !phiN.gcd(r).equals(BigInteger.ONE));
        return r;
    }

    static class KeyPair {
        private PublicKey publicKey;
        private PrivateKey privateKey;

        public KeyPair(PublicKey publicKey, PrivateKey privateKey) {
            this.publicKey = publicKey;
            this.privateKey = privateKey;
        }

        public PublicKey getPublicKey() {
            return publicKey;
        }

        public PrivateKey getPrivateKey() {
            return privateKey;
        }
    }

    static class PublicKey {
        private BigInteger earlyBird;
        private BigInteger normalBird;

        public PublicKey(BigInteger r, BigInteger m) {
            this.earlyBird = r;
            this.normalBird = m;
        }

        public BigInteger getR() {
            return earlyBird;
        }

        public BigInteger getM() {
            return normalBird;
        }
    }

    static class PrivateKey {
        private BigInteger darkBird;
        private BigInteger neutralBird;

        public PrivateKey(BigInteger f, BigInteger m) {
            this.darkBird = f;
            this.neutralBird = m;
        }

        public BigInteger getF() {
            return darkBird;
        }

        public BigInteger getM() {
            return neutralBird;
        }
    }


    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int value = 0;
        String inputText, outputText;
        int[] outputBinary;
        int[] outputASCII;
        int[] inputASCII;
        int[] inputBinary;
        List<Integer> tempBinary = new ArrayList<>();
        List<Integer> tempASCII = new ArrayList<>();
        List<Character> tempChar = new ArrayList<>();
        char[] outputChar, inputChar;
        int question;
        int funcOption = 0;
        String cVal;

        boolean choice = false;

        System.out.println("Hello there, welcome to Patrick O'Connell's Assignment 1 Text converter ");
        System.out.println("Below are three options to select from to choose what function you would like to do.");
        while (!choice) {
            question = 0;
            System.out.println("Function 1 = String --> List of Characters");
            System.out.println("Function 2 = List of Characters --> String");
            System.out.println("Function 3 = List of Characters --> ASCII");
            System.out.println("Function 4 = ASCII --> List of Characters");
            System.out.println("Function 5 = ASCII --> Binary");
            System.out.println("Function 6 = Binary --> ASCII");
            System.out.println("Function 7 NEW = String --> ASCII + RSA Function");
            System.out.println("Type 1, 2, 3, 4, 5, 6, or 7 for your desired function respectively:");
            funcOption = scan.nextInt();
            switch (funcOption) {
                case 1 -> {
                    KeyPair keyPair = keyGen();
                    System.out.println("You have Selected Function 1");
                    System.out.println("First we would like you to enter the String: ");
                    inputText = scan.nextLine();
                    inputText = scan.nextLine();
                    outputChar = new char[inputText.length()];
                    outputChar = stringToChar(inputText);
                    System.out.println("Input String " + inputText);
                    System.out.println("Output List ");
                    System.out.println(Arrays.toString(outputChar));

                    // Encrypt and decrypt a message
                    List<BigInteger> encryptedMessage = encrypt(inputText, keyPair.getPublicKey());
                    String decryptedMessage = decrypt(encryptedMessage, keyPair.getPrivateKey());

                    System.out.println("Original message: " + inputText);
                    System.out.println("Encrypted message: " + encryptedMessage);
                    System.out.println("Decrypted message: " + decryptedMessage);

                }
                //==========================================================================================================================

                case 2 -> {
                    tempChar.clear();
                    System.out.println("You have Selected Function 2");
                    System.out.println("First we would like you to enter the List of Characters: ");
                    while (question != -1) {

                        System.out.println("Enter a Char: ");
                        System.out.println("Enter \"space\" if you want to add a space");
                        System.out.println("if complete enter \"done\":");
                        cVal = scan.next();
                        if (cVal.equals("done")) {
                            question = -1;
                            break;
                        }
                        if (cVal.equals("space")) {
                            tempChar.add(' ');
                        } else {
                            tempChar.add(cVal.charAt(0));
                        }
                        System.out.println(tempChar.toString());

                    }
                    outputText = charToString(tempChar);

                    System.out.println("Input List of Characters ");
                    System.out.println(tempChar);
                    System.out.println("Output String ");
                    System.out.println(outputText);

                }
                //==========================================================================================================================
                case 3 -> {

                    tempChar.clear();
                    System.out.println("You have Selected Function 3");
                    System.out.println("First we would like you to enter the List of Characters: ");
                    while (question != -1) {
                        System.out.println("Enter a Char: ");
                        System.out.println("Enter \"space\" if you want to add a space");
                        System.out.println("if complete enter \"done\":");
                        cVal = scan.next();
                        if (cVal.equals("done")) {
                            question = -1;
                            break;
                        }
                        if (cVal.equals("space")) {
                            tempChar.add(' ');
                        } else {
                            tempChar.add(cVal.charAt(0));
                        }
                        System.out.println(tempChar);

                    }

                    outputASCII = charToASCII(tempChar);
                    System.out.println("Input List of Characters ");
                    System.out.println(tempChar);
                    System.out.println("Output ASCII ");
                    System.out.println(Arrays.toString(outputASCII));
                }
                //==========================================================================================================================
                case 4 -> {
                    tempASCII.clear();
                    System.out.println("You have Selected Function 4");
                    System.out.println("First we would like you to enter the ASCII list: ");
                    value = 0;
                    while (value != -1) {
                        System.out.println("Type -1 to finish adding values otherwise type and ASCII value: ");
                        value = scan.nextInt();
                        if (value == -1) {
                            break;
                        } else {
                            tempASCII.add(value);
                            System.out.println(tempASCII.toString());
                        }
                    }
                    inputASCII = new int[tempASCII.size()];
                    for (int j = 0; j < tempASCII.size(); j++) {
                        inputASCII[j] = tempASCII.get(j);
                    }
                    outputChar = ASCIItoChar(inputASCII);
                    System.out.println("Input ASCII ");
                    System.out.println(Arrays.toString(inputASCII));
                    System.out.println("Output List of Characters ");
                    System.out.println(Arrays.toString(outputChar));

                }
                //==========================================================================================================================
                case 5 -> {
                    tempBinary.clear();
                    tempASCII.clear();
                    System.out.println("You have Selected Function 5");
                    System.out.println("First we would like you to enter the ASCII list: ");
                    value = 0;
                    while (value != -1) {
                        System.out.println("Type -1 to finish adding values otherwise type an ASCII value: ");
                        value = scan.nextInt();
                        if (value == -1) {
                            break;
                        } else {
                            tempASCII.add(value);
                            System.out.println(tempASCII.toString());
                        }
                    }
                    inputASCII = new int[tempASCII.size()];
                    for (int j = 0; j < tempASCII.size(); j++) {
                        inputASCII[j] = tempASCII.get(j);
                    }
                    outputBinary = ASCIItoBinary(inputASCII);
                    System.out.println("Input List of ASCII ");
                    System.out.println(Arrays.toString(inputASCII));
                    System.out.println("Output Binary ");
                    System.out.println(Arrays.toString(outputBinary));
                }
                //==========================================================================================================================

                case 6 -> {
                    tempBinary.clear();
                    System.out.println("You have Selected Function 6");
                    System.out.println("First we would like you to enter the Binary list: ");
                    value = 0;
                    while (value != -1) {
                        System.out.println("Type -1 to finish adding values otherwise type a Binary value: ");
                        value = scan.nextInt();
                        if (value == -1) {
                            break;
                        } else {
                            tempBinary.add(value);
                            System.out.println(tempBinary.toString());
                        }
                    }
                    inputBinary = new int[tempBinary.size()];
                    for (int j = 0; j < tempBinary.size(); j++) {
                        inputBinary[j] = tempBinary.get(j);
                    }
                    outputASCII = BinaryToASCII(inputBinary);
                    System.out.println("Input List of Binary ");
                    System.out.println(Arrays.toString(inputBinary));
                    System.out.println("Output ASCII ");
                    System.out.println(Arrays.toString(outputASCII));

                }
                //==========================================================================================================================
                case 7 ->{
                    tempBinary.clear();
                    tempASCII.clear();
                    KeyPair keyPair = keyGen();
                    System.out.println("You have Selected Function 7");
                    System.out.println("First we would like you to enter the String: ");
                    inputText = scan.nextLine();
                    inputText = scan.nextLine();
                    outputChar = new char[inputText.length()];
                    outputChar = stringToChar(inputText);
                    System.out.println("Input String " + inputText);
                    System.out.println("Output List ");
                    System.out.println(Arrays.toString(outputChar));
                    for(int i=0; i<outputChar.length; i++){
                        tempChar.add(outputChar[i]);
                    }

                    outputASCII = charToASCII(tempChar);
                    System.out.println("Output ASCII ");
                    System.out.println(Arrays.toString(outputASCII));

                    // Encrypt and decrypt a message
                    List<BigInteger> encryptedMessage = encrypt(inputText, keyPair.getPublicKey());
                    String decryptedMessage = decrypt(encryptedMessage, keyPair.getPrivateKey());
                    System.out.println("Public Key 1: "+keyPair.getPublicKey().getR());
                    System.out.println("Public Key 2: "+keyPair.getPublicKey().getM());
                    System.out.println("Private Key 1: "+keyPair.getPrivateKey().getF());
                    System.out.println("Private Key 2: "+keyPair.getPrivateKey().getM());
                    System.out.println("Original message: " + inputText);
                    System.out.println("Encrypted message: " + encryptedMessage);
                    System.out.println("Decrypted message: " + decryptedMessage);
                }
                default -> {
                    break;
                }
            }
            System.out.println("Would you like to continue? 1 for Yes or 2 for No:");
            question = scan.nextInt();
            if (question == 2) {
                choice = true;
            }
        }
        System.out.println("You have opted to end the program. Thank you very much!");
    }
}
