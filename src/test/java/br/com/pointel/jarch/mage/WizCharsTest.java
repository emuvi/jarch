package br.com.pointel.jarch.mage;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WizCharsTest {

    @Test
    void testGetWords() {
        var tests = List.of(
            new GetWordsTest("abc test123", List.of("abc", "test", "123")),
            new GetWordsTest("abc test123.45", List.of("abc", "test", "123.45")),
            new GetWordsTest("abc test123.45 def", List.of("abc", "test", "123.45", "def")),
            new GetWordsTest("abc test123.45 def 6789", List.of("abc", "test", "123.45", "def", "6789")),
            new GetWordsTest("'abc' test123", List.of("'", "abc", "'", "test", "123")),
            new GetWordsTest("'abc'?! test123", List.of("'", "abc", "'?!", "test", "123")),
            new GetWordsTest("abc test123.45 abc/def", List.of("abc", "test", "123.45", "abc", "/", "def")),
            new GetWordsTest("abc test123.45 abc\\def", List.of("abc", "test", "123.45", "abc", "\\", "def")),
            new GetWordsTest("abc test123.45 abc.def", List.of("abc", "test", "123.45", "abc", ".", "def"))
        );
        for (var test : tests) {
            var result = WizString.getWords(test.input);
            Assertions.assertLinesMatch(test.expected, result);
        }
    }

    private record GetWordsTest(String input, List<String> expected) {}

}
