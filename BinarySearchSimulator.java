import java.sql.SQLOutput;
import java.util.*;

class BinarySearchSimulator {

    //Create an enum to set up the cases for switch later. Create boolean gameRunning and set to true.
    public enum gameState{
        setupGame,
        compareInput,
        correctAnswer,
        gameOver,
        userGaveUp
    }

    public static void main(String[] args) {

        //Set initial universal variables.
        boolean gameRunning = true;
        gameState game = gameState.setupGame;
        int guessCount = 1;
        String guess = "";
        int numDigits = 0;
        boolean j = false;
        String history = "";
        int machineComputation = 0;

        //Set up the solution with random class as well as a Scanner to read user input.
        ArrayList<Integer> solutionNumber = new ArrayList<Integer>();
        String solution = "";
        Scanner scan = new Scanner(System.in);

        //start the game
        while(gameRunning) {
            switch (game) {
                case setupGame: {

                    //Ask the user how many digits between 1 and 10 they want to guess, store it
                    System.out.println("Enter the amount of digits you wish to guess:");
                    numDigits = scan.nextInt();

                    //Initialize the solution with random integers between 0 and 9 based off of the length of numDigits
                    for(int i = 0; i < numDigits; i++) {
                        int x = (int) (Math.random() * 10);
                        solutionNumber.add(x);
                    }

                    //Shuffle the solutionNumber array list
                    Collections.shuffle(solutionNumber);

                    //Add the elements of solutionNumber to Solution
                    for(int i = 0; i < solutionNumber.size(); i++) {
                        solution += solutionNumber.get(i);
                    }

                    //Compute the optimal number of guesses
                    machineComputation = computeNumGuesses(Integer.parseInt(solution), numDigits);
                    game = gameState.compareInput;
                    break;
                }
                case compareInput: {
                    //Run checks to see if user input is valid
                    System.out.println("Enter guess " + guessCount + ":");
                        guess = scan.next();
                    if (guess.equals("9999")) {
                        history += "Guess " + guessCount + ": " + guess + ", forfeit\n";
                        System.out.println(history);
                        game = gameState.userGaveUp;
                        break;
                    }
                    else if(guess.length() != solution.length()) {
                        System.out.println("Your guess is not the correct number of digits. You have been charged with a meaningless guess. Try again.");
                        history += "Guess " + guessCount + ": " + guess + ", invalid input\n";
                        System.out.println(history);
                        guessCount++;
                        break;
                    }
                    //make conditions to compare user guess
                    else if (!(guess.equals(solution))) {
                        if(Integer.parseInt(guess) < Integer.parseInt(solution)) {
                            System.out.println("Your guess is less than the answer! Try again.");
                            history +="Guess " + guessCount + ": " + guess + ", LESS THAN ANSWER\n";
                            System.out.println(history);
                        }
                        else {
                            System.out.println("Your guess is greater than the answer! Try again.");
                            history +="Guess " + guessCount + ": " + guess + ", GREATER THAN ANSWER\n";
                            System.out.println(history);
                        }
                        guessCount++;
                        break;
                    }
                    else {
                        game = gameState.correctAnswer;
                        break;
                    }
                }
                case correctAnswer: {
                    history +="Guess " + guessCount + ": " + guess + ", Correct!\n";
                    System.out.println(history);
                    System.out.println("Congratulations, you guessed correctly in " + guessCount + " guesses!");
                    System.out.println("Solution: " + solution);
                    System.out.println("The optimal number of guesses was: " + machineComputation);
                    game = gameState.gameOver;
                    break;
                }
                case gameOver: {
                    if (!(j)) {
                        System.out.println("\n\nCome on, play again! The game is fun. Enter Y to play again, enter N to quit.");
                    }
                    String temp = scan.next();
                    if (temp.toUpperCase().equals("Y")) {
                        guessCount = 1;
                        solution = "";
                        history = "";
                        solutionNumber.clear();
                        j = false;
                        game = gameState.setupGame;
                        break;
                    } else if (temp.toUpperCase().equals("N")) {
                        gameRunning = false;
                        scan.close();
                        break;
                    } else {
                        System.out.println("Please input either Y or N.");
                        j = true;
                        break;
                    }
                }
                case userGaveUp: {
                    System.out.println("Awe, it stinks that you couldn't get it!");
                    System.out.println("The answer is: " + solution);
                    System.out.println("The optimal number of guesses was: " + machineComputation);
                    game = gameState.gameOver;
                    break;
                }
            }
        }
    }

    public static int computeNumGuesses(int input, int numDigits) {
        int numGuesses = 1;
        int l = 0;
        int r = (int) Math.pow(10, numDigits)-1;
        int guess = (r / 2) + 1;

        while(guess != input) {
            if(guess > input) {
                r = guess;
            }
            else {
                l = guess;
            }
            guess = (l+r)/2;
            numGuesses++;
        }
        return numGuesses;
    }
}



