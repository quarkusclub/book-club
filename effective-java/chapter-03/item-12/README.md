# Item 12 - Always override toString

Small, executable examples from Effective Java, Chapter 3. PhoneNumber connects Item 10's
equality, Item 11's hashing, and Item 12's textual representation. Requires Java 25;
uses Quarkus 3.33.3.3, JUnit 5, and AssertJ.

## Concepts

| Package | Demonstration |
| --- | --- |
| `representation` | Inherited Object text versus useful text; concatenation, println, String.format and printf |
| `format` | PhoneNumber's specified format and leading zeros; Potion's deliberately unspecified description; accessors |
| `generated` | A useful generated record representation versus a natural phone-number representation |

PhoneNumber promises twelve characters in `XXX-YYY-ZZZZ` format and uses Locale.ROOT for
predictable digits. Its tests assert exact output. Potion promises a useful description,
while leaving exact formatting open; its tests check relevant information without freezing
punctuation. Both provide direct access to state, avoiding dependence on textual parsing.
Generated text is useful; choose a domain-specific representation when it communicates better.

## Run the demonstrations

From this directory, with JDK 25 selected:

```bash
./mvnw test
./mvnw -Dtest=PhoneNumberWithoutToStringTest,ImplicitToStringTest test
./mvnw -Dtest=PhoneNumberTest,PotionTest,PhoneNumberRecordTest test
```

From `effective-java/`:

```bash
mvn -pl chapter-03/item-12 test
mvn test
```

## Slides

[Portuguese](slides/index.html) · [English](slides/en.html). Both include speaker notes and
fetch implementations and tests directly from this module. Use N for the notes panel and S
for Reveal's speaker view.

Serve the repository root over HTTP:

```bash
jwebserver -p 8000 -d "$PWD"
```

Open <http://localhost:8000/effective-java/chapter-03/item-12/slides/>. Opening the deck via
`file://` cannot load the source snippets.

The presentation follows Effective Java, third edition, Item 12. The inherited behavior is
defined by the [Object.toString API](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/lang/Object.html#toString()).
