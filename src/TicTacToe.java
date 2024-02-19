
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class TicTacToe {
    private static ArrayList<ArrayList<String>> table = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static int quantityX = 0;
    private static int quantityO = 0;
    private static ArrayList<String> availableSpaces = new ArrayList<>(Arrays.asList(
            "1 1", "1 2", "1 3",
            "2 1", "2 2", "2 3",
            "3 1", "3 2", "3 3"
    ));

    TicTacToe() {
        initializeTable();
    }

    public static void startGame() {
        new TicTacToe();

        String inputCommand;
        do {
            do {
                System.out.println("Input command:");
                inputCommand = scanner.nextLine();

                if (!validateInputCommand(inputCommand)) {
                    System.out.println("Bad Parameters!");
                    continue;
                }

                if (inputCommand.equals("exit")) {
                    return;
                }
                break;
            } while(true);

            displayTable();

            String[] typeOfPlayers = inputCommand.split("\\s");

            Player playerOne = getTypeOfPlayer(typeOfPlayers[1], "X");
            Player playerTwo = getTypeOfPlayer(typeOfPlayers[2], "O");

            ArrayList<Player> players = new ArrayList<>();

            players.add(playerOne);
            players.add(playerTwo);

            outerloop:
            do {
                for (Player player : players) {
                    int[] coordinates = player.getCoordinates(availableSpaces, table);

                    setValueToCell(coordinates, player.getSymbol());
                    displayTable();

                    if (checkForWin(player.getSymbol())) {
                        System.out.printf("%s wins\n", player.getSymbol());
                        break outerloop;
                    }

                    if (quantityX == 5) {
                        System.out.println("Draw");
                        break outerloop;
                    }
                }
            } while(true);
        } while(true);
    }

    private static Player getTypeOfPlayer(String type, String symbol) {
        return type.equals("user") ?
                new User(symbol) :
                new Computer(symbol, type);
    }

    private static boolean validateInputCommand(String inputCommand) {
        // Define regular expressions for valid commands
        String[] patterns = {
                "^start (easy|medium|user) (easy|medium|user)$",
                "^exit$"
        };

        // Check if the command matches any pattern
        for (String pattern : patterns) {
            if (Pattern.matches(pattern, inputCommand)) {
                return true;
            }
        }

        return false;
    }

    private static void setValueToCell(int[] coordinates, String symbol) {
        int row = coordinates[0] - 1;
        int col = coordinates[1] - 1;

        table.get(row).set(col, symbol);

        if (symbol.equals("X")) {
            quantityX++;
        } else {
            quantityO++;
        }
    }

    private static void initializeTable() {
        for (int i = 0; i < 3; i++) {
            ArrayList<String> row = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                row.add(" ");
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

    private static boolean checkForWin(String symbol) {
        // Check rows
        for (int row = 0; row < 3; row++) {
            String firstCell = table.get(row).get(0);

            if (!firstCell.equals(symbol)) {
                continue;
            }

            String secondCell = table.get(row).get(1);
            String thirdCell = table.get(row).get(2);

            if (firstCell.equals(secondCell) && firstCell.equals(thirdCell)) {
                return true;
            }
        }

        // Check columns
        for (int col = 0; col < 3; col++) {
            String firstCell = table.get(0).get(col);

            if (!firstCell.equals(symbol)) {
                continue;
            }

            String secondCell = table.get(1).get(col);
            String thirdCell = table.get(2).get(col);

            if (firstCell.equals(secondCell) && firstCell.equals(thirdCell)) {
                return true;
            }
        }

        String middleCell = table.get(1).get(1);

        if (!middleCell.equals(symbol)) {
            return false;
        }

        String firstCellLR = table.get(0).get(0);
        String thirdCellLR = table.get(2).get(2);

        String firstCellRL = table.get(0).get(2);
        String thirdCellRL = table.get(2).get(0);

        // Check diagonals
        if ((firstCellLR.equals(middleCell) && firstCellLR.equals(thirdCellLR)) ||
                (firstCellRL.equals(middleCell) && firstCellRL.equals(thirdCellRL))) {
            return true;
        }

        return false;
    }
}
