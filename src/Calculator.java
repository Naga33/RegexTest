import java.util.HashMap;
import java.util.Map;

public class Calculator {

    private static Calculator uniqueInstance;
    private TokenList tokenList;
    private String[] operationOrder = {"divide", "multiply", "sum", "subtract"};
    private Map<String,Double> bindings = new HashMap<>();

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
        replaceIds();
        while(tokenList.getTokenArrayList().size()>1){

            for (int i = 0; i < operationOrder.length; i++) {
                calculateOperators(operationOrder[i]);
            }
            calculateAssign();
        }
        saveResultToBinding();
        printBindings();
    }

    private void replaceIds(){
        for (Token token:tokenList.getTokenArrayList()
        ) {
            if(token.getName().equals("id")){
                //check binding keys for token value
                if(bindings.containsKey(token.getValue())){
                    token.setValue(bindings.get(token.getValue()).toString());
                }
            }
        }
    }

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

                refreshTokenArrayList(i,resultToken);//check i-1 is correct

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
                bindings.put(bindKey,bindValue);

                refreshTokenArrayList(i,nextToken);

                i=0;
            }
        }
    }

    private void saveResultToBinding(){
        if(tokenList.getTokenArrayList().size()==1){
            String finalValue = tokenList.getTokenArrayList().get(0).getValue();
            Double finalResult = Double.parseDouble(finalValue);
            bindings.put("_",finalResult);
            System.out.println("New binding: "+bindings.get("_"));
        }//needs to be saved but won't need serialisation in their version.
    }

    private void printBindings() {
        for (String bindingKey:bindings.keySet()
             ) {
            System.out.println(bindingKey+" : "+bindings.get(bindingKey));
        }
    }
}
