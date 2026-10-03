package club.quarkusclub.bookclub.effectivejava.chapter03.item12.generated;

import static org.assertj.core.api.Assertions.assertThat;

import club.quarkusclub.bookclub.effectivejava.chapter03.item12.format.PhoneNumber;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Item 12 - Generated text is useful but domain text can be more natural")
class PhoneNumberRecordTest {

    @Test
    @DisplayName("the generated record representation names all its components")
    void generatedRepresentationShowsComponents() {
        PhoneNumberRecord number = new PhoneNumberRecord((short) 313, (short) 414, (short) 533);

        assertThat(number.toString())
                .startsWith("PhoneNumberRecord[")
                .contains("areaCode=313", "prefix=414", "lineNumber=533");
    }

    @Test
    @DisplayName("the same numeric components can have a natural domain representation")
    void domainRepresentationIsMoreNatural() {
        PhoneNumberRecord generated = new PhoneNumberRecord((short) 313, (short) 414, (short) 533);
        PhoneNumber domain = new PhoneNumber(
                generated.areaCode(), generated.prefix(), generated.lineNumber());

        assertThat(generated.toString()).contains("lineNumber=533");
        assertThat(domain.toString()).isEqualTo("313-414-0533");
    }
}
