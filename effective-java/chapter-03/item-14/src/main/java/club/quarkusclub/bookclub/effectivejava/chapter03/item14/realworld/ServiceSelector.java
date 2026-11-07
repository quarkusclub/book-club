package club.quarkusclub.bookclub.effectivejava.chapter03.item14.realworld;

import java.util.Collection;
import java.util.Optional;

/**
 * Reproduz {@code WorkflowUtils.loadFirst} do SDK Java do Open Workflow, sem o
 * {@code ServiceLoader}: apenas a ordenação natural decide qual implementação vence.
 */
public final class ServiceSelector {

    private ServiceSelector() {
    }

    public static <T extends Comparable<? super T>> Optional<T> loadFirst(Collection<T> services) {
        return services.stream().sorted().findFirst();
    }
}
