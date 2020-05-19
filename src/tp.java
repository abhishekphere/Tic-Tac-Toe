//Ryan McCabe
//HW 2-P

import java.util.*;

public class tp {

    public static int[] numbers;
    public static int[] operators;
    public static int target;
    public static int currentbest;
    public static String output;

    public static void main(String[] args) {
        numbers = new int[] {9, 0, 9, 1, 9, 3, 6, 7, 4, 9, 2,
                6, 7, 6, 9, 8, 2, 6, 6, 2, 6, 0, 3, 9, 9, 1, 2, 3, 3,       //set of numbers to use
                4, 6, 1, 7, 4, 4, 6, 7, 4, 5, 0, 9, 6, 8, 2, 0, 5, 9, 9,
                5, 1, 1, 2, 0, 6, 9, 7, 1, 1, 2, 6, 3, 3, 2, 7, 7, 0, 9,
                0, 3, 2, 2, 3, 2, 4, 7, 8, 5, 9, 3, 7, 1, 3, 4, 8, 7, 4,
                0, 7, 3, 4, 5, 4, 4, 1, 7, 7, 8, 1, 2, 0};
        System.out.println("Number Set: " + Arrays.toString(numbers));

        target = 5038;                                                      //target value to climb towards
        System.out.println("Target: " + target);
        System.out.println();

        operators = new int[99];                                            //generate a random set of operators
        for (int i = 0; i < 99; i++){                                       //1=+, 2=-, 3=*, 4=/
            int temp = (int)(Math.random() * 4);                            //generate 99 operators to place in between
            operators[i] = temp;                                            //the 100 numbers
        }

        int calc = statecalc();                                             //adding up the initial state
        System.out.println(output);
        System.out.println("Distance from Target " + currentbest);

        do{
            int op = (int)(Math.random() * 2);
            if (op == 1){                               //op = 1 is doing a swap
                int value1 = (int)(Math.random() * 99); //choosing the two numbers to swap
                int value2 = (int)(Math.random() * 99);
                swap (value1,value2);                   //doing the swap
            } else {
                int value1 = (int)(Math.random() * 98); //op = 2 is doing changeSign
                int value2 = (int)(Math.random() * 4);  //chosing the operator to swap and what to swap it to
                changeSign(value1,value2);              //doing the swap
            }

            calc = statecalc();
            if (currentbest > calc){                    // if the new number is closer
                currentbest = calc;                     // set it as the new best
                System.out.println("Best state: " + output);
                System.out.println("Distance from Target " + currentbest);
            }
        } while (currentbest >= 0);                      //keep going until the distance is greater or equal to 0
    }

    //Calculates the current distance from the current set of numbers/operators to the target
    public static int statecalc(){
        int distance = numbers[0];                          //running total of numbers
        output = new String();                              //a String that will print the numbers and operations
        output = output.concat(String.valueOf(numbers[0])); //adding the first number to distance and output string

        for (int i = 0; i < 99; i++){
            if (operators[i] == 0){         //0 = +
                distance += numbers[i+1];
                output = output.concat("+" + String.valueOf(String.valueOf(numbers[i+1])));
            } else if (operators[i] == 1){  //1 = -
                distance -= numbers[i+1];
                output = output.concat("-" + String.valueOf(String.valueOf(numbers[i+1])));
            } else if (operators[i] == 2){  //2 = *
                distance = distance * numbers[i+1];
                output = output.concat("*" + String.valueOf(String.valueOf(numbers[i+1])));
            } else {                        //3 = /
                if (numbers[i+1] != 0){     //avoid dividing by zero
                    distance = distance / numbers[i+1];
                    output = output.concat("/" + String.valueOf(String.valueOf(numbers[i+1])));
                }
            }
        }
        return (target - distance);         //return how far away from the target we are
    }

    //Swaps two given numbers at the indexes value1 and value2
    public static void swap(int value1, int value2){
        int temp = numbers[value1];
        numbers[value1] = numbers[value2];
        numbers[value2] = temp;
    }

    //changes the operator at index value1 to the operator represented by value2
    public static void changeSign(int value1, int value2){
        operators[value1] = value2;
    }
}
