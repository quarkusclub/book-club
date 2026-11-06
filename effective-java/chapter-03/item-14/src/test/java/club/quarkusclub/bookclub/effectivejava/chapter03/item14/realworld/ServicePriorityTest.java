package club.quarkusclub.bookclub.effectivejava.chapter03.item14.realworld;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ServicePriority before and after open-workflow-specification/sdk-java#1799")
class ServicePriorityTest {

    record LegacyService(String name, int priority) implements SubtractionServicePriority {
    }

    record Service(String name, int priority) implements ServicePriority {
    }

    @Test
    @DisplayName("Subtraction overflows and loadFirst picks the default instead of the winner")
    void subtractionPicksTheWrongService() {
        var standard = new LegacyService("default", SubtractionServicePriority.DEFAULT_PRIORITY);
        var winner = new LegacyService("always-wins", Integer.MIN_VALUE);

        assertThat(ServiceSelector.loadFirst(List.of(standard, winner))).contains(standard);
        assertThat(ServiceSelector.loadFirst(List.of(winner, standard))).contains(standard);
    }

    @Test
    @DisplayName("Subtraction breaks transitivity: MIN < 0 and 0 < 1000, yet MIN > 1000")
    void subtractionBreaksTransitivity() {
        var min = new LegacyService("min", Integer.MIN_VALUE);
        var zero = new LegacyService("zero", 0);
        var thousand = new LegacyService("thousand", 1000);

        assertThat(min.compareTo(zero)).isNegative();
        assertThat(zero.compareTo(thousand)).isNegative();
        assertThat(min.compareTo(thousand)).isPositive();
    }

    @Test
    @DisplayName("Integer.compare lets the lowest priority value win")
    void integerComparePicksTheWinner() {
        var standard = new Service("default", ServicePriority.DEFAULT_PRIORITY);
        var winner = new Service("always-wins", Integer.MIN_VALUE);

        assertThat(ServiceSelector.loadFirst(List.of(standard, winner))).contains(winner);
        assertThat(ServiceSelector.loadFirst(List.of(winner, standard))).contains(winner);
    }

    @Test
    @DisplayName("The comparingInt comparator orders exactly like the natural ordering")
    void comparatorMatchesNaturalOrdering() {
        var services = List.<ServicePriority>of(
                new Service("max", Integer.MAX_VALUE),
                new Service("default", ServicePriority.DEFAULT_PRIORITY),
                new Service("min", Integer.MIN_VALUE));
        var natural = new ArrayList<>(services);
        var byComparator = new ArrayList<>(services);

        natural.sort(null);
        byComparator.sort(ServicePriority.BY_PRIORITY);

        assertThat(byComparator).containsExactlyElementsOf(natural);
        assertThat(natural).extracting(ServicePriority::priority)
                .containsExactly(Integer.MIN_VALUE, ServicePriority.DEFAULT_PRIORITY, Integer.MAX_VALUE);
    }
}
