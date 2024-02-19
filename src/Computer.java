
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

        switch (difficulty) {
            case "easy":
                inputLine = getRandomAvailableSpace(availableSpaces);
                break;
            case "medium":
                inputLine = getMediumAvailableSpace(availableSpaces, table);
                break;
            default:
                inputLine = "";
                break;
        }

        System.out.printf("inputLine: %s\n", inputLine);

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

    private String getAvailableSpaceByFunction(ArrayList<ArrayList<String>> table, Function<ArrayList<HashMap<String, String>>, String> lineChecker) {
        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            ArrayList<HashMap<String, String>> row = new ArrayList<>();
            HashMap<String, String> rowMap = new HashMap<>();

            ArrayList<HashMap<String, String>> column = new ArrayList<>();
            HashMap<String, String> columnMap = new HashMap<>();

            for (int j = 0; j < 3; j++) {
                String cellCoordinateForRow = i + " " + j;
                String cellValueForRow = table.get(i).get(j);
                rowMap.put(cellCoordinateForRow, cellValueForRow);
                row.add(rowMap);

                String cellCoordinateForColumn = j + " " + i;
                String cellValueForColumn = table.get(j).get(i);
                columnMap.put(cellCoordinateForColumn, cellValueForColumn);
                column.add(columnMap);
            }
            String availableSpace = lineChecker.apply(row);
            if (availableSpace != null) {
                return availableSpace;
            }
            availableSpace = lineChecker.apply(column);
            if (availableSpace != null) {
                return availableSpace;
            }
        }

        // Check diagonals
        ArrayList<HashMap<String, String>> diagonalLR = new ArrayList<>();
        HashMap<String, String> diagonalLRMap = new HashMap<>();

        ArrayList<HashMap<String, String>> diagonalRL = new ArrayList<>();
        HashMap<String, String> diagonalRLMap = new HashMap<>();

        for (int i = 0; i < 3; i++) {
            String cellCoordinateForDiagonalLR = i + " " + i;
            String cellValueForDiagonalLR = table.get(i).get(i);
            diagonalLRMap.put(cellCoordinateForDiagonalLR, cellValueForDiagonalLR);
            diagonalLR.add(diagonalLRMap);

            String cellCoordinateForDiagonalRL = i + " " + (2 - i);
            String cellValueForDiagonalRL = table.get(i).get(2 - i);
            diagonalRLMap.put(cellCoordinateForDiagonalRL, cellValueForDiagonalRL);
            diagonalRL.add(diagonalRLMap);
        }

        String availableSpace = lineChecker.apply(diagonalLR);
        if (availableSpace != null) {
            return availableSpace;
        }

        availableSpace = lineChecker.apply(diagonalRL);
        if (availableSpace != null) {
            return availableSpace;
        }

        return null;
    }

    private String getAvailableSpaceForTwoInARow(ArrayList<ArrayList<String>> table, Function<ArrayList<HashMap<String, String>>, String> lineChecker) {
        return getAvailableSpaceByFunction(table, lineChecker);
    }


    private String findAvailableSpaceInLineToWin(ArrayList<HashMap<String, String>> cells) {
        int playersSymbolCounter = 0;
        String availableSpace = "";

        for(HashMap<String, String> cellMap : cells) {
            for (Map.Entry<String, String> cell : cellMap.entrySet()) {
                String cellValue = cell.getValue();
                String cellCoordinate = cell.getKey();

                if (cellValue.equals(this.getSymbol())) {
                    playersSymbolCounter++;
                } else if (cell.equals(" ")) {
                    availableSpace = cellCoordinate;
                }
            }
        }

        if (playersSymbolCounter == 2) {
            return availableSpace;
        }

        return null; // No available space found
    }

    private String getAvailableSpaceToBlockOpponentWin(ArrayList<ArrayList<String>> table, Function<    ArrayList<HashMap<String, String>>, String> lineChecker) {
        return getAvailableSpaceByFunction(table, lineChecker);
    }

    private String findAvailableSpaceInLineToBlockOpponentsWin(ArrayList<HashMap<String, String>> cells) {
        int opponentsSymbolCounter = 0;
        String availableSpace = "";

        System.out.println("cell size " + cells.size());

        for(HashMap<String, String> cellMap : cells) {
            System.out.println("cellMap size " + cellMap.size());
            for (Map.Entry<String, String> cell : cellMap.entrySet()) {
                String cellValue = cell.getValue();
                String cellCoordinate = cell.getKey();

                System.out.println("(" + cellCoordinate + ") "+ cellValue);
                if (cellValue.equals(this.getOpponentsSymbol())) {
                    opponentsSymbolCounter++;
                } else if (cell.equals(" ")) {
                    availableSpace = cellCoordinate;
                }
            }
        }

        System.out.println(opponentsSymbolCounter);

        if (opponentsSymbolCounter == 2) {
            return availableSpace;
        }

        return null; // No available space found
    }
}
