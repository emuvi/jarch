package com.vidlus.jarch.flow;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import com.vidlus.jarch.desk.TextHistory;
import com.vidlus.jarch.mage.WizString;

/**
 * A utility class designed to read, parse, and write files composed of text sections.
 * Each section is identified by a markdown-style header, allowing for structured text storage.
 *
 * @author emuvi
 */
public class CharsSections {

    private final File file;

    /**
     * Constructs a new CharsSections instance for the specified file.
     * 
     * @param file The file to be read from or written to.
     */
    public CharsSections(File file) {
        this.file = file;
    }

    /**
     * Gets the file associated with this CharsSections instance.
     * 
     * @return The target file.
     */
    public File getFile() {
        return file;
    }

    /**
     * Reads and parses the associated file into a CharsSectionsMap.
     * 
     * @return A map of section names to lists of strings.
     * @throws Exception If an error occurs during file reading.
     */
    public CharsSectionsMap read() throws Exception {
        return read(null);
    }
    
    /**
     * Reads and parses the associated file into a CharsSectionsMap, optionally saving
     * the raw content into the provided TextHistory.
     * 
     * @param history The text history to record the file state before reading (may be null).
     * @return A map of section names to lists of strings.
     * @throws Exception If an error occurs during file reading.
     */
    public CharsSectionsMap read(TextHistory history) throws Exception {
        var source = Files.readString(file.toPath(), StandardCharsets.UTF_8).trim();
        if (history != null) {
            history.push(file, source);
        }
        return parse(source);
    }

    /**
     * Parses raw string content into a CharsSectionsMap based on markdown-style section headers.
     * 
     * @param source The raw string content to parse.
     * @return A map of section names to lists of strings.
     */
    public static CharsSectionsMap parse(String source) {
        var lines = WizString.getLines(source);
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
    
    /**
     * Formats and writes the given CharsSectionsMap to the associated file.
     * 
     * @param source The CharsSectionsMap to write.
     * @throws Exception If an error occurs during file writing.
     */
    public void write(CharsSectionsMap source) throws Exception {
        write(source, null);
    }

    /**
     * Formats and writes the given CharsSectionsMap to the associated file, optionally saving
     * the formatted text into the provided TextHistory.
     * 
     * @param source The CharsSectionsMap to write.
     * @param history The text history to record the file state after writing (may be null).
     * @throws Exception If an error occurs during file writing.
     */
    public void write(CharsSectionsMap source, TextHistory history) throws Exception {
        var text = format(source);
        Files.writeString(file.toPath(), text, StandardCharsets.UTF_8);
        if (history != null) {
            history.push(file, text);
        }
    }

    /**
     * Formats a CharsSectionsMap into a single raw string containing markdown-style section headers.
     * 
     * @param source The map of sections to format.
     * @return The formatted string representation of the sections.
     */
    public static String format(CharsSectionsMap source) {
        var builder = new StringBuilder();
        builder.append("\n");
        var emptyNameSectionLines = source.get("");
        if (emptyNameSectionLines != null && !emptyNameSectionLines.isEmpty()) {
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
        return builder.toString();
    }
    
    /**
     * Helper method to append lines of a section to a StringBuilder.
     * 
     * @param builder     the StringBuilder to append to
     * @param lines       the list of strings representing the section's lines
     * @param spaceOnHead whether an extra empty line should be added at the top if the section is non-empty
     */
    private static void putLines(StringBuilder builder, List<String> lines, boolean spaceOnHead) {
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
