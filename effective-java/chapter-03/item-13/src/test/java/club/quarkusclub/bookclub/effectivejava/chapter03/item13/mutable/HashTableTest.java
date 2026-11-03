package club.quarkusclub.bookclub.effectivejava.chapter03.item13.mutable;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("HashTable copies must reconstruct every chain node")
class HashTableTest {
    @Test
    @DisplayName("Copying only buckets shares the chain tail while rebuilding nodes isolates it")
    void cloneOwnsChain() {
        var original = new HashTable();
        original.put("Aa", "first");
        original.put("BB", "second");
        assertThat("Aa".hashCode()).isEqualTo("BB".hashCode());
        original.shallowBucketsCopy().put("Aa", "shared");
        assertThat(original.get("Aa")).isEqualTo("shared");

        var copy = original.clone();
        copy.put("Aa", "copy tail");
        copy.put("BB", "copy head");
        assertThat(original.get("Aa")).isEqualTo("shared");
        assertThat(original.get("BB")).isEqualTo("second");
        assertThat(copy.get("Aa")).isEqualTo("copy tail");
        assertThat(copy.get("BB")).isEqualTo("copy head");
    }

    @Test
    @DisplayName("Empty buckets and later insertions remain independent")
    void emptyBucketsRemainIndependent() {
        var original = new HashTable();
        var copy = original.clone();
        copy.put("new", "value");

        assertThat(original.get("new")).isNull();
        assertThat(copy.get("new")).isEqualTo("value");
    }
}
