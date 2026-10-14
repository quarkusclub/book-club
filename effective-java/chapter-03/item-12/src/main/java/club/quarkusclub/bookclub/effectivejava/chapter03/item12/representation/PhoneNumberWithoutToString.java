package club.quarkusclub.bookclub.effectivejava.chapter03.item12.representation;

import club.quarkusclub.bookclub.effectivejava.chapter03.item12.format.PhoneNumber;

public final class PhoneNumberWithoutToString {

    private final PhoneNumber number;

    public PhoneNumberWithoutToString(int areaCode, int prefix, int lineNumber) {
        this.number = new PhoneNumber(areaCode, prefix, lineNumber);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof PhoneNumberWithoutToString other && number.equals(other.number);
    }

    @Override
    public int hashCode() {
        return number.hashCode();
    }
}
