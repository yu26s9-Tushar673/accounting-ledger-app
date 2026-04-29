package com.pluralsight.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Console
{
    private static final Scanner scanner = new Scanner(System.in);

    public static LocalDate promptForDate(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                String input = scanner.nextLine().trim();

                if (input.isEmpty())
                {   return null;    }

                return LocalDate.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (DateTimeParseException e) {
                System.out.print("Invalid date format, please enter a date with format (yyyy-MM-dd): ");
            }
        }
    }

    /**
     * Prompts the user for a string.
     * @param prompt to display the user
     * @return the string the user selected.
     */
    public static String promptForString(String prompt) {
        System.out.print(prompt);
        while (true)
        {
            try {
                String input = scanner.nextLine().trim();
                if (input.isEmpty())
                {   return "";    }
                return input;
            } catch (Exception e) {
                System.out.println("Error! Please try again.");
            }
        }
    }

    /**
     * Prompts the user for an integer.
     * @param prompt to display the user
     * @return the int the user selected.
     */
    public static double promptForDouble(String prompt) {

        System.out.print(prompt);

        double result;

        while(true){
            try{
                result =  scanner.nextDouble();
                scanner.nextLine();
                return result;
            }
            catch (InputMismatchException e){
                System.out.print("Invalid selection, please enter a number: ");
            }
            scanner.nextLine(); // Buffer handling
        }
    }


    /**
     * Prompts the user for an integer.
     * @param prompt to display the user
     * @return the int the user selected.
     */
    public static int promptForInt(String prompt) {

        System.out.print(prompt);

        int result;

        while(true){
            try{
                result =  scanner.nextInt();
                scanner.nextLine();
                return result;
            }
            catch (InputMismatchException e){
                System.out.print("Invalid selection, please enter a number: ");
            }
            scanner.nextLine(); // Buffer handling
        }
    }

    /**
     * Prompts the user for a float integer.
     * @param prompt to display the user
     * @return the float the user selected.
     */
    public static float promptForFloat(String prompt) {

        System.out.print(prompt);

        float result;

        while(true){
            try{
                result =  scanner.nextFloat();
                scanner.nextLine();
                return result;
            }
            catch (InputMismatchException e){
                System.out.print("Invalid selection, please enter a number: ");
            }
            scanner.nextLine(); // Buffer handling
        }
    }

    /**
     * Prompts the user for a float integer.
     * @param prompt to display the user
     * @return the float the user selected.
     */
    public static Float promptNullableFloat(String prompt) {

        System.out.print(prompt);

        while(true){
            try{
                String input =  scanner.nextLine().trim();

                if(input.isEmpty())
                { return null;}

                return Float.parseFloat(input);
            }
            catch (InputMismatchException e){
                System.out.print("Invalid selection, please enter a number: ");
            }
        }
    }

    /**
     * Prompts the user for an integer.
     * @param prompt to display the user
     * @return the int the user selected.
     */
    public static int promptForInt(String prompt, int min, int max) {

        System.out.print(prompt);

        int result;

        while(true){
            try{
                result =  scanner.nextInt();

                if(result >= min && result <= max){
                    scanner.nextLine();
                    return result;
                }
                else{
                    scanner.nextLine();
                    System.out.printf("Invalid selection, please enter a number between %d and %d: ", min, max);
                }
            }
            catch (InputMismatchException e){
                scanner.nextLine();
                System.out.print("Invalid selection, please enter a number: ");
            }
        }
    }

    /**
     * Prompts the user for a Yes or No which is returned as a boolean.
     * @param prompt to display the user
     * @return the boolean
     */
    public static boolean promptForYesNo(String prompt) {
        System.out.print(prompt + "(yes/no): ");
        String userInput = scanner.nextLine().trim();
        while (true) {
            if (userInput.equalsIgnoreCase("yes") || userInput.equalsIgnoreCase("y"))
            {   return true;    }
            else if (userInput.equalsIgnoreCase("no") || userInput.equalsIgnoreCase("n"))
            {   return false;   }
            System.out.println("Invalid Input! Please enter yes or no.");
        }
    }
}