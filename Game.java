package bullscows;

import java.util.*;

public class Game {
    private String input;
    private String secretCode;
    private int bull;
    private int cow;
    private State state;
    private int lengthOfSecretCode;
    private int numberOfPossibleSymbols;
    private final Scanner scanner;
    private int turn ;

    public Game() {
        this.state = State.notWon;
        this.scanner = new Scanner(System.in);
        this.turn =1;
    }

    public void start() {
        enterLengthOfSecretCode();
        enterNumberOfPossibleSymbolsInTheCode();
        printStartMessage();
        generateSecretNumber();
        while (state == State.notWon) {
            printTurn();
            guessSecretCode();
            checkIfCodeIsCorrect();
            printResult();
            resetBullsAndCows();
            incrementTurn();
        }

    }

    private void incrementTurn() {
        this.turn ++;
    }

    private void printTurn() {
        System.out.printf("Turn %d:\n", this.turn);
    }

    private void printStartMessage() {
        String secretCodeInHiddenForm = "*".repeat(lengthOfSecretCode);
        System.out.printf("The secret code is prepared: %s ",secretCodeInHiddenForm);
        if (numberOfPossibleSymbols <= 10) {
            System.out.printf("(0-%s).\n", numberOfPossibleSymbols-1);
        } else if (numberOfPossibleSymbols == 11) {
            System.out.println("(0-9, a).\n");
        } else {
            System.out.printf("(0-9, a-%c).\n", (char)(numberOfPossibleSymbols+86));
        }
        System.out.println("Okay, let's start a game!");
    }

    private void enterNumberOfPossibleSymbolsInTheCode() {
        boolean accepted = false;
        while (!accepted) {
            //more or equal amount of symbols than length of the secret code
            System.out.println("Input the number of possible symbols in the code:");
            String numberOfPossibleSymbolsStr = scanner.next();
            if (numberOfPossibleSymbolsStr.matches("\\d+")) {
                int numberOfPossibleSymbolsInt = Integer.parseInt(numberOfPossibleSymbolsStr);
                if (numberOfPossibleSymbolsInt >= lengthOfSecretCode && numberOfPossibleSymbolsInt <= 36) {
                    accepted = true;
                    this.numberOfPossibleSymbols = numberOfPossibleSymbolsInt;
                } else {
                    System.out.println("Error: the number of symbol has to be greater or equal to the " +
                            "length of the secret code");
                    System.exit(0);

                }
            } else {
                System.out.printf("Error: \"%s\" is not a valid number.\n", numberOfPossibleSymbolsStr);
                System.exit(0);

            }
        }
    }

    private void enterLengthOfSecretCode() {
        boolean accepted = false;
        while (!accepted) {
                System.out.println("Input the length of the secret code:");
                String lengthOfSecretCodeStr = scanner.next();
            if (lengthOfSecretCodeStr.matches("\\d+")) {
                int lengthOfSecretCodeVar = Integer.parseInt(lengthOfSecretCodeStr);
                if (lengthOfSecretCodeVar < 36 && lengthOfSecretCodeVar >0) {
                    accepted = true;
                    this.lengthOfSecretCode = lengthOfSecretCodeVar;
                } else {
                    System.out.println("Error: can't generate a secret number with a length greater than 36" +
                            " because there aren't enough unique digits and characters.");
                    System.exit(0);
                }
            } else {
                System.out.printf("Error: \"%s\" isn't a valid number.\n", lengthOfSecretCodeStr);
                System.exit(0);
            }
        }
    }

    private void resetBullsAndCows() {
        this.bull = 0;
        this.cow = 0;
    }

    private void generateSecretNumber() {
        List<Character> list = new ArrayList<>(List.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a',
                'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't','u',
                'v',  'w',  'x', 'y', 'z'));
        List<Character> randomList = list.subList(0, numberOfPossibleSymbols);
        Collections.shuffle(randomList);
        StringBuilder result = new StringBuilder();
        for (var ch : randomList.subList(0, lengthOfSecretCode)) {
            result.append(ch);
        }
        this.secretCode = result.toString();
    }

    private void printResult() {
        if (bull == 0 && cow == 0) {
            System.out.println("None");
        }else if (bull == secretCode.length()) {
            state = State.won;
            System.out.printf("Grade: %d bull(s)\nCongratulations: You guessed the secret code.\n",bull);
        } else {
            System.out.printf("Grade: %d bull(s) and %d cows(s).\n", bull, cow);
        }
    }

    private void checkIfCodeIsCorrect() {
        //check for bulls
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == secretCode.charAt(i)) {
                this.bull++;
            } else {
                for (int j = 0; j < input.length(); j++) {
                    if (secretCode.charAt(j) == input.charAt(i) && (i != j)) {
                        this.cow++;
                    }
                }
            }
        }
        //check for cows
    }

    private void guessSecretCode() {
        this.input = new Scanner(System.in).next();
    }
}
