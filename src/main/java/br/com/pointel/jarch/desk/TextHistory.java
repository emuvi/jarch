package br.com.pointel.jarch.desk;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author emuvi
 */
public class TextHistory {

    private final Integer size;
    private final Map<File, History> notesHistory = new HashMap<>();

    public TextHistory(Integer size) {
        this.size = size;
    }

    public void push(File file, String source) {
        getHistory(file).push(source);
    }

    public void undo(File file) throws Exception {
        getHistory(file).undo();
    }

    public void redo(File file) throws Exception {
        getHistory(file).redo();
    }
    
    public void clean() {
        notesHistory.clear();
    }

    private History getHistory(File file) {
        var history = notesHistory.get(file);
        if (history == null) {
            history = new History(file);
            notesHistory.put(file, history);
        }
        return history;
    }

    private class History {

        private final File file;
        private final List<String> sources;
        private Integer index;

        public History(File file) {
            this.file = file;
            this.sources = new ArrayList<>();
            this.index = -1;
        }

        public void push(String source) {
            assert source != null;
            if (index > -1 && source.equals(sources.get(index))) {
                return;
            }
            if (index + 1 < sources.size() && source.equals(sources.get(index + 1))) {
                index++;
                return;
            }
            index++;
            sources.add(index, source);
            while (index > size) {
                sources.remove(0);
                index--;
            }
            while (index > sources.size() -1) {
                sources.remove(sources.size() -1);
            }
        }

        public void undo() throws Exception {
            if (index < 0) {
                throw new Exception("No source in the history to undo.");
            }
            rewrite();
            index--;
        }

        public void redo() throws Exception {
            if (index >= sources.size() - 1) {
                throw new Exception("No source in the history to redo.");
            }
            index++;
            rewrite();
        }

        private void rewrite() throws Exception {
            var source = sources.get(index);
            Files.writeString(file.toPath(), source, StandardCharsets.UTF_8);
        }

    }

}
