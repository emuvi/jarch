package com.vidlus.jarch.mage;

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
import groovy.lang.Script;

public class WizGroovy {

    private static String extension = ".gvy";

    public static File folder = new File("gvy");

    private WizGroovy() {}

    /**
     * Gets the default file extension for Groovy scripts.
     *
     * @return the file extension (e.g., ".gvy")
     */
    public static String getExtension() {
        return extension;
    }

    /**
     * Sets the file extension for Groovy scripts.
     * Automatically normalizes the extension to lowercase and ensures it starts with a dot.
     *
     * @param extension the new extension (e.g., ".gvy" or "gvy")
     */
    public static void setExtension(String extension) {
        extension = extension == null ? "" : extension.toLowerCase().trim();
        if (!extension.startsWith(".")) {
            extension = "." + extension;
        }
        WizGroovy.extension = extension;
    }

    /**
     * Gets the default folder where Groovy scripts are stored.
     *
     * @return the default folder
     */
    public static File getFolder() {
        return folder;
    }

    /**
     * Sets the default folder where Groovy scripts are stored.
     *
     * @param folder the new default folder
     */
    public static void setFolder(File folder) {
        WizGroovy.folder = folder;
    }

    /**
     * Gets the names of all Groovy scripts in the default folder.
     *
     * @return a list of script file names with extensions
     */
    public static List<String> getNames() {
        return getNames(folder);
    }
 
    /**
     * Gets the names of all Groovy scripts in a specified folder.
     * Creates the folder if it does not exist.
     *
     * @param folder the folder to list
     * @return a list of script file names with extensions
     */
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

    /**
     * Populates a combo box model with the names of all Groovy scripts from the default folder.
     *
     * @param field the DefaultComboBoxModel to populate
     */
    public static void loadNames(DefaultComboBoxModel<String> field) {
        loadNames(field, folder);
    }
    
    /**
     * Populates a combo box model with the names of all Groovy scripts from a specified folder.
     *
     * @param field the DefaultComboBoxModel to populate
     * @param folder the folder to list scripts from
     */
    public static void loadNames(DefaultComboBoxModel<String> field, File folder) {
        field.removeAllElements();
        for(var chatName : getNames(folder)) {
            field.addElement(chatName);
        }
    }

    /**
     * Checks if a Groovy script exists in the default folder.
     *
     * @param name the script name (without extension)
     * @return true if the script exists, false otherwise
     */
    public static boolean exists(String name) {
        return exists(name, folder);
    }

    /**
     * Checks if a Groovy script exists in a specified folder.
     *
     * @param name the script name (without extension)
     * @param folder the folder to check in
     * @return true if the script exists, false otherwise
     */
    public static boolean exists(String name, File folder) {
        var groovyFile = new File(folder, name + extension);
        return groovyFile.exists() && groovyFile.isFile();
    }

    /**
     * Loads a Groovy script from the default folder as a string.
     *
     * @param name the script name (without extension)
     * @return the script source code
     * @throws Exception if the file cannot be read
     */
    public static String load(String name) throws Exception {
        return load(name, folder);
    }
    
    /**
     * Loads a Groovy script from a specified folder as a string.
     *
     * @param name the script name (without extension)
     * @param folder the folder to load from
     * @return the script source code
     * @throws Exception if the file cannot be read
     */
    public static String load(String name, File folder) throws Exception {
        var groovyFile = new File(folder, name + extension);
        return Files.readString(groovyFile.toPath(), StandardCharsets.UTF_8);
    }

    /**
     * Saves a Groovy script to the default folder.
     *
     * @param name the script name (without extension)
     * @param source the script source code
     * @throws Exception if the file cannot be written
     */
    public static void save(String name, String source) throws Exception {
        save(name, folder, source);
    }

    /**
     * Saves a Groovy script to a specified folder.
     *
     * @param name the script name (without extension)
     * @param folder the folder to save to
     * @param source the script source code
     * @throws Exception if the file cannot be written
     */
    public static void save(String name, File folder, String source) throws Exception {
        if (!folder.exists()) {
            folder.mkdirs();
        }
        var groovyFile = new File(folder, name + extension);
        Files.writeString(groovyFile.toPath(), source, StandardCharsets.UTF_8);
    }

    /**
     * Deletes a Groovy script from the default folder.
     *
     * @param name the script name (without extension)
     * @return true if successfully deleted, false otherwise
     */
    public static boolean delete(String name) {
        return delete(name, folder);
    }

    /**
     * Deletes a Groovy script from a specified folder.
     *
     * @param name the script name (without extension)
     * @param folder the folder to delete from
     * @return true if successfully deleted, false otherwise
     */
    public static boolean delete(String name, File folder) {
        var groovyFile = new File(folder, name + extension);
        if (groovyFile.exists()) {
            return groovyFile.delete();
        }
        return false;
    }

    /**
     * Loads and executes a Groovy script from the default folder.
     *
     * @param name the script name (without extension)
     * @return the result of the script execution
     * @throws Exception if the script cannot be loaded or executed
     */
    public static Object call(String name) throws Exception {
        return run(load(name));
    }

    /**
     * Loads and executes a Groovy script from a specified folder.
     *
     * @param name the script name (without extension)
     * @param folder the folder to load from
     * @return the result of the script execution
     * @throws Exception if the script cannot be loaded or executed
     */
    public static Object call(String name, File folder) throws Exception {
        return run(load(name, folder));
    }
    
    /**
     * Loads and executes a Groovy script from the default folder with supplied variables.
     *
     * @param name the script name (without extension)
     * @param variables the variables available to the script
     * @return the result of the script execution
     * @throws Exception if the script cannot be loaded or executed
     */
    public static Object call(String name, Map<String, Object> variables) throws Exception {
        return run(load(name), variables);
    }

    /**
     * Loads and executes a Groovy script from a specified folder with supplied variables.
     *
     * @param name the script name (without extension)
     * @param folder the folder to load from
     * @param variables the variables available to the script
     * @return the result of the script execution
     * @throws Exception if the script cannot be loaded or executed
     */
    public static Object call(String name, File folder, Map<String, Object> variables) throws Exception {
        return run(load(name, folder), variables);
    }

    /**
     * Parses Groovy source code into a Script object.
     *
     * @param source the Groovy source code
     * @return the compiled Script object
     */
    public static Script parse(String source) {
        return new GroovyShell().parse(source);
    }

    /**
     * Parses Groovy source code into a Script object with supplied variables.
     *
     * @param source the Groovy source code
     * @param variables the variables available to the script
     * @return the compiled Script object
     */
    public static Script parse(String source, Map<String, Object> variables) {
        var binding = new Binding(variables);
        return new GroovyShell(binding).parse(source);
    }
    
    /**
     * Executes Groovy source code and returns the result.
     *
     * @param source the Groovy source code
     * @return the result of the script execution
     * @throws Exception if the script cannot be executed
     */
    public static Object run(String source) throws Exception {
        return run(source, null);
    }
    
    /**
     * Executes Groovy source code with supplied variables and returns the result.
     *
     * @param source the Groovy source code
     * @param variables the variables available to the script
     * @return the result of the script execution
     * @throws Exception if the script cannot be executed
     */
    public static Object run(String source, Map<String, Object> variables) throws Exception {
        var binding = new Binding(variables);
        var shell = new GroovyShell(binding);
        return shell.evaluate(source);
    }

    /**
     * Evaluates a Groovy expression and returns the result.
     * This is a convenience method equivalent to run(expression).
     *
     * @param expression the Groovy expression to evaluate
     * @return the result of the expression
     * @throws Exception if the expression cannot be evaluated
     */
    public static Object eval(String expression) throws Exception {
        return run(expression, null);
    }
    
    /**
     * Creates a map of variables from Variable records.
     * Useful for quickly creating the variables map for script execution.
     *
     * @param variables the Variable records to include
     * @return a map of variable names to values
     */
    public static Map<String, Object> vars(Variable... variables) {
        var result = new HashMap<String, Object>();
        if (variables != null) {
            for (var variable : variables) {
                result.put(variable.name(), variable.value());
            }
        }
        return result;
    }
    
    /**
     * Creates a Variable record for use in the vars() method.
     *
     * @param name the variable name
     * @param value the variable value
     * @return a Variable record
     */
    public static Variable map(String name, Object value) {
        return new Variable(name, value);
    }
    
    /**
     * A record representing a named variable for script execution.
     *
     * @param name the variable name
     * @param value the variable value
     */
    public static record Variable(String name, Object value){}

    /**
     * A reusable shell context for executing multiple scripts 
     * while sharing the same variables and environment.
     * Maintains a persistent Groovy binding across multiple evaluations.
     */
    public static class ShellContext {
        private final Binding binding;
        private final GroovyShell shell;

        /**
         * Creates a new ShellContext with an empty set of variables.
         */
        public ShellContext() {
            this(new HashMap<>());
        }

        /**
         * Creates a new ShellContext with initial variables.
         *
         * @param initialVariables the initial variables available to scripts
         */
        public ShellContext(Map<String, Object> initialVariables) {
            this.binding = new Binding(initialVariables);
            this.shell = new GroovyShell(this.binding);
        }

        /**
         * Sets or updates a variable in the shell context.
         *
         * @param name the variable name
         * @param value the variable value
         * @return this ShellContext for method chaining
         */
        public ShellContext set(String name, Object value) {
            this.binding.setVariable(name, value);
            return this;
        }

        /**
         * Gets a variable from the shell context.
         *
         * @param name the variable name
         * @return the variable value, or null if not found
         */
        public Object get(String name) {
            return this.binding.hasVariable(name) ? this.binding.getVariable(name) : null;
        }

        /**
         * Evaluates a Groovy script within this context.
         * The script has access to all variables in the binding.
         *
         * @param script the Groovy source code to evaluate
         * @return the result of the script execution
         */
        public Object eval(String script) {
            return this.shell.evaluate(script);
        }

        /**
         * Parses a Groovy script within this context.
         *
         * @param script the Groovy source code to parse
         * @return the compiled Script object
         */
        public Script parse(String script) {
            return this.shell.parse(script);
        }
    }
    
}
