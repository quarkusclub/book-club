# Item 13 — Override clone judiciously

Six small examples grouped by concept, matching Items 11 and 12. Uses Java 25 and the
existing Quarkus 3.33.3.3, JUnit 5 and AssertJ setup. The examples themselves require only the JDK.

## Run

From this directory with JDK 25 selected:

```bash
./mvnw clean test
sh scripts/run_demos.sh
sh scripts/run_demos.sh Stack
./mvnw -Dtest=StackTest,HashTableTest test
```

From `effective-java/`: `mvn -pl chapter-03/item-13 test`, or `mvn test` for all sessions.
Each class also runs directly, for example:

```bash
java -cp target/classes club.quarkusclub.bookclub.effectivejava.chapter03.item13.contract.PhoneNumber
```

| Source file | Teaching purpose |
| --- | --- |
| `contract/CloneableDemo.java` | Mixin, marker, protected access, reflection and unsupported cloning |
| `contract/PhoneNumber.java` | Simple clone, covariant return, equality and skipped construction |
| `inheritance/InheritanceExample.java` | One base and one child: constructor copying versus `super.clone()` |
| `mutable/Stack.java` | Shared-array corruption, storage repair and shared mutable payloads |
| `mutable/HashTable.java` | Bucket-only copying versus iterative reconstruction of all chain nodes |
| `alternatives/Account.java` | Copy constructor and factory retaining an independent final list |

Each has one matching test class. Negative compilation fixtures end in `.java.txt`;
`CloneableDemoTest` invokes the real Java 25 compiler and verifies their rejection. Broken
variants are explicitly named `shallowCopy`, `shallowBucketsCopy` and `brokenClone`.
The counter in PhoneNumber is sequential teaching instrumentation. The examples do not
promise thread safety, and no demonstration depends on an unpredictable race.

## Present

[Portuguese](slides/index.html) · [English](slides/en.html) ·
[Presenter guide](PRESENTER_NOTES.md) · [Portuguese study notes](STUDY_NOTES.md).

The deck follows the same sequence as Items 11 and 12: short concept slides, source excerpts
and matching test slides. Both languages contain 28 sections, include translated speaker notes,
fetch code from real files and use the shared CSS and initialization unchanged. DDD and other deeper explanations stay in the notes without
adding demonstration entities.

From the repository root:

```bash
jwebserver -p 8000 -d "$PWD"
```

Open <http://localhost:8000/effective-java/chapter-03/item-13/slides/> or append `en.html`.
Use right/left to navigate, N for notes, S for presenter view and T to change theme. Source fetching requires HTTP; opening the deck through `file://` fails.
Reveal.js and fonts use the same external CDNs as previous sessions.

PDF exports: [Portuguese](slides/item-13-pt.pdf) · [English](slides/item-13-en.pdf).
To export again, open the deck with `?print-pdf&theme=light`, wait for snippets to load, and print to PDF
with backgrounds enabled and browser headers/footers disabled. The shared print theme uses
a light background for paper.

```bash
python3 scripts/validate_slides.py
grep -c '<section' slides/index.html slides/en.html
```

The validator checks matching section structure, source paths and line ranges, valid
excerpts, and coverage of every implementation and test file.

## References and fidelity to the book

The complete Item 13 was read in the [PDF supplied for this session][book]: printed pages
58–65, PDF pages 79–86. No book copy was found in the repository. The presentation preserves
the progression from the marker and contract to simple state, inheritance, mutable internals,
implementation constraints and alternatives. Explanations and tests are original; no long
passages are transcribed. The official companion directory contains PhoneNumber and Stack,
not a HashTable implementation.

| Item topic | Local example | Consulted source | Relationship |
| --- | --- | --- | --- |
| Mixin, marker and protected access | CloneableDemo | [Item 13][book], [Cloneable API][marker] | Direct topic; own minimal example |
| Contract and inheritance convention | PhoneNumber, InheritanceExample | [Object.clone][clone], [Item 13][book] | API verification; own hierarchy |
| Simple fields and covariant return | PhoneNumber | [Author source][phone], [JLS overriding][override] | Adaptation; constructor counter added |
| Mutable backing array and final constraint | Stack | [Author source][stack], [Item 13][book] | Adaptation; explicit broken variant added |
| Shared array payloads | StackTest | [JLS array members][arrays] | Additional clarification |
| Chained bucket reconstruction | HashTable | [Item 13][book] | Own reduced executable adaptation |
| Overridable calls, exceptions, thread safety | Existing examples and notes | [Item 13][book], [JLS memory model][threads] | Direct topics; no additional hierarchy or race demo |
| Immutable values and copying alternatives | Account | [Item 13][book], [Bloch interview][interview] | Own example; interview predates the third edition |
| Reflection and modules in Java 25 | CloneableDemoTest | [AccessibleObject API][reflection] | Current platform clarification |
| Entity identity and aggregates | Study notes only | [Evans DDD reference][ddd] | Supplementary engineering application |

[book]: https://kea.nu/files/textbooks/new/Effective%20Java%20%282017%2C%20Addison-Wesley%29.pdf#page=79
[marker]: https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/lang/Cloneable.html
[clone]: https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/lang/Object.html#clone()
[phone]: https://github.com/jbloch/effective-java-3e-source-code/blob/master/src/effectivejava/chapter3/item13/contract/PhoneNumber.java
[stack]: https://github.com/jbloch/effective-java-3e-source-code/blob/master/src/effectivejava/chapter3/item13/mutable/Stack.java
[override]: https://docs.oracle.com/javase/specs/jls/se25/html/jls-8.html#jls-8.4.8.3
[arrays]: https://docs.oracle.com/javase/specs/jls/se25/html/jls-10.html#jls-10.7
[threads]: https://docs.oracle.com/javase/specs/jls/se25/html/jls-17.html#jls-17.4.5
[reflection]: https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/lang/reflect/AccessibleObject.html#trySetAccessible()
[interview]: https://www.artima.com/articles/josh-bloch-on-design
[ddd]: https://www.domainlanguage.com/wp-content/uploads/2016/05/DDD_Reference_2015-03.pdf
