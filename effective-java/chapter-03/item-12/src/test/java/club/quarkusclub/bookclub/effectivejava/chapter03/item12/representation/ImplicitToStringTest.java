package club.quarkusclub.bookclub.effectivejava.chapter03.item12.representation;

import static org.assertj.core.api.Assertions.assertThat;

import club.quarkusclub.bookclub.effectivejava.chapter03.item12.format.PhoneNumber;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Item 12 - Common output operations invoke toString implicitly")
class ImplicitToStringTest {

    @Test
    @DisplayName("String concatenation includes the useful phone representation")
    void concatenationUsesToString() {
        PhoneNumber number = new PhoneNumber(313, 414, 533);

        assertThat("Failed to connect to " + number)
                .isEqualTo("Failed to connect to 313-414-0533");
    }

    @Test
    @DisplayName("System.out.println(Object) uses the phone representation")
    void printlnUsesToString() {
        PhoneNumber number = new PhoneNumber(313, 414, 533);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream previous = System.out;
        try (PrintStream captured = new PrintStream(output, true, StandardCharsets.UTF_8)) {
            System.setOut(captured);
            System.out.println(number);
        } finally {
            System.setOut(previous);
        }

        assertThat(output.toString(StandardCharsets.UTF_8))
                .isEqualTo("313-414-0533" + System.lineSeparator());
    }

    @Test
    @DisplayName("String.format with percent s uses the phone representation")
    void stringFormatUsesToString() {
        PhoneNumber number = new PhoneNumber(313, 414, 533);

        assertThat(String.format("Failed to connect to %s", number))
                .isEqualTo("Failed to connect to 313-414-0533");
    }

    @Test
    @DisplayName("printf with percent s writes the phone representation")
    void printfUsesToString() {
        PhoneNumber number = new PhoneNumber(313, 414, 533);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try (PrintStream printer = new PrintStream(output, true, StandardCharsets.UTF_8)) {
            printer.printf("Failed to connect to %s%n", number);
        }

        assertThat(output.toString(StandardCharsets.UTF_8))
                .isEqualTo("Failed to connect to 313-414-0533" + System.lineSeparator());
    }
}
