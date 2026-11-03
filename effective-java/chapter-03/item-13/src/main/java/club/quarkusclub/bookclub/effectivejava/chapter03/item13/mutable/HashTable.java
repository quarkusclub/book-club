package club.quarkusclub.bookclub.effectivejava.chapter03.item13.mutable;

public final class HashTable implements Cloneable {
    private Entry[] buckets = new Entry[4];

    private static final class Entry {
        private final String key;
        private String value;
        private Entry next;

        private Entry(String key, String value, Entry next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        private Entry copyChain() {
            Entry head = new Entry(key, value, null);
            Entry tail = head;
            for (Entry node = next; node != null; node = node.next) {
                tail.next = new Entry(node.key, node.value, null);
                tail = tail.next;
            }
            return head;
        }
    }

    public void put(String key, String value) {
        int bucket = Math.floorMod(key.hashCode(), buckets.length);
        for (Entry node = buckets[bucket]; node != null; node = node.next) {
            if (node.key.equals(key)) {
                node.value = value;
                return;
            }
        }
        buckets[bucket] = new Entry(key, value, buckets[bucket]);
    }

    public String get(String key) {
        int bucket = Math.floorMod(key.hashCode(), buckets.length);
        for (Entry node = buckets[bucket]; node != null; node = node.next) {
            if (node.key.equals(key)) {
                return node.value;
            }
        }
        return null;
    }

    public HashTable shallowBucketsCopy() {
        try {
            HashTable copy = (HashTable) super.clone();
            copy.buckets = buckets.clone();
            return copy;
        } catch (CloneNotSupportedException impossible) {
            throw new AssertionError(impossible);
        }
    }

    @Override
    public HashTable clone() {
        HashTable copy = shallowBucketsCopy();
        for (int i = 0; i < buckets.length; i++) {
            copy.buckets[i] = buckets[i] == null ? null : buckets[i].copyChain();
        }
        return copy;
    }

}
