package ca.main;

import ca.reversi.Board;

import java.util.InputMismatchException;
import java.util.Scanner;

import static ca.reversi.Reversi.initGame;

public class Main {

    public static int white_win = 0;
    public static int black_win = 0;
    public static int draw = 0;

    public static int optionMenu(){
        int input = 0;
        System.out.println("Please choose an option:\n1.AI (Black) vs Player (White)\n2.AI (non-heuristic-Black) vs AI (heuristic-White)");
        while (true) {
            System.out.print("> ");
            Scanner scan = new Scanner(System.in);
            try {
                input = scan.nextInt();
                scan.nextLine();
            } catch (InputMismatchException exc) {
                System.out.println("Please choose an appropiate option");
                continue;
            }
            if (input == 1 || input == 2) {
                break;
            }
            System.out.println("Please choose an appropiate option");
        }
        return input;
    }

    public static void main(String[] args) {
	// write your code here
        int option = optionMenu();
        int sampleSize = 1;
        if (option == 2){
            System.out.print("Please choose the sample size (how match matches): ");
            Scanner scanner = new Scanner(System.in);
            sampleSize = scanner.nextInt();
            scanner.nextLine();
        }
        for (int i = 0; i < sampleSize; i++) {
            Board b = new Board();
            initGame(b, option);
        }
        System.out.println("Summary: White " + white_win + "-" + black_win + " Black\nDraw: "+ draw);
    }
}
