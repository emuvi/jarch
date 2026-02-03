package br.com.pointel.jarch.mage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

class WizStringTest {

    @Test
    void testGetParameterName() {
        assertEquals(null, WizString.getParameterName(null));
        assertEquals("", WizString.getParameterName(""));
        assertEquals("TEST", WizString.getParameterName("test"));
        assertEquals("TEST", WizString.getParameterName("TEST"));
        assertEquals("TEST_CASE", WizString.getParameterName("TestCase"));
        assertEquals("TEST_CASE", WizString.getParameterName("testCase"));
        assertEquals("TEST_CASE", WizString.getParameterName("test Case"));
        assertEquals("TEST_CASE", WizString.getParameterName("test-Case"));
        assertEquals("TEST_CASE", WizString.getParameterName("test-case"));
        assertEquals("TEST_CASE", WizString.getParameterName("test--case"));
        assertEquals("A_URL_VALUE", WizString.getParameterName("aURL_Value"));
        assertEquals("MY_URL", WizString.getParameterName("myURL"));
        assertEquals("TEST_CASE", WizString.getParameterName(" test case "));
        assertEquals("FIRST_NAME", WizString.getParameterName("first_name"));
    }

    @Test
    void testGetWordsOnDiffers() {
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
            var result = WizString.getWordsOnDiffers(test.input);
            Assertions.assertLinesMatch(test.expected, result);
        }
    }

    private record GetWordsTest(String input, List<String> expected) {}

}
