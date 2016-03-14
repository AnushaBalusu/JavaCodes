/**
 * This program accepts a words file and player names (more than 1)
 * Players play the hangman game in round robin fashion and a round is played
 * after each player gets a chance once
 * The one player with the fewest guesses wins
 * It calculates the scores and prints the top 4 players
 * 
 * @version    $Id: Hangman.java, v 1 2015/14/09 $
 *
 * @author     Anusha Balusu, Pankhuri Roy
 */

import java.util.Scanner;
import java.util.Random;
import java.io.FileReader;
import java.io.File;
import java.util.ArrayList;

class Hangman {
   public final int GRID_LENGTH = 20;
   public final int GRID_WIDTH = 20;
   public final char SYMBOL = '#';
   int grid[][] = new int[GRID_LENGTH][GRID_WIDTH];
   public static final int MIN_SCORE = -5;
   public static int MAX_SCORE = 10;
   ArrayList<String> wordsList = new ArrayList<String>();
   static final int noOfGuesses = 8;
   char[] guessedLetters = null;

   /**
    * The main program.
    *
    * @param   args           words text file followed by player names
    */

   public static void main( String[] args ) {

      int totalLines = 0;
      int randomIndex = 0;
      String word = null;
      Hangman hangman = new Hangman();
      hangman.fillHangmanGrid();
      //      hangman.printHangman(8);
      //      hangman1.printGridValues();
      try {
         // user inputs words file name and atleast 2 players
         if(args.length > 2) {

            Scanner userWish = null;
            int playerCount = 0;
            totalLines = hangman.storeWordsFromFile(args[0]);
            if(totalLines > 0){ // words file not empty
               float[] score = new float[args.length - 1];
               String[] player = new String[args.length - 1];
               // put player names in array
               for(int counter = 0; counter < player.length; counter++) {
                  player[counter] = args[counter + 1];
               }
               do{ // do-while loop for rounds. In 1 round, all players play once.
                  for(playerCount = 0; playerCount < player.length; playerCount++) {
                     // word should not have more than 8 distinct letters
                     word = hangman.getRandomWord(totalLines, hangman);
                     System.out.println("-------------------------------");
                     System.out.println("Player: " + player[playerCount]);
                     System.out.println("-------------------------------");
                     hangman.printWord(word,' ');

                     // Each player gets 8 chances to guess
                     score[playerCount] += hangman.playOneWord(word, hangman);
                     System.out.println("Your score is: " + score[playerCount]);

                  }
                  System.out.print("Do you want to continue(y/n)? ");
                  userWish = new Scanner(System.in);
               }while(userWish.next().equals("y"));
               userWish.close();
               hangman.sortAndDisplay(player,score);
            }
         }else {
            System.out.println("Give words file and atleast two players");
         }
      } catch(ArrayIndexOutOfBoundsException e) {
         System.out.println("File not given");

      }
   }

   /*
    * Sorts the players and scores lists based on the score values and 
    * prints the top four players
    *
    * @param      player      list of players
    * @param      score       list of scores
    */

   public void sortAndDisplay(String[] player, float[] score) {
      float tempScore;
      String tempName;
      for(int index = 0; index < score.length; index++) {
         for(int innerIndex = 0; innerIndex < score.length - 1; innerIndex++) {
            if( score[innerIndex] < score[innerIndex+1] ) {
               tempScore = score[innerIndex];
               score[innerIndex] = score[innerIndex+1];
               score[innerIndex+1] = tempScore;

               tempName = player[innerIndex];
               player[innerIndex] = player[innerIndex+1];
               player[innerIndex+1] = tempName;
            }
         }
      }

      int length = (score.length <= 4) ? score.length : 4;
      System.out.println("The top " + length +" players are:");
      for(int index = 0; index < length; index++) {
         System.out.println("    " + player[index] + ":     " + score[index]);
      }
   }

   /**
    * Takes a character as input until 8 chances and calculates the score based
    * on whether the guess is right or not.
    *
    * @param      word       word to be guessed
    * @param      hangman    hangman object
    *
    * @return                score after one word is played
    */

   public float playOneWord(String word, Hangman hangman) {
      int guessCount = 0;
      int  wrongGuessCount = 0;
      float score = 0;
      Scanner sc = null;
      for(guessCount = 0; guessCount < noOfGuesses; guessCount++) {
         System.out.print("Guess an alphabet: ");
         sc = new Scanner(System.in);
         // if guess is wrong
         if(!hangman.printWord(word,sc.next().charAt(0))){
            wrongGuessCount++; 
            score += MIN_SCORE; 
            hangman.printHangman(wrongGuessCount);
         }else {
            score += MAX_SCORE; 
         }
         if(word.equals(new String(hangman.guessedLetters))) {
            score += (noOfGuesses - guessCount - 1) * 10;
            System.out.println("Congrats....");
            break;
         }
         System.out.println("No. of guesses left: " + (noOfGuesses - guessCount - 1));
      } // End for loop
      score = ((score - (MIN_SCORE * noOfGuesses)) / 
            ((MAX_SCORE - MIN_SCORE) * noOfGuesses)) * 100;
      //sc.close();
      return score;
   }

   /**
    * Prints the word initially and also after each guess
    * The guessed letters are displayed while others are displayed as dashes.
    *
    * @param      word     word to be guessed
    * @param      letter   letter guessed by user
    *
    * @return              returns true if guess is right. Returns false if 
    *                      guess is wrong or alreayd guessed.
    */

   public boolean printWord(String word, char letter) {
      char letterGuess = letter;
      boolean isLetterPresent = false;
      // print word for the 1st time
      if(letter == ' ') {
         Random random = new Random();
         int noOfDistinctChars = word.length();
         do{
            int index = random.nextInt(word.length());
            guessedLetters[index] = word.charAt(index);
            noOfDistinctChars--;
            letter = word.charAt(index);
            index = 0; // letter occurence can be before the index also
            // if letter is repeated, fill that occurence also
            while((index = word.indexOf(letter,index+1)) != -1) {
               guessedLetters[index] = letter;
            }
         }while( noOfDistinctChars > noOfGuesses-2 );
      }
      // put the char in the word if guessed right else put '_'
      for(int counter = 0; counter < word.length(); counter++) {
         if(letter == guessedLetters[counter] && letterGuess != ' ') {
            System.out.println("Letter already guessed or given!");
            break;
         }
         else if(letter == word.charAt(counter)){
            guessedLetters[counter] = letter;
            isLetterPresent = true;
         }else {
            guessedLetters[counter] = (guessedLetters[counter] != 0) ? 
               guessedLetters[counter] : '_';
         }
      }
      // prints the word
      System.out.print("Word: ");
      for(int counter = 0; counter < guessedLetters.length; counter++) {
         System.out.print(guessedLetters[counter] + " ");
      }System.out.println();
      return isLetterPresent;
   }

   /**
    * Gets a random word from the words list.
    *
    * @param      noOfWords      total number of words
    * @param      hangman        hangman object
    *
    * @return                    the random word
    */

   public String getRandomWord(int noOfWords, Hangman hangman) {

      Random random = new Random();
      int randomIndex = random.nextInt(noOfWords);
      String word = wordsList.get(randomIndex);
      guessedLetters = new char[word.length()];
      return word;
   }

   /**
    * Reads the words file, stores in an list and counts the number of 
    * lines (words) in it.
    *
    * @param      fileName       name of the words text file.
    *
    * @return                    number of words in the file
    */

   public int storeWordsFromFile(String fileName) {
      int noOfLines = 0;
      String line = null;
      try {
         File file = new File(fileName);
         Scanner input = new Scanner(new FileReader(file));
         while(input.hasNextLine()) {
            line = input.nextLine();
            wordsList.add(line);
            noOfLines++;
         }
         input.close();
      }catch(Exception e){
         System.out.println("File not found");
      }
      return noOfLines - 1;
   }

   /**
    * Prints the hangman with # symbols 
    * Draws # wherever the grid values are less than or equal to wrong guesses.
    *
    * @param      noOfWrongGuesses     Number of times a player guesses wrong.
    */

   public void printHangman(int noOfWrongGuesses) {
      for( int row = 0; row < GRID_LENGTH; row++ ) {
         for( int col = 0; col < GRID_WIDTH; col++ ){
            if( grid[row][col] <= noOfWrongGuesses && grid[row][col] > 0 ) {
               System.out.print(SYMBOL);
            }else {
               System.out.print(" ");
            }
         }
         System.out.println();
      }
   }

   /**
    * Assigns an integer value to each part of the 2d grid for drawing hangman.
    */

   public void fillHangmanGrid(){
      // assign 1 for base
      grid[GRID_LENGTH - 2][2] = 1;
      grid[GRID_LENGTH - 2][GRID_WIDTH - 3] = 1;
      int gridCounter = 0;
      int index = 0;
      for(gridCounter = 2; gridCounter < GRID_WIDTH - 2; gridCounter++) {
         grid[GRID_LENGTH - 3][gridCounter] = 1;
      }

      // assign 2 for pole
      for(gridCounter = GRID_LENGTH - 4; gridCounter > 2; gridCounter--) {
         grid[gridCounter][GRID_WIDTH/2-1] = 2; 
      }
      gridCounter++;
      for(index = 0; index < GRID_WIDTH/3; index++) {
         grid[gridCounter][GRID_WIDTH/2 + index] = 2;
      }
      index += GRID_WIDTH/2 - 1; 
      grid[++gridCounter][index] = 2;
      grid[++gridCounter][index] = 2;

      // assign 3 for head
      grid[gridCounter][index-1] = 3;
      grid[gridCounter][index-2] = 3;
      grid[gridCounter][index+1] = 3;
      grid[gridCounter][index+2] = 3;
      grid[++gridCounter][index-2] = 3;
      grid[gridCounter++][index+2] = 3;

      grid[gridCounter][index-1] = 3;
      grid[gridCounter][index-2] = 3;
      grid[gridCounter][index+1] = 3;
      grid[gridCounter][index+2] = 3;

      // assign 4 for body
      for(int counter = 0; counter <= GRID_WIDTH/3; counter++) {
         grid[gridCounter + counter][index] = 4;
      }

      // assign 5 for left hand
      grid[gridCounter+2][index-1] = 5;
      grid[gridCounter+2][index-2] = 5;
      grid[gridCounter+2][index-3] = 5;

      // assign 6 for right hand
      grid[gridCounter+2][index+1] = 6;
      grid[gridCounter+2][index+2] = 6;
      grid[gridCounter+2][index+3] = 6;

      // assign 7 for left leg
      grid[gridCounter+4][index-1] = 7;
      grid[gridCounter+4][index-2] = 7;
      grid[gridCounter+4][index-3] = 7;

      // assign 8 for right leg
      grid[gridCounter+4][index+1] = 8;
      grid[gridCounter+4][index+2] = 8;
      grid[gridCounter+4][index+3] = 8;
   }
}   
