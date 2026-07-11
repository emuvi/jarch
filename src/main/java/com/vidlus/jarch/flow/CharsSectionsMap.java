package com.vidlus.jarch.flow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import com.vidlus.jarch.mage.WizString;

/**
 * A specialized map structure intended to store named text sections as lists of strings.
 * Built upon {@link LinkedHashMap} to maintain insertion order of sections.
 *
 * @author emuvi
 */
public class CharsSectionsMap extends LinkedHashMap<String, List<String>> {
    
    /**
     * Creates a new empty section with the specified name and adds it to the map.
     * Overwrites any existing section with the same name.
     * 
     * @param name The name of the new section.
     * @return The newly created list of strings for the section.
     */
    public List<String> newSection(String name) {
        var result = new ArrayList<String>();
        put(name, result);
        return result;
    }

    /**
     * Retrieves the content of a section as a single string, with lines joined by newline characters.
     * 
     * @param name The name of the section.
     * @return The joined string content of the section, or null if the section does not exist.
     */
    public String getString(String name) {
        var list = get(name);
        if (list == null) return null;
        return String.join("\n", list);
    }

    /**
     * Sets the content of a section from a single string. The string is split into multiple lines.
     * 
     * @param name The name of the section.
     * @param content The string content to set. If null or empty, an empty section is created.
     */
    public void setString(String name, String content) {
        var list = newSection(name);
        if (content != null && !content.isEmpty()) {
            list.addAll(Arrays.asList(WizString.getLines(content)));
        }
    }

    /**
     * Adds a single line of text to the specified section.
     * If the section does not exist, it is created.
     * 
     * @param name The name of the section.
     * @param line The line of text to add.
     */
    public void addLine(String name, String line) {
        getOrCreateSection(name).add(line);
    }

    /**
     * Adds multiple lines of text to the specified section.
     * If the section does not exist, it is created.
     * 
     * @param name The name of the section.
     * @param lines The lines of text to add.
     */
    public void addLines(String name, String... lines) {
        getOrCreateSection(name).addAll(Arrays.asList(lines));
    }

    /**
     * Adds a list of lines to the specified section.
     * If the section does not exist, it is created.
     * 
     * @param name The name of the section.
     * @param lines The list of lines to add.
     */
    public void addLines(String name, List<String> lines) {
        getOrCreateSection(name).addAll(lines);
    }

    /**
     * Checks whether a section with the given name exists.
     * 
     * @param name The name of the section.
     * @return true if the section exists, false otherwise.
     */
    public boolean hasSection(String name) {
        return containsKey(name);
    }
    
    /**
     * Retrieves the list of lines for the specified section.
     * 
     * @param name The name of the section.
     * @return The list of lines, or null if the section does not exist.
     */
    public List<String> getSection(String name) {
        return get(name);
    }

    /**
     * Retrieves the list of lines for the specified section, creating it if it does not exist.
     * 
     * @param name The name of the section.
     * @return The list of lines for the section.
     */
    public List<String> getOrCreateSection(String name) {
        var list = get(name);
        if (list == null) {
            list = newSection(name);
        }
        return list;
    }

    /**
     * Removes the specified section from the map entirely.
     * 
     * @param name The name of the section to remove.
     */
    public void removeSection(String name) {
        remove(name);
    }
    
    /**
     * Clears all lines from the specified section, keeping the section name in the map.
     * 
     * @param name The name of the section to clear.
     */
    public void clearSection(String name) {
        var list = get(name);
        if (list != null) {
            list.clear();
        }
    }
    
    /**
     * Checks if a section is empty or does not exist.
     * 
     * @param name The name of the section.
     * @return true if the section doesn't exist or has no lines, false otherwise.
     */
    public boolean isSectionEmpty(String name) {
        var list = get(name);
        return list == null || list.isEmpty();
    }
    
}
