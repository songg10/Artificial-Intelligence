package ca.reversi;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import ca.AI.AI;
import ca.AI.EnhancedAI;
import ca.main.Main;

public class Reversi {
    public static void initGame(Board b, int option){
        Scanner scan = new Scanner(System.in);
        Board.Point move = b.new Point(-1, -1);
        System.out.println("Black Moves first");

        int result;
        Boolean skip;
        String input;

        while(true){
            skip = false;

            HashSet<Board.Point> blackPlaceableLocations = b.getPlaceableLocations('B', 'W');
            HashSet<Board.Point> whitePlaceableLocations = b.getPlaceableLocations('W', 'B');

            b.showPlaceableLocations(blackPlaceableLocations, 'B', 'W');
            result = b.gameResult(whitePlaceableLocations, blackPlaceableLocations);

            if(result==0){System.out.println("It is a draw."); Main.draw++; break;}
            else if(result==1){System.out.println("White wins: "+b.WScore+":"+b.BScore); Main.white_win++; break;}
            else if(result==-1){System.out.println("Black wins: "+b.BScore+":"+b.WScore); Main.black_win++;break;}

            if(blackPlaceableLocations.isEmpty()){
                System.out.println("Black needs to skip... Passing to white");
                skip = true;
            }

            if(!skip){
                System.out.println("Place move (Black): ");
                Board newb = new Board(b);
                AI newAI = new AI();

                newAI.playout(newb);
                move=newAI.chooseMove();
                b.placeMove(move, 'B', 'W');
                b.updateScores();
                System.out.println("\nBlack: "+b.BScore+" White: "+b.WScore);
            }
            skip = false;

            whitePlaceableLocations = b.getPlaceableLocations('W', 'B');
            blackPlaceableLocations = b.getPlaceableLocations('B', 'W');

            b.showPlaceableLocations(whitePlaceableLocations, 'W', 'B');
            result = b.gameResult(whitePlaceableLocations, blackPlaceableLocations);

            if(result==0){System.out.println("It is a draw."); Main.draw++; break;}
            else if(result==1){System.out.println("White wins: "+b.WScore+":"+b.BScore); Main.white_win++; break;}
            else if(result==-1){System.out.println("Black wins: "+b.BScore+":"+b.WScore); Main.black_win++;break;}

            if(whitePlaceableLocations.isEmpty()){
                System.out.println("White needs to skip... Passing to Black");
                skip = true;
            }

            if(!skip){
                System.out.println("Place move (White): ");
                if (option == 2) {
                    Board newb = new Board(b);
                    EnhancedAI newAI = new EnhancedAI();

                    newAI.playout(newb);
                    move = newAI.chooseMove();
                }

                else {
                    System.out.println("Place move (Black): ");
                    input = scan.next();
                    move.y = b.coordinateX(input.charAt(0));
                    move.x = (Integer.parseInt(input.charAt(1) + "") - 1);

                    while (!whitePlaceableLocations.contains(move)) {
                        System.out.println("Invalid move!\n\nPlace move (White): ");
                        input = scan.next();
                        move.y = b.coordinateX(input.charAt(0));
                        move.x = (Integer.parseInt(input.charAt(1) + "") - 1);
                    }
                }
                b.placeMove(move, 'W', 'B');
                b.updateScores();
                System.out.println("\nWhite: "+b.WScore+" Black: "+b.BScore);
            }
        }

    }
}
