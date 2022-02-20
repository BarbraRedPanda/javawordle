//words from https://github.com/charlesreid1/five-letter-words/blob/master/sgb-words.txt

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
    public static int ansNumb;
    public static int encodedNum;
    public static LocalDate dateObj = LocalDate.now();
    public static DateTimeFormatter format1 = DateTimeFormatter.ofPattern("MMddyyyy");
    public static DateTimeFormatter format2 = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    public static String filtDate = dateObj.format(format1);
    public static String filtDate2 = dateObj.format(format2);
    //public static int losses = 0;
    public static ArrayList<Integer> winStats = new ArrayList<Integer>();
    

    public static ArrayList<ArrayList<String>> statsOut = new ArrayList<ArrayList<String>>();
    /*public static String[][] statsOut = {
                                            {"|", ".", ".", ".", ".". },
                                            {"|", ".", ".", ".", "."},
                                            {"|", ".", ".", ".", "."},
                                            {"|", ".", ".", ".", "."},
                                            {"|", ".", ".", ".", "."},
                                            {"|", ".", ".", ".", "."},
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

        clearOut(); 
        print();
        
        if(correctLetters == 5) {           //Note: logic could be written as guess.equals(answer), kept it this way just inh case i wanted to do something 
            System.out.print("you von, ");
            winStats.add((guessCount));
            //System.out.println(guessCount);
            playAgain();
        }   else    {
            //System.out.println(guessCount);
            guess();
        }
        
    }

    public static void main(String[] args) throws FileNotFoundException{

        File file = new File("sgb-words.txt");
        Scanner scanFile = new Scanner(file);
        

        
        
        encodedNum = 0;
        for(int i = 0; i < 8; i++)	{
            if(i < 2 || i > 3) encodedNum = encodedNum + Integer.parseInt(filtDate.substring(i, i + 1));
            else if(i >= 2 && i < 4) encodedNum = encodedNum + (2*(Integer.parseInt(filtDate.substring(i, i + 1))));
        }
        

        clearOut();
        /*for(int i = 0; i < 6; i++)	{
            for(int j = 0; j < 5; j++)	{
                prevAnswers[i][j] = ".";
                prevResults[i][j] = ".";
            }
        }*/
        
        while(scanFile.hasNext())    {
            String word = scanFile.next();
            words.add(word);
            //System.out.print(words.get(i) + ", ");
        }


        
        System.out.print("Daily or random? (d/r) ");
        String daily = scan.nextLine().toLowerCase();
        clearOut();
        if(daily.equals("d")) newAnswer(true);
        else newAnswer(false);
        //System.out.println(answer);


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


    public static void newAnswer(Boolean daily)  {
        ansLetters.clear();
        
        if(!daily) ansNumb = rand.nextInt(words.size());
        else if(daily) ansNumb = (encodedNum);
        answer = words.get(ansNumb);

        for(int i = 0; i < answer.length(); i++){
            ansLetters.add(answer.substring(i, i+1));
        }
    }

    public static void playAgain() {
        System.out.print("play again? (y/n) ");
        String yesno = scan.next().toLowerCase();
        if(yesno.equals("y")) {
            clearOut();
            guessCount = 0;
            for(int i = 0; i < 6; i++)	{
                for(int j = 0; j < 5; j++)	{
                    prevAnswers[i][j] = "";
                    prevResults[i][j] = "";
                }
            }
            newAnswer(false);
            //System.out.println(answer);
            guess();
        } else if(yesno.equals("n"))  {
            System.out.println("FUCK YOU!!!!!");
            printStats();
            //playAgain();
        } else  {
            playAgain();
        }
   
    }

    public static void guess() {
        if(guessCount > 5){
            System.out.println("No guesses left bozo");
            System.out.println("Correct answer: " + answer);
            winStats.add((guessCount));
            playAgain();
        }   else    {
            guessLetters.clear();
            if(guessCount == 0) {
                print();
                System.out.print("Guess: ");
            } 
            guess = (scan.next()).toLowerCase();
            if(guess.equals("hackermans")) {
                System.out.println(answer);
                guess();
            }
            Boolean listed = false;
            for(int i = 0; i < words.size(); i++)	{
                if(guess.equals(words.get(i)))  {
                    listed = true;
                }
            }
            if (!listed) {
                print();
                System.out.println("Please input a valid word!");
                System.out.print("Guess: ");
                guess();
            }   else    {
                ++guessCount;
                for(int i = 0; i < guess.length(); i++) {
                    guessLetters.add(guess.substring(i, i+1));
                    //System.out.println(guessLetters.get(i));
                    prevAnswers[guessCount-1][i] = guess.substring(i, i+1);
                }
                letterCheck(/*guessLetters, ansLetters*/);
            }
        }
        
   
    }
    public static void clearOut()   {
        System.out.print("\033[H\033[2J");  
        System.out.flush(); 
    }
    public static void print()  {
        clearOut();
        System.out.println("Wordle #" + (ansNumb + 1) + " " + filtDate2);
        System.out.println(answer);
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

    public static void printStats()  {
        clearOut();
        //int[] gcFreq = new int[7];
        ArrayList<Integer> gcFreq = new ArrayList<Integer>();
        for(int i = 1; i < 8; i++)	{
            gcFreq.add(Collections.frequency(winStats, i));
        }
        int highest = gcFreq.get(0);
        for(int i = 0; i < gcFreq.size(); i++)	{
            
            if(gcFreq.get(i) > highest) {
                highest = gcFreq.get(i);
            }

        }
        for(int i = 0; i < highest; i++)	{
            statsOut.add(new ArrayList<String>());
            statsOut.get(i).add(0, "|");
            for(int j = 0; j < 7; j++)	{
                if(gcFreq.get(j) >= (highest-i))  {
                    statsOut.get(i).add(j+1, "# ");
                }   else{
                    statsOut.get(i).add(j+1, "  ");
                }
                
            }
            
            
        }
        statsOut.add(new ArrayList<String>());
        for(int i = 0; i < 7; i++)	{
            if(i == 0) statsOut.get(highest).add(i, "|" + Integer.toString(i+1) + " ");
            else if(i == 6) statsOut.get(highest).add(i, "L");
            else statsOut.get(highest).add(i, Integer.toString(i+1) + " ");
        }
        for(int i = 0; i < (highest + 1); i++)	{
            for(int j = 0; j < statsOut.get(i).size(); j++)	{
                System.out.print(statsOut.get(i).get(j));
            }
            System.out.println();
            if(i == highest-1)    {
                System.out.print("|- -");
                for(int j = 0; j < 4; j++)	{
                    System.out.print(" -");
                }
                System.out.println(" -");
            }
        }
        
        
    }


    /*public static void finalLists(String result, int howManyGuesses)  {
        winStats.add(guessCount);

    }*/



}

