# Item 15 - Minimize the accessibility of classes and members

Effective Java, third edition, Chapter 4. A session about protecting invariants and keeping
implementation details out of public contracts, with 26 executable tests and a 50-slide deck
in each language. Both decks include complete speaker notes and load code from this module.

## Run the examples

Use a full **JDK 25**, not a JRE: compilation tests need `ToolProvider.getSystemJavaCompiler()`.
This module follows the repository's Quarkus 3.33 LTS setup. The examples are plain Java and
JUnit/AssertJ tests; they do not require an application server or `@QuarkusTest`.

From this directory:

```bash
./mvnw test
```

Or from `effective-java/`:

```bash
mvn -pl chapter-04/item-15 test
```

Negative compilation examples are Java text blocks inside tests, not broken files in
`src/main/java`. The tests assert rejection and the expected javac diagnostic code. The JPMS
fixtures live in `src/test/resources/modules/` and are compiled into JUnit temporary directories.
They intentionally do not turn the Quarkus application into a named Java module.

## Present the slides

From the repository root:

```bash
jwebserver -p 8000 -d "$PWD"
```

- [Portuguese deck](slides/index.html): `http://localhost:8000/effective-java/chapter-04/item-15/slides/`
- [English deck](slides/en.html): `http://localhost:8000/effective-java/chapter-04/item-15/slides/en.html`
- Press **S** for Reveal's separate speaker view, or **N** to show notes in the current window.
- Serve the repository root over HTTP. Opening an HTML file directly cannot load the snippets.
- Reveal.js and fonts use the same external CDN setup as the existing session; presentation
  requires network access. The local examples and Maven tests do not need a running web server.

## Presenter route

Plan for approximately 30 minutes. Most slides are short implementation/test pairs. Ask for
a prediction before showing the test, then discuss why the observed result matters for API design.

| Time | Slides | Topic | Evidence |
| --- | --- | --- | --- |
| 0-5 min | 1-8 | Public fields bypass invariants; private state enables validated operations | `InventoryTest` (4 tests) |
| 5-10 min | 9-15 | Public facade, package-private helper, private nested type, same-package tests | `TicketOfficeTest` (3), `AccessCompilationTest` |
| 10-18 min | 16-28 | Four access levels, subpackages, protected receivers, overriding | `AccessRulesTest` (1), `AccessCompilationTest` (10 total) |
| 18-24 min | 29-38 | Final arrays, defensive copies, unmodifiable views and snapshots | `FormatsTest` (3), `FormatCatalogTest` (2) |
| 24-28 min | 39-45 | Named modules, exported packages, classpath control | `ModuleBoundaryTest` (3) |
| 28-30 min | 46-50 | Review exercise, live command, references, questions | Reuse the examples above |

For a longer live session, run a single group from this directory:

```bash
./mvnw -Dtest=InventoryTest,TicketOfficeTest test
./mvnw -Dtest=AccessRulesTest,AccessCompilationTest test
./mvnw -Dtest=FormatsTest,FormatCatalogTest test
./mvnw -Dtest=ModuleBoundaryTest test
```

## What the examples establish

- `encapsulation/`: a public field permits invalid state; `Inventory` controls valid sequential
  state changes. Private fields alone do **not** guarantee thread safety.
- `facade/`: clients use `TicketOffice`; a same-package test can exercise `SeatFormatter`
  without making it public. `Ticket` demonstrates a private static nested implementation.
  Since the formatter currently serves one class, nesting it would be a reasonable further reduction.
- `access/` and `compiler/`: package access includes protected members; subpackages have no
  inherited access; an external subclass must respect protected instance-receiver restrictions;
  an override cannot narrow access; public interface methods need public implementations.
- `arrays/`: a public final array and a getter returning its storage both leak mutability.
  A defensive array copy and an unmodifiable list of immutable strings avoid that leak.
- `views/`: an unmodifiable view observes later internal changes, while a snapshot does not.
  Neither approach makes arbitrary mutable elements immutable.
- `modules/`: a public type in a non-exported package is unavailable to another named module
  under ordinary access rules. The control case omits descriptors and compiles the same
  implementation on the classpath. A Maven module is not a JPMS module.

Design recommendations, such as preserving the freedom to change implementations and avoiding
public API added only for testing, are motivated by these examples. They are not claims that
unit tests can prove future compatibility. Narrowing an already published API may break clients.
Reflection overrides, serialization compatibility, and concurrent inventory updates are outside
the executable examples in this session.

## References

- Joshua Bloch, *Effective Java*, third edition, Item 15.
- [JLS 25, 6.6 - Access Control](https://docs.oracle.com/javase/specs/jls/se25/html/jls-6.html#jls-6.6).
- [JLS 25, 8.4.8.3 - Requirements in Overriding and Hiding](https://docs.oracle.com/javase/specs/jls/se25/html/jls-8.html#jls-8.4.8.3).
- [JLS 25, 7.7.2 - Exported and Opened Packages](https://docs.oracle.com/javase/specs/jls/se25/html/jls-7.html#jls-7.7.2).
- [JavaCompiler API](https://docs.oracle.com/en/java/javase/25/docs/api/java.compiler/javax/tools/JavaCompiler.html).
- [Quarkus testing guide](https://quarkus.io/guides/getting-started-testing).
