# Quarkus Club Book Club

Code and slides for the [Quarkus Club](https://quarkusclub.github.io) Book Club: a technical
reading group that goes through one book, one Item (or chapter) at a time. Every session ships
two things: a **reveal.js deck**, in Portuguese and English, and **code that compiles and is
tested**, proving every claim the slides make.

This sits alongside the [`workshops`](https://github.com/quarkusclub/workshops) repository as a
second track of hands-on material from the same community. See the club's
[Book Club page](https://quarkusclub.github.io/bookclub) for the full list of sessions, including
ones that are video-based rather than code-based.

## Books

| Book | Sessions so far | |
| --- | --- | --- |
| Effective Java | 3 (Chapter 3, Items 10–12) | [`effective-java/README.md`](effective-java/README.md) |

Each book's own `README.md` has the full, chapter-grouped session table; this one stays a
one-line-per-book index so it doesn't need editing every week. The live, filterable version is
[`index.html`](index.html).

## Repository layout

```
.
├── LICENSE                       Apache License 2.0
├── assets/
│   ├── deck.css                  the deck theme, shared with quarkusclub/workshops
│   ├── deck.js                   snippet loading, copy buttons, notes, Reveal config
│   ├── favicon.ico, logo.png     the Quarkus Club mark
│   └── quarkus-logo*.svg         Quarkus marks, light and dark
└── effective-java/
    ├── README.md                 this book's session table, grouped by chapter
    ├── pom.xml                   aggregator, one module per item covered so far
    └── chapter-03/
        ├── item-10/
        │   ├── slides/           index.html (PT) and en.html (EN)
        │   ├── src/              the Quarkus + Java 25 module, main and test
        │   └── pom.xml
        ├── item-11/               hashCode contract, distribution, and caching
        └── item-12/               useful toString representations and format contracts
```

Each book gets its own top-level directory (`effective-java/`, and whatever comes next), its own
`README.md` with the full session table, and each item discussed gets its own Maven module at
`<book>/chapter-NN/item-NN/`. A book's directory name is the book's own name in English. Slides
ship in both Portuguese (the language the sessions are held in) and English; everything else -
code, commit messages, this file - is English only.

## How the decks work

`assets/deck.css` **is the contract**, copied from
[`quarkusclub/workshops`](https://github.com/quarkusclub/workshops): a deck is pure composition,
declaring `<section>` elements with predefined classes, never per-deck CSS. See that repository's
`SLIDES.md` for the full class vocabulary and authoring guide - it applies here unchanged.

Code slides fetch straight from the real project files via `data-src`, so a slide can never show
code that does not compile. Every concept in this repository's slides is followed by a matching
`.code.small` slide showing the **test that proves it**, not just the implementation.

### Presenting

```bash
jwebserver -p 8000 -d "$PWD"     # from the repository root, JDK 18+
```

Then open <http://localhost:8000/effective-java/chapter-03/item-10/slides/>. Opening the HTML
file directly from disk will not work: the deck reads code over `fetch`, which needs a real
origin.

## Working on the code

```bash
cd effective-java
mvn clean test              # builds and tests every item module
mvn -pl chapter-03/item-10 test
```

Requires JDK 25. Two ways to pin it, pick whichever you already use:

- [mise](https://mise.jdx.dev): `mise.toml` pins the version, and its `enter` hook also points
  `core.hooksPath` at this repository's `.githooks/` the moment you `cd` in (after a one-time
  `mise trust`).
- [SDKMAN](https://sdkman.io): `.sdkmanrc` pins the same version; run `sdk env` to switch to it
  (or enable `sdkman_auto_env` to do it automatically). SDKMAN has no equivalent to mise's `enter`
  hook, so it cannot wire up `core.hooksPath` for you - run
  `git config core.hooksPath .githooks` once yourself if you want the local guard below without
  installing mise.

Either way, `.github/workflows/attribution-ai-guard.yml` runs in CI regardless of local setup, so
no commit, PR title, or PR description can mention or credit an AI tool.

## Adding a session

1. Create `<book>/chapter-NN/item-NN/` with the Quarkus CLI, matching the package convention
   `club.quarkusclub.bookclub.<book>.chapterNN.itemNN.<concept>`.
2. Add the new module to `<book>/pom.xml`.
3. Create `slides/index.html` and `en.html`, composing classes from `assets/deck.css`. Keep both
   languages structurally identical - the same number of slides, in the same order.
4. Add a row to `<book>/README.md`'s session table, under the right chapter, and to the hub page
   at [`index.html`](index.html). Bump the "Sessions so far" count in the table above if this is a
   new book.

## Credits and licence

Licensed under the Apache License 2.0 - see [`LICENSE`](LICENSE). The Quarkus name and logo are
trademarks of Red Hat, Inc. Quarkus Club is an independent user group and is not affiliated with,
nor endorsed by, Red Hat.
