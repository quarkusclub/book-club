package club.quarkusclub.bookclub.effectivejava.chapter03.item13.contract;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Cloneable does not publish clone or bypass access and construction rules")
class CloneableDemoTest {
    @TempDir
    Path output;

    @Test
    @DisplayName("A mixin exposes an operation while Cloneable declares none")
    void mixinExposesOperation() {
        CloneableDemo.Describable value = () -> "Book Club";

        assertThat(value.describe()).isEqualTo("Book Club");
        assertThat(Cloneable.class.getDeclaredMethods()).isEmpty();
    }

    @Test
    @DisplayName("The marker provides neither a public clone nor reflective access")
    void markerDoesNotGrantAccess() throws ReflectiveOperationException {
        var method = Object.class.getDeclaredMethod("clone");
        var value = new CloneableDemo.MarkerOnly();

        assertThatThrownBy(() -> value.getClass().getMethod("clone"))
                .isInstanceOf(NoSuchMethodException.class);
        assertThatThrownBy(() -> method.invoke(value)).isInstanceOf(IllegalAccessException.class);
    }

    @Test
    @DisplayName("Object clone rejects a receiver without Cloneable")
    void missingMarkerRejectsClone() {
        assertThatThrownBy(CloneableDemo::unsupportedCopy)
                .isInstanceOf(CloneNotSupportedException.class);
    }

    @Test
    @DisplayName("Java 25 rejects a protected clone call from a client")
    void protectedCloneDoesNotCompile() throws IOException {
        assertThat(compile("CloneClient")).contains("compiler.err.report.access");
    }

    @Test
    @DisplayName("Java 25 rejects replacing a final reference inside a copy method")
    void finalRepairDoesNotCompile() throws IOException {
        assertThat(compile("FinalRepair")).contains("compiler.err.cant.assign.val.to.var");
    }

    private List<String> compile(String name) throws IOException {
        Path source = output.resolve(name + ".java");
        try (var input = getClass().getResourceAsStream("/compilation/" + name + ".java.txt")) {
            assertThat(input).isNotNull();
            Files.copy(input, source);
        }
        var compiler = ToolProvider.getSystemJavaCompiler();
        var diagnostics = new DiagnosticCollector<JavaFileObject>();
        try (var files = compiler.getStandardFileManager(diagnostics, null, null)) {
            boolean compiled = compiler.getTask(null, files, diagnostics,
                    List.of("--release", "25", "-proc:none", "-d", output.toString()),
                    null, files.getJavaFileObjects(source)).call();
            assertThat(compiled).isFalse();
        }
        return diagnostics.getDiagnostics().stream()
                .filter(diagnostic -> diagnostic.getKind() == Diagnostic.Kind.ERROR)
                .map(Diagnostic::getCode).toList();
    }
}
