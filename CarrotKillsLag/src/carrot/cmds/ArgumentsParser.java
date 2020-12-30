package carrot.cmds;

import java.util.ArrayList;

public final class ArgumentsParser {
    /** Returns true if the index is too big, meaning the array is too small. True == Out of range
     * @param array array
     * @param indexedLength The length - 1
     * @return If index is out of range
     */
    public static boolean ArgsTooSmall(Object[] array, int indexedLength){
        if (array == null){
            return false;
        }
        return array.length <= indexedLength;
    }
    public static ParsedValue<String> ParseString(String[] args, int index) {
        if (ArgsTooSmall(args, index)) {
            return new ParsedValue("ERR_STR", true);
        }
        return new ParsedValue(args[index], false);
    }

    public static ParsedValue<Integer> ParseInt(String[] args, int index){
        try{
            if (ArgsTooSmall(args, index)) {
                return new ParsedValue(0, true);
            }
            return new ParsedValue(Integer.parseInt(args[index]), false);
        }
        catch (Exception e){
            return new ParsedValue(0, true);
        }
    }
    public static ParsedValue<Double> ParseDouble(String[] args, int index){
        try{
            if (ArgsTooSmall(args, index)) {
                return new ParsedValue(0.0d, true);
            }
            return new ParsedValue(Double.parseDouble(args[index]), false);
        }
        catch (Exception e){
            return new ParsedValue(0.0d, true);
        }
    }

    public static String GetCommand(String[] fullArgs){
        if (ArgsTooSmall(fullArgs, 0)){
            return "intern_cmd_broken_rip_xd";
        }
        return fullArgs[0];
    }

    public static ArrayList<String> GetCommandArgs(String[] fullArgs){
        ArrayList<String> args = new ArrayList();
        for(String str : fullArgs){
            args.add(str);
        }
        args.remove(0);
        return args;
    }

    public static String[] ToArray(ArrayList<String> arr){
        return arr.toArray(new String[0]);
    }
}
