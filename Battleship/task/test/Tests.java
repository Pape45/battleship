import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;


public class Tests extends StageTest<String> {
    @DynamicTestingMethod
    CheckResult test1() {

        TestedProgram main = new TestedProgram();
        String output = main.start().trim();
        String[][] matrix;

        if (!output.toLowerCase().contains("aircraft carrier")) {
            return CheckResult.wrong("After starting the program, you should request " +
                "the coordinates of the Aircraft Carrier in that way:\n" +
                "\"Enter the coordinates of the Aircraft Carrier (5 cells):\"");
        }

        output = main.execute("F3 F7").trim();
        matrix = getFieldMatrix(output);
        findShipByCoordinates(matrix, "F3 F7");

        if (!output.toLowerCase().contains("battleship")) {
            return CheckResult.wrong("After asking for the Aircraft Carrier " +
                "coordinates, you should request " +
                "the coordinates of the Battleship in that way:\n" +
                "\"Enter the coordinates of the Battleship (4 cells):\"");
        }

        output = main.execute("A1 D1").trim();
        matrix = getFieldMatrix(output);
        findShipByCoordinates(matrix, "A1 D1");

        if (!output.toLowerCase().contains("submarine")) {
            return CheckResult.wrong("After asking for the Battleship coordinates, you should request " +
                "the coordinates of the Submarine in that way:\n" +
                "\"Enter the coordinates of the Submarine (3 cells):\"");
        }

        output = main.execute("J7 J10").trim();
        if (isGameFieldPrinted(output)) {
            return CheckResult.wrong("Your program should not print a game field if there is an input mistake.");
        }

        output = main.execute("J10 J8").trim();
        matrix = getFieldMatrix(output);
        findShipByCoordinates(matrix, "J10 J8");

        if (!output.toLowerCase().contains("cruiser")) {
            return CheckResult.wrong("After asking for the Submarine coordinates, you should request " +
                "the coordinates of the Cruiser in that way:\n" +
                "\"Enter the coordinates of the Cruiser (3 cells):\"");
        }

        output = main.execute("B9 D8").trim();
        if (isGameFieldPrinted(output)) {
            return CheckResult.wrong("Your program should not print a game field if there is an input mistake.");
        }

        output = main.execute("B9 D9").trim();
        matrix = getFieldMatrix(output);
        findShipByCoordinates(matrix, "B9 D9");

        if (!output.toLowerCase().contains("destroyer")) {
            return CheckResult.wrong("After asking for the Cruiser coordinates, " +
                "you should request " +
                "the coordinates of the Destroyer in that way:\n" +
                "\"Enter the coordinates of the Destroyer (2 cells):\"");
        }

        output = main.execute("E6 D6").trim();
        if (isGameFieldPrinted(output)) {
            return CheckResult.wrong(
                "Your program should not print a game field " +
                    "if there is an input mistake. " +
                "(Too close to another ship)");
        }

        if (!output.toLowerCase().contains("error")) {
            return CheckResult.wrong(
                "Your program should report an error " +
                    "if there is an input mistake. " +
                    "(Too close to another ship)");
        }

        output = main.execute("I2 J2").trim();
        matrix = getFieldMatrix(output);
        findShipByCoordinates(matrix, "I2 J2");

        return CheckResult.correct();
    }

    @DynamicTestingMethod
    CheckResult test2() {

        TestedProgram main = new TestedProgram();
        String output = main.start().trim();
        String[][] matrix;

        if (!output.toLowerCase().contains("aircraft carrier")) {
            return CheckResult.wrong("After starting the program, you should request " +
                "the coordinates of the Aircraft Carrier in that way:\n" +
                "\"Enter the coordinates of the Aircraft Carrier (5 cells):\"");
        }

        output = main.execute("J3 J7").trim();
        matrix = getFieldMatrix(output);
        findShipByCoordinates(matrix, "J3 J7");

        if (!output.toLowerCase().contains("battleship")) {
            return CheckResult.wrong("After asking for the Aircraft Carrier coordinates, you should request " +
                "the coordinates of the Battleship in that way:\n" +
                "\"Enter the coordinates of the Battleship (4 cells):\"");
        }

        output = main.execute("C8 B8").trim();
        if (isGameFieldPrinted(output)) {
            return CheckResult.wrong("Your program should not print a game field if there is an input mistake. " +
                "(Incorrect length of the ship)");
        }

        output = main.execute("C8 F8").trim();
        matrix = getFieldMatrix(output);
        findShipByCoordinates(matrix, "C8 F8");

        if (!output.toLowerCase().contains("submarine")) {
            return CheckResult.wrong("After asking for the Battleship coordinates, you should request " +
                "the coordinates of the Submarine in that way:\n" +
                "\"Enter the coordinates of the Submarine (3 cells):\"");
        }

        output = main.execute("A1 C2").trim();
        if (isGameFieldPrinted(output)) {
            return CheckResult.wrong("Your program should not print a game field if there is an input mistake.");
        }

        output = main.execute("A1 C1").trim();
        matrix = getFieldMatrix(output);
        findShipByCoordinates(matrix, "A1 C1");

        if (!output.toLowerCase().contains("cruiser")) {
            return CheckResult.wrong("After asking for the Submarine coordinates, you should request " +
                "the coordinates of the Cruiser in that way:\n" +
                "\"Enter the coordinates of the Cruiser (3 cells):\"");
        }

        output = main.execute("H1 H3").trim();
        matrix = getFieldMatrix(output);
        findShipByCoordinates(matrix, "H1 H3");

        if (!output.toLowerCase().contains("destroyer")) {
            return CheckResult.wrong("After asking for the Cruiser coordinates, you should request " +
                "the coordinates of the Destroyer in that way:\n" +
                "\"Enter the coordinates of the Destroyer (2 cells):\"");
        }

        output = main.execute("G2 E2").trim();
        if (isGameFieldPrinted(output)) {
            return CheckResult.wrong("Your program should not print a game field if there is an input mistake. " +
                "(Too close to another ship)");
        }

        output = main.execute("B5 C5").trim();
        matrix = getFieldMatrix(output);
        findShipByCoordinates(matrix, "B5 C5");

        return CheckResult.correct();
    }

    void findShipByCoordinates(String[][] matrix, String coordinates) {
        int[] coordinatesInt = parseCoordinates(coordinates);

        if (coordinatesInt[0] > coordinatesInt[2]) {
            int swap = coordinatesInt[0];
            coordinatesInt[0] = coordinatesInt[2];
            coordinatesInt[2] = swap;
        } else if (coordinatesInt[1] > coordinatesInt[3]) {
            int swap = coordinatesInt[1];
            coordinatesInt[1] = coordinatesInt[3];
            coordinatesInt[3] = swap;
        }

        if (coordinatesInt[0] == coordinatesInt[2]) {
            int cord = coordinatesInt[0];
            for (int i = coordinatesInt[1]; i <= coordinatesInt[3]; i++) {
                if (!matrix[cord][i].toLowerCase().equals("x") && !matrix[cord][i].toLowerCase().equals("o")) {
                    throw new WrongAnswer("The ship's cells were not found at the coordinates \"" + coordinates + "\"");
                }
            }
        } else {
            int cord = coordinatesInt[1];
            for (int i = coordinatesInt[0]; i <= coordinatesInt[2]; i++) {
                if (!matrix[i][cord].toLowerCase().equals("x") && !matrix[i][cord].toLowerCase().equals("o")) {
                    throw new WrongAnswer("The ship's cells were not found at the \"" + coordinates + "\"");
                }
            }
        }
    }

    int[] parseCoordinates(String coordinatesString) {
        String[] splittedCoords = coordinatesString.split(" ");
        int[] parsedCoordinates = new int[4];

        parsedCoordinates[0] = charToInt(splittedCoords[0].substring(0, 1));
        parsedCoordinates[1] = Integer.parseInt(splittedCoords[0].substring(1)) - 1;
        parsedCoordinates[2] = charToInt(splittedCoords[1].substring(0, 1));
        parsedCoordinates[3] = Integer.parseInt(splittedCoords[1].substring(1)) - 1;

        return parsedCoordinates;
    }

    int charToInt(String charCoordinate) {
        charCoordinate = charCoordinate.toLowerCase();
        char character = charCoordinate.charAt(0);
        return (int) character - (int) 'a';
    }

    String[][] getFieldMatrix(String output) {

        WrongAnswer cantParseException = new WrongAnswer(
            "Can't parse the game field\n" +
            "Make sure you print it like in examples!");

        String[] splittedOutput = output.split("\n");
        String[][] matrix = new String[10][10];

        try {
            int index = 0;
            while (!(splittedOutput[index].contains("1") &&
                splittedOutput[index].contains("2") &&
                splittedOutput[index].contains("10"))) {
                index++;
                if (index > 1000) {
                    throw cantParseException;
                }
            }
            index++;

            for (int i = 0; i < 10; i++) {
                String temp = splittedOutput[index].substring(2).trim();
                if (temp.length() == 0) {
                    throw cantParseException;
                }
                String[] splittedLine = temp.trim().split(" ");
                if (splittedLine.length != 10) {
                    throw cantParseException;
                }
                matrix[i] = splittedLine;
                index++;
            }
        } catch (IndexOutOfBoundsException ignored) {
            throw cantParseException;
        }

        return matrix;
    }

    boolean isGameFieldPrinted(String output) {
        return output.contains("1") && output.contains("2") && output.contains("10");
    }

}
