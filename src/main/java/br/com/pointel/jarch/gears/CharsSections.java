package br.com.pointel.jarch.gears;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import br.com.pointel.jarch.mage.WizChars;

/**
 *
 * @author emuvi
 */
public class CharsSections {

    private final File file;

    public CharsSections(File file) {
        this.file = file;
    }

    public CharsSectionsMap read() throws Exception {
        return read(null);
    }
    
    public CharsSectionsMap read(TextHistory history) throws Exception {
        var source = Files.readString(file.toPath(), StandardCharsets.UTF_8).trim();
        if (history != null) {
            history.push(file, source);
        }
        var lines = WizChars.getLines(source);
        var result = new CharsSectionsMap();
        var actualName = "";
        var actualList = result.newSection(actualName);
        for (int i = 0; i < lines.length; i++) {
            var actualLine = lines[i];
            var nextLine = i < lines.length - 1 ? lines[i + 1] : "";
            var plusLine = i < lines.length - 2 ? lines[i + 2] : "";
            if ("---".equals(actualLine) && nextLine.startsWith("# ")) {
                actualName = nextLine.substring(2);
                if (result.containsKey(actualName)) {
                    actualList = result.get(actualName);
                } else {
                    actualList = result.newSection(actualName);
                }
                i += 1;
                if ("---".equals(plusLine)) {
                    i += 1;
                }
            } else if (actualLine.startsWith("# ") && "---".equals(nextLine)) {
                actualName = actualLine.substring(2);
                if (result.containsKey(actualName)) {
                    actualList = result.get(actualName);
                } else {
                    actualList = new ArrayList<>();
                    result.put(actualName, actualList);
                }
                i += 1;
            } else {
                actualList.add(actualLine);
            }
        }
        return result;
    }
    
    public void write(CharsSectionsMap source) throws Exception {
        write(source, null);
    }

    public void write(CharsSectionsMap source, TextHistory history) throws Exception {
        var builder = new StringBuilder();
        builder.append("\n");
        var emptyNameSectionLines = source.get("");
        if (!emptyNameSectionLines.isEmpty()) {
            putLines(builder, emptyNameSectionLines, false);
        }
        for (var sectionName : source.keySet()) {
            if (sectionName.isEmpty()) {
                continue;
            }
            var lines = source.get(sectionName);
            builder.append("---\n");
            builder.append("# ");
            builder.append(sectionName);
            builder.append("\n---\n");
            putLines(builder, lines, true);
        }
        var text = builder.toString();
        Files.writeString(file.toPath(), text, StandardCharsets.UTF_8);
        if (history != null) {
            history.push(file, text);
        }
    }
    
    private void putLines(StringBuilder builder, List<String> lines, boolean spaceOnHead) {
        var starting = true;
        var lastEmpty = false;
        for (var line : lines) {
            if (starting) {
                if (line.isEmpty()) {
                    continue;
                } else {
                    starting = false;
                    if (spaceOnHead) {
                        builder.append("\n");
                    }
                }
            }
            builder.append(line);
            builder.append("\n");
            lastEmpty = line.isEmpty();
        }
        if (!lastEmpty) {
            builder.append("\n");
        }
    }

}
