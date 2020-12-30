package carrot.cmds;

public class ParsedValue<T> {
    T value;
    boolean failed;

    public ParsedValue(T value, boolean failed){
        this.value = value;
        this.failed = failed;
    }
}
