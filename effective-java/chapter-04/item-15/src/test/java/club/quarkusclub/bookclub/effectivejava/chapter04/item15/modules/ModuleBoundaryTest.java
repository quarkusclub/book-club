package club.quarkusclub.bookclub.effectivejava.chapter04.item15.modules;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import club.quarkusclub.bookclub.effectivejava.chapter04.item15.compiler.Compilation;

@DisplayName("Item 15 - JPMS exports constrain ordinary access on the module path")
class ModuleBoundaryTest {
    @TempDir Path temporary;

    @Test
    @DisplayName("a named client module can use a public type in an exported package")
    void exportedApi() throws Exception {
        Path sources = copySources();
        var result = Compilation.files(temporary.resolve("classes"), sources(sources),
                List.of("--module-source-path", sources.toString()));
        assertThat(result.successful()).isTrue();
    }

    @Test
    @DisplayName("a public type in a non-exported package is rejected across named modules")
    void hiddenImplementation() throws Exception {
        Path sources = copySources();
        useInternalType(sources);
        var result = Compilation.files(temporary.resolve("classes"), sources(sources),
                List.of("--module-source-path", sources.toString()));
        assertThat(result.successful()).isFalse();
        assertThat(result.errorCodes()).contains("compiler.err.package.not.visible");
    }

    @Test
    @DisplayName("without module descriptors the same public implementation is accessible on the classpath")
    void classpathHasNoExportsBoundary() throws Exception {
        Path sources = copySources();
        useInternalType(sources);
        var classpathSources = sources(sources).stream()
                .filter(p -> !p.getFileName().toString().equals("module-info.java")).toList();
        var result = Compilation.files(temporary.resolve("classes"), classpathSources, List.of());
        assertThat(result.successful()).isTrue();
    }

    private Path copySources() throws Exception {
        Path original = Path.of("src/test/resources/modules");
        Path copy = temporary.resolve("sources");
        for (Path source : sources(original)) {
            Path target = copy.resolve(original.relativize(source));
            Files.createDirectories(target.getParent());
            Files.copy(source, target);
        }
        return copy;
    }

    private List<Path> sources(Path directory) throws Exception {
        try (var paths = Files.walk(directory)) {
            return paths.filter(p -> p.toString().endsWith(".java")).sorted().toList();
        }
    }

    private void useInternalType(Path sources) throws Exception {
        Path client = sources.resolve("club.client/club/client/Client.java");
        Files.writeString(client, """
                package club.client;
                import club.tickets.internal.SeatAllocator;
                public final class Client {
                    public String issue() { return new SeatAllocator().allocate(); }
                }
                """);
    }
}
