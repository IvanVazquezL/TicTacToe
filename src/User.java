
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class User extends Player{
    private Scanner scanner = new Scanner(System.in);
    User(String symbol) {
        super(symbol);
    }

    @Override
    public int[] getCoordinates(ArrayList<String> availableSpaces, ArrayList<ArrayList<String>> table) {
        int[] coordinates;
        do {
            System.out.println("Enter the coordinates:");
            String inputLine = scanner.nextLine();

            if (!isAValidInputLine(inputLine)) {
                System.out.println("You should enter numbers!");
                continue;
            }

            coordinates = Arrays.stream(inputLine.split("\\s"))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            if (!areCoordinatesWithinBounds(coordinates)) {
                System.out.println("Coordinates should be from 1 to 3!");
                continue;
            }

            if (!isCellEmpty(inputLine, availableSpaces)) {
                System.out.println("This cell is occupied! Choose another one!");
                continue;
            }

            availableSpaces.remove(inputLine);
            break;
        } while(true);

        return coordinates;
    }

    private static boolean isAValidInputLine(String inputLine) {
        return inputLine.matches("\\d+\\s\\d+");
    }

    private static boolean isCellEmpty(String inputLine, ArrayList<String> availableSpaces) {

        return availableSpaces.contains(inputLine);
    }

    private static boolean areCoordinatesWithinBounds(int[] coordinates) {
        int MIN_COORDINATE = 1;
        int MAX_COORDINATE = 3;

        int row = coordinates[0];
        int column = coordinates[1];

        return row >= MIN_COORDINATE && row <= MAX_COORDINATE &&
                column >= MIN_COORDINATE && column <= MAX_COORDINATE;
    }
}
