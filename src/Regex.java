public class Regex {

    private String name;
    private String value;

    public Regex(String aName, String aValue){
        name = aName;
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
