package br.com.pointel.jarch.flow;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.LoggerFactory;

public class FTP implements AutoCloseable {

    private final String host;
    private final int port;
    private final String user;
    private final String pass;
    private final Pace pace;
    private final FTPClient client;

    public FTP(String host, String user, String pass) {
        this(host, 21, user, pass, null);
    }

    public FTP(String host, int port, String user, String pass) {
        this(host, port, user, pass, null);
    }

    public FTP(String host, int port, String user, String pass, Pace pace) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.pass = pass;
        this.pace = pace != null ? pace : new Pace(LoggerFactory.getLogger(FTP.class));
        this.client = new FTPClient();
    }

    public FTP connect() throws Exception {
        pace.info("Connecting to " + host + ":" + port);
        client.connect(host, port);
        int reply = client.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            client.disconnect();
            throw new Exception("Exception in connecting to FTP Server");
        }
        pace.info("Logging in as " + user);
        if (!client.login(user, pass)) {
            throw new Exception("Exception in logging in to FTP Server");
        }
        client.enterLocalPassiveMode();
        client.setFileType(org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE);
        return this;
    }

    public void disconnect() {
        if (client.isConnected()) {
            try {
                client.logout();
                client.disconnect();
            } catch (IOException e) {
                pace.error("Error disconnecting", e);
            }
        }
    }

    @Override
    public void close() {
        disconnect();
    }

    public List<String> listNames(String path) throws Exception {
        pace.waitIfPausedAndThrowIfStopped();
        pace.info("Listing names in " + path);
        var names = client.listNames(path);
        return names != null ? Arrays.asList(names) : List.of();
    }

    public List<FTPFile> listFiles(String path) throws Exception {
        pace.waitIfPausedAndThrowIfStopped();
        pace.info("Listing files in " + path);
        var files = client.listFiles(path);
        return files != null ? Arrays.asList(files) : List.of();
    }

    public void upload(File local, String remote) throws Exception {
        pace.waitIfPausedAndThrowIfStopped();
        pace.info("Uploading " + local.getName() + " to " + remote);
        try (InputStream input = new FileInputStream(local)) {
            if (!client.storeFile(remote, input)) {
                throw new Exception("Could not upload file.");
            }
        }
    }

    public void download(String remote, File local) throws Exception {
        pace.waitIfPausedAndThrowIfStopped();
        pace.info("Downloading " + remote + " to " + local.getName());
        try (OutputStream output = new FileOutputStream(local)) {
            if (!client.retrieveFile(remote, output)) {
                throw new Exception("Could not download file.");
            }
        }
    }

    public void makeDir(String path) throws Exception {
        pace.waitIfPausedAndThrowIfStopped();
        pace.info("Making directory " + path);
        if (!client.makeDirectory(path)) {
            throw new Exception("Could not create directory.");
        }
    }

    public void removeDir(String path) throws Exception {
        pace.waitIfPausedAndThrowIfStopped();
        pace.info("Removing directory " + path);
        if (!client.removeDirectory(path)) {
            throw new Exception("Could not remove directory.");
        }
    }

    public void removeFile(String path) throws Exception {
        pace.waitIfPausedAndThrowIfStopped();
        pace.info("Removing file " + path);
        if (!client.deleteFile(path)) {
            throw new Exception("Could not remove file.");
        }
    }

    public void rename(String from, String to) throws Exception {
        pace.waitIfPausedAndThrowIfStopped();
        pace.info("Renaming " + from + " to " + to);
        if (!client.rename(from, to)) {
            throw new Exception("Could not rename file.");
        }
    }

    public void changeDir(String path) throws Exception {
        pace.waitIfPausedAndThrowIfStopped();
        pace.info("Changing directory to " + path);
        if (!client.changeWorkingDirectory(path)) {
            throw new Exception("Could not change directory.");
        }
    }

    public void changeToParentDir() throws Exception {
        pace.waitIfPausedAndThrowIfStopped();
        pace.info("Changing to parent directory");
        if (!client.changeToParentDirectory()) {
            throw new Exception("Could not change to parent directory.");
        }
    }

    public String printWorkingDirectory() throws Exception {
        pace.waitIfPausedAndThrowIfStopped();
        return client.printWorkingDirectory();
    }

    public boolean exists(String path) throws Exception {
        pace.waitIfPausedAndThrowIfStopped();
        var names = client.listNames(path);
        if (names != null && names.length > 0) {
            return true;
        }
        try {
            var current = client.printWorkingDirectory();
            if (client.changeWorkingDirectory(path)) {
                client.changeWorkingDirectory(current);
                return true;
            }
        } catch (Exception e) {
            // Ignore
        }
        return false;
    }

    public FTPClient getClient() {
        return client;
    }

}
