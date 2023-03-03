import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException;

public class Hangman {
    public static void main(String[] args) {
        String wordsFile;
        if (args.length > 0) {
            wordsFile = args[0];
        } else {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Words file? [leave empty to use shortwords.txt]:");
            wordsFile = scanner.nextLine();
        }

        if (wordsFile.isBlank()) {
            wordsFile = "shortwords.txt";
        }

        ArrayList<String> words = readWordsFile(wordsFile);
        String selectedWord = selectRandomWord(words);
        String currentAnswer = randomFillWord(selectedWord);

        runGameLoop(selectedWord, currentAnswer);
    }

    public static ArrayList<String> readWordsFile(String filename) {
        ArrayList<String> words = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(filename));
            while (scanner.hasNextLine()) {
                String word = scanner.nextLine();
                words.add(word);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
        return words;
    }

    public static String getUserInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Guess the missing letter: ");
        String guess = scanner.nextLine();
        return guess;
    }

    public static String askFileName() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Words file? [leave empty to use short_words.txt] : ");
        String fileName = scanner.nextLine();
        if (fileName.equals("")) {
            return "short_words.txt";
        }
        return fileName;
    }

    public static String selectRandomWord(List<String> words) {
        Random rand = new Random();
        int randomIndex = rand.nextInt(words.size());
        String word = words.get(randomIndex).trim();
        return word;
    }

    public static String randomFillWord(String word) {
        Random rand = new Random();
        int randomIndex = rand.nextInt(word.length());
        StringBuilder sb = new StringBuilder(word.length());
        for (int i = 0; i < word.length(); i++) {
            if (i == randomIndex) {
                sb.append(word.charAt(randomIndex));
            } else {
                sb.append("_");
            }
        }
        return sb.toString();
    }

    public static boolean isMissingChar(String originalWord, String answerWord, char c) {
        return originalWord.contains(String.valueOf(c)) && !answerWord.contains(String.valueOf(c));
    }

    public static String fillInChar(String originalWord, String answerWord, char c) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < originalWord.length(); i++) {
            if (originalWord.charAt(i) == c) {
                sb.append(c);
            } else if (answerWord.charAt(i) != '_') {
                sb.append(answerWord.charAt(i));
            } else {
                sb.append("_");
            }
        }
        return sb.toString();
    }

    public static String doCorrectAnswer(String originalWord, String answerWord, char c) {
        answerWord = fillInChar(originalWord, answerWord, c);
        System.out.println(answerWord);
        return answerWord;
    }

    public static void doWrongAnswer(int remainingGuesses) {
        System.out.println("Wrong! Number of guesses left: " + remainingGuesses);
        drawFigure(remainingGuesses);
    }

    public static void drawFigure(int remainingGuesses) {
        String[] stages = {
            "/----\n|\n|\n|\n|\n_______",
            "/----\n|   0\n|\n|\n|\n_______",
            "/----\n|   0\n|  /|\\\n|\n|\n_______",
            "/----\n|   0\n|  /|\\\n|   |\n|\n_______",
            "/----\n|   0\n|  /|\\\n|   |\n|  / \\\n_______"
        };

        System.out.println(stages[4 - remainingGuesses]);

    }

    public static void runGameLoop(String word, String answer) {
        System.out.println("Guess the word: " + answer);
        int tries = 4;
        while (!answer.equals(word)) {
            String guess = getUserInput();
            if (tries == 0) {
                System.out.println("Sorry, you are out of guesses. The word was: " + word);
                break;
            }
            if (guess.equals("quit") || guess.equals("exit")) {
                System.out.println("Bye!");
                break;
            }
            char c = guess.charAt(0);
            if (isMissingChar(word, answer, c)) {
                answer = doCorrectAnswer(word, answer, c);
            } else {
                doWrongAnswer(tries);
                tries--;
            }
        }
    }
}