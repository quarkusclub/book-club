package club.quarkusclub.bookclub.effectivejava.chapter03.item14.multifield;

import java.util.Comparator;

import static java.util.Comparator.comparingInt;

public record ComparatorPhoneNumber(int areaCode, int prefix, int lineNum)
        implements Comparable<ComparatorPhoneNumber> {

    private static final Comparator<ComparatorPhoneNumber> COMPARATOR =
            comparingInt(ComparatorPhoneNumber::areaCode)
                    .thenComparingInt(ComparatorPhoneNumber::prefix)
                    .thenComparingInt(ComparatorPhoneNumber::lineNum);

    @Override
    public int compareTo(ComparatorPhoneNumber other) {
        return COMPARATOR.compare(this, other);
    }
}
