package sonnet.junit;

public class Strings {
    public Strings() {
    }
    int length( String s ) { return s.length(); }                                   // Will return the length of String s. e.g.  "Cat" returns 3
    String concatenate(String s1, String s2 ) { return s1+s2; }                        // Will concatenate String s1 to s2. e.g. "Germ" and "any"  returns "Germany"
    String remove(String s1, String s2 ) { return s2.replace(s1, ""); }  //  Will remove s1 from s2. e.g. "Out" and "Outstanding"  returns "standing"
    boolean contains(String s1, String s2 ) { return s2.contains(s1); }             // Will return true if s1 is contained in s2.  e.g. "Cat" and "Caterpillar"  returns true
}
