package club.quarkusclub.bookclub.effectivejava.chapter03.item14.multifield;

public record PhoneNumber(int areaCode, int prefix, int lineNum) implements Comparable<PhoneNumber> {

    @Override
    public int compareTo(PhoneNumber other) {
        int result = Integer.compare(areaCode, other.areaCode);
        if (result == 0) {
            result = Integer.compare(prefix, other.prefix);
            if (result == 0) {
                result = Integer.compare(lineNum, other.lineNum);
            }
        }
        return result;
    }
}
