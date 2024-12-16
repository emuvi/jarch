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

/**
 *
 * @author emuvi
 */
public class WizGroovy {
    
    private static final File FOLDER_GROOVY = new File("groovy");
    
    public static void openFolder() throws Exception {
        WizSwing.open(FOLDER_GROOVY);
    }
 
    public static List<String> getNames() {
        var result = new ArrayList<String>();
        try {
            Files.createDirectories(FOLDER_GROOVY.toPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (var inside : FOLDER_GROOVY.listFiles()) {
            if (inside.isFile() && inside.getName().toLowerCase().endsWith(".gvy")) {
                result.add(inside.getName());
            }
        }
        return result;
    }
    
    public static void loadNames(DefaultComboBoxModel<String> field) {
        field.removeAllElements();
        for(var chatName : getNames()) {
            field.addElement(chatName);
        }
    }
    
    public static boolean create(String name) throws Exception {
        return new File(FOLDER_GROOVY, name).createNewFile();
    }
    
    public static boolean delete(String name) throws Exception {
        return new File(FOLDER_GROOVY, name).delete();
    }
    
    public static String load(String name) throws Exception {
        var groovyFile = new File(FOLDER_GROOVY, name);
        return Files.readString(groovyFile.toPath(), StandardCharsets.UTF_8);
    }
    
    public static void save(String name, String source) throws Exception {
        var groovyFile = new File(FOLDER_GROOVY, name);
        Files.writeString(groovyFile.toPath(), source, StandardCharsets.UTF_8);
    }
    
    public static Object call(String name) throws Exception {
        return run(load(name));
    }
    
    public static Object call(String name, Map variables) throws Exception {
        return run(load(name), variables);
    }
    
    public static Object run(String source) throws Exception {
        return run(source, null);
    }
    
    public static Object run(String source, Map variables) throws Exception {
        var binding = new Binding(variables);
        var shell = new GroovyShell(binding);
        return shell.evaluate(source);
    }
    
    public static Map<String, Object> mapped(Variable... variables) {
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
