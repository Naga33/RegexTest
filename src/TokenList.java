import java.util.ArrayList;
import java.util.regex.Matcher;

public class TokenList {

    private static TokenList uniqueInstance;
    private String expression;
    private RegexList regexList = RegexList.getInstance();
    private ArrayList<Token> tokenArrayList = new ArrayList<>();

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
        //TODO: split this method up into smaller methods and fix regex hack

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

        //if array list contains num with no value, remove (hack as result of num regex - fix this properly)
        ArrayList<Token> removeTokenList = new ArrayList<>();
        for (Token token:tokenArrayList
        ) {
            if(token.getValue().equals("")){
                removeTokenList.add(token);
            }
        }
        for (Token token:removeTokenList
        ) {
            tokenArrayList.remove(token);
        }
    }

    public ArrayList<Token> getTokenArrayList(){
        return tokenArrayList;
    }


}
