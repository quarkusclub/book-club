package club.quarkusclub.bookclub.effectivejava.chapter04.item15.compiler;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Path;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

@DisplayName("Item 15 - The compiler enforces Java access rules")
class AccessCompilationTest {
    @TempDir Path output;

    @Test
    @DisplayName("an external client can use the public facade")
    void publicFacade() throws Exception {
        var result = Compilation.source(output, "Client", """
                package client;
                import club.quarkusclub.bookclub.effectivejava.chapter04.item15.facade.TicketOffice;
                public class Client { String issue() { return new TicketOffice().issue(2, 7); } }
                """);
        assertThat(result.successful()).isTrue();
    }

    @Test
    @DisplayName("an external client cannot import a package-private implementation")
    void packagePrivateType() throws Exception {
        var result = Compilation.source(output, "Client", """
                package client;
                import club.quarkusclub.bookclub.effectivejava.chapter04.item15.facade.SeatFormatter;
                public class Client { String issue() { return SeatFormatter.format(2, 7); } }
                """);
        assertThat(result.successful()).isFalse();
        assertThat(result.errorCodes()).contains("compiler.err.not.def.public.cant.access");
    }

    @Test
    @DisplayName("private members are inaccessible even to another same-package class")
    void privateMember() throws Exception {
        var result = Compilation.source(output, "Client", """
                package club.quarkusclub.bookclub.effectivejava.chapter04.item15.access;
                public class Client { String read() { return new AccessRules().privateCode(); } }
                """);
        assertThat(result.successful()).isFalse();
        assertThat(result.errorCodes()).contains("compiler.err.report.access");
    }

    @Test
    @DisplayName("a subpackage does not inherit access to its parent package")
    void subpackageIsDifferent() throws Exception {
        var result = Compilation.source(output, "Client", """
                package club.quarkusclub.bookclub.effectivejava.chapter04.item15.access.client;
                import club.quarkusclub.bookclub.effectivejava.chapter04.item15.access.AccessRules;
                public class Client { String read() { return new AccessRules().packageCode(); } }
                """);
        assertThat(result.successful()).isFalse();
        assertThat(result.errorCodes()).contains("compiler.err.not.def.public.cant.access");
    }

    @Test
    @DisplayName("an unrelated external caller cannot access a protected member")
    void unrelatedProtected() throws Exception {
        var result = Compilation.source(output, "Client", """
                package client;
                import club.quarkusclub.bookclub.effectivejava.chapter04.item15.access.AccessRules;
                public class Client { String read() { return new AccessRules().protectedCode(); } }
                """);
        assertThat(result.successful()).isFalse();
        assertThat(result.errorCodes()).contains("compiler.err.report.access");
    }

    @Test
    @DisplayName("an external subclass can access protected members through a subclass receiver")
    void subclassReceiver() throws Exception {
        var result = Compilation.source(output, "Child", """
                package client;
                import club.quarkusclub.bookclub.effectivejava.chapter04.item15.access.AccessRules;
                public class Child extends AccessRules {
                    String read(Child other) { return other.protectedCode(); }
                }
                """);
        assertThat(result.successful()).isTrue();
    }

    @Test
    @DisplayName("an external subclass cannot access a protected member through a base-typed receiver")
    void baseReceiver() throws Exception {
        var result = Compilation.source(output, "Child", """
                package client;
                import club.quarkusclub.bookclub.effectivejava.chapter04.item15.access.AccessRules;
                public class Child extends AccessRules {
                    String read(AccessRules other) { return other.protectedCode(); }
                }
                """);
        assertThat(result.successful()).isFalse();
        assertThat(result.errorCodes()).contains("compiler.err.report.access");
    }

    @Test
    @DisplayName("an override cannot narrow a public method to protected")
    void narrowOverride() throws Exception {
        var result = Compilation.source(output, "Child", """
                package client;
                import club.quarkusclub.bookclub.effectivejava.chapter04.item15.access.AccessRules;
                public class Child extends AccessRules {
                    @Override protected String publicCode() { return "hidden"; }
                }
                """);
        assertThat(result.successful()).isFalse();
        assertThat(result.errorCodes()).contains("compiler.err.override.weaker.access");
    }

    @Test
    @DisplayName("an interface implementation cannot give its public method package access")
    void interfaceVisibility() throws Exception {
        var result = Compilation.source(output, "Worker", """
                package client;
                interface Job { void run(); }
                public class Worker implements Job { void run() {} }
                """);
        assertThat(result.successful()).isFalse();
        assertThat(result.errorCodes()).contains("compiler.err.override.weaker.access");
    }

    @Test
    @DisplayName("a public interface implementation compiles")
    void publicImplementation() throws Exception {
        var result = Compilation.source(output, "Worker", """
                package client;
                interface Job { void run(); }
                public class Worker implements Job { public void run() {} }
                """);
        assertThat(result.successful()).isTrue();
    }
}
