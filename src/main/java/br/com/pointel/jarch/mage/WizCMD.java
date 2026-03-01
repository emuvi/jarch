package br.com.pointel.jarch.mage;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for executing external commands and managing process streams.
 */
public class WizCMD {
    
    private static final Logger log = LoggerFactory.getLogger(WizCMD.class);

    private WizCMD() {}

    /**
     * Makes a command runner with the given command parts.
     *
     * @param command the command and its arguments
     * @return a Runner instance
     */
    public static Runner make(String... command) {
        return new Runner(command);
    }
    
    /**
     * Makes a command runner with the given command list.
     *
     * @param command the command and its arguments
     * @return a Runner instance
     */
    public static Runner make(List<String> command) {
        return new Runner(command.toArray(String[]::new));
    }

    /**
     * Executes a command and returns the exit code.
     *
     * @param command the command and its arguments
     * @return the exit code of the process
     * @throws Exception if an error occurs during execution
     */
    public static int run(String... command) throws Exception {
        return make(command).run();
    }

    /**
     * Executes a command and returns the standard output as a String.
     *
     * @param command the command and its arguments
     * @return the standard output of the process
     * @throws Exception if an error occurs during execution
     */
    public static String runOutput(String... command) throws Exception {
        return make(command).runOutput();
    }
    
    /**
     * Executes a command and returns the standard output as a List of Strings.
     *
     * @param command the command and its arguments
     * @return the standard output lines of the process
     * @throws Exception if an error occurs during execution
     */
    public static List<String> runLines(String... command) throws Exception {
        return make(command).runLines();
    }

    /**
     * Executes a command and returns the standard output and error output as a String.
     *
     * @param command the command and its arguments
     * @return the standard and error output of the process
     * @throws Exception if an error occurs during execution
     */
    public static String runOutputWithErrors(String... command) throws Exception {
        return make(command).redirectError().runOutput();
    }
    
    /**
     * Executes a command and returns the standard output and error output as a List of Strings.
     *
     * @param command the command and its arguments
     * @return the standard and error output lines of the process
     * @throws Exception if an error occurs during execution
     */
    public static List<String> runLinesWithErrors(String... command) throws Exception {
        return make(command).redirectError().runLines();
    }

    /**
     * Starts a command asynchronously.
     *
     * @param command the command and its arguments
     * @return the started Process
     * @throws Exception if an error occurs during execution
     */
    public static Process runAsync(String... command) throws Exception {
        return make(command).runAsync();
    }

    /**
     * Starts a command asynchronously and consumes the standard output.
     *
     * @param onOutput the consumer for standard output lines
     * @param command the command and its arguments
     * @return the started Process
     * @throws Exception if an error occurs during execution
     */
    public static Process runAsync(Consumer<String> onOutput, String... command) throws Exception {
        return make(command).onOutput(onOutput).runAsync();
    }

    /**
     * Starts a command asynchronously and consumes the standard output and error output.
     *
     * @param onOutput the consumer for standard output lines
     * @param onError the consumer for error output lines
     * @param command the command and its arguments
     * @return the started Process
     * @throws Exception if an error occurs during execution
     */
    public static Process runAsync(Consumer<String> onOutput, Consumer<String> onError, String... command) throws Exception {
        return make(command).onOutput(onOutput).onError(onError).runAsync();
    }

    /**
     * Starts a command asynchronously and consumes the standard output (with error stream redirected to it).
     *
     * @param onOutput the consumer for standard output lines (including errors)
     * @param command the command and its arguments
     * @return the started Process
     * @throws Exception if an error occurs during execution
     */
    public static Process runAsyncWithErrors(Consumer<String> onOutput, String... command) throws Exception {
        return make(command).redirectError().onOutput(onOutput).runAsync();
    }
    
    /**
     * Reads an InputStream into a String using UTF-8.
     *
     * @param input the InputStream to read
     * @return the content of the stream as a String
     * @throws IOException if an I/O error occurs
     */
    public static String read(InputStream input) throws IOException {
        return read(input, StandardCharsets.UTF_8);
    }

    /**
     * Reads an InputStream into a String using the specified Charset.
     *
     * @param input the InputStream to read
     * @param charset the Charset to use
     * @return the content of the stream as a String
     * @throws IOException if an I/O error occurs
     */
    public static String read(InputStream input, Charset charset) throws IOException {
        try (var reader = new BufferedReader(new InputStreamReader(input, charset))) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }
    
    /**
     * Reads an InputStream into a List of Strings using UTF-8.
     *
     * @param input the InputStream to read
     * @return the lines of the stream
     * @throws IOException if an I/O error occurs
     */
    public static List<String> readLines(InputStream input) throws IOException {
        return readLines(input, StandardCharsets.UTF_8);
    }
    
    /**
     * Reads an InputStream into a List of Strings using the specified Charset.
     *
     * @param input the InputStream to read
     * @param charset the Charset to use
     * @return the lines of the stream
     * @throws IOException if an I/O error occurs
     */
    public static List<String> readLines(InputStream input, Charset charset) throws IOException {
        try (var reader = new BufferedReader(new InputStreamReader(input, charset))) {
            return reader.lines().toList();
        }
    }
    
    /**
     * Pipes data from an InputStream to an OutputStream.
     *
     * @param input the source InputStream
     * @param output the destination OutputStream
     * @throws IOException if an I/O error occurs
     */
    public static void pipe(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[8192];
        int n;
        while ((n = input.read(buffer)) != -1) {
            output.write(buffer, 0, n);
        }
    }

    /**
     * Parses a command string into a list of arguments, handling quotes and escapes.
     *
     * @param command the command string to parse
     * @return a list of parsed arguments
     */
    public static List<String> parse(String command) {
        List<String> tokens = new ArrayList<>();
        StringBuilder currentToken = new StringBuilder();
        boolean inQuotes = false;
        char quoteChar = 0;
        boolean escaped = false;

        for (int i = 0; i < command.length(); i++) {
            char c = command.charAt(i);
            if (escaped) {
                currentToken.append(c);
                escaped = false;
            } else if (c == '\\') {
                escaped = true;
            } else if (inQuotes) {
                if (c == quoteChar) {
                    inQuotes = false;
                } else {
                    currentToken.append(c);
                }
            } else {
                if (c == '"' || c == '\'') {
                    inQuotes = true;
                    quoteChar = c;
                } else if (Character.isWhitespace(c)) {
                    if (currentToken.length() > 0) {
                        tokens.add(currentToken.toString());
                        currentToken.setLength(0);
                    }
                } else {
                    currentToken.append(c);
                }
            }
        }
        if (currentToken.length() > 0) {
            tokens.add(currentToken.toString());
        }
        return tokens;
    }

    /**
     * Fluent builder and executor for external processes.
     */
    public static class Runner {
        
        private final List<String> command;
        private File directory;
        private final Map<String, String> environment = new HashMap<>();
        private boolean redirectErrorStream = false;
        private Consumer<String> onOutput;
        private Consumer<String> onError;
        private String input;
        private Charset charset = StandardCharsets.UTF_8;
        private long timeout = 0;
        private TimeUnit timeUnit = TimeUnit.SECONDS;

        /**
         * Creates a new Runner with the given command parts.
         *
         * @param command the command and its arguments
         */
        public Runner(String... command) {
            this.command = new ArrayList<>(Arrays.asList(command));
        }

        /**
         * Adds a single argument to the command.
         *
         * @param arg the argument to add
         * @return this Runner instance
         */
        public Runner arg(String arg) {
            this.command.add(arg);
            return this;
        }

        /**
         * Adds multiple arguments to the command.
         *
         * @param args the arguments to add
         * @return this Runner instance
         */
        public Runner args(String... args) {
            this.command.addAll(Arrays.asList(args));
            return this;
        }

        /**
         * Sets the working directory for the process.
         *
         * @param directory the working directory
         * @return this Runner instance
         */
        public Runner dir(File directory) {
            this.directory = directory;
            return this;
        }

        /**
         * Sets the working directory for the process.
         *
         * @param directory the path to the working directory
         * @return this Runner instance
         */
        public Runner dir(String directory) {
            this.directory = new File(directory);
            return this;
        }

        /**
         * Sets an environment variable for the process.
         *
         * @param key the environment variable name
         * @param value the environment variable value
         * @return this Runner instance
         */
        public Runner env(String key, String value) {
            this.environment.put(key, value);
            return this;
        }

        /**
         * Sets multiple environment variables for the process.
         *
         * @param env a map of environment variables
         * @return this Runner instance
         */
        public Runner env(Map<String, String> env) {
            this.environment.putAll(env);
            return this;
        }

        /**
         * Redirects the error stream to the standard output stream.
         *
         * @return this Runner instance
         */
        public Runner redirectError() {
            this.redirectErrorStream = true;
            return this;
        }

        /**
         * Sets a consumer to handle standard output lines.
         *
         * @param onOutput the consumer for output lines
         * @return this Runner instance
         */
        public Runner onOutput(Consumer<String> onOutput) {
            this.onOutput = onOutput;
            return this;
        }

        /**
         * Sets a consumer to handle error output lines.
         *
         * @param onError the consumer for error lines
         * @return this Runner instance
         */
        public Runner onError(Consumer<String> onError) {
            this.onError = onError;
            return this;
        }

        /**
         * Sets the input string to be written to the process's standard input.
         *
         * @param input the input string
         * @return this Runner instance
         */
        public Runner input(String input) {
            this.input = input;
            return this;
        }

        /**
         * Sets the charset for stream handling.
         *
         * @param charset the charset to use
         * @return this Runner instance
         */
        public Runner charset(Charset charset) {
            this.charset = charset;
            return this;
        }

        /**
         * Sets a timeout for the process execution.
         *
         * @param timeout the timeout value
         * @param timeUnit the time unit of the timeout value
         * @return this Runner instance
         */
        public Runner timeout(long timeout, TimeUnit timeUnit) {
            this.timeout = timeout;
            this.timeUnit = timeUnit;
            return this;
        }

        /**
         * Configures and starts the process.
         *
         * @return the started Process
         * @throws IOException if an I/O error occurs
         */
        public Process build() throws IOException {
            var builder = new ProcessBuilder(command);
            if (directory != null) {
                builder.directory(directory);
            }
            if (!environment.isEmpty()) {
                builder.environment().putAll(environment);
            }
            builder.redirectErrorStream(redirectErrorStream);
            return builder.start();
        }

        /**
         * Executes the process and waits for it to complete.
         *
         * @return the exit code of the process
         * @throws Exception if an error occurs or the process times out
         */
        public int run() throws Exception {
            var process = build();
            handleInput(process);
            var threads = handleOutput(process);
            if (timeout > 0) {
                if (!process.waitFor(timeout, timeUnit)) {
                    process.destroy();
                    throw new Exception("Process timed out");
                }
            } else {
                process.waitFor();
            }
            for (var thread : threads) {
                try {
                    thread.join(1000);
                } catch (InterruptedException e) {
                    // Ignore
                }
            }
            return process.exitValue();
        }

        /**
         * Executes the process and captures the standard output as a String.
         *
         * @return the standard output
         * @throws Exception if an error occurs
         */
        public String runOutput() throws Exception {
            var output = new StringBuilder();
            this.onOutput = line -> output.append(line).append(System.lineSeparator());
            run();
            return output.toString().trim();
        }
        
        /**
         * Executes the process and captures the standard output as a List of Strings.
         *
         * @return the standard output lines
         * @throws Exception if an error occurs
         */
        public List<String> runLines() throws Exception {
            var lines = new ArrayList<String>();
            this.onOutput = lines::add;
            run();
            return lines;
        }

        /**
         * Starts the process asynchronously.
         *
         * @return the started Process
         * @throws IOException if an I/O error occurs
         */
        public Process runAsync() throws IOException {
            var process = build();
            handleInput(process);
            handleOutput(process);
            return process;
        }

        private void handleInput(Process process) throws IOException {
            if (input != null) {
                try (OutputStream os = process.getOutputStream()) {
                    os.write(input.getBytes(charset));
                    os.flush();
                }
            }
        }

        private List<Thread> handleOutput(Process process) {
            var threads = new ArrayList<Thread>();
            var outThread = new Thread(() -> {
                try (var reader = new BufferedReader(new InputStreamReader(process.getInputStream(), charset))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (onOutput != null) {
                            onOutput.accept(line);
                        }
                    }
                } catch (IOException e) {
                    log.error("Error reading output stream", e);
                }
            });
            outThread.start();
            threads.add(outThread);

            if (!redirectErrorStream) {
                var errThread = new Thread(() -> {
                    try (var reader = new BufferedReader(new InputStreamReader(process.getErrorStream(), charset))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            if (onError != null) {
                                onError.accept(line);
                            }
                        }
                    } catch (IOException e) {
                        log.error("Error reading error stream", e);
                    }
                });
                errThread.start();
                threads.add(errThread);
            }
            return threads;
        }
    }

}
