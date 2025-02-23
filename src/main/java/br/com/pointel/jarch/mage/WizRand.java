package br.com.pointel.jarch.mage;

import java.util.List;
import java.util.Random;

public class WizRand {

    private static final Random RAND = new Random();

    public static Boolean getBool() {
        return RAND.nextBoolean();
    }

    public static Boolean getBool(Integer min, Integer max, Integer odds) {
        return WizRand.getInt(min, max) < odds;
    }

    public static Integer getDigit() {
        return RAND.nextInt(10);
    }

    public static Integer getInt() {
        return RAND.nextInt();
    }

    public static Integer getInt(Integer max) {
        return RAND.nextInt(max);
    }

    public static Integer getInt(Integer min, Integer max) {
        return min + RAND.nextInt(max - min);
    }

    public static char getChar() {
        return WizChar.SIMPLE[WizRand.getInt(0, WizChar.SIMPLE.length)];
    }

    public static String getChars(int size) {
        var result = "";
        if (size > 0) {
            while (result.length() < size) {
                result = result + WizRand.getChar();
            }
        }
        return result;
    }

    public static Float getFloat() {
        return RAND.nextFloat();
    }

    public static Double getDouble() {
        return RAND.nextDouble();
    }

    public static Double getGaussian() {
        return RAND.nextGaussian();
    }

    public static void getBytes(byte[] bytes) {
        RAND.nextBytes(bytes);
    }

    public static <T> T getItem(T[] fromArray) {
        if (fromArray != null) {
            if (fromArray.length > 0) {
                return fromArray[WizRand.getInt(fromArray.length)];
            }
        }
        return null;
    }

    public static <T> T getItem(List<T> fromList) {
        if (fromList != null) {
            if (fromList.size() > 0) {
                return fromList.get(WizRand.getInt(fromList.size()));
            }
        }
        return null;
    }
}
