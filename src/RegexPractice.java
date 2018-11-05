import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexPractice {
    public static void main(String[] args) {

        ArrayList<Token> tokenList = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);
        String expr = scanner.next();

        Regex cos = new Regex("cos", "\\Acos");
        Regex sin = new Regex("sin", "\\Asin");
        Regex log = new Regex("log", "\\Alog");
        Regex sqrt = new Regex("sqrt", "\\Asqrt");
        Regex id = new Regex("id", "\\A[a-zA-Z0-9_]+");
        Regex openParen = new Regex("openParen", "\\A\\(");
        Regex closeParen = new Regex("closeParen", "\\A\\)");
        Regex assign = new Regex("assign", "\\A=");
        Regex num = new Regex("num", "\\A[0-9]*(\\.[0-9]+)*");
        Regex sum = new Regex("sum", "\\A\\+");
        Regex subtract = new Regex("subtract", "\\A-");
        Regex multiply = new Regex("multiply", "\\A\\*");
        Regex divide = new Regex("divide", "\\A\\/");

        ArrayList<Regex> regexList = new ArrayList<>();
        regexList.add(cos);
        regexList.add(sin);
        regexList.add(log);
        regexList.add(sqrt);
        regexList.add(id);
        regexList.add(openParen);
        regexList.add(assign);
        regexList.add(num);
        regexList.add(sum);
        regexList.add(subtract);
        regexList.add(multiply);
        regexList.add(divide);
        regexList.add(closeParen);

        Pattern regexPattern;
        Matcher matcher;
        String exprExtract;
        String newExpr = expr;

        while(!newExpr.equals("")){

            for (Regex currentRegex:regexList
            ) {
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
                }
                if(newExpr.equals("")){
                    break;
                }
            }
        }

        //if array list contains num with no value, remove (hack as result of num regex - fix this properly)
        ArrayList<Token> removeTokenList = new ArrayList<>();
        for (Token token:tokenList
             ) {
            if(token.getValue().equals("")){
                removeTokenList.add(token);
            }
        }
        for (Token token:removeTokenList
             ) {
            tokenList.remove(token);
        }


        //BINDINGS
        Map<String,Double> bindings = new HashMap<>();//they use treemap, but that allows duplicate keys and is slower as it retains order. figure out why they use treemap
        //test
        bindings.put("_",5.0);
        bindings.put("a_09", 1.10000001);


        //check for ids (could put all tokens in array of something, iterate through token names?)
        for (Token token:tokenList
             ) {
            if(token.getName().equals("id")){
                //check binding keys for token value
                if(bindings.containsKey(token.getValue())){
                    token.setValue(bindings.get(token.getValue()).toString());//must be string till eval?
                }
            }
        }

        //print
        System.out.println("\n\nPrinting tokenList");
        for (Token token:tokenList
        ) {
            System.out.println(token.toString());
        }


        while(tokenList.size()>1){

            for (int i = 1; i < tokenList.size()-1; i++) {

                //print
                System.out.println("\n\nPrinting tokenList");
                for (Token token:tokenList
                ) {
                    System.out.println(token.toString());
                }
                System.out.println("\n\nfor loop iteration: "+i);


                Token currentToken = tokenList.get(i);
                Token previousToken = tokenList.get(i-1);
                Token nextToken = tokenList.get(i+1);
                CalcTree calculation;

                if(currentToken.getName().equals("divide")){
                    calculation = new CalcTree(currentToken.getValue(),
                            new CalcTree(previousToken.getValue(), null, null),
                            new CalcTree(nextToken.getValue(), null, null));

                    Double result = calculation.eval(calculation);//this can't be good practice...
                    System.out.println("\n\nResult = "+result);

                    Token resultToken = new Token("num",result.toString());

                    tokenList.set(i-1, resultToken);
                    tokenList.remove(i);
                    tokenList.remove(i);

                    i=0;
                }
            }

            for (int i = 1; i < tokenList.size()-1; i++) {

                //print
                System.out.println("\n\nPrinting tokenList");
                for (Token token:tokenList
                ) {
                    System.out.println(token.toString());
                }
                System.out.println("\n\nfor loop iteration: "+i);


                Token currentToken = tokenList.get(i);
                Token previousToken = tokenList.get(i-1);
                Token nextToken = tokenList.get(i+1);
                CalcTree calculation;

                if(currentToken.getName().equals("multiply")){
                    calculation = new CalcTree(currentToken.getValue(),
                            new CalcTree(previousToken.getValue(), null, null),
                            new CalcTree(nextToken.getValue(), null, null));

                    Double result = calculation.eval(calculation);//this can't be good practice...
                    System.out.println("\n\nResult = "+result);

                    Token resultToken = new Token("num",result.toString());

                    tokenList.set(i-1, resultToken);
                    tokenList.remove(i);
                    tokenList.remove(i);

                    i=0;
                }
            }

            for (int i = 1; i < tokenList.size()-1; i++) {

                //print
                System.out.println("\n\nPrinting tokenList");
                for (Token token:tokenList
                ) {
                    System.out.println(token.toString());
                }
                System.out.println("\n\nfor loop iteration: "+i);


                Token currentToken = tokenList.get(i);
                Token previousToken = tokenList.get(i-1);
                Token nextToken = tokenList.get(i+1);
                CalcTree calculation;

                if(currentToken.getName().equals("sum")){
                    calculation = new CalcTree(currentToken.getValue(),
                            new CalcTree(previousToken.getValue(), null, null),
                            new CalcTree(nextToken.getValue(), null, null));

                    Double result = calculation.eval(calculation);//this can't be good practice...
                    System.out.println("\n\nResult = "+result);

                    Token resultToken = new Token("num",result.toString());

                    tokenList.set(i-1, resultToken);
                    tokenList.remove(i);
                    tokenList.remove(i);

                    i=0;
                }
            }

            for (int i = 1; i < tokenList.size()-1; i++) {

                //print
                System.out.println("\n\nPrinting tokenList");
                for (Token token:tokenList
                ) {
                    System.out.println(token.toString());
                }
                System.out.println("\n\nfor loop iteration: "+i);


                Token currentToken = tokenList.get(i);
                Token previousToken = tokenList.get(i-1);
                Token nextToken = tokenList.get(i+1);
                CalcTree calculation;

                if(currentToken.getName().equals("subtract")){
                    calculation = new CalcTree(currentToken.getValue(),
                            new CalcTree(previousToken.getValue(), null, null),
                            new CalcTree(nextToken.getValue(), null, null));

                    Double result = calculation.eval(calculation);//this can't be good practice...
                    System.out.println("\n\nResult = "+result);

                    Token resultToken = new Token("num",result.toString());

                    tokenList.set(i-1, resultToken);
                    tokenList.remove(i);
                    tokenList.remove(i);

                    i=0;
                }
            }

            //print
            System.out.println("\n\nPrinting tokenList");
            for (Token token:tokenList
            ) {
                System.out.println("index: "+tokenList.indexOf(token));
                System.out.println(token.toString());
            }
        }//while end

        if(tokenList.size()==1){
            Double finalResult = Double.parseDouble(tokenList.get(0).getValue());
            bindings.put("_",finalResult);
            System.out.println("New binding: "+bindings.get("_"));
        }//needs to be saved but won't need serialisation in their version.


        //print
        System.out.println("\n\nPrinting tokenList");
        for (Token token:tokenList
        ) {
            System.out.println("index: "+tokenList.indexOf(token));
            System.out.println(token.toString());
        }


    }


}
