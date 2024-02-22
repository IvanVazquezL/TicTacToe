
import java.util.*;
import java.util.function.Function;

public class Computer extends User{
    private String difficulty;
    Computer(String symbol, String difficulty) {
        super(symbol);
        this.difficulty = difficulty;
    }

    @Override
    public int[] getCoordinates(ArrayList<String> availableSpaces, ArrayList<ArrayList<String>> table) {
        System.out.printf("Making move level \"%s\"\n", difficulty);
        String inputLine;

        //System.out.println(availableSpaces);

        switch (difficulty) {
            case "easy":
                inputLine = getRandomAvailableSpace(availableSpaces);
                break;
            case "medium":
                inputLine = getMediumAvailableSpace(availableSpaces, table);
                break;
            case "hard":
                inputLine = getHardAvailableSpace(table);
                break;
            default:
                inputLine = "";
                break;
        }

        //System.out.printf("inputLine: %s\n", inputLine);

        availableSpaces.remove(inputLine);

        int[] coordinates = Arrays.stream(inputLine.split("\\s"))
                .mapToInt(Integer::parseInt)
                .toArray();
        return coordinates;
    }

    private String getRandomAvailableSpace(ArrayList<String> availableSpaces) {
        Random random = new Random();
        // Generate a random index within the range of availableSpaces
        int randomIndex = random.nextInt(availableSpaces.size());
        return availableSpaces.get(randomIndex);
    }

    private String getMediumAvailableSpace(ArrayList<String> availableSpaces, ArrayList<ArrayList<String>> table) {
        String availableSpaceForTwoInARow = getAvailableSpaceForTwoInARow(table, this::findAvailableSpaceInLineToWin);

        if (availableSpaceForTwoInARow != null) {
            return availableSpaceForTwoInARow;
        }

        String availableSpaceToBlockOpponentWin = getAvailableSpaceToBlockOpponentWin(table, this::findAvailableSpaceInLineToBlockOpponentsWin);

        if (availableSpaceToBlockOpponentWin != null) {
            return availableSpaceToBlockOpponentWin;
        }

        return getRandomAvailableSpace(availableSpaces);
    }

    private String getAvailableSpaceByFunction(ArrayList<ArrayList<String>> table, Function<HashMap<String, String>, String> lineChecker) {
        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            HashMap<String, String> rowMap = new HashMap<>();
            HashMap<String, String> columnMap = new HashMap<>();

            for (int j = 0; j < 3; j++) {
                String cellCoordinateForRow = (i + 1) + " " + (j + 1);
                String cellValueForRow = table.get(i).get(j);
                rowMap.put(cellCoordinateForRow, cellValueForRow);

                String cellCoordinateForColumn = (j + 1) + " " + (i + 1);
                String cellValueForColumn = table.get(j).get(i);
                columnMap.put(cellCoordinateForColumn, cellValueForColumn);
            }
            String availableSpace = lineChecker.apply(rowMap);
            if (availableSpace != null) {
                return availableSpace;
            }
            availableSpace = lineChecker.apply(columnMap);
            if (availableSpace != null) {
                return availableSpace;
            }
        }

        // Check diagonals
        HashMap<String, String> diagonalLRMap = new HashMap<>();
        HashMap<String, String> diagonalRLMap = new HashMap<>();

        for (int i = 0; i < 3; i++) {
            String cellCoordinateForDiagonalLR = (i + 1) + " " + (i + 1);
            String cellValueForDiagonalLR = table.get(i).get(i);
            diagonalLRMap.put(cellCoordinateForDiagonalLR, cellValueForDiagonalLR);

            String cellCoordinateForDiagonalRL = (i + 1) + " " + (2 - i + 1);
            String cellValueForDiagonalRL = table.get(i).get(2 - i);
            diagonalRLMap.put(cellCoordinateForDiagonalRL, cellValueForDiagonalRL);
        }

        String availableSpace = lineChecker.apply(diagonalLRMap);
        if (availableSpace != null) {
            return availableSpace;
        }

        availableSpace = lineChecker.apply(diagonalRLMap);
        if (availableSpace != null) {
            return availableSpace;
        }

        return null;
    }

    private String getAvailableSpaceForTwoInARow(ArrayList<ArrayList<String>> table, Function<HashMap<String, String>, String> lineChecker) {
        return getAvailableSpaceByFunction(table, lineChecker);
    }


    private String findAvailableSpaceInLineToWin(HashMap<String, String> cellMap) {
        int playersSymbolCounter = 0;
        boolean availableSpaceExistsInLine = false;
        String availableSpace = "";

        for (Map.Entry<String, String> cell : cellMap.entrySet()) {
            String cellValue = cell.getValue();
            String cellCoordinate = cell.getKey();

            //System.out.println("(" + cellCoordinate + ") "+ cellValue);

            if (cellValue.equals(this.getSymbol())) {
                playersSymbolCounter++;
            } else if (cellValue.equals(" ")) {
                availableSpace = cellCoordinate;
                availableSpaceExistsInLine = true;
            }
        }

        if (playersSymbolCounter == 2 && availableSpaceExistsInLine) {
            return availableSpace;
        }

        return null; // No available space found
    }

    private String getAvailableSpaceToBlockOpponentWin(ArrayList<ArrayList<String>> table, Function<HashMap<String, String>, String> lineChecker) {
        return getAvailableSpaceByFunction(table, lineChecker);
    }

    private String findAvailableSpaceInLineToBlockOpponentsWin(HashMap<String, String> cellMap) {
        int opponentsSymbolCounter = 0;
        boolean availableSpaceExistsInLine = false;
        String availableSpace = "";

        for (Map.Entry<String, String> cell : cellMap.entrySet()) {
            String cellValue = cell.getValue();
            String cellCoordinate = cell.getKey();

            //System.out.println("(" + cellCoordinate + ") "+ cellValue);
            if (cellValue.equals(this.getOpponentsSymbol())) {
                opponentsSymbolCounter++;
            } else if (cellValue.equals(" ")) {
                availableSpace = cellCoordinate;
                availableSpaceExistsInLine = true;
            }
        }

        //System.out.println(opponentsSymbolCounter);

        if (opponentsSymbolCounter == 2 && availableSpaceExistsInLine) {
            //System.out.println("op: " +availableSpace);
            return availableSpace;
        }

        return null; // No available space found
    }

    private String getHardAvailableSpace(ArrayList<ArrayList<String>> table) {
        Move move = Minimax.getMove(table, this.getSymbol(), this.getOpponentsSymbol());
        ArrayList<String> availableSpots = new ArrayList<>(Arrays.asList(
                "1 1", "1 2", "1 3",
                "2 1", "2 2", "2 3",
                "3 1", "3 2", "3 3"
        ));
        return availableSpots.get(move.getIndex());
    }
}
