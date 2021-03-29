package eu.menzani.lang;

public class TargetReplacement {
    private final char target;
    private final CharSequence replacement;

    public TargetReplacement(char target, CharSequence replacement) {
        this.target = target;
        this.replacement = replacement;
    }

    public char getTarget() {
        return target;
    }

    public CharSequence getReplacement() {
        return replacement;
    }
}
