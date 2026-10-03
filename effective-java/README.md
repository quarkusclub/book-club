# Effective Java

Joshua Bloch, 3rd edition. 90 items across 12 chapters; the club covers one per session, in
Portuguese, with a Quarkus module and a bilingual reveal.js deck proving every claim.

## Sessions

### Chapter 3 - Methods Common to All Objects

| Item | Topic | Slides | Code |
| --- | --- | --- | --- |
| 10 | The `equals()` contract | [PT](chapter-03/item-10/slides/index.html) · [EN](chapter-03/item-10/slides/en.html) | [item-10](chapter-03/item-10/) |
| 11 | Always override `hashCode` when you override `equals` | [PT](chapter-03/item-11/slides/index.html) · [EN](chapter-03/item-11/slides/en.html) | [item-11](chapter-03/item-11/) |
| 12 | Always override `toString` | [PT](chapter-03/item-12/slides/index.html) · [EN](chapter-03/item-12/slides/en.html) | [item-12](chapter-03/item-12/) |

## Building

```bash
cd effective-java
mvn clean test              # every session module in this book
mvn -pl chapter-03/item-10 test
```

## Adding a session to this book

1. Create `chapter-NN/item-NN/` with the Quarkus CLI, package
   `club.quarkusclub.bookclub.effectivejava.chapterNN.itemNN.<concept>`.
2. Add `<module>chapter-NN/item-NN</module>` to this directory's `pom.xml`.
3. Create `slides/index.html` and `en.html` under it (see the repository root `README.md` for the
   deck conventions).
4. Add a row to the table above, under the right chapter (start a new `###` heading for a chapter
   that doesn't have one yet).
5. Add the same item to `index.html`'s hub page and, if it opens a new chapter there too, to the
   root `README.md`'s book list.
