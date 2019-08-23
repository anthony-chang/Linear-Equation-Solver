/*
Anthony Chang
ICS4U1
Mr. Jay
Line Class Assignment
 */

import java.util.*;

class Line {
    public int A, B, C;

    public Line() { //constructor with no arguments
        A = 0;
        B = 0;
        C = 0;
    }

    public Line (int a, int b, int c) { //constructor with A, B, C parameters
        A = a;
        B = b;
        C = c;
    }
    public String toString() {
        StringBuilder equation = new StringBuilder();
        /* A */
        if (Math.abs(A) != 1 && A != 0) equation.append(A + "x"); //if A is not -1, 1, or 0, display as normal
        else if (A == 1) equation.append("x");  //if A = 1, don't display A
        else if (A == -1) equation.append("-x"); //if A = -1, display -x
        /* B */
        if (B < 0) equation.append(" - "); //if B is negative, minus sign
        else if (B > 0 && A !=0) equation.append(" + "); //if B is positive, and A isn't 0, add plus sign
        if (Math.abs(B) != 1 && B != 0) equation.append(Math.abs(B) + "y"); //if B is not -1, 1, or 0, display |B|
        else if (Math.abs(B) == 1) equation.append("y"); //B = 1, add just y
        /* C */
        if (C < 0) equation.append(" - "); //if C is negative, minus sign
        else if (C > 0) equation.append(" + "); //if C is positive, plus sign
        if (C != 0) equation.append(Math.abs(C) + " = 0"); //if C isn't 0, display |C| and = 0
        else equation.append(" = 0"); //else just add the = 0

        if (A == 0 && B ==0) return "Invalid line"; //if x and y don't exist, not a line
        return equation.toString().trim(); //return string version of equation
    }
    private static boolean validator (String str) {
        str = str.toLowerCase(); //converts to lowercase
        String noSpace = str.replaceAll("\\s", ""); //new string without the spaces
        int equals = 0; //counts the number of equals signs

        if (!str.matches("[\\sxy0-9+=\\-]+")) { //if str has a character besides x, y, +, -, =, and numbers
            System.out.println("Invalid character error. Try again");
            return false; //invalid line
        }
        for (int i = 0; i < str.length(); i++) { //if str has consecutive spaces
            if (str.charAt(i) == ' ' && str.charAt(i + 1) == ' ') { //if a space is followed by another space
                System.out.println("Invalid spacing error. Try again");
                return false; //invalid line
            }
            try {
                //if use enters: number space number
                if (String.valueOf(str.charAt(i)).matches("[0-9]") && str.charAt(i + 1) == ' ' && String.valueOf(str.charAt(i + 2)).matches("[0-9]")) {
                    System.out.println("Invalid spacing error. Try again");
                    return false;
                }
            } catch (StringIndexOutOfBoundsException e) { //so it doesn't give an error from checking end of stirng
            }
        }
        for (int i = 0; i < noSpace.length(); i++) {
            if (String.valueOf(noSpace.charAt(i)).matches("[xy]")) { //if charAt i is an x or y
                try {
                    if (String.valueOf(noSpace.charAt(i + 1)).matches("[0-9]") || String.valueOf(noSpace.charAt(i + 1)).matches("[xy]")) { //if the next char is a number, x or y
                        System.out.println("Invalid entry. Try again");
                        return false; //invalid line
                    }
                } catch (StringIndexOutOfBoundsException e) { //if noSpace.charAt(i) is the end of the sentence, charAt(i + 1) doesn't exist
                                                              // of if noSpace is only 1 char long
                }
            }
            else if (noSpace.charAt(i) == '=') equals++; //keeps track of number of equal signs
        }
        if (equals > 1) { //if there is more than 1 equal sign
            System.out.println("Invalid entry. Try again");
            return false; //invalid line
        }
        return true;
    }

    public void readLine(Scanner in) {
        in = new Scanner(System.in);
        String input;
        do { //do while the line is valid (A and B can't both be 0)
            do { //do while validator() is false (until there is no spacing/character errors)
                input = in.nextLine();
            } while (!validator(input));

            A = 0; //assigns values of A, B, C, to be 0 temporarily
            B = 0;
            C = 0;
            input = input.toLowerCase(); //converts to lowercase
            input = input.replaceAll("\\s+", ""); //removes all spaces
            input = input.replaceAll("--", "+"); //replaces double minus to +
            input = input.replaceAll("-", "+-"); //replaces all minus signs with +-
            input = input.replaceAll("-x", "-1x"); //adds coefficient 1
            input = input.replaceAll("-y", "-1y"); //adds coefficient 1
            if (!input.contains("=")) input = input.concat("=0"); //assumes equation =0 if not specified

            String inputLS = input.substring(0, input.indexOf('=')); //string with left side of equation
            String inputRS = input.substring(input.indexOf('=') + 1, input.length()); //string with right side of equation

            //left side equation
            String[] equationLS = inputLS.split("\\+"); //splits the string at each instance of "+"
            //right side equation
            String[] equationRS = inputRS.split("\\+"); //splits the string at each instance of "+"

            int indexX, indexY; //temporary to stores the index of "x" and "y"
            /***********************left side of equation ***********************************/
            if (inputLS.contains("x")) { //checks if there is an x in the left side
                for (int i = 0; i < equationLS.length; i++) {
                    if (equationLS[i].contains("x")) {
                        indexX = equationLS[i].indexOf('x'); //stores the index of x, if it is found in an equationLS[i]
                        try {
                            A += Integer.parseInt(equationLS[i].substring(0, indexX)); //parse and adds all the numbers before x
                        } catch (NumberFormatException e) { //if there is no number in front of x, the coefficient must be 1
                            A += 1;
                        }
                    }
                }
            }
            if (inputLS.contains("y")) { //checks if there is a y in the left side
                for (int i = 0; i < equationLS.length; i++) {
                    if (equationLS[i].contains("y")) {
                        indexY = equationLS[i].indexOf('y'); //stores the index of y, if it is found in an equation[i]
                        try {
                            B += Integer.parseInt(equationLS[i].substring(0, indexY)); //parse and adds all the numbers before y
                        } catch (NumberFormatException e) { //if there is no number in front of y, the coefficient must be 1
                            B += 1;
                        }
                    }
                }
            }
            for (int i = 0; i < equationLS.length; i++) {
                //if equationLS[i] doesn't have "x" or "y" (and isn't empty), it must be a constant (C)
                if (!equationLS[i].contains("x") && !equationLS[i].contains("y") && !equationLS[i].equals("")) {
                    C += Integer.parseInt(equationLS[i]); //parse that index
                }
            }
            /***********************right side of equation ****************************************/
            //******* does the same thing as left side of the equation, except reversed (subtracts values instead of adds) **********
            if (inputRS.contains("x")) {
                for (int i = 0; i < equationRS.length; i++) {
                    if (equationRS[i].contains("x")) {
                        indexX = equationRS[i].indexOf('x');
                        try {
                            A -= Integer.parseInt(equationRS[i].substring(0, indexX));
                        } catch (NumberFormatException e) { //if there is no number in front of x, the coefficient must be 1
                            A -= 1;
                        }
                    }
                }
            }
            if (inputRS.contains("y")) {
                for (int i = 0; i < equationRS.length; i++) {
                    if (equationRS[i].contains("y")) {
                        indexY = equationRS[i].indexOf('y');
                        try {
                            B -= Integer.parseInt(equationRS[i].substring(0, indexY));
                        } catch (NumberFormatException e) { //if there is no number in front of y, the coefficient must be 1
                            B -= 1;
                        }
                    }
                }
            }
            for (int i = 0; i < equationRS.length; i++) {
                if (!equationRS[i].contains("x") && !equationRS[i].contains("y") && !equationRS[i].equals("")) {
                    C -= Integer.parseInt(equationRS[i]);
                }
            }
            if (A == 0 && B == 0) System.out.println("Invalid line. Try again.");

        } while (A == 0 && B == 0);
    }

    public String xint() {
        if (A == 0) return "N/A"; //horizontal line
        double xint = (double) -C/A; //xint formula
        if (Math.abs(xint) == 0) xint = 0; //so it doesn't return -0
        return xint + "";

    }
    public String yint() {
        if (B == 0) return "N/A"; //vertical line
        double yint = (double) -C/B; //yint formula
        if (Math.abs(yint) == 0) yint = 0; //so it doesn't return -0
        return yint + "";
    }
    public String slope() {

        if (B == 0) return "Infinity"; //vertical line
        double slope = (double) -A/B; //slope formula
        if (Math.abs(slope) == 0) slope = 0; //so it doesn't return -0
        return slope + "";
    }
    public boolean vertical() {
        if (B == 0) return true;
        return false;
    }
    public boolean horizontal() {
        if (A == 0) return true;
        return false;
    }

    public Point intersect(Line lineB) {
        //finds intersection using Cramer's rule (2x2 matrix)
        int A1 = A, B1 = B, C1 = -C;
        int A2 = lineB.A, B2 = lineB.B, C2 = -lineB.C;
        int xNum, xDenom, yNum, yDenom; //numerators and denominators
        double x, y;

        xNum = (C1 * B2) - (C2 * B1); //determinant of x numerator
        xDenom = (A1 * B2) - (A2 * B1); //determinant of x denominator
        yNum = (A1 * C2) - (A2 * C1); //determinant of y numerator
        yDenom = (A1 * B2) - (A2 * B1); //determinant of y denominator
        x = (double) xNum/xDenom;
        y = (double) yNum/yDenom;
        if (Math.abs(x) == 0) x = 0; //so it doesn't return -0
        if (Math.abs(y) == 0) y = 0;

        return new Point(x, y);
    }
}

class Point {
    private double x, y; //coordinates of point
    public Point(double a, double b) {
        x = a;
        y = b;
    }
    public String toString() { //method to return in format (x, y)
        return "(" + x + ", " + y + ")";
    }
}

public class ACLineClass {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        char choice;
        Line lineA = new Line();
        Line lineB = new Line();
        //input
        System.out.println("Enter a linear equation");
        lineA.readLine(sc);

        do {
            //menu
            System.out.println("What would you like to find out about the line: " + lineA.toString()); //outputs simplified equation
            System.out.println("(1) x-intercept");
            System.out.println("(2) y-intercept");
            System.out.println("(3) Slope");
            System.out.println("(4) Is vertical?");
            System.out.println("(5) Is horizontal?");
            System.out.println("(6) Intersection with another line");
            System.out.println("(7) Change the line");
            System.out.println("(0) Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextLine().toUpperCase().charAt(0);

            if (choice == '1') {
                System.out.print("The x-int of " + lineA.toString() + " is: ");
                System.out.println(lineA.xint()); //calls x-int method

                System.out.println("\nPress enter to continue.");
                sc.nextLine();
            }
            else if (choice == '2') {
                System.out.print("The y-int of " + lineA.toString() + " is: ");
                System.out.println(lineA.yint()); //calls y-int method

                System.out.println("\nPress enter to continue.");
                sc.nextLine();
            }
            else if (choice == '3') {
                System.out.print("The slope of " + lineA.toString() + " is: ");
                System.out.println(lineA.slope()); //calls slope method

                System.out.println("\nPress enter to continue.");
                sc.nextLine();
            }
            else if (choice == '4') {
                System.out.print("The line " + lineA.toString() + " is: ");
                if (lineA.vertical()) System.out.println("vertical."); //if vertical method is true
                else System.out.println("not vertical.");

                System.out.println("\nPress enter to continue.");
                sc.nextLine();
            }
            else if (choice == '5') {
                System.out.print("The line " + lineA.toString() + " is: ");
                if (lineA.horizontal()) System.out.println("horizontal."); //if horizontal method is true
                else System.out.println("not horizontal.");

                System.out.println("\nPress enter to continue.");
                sc.nextLine();
            }
            else if (choice == '6') {
                System.out.println("Enter another line");
                lineB.readLine(sc); //input new line
                Point intersection = lineA.intersect(lineB);

                //won't call slope method if lines are parallel or coincident to prevent dividing by 0 errors or other errors
                if (lineA.slope().equals(lineB.slope()) && lineA.C == lineB.C) System.out.println("Lines are coincident."); //if the slope and y-ints are the same, they are the same line
                else if (lineA.slope().equals(lineB.slope())) System.out.println("Lines are parallel."); //if the slope is the same, but y-ints are different, lines are parallel
                else System.out.println("Intersection at P" + intersection.toString()); //else, call the intersection method

                System.out.println("\nPress enter to continue.");
                sc.nextLine();
            }
            else if (choice == '7') {
                System.out.println("Enter a new linear equation");
                lineA.readLine(sc); //overwrites current line
            }
            else if (choice != '0'){
                System.out.println("Invalid choice. Try again\n");
            }
        } while (choice != '0');
        System.out.println("Terminated.");
    }
}