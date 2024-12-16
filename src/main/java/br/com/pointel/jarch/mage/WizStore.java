package br.com.pointel.jarch.mage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author emuvi
 */
public class WizStore {
    
    private static final File FOLDER_STORE = new File("store");
    
    public static void openFolder() throws Exception {
        WizSwing.open(FOLDER_STORE);
    }
 
    public static List<String> getNames() {
        var result = new ArrayList<String>();
        try {
            Files.createDirectories(FOLDER_STORE.toPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (var inside : FOLDER_STORE.listFiles()) {
            result.add(inside.getName());
        }
        return result;
    }
    
    public static void loadNames(DefaultComboBoxModel<String> field) {
        field.removeAllElements();
        for(var storeName : getNames()) {
            field.addElement(storeName);
        }
    }
    
    public static boolean create(String name) throws Exception {
        return new File(FOLDER_STORE, name).createNewFile();
    }
    
    public static boolean delete(String name) throws Exception {
        return new File(FOLDER_STORE, name).delete();
    }
    
    public static String loadText(String name) throws Exception {
        var storeFile = new File(FOLDER_STORE, name);
        return Files.readString(storeFile.toPath(), StandardCharsets.UTF_8);
    }
    
    public static void saveText(String name, String source) throws Exception {
        var storeFile = new File(FOLDER_STORE, name);
        Files.writeString(storeFile.toPath(), source, StandardCharsets.UTF_8);
    }
    
    public static BufferedImage loadImage(String name) throws Exception {
        var storeFile = new File(FOLDER_STORE, name);
        return ImageIO.read(storeFile);
    }
    
    public static void saveImage(String name, BufferedImage image) throws Exception {
        var storeFile = new File(FOLDER_STORE, name);
        var formatName = FilenameUtils.getExtension(name).toUpperCase();
        ImageIO.write(image, formatName, storeFile);
    }
    
    public static <T extends Serializable> T loadObject(String name, Class<T> clazz) throws Exception {
        var storeFile = new File(FOLDER_STORE, name);
        try (var fileIn = new FileInputStream(storeFile);
                var objIn = new ObjectInputStream(fileIn)) {
            return clazz.cast(objIn.readObject());
        }
    }
    
    public static <T extends Serializable> void saveObject(String name, T object) throws Exception {
        var storeFile = new File(FOLDER_STORE, name);
        try (var fileOut = new FileOutputStream(storeFile);
                var objOut = new ObjectOutputStream(fileOut)) {
            objOut.writeObject(object);
        }
    }
    
}
