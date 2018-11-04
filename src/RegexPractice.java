import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexPractice {
    public static void main(String[] args) {

        ArrayList<Token> tokenList = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);
        String expr = scanner.next();


        Regex underscore = new Regex("underscore", "\\A_");
        Regex cos = new Regex("cos", "\\Acos");
        Regex sin = new Regex("sin", "\\Asin");
        Regex log = new Regex("log", "\\Alog");
        Regex sqrt = new Regex("sqrt", "\\Asqrt");
        Regex id = new Regex("id", "\\A[a-zA-Z0-9_]+");
        Regex openParen = new Regex("openParen", "\\A\\(");
        Regex closeParen = new Regex("closeParen", "\\A\\)");
        Regex assign = new Regex("assign", "\\A=");
        Regex num = new Regex("num", "\\A[0-9]*+(\\.[0-9]+)*");
        Regex sum = new Regex("sum", "\\A\\+");
        Regex subtract = new Regex("subtract", "\\A-");
        Regex multiply = new Regex("multiply", "\\A\\*");
        Regex divide = new Regex("divide", "\\A\\/");

        ArrayList<Regex> regexList = new ArrayList<>();
        regexList.add(underscore);
        regexList.add(cos);
        regexList.add(sin);
        regexList.add(log);
        regexList.add(sqrt);
        regexList.add(id);
        regexList.add(openParen);
        regexList.add(closeParen);
        regexList.add(assign);
        regexList.add(num);
        regexList.add(sum);
        regexList.add(subtract);
        regexList.add(multiply);
        regexList.add(divide);

        Pattern regexPattern;
        Matcher matcher;
        String exprExtract;
        String newExpr = expr;


        for (Regex currentRegex:regexList
             ) {
            System.out.println(currentRegex.toString());

            //compile regex string and compare to expression
            regexPattern = Pattern.compile(currentRegex.getValue());
            matcher = regexPattern.matcher(newExpr);



            if(matcher.find()){
                //extract substring from expression
                exprExtract = matcher.group(0);

                //add substring and token type to token list
                tokenList.add(new Token(currentRegex.getName(), exprExtract));

                //remove substring from expression
                newExpr = newExpr.replaceFirst(currentRegex.getValue(),"");

                System.out.println("extract: "+exprExtract);
                System.out.println("newString: "+newExpr);
            }
            if(newExpr.equals("")){
                break;
            }
        }

        //print
        System.out.println("\n\nPrinting tokenList");
        for (Token token:tokenList
             ) {
            System.out.println(token.toString());
        }











//        String regex = ".*\\d.*";
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(expr);
//        boolean matches = matcher.matches();
//        System.out.println("matches = " + matches);

//        String letter = "[a-zA-Z]";
//        String underscore = "_";
//        String sum = "\\+";
//        String subtract = "\\-";
//        String product = "\\*";
//        String divide = "/";
//        String openParen = "\\(";
//        String closeParen = "\\)";
//        String number = "\\d";
//        String assign = "\\=";
//        String decimal = "\\.";
//        String[] possibleCharacters = {letter,underscore,sum,subtract,product,divide,openParen,closeParen,number,assign,decimal};
//        Pattern[] possiblePatterns = new Pattern[possibleCharacters.length];
//
//        //compile regex strings
//        for (int i = 0; i < possibleCharacters.length; i++) {
//            possiblePatterns[i] = Pattern.compile(possibleCharacters[i]);
//        }
//
//        //split expr into characters
//        String[] exprCharacters = expr.split("(?<!^)"); //negative look behind
//
//
//        Matcher currentMatcher;
//        boolean match = false;
//        String currentCharacter;
//        int count = 0;
//
//        //change this loop if future iterations become part of a token? remove from string or array?
//        for (int i = 0; i < exprCharacters.length; i++) {
//
//            //here we test for the type of the first character
//            while(!match){
//                currentMatcher = possiblePatterns[count].matcher(exprCharacters[i]);
//                match = currentMatcher.matches();
//
//                if(match){
//                    System.out.println(possiblePatterns[count].toString()+" was found at position "+i);
//
//                    //if match is true then we want to determine token type
//                    currentCharacter = possiblePatterns[count].toString();
//                    if(currentCharacter == underscore){
//                        //could be id that is longer than just _
//                        while ()
//                    }
//
//                }
//
//                count++;
//            }
//
//
//
//        }


    }
}
