import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class TicTacToe {
    private static ArrayList<ArrayList<String>> table = new ArrayList<>();
    private static HashMap<String, String> tablesSymbols = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);
    private static int quantityX = 0;
    private static int quantityO = 0;


    TicTacToe(String initialState) {
        tablesSymbols.put("_", " ");
        tablesSymbols.put("X", "X");
        tablesSymbols.put("O", "O");
        initializeTable(initialState);
        displayTable();
    }

    public static void startGame() {
        System.out.println("Enter the cells:");
        String initialState = scanner.nextLine();
        new TicTacToe(initialState);

        do {
            System.out.println("Enter the coordinates:");
            String inputLine = scanner.nextLine();

            if (!isAValidInputLine(inputLine)) {
                System.out.println("You should enter numbers!");
                continue;
            }

            int[] coordinates = Arrays.stream(inputLine.split("\\s"))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            if (!areCoordinatesWithinBounds(coordinates)) {
                System.out.println("Coordinates should be from 1 to 3!");
                continue;
            }

            if (!isCellEmpty(coordinates)) {
                System.out.println("This cell is occupied! Choose another one!");
                continue;
            }

            setValueToCell(coordinates);
            displayTable();

        } while(true);
    }

    private static void setValueToCell(int[] coordinates) {
        String value = quantityX == quantityO ? "X" : "O";
        table.get(coordinates[0] - 1).set(coordinates[1] - 1, value);
    }

    private static boolean areCoordinatesWithinBounds(int[] coordinates) {
        int MIN_COORDINATE = 1;
        int MAX_COORDINATE = 3;

        int row = coordinates[0];
        int column = coordinates[1];

        return row >= MIN_COORDINATE && row <= MAX_COORDINATE &&
                column >= MIN_COORDINATE && column <= MAX_COORDINATE;
    }

    private static boolean isAValidInputLine(String inputLine) {
        return inputLine.matches("\\d+\\s\\d+");
    }

    private static boolean isCellEmpty(int[] coordinates) {
        String cellValue = table.get(coordinates[0] - 1).get(coordinates[1] - 1);
        return cellValue.equals(" ");
    }

    private static void initializeTable(String initialState) {
        int initialStateIndex = 0;
        for (int i = 0; i < 3; i++) {
            ArrayList<String> row = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                String symbol = String.valueOf(initialState.charAt(initialStateIndex));
                row.add(tablesSymbols.get(symbol));

                switch (symbol) {
                    case "X":
                        quantityX++;
                        break;
                    case "O":
                        quantityO++;
                        break;
                    default:
                        // Handle any other symbols if needed
                        break;
                }

                initialStateIndex++;
            }
            table.add(row);
        }
    }

    private static void displayTable() {
        System.out.println("---------");
        for (ArrayList<String> row : table) {
            System.out.print("| ");
            for (String element : row) {
                System.out.print(element + " ");
            }
            System.out.println("|");
        }

        System.out.println("---------");
    }
}
