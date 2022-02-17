//words from https://github.com/charlesreid1/five-letter-words/blob/master/sgb-words.txt

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Random;

public class Main   {


    public static String guess;
    public static String answer;
    public static int guessCount = 0;
    public static ArrayList<String> words = new ArrayList<String>();
    public static Scanner scan = new Scanner(System.in);
    public static ArrayList<String> guessLetters = new ArrayList<String>();
    public static ArrayList<String> ansLetters = new ArrayList<String>();
    public static String[][] prevAnswers = new String[6][5];
    public static String[][] prevResults = new String[6][5];
    public static Random rand = new Random();
    
    /*public static String[][] prevAnswers = {
                                            {".", ".", ".", ".", "."},
                                            {".", ".", ".", ".", "."},
                                            {".", ".", ".", ".", "."},
                                            {".", ".", ".", ".", "."},
                                            {".", ".", ".", ".", "."},
                                            {".", ".", ".", ".", "."},
                                            };
    public static String[][] prevResults = {
                                            {".", ".", ".", ".", "."},
                                            {".", ".", ".", ".", "."},
                                            {".", ".", ".", ".", "."},
                                            {".", ".", ".", ".", "."},
                                            {".", ".", ".", ".", "."},
                                            {".", ".", ".", ".", "."},
                                            };
    */


    public static void letterCheck(/*ArrayList<String> newguessLetters, ArrayList<String> newansLetters*/) {
        int correctLetters = 0;
        for(int i = 0; i < guessLetters.size(); i++){
            //System.out.println(guessLetters.get(i) + " " + ansLetters.get(i));
            if((guessLetters.get(i)).equals(ansLetters.get(i)))   {
                ++correctLetters;
                prevResults[guessCount-1][i] = "X";
            }   else if(inTheWord(guessLetters.get(i), ansLetters))    {
                prevResults[guessCount-1][i] = "*";
            }   else    {
                prevResults[guessCount-1][i] = "-";
            }
        }
        
        /*for(int y = 0; y < 6; y++)	{
            for(int x = 0; x < 5; x++)	{
                System.out.print(prevAnswers[y][x]);
            }  
                
        }*/
        /*for(int i = 0; i < 6; i++)	{
            System.out.println(Arrays.toString(prevAnswers[i]) + " " + Arrays.toString(prevResults[i]));
        }*/

        System.out.print("\033[H\033[2J");  
        System.out.flush();  
        print();
        
        if(correctLetters == 5) {
            System.out.println("you von");
            playAgain();
        }   else    {
            guess();
        }
        
    }


    
    public static void main(String[] args) throws FileNotFoundException{

        File file = new File("sgb-words.txt");
        Scanner scanFile = new Scanner(file);
        System.out.print("\033[H\033[2J");  
        System.out.flush();
        for(int i = 0; i < 6; i++)	{
            for(int j = 0; j < 5; j++)	{
                prevAnswers[i][j] = ".";
                prevResults[i][j] = ".";
            }
        }
        
        while(scanFile.hasNext())    {
            String word = scanFile.next();
            words.add(word);
            //System.out.print(words.get(i) + ", ");
        }

        answer = words.get(rand.nextInt(words.size()));
        
        for(int i = 0; i < answer.length(); i++){
            ansLetters.add(answer.substring(i, i+1));
            //System.out.println(ansLetters.get(i));
        }
        System.out.println(answer);


        //ArrayList<String> tries = new ArrayList<String>();
        //for(var i = 0; i < 5; i++)  {}

        //THSI SHIT SUCKS SO BAD FIND A BETTER SAY
        

        guess();
    }

    public static Boolean inTheWord(String letter, ArrayList<String> ansLetters) {
        for(int i = 0; i < ansLetters.size();){
            if(letter.equals(ansLetters.get(i))) {
                return true;
            }   else    {
                return false;
            }
        }
        return null;
   
    }


    public static void playAgain() {
        System.out.print("play again (y/n)? ");
        String yesno = scan.next().toLowerCase();
        if(yesno.equals("y")) {
            guessCount = 0;
            for(int i = 0; i < 6; i++)	{
                for(int j = 0; j < 5; j++)	{
                    prevAnswers[i][j] = "";
                    prevResults[i][j] = "";
                }
            }
            answer = words.get(rand.nextInt(words.size()));
            for(int i = 0; i < answer.length(); i++){
                ansLetters.add(answer.substring(i, i+1));
            }
            System.out.println(answer);
            guess();
        } else if(yesno.equals("n"))  {
            System.out.println("FUCK YOU!!!!!");
        } else  {
            playAgain();
        }
   
    }

    public static void guess() {
        if(guessCount > 5){
            System.out.println("No guesses left bozo");
            playAgain();
        }   else    {
            guessLetters.clear();
            
            guess = (scan.next()).toLowerCase();
            int count = 0;
            for(int i = 0; i < words.size(); i++)	{
                if(guess.equals(words.get(i)))  {
                    ++count;
                    if(count == 6)  {
                        System.out.println("No guesses left bozo");
                        playAgain();
                    }
                } 
            }
            if (count == 0) {
                print();
                System.out.println("Please input a valid word!");

                guess();
            }   else    {
                ++guessCount;
                for(int i = 0; i < guess.length(); i++){
                    guessLetters.add(guess.substring(i, i+1));
                    //System.out.println(guessLetters.get(i));
                    prevAnswers[guessCount-1][i] = guess.substring(i, i+1);
                }
                letterCheck(/*guessLetters, ansLetters*/);
            }
        }
        
   
    }

    public static void print()  {
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
        for(int i = 0; i < guessCount; i++)	{
            for(int j = 0; j < 5; j++)	{
                System.out.print((prevAnswers[i][j]).toUpperCase());
            }
            System.out.print(" ");
            for(int j = 0; j < 5; j++)	{
                System.out.print(prevResults[i][j]);
            }
            System.out.println();
        }
    }

}

