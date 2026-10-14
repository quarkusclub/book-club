package club.quarkusclub.bookclub.effectivejava.chapter03.item11.contract;

import java.util.Objects;

/**
 * The supplied hash simulates different identity hashes deterministically.
 * It is deliberately excluded from equality, making this implementation invalid.
 */
public final class PhoneNumberBrokenHashCode {

    private final PhoneNumber number;
    private final int instanceHash;

    public PhoneNumberBrokenHashCode(PhoneNumber number, int instanceHash) {
        this.number = Objects.requireNonNull(number);
        this.instanceHash = instanceHash;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof PhoneNumberBrokenHashCode other && number.equals(other.number);
    }

    @Override
    public int hashCode() {
        return instanceHash;
    }
}
