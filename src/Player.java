
import java.util.ArrayList;

public abstract class Player {
    private String symbol;
    Player(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public abstract int[] getCoordinates(ArrayList<String> availableSpaces, ArrayList<ArrayList<String>> table);
}
