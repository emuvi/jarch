package com.vidlus.jarch.flow;

import java.util.List;
import java.util.regex.Pattern;

/**
 * A highly customizable text parser and tokenizer.
 * It traverses a string sequentially and extracts specific substrings into 
 * {@link DatexNode}s based on defined start and end {@link DatexToken} rules.
 */
public class Datex {

    private final List<DatexNode> nodes;

    /**
     * Constructs a parser configured with a single node.
     *
     * @param node the single DatexNode to extract
     */
    public Datex(DatexNode node) {
        this(new DatexNode[] { node });
    }

    /**
     * Constructs a parser configured with an array of sequential nodes.
     *
     * @param nodes the array of DatexNodes to extract
     */
    public Datex(DatexNode[] nodes) {
        this.nodes = List.of(nodes);
    }

    /**
     * Constructs a parser configured with a list of sequential nodes.
     *
     * @param nodes the list of DatexNodes to extract
     */
    public Datex(List<DatexNode> nodes) {
        this.nodes = nodes;
    }

    /**
     * Parses the given text according to the registered nodes.
     * As the text is scanned, nodes are populated with the matched values.
     *
     * @param text the source text to parse
     * @throws Exception if a required node cannot find its start or end tokens
     */
    public void parse(String text) throws Exception {
        clean();
        var cursor = 0;
        for (var node : nodes) {
            var startTokenMatchEnd = -1;
            for (var token : node.getStartsWith()) {
                var matchEnd = matchToken(token, text, cursor, true);
                if (matchEnd != -1) {
                    startTokenMatchEnd = matchEnd;
                    break;
                }
            }
            if (startTokenMatchEnd == -1) {
                if (node.isRequired()) {
                    throw new Exception("Required node '" + node.getName() + "' start token not found at index " + cursor);
                } else {
                    continue;
                }
            }
            var endTokenMatchStart = -1;
            var endTokenMatchEnd = -1;
            for (var i = startTokenMatchEnd; i <= text.length(); i++) {
                for (var token : node.getEndsWith()) {
                    var matchEnd = matchToken(token, text, i, false);
                    if (matchEnd != -1) {
                        var index = skipWhitespace(text, i);
                        endTokenMatchStart = index;
                        endTokenMatchEnd = matchEnd;
                        break;
                    }
                }
                if (endTokenMatchStart != -1) {
                    break;
                }
            }
            if (endTokenMatchStart == -1) {
                throw new Exception("Node '" + node.getName() + "' end token not found starting from index " + startTokenMatchEnd);
            }
            var value = text.substring(startTokenMatchEnd, endTokenMatchStart);
            node.setValue(value);
            cursor = endTokenMatchEnd;
        }
    }

    /**
     * Clears all previously matched values from the registered nodes.
     */
    public void clean() {
        for (DatexNode node : nodes) {
            node.clean();
        }
    }

    /**
     * Retrieves the parsed value of a specific node by its name.
     *
     * @param name the name of the node
     * @return the extracted string value, or null if not found
     */
    public String getValue(String name) {
        for (DatexNode node : nodes) {
            if (node.getName().equals(name)) {
                return node.getValue();
            }
        }
        return null;
    }

    /**
     * Retrieves a specific node by its name.
     *
     * @param name the name of the node
     * @return the DatexNode, or null if not found
     */
    public DatexNode getNode(String name) {
        for (DatexNode node : nodes) {
            if (node.getName().equals(name)) {
                return node;
            }
        }
        return null;
    }

    /**
     * @return the list of all nodes registered in this parser
     */
    public List<DatexNode> getNodes() {
        return nodes;
    }

    /**
     * Core matching logic testing if a token matches the text at a specific index.
     *
     * @param token        the token to match
     * @param text         the text to search
     * @param fromIndex    the current cursor position
     * @param isStartToken whether this is a starting boundary token
     * @return the resulting cursor index after the match, or -1 if no match
     */
    private int matchToken(DatexToken token, String text, int fromIndex, boolean isStartToken) {
        var index = skipWhitespace(text, fromIndex);
        switch (token.getKind()) {
            case TextBegin:
                return fromIndex == 0 ? index : -1;
            case Literal:
                if (text.startsWith(token.getCode(), index)) {
                    return index + token.getCode().length();
                }
                return -1;
            case Regex:
                var p = Pattern.compile(token.getCode());
                var m = p.matcher(text);
                m.region(index, text.length());
                if (m.lookingAt()) {
                    // For start tokens, return end of match; for end tokens, return start of match
                    return isStartToken ? m.end() : index;
                }
                return -1;
            case TextEnd:
                if (index == text.length()) {
                    return index;
                }
                return -1;
            default:
                return -1;
        }
    }

    /**
     * Skips whitespace characters to find the next significant index.
     *
     * @param text  the string to analyze
     * @param index the starting index
     * @return the index of the next non-whitespace character
     */
    private int skipWhitespace(String text, int index) {
        while (index < text.length() && Character.isWhitespace(text.charAt(index))) {
            index++;
        }
        return index;
    }

}
