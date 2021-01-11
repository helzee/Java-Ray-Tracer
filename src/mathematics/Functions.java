package mathematics;

public class Functions {

    public static boolean inRange(double value, double starting, double ending) {
        if (value > starting && value < ending)
            return true;
        return false;
    }

    public static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    public static double asRadians(double degree) {
        return degree * Math.PI / 180;
    }

}
