package br.com.pointel.jarch.mage;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class WizText {
    
    public static String read(File file) throws Exception {
        return Files.readString(file.toPath(), StandardCharsets.UTF_8);
    }
    
    public static void write(File file, String chars) throws Exception {
        Files.writeString(file.toPath(), chars, StandardCharsets.UTF_8, 
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }
    
    public static void append(File file, String chars) throws Exception {
        Files.writeString(file.toPath(), chars, StandardCharsets.UTF_8, 
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }
    
}