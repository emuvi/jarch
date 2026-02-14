package br.com.pointel.jarch.flow;

import java.util.regex.Pattern;

public class Datex {

    private final DatexNode[] nodes;

    public Datex(DatexNode node) {
        this(new DatexNode[] { node });
    }

    public Datex(DatexNode[] nodes) {
        this.nodes = nodes;
    }

    public void parse(String text) throws Exception {
        clean();
        var cursor = 0;
        for (var node : nodes) {
            var startTokenMatchEnd = -1;
            for (var token : node.getStartsWith()) {
                var matchEnd = matchToken(token, text, cursor);
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
                    var matchEnd = matchToken(token, text, i);
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

    public void clean() {
        for (DatexNode node : nodes) {
            node.clean();
        }
    }

    public String getValue(String name) {
        for (DatexNode node : nodes) {
            if (node.getName().equals(name)) {
                return node.getValue();
            }
        }
        return null;
    }

    public DatexNode getNode(String name) {
        for (DatexNode node : nodes) {
            if (node.getName().equals(name)) {
                return node;
            }
        }
        return null;
    }

    public DatexNode[] getNodes() {
        return nodes;
    }

    private int matchToken(DatexToken token, String text, int fromIndex) {
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
                    return m.end();
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

    private int skipWhitespace(String text, int index) {
        while (index < text.length() && Character.isWhitespace(text.charAt(index))) {
            index++;
        }
        return index;
    }

}
