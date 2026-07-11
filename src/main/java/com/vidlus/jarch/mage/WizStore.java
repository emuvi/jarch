package com.vidlus.jarch.mage;

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
 * A utility class for localized file storage management.
 * Provides high-level abstractions for saving and loading various data types
 * (text, images, objects, and raw bytes) to a designated storage directory.
 */
public class WizStore {
    
    /**
     * The target directory where all WizStore operations take place.
     * Defaults to a folder named "store" in the current working directory.
     */
    private static volatile File folder = new File("store");

    private WizStore() {
    }

    /**
     * Retrieves the current base folder used for storage operations.
     *
     * @return the active storage folder
     */
    public static File getFolder() {
        return folder;
    }

    /**
     * Reassigns the base folder used for all subsequent storage operations.
     *
     * @param customFolder the new folder location to use
     */
    public static void setFolder(File customFolder) {
        if (customFolder != null) {
            WizStore.folder = customFolder;
        }
    }

    /**
     * Opens the current storage folder natively using the operating system's default file explorer.
     *
     * @throws Exception if opening the folder fails or the OS is unsupported
     */
    public static void openFolder() throws Exception {
        WizGUI.open(folder);
    }
    
    // =========================================================================
    // STORE MANAGEMENT
    // =========================================================================

    /**
     * Silently ensures that the active storage folder physically exists on disk.
     * If it does not exist, it attempts to create the directory structure.
     */
    public static void prepareFolder() {
        if (!folder.exists()) {
            try {
                Files.createDirectories(folder.toPath());
            } catch (Exception e) {
                // Ignore if we can't create it immediately, subsequent writes will fail loudly.
            }
        }
    }

    /**
     * Retrieves a list of all file names currently present inside the active storage folder.
     *
     * @return a List of file name strings. Returns an empty list if the folder does not exist or is empty.
     */
    public static List<String> getNames() {
        var result = new ArrayList<String>();
        prepareFolder();
        if (!folder.exists()) {
            return result;
        }
        File[] files = folder.listFiles();
        if (files != null) {
            for (File inside : files) {
                if (inside.isFile()) {
                    result.add(inside.getName());
                }
            }
        }
        return result;
    }
    
    /**
     * Populates a Swing DefaultComboBoxModel with the names of all files in the storage folder.
     * Clears any existing elements in the model first.
     *
     * @param field the combo box model to populate
     */
    public static void loadNames(DefaultComboBoxModel<String> field) {
        if (field == null) return;
        field.removeAllElements();
        for (var storeName : getNames()) {
            field.addElement(storeName);
        }
    }
    
    /**
     * Checks if a specific file name exists inside the current storage folder.
     *
     * @param name the exact file name (including extension)
     * @return true if the file exists and is readable, false otherwise
     */
    public static boolean exists(String name) {
        if (WizString.isEmpty(name)) return false;
        return new File(folder, name).exists();
    }

    /**
     * Creates a new, empty file inside the storage folder.
     *
     * @param name the name of the file to create
     * @return true if the file was created successfully, false if it already exists
     * @throws Exception if a physical I/O error occurs
     */
    public static boolean create(String name) throws Exception {
        prepareFolder();
        return new File(folder, name).createNewFile();
    }
    
    /**
     * Deletes a specific file from the storage folder.
     *
     * @param name the exact file name to delete
     * @return true if the file was successfully deleted, false otherwise
     */
    public static boolean delete(String name) {
        if (WizString.isEmpty(name)) return false;
        return new File(folder, name).delete();
    }

    /**
     * Renames an existing file within the storage folder.
     *
     * @param oldName the current name of the file
     * @param newName the desired new name for the file
     * @return true if the rename was successful, false if the old file is missing or the new file already exists
     */
    public static boolean rename(String oldName, String newName) {
        if (WizString.isEmpty(oldName) || WizString.isEmpty(newName)) return false;
        File oldFile = new File(folder, oldName);
        File newFile = new File(folder, newName);
        if (!oldFile.exists() || newFile.exists()) return false;
        return oldFile.renameTo(newFile);
    }

    /**
     * Deletes every file currently stored in the active storage folder.
     * Does not delete the folder itself.
     */
    public static void clear() {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
    }

    // =========================================================================
    // I/O OPERATIONS (TEXT)
    // =========================================================================

    /**
     * Reads the entire contents of a text file from the storage folder into a String.
     * Assumes UTF-8 encoding.
     *
     * @param name the name of the text file
     * @return the full text content, or null if the file does not exist
     * @throws Exception if an I/O error occurs during reading
     */
    public static String loadText(String name) throws Exception {
        var storeFile = new File(folder, name);
        if (!storeFile.exists()) return null;
        return Files.readString(storeFile.toPath(), StandardCharsets.UTF_8);
    }
    
    /**
     * Writes a block of text into a file in the storage folder.
     * Creates or overwrites the file using UTF-8 encoding.
     *
     * @param name   the exact name of the file to save to
     * @param source the text content to write
     * @throws Exception if an I/O error occurs during writing
     */
    public static void saveText(String name, String source) throws Exception {
        prepareFolder();
        var storeFile = new File(folder, name);
        if (source == null) source = "";
        Files.writeString(storeFile.toPath(), source, StandardCharsets.UTF_8);
    }
    
    // =========================================================================
    // I/O OPERATIONS (IMAGE)
    // =========================================================================

    /**
     * Reads an image file from the storage folder into a BufferedImage object.
     *
     * @param name the exact name of the image file (including extension)
     * @return the loaded BufferedImage, or null if the file does not exist
     * @throws Exception if an I/O error or decoding failure occurs
     */
    public static BufferedImage loadImage(String name) throws Exception {
        var storeFile = new File(folder, name);
        if (!storeFile.exists()) return null;
        return ImageIO.read(storeFile);
    }
    
    /**
     * Saves a BufferedImage object to the storage folder.
     * The image format is automatically determined by the file extension (e.g., .png, .jpg).
     * Defaults to PNG format if no extension is provided.
     *
     * @param name  the exact name of the file to save to
     * @param image the BufferedImage data to save
     * @throws Exception if an I/O error occurs during writing
     */
    public static void saveImage(String name, BufferedImage image) throws Exception {
        if (image == null) return;
        prepareFolder();
        var storeFile = new File(folder, name);
        var formatName = FilenameUtils.getExtension(name).toUpperCase();
        if (formatName.isEmpty()) formatName = "PNG";
        ImageIO.write(image, formatName, storeFile);
    }
    
    // =========================================================================
    // I/O OPERATIONS (SERIALIZED OBJECTS)
    // =========================================================================

    /**
     * Deserializes a Java Object stored in a file within the storage folder.
     *
     * @param <T>   the expected type of the object
     * @param name  the exact name of the data file
     * @param clazz the Class definition matching the expected object
     * @return the strongly typed deserialized object, or null if the file does not exist
     * @throws Exception if an I/O error occurs, class is missing, or type casting fails
     */
    public static <T extends Serializable> T loadObject(String name, Class<T> clazz) throws Exception {
        var storeFile = new File(folder, name);
        if (!storeFile.exists()) return null;
        try (var fileIn = new FileInputStream(storeFile);
             var objIn = new ObjectInputStream(fileIn)) {
            return clazz.cast(objIn.readObject());
        }
    }
    
    /**
     * Serializes a Java Object and saves it to a file in the storage folder.
     *
     * @param <T>    the type of the object
     * @param name   the exact name of the data file to save to
     * @param object the Serializable object to persist
     * @throws Exception if an I/O error occurs during serialization
     */
    public static <T extends Serializable> void saveObject(String name, T object) throws Exception {
        if (object == null) return;
        prepareFolder();
        var storeFile = new File(folder, name);
        try (var fileOut = new FileOutputStream(storeFile);
             var objOut = new ObjectOutputStream(fileOut)) {
            objOut.writeObject(object);
        }
    }

    // =========================================================================
    // I/O OPERATIONS (RAW BYTES)
    // =========================================================================

    /**
     * Reads the entire contents of a file from the storage folder into a raw byte array.
     *
     * @param name the exact name of the file
     * @return the binary data array, or null if the file does not exist
     * @throws Exception if an I/O error occurs during reading
     */
    public static byte[] loadBytes(String name) throws Exception {
        var storeFile = new File(folder, name);
        if (!storeFile.exists()) return null;
        return Files.readAllBytes(storeFile.toPath());
    }

    /**
     * Writes an array of raw binary data to a file in the storage folder.
     *
     * @param name the exact name of the file to save to
     * @param data the binary data array to write
     * @throws Exception if an I/O error occurs during writing
     */
    public static void saveBytes(String name, byte[] data) throws Exception {
        if (data == null) return;
        prepareFolder();
        var storeFile = new File(folder, name);
        Files.write(storeFile.toPath(), data);
    }
    
}
