package com.vidlus.jarch.flow;

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

/**
 * A robust File Transfer Protocol (FTP) client abstraction that utilizes Apache Commons Net.
 * Simplifies connecting, logging in, uploading, downloading, and managing directories
 * over an FTP connection. Automatically supports Pace logging and execution halting.
 */
public class FTP implements AutoCloseable {

    private final String host;
    private final int port;
    private final String user;
    private final String pass;
    private final Pace pace;
    private final FTPClient client;

    /**
     * Constructs an FTP client for the default port 21.
     *
     * @param host the FTP server hostname or IP
     * @param user the login username
     * @param pass the login password
     */
    public FTP(String host, String user, String pass) {
        this(host, 21, user, pass, null);
    }

    /**
     * Constructs an FTP client for a specific port.
     *
     * @param host the FTP server hostname or IP
     * @param port the FTP server port
     * @param user the login username
     * @param pass the login password
     */
    public FTP(String host, int port, String user, String pass) {
        this(host, port, user, pass, null);
    }

    /**
     * Constructs an FTP client with a custom Pace logger.
     *
     * @param host the FTP server hostname or IP
     * @param port the FTP server port
     * @param user the login username
     * @param pass the login password
     * @param pace the Pace progress and lifecycle tracker
     */
    public FTP(String host, int port, String user, String pass, Pace pace) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.pass = pass;
        this.pace = pace != null ? pace : new Pace(LoggerFactory.getLogger(FTP.class));
        this.client = new FTPClient();
    }

    /**
     * @return true if the underlying Apache FTPClient is actively connected
     */
    public boolean isConnected() {
        return client.isConnected();
    }

    /**
     * Initiates the connection, logs in, sets Passive Mode, and configures BINARY transfer mode.
     *
     * @return this FTP instance for method chaining
     * @throws Exception if the connection is refused or login fails
     */
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

    /**
     * Gracefully logs out and disconnects the active session.
     */
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

    /**
     * Allows this class to be used in try-with-resources blocks.
     */
    @Override
    public void close() {
        disconnect();
    }

    /**
     * Lists all file and folder names at the given remote path.
     *
     * @param path the remote directory path
     * @return a list of names
     * @throws Exception if an I/O error occurs
     */
    public List<String> listNames(String path) throws Exception {
        pace.waitIfPausedAndThrowIfStopped();
        pace.info("Listing names in " + path);
        var names = client.listNames(path);
        return names != null ? Arrays.asList(names) : List.of();
    }

    /**
     * Lists detailed file objects (metadata) at the given remote path.
     *
     * @param path the remote directory path
     * @return a list of FTPFiles
     * @throws Exception if an I/O error occurs
     */
    public List<FTPFile> listFiles(String path) throws Exception {
        pace.waitIfPausedAndThrowIfStopped();
        pace.info("Listing files in " + path);
        var files = client.listFiles(path);
        return files != null ? Arrays.asList(files) : List.of();
    }

    /**
     * Streams a local file up to a remote path.
     *
     * @param local  the physical file to upload
     * @param remote the target filename on the FTP server
     * @throws Exception if an I/O error occurs
     */
    public void upload(File local, String remote) throws Exception {
        pace.waitIfPausedAndThrowIfStopped();
        pace.info("Uploading " + local.getName() + " to " + remote);
        try (InputStream input = new FileInputStream(local)) {
            if (!client.storeFile(remote, input)) {
                throw new Exception("Could not upload file.");
            }
        }
    }

    /**
     * Downloads a remote file down to a local physical file.
     *
     * @param remote the source filename on the FTP server
     * @param local  the target physical file to save into
     * @throws Exception if an I/O error occurs
     */
    public void download(String remote, File local) throws Exception {
        pace.waitIfPausedAndThrowIfStopped();
        pace.info("Downloading " + remote + " to " + local.getName());
        try (OutputStream output = new FileOutputStream(local)) {
            if (!client.retrieveFile(remote, output)) {
                throw new Exception("Could not download file.");
            }
        }
    }

    /**
     * Safely changes the working directory to a given path, creating any missing 
     * parent directories sequentially along the way.
     *
     * @param path the target directory path
     * @throws Exception if folder creation or traversal fails
     */
    public void openWithMakeDir(String path) throws Exception {
        pace.waitIfPausedAndThrowIfStopped();
        pace.info("Checking/Creating directory " + path);
        if (client.changeWorkingDirectory(path)) {
            return;
        }
        if (path.startsWith("/")) {
            client.changeWorkingDirectory("/");
        }
        for (String part : path.split("/")) {
            if (part.isEmpty()) {
                continue;
            }
            if (!client.changeWorkingDirectory(part)) {
                if (!client.makeDirectory(part)) {
                    throw new Exception("Could not create directory: " + part);
                }
                if (!client.changeWorkingDirectory(part)) {
                    throw new Exception("Could not open directory: " + part);
                }
            }
        }
    }

    /**
     * Creates a new directory at the specified path.
     *
     * @param path the directory name
     * @throws Exception if an I/O error occurs
     */
    public void makeDir(String path) throws Exception {
        pace.waitIfPausedAndThrowIfStopped();
        pace.info("Making directory " + path);
        if (!client.makeDirectory(path)) {
            throw new Exception("Could not create directory.");
        }
    }

    /**
     * Removes an empty directory at the specified path.
     *
     * @param path the directory to remove
     * @throws Exception if the directory does not exist or isn't empty
     */
    public void removeDir(String path) throws Exception {
        pace.waitIfPausedAndThrowIfStopped();
        pace.info("Removing directory " + path);
        if (!client.removeDirectory(path)) {
            throw new Exception("Could not remove directory.");
        }
    }

    /**
     * Deletes a remote file.
     *
     * @param path the file to delete
     * @throws Exception if an I/O error occurs
     */
    public void removeFile(String path) throws Exception {
        pace.waitIfPausedAndThrowIfStopped();
        pace.info("Removing file " + path);
        if (!client.deleteFile(path)) {
            throw new Exception("Could not remove file.");
        }
    }

    /**
     * Renames a remote file or folder.
     *
     * @param from the existing name
     * @param to   the new name
     * @throws Exception if an I/O error occurs
     */
    public void rename(String from, String to) throws Exception {
        pace.waitIfPausedAndThrowIfStopped();
        pace.info("Renaming " + from + " to " + to);
        if (!client.rename(from, to)) {
            throw new Exception("Could not rename file.");
        }
    }

    /**
     * Changes the current working directory to the specified path.
     *
     * @param path the new working directory
     * @throws Exception if the directory doesn't exist
     */
    public void changeDir(String path) throws Exception {
        pace.waitIfPausedAndThrowIfStopped();
        pace.info("Changing directory to " + path);
        if (!client.changeWorkingDirectory(path)) {
            throw new Exception("Could not change directory.");
        }
    }

    /**
     * Moves the working directory up one level to its parent.
     *
     * @throws Exception if the operation fails
     */
    public void changeToParentDir() throws Exception {
        pace.waitIfPausedAndThrowIfStopped();
        pace.info("Changing to parent directory");
        if (!client.changeToParentDirectory()) {
            throw new Exception("Could not change to parent directory.");
        }
    }

    /**
     * @return the absolute path of the current working directory on the server
     * @throws Exception if an I/O error occurs
     */
    public String printWorkingDirectory() throws Exception {
        pace.waitIfPausedAndThrowIfStopped();
        return client.printWorkingDirectory();
    }

    /**
     * Checks if a file or directory exists on the server.
     *
     * @param path the path to check
     * @return true if the resource exists
     * @throws Exception if an I/O error occurs
     */
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

    /**
     * Provides access to the raw Apache Commons FTPClient instance.
     *
     * @return the underlying FTPClient
     */
    public FTPClient getClient() {
        return client;
    }

}
