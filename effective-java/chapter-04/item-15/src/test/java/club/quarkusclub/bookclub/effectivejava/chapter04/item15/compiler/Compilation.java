package club.quarkusclub.bookclub.effectivejava.chapter04.item15.compiler;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.ToolProvider;

public final class Compilation {
    private Compilation() {}

    public record Result(boolean successful, List<String> errorCodes) {}

    public static Result source(Path output, String className, String source) throws IOException {
        var file = new SimpleJavaFileObject(URI.create("string:///" + className + ".java"),
                JavaFileObject.Kind.SOURCE) {
            @Override public CharSequence getCharContent(boolean ignoreEncodingErrors) {
                return source;
            }
        };
        return compile(output, List.of(file), List.of("--class-path", "target/classes"));
    }

    public static Result files(Path output, List<Path> sources, List<String> options) throws IOException {
        var compiler = Objects.requireNonNull(ToolProvider.getSystemJavaCompiler(), "A full JDK is required");
        try (var manager = compiler.getStandardFileManager(null, Locale.ROOT, StandardCharsets.UTF_8)) {
            var files = new ArrayList<JavaFileObject>();
            manager.getJavaFileObjectsFromPaths(sources).forEach(files::add);
            return compile(output, files, options);
        }
    }

    private static Result compile(Path output, List<JavaFileObject> sources, List<String> extra)
            throws IOException {
        Files.createDirectories(output);
        var compiler = Objects.requireNonNull(ToolProvider.getSystemJavaCompiler(), "A full JDK is required");
        var diagnostics = new DiagnosticCollector<JavaFileObject>();
        var options = new ArrayList<>(List.of("--release", "25", "-proc:none", "-d", output.toString()));
        options.addAll(extra);
        try (var manager = compiler.getStandardFileManager(diagnostics, Locale.ROOT, StandardCharsets.UTF_8)) {
            boolean successful = compiler.getTask(new StringWriter(), manager, diagnostics,
                    options, null, sources).call();
            var errors = diagnostics.getDiagnostics().stream()
                    .filter(d -> d.getKind() == Diagnostic.Kind.ERROR)
                    .map(Diagnostic::getCode).toList();
            return new Result(successful, errors);
        }
    }
}
