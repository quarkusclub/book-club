package club.quarkusclub.bookclub.effectivejava.chapter03.item11.distribution;

import club.quarkusclub.bookclub.effectivejava.chapter03.item11.contract.PhoneNumber;

/**
 * A legal but poorly distributed hash: equality still compares all significant fields.
 */
public final class ConstantHashPhoneNumber {

    private final PhoneNumber number;

    public ConstantHashPhoneNumber(int areaCode, int prefix, int lineNumber) {
        this.number = new PhoneNumber(areaCode, prefix, lineNumber);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ConstantHashPhoneNumber other && number.equals(other.number);
    }

    @Override
    public int hashCode() {
        return 42;
    }
}
