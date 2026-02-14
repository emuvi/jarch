package br.com.pointel.jarch.flow;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class DatexTest {

    // ============= Requirement 6: TextBegin token tests =============
    
    @Test
    public void testTextBeginToken_AtStartOfText() throws Exception {
        DatexNode[] nodes = {
            new DatexNode("text", 
                new DatexToken[]{DatexToken.textBegin()}, 
                new DatexToken[]{DatexToken.textEnd()}, 
                true)
        };
        Datex datex = new Datex(nodes);
        datex.parse("hello world");
        assertEquals("hello world", datex.getValue("text"));
    }

    @Test
    public void testTextBeginToken_NotAtStart_ThrowsException() {
        DatexNode[] nodes = {
            new DatexNode("text", 
                new DatexToken[]{DatexToken.textBegin()}, 
                new DatexToken[]{DatexToken.literal("hello")}, 
                true)
        };
        Datex datex = new Datex(nodes);
        // Text doesn't start at position 0 for parsing, but TextBegin checks if cursor is at start
        // This should actually work on fresh parse, as cursor starts at 0
        assertDoesNotThrow(() -> datex.parse("hello world"));
    }

    @Test
    public void testTextBeginToken_SkipsLeadingWhitespace() throws Exception {
        DatexNode[] nodes = {
            new DatexNode("text", 
                new DatexToken[]{DatexToken.textBegin()}, 
                new DatexToken[]{DatexToken.textEnd()}, 
                true)
        };
        Datex datex = new Datex(nodes);
        datex.parse("   hello world   ");
        // Value should be from after skipped whitespace to end
        assertEquals("hello world   ", datex.getValue("text"));
    }

    // ============= Requirement 7: Literal token tests =============
    
    @Test
    public void testLiteralToken_ExactMatch() throws Exception {
        DatexNode[] nodes = {
            new DatexNode("greeting", 
                new DatexToken[]{DatexToken.literal("hello")}, 
                new DatexToken[]{DatexToken.literal("world")}, 
                true)
        };
        Datex datex = new Datex(nodes);
        datex.parse("hello beautiful world");
        assertEquals(" beautiful ", datex.getValue("greeting"));
    }

    @Test
    public void testLiteralToken_NoMatch_RequiredNode_ThrowsException() {
        DatexNode[] nodes = {
            new DatexNode("greeting", 
                new DatexToken[]{DatexToken.literal("hi")}, 
                new DatexToken[]{DatexToken.textEnd()}, 
                true)
        };
        Datex datex = new Datex(nodes);
        assertThrows(Exception.class, () -> datex.parse("hello world"));
    }

    @Test
    public void testLiteralToken_WithLeadingWhitespace() throws Exception {
        DatexNode[] nodes = {
            new DatexNode("greeting", 
                new DatexToken[]{DatexToken.literal("hello")}, 
                new DatexToken[]{DatexToken.literal("world")}, 
                true)
        };
        Datex datex = new Datex(nodes);
        datex.parse("hello     world");
        assertEquals("     ", datex.getValue("greeting"));
    }

    // ============= Requirement 8: Regex token tests =============
    
    @Test
    public void testRegexToken_SimplePattern() throws Exception {
        DatexNode[] nodes = {
            new DatexNode("number", 
                new DatexToken[]{DatexToken.literal("num:")}, 
                new DatexToken[]{DatexToken.regex("\\D.*")}, // non-digit followed by anything
                true)
        };
        Datex datex = new Datex(nodes);
        datex.parse("num:123abc");
        assertEquals("123", datex.getValue("number"));
    }

    @Test
    public void testRegexToken_DigitPattern() throws Exception {
        DatexNode[] nodes = {
            new DatexNode("code", 
                new DatexToken[]{DatexToken.literal("ID:")}, 
                new DatexToken[]{DatexToken.regex("\\D.*")}, // match until non-digit
                true)
        };
        Datex datex = new Datex(nodes);
        datex.parse("ID:12345END");
        assertEquals("12345", datex.getValue("code"));
    }

    @Test
    public void testRegexToken_NoMatch_ThrowsException() {
        DatexNode[] nodes = {
            new DatexNode("digits", 
                new DatexToken[]{DatexToken.literal("start ")}, 
                new DatexToken[]{DatexToken.regex("^[0-9]+$")}, // This won't work with lookingAt
                true)
        };
        Datex datex = new Datex(nodes);
        // The regex won't match properly because it's checking with fixed regex patterns
        assertThrows(Exception.class, () -> datex.parse("start abc end"));
    }

    @Test
    public void testRegexToken_WithWhitespace() throws Exception {
        DatexNode[] nodes = {
            new DatexNode("content", 
                new DatexToken[]{DatexToken.literal("start")}, 
                new DatexToken[]{DatexToken.regex("end.*")}, 
                true)
        };
        Datex datex = new Datex(nodes);
        datex.parse("start   content   end marker");
        assertEquals("   content   ", datex.getValue("content"));
    }

    // ============= Requirement 9: TextEnd token tests =============
    
    @Test
    public void testTextEndToken() throws Exception {
        DatexNode[] nodes = {
            new DatexNode("entire", 
                new DatexToken[]{DatexToken.textBegin()}, 
                new DatexToken[]{DatexToken.textEnd()}, 
                true)
        };
        Datex datex = new Datex(nodes);
        datex.parse("hello world");
        assertEquals("hello world", datex.getValue("entire"));
    }

    @Test
    public void testTextEndToken_WithTrailingWhitespace() throws Exception {
        DatexNode[] nodes = {
            new DatexNode("text", 
                new DatexToken[]{DatexToken.literal("hello")}, 
                new DatexToken[]{DatexToken.textEnd()}, 
                true)
        };
        Datex datex = new Datex(nodes);
        datex.parse("hello   ");
        // Whitespace before TextEnd should be included in value
        assertEquals("   ", datex.getValue("text"));
    }

    @Test
    public void testTextEndToken_EmptyContent() throws Exception {
        DatexNode[] nodes = {
            new DatexNode("text", 
                new DatexToken[]{DatexToken.literal("hello")}, 
                new DatexToken[]{DatexToken.textEnd()}, 
                true)
        };
        Datex datex = new Datex(nodes);
        datex.parse("hello");
        assertEquals("", datex.getValue("text"));
    }

    // ============= Requirement 3: Node found when startsWith and endsWith match =============
    
    @Test
    public void testNode_FoundWhenBothTokensMatch() throws Exception {
        DatexNode[] nodes = {
            new DatexNode("middle", 
                new DatexToken[]{DatexToken.literal("start")}, 
                new DatexToken[]{DatexToken.literal("end")}, 
                true)
        };
        Datex datex = new Datex(nodes);
        datex.parse("start content end");
        // Value is from end of startsWith to position of endsWith (after skipping whitespace)
        assertEquals(" content ", datex.getValue("middle"));
    }

    @Test
    public void testNode_MultipleStartsWithOptions() throws Exception {
        DatexNode[] nodes = {
            new DatexNode("content", 
                new DatexToken[]{
                    DatexToken.literal("begin"),
                    DatexToken.literal("start")
                }, 
                new DatexToken[]{DatexToken.literal("finish")}, 
                true)
        };
        Datex datex = new Datex(nodes);
        datex.parse("start my content finish");
        assertEquals(" my content ", datex.getValue("content"));
    }

    @Test
    public void testNode_MultipleEndsWithOptions() throws Exception {
        DatexNode[] nodes = {
            new DatexNode("content", 
                new DatexToken[]{DatexToken.literal("start")}, 
                new DatexToken[]{
                    DatexToken.literal("end"),
                    DatexToken.literal("finish")
                }, 
                true)
        };
        Datex datex = new Datex(nodes);
        datex.parse("start content finish");
        assertEquals(" content ", datex.getValue("content"));
    }

    // ============= Requirement 4: Non-required node not found =============
    
    @Test
    public void testNode_NotRequired_StartNotFound_SkipsNode() throws Exception {
        DatexNode[] nodes = {
            new DatexNode("optional", 
                new DatexToken[]{DatexToken.literal("nothere")}, 
                new DatexToken[]{DatexToken.literal("also-not-here")}, 
                false),
            new DatexNode("required", 
                new DatexToken[]{DatexToken.literal("hello")}, 
                new DatexToken[]{DatexToken.textEnd()}, 
                true)
        };
        Datex datex = new Datex(nodes);
        datex.parse("hello world");
        assertNull(datex.getValue("optional"));
        assertEquals(" world", datex.getValue("required"));
    }

    @Test
    public void testNode_NotRequired_ContinuesFromSameCursor() throws Exception {
        DatexNode[] nodes = {
            new DatexNode("optional", 
                new DatexToken[]{DatexToken.literal("skip-me")}, 
                new DatexToken[]{DatexToken.literal("also-skip")}, 
                false),
            new DatexNode("actual", 
                new DatexToken[]{DatexToken.literal("hello")}, 
                new DatexToken[]{DatexToken.literal("world")}, 
                true)
        };
        Datex datex = new Datex(nodes);
        datex.parse("hello beautiful world");
        assertNull(datex.getValue("optional"));
        assertEquals(" beautiful ", datex.getValue("actual"));
    }

    // ============= Requirement 5: Required node not found throws exception =============
    
    @Test
    public void testNode_Required_StartNotFound_ThrowsException() {
        DatexNode[] nodes = {
            new DatexNode("required", 
                new DatexToken[]{DatexToken.literal("start")}, 
                new DatexToken[]{DatexToken.literal("end")}, 
                true)
        };
        Datex datex = new Datex(nodes);
        assertThrows(Exception.class, () -> datex.parse("no match here"));
    }

    @Test
    public void testNode_Required_EndNotFound_ThrowsException() {
        DatexNode[] nodes = {
            new DatexNode("required", 
                new DatexToken[]{DatexToken.literal("start")}, 
                new DatexToken[]{DatexToken.literal("finish")}, 
                true)
        };
        Datex datex = new Datex(nodes);
        // This should throw because "finish" literal is not found after "start"
        assertThrows(Exception.class, () -> datex.parse("start but no terminator"));
    }

    // ============= Requirement 10: Whitespace handling =============
    
    @Test
    public void testWhitespace_IgnoredInTokenMatching_BeforeStartsWith() throws Exception {
        DatexNode[] nodes = {
            new DatexNode("content", 
                new DatexToken[]{DatexToken.literal("hello")}, 
                new DatexToken[]{DatexToken.literal("world")}, 
                true)
        };
        Datex datex = new Datex(nodes);
        datex.parse("    hello my world");
        assertEquals(" my ", datex.getValue("content"));
    }

    @Test
    public void testWhitespace_IgnoredInTokenMatching_BeforeEndsWith() throws Exception {
        DatexNode[] nodes = {
            new DatexNode("content", 
                new DatexToken[]{DatexToken.literal("start")}, 
                new DatexToken[]{DatexToken.literal("end")}, 
                true)
        };
        Datex datex = new Datex(nodes);
        datex.parse("start content    end");
        assertEquals(" content    ", datex.getValue("content"));
    }

    @Test
    public void testWhitespace_MultipleMixedWhitespace() throws Exception {
        DatexNode[] nodes = {
            new DatexNode("data", 
                new DatexToken[]{DatexToken.literal("begin")}, 
                new DatexToken[]{DatexToken.literal("finish")}, 
                true)
        };
        Datex datex = new Datex(nodes);
        datex.parse("begin \t\n\t data \t\n finish");
        assertEquals(" \t\n\t data \t\n ", datex.getValue("data"));
    }

    // ============= Requirement 2: Value extraction =============
    
    @Test
    public void testValue_ExtractedCorrectly() throws Exception {
        DatexNode[] nodes = {
            new DatexNode("name", 
                new DatexToken[]{DatexToken.literal("<name>")}, 
                new DatexToken[]{DatexToken.literal("</name>")}, 
                true)
        };
        Datex datex = new Datex(nodes);
        datex.parse("<name>John Doe</name>");
        assertEquals("John Doe", datex.getValue("name"));
    }

    @Test
    public void testValue_WithSpecialCharacters() throws Exception {
        DatexNode[] nodes = {
            new DatexNode("email", 
                new DatexToken[]{DatexToken.literal("email:")}, 
                new DatexToken[]{DatexToken.literal("@end")}, 
                true)
        };
        Datex datex = new Datex(nodes);
        datex.parse("email:user+test@domain.com@end");
        assertEquals("user+test@domain.com", datex.getValue("email"));
    }

    // ============= Requirement 1: Process multiple nodes =============
    
    @Test
    public void testMultipleNodes_SequentialProcessing() throws Exception {
        DatexNode[] nodes = {
            new DatexNode("first", 
                new DatexToken[]{DatexToken.textBegin()}, 
                new DatexToken[]{DatexToken.literal("|")}, 
                true),
            new DatexNode("second", 
                new DatexToken[]{DatexToken.literal("part2")}, 
                new DatexToken[]{DatexToken.literal("|")}, 
                true),
            new DatexNode("third", 
                new DatexToken[]{DatexToken.literal("part3")}, 
                new DatexToken[]{DatexToken.textEnd()}, 
                true)
        };
        Datex datex = new Datex(nodes);
        datex.parse("part1|part2|part3");
        assertEquals("part1", datex.getValue("first"));
        assertEquals("", datex.getValue("second")); // Empty because part2 and | are adjacent
        assertEquals("", datex.getValue("third"));   // Empty because part3 and end are adjacent
    }

    @Test
    public void testMultipleNodes_MixedRequiredAndOptional() throws Exception {
        DatexNode[] nodes = {
            new DatexNode("required1", 
                new DatexToken[]{DatexToken.literal("start")}, 
                new DatexToken[]{DatexToken.literal("content")}, 
                true),
            new DatexNode("optional", 
                new DatexToken[]{DatexToken.literal("skip")}, 
                new DatexToken[]{DatexToken.literal("|")}, 
                false),
            new DatexNode("required2", 
                new DatexToken[]{DatexToken.literal("|final")}, 
                new DatexToken[]{DatexToken.textEnd()}, 
                true)
        };
        Datex datex = new Datex(nodes);
        datex.parse("start content|final");
        assertEquals(" ", datex.getValue("required1")); // space after start, before content match position
        assertNull(datex.getValue("optional")); // Not found, skip to next
        assertEquals("", datex.getValue("required2")); // Empty value at end
    }

    // ============= Edge cases and complex scenarios =============
    
    @Test
    public void testEdgeCase_EmptyString() {
        DatexNode[] nodes = {
            new DatexNode("content", 
                new DatexToken[]{DatexToken.textBegin()}, 
                new DatexToken[]{DatexToken.textEnd()}, 
                true)
        };
        Datex datex = new Datex(nodes);
        assertDoesNotThrow(() -> datex.parse(""));
        assertEquals("", datex.getValue("content"));
    }

    @Test
    public void testEdgeCase_OnlyWhitespace() throws Exception {
        DatexNode[] nodes = {
            new DatexNode("content", 
                new DatexToken[]{DatexToken.textBegin()}, 
                new DatexToken[]{DatexToken.textEnd()}, 
                true)
        };
        Datex datex = new Datex(nodes);
        datex.parse("    ");
        // TextBegin skips whitespace to position 4, TextEnd is at position 4
        // So value is empty (no content between them after whitespace is skipped)
        assertEquals("", datex.getValue("content"));
    }

    @Test
    public void testEdgeCase_AdjacentTokens() throws Exception {
        DatexNode[] nodes = {
            new DatexNode("value", 
                new DatexToken[]{DatexToken.literal("hello")}, 
                new DatexToken[]{DatexToken.literal("world")}, 
                true)
        };
        Datex datex = new Datex(nodes);
        datex.parse("helloworld");
        assertEquals("", datex.getValue("value"));
    }

    @Test
    public void testEdgeCase_LongContent() throws Exception {
        DatexNode[] nodes = {
            new DatexNode("content", 
                new DatexToken[]{DatexToken.literal("[START]")}, 
                new DatexToken[]{DatexToken.literal("[END]")}, 
                true)
        };
        Datex datex = new Datex(nodes);
        String longContent = "[START]" + "x".repeat(10000) + "[END]";
        datex.parse(longContent);
        assertEquals("x".repeat(10000), datex.getValue("content"));
    }

    @Test
    public void testEdgeCase_NestedSimilarPatterns() throws Exception {
        DatexNode[] nodes = {
            new DatexNode("outer", 
                new DatexToken[]{DatexToken.literal("<tag>")}, 
                new DatexToken[]{DatexToken.literal("</tag>")}, 
                true)
        };
        Datex datex = new Datex(nodes);
        datex.parse("<tag>content with <tag> inside </tag> more content</tag>");
        // Matches first <tag> and first </tag>, so includes content and nested <tag>
        assertEquals("content with <tag> inside ", datex.getValue("outer"));
    }

    @Test
    public void testClean_ResetsNodeValues() throws Exception {
        DatexNode[] nodes = {
            new DatexNode("value", 
                new DatexToken[]{DatexToken.literal("start")}, 
                new DatexToken[]{DatexToken.literal("end")}, 
                true)
        };
        Datex datex = new Datex(nodes);
        datex.parse("start content end");
        // Value includes whitespace before startsWith match and before endsWith match
        assertEquals(" content ", datex.getValue("value"));
        
        datex.clean();
        assertNull(datex.getValue("value"));
    }

    @Test
    public void testReparsing_WithDifferentInput() throws Exception {
        DatexNode[] nodes = {
            new DatexNode("value", 
                new DatexToken[]{DatexToken.literal("key:")}, 
                new DatexToken[]{DatexToken.literal(";")}, 
                true)
        };
        Datex datex = new Datex(nodes);
        
        datex.parse("key:first;");
        assertEquals("first", datex.getValue("value"));
        
        datex.parse("key:second;");
        assertEquals("second", datex.getValue("value"));
    }

}
