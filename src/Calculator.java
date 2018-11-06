import java.util.ArrayList;

public class Calculator {

    private static Calculator uniqueInstance;
    private TokenList tokenList;
    private String[] operationOrder = {"divide", "multiply", "sum", "subtract"};

    private Calculator(String expression){

        this.tokenList = TokenList.getInstance(expression);
    }

    //singleton
    public static synchronized Calculator getInstance(String expression){
        if(uniqueInstance==null){
            uniqueInstance = new Calculator(expression);
        }
        return uniqueInstance;
    }


    public void solveExpression(){

        while(tokenList.getTokenArrayList().size()>1){

//            ArrayList<Integer> subTokenIndexList = tokenList.extractParenthesisExpression();
//            ArrayList<Token> subTokens = createSubTokenList(subTokenIndexList);
//            Token resultToken;

            for (int i = 0; i < operationOrder.length; i++) {
                calculateOperators(operationOrder[i]);
            }
            calculateAssign();
        }
        saveResultToBinding();
    }




//    private ArrayList<Token> createSubTokenList(ArrayList<Integer> subTokenIndexList){
//        ArrayList<Token> subTokenList = new ArrayList<>();
//
//        //remove brackets either side of sub expression
//        subTokenIndexList.remove(0);
//        subTokenIndexList.remove(subTokenIndexList.size()-1);
//
//        //create sub token list from indexes above
//        for (Integer index:subTokenIndexList
//             ) {
//            subTokenList.add(tokenList.getTokenArrayList().get(index));
//        }
//        return subTokenList;
//    }

//    private void updateTokenList(Token updateToken, ArrayList<Integer> subTokenIndexList){
//        //print
//        System.out.println("\n\nBefore");
//        for (Token token:tokenList.getTokenArrayList()
//        ) {
//            System.out.println(token.toString());
//        }
//
//        tokenList.getTokenArrayList().set(subTokenIndexList.get(0),updateToken);
//        while(subTokenIndexList.size()>1){
//            tokenList.getTokenArrayList().remove(subTokenIndexList.get(1));
//            subTokenIndexList.remove(1);
//        }
//
//
//
////        for (int i = 0; i < subTokenIndexList.size(); i++) {
////            if(i==0){
////                tokenList.getTokenArrayList().set(subTokenIndexList.get(i),updateToken);
////            }
////            else{
////                tokenList.getTokenArrayList().remove(subTokenIndexList.get(i));
////                subTokenIndexList.remove(i);
////            }
////        }
//
//        //print
//        System.out.println("\n\nAfter");
//        for (Token token:tokenList.getTokenArrayList()
//        ) {
//            System.out.println(token.toString());
//        }
//    }

    private void calculateOperators(String operation){
        String resultTokenType = "num";
        Token resultToken;

        for (int i = 1; i < tokenList.getTokenArrayList().size()-1; i++) {

            Token currentToken = tokenList.getTokenArrayList().get(i);
            Token previousToken = tokenList.getTokenArrayList().get(i-1);
            Token nextToken = tokenList.getTokenArrayList().get(i+1);
            CalcTree calculation;

            if(currentToken.getName().equals(operation)){

                calculation = new CalcTree(currentToken.getValue(),
                        new CalcTree(previousToken.getValue(), null, null),
                        new CalcTree(nextToken.getValue(), null, null));

                Double result = calculation.eval(calculation);//this can't be good practice...
                System.out.println("\n\nResult = "+result);

                resultToken = new Token(resultTokenType,result.toString());
                refreshTokenArrayList(i,resultToken);
                i=0;
            }
        }
    }


    private void refreshTokenArrayList(int index, Token refreshToken){
        //print
        System.out.println("\n\nBefore");
        for (Token token:tokenList.getTokenArrayList()
            ) {
        System.out.println(token.toString());
        }

        tokenList.getTokenArrayList().set(index-1, refreshToken);
        tokenList.getTokenArrayList().remove(index);
        tokenList.getTokenArrayList().remove(index);

        //print
        System.out.println("\n\nAfter");
        for (Token token:tokenList.getTokenArrayList()
            ) {
        System.out.println(token.toString());
        }
    }

    private void calculateAssign(){

        for (int i = 1; i < tokenList.getTokenArrayList().size()-1; i++) {

            Token currentToken = tokenList.getTokenArrayList().get(i);
            Token previousToken = tokenList.getTokenArrayList().get(i-1);
            Token nextToken = tokenList.getTokenArrayList().get(i+1);

            if(currentToken.getName().equals("assign")){
                Double bindValue = Double.parseDouble(nextToken.getValue());
                String bindKey = previousToken.getValue();
                tokenList.updateBindings(bindKey,bindValue);
                //bindings.put(bindKey,bindValue);
                refreshTokenArrayList(i,nextToken);
            }
        }
    }

    private void saveResultToBinding(){
        if(tokenList.getTokenArrayList().size()==1){
            String finalValue = tokenList.getTokenArrayList().get(0).getValue();
            Double finalResult = Double.parseDouble(finalValue);
            tokenList.updateBindings("_",finalResult);
            //bindings.put("_",finalResult);
        }//needs to be saved but won't need serialisation in their version.
    }


}
