package club.quarkusclub.bookclub.effectivejava.chapter03.item11.contract;

/**
 * Deliberately violates the hash contract by inheriting Object.hashCode alongside value equality.
 */
public final class PhoneNumberWithoutHashCode {

    private final short areaCode;
    private final short prefix;
    private final short lineNumber;

    public PhoneNumberWithoutHashCode(int areaCode, int prefix, int lineNumber) {
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
        return o instanceof PhoneNumberWithoutHashCode other
                && other.lineNumber == lineNumber
                && other.prefix == prefix
                && other.areaCode == areaCode;
    }
}
