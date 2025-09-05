package br.com.pointel.jarch.flow;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import org.slf4j.LoggerFactory;
import br.com.pointel.jarch.data.BasedLink;
import br.com.pointel.jarch.data.Select;
import br.com.pointel.jarch.data.TableHead;

public class CSVExport implements Runnable {

    private final BasedLink origin;
    private final File destiny;
    private final Pace pace;

    public CSVExport(BasedLink origin, File destiny) {
        this(origin, destiny, null);
    }

    public CSVExport(BasedLink origin, File destiny, Pace pace) {
        this.origin = origin;
        this.destiny = destiny;
        this.pace = pace != null ? pace
            : new Pace(LoggerFactory.getLogger(CSVExport.class));
    }

    public void run() {
        try {
            pace.info("Origin: " + origin);
            pace.info("Destiny: " + destiny);
            if (!destiny.exists()) {
                Files.createDirectories(destiny.toPath());
            }
            if (!destiny.exists()) {
                throw new Exception("Could not create the destination folder.");
            }
            if (!destiny.isDirectory()) {
                throw new Exception("The destination must be a directory.");
            }
            pace.waitIfPausedAndThrowIfStopped();
            pace.info("Connecting to Origin...");
            try (var originConn = origin.connect()) {
                pace.info("Connected.");
                pace.waitIfPausedAndThrowIfStopped();
                pace.info("Getting tables...");
                var eOrmOrigin = origin.getEOrm(originConn);
                var tableHeads = eOrmOrigin.getHeads();
                for (TableHead tableHead : tableHeads) {
                    pace.info("Processing: %s...", tableHead);
                    var table = tableHead.getTable(originConn);
                    try (var writer = new PrintWriter(new FileOutputStream(new File(
                                    destiny,
                                    tableHead.getNameForFile() + ".tab"), false), true)) {
                        writer.write(table.toString());
                    }
                    final var fileDestiny = new File(destiny, tableHead.getNameForFile()
                                    + ".csv");
                    try (var csvFile = new CSVFile(fileDestiny, CSVFile.Mode.WRITE)) {
                        final var row = new String[table.fieldList.size()];
                        for (var i = 0; i < table.fieldList.size(); i++) {
                            row[i] = table.fieldList.get(i).name;
                        }
                        csvFile.writeLine(row);
                        var rstOrigin = eOrmOrigin.select(new Select(tableHead));
                        var recordCount = 0L;
                        while (rstOrigin.next()) {
                            recordCount++;
                            pace.debug("Writing record " + recordCount + " of " + tableHead.name);
                            for (var i = 0; i < table.fieldList.size(); i++) {
                                row[i] = table.fieldList.get(i).toFormatted(rstOrigin.getObject(i + 1));
                            }
                            csvFile.writeLine(row);
                        }
                    }
                }
            }
            pace.info("CSV Export Finished!");
        } catch (Exception error) {
            pace.error("Could not export", error);
        }
    }
}
