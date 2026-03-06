package br.com.pointel.jarch.mage;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;

public class WizGVY {

    private static String extension = ".gvy";

    public static File folder = new File("gvy");

    public static String getExtension() {
        return extension;
    }

    public static void setExtension(String extension) {
        extension = extension == null ? "" : extension.toLowerCase().trim();
        if (!extension.startsWith(".")) {
            extension = "." + extension;
        }
        WizGVY.extension = extension;
    }

    public static File getFolder() {
        return folder;
    }

    public static void setFolder(File folder) {
        WizGVY.folder = folder;
    }

    public static List<String> getNames() {
        return getNames(folder);
    }
 
    public static List<String> getNames(File folder) {
        var result = new ArrayList<String>();
        try {
            Files.createDirectories(folder.toPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (var inside : folder.listFiles()) {
            if (inside.isFile() && inside.getName().toLowerCase().endsWith(extension)) {
                result.add(inside.getName());
            }
        }
        return result;
    }

    public static void loadNames(DefaultComboBoxModel<String> field) {
        loadNames(field, folder);
    }
    
    public static void loadNames(DefaultComboBoxModel<String> field, File folder) {
        field.removeAllElements();
        for(var chatName : getNames(folder)) {
            field.addElement(chatName);
        }
    }

    public static String load(String name) throws Exception {
        return load(name, folder);
    }
    
    public static String load(String name, File folder) throws Exception {
        var groovyFile = new File(folder, name + extension);
        return Files.readString(groovyFile.toPath(), StandardCharsets.UTF_8);
    }

    public static void save(String name, String source) throws Exception {
        save(name, folder, source);
    }

    public static void save(String name, File folder, String source) throws Exception {
        var groovyFile = new File(folder, name + extension);
        Files.writeString(groovyFile.toPath(), source, StandardCharsets.UTF_8);
    }

    public static Object call(String name) throws Exception {
        return run(load(name));
    }

    public static Object call(String name, File folder) throws Exception {
        return run(load(name, folder));
    }
    
    public static Object call(String name, Map<String, Object> variables) throws Exception {
        return run(load(name), variables);
    }

    public static Object call(String name, File folder, Map<String, Object> variables) throws Exception {
        return run(load(name, folder), variables);
    }
    
    public static Object run(String source) throws Exception {
        return run(source, null);
    }
    
    public static Object run(String source, Map<String, Object> variables) throws Exception {
        var binding = new Binding(variables);
        var shell = new GroovyShell(binding);
        return shell.evaluate(source);
    }
    
    public static Map<String, Object> vars(Variable... variables) {
        var result = new HashMap<String, Object>();
        for (var variable : variables) {
            result.put(variable.name(), variable.value());
        }
        return result;
    }
    
    public static Variable map(String name, Object value) {
        return new Variable(name, value);
    }
    
    public static record Variable(String name, Object value){}
    
}
