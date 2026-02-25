package br.com.pointel.jarch.mage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WizStringTest {

    @Test
    public void testGetSimilarityWords() {
        // Exact match
        Assertions.assertEquals(1.0, WizString.getSimilarityWords("hello world", "hello world"), 0.001);
        
        // Nulls
        Assertions.assertEquals(1.0, WizString.getSimilarityWords((String)null, (String)null), 0.001);
        Assertions.assertEquals(0.0, WizString.getSimilarityWords(null, "a"), 0.001);
        Assertions.assertEquals(0.0, WizString.getSimilarityWords("a", null), 0.001);
        
        // Empty
        Assertions.assertEquals(1.0, WizString.getSimilarityWords("", ""), 0.001);
        
        // Simple difference
        // "a b c" vs "a b" -> 2 matches, max len 3 -> 0.666
        Assertions.assertEquals(2.0/3.0, WizString.getSimilarityWords("a b c", "a b"), 0.001);
        
        // Insertion with lookahead
        // "a b c" vs "a b x c"
        // a=a, b=b. c!=x. c found at end of s2. x skipped.
        // Matches: a, b, c = 3. Total words: max(3, 4) = 4. -> 0.75
        Assertions.assertEquals(0.75, WizString.getSimilarityWords("a b c", "a b x c"), 0.001);
        
        // Substitution
        // "a b c" vs "a b d"
        // a=a, b=b. c!=d. No lookahead match.
        // Matches: 2. Total: 3. -> 0.666
        Assertions.assertEquals(2.0/3.0, WizString.getSimilarityWords("a b c", "a b d"), 0.001);
        
        // Complete difference
        Assertions.assertEquals(0.0, WizString.getSimilarityWords("a b c", "d e f"), 0.001);
    }

    @Test
    public void testGetLevenshteinDistance() {
        Assertions.assertEquals(0, WizString.getLevenshteinDistance(null, null));
        Assertions.assertEquals(3, WizString.getLevenshteinDistance("abc", null));
        Assertions.assertEquals(3, WizString.getLevenshteinDistance(null, "abc"));
        Assertions.assertEquals(0, WizString.getLevenshteinDistance("abc", "abc"));
        
        Assertions.assertEquals(1, WizString.getLevenshteinDistance("kitten", "sitten")); // s -> k
        Assertions.assertEquals(1, WizString.getLevenshteinDistance("sittin", "sitting")); // +g
        Assertions.assertEquals(3, WizString.getLevenshteinDistance("kitten", "sitting"));
    }

    @Test
    public void testGetDistanceWords() {
        // Exact
        Assertions.assertEquals(0, WizString.getDistanceWords("hello world", "hello world"));
        
        // Nulls
        Assertions.assertEquals(0, WizString.getDistanceWords((String)null, (String)null));
        Assertions.assertEquals(3, WizString.getDistanceWords("abc", null)); 
        
        // Simple insertion
        // "a b" vs "a b c" -> "c" is extra. len 1.
        Assertions.assertEquals(1, WizString.getDistanceWords("a b", "a b c"));
        
        // Simple deletion
        // "a b c" vs "a b" -> "c" is extra. len 1.
        Assertions.assertEquals(1, WizString.getDistanceWords("a b c", "a b"));
        
        // Substitution (Levenshtein)
        // "word1" vs "word2" -> Lev("word1", "word2") = 1.
        Assertions.assertEquals(1, WizString.getDistanceWords("word1", "word2"));
        
        // Complex: "apple banana cherry" vs "apple baana date cherry"
        // apple=apple
        // banana vs baana -> Lev=1.
        // cherry vs date -> cherry found ahead. date skipped (len 4).
        // cherry=cherry.
        // Total = 1 + 4 = 5.
        Assertions.assertEquals(5, WizString.getDistanceWords("apple banana cherry", "apple baana date cherry"));
        
        // Block skip
        // "a z z z b" vs "a b" -> z z z skipped (len 3).
        Assertions.assertEquals(3, WizString.getDistanceWords("a z z z b", "a b"));
    }
}