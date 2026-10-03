package club.quarkusclub.bookclub.effectivejava.chapter03.item12.format;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Item 12 - An unspecified description remains useful to humans")
class PotionTest {

    @Test
    @DisplayName("the description includes relevant state without freezing its exact punctuation")
    void descriptionIncludesRelevantState() {
        Potion potion = new Potion(9, "love", "turpentine", "india ink");

        assertThat(potion.toString()).contains("9", "love", "turpentine", "india ink");
    }

    @Test
    @DisplayName("component accessors provide state independently of the description format")
    void accessorsAvoidDependingOnText() {
        Potion potion = new Potion(9, "love", "turpentine", "india ink");

        assertThat(potion.number()).isEqualTo(9);
        assertThat(potion.type()).isEqualTo("love");
        assertThat(potion.smell()).isEqualTo("turpentine");
        assertThat(potion.look()).isEqualTo("india ink");
    }
}
