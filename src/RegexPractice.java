import java.util.*;

public class RegexPractice {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String expr = scanner.next();

        Calculator calculator = Calculator.getInstance(expr);
        calculator.solveExpression();

    }
}
