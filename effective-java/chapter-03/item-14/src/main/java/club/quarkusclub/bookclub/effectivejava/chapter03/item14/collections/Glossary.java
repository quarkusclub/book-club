package club.quarkusclub.bookclub.effectivejava.chapter03.item14.collections;

import club.quarkusclub.bookclub.effectivejava.chapter03.item14.contract.CaseInsensitiveString;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public final class Glossary {

    private final SortedSet<CaseInsensitiveString> terms = new TreeSet<>();

    public boolean add(String term) {
        return terms.add(new CaseInsensitiveString(term));
    }

    public List<String> terms() {
        return terms.stream().map(CaseInsensitiveString::value).toList();
    }

    public String first() {
        return terms.first().value();
    }
}
