package club.quarkusclub.bookclub.effectivejava.chapter03.item12.generated;

public record PhoneNumberRecord(short areaCode, short prefix, short lineNumber) {

    public PhoneNumberRecord {
        rangeCheck(areaCode, 999, "area code");
        rangeCheck(prefix, 999, "prefix");
        rangeCheck(lineNumber, 9999, "line number");
    }

    private static void rangeCheck(short value, int max, String field) {
        if (value < 0 || value > max) {
            throw new IllegalArgumentException(field + ": " + value);
        }
    }
}
