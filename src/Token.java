public class Token {

    private String name;
    private String value;

    public Token(String aType, String aValue){

        name = aType;
        value = aValue;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return name+": "+value;
    }
}
