package br.com.pointel.jarch.mage;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author emuvi
 */
public class WizChats {
    
    private static final File FOLDER_CHATS = new File("chats");
    private static final String TAG_INSERT = "<<INSERT_HERE>>";
    
    public static void openFolder() throws Exception {
        WizSwing.open(FOLDER_CHATS);
    }
 
    public static List<String> getNames() {
        var result = new ArrayList<String>();
        try {
            Files.createDirectories(FOLDER_CHATS.toPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (var inside : FOLDER_CHATS.listFiles()) {
            if (inside.isFile() && inside.getName().toLowerCase().endsWith(".txt")) {
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
        return new File(FOLDER_CHATS, name).createNewFile();
    }
    
    public static boolean delete(String name) throws Exception {
        return new File(FOLDER_CHATS, name).delete();
    }
    
    public static String load(String name) throws Exception {
        var chatFile = new File(FOLDER_CHATS, name);
        return Files.readString(chatFile.toPath(), StandardCharsets.UTF_8);
    }
    
    public static void save(String name, String source) throws Exception {
        var chatFile = new File(FOLDER_CHATS, name);
        Files.writeString(chatFile.toPath(), source, StandardCharsets.UTF_8);
    }
    
    public static String maybeInsertClipboard(String name) throws Exception {
        var chat = load(name);
        if (chat.contains(TAG_INSERT)) {
            chat = chat.replace(TAG_INSERT, WizSwing.getStringOnClipboard());
        }
        return chat;
    }
    
    public static String forceInsertClipboard(String name) throws Exception {
        var chat = load(name);
        if (chat.contains(TAG_INSERT)) {
            chat = chat.replace(TAG_INSERT, WizSwing.getStringOnClipboard());
        } else {
            chat += "\n" + WizSwing.getStringOnClipboard();
        }
        return chat;
    }
    
}
