package club.quarkusclub.bookclub.effectivejava.chapter03.item12.format;

import java.util.Locale;

public final class PhoneNumber {

    private final short areaCode;
    private final short prefix;
    private final short lineNumber;

    public PhoneNumber(int areaCode, int prefix, int lineNumber) {
        this.areaCode = rangeCheck(areaCode, 999, "area code");
        this.prefix = rangeCheck(prefix, 999, "prefix");
        this.lineNumber = rangeCheck(lineNumber, 9999, "line number");
    }

    private static short rangeCheck(int value, int max, String field) {
        if (value < 0 || value > max) {
            throw new IllegalArgumentException(field + ": " + value);
        }
        return (short) value;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof PhoneNumber other
                && other.lineNumber == lineNumber
                && other.prefix == prefix
                && other.areaCode == areaCode;
    }

    @Override
    public int hashCode() {
        int result = Short.hashCode(areaCode);
        result = 31 * result + Short.hashCode(prefix);
        result = 31 * result + Short.hashCode(lineNumber);
        return result;
    }

    public short areaCode() {
        return areaCode;
    }

    public short prefix() {
        return prefix;
    }

    public short lineNumber() {
        return lineNumber;
    }

    /**
     * Returns twelve characters in the format "XXX-YYY-ZZZZ", using decimal digits
     * for the area code, prefix, and line number. Fields are padded with leading zeros;
     * for example, line number 123 is represented as "0123".
     */
    @Override
    public String toString() {
        return String.format(Locale.ROOT, "%03d-%03d-%04d",
                areaCode, prefix, lineNumber);
    }
}
