package numbers;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        MagicNumbers magicNumbers = new MagicNumbers();
        magicNumbers.buzzInit();
    }

}

class MagicNumbers {
    public static void buzzInit() {

        printGreetings();
        printInstructions();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter a request: ");
            String inputLine = scanner.nextLine();
            System.out.println();
            String[] inputLinesplitted = inputLine.split(" ");

            if (inputLine.equals("")) {
                printInstructions();
                continue;
            }
            if (inputLinesplitted.length == 1) {
                long oneDigitNumber;

                try {
                    oneDigitNumber = Long.parseLong(inputLinesplitted[0]);
                } catch (NumberFormatException ex) {
                    System.out.println("The first parameter should be a natural number or zero.");
                    System.out.println();
                    continue;
                }

                if (oneDigitNumber == 0) {
                    System.out.println("Goodbye!");
                    break;
                }
                if (!isNatural(oneDigitNumber)) {
                    continue;
                }
                singleInput(oneDigitNumber);

            } else if (inputLinesplitted.length == 2) {
                long firstNumber = Long.parseLong(inputLinesplitted[0]);
                long secondNumber = Long.parseLong(inputLinesplitted[1]);
                if (!areNatural(firstNumber, secondNumber)) {
                    continue;
                }
                doubleInput(firstNumber, secondNumber);
            } else if (inputLinesplitted.length >= 3) {
                long firstNumber = Long.parseLong(inputLinesplitted[0]);
                long secondNumber = Long.parseLong(inputLinesplitted[1]);

                List<String> inputPropertiesList = new ArrayList<>();
                for (int i = 2; i < inputLinesplitted.length; i++) {
                    inputPropertiesList.add(inputLinesplitted[i].toLowerCase());
                }
                //TODO: refactor method to accept: '-' symbol
                if (!checkConditions(firstNumber, secondNumber, inputPropertiesList)) {
                    continue;
                }
                multipleInputs(firstNumber, secondNumber, inputPropertiesList);
            }

        }
    }

    public static boolean checkConditions(long firstNumber, long secondNumber, List<String> properties) {
        if (!areNatural(firstNumber, secondNumber)) {
            return false;
            //TODO: refactor method to accept: '-' symbol
        } else if (!checkIfPropertiesExist(properties)) {
            System.out.println();
            return false;
        } else if (checkIfPropertiesMutuallyExclusive(properties)) {
            System.out.println();
            return false;
        }
        return true;
    }

    public static void multipleInputs(long firstNumber, long secondNumber, List<String> properties) {
        int whileCounter = 0;
        int normalPropertyCounter = 0;
        int oppositePropertyCounter = 0;
        List<String> oppositeProperties = new ArrayList<>();
        List<String> normalProperties = new ArrayList<>();

        for (String property : properties) {
            if (property.contains("-")) {
                oppositeProperties.add(property.substring(1));
            } else {
                normalProperties.add(property);
            }
        }


        while (whileCounter < secondNumber) {
            for (int i = 0; i < normalProperties.size(); i++) {
                if (getProperties(firstNumber).contains(normalProperties.get(i))) {
                    normalPropertyCounter++;
                }
            }
            for (int i = 0; i < oppositeProperties.size(); i++) {
                if (!getProperties(firstNumber).contains(oppositeProperties.get(i))) {
                    oppositePropertyCounter++;
                }
            }
            if (normalPropertyCounter == normalProperties.size() &&
                    oppositePropertyCounter == oppositeProperties.size()) {
                System.out.println(getProperties(firstNumber));
                whileCounter++;
            }
            normalPropertyCounter = 0;
            oppositePropertyCounter = 0;
            firstNumber++;
        }
        System.out.println();
    }

    public static void doubleInput(long firstNumber, long secondNumber) {
        for (int i = 0; i < secondNumber; i++) {
            System.out.println(getProperties(firstNumber));
            firstNumber++;
        }
        System.out.println();
    }

    public static void singleInput(long number) {
        System.out.println("Properties of " + number);
        System.out.println("buzz: " + isBuzz(number));
        System.out.println("duck: " + isDuck(number));
        System.out.println("palindromic: " + isPalindromic(number));
        System.out.println("gapful: " + isGapful(number));
        System.out.println("spy: " + isSpy(number));
        System.out.println("square: " + isSquare(number));
        System.out.println("sunny: " + isSunny(number));
        System.out.println("jumping: " + isJumping(number));
        System.out.println("happy: " + isHappy(number));
        System.out.println("sad: " + !isHappy(number));
        System.out.println("even: " + isEven(number));
        System.out.println("odd: " + !isEven(number));
        System.out.println();
    }


    public static String getProperties(long firstNumber) {

        String properties = firstNumber + " is";

        if (isBuzz(firstNumber)) {
            properties += " buzz,";
        }
        if (isDuck(firstNumber)) {
            properties += " duck,";
        }
        if (isPalindromic(firstNumber)) {
            properties += " palindromic,";
        }
        if (isSpy(firstNumber)) {
            properties += " spy,";
        }
        if (isGapful(firstNumber)) {
            properties += " gapful,";
        }
        if (isSquare(firstNumber)) {
            properties += " square,";
        }
        if (isSunny(firstNumber)) {
            properties += " sunny,";
        }
        if (isJumping(firstNumber)) {
            properties += " jumping,";
        }
        if (isHappy(firstNumber)) {
            properties += " happy,";
        } else {
            properties += " sad,";
        }
        if (isEven(firstNumber)) {
            properties += " even";
        } else {
            properties += " odd";
        }
        return properties;
    }

    public static boolean isHappy(long number) {
        {
            Set<Long> st = new HashSet<>();
            while (true) {
                number = numSquareSum(number);
                if (number == 1)
                    return true;
                if (st.contains(number))
                    return false;
                st.add(number);
            }
        }
    }

    public static long numSquareSum(long number) {
        long squareSum = 0;
        while (number != 0) {
            squareSum += (number % 10) * (number % 10);
            number /= 10;
        }
        return squareSum;
    }

    public static boolean isJumping(long number) {
        boolean isNumberJumping = true;
        while (number != 0) {
            long firstDigit = number % 10;
            number = number / 10;
            if (number != 0) {
                long secondDigit = number % 10;
                if (Math.abs(firstDigit - secondDigit) != 1) {
                    isNumberJumping = false;
                    break;
                }
            }
        }
        return isNumberJumping;
    }

    public static boolean isSquare(long number) {
        return Math.sqrt(number) % 1 == 0;
    }

    public static boolean isSunny(long number) {
        return isSquare(number + 1);
    }

    public static boolean isSpy(long number) {
        String numberAsString = Long.toString(number);
        long product = 1;
        long sum = 0;
        for (int i = 0; i < numberAsString.length(); i++) {
            sum += Character.getNumericValue(numberAsString.charAt(i));
            product = product * Character.getNumericValue(numberAsString.charAt(i));
        }
        return sum == product;
    }

    public static boolean isGapful(long number) {
        String numberAsString = Long.toString(number);
        String divider = "";
        divider += numberAsString.charAt(0);
        divider += numberAsString.charAt(numberAsString.length() - 1);
        int dividerAsNumber = Integer.parseInt(divider);

        if (numberAsString.length() >= 3 && (number % dividerAsNumber == 0)) {
            return true;
        }
        return false;
    }

    public static boolean isPalindromic(long number) {
        String numberAsString = Long.toString(number);
        int counter = 0;
        for (int i = 0; i < numberAsString.length(); i++) {
            if (numberAsString.charAt(i) == numberAsString.charAt(numberAsString.length() - i - 1))
                counter++;
        }
        return counter == numberAsString.length();
    }

    public static boolean isDuck(long number) {
        String numberAsString = Long.toString(number);
        if (numberAsString.contains("0") && numberAsString.charAt(0) != '0') {
            return true;
        }
        return false;
    }

    public static boolean isBuzz(long number) {
        return isDivisibleBySeven(number) || endsWithSeven(number);
    }

    public static boolean isDivisibleBySeven(long number) {
        return number % 7 == 0;
    }

    public static boolean endsWithSeven(long number) {
        return number % 10 == 7;
    }

    public static boolean isNatural(long number) {
        if (number < 1) {
            System.out.println("The first parameter should be a natural number or zero.");
            System.out.println();
            return false;
        }
        return true;
    }

    public static boolean areNatural(long firstNumber, long secondNumber) {
        if (firstNumber < 1) {
            System.out.println("The first parameter should be a natural number or zero.");
            System.out.println();
            return false;
        } else if (secondNumber < 1) {
            System.out.println("The second parameter should be a natural number or zero.");
            System.out.println();
            return false;
        }
        return true;
    }


    public static boolean isEven(long number) {
        return number % 2 == 0;
    }

    public static boolean checkIfPropertiesExist(List<String> inputProperties) {

        List<String> listOfProperties = List.of
                ("BUZZ", "DUCK", "PALINDROMIC", "GAPFUL", "SPY", "SQUARE", "SUNNY", "JUMPING", "HAPPY", "SAD", "EVEN", "ODD");

        StringBuilder incorrectProperties = new StringBuilder("");
        int correctPropertiesCounter = 0;

        for (int i = 0; i < inputProperties.size(); i++) {
            if (inputProperties.get(i).charAt(0) != '-' && listOfProperties.contains(inputProperties.get(i).toUpperCase())) {
                correctPropertiesCounter++;
            } else if (inputProperties.get(i).charAt(0) == '-' && listOfProperties.contains(inputProperties.get(i).substring(1).toUpperCase())) {
                correctPropertiesCounter++;
            } else if (!listOfProperties.contains(inputProperties.get(i).toUpperCase())) {
                incorrectProperties.append(inputProperties.get(i).toUpperCase() + ",");
            }
        }

        if (correctPropertiesCounter == inputProperties.size()) {
            return true;
        } else if (inputProperties.size() - correctPropertiesCounter == 1) {
            incorrectProperties.deleteCharAt(incorrectProperties.length() - 1);
            System.out.println("The property [" + incorrectProperties + "] is wrong.");
            printAvailableProperties(listOfProperties);
            return false;
        } else if (inputProperties.size() > 1) {
            incorrectProperties.deleteCharAt(incorrectProperties.length() - 1);
            System.out.println("The properties [" + incorrectProperties + "] are wrong");
            printAvailableProperties(listOfProperties);
            return false;
        }
        return true;
    }

    public static boolean checkIfPropertiesMutuallyExclusive(List<String> inputProperties) {
        if (inputProperties.size() > 1) {
            String message = "There are no numbers with these properties.";
            if (inputProperties.contains("sunny") && inputProperties.contains("square")) {
                System.out.println("The request contains mutually exclusive properties: [SUNNY, SQUARE]");
                System.out.println("There are no numbers with these properties.");
                return true;
            } else if (inputProperties.contains("-sunny") && inputProperties.contains("sunny")) {
                System.out.println("The request contains mutually exclusive properties: [-SUNNY, SUNNY]");
                System.out.println("There are no numbers with these properties.");
                return true;
            } else if (inputProperties.contains("-square") && inputProperties.contains("square")) {
                System.out.println("The request contains mutually exclusive properties: [-SQUARE, SQUARE]");
                System.out.println("There are no numbers with these properties.");
                return true;
            } else if (inputProperties.contains("even") && inputProperties.contains("odd")) {
                System.out.println("The request contains mutually exclusive properties: [EVEN, ODD]");
                System.out.println("There are no numbers with these properties.");
                return true;
            } else if (inputProperties.contains("-even") && inputProperties.contains("-odd")) {
                System.out.println("The request contains mutually exclusive properties: [-EVEN, -ODD]");
                System.out.println("There are no numbers with these properties.");
                return true;
            } else if (inputProperties.contains("-even") && inputProperties.contains("even")) {
                System.out.println("The request contains mutually exclusive properties: [-EVEN, EVEN]");
                System.out.println("There are no numbers with these properties.");
                return true;
            } else if (inputProperties.contains("-odd") && inputProperties.contains("odd")) {
                System.out.println("The request contains mutually exclusive properties: [-ODD, ODD]");
                System.out.println("There are no numbers with these properties.");
                return true;
            } else if (inputProperties.contains("duck") && inputProperties.contains("spy")) {
                System.out.println("The request contains mutually exclusive properties: [DUCK, SPY]");
                System.out.println("There are no numbers with these properties.");
                return true;
            } else if (inputProperties.contains("-duck") && inputProperties.contains("duck")) {
                System.out.println("The request contains mutually exclusive properties: [-DUCK, DUCK]");
                System.out.println("There are no numbers with these properties.");
                return true;
            } else if (inputProperties.contains("-spy") && inputProperties.contains("spy")) {
                System.out.println("The request contains mutually exclusive properties: [-SPY, SPY]");
                System.out.println("There are no numbers with these properties.");
                return true;
            } else if (inputProperties.contains("-duck") && inputProperties.contains("-spy")) {
                System.out.println("The request contains mutually exclusive properties: [-DUCK, -SPY]");
                System.out.println("There are no numbers with these properties.");
                return true;
            } else if (inputProperties.contains("happy") && inputProperties.contains("sad")) {
                System.out.println("The request contains mutually exclusive properties: [HAPPY, SAD]");
                System.out.println("There are no numbers with these properties.");
                return true;
            } else if (inputProperties.contains("-happy") && inputProperties.contains("happy")) {
                System.out.println("The request contains mutually exclusive properties: [-HAPPY, HAPPY]");
                System.out.println("There are no numbers with these properties.");
                return true;
            } else if (inputProperties.contains("-sad") && inputProperties.contains("sad")) {
                System.out.println("The request contains mutually exclusive properties: [-SAD, SAD]");
                System.out.println("There are no numbers with these properties.");
                return true;
            } else if (inputProperties.contains("-happy") && inputProperties.contains("-sad")) {
                System.out.println("The request contains mutually exclusive properties: [-HAPPY, -SAD]");
                System.out.println("There are no numbers with these properties.");
                return true;
            }
        }
        return false;
    }

    public static void printAvailableProperties(List<String> listOfProperties) {
        System.out.println("Available properties: " + listOfProperties);
    }

    public static void printGreetings() {
        System.out.println("Welcome to Amazing Numbers!");
        System.out.println();
    }

    public static void printInstructions() {
        System.out.println("Supported requests:");
        System.out.println("- enter a natural number to know its properties;");
        System.out.println("- enter two natural numbers to obtain the properties of the list:");
        System.out.println("* the first parameter represents a starting number;");
        System.out.println("* the second parameter shows how many consecutive numbers are to be processed;");
        System.out.println("- two natural numbers and properties to search for;");
        System.out.println("- a property preceded by minus must not be present in numbers;");
        System.out.println("- separate the parameters with one space;");
        System.out.println("- enter 0 to exit.");
        System.out.println();
    }
}
