
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Minimax {

    private static String playerOne = "X";
    private static String playerTwo = "O";
    private static ArrayList<Object> originalBoard =
            new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8));
    Minimax() {}

    public static Move getMove(ArrayList<ArrayList<String>> table, String player, String opponent) {
        int originalBoardIndex = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String cellValueForRow = table.get(i).get(j);
                if (!cellValueForRow.equals(" ")) {
                    originalBoard.set(originalBoardIndex, cellValueForRow);
                }
                originalBoardIndex++;
            }
        }
        playerOne = player;
        playerTwo = opponent;
        Move move = minimax(originalBoard, player);
        return move;
    }

    private static Move minimax(ArrayList<Object> newBoard, String player) {
        ArrayList<Integer> availableSpots = emptyIndexes(newBoard);

        // checks for the terminal states such as win, lose, and tie and returning a value accordingly
        if (winning(newBoard, playerTwo)){
            return new Move(-10);
        }
        else if (winning(newBoard, playerOne)){
            return new Move(10);
        }
        else if (availableSpots.size() == 0){
            return new Move(0);
        }

        ArrayList<Move> moves = new ArrayList<>();

        for (int i = 0; i < availableSpots.size(); i++) {
            //create an object for each and store the index of that spot
            Move move = new Move();
            int index = availableSpots.get(i);
            move.setIndex(index);

            // set the empty spot to the current player
            newBoard.set(index, player);

            /*collect the score resulted from calling minimax
            on the opponent of the current player*/
            if (player.equals(playerOne)){
                Move result = minimax(newBoard, playerTwo);
                move.setScore(result.getScore());
            }
            else{
                Move result = minimax(newBoard, playerOne);
                move.setScore(result.getScore());
            }

            // reset the spot to empty
            newBoard.set(availableSpots.get(i), move.getIndex());

            // push the object to the array
            moves.add(move);
        }

        // if it is the computer's turn loop over the moves and choose the move with the highest score
        int bestMove = -1;

        if(player.equals(playerOne)){
            int bestScore = -10000;
            for(int i = 0; i < moves.size(); i++){
                if(moves.get(i).getScore() > bestScore){
                    bestScore = moves.get(i).getScore();
                    bestMove = i;
                }
            }
        } else {
            // else loop over the moves and choose the move with the lowest score
            int bestScore = 10000;
            for(int i = 0; i < moves.size(); i++){
                if(moves.get(i).getScore() < bestScore){
                    bestScore = moves.get(i).getScore();
                    bestMove = i;
                }
            }
        }

        // return the chosen move (object) from the moves array
        return moves.get(bestMove);
    }

    private static ArrayList<Integer> emptyIndexes(ArrayList<Object> board) {
        return IntStream.range(0, board.size())
                .filter(i -> board.get(i) instanceof Integer)
                .boxed()
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private static boolean winning(ArrayList<Object> board, String player) {
        Object cell0 = board.get(0);
        Object cell1 = board.get(1);
        Object cell2 = board.get(2);
        Object cell3 = board.get(3);
        Object cell4 = board.get(4);
        Object cell5 = board.get(5);
        Object cell6 = board.get(6);
        Object cell7 = board.get(7);
        Object cell8 = board.get(8);

        if (
                (cell0 == player && cell1 == player && cell2 == player) ||
                        (cell3 == player && cell4 == player && cell5 == player) ||
                        (cell6 == player && cell7 == player && cell8 == player) ||
                        (cell0 == player && cell3 == player && cell6 == player) ||
                        (cell1 == player && cell4 == player && cell7 == player) ||
                        (cell2 == player && cell5 == player && cell8 == player) ||
                        (cell0 == player && cell4 == player && cell8 == player) ||
                        (cell2 == player && cell4 == player && cell6 == player)
        ){
            return true;
        } else {
            return false;
        }
    }
}
