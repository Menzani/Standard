package eu.menzani.lang;

public class StringBuildersTest {
    public void replace() {
        StringBuilder builder = new StringBuilder("abc");
        StringBuilders.replace(builder, new TargetReplacement('b', "bb"), new TargetReplacement('c', "cc"));
        Assert.equal(builder.toString(), "abbcc");
    }

    public void replaceInRange() {
        StringBuilder builder = new StringBuilder("abcabc");
        StringBuilders.replace(builder, 0, 3, new TargetReplacement('b', "bb"), new TargetReplacement('c', "cc"));
        Assert.equal(builder.toString(), "abbccabc");
    }
}
