import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

public class TokenList {

    private static TokenList uniqueInstance;
    private String expression;
    private RegexList regexList = RegexList.getInstance();
    private ArrayList<Token> tokenArrayList = new ArrayList<>();
    private Map<String,Double> bindings = new HashMap<>();

    private TokenList(String expression){
        this.expression = expression;
        createTokenArrayList();
    }

    //singleton
    public static synchronized TokenList getInstance(String expression){
        if(uniqueInstance==null){
            uniqueInstance = new TokenList(expression);
        }
        return uniqueInstance;
    }

    private void createTokenArrayList(){

        while(!expression.equals("")){

            for (Regex currentRegex:regexList.getRegexArrayList()
            ) {
                //compile regex string and compare to expression
                Matcher matcher = currentRegex.getPattern().matcher(expression);

                if(matcher.find()){
                    //extract substring from expression
                    String exprExtract = matcher.group(0);

                    //add substring and token type to token list
                    tokenArrayList.add(new Token(currentRegex.getName(), exprExtract));

                    //remove substring from expression
                    expression = expression.replaceFirst(currentRegex.getValue(),"");
                }
                if(expression.equals("")){
                    break;
                }
            }
        }
        //removeNumsWithoutValue();
        replaceIds();
    }

    private void removeNumsWithoutValue(){

        ArrayList<Token> removeTokenList = new ArrayList<>();

        //add valueless tokens to removeTokenList
        for (Token token:tokenArrayList
        ) {
            if(token.getValue().equals("")){
                removeTokenList.add(token);
            }
        }

        //remove valueless tokens from tokenArrayList
        for (Token token:removeTokenList
        ) {
            tokenArrayList.remove(token);
        }

    }

    private void replaceIds(){
        for (Token token:tokenArrayList
        ) {
            if(token.getName().equals("id")){
                //check binding keys for token value
                if(bindings.containsKey(token.getValue())){
                    token.setValue(bindings.get(token.getValue()).toString());
                }
            }
        }
    }

//    public ArrayList<Integer> extractParenthesisExpression(){
//        ArrayList<Integer> subTokenIndexList = new ArrayList<>();
//        int outerCount = 0;
//        int innerCount = 1;
//        boolean outerCheck = true;
//        boolean innerCheck = true;
//
//        while(outerCheck){
//            Token currentToken = tokenArrayList.get(outerCount);
//
//            if(currentToken.getValue().equals("(")){
//                //add current token to subTokenList
//                subTokenIndexList.add(outerCount);
//
//                //check each token onwards from this point
//                while (innerCheck){
//                    Token nextToken = tokenArrayList.get(innerCount);
//                    if(nextToken.getValue().equals("(")){
//                        //clear subTokenList and restart
//                        subTokenIndexList.clear();
//                        innerCount++;
//                    }
//                    if(nextToken.getValue().equals(")")){
//                        //add to subTokenIndexList and finish searching
//                        subTokenIndexList.add(innerCount);
//                        innerCheck = false;
//                        outerCheck = false;
//                    }
//                    else{
//                        subTokenIndexList.add(innerCount);
//                        innerCount++;
//                    }
//                }
//            }
//            outerCount = outerCount + innerCount;
//        }
//        //print
//        System.out.println("\n\nSubTokenList: ");
//        for (Integer index:subTokenIndexList
//        ) {
//            System.out.println("index: "+ index + "\nToken: "+ tokenArrayList.get(index));
//        }
//        return subTokenIndexList;
//    }


    public ArrayList<Token> getTokenArrayList(){
        return tokenArrayList;
    }

    public void updateBindings(String key, Double value){
        this.bindings.put(key,value);
        printBindings();
    }

    private void printBindings() {
        System.out.println("\n\nPrinting bindings:");
        for (String bindingKey:bindings.keySet()
        ) {
            System.out.println(bindingKey+" : "+bindings.get(bindingKey));
        }
    }


}
