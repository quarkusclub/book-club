package club.quarkusclub.bookclub.effectivejava.chapter04.item15.access;

public class AccessRules {
    private String privateCode() { return "private"; }

    String packageCode() { return privateCode(); }

    protected String protectedCode() { return "protected"; }

    public String publicCode() { return "public"; }
}
