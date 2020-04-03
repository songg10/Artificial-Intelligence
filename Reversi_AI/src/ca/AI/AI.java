package ca.AI;
import ca.reversi.Board;
import java.util.HashSet;
import java.util.Random;

public class AI {



    static HashSet<Board.Point> rec = new HashSet<>();
    private static final char boardX[] = new char[]{'A','B','C','D','E','F','G','H'};

    public static Board.Point chooseMove(){
        Board.Point max = null;
        int most_win=0;
        for(Board.Point mov:rec){
            if (mov.win>=most_win){
                most_win=mov.win;
                max=mov;
            }
        }
        System.out.println("Black's move: "+ boardX[max.y]+(max.x+1));
        return max;
    }



    public static void playout(Board board) {
        Board.Point move = board.new Point(-1, -1);
        rec = board.getPlaceableLocations('B', 'W');

        int result;
        Boolean skip;
        char end_game = '0';
        int count=0;

        for(Board.Point mov:rec)
        {
            Board b1 = new Board(board);
            b1.placeMove(mov,'B','W');
            for(int i=0;i<1000;i++){
                Board b = new Board(b1);
                while (true) {

                    skip = false;
                    HashSet<Board.Point>  whitePlaceableLocations = b.getPlaceableLocations('W', 'B');
                    HashSet<Board.Point>  blackPlaceableLocations = b.getPlaceableLocations('B', 'W');


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
                    skip = false;

                    blackPlaceableLocations = b.getPlaceableLocations('B', 'W');
                    whitePlaceableLocations = b.getPlaceableLocations('W', 'B');
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

                    b.updateScores();

                }
                if (end_game == 'B')
                    mov.win++;
                else if (end_game == 'W')
                    mov.lose++;
                else if (end_game =='D')
                    mov.draw++;

            }

        }
        System.out.println("end playout");
    }
}
