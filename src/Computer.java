
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Computer extends User{
    private String difficulty;
    Computer(String symbol, String difficulty) {
        super(symbol);
        this.difficulty = difficulty;
    }

    @Override
    public int[] getCoordinates(ArrayList<String> availableSpaces, ArrayList<ArrayList<String>> table) {
        System.out.printf("Making move level \"%s\"", difficulty);
        String inputLine;

        switch (difficulty) {
            case "easy":
                inputLine = getRandomAvailableSpace(availableSpaces);
                break;
            case "medium":
                inputLine = getMediumAvailableSpace(availableSpaces, table);
            default:
                inputLine = "";
                break;
        }

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
        String availableSpaceForTwoInARow = getAvailableSpaceForTwoInARow(table);

        if (availableSpaceForTwoInARow != null) {
            return availableSpaceForTwoInARow;
        }

        String availableSpaceToBlockOpponentWin = getAvailableSpaceToBlockOpponentWin(table);

        if (availableSpaceToBlockOpponentWin != null) {
            return availableSpaceToBlockOpponentWin;
        }

        return getRandomAvailableSpace(availableSpaces);
    }

    private String getAvailableSpaceForTwoInARow(ArrayList<ArrayList<String>> table) {
        // Check rows
        for (int row = 0; row < 3; row++) {
            ArrayList<String> cells = new ArrayList<>();

            String firstCell = table.get(row).get(0);
            String secondCell = table.get(row).get(1);
            String thirdCell = table.get(row).get(2);

            cells.add(firstCell);
            cells.add(secondCell);
            cells.add(thirdCell);

            String availableSpace = findAvailableSpaceInLine(cells);

            if (availableSpace != null) {
                return availableSpace;
            }
        }

        // Check columns
        for (int col = 0; col < 3; col++) {
            ArrayList<String> cells = new ArrayList<>();

            String firstCell = table.get(0).get(col);
            String secondCell = table.get(1).get(col);
            String thirdCell = table.get(2).get(col);

            cells.add(firstCell);
            cells.add(secondCell);
            cells.add(thirdCell);

            String availableSpace = findAvailableSpaceInLine(cells);

            if (availableSpace != null) {
                return availableSpace;
            }
        }

        // Check diagonals
        String middleCell = table.get(1).get(1);

        String firstCellLR = table.get(0).get(0);
        String thirdCellLR = table.get(2).get(2);

        ArrayList<String> diagonalLR = new ArrayList<>();
        diagonalLR.add(firstCellLR);
        diagonalLR.add(middleCell);
        diagonalLR.add(thirdCellLR);

        String availableSpaceDiagonalLR = findAvailableSpaceInLine(diagonalLR);

        if (availableSpaceDiagonalLR != null) {
            return availableSpaceDiagonalLR;
        }

        String firstCellRL = table.get(0).get(2);
        String thirdCellRL = table.get(2).get(0);

        ArrayList<String> diagonalRL = new ArrayList<>();
        diagonalRL.add(firstCellRL);
        diagonalRL.add(middleCell);
        diagonalRL.add(thirdCellRL);

        String availableSpaceDiagonalRL = findAvailableSpaceInLine(diagonalRL);

        if (availableSpaceDiagonalRL != null) {
            return availableSpaceDiagonalRL;
        }

        return null;
    }

    private String findAvailableSpaceInLine(ArrayList<String> line) {
        int playersSymbolCounter = 0;
        String availableSpace = "";

        for(String cell : line) {
            if (cell.equals(this.getSymbol())) {
                playersSymbolCounter++;
            } else if (cell.equals(" ")) {
                availableSpace = cell;
            }
        }

        if (playersSymbolCounter == 2) {
            return availableSpace;
        }

        return null; // No available space found
    }

    private String getAvailableSpaceToBlockOpponentWin(ArrayList<ArrayList<String>> table) {
        return null;
    }
}
