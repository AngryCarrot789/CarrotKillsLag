package carrot.maths;

public final class Maths {
    public static int Clamp(int value, int min, int max){
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }

    public static double Clamp(double value, double min, double max){
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }

    public static int CompareInt(int x, int y) {
        return (x < y) ? -1 : ((x == y) ? 0 : 1);
    }
}
