package club.quarkusclub.bookclub.effectivejava.chapter03.item11.caching;

/**
 * The counter observes sequential calculations for the demo, not timing or concurrency.
 * A genuine zero hash is recomputed because zero also means "not calculated".
 */
public final class CachedPhoneNumber {

    private final short areaCode;
    private final short prefix;
    private final short lineNumber;

    private int hashCode;
    private int hashComputations;

    public CachedPhoneNumber(int areaCode, int prefix, int lineNumber) {
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
        return o instanceof CachedPhoneNumber other
                && other.lineNumber == lineNumber
                && other.prefix == prefix
                && other.areaCode == areaCode;
    }

    @Override
    public int hashCode() {
        int result = hashCode;
        if (result == 0) {
            hashComputations++;
            result = Short.hashCode(areaCode);
            result = 31 * result + Short.hashCode(prefix);
            result = 31 * result + Short.hashCode(lineNumber);
            hashCode = result;
        }
        return result;
    }

    int hashComputations() {
        return hashComputations;
    }
}
