# Item 11 - Always override hashCode when you override equals

Small, executable examples from Effective Java, Chapter 3. PhoneNumber continues the
value-equality example from Item 10. Requires Java 25; uses Quarkus 3.33.3.3, JUnit 5,
and AssertJ. The examples run through tests, without a business application or timing benchmark.

## Concepts

| Package | Demonstration |
| --- | --- |
| `contract` | Correct PhoneNumber hashing, permitted collisions, and broken HashSet/HashMap lookups |
| `distribution` | Constant hashes remain semantically valid but concentrate candidates; String collisions |
| `objects` | Primitive hash methods, canonical state, Objects.hash varargs, boxing and unboxing |
| `caching` | Lazy caching, the zero sentinel, and an immutable Document with a calculation counter |

The missing override retains identity hashing. Its lookup failure is demonstrated with a
separate broken implementation using controlled hashes, so the test does not rely on random
identity hashes. The bucket example models OpenJDK's spreading for a table of sixteen slots;
it does not inspect private HashMap storage or promise that algorithm as an API contract.

Caching tests count sequential calculations rather than elapsed time. Document defensively
copies its list of immutable strings. The counters are demonstration instrumentation; these
lazy caches do not guarantee exactly one calculation under concurrent calls. A real zero hash
can be recalculated, without violating the contract. PhoneNumber's cheap calculation probably
does not justify caching.

## Run the demonstrations

From this directory, with JDK 25 selected:

```bash
./mvnw test
./mvnw -Dtest=BrokenHashCodeTest,PhoneNumberTest test
./mvnw -Dtest=ConstantHashPhoneNumberTest,DocumentTest,CachedPhoneNumberTest test
```

From `effective-java/`:

```bash
mvn -pl chapter-03/item-11 test
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

Open <http://localhost:8000/effective-java/chapter-03/item-11/slides/>. The closing slide links
to Item 12. Opening the deck via `file://` cannot load the source snippets.

## Supporting references

The presentation follows Effective Java, third edition, Item 11. Details are checked against
the [Object.hashCode contract](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/lang/Object.html#hashCode()),
[Objects.hash API](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/Objects.html#hash(java.lang.Object...)),
[OpenJDK HashMap implementation](https://github.com/openjdk/jdk25u/blob/master/src/java.base/share/classes/java/util/HashMap.java),
and [CERT's historical collision advisory](https://www.kb.cert.org/vuls/id/903934/).
The [Integer](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/lang/Integer.html#hashCode())
and [String](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/lang/String.html#hashCode())
APIs illustrate explicitly specified hash results.
