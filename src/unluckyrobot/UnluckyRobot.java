/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unluckyrobot;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Shawn Gregory
 */
public class UnluckyRobot {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        int totalScore = 300;
        int reward;
        char direction;
        int x = 0;
        int y = 0;
        int u_reward = 10;   // u = up
        int lrd_reward = 50;    // lrd l = left r = right d = down
        for (int itrCount = 0; isGameOver(x, y, totalScore, itrCount); itrCount++){
            displayInfo_function(x, y, itrCount, totalScore);
            direction = inputDirection();
            reward = reward();
            switch (direction) {
                case 'u':
                    reward -= u_reward;
                    y += 1;
                    break;
                case 'r':
                    reward -= lrd_reward;
                    x += 1;
                    break;
                case 'l':
                    reward -= lrd_reward;
                    x -= 1;
                    break;
                case 'd':
                    reward -= lrd_reward;
                    y -= 1;
            }  
            reward = punishOrMercy(direction, reward);
            if (doesExceed(x, y, direction)){
                System.out.println("Exceed boundary, -2000 damage applied");
                totalScore -= 2000;
            }
            totalScore += reward;
            System.out.printf("\n");
        }
        evaluation(totalScore);
    }
    
    /**
     * At the start of each round, this method displays all relevant information
     * @param x x position of the robot
     * @param y y position of the robot
     * @param itrCount how many turns have gone by
     * @param totalScore how much points the player has
     */
    public static void displayInfo_function(int x, int y, int itrCount, int totalScore) {
        System.out.println("For point (X=" + x + "), (Y=" + y + ") at iterations: "
                + itrCount + " the total score: " + totalScore);
    }
    
    /**
     * To find out if the robot has left the grid
     * @param x x position of the robot
     * @param y y position of the robot
     * @param direction the direction the robot is heading
     * @return true if the robot has left the grid
     */
    public static boolean doesExceed(int x, int y, char direction){
        int max = 4;
        int min = 0;
        return ((x > max || x < min) || (y > max || y < min));
    }
    
    /**
     * To calculate the reward of the turn
     * @return the amount of reward the player receives
     */
    public static int reward(){
        
        Random rand = new Random();
        
        int negative = -100;
        int postive4 = 300;
        int postive5 = 400;
        int postive6 = 600;
        int dice = rand.nextInt(6) + 1;
        int reward = 0;
        switch (dice){
            case 1:
            case 2:
            case 3:
                reward = dice * negative;
                break;
            case 4:
                reward = postive4;
                break;
            case 5:
                reward = postive5;
                break;
            case 6:
                reward = postive6;
        }
        System.out.println("Dice: " + dice + ", reward: " + reward);
        return reward;
    }
    
    /**
     * To show mercy to players who receive a negative value
     * @param direction only activates player chooses 'u'
     * @param reward only activates if player has negative value
     * @return if player receives mercy or not
     */
    public static int punishOrMercy(char direction, int reward){
        
        Random rand = new Random();
        
        int coin;
        if (reward < 0 && direction == 'u'){
            coin = rand.nextInt(2);
            if (coin == 1){
                System.out.println("Coin: tail | Mercy, the negative reward is removed");
                return 0;
            }
            else {
                System.out.println("Coin: head | No mercy, the negative reward is applied");
                return reward;
            }
        }
        else
            return reward;
    }
    
    /**
     * To convert a name to a TitleCase
     * @param str The name to be converted
     * @return The converted name
     */
    public static String toTitleCase(String str) {
        return Character.toUpperCase(str.charAt(0)) + str.substring(1, str.indexOf(" "))
               .toLowerCase() + " " + Character.toUpperCase(str.charAt(str.indexOf(" ") + 1))
               + str.substring(str.indexOf(" ") + 2).toLowerCase();
    }
    
    /**
     * Win/Lose condition is printed
     * @param totalScore Score used to determine Win/Lose condition
     */
    public static void evaluation(int totalScore){
    
        Scanner console = new Scanner (System.in);
        
        System.out.print("Please enter your name (only two words): ");
        String name = console.nextLine();
        name = toTitleCase(name);
        if (totalScore >= 2000)
            System.out.println("Victory, " + name + ", your score is " + totalScore);
        else
            System.out.println("Mission failed, " + name + ", your score is " + totalScore);    
    }
    
    /**
     * To determine the direction you are going up, down, left or right
     * @return the direction chosen
     */
    public static char inputDirection(){
        
        Scanner console = new Scanner (System.in);
        
        String validinput = "uldr";
        char inputDirection;
        do {
            System.out.print("Please input a valid direction: ");
            inputDirection = console.next().charAt(0);
        } while (!validinput.contains(String.valueOf(inputDirection)));
        return inputDirection;
    } 
    
    /**
     * To determine Win/Lose Condition if it meets certain conditions
     * @param x x coordination
     * @param y y coordination
     * @param totalScore how much points the player has
     * @param itrCount the amount of turns the player has received
     * @return true if any of the conditions are met the game is ended  
     */
    public static boolean isGameOver(int x, int y, int totalScore, int itrCount){
        int min = 0;
        int max = 4;
        int lowScore = -1000;
        int highScore = 2000;
        int maxturns = 20;
        if (x == max & y == min)
            return false;
        if (x == max & y == max)
            return false;
        if (totalScore < lowScore)
            return false;
        if (itrCount > maxturns)
            return false;
        return totalScore <= highScore;
    }
}
