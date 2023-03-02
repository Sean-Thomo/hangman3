import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Hangman {
    public static void main(String[] args) {
        ArrayList<String> wordsArray = getStringArray();
        String selectedWord = selectRandomWord(wordsArray);
        String currentAnswer = randomFillWord(selectedWord);
        runGameLoop(selectedWord, currentAnswer);
    }

    static void runGameLoop(String word, String answer) {
        System.out.println("Guess the word: "+answer);
        int tries = 4;

        while (answer != word) {
            char guess = getUserInput();

            if (tries == 0) {
                System.out.println("Sorry, you're out of guesses. The word was: "+guess);
            } 

            if (isMissingChar(word, answer, guess) == true) {
                String getAnswer =  doCorrectAnswer(word, answer, guess);
            } else if (!isMissingChar(word, answer, guess)) {
                doWrongAnswer(word, tries);
                tries --;
                System.out.println("Wrong! Number of guesses left: "+tries);
            }
        }
    }

    static char getUserInput() {
        Scanner input = new Scanner(System.in);
        System.out.println("Guess the missing letter: ");
        return input.nextLine().charAt(0);
    }


    static ArrayList<String> getStringArray() {
        try {
            ArrayList<String> wordsArray = new ArrayList<String>();
            String fileName = getFileName();
            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String word = myReader.nextLine();
                wordsArray.add(word);
            }
            myReader.close();

            return wordsArray;
    
        } catch (FileNotFoundException e) {
            System.out.println("An error occured");
            e.printStackTrace();
        }
        return null;
    }


    static int getRandomInt(int max){
        Random num = new Random(); 
        return num.nextInt((max - 0) + 1) + 0;
    }


    static String selectRandomWord(ArrayList<String> wordsArray) {
        int index = getRandomInt(wordsArray.size());
        final String word = wordsArray.get(index);
        return word;
    }


    static void isCorrectLetter(char letter, char answer) {
        if (letter == (char) answer) {
            System.out.println("Well done! You are awesome!");
        } else {
            System.out.println("Wrong! Do better next time.");
        }
    }

    static String getFileName() {
        Scanner input = new Scanner(System.in);
        System.out.println("Words file? [leave empty to use shortwords.txt]:");
        String fileName = input.nextLine();
        if (fileName.isBlank()) {
            return "shortwords.txt";
        } else {
            return fileName;
        }
    }

    static String randomFillWord(String word) {
        int randomIndex = getRandomInt(word.length());

        char[] wordChars = word.toCharArray();
        char[] maskedChars = new char[wordChars.length];

        for (int i = 0; i < wordChars.length; i++) {
            if (i == randomIndex) {
                maskedChars[i] = wordChars[i];
            } else {
                maskedChars[i] = '_';
            }
        }

        String newWord = new String(maskedChars);
        return newWord;
    }

    static Boolean isMissingChar(String originalWord, String answerWord, char guess) {
        ArrayList<String> listOriginal = new ArrayList<String>(Arrays.asList(originalWord.split("")));
        ArrayList<String> listAnswer = new ArrayList<String>(Arrays.asList(answerWord.split("")));

        if (listOriginal.contains(Character.toString(guess)) && !listAnswer.contains(Character.toString(guess))) {
            return true;
        } else {
            return false;
        }
    }

    static String fillInChar(String originalWord, String answerWord, char guess) {
        char[] originalChars = originalWord.toCharArray();
        char[] answerChars = answerWord.toCharArray();
        char[] moreCompleteWord = new char[originalWord.length()];

        for (int i = 0; i < originalWord.length(); i++) {
            if (guess == originalChars[i] && guess != answerChars[i]) {
                moreCompleteWord[i] = guess;
            } else if (answerChars[i] != '_') {
                moreCompleteWord[i] = answerChars[i];
            } else {
                moreCompleteWord[i] = '_';
            }
        }

        String completeString = new String(moreCompleteWord);
        return completeString;
    }

    static String doCorrectAnswer(String originalWord, String answer, char guess) {
        String correctAnswer = fillInChar(originalWord, answer, getUserInput());
        System.out.println(correctAnswer);
        return correctAnswer;
    }

    static void doWrongAnswer(String answer, int numberGuesses) {
        System.out.println("Wrong! Number of guesses left: " + numberGuesses);
        drawFigure(numberGuesses);
    }

    static void drawFigure(int numberGuesses) {
        String[] stages = {
            "/----\n|\n|\n|\n|\n_______",
            "/----\n|   0\n|\n|\n|\n_______",
            "/----\n|   0\n|  /|\\\n|\n|\n_______",
            "/----\n|   0\n|  /|\\\n|   |\n|\n_______",
            "/----\n|   0\n|  /|\\\n|   |\n|  / \\\n_______"
        };
        
        if (numberGuesses == 4) {
            System.out.println(stages[0]);
        } else if (numberGuesses == 3) {
            System.out.println(stages[1]);
        } else if (numberGuesses == 2) {
            System.out.println(stages[2]);
        } else if (numberGuesses == 1) {
            System.out.println(stages[3]);
        } else if (numberGuesses == 0) {
            System.out.println(stages[4]);
        }

    }
}