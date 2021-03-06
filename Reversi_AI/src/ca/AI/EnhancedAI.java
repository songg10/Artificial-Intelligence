package ca.AI;
import ca.reversi.Board;

import java.util.*;

import static java.lang.Math.abs;


public class EnhancedAI {

    static HashSet<Board.Point> rec = new HashSet<>();
    private static final char boardX[] = new char[]{'A','B','C','D','E','F','G','H'};
    private static  final int weightedBoard [][] = new int[] [] {
            {100, -1, 5, 2, 2, 5, -1, 100,},
            {-1, -10, 1, 1, 1, 1, -10, -1,},
            {5, 1, 1, 1, 1, 1, 1, 5,},
            {2 , 1, 1, 0, 0, 1, 1, 2,},
            {2, 1, 1, 0, 0, 1, 1, 2,},
            {5, 1, 1, 1, 1, 1, 1, 1,},
            {-1, -10 , 1, 1, 1, 1, -10, -1,},
            {100, -1, 5, 2, 2, 5, -1, 100,},
    };

    public static Board.Point chooseMove(){
        Board.Point max = null;
        double most_win=0;


        for(Board.Point mov1:rec){
            if (abs(mov1.win+weightedBoard[mov1.x][mov1.y])>=most_win){
                max=mov1;
                most_win=mov1.win+weightedBoard[mov1.x][mov1.y];
            }
        }
        System.out.println("White's move: "+ boardX[max.y]+(max.x+1));
        return max;
    }



    public static void playout(Board board) {
        Board.Point move = board.new Point(-1, -1);
        rec = board.getPlaceableLocations('W', 'B');

        int result;
        Boolean skip;
        char end_game = '0';
        int count=0;

        for(Board.Point mov:rec)
        {
            Board b1 = new Board(board);
            b1.placeMove(mov,'W','B');
            for(int i=0;i<1000;i++){
                Board b = new Board(b1);
                while (true) {

                    skip = false;

                    HashSet<Board.Point> blackPlaceableLocations = b.getPlaceableLocations('B', 'W');
                    HashSet<Board.Point> whitePlaceableLocations = b.getPlaceableLocations('W', 'B');
                    result = b.gameResult(whitePlaceableLocations, blackPlaceableLocations);

                    if (result == 0) {
                        end_game = 'D';
                        break;
                    } else if (result == 1) {
                        end_game = 'W';
                        break;
                    } else if (result == -1) {
                        end_game = 'B';
                        break;
                    }

                    if (blackPlaceableLocations.isEmpty()) {
                        skip = true;
                    }

                    if (!skip) {
                        Random rand = new Random();
                        int rand_int = rand.nextInt(blackPlaceableLocations.size());
                        int index = 0;
                        for (Board.Point obj : blackPlaceableLocations) {
                            if (index == rand_int) {
                                move = obj;
                                break;
                            }
                            index++;

                        }
                        b.placeMove(move, 'B', 'W');
                    }
                    skip = false;
                    whitePlaceableLocations = b.getPlaceableLocations('W', 'B');
                    blackPlaceableLocations = b.getPlaceableLocations('B', 'W');


                    result = b.gameResult(whitePlaceableLocations, blackPlaceableLocations);

                    if (result == 0) {
                        end_game='D';
                        break;
                    } else if (result == 1) {
                        end_game = 'W';
                        break;
                    } else if (result == -1) {
                        end_game = 'B';
                        break;
                    }

                    if (whitePlaceableLocations.isEmpty()) {
                        skip = true;
                    }
                    if (!skip) {
                        Random rand = new Random();
                        int rand_int = rand.nextInt(whitePlaceableLocations.size());
                        int index = 0;
                        //System.out.println("index"+ index+"  randint"+rand_int);
                        for (Board.Point obj : whitePlaceableLocations) {
                            if (index == rand_int) {
                                move = obj;
                                break;
                            }
                            index++;
                        }

                        b.placeMove(move, 'W', 'B');
                    }

                    b.updateScores();

                }
                if (end_game == 'W')
                    mov.win++;
                else if (end_game == 'B')
                    mov.lose++;
                else if (end_game =='D')
                    mov.draw++;

            }

        }
        System.out.println("end playout");
    }
}
