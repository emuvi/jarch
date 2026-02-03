package br.com.pointel.jarch.mage;

import java.io.File;
import java.nio.file.Files;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class WizTextTest {

    private File tempFile;

    @BeforeEach
    public void setUp() throws Exception {
        tempFile = File.createTempFile("test", ".txt");
    }

    @AfterEach
    public void tearDown() throws Exception {
        if (tempFile != null && tempFile.exists()) {
            tempFile.delete();
        }
    }

    @Test
    public void testWriteAndRead() throws Exception {
        String content = "Hello, World!";
        WizText.write(tempFile, content);
        String readContent = WizText.read(tempFile);
        assertEquals(content, readContent);
    }

    @Test
    public void testAppend() throws Exception {
        String initialContent = "Initial content.";
        WizText.write(tempFile, initialContent);

        String appendedContent = " Appended content.";
        WizText.append(tempFile, appendedContent);

        String finalContent = WizText.read(tempFile);
        assertEquals(initialContent + appendedContent, finalContent);
    }
    
    @Test
    public void testReadNonExistent() throws Exception {
        File nonExistent = new File("non-existent-file-for-sure.txt");
        if (nonExistent.exists()) {
            nonExistent.delete();
        }
        assertThrows(java.nio.file.NoSuchFileException.class, () -> {
            WizText.read(nonExistent);
        });
    }

}
