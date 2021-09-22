import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import sec.api.Expression;
import sec.api.ObserverHandler;
import sec.api.PluginObserver;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class CSVOutput extends PluginObserver {

    public CSVOutput(ObserverHandler handler) {
        this.desc = "Writes out all values of x and y to a two-column CSV file";
        this.handler = handler;
        this.handler.attach(this);
    }
  
    @Override
    public void update() {
        Expression ex = handler.getState(); //Get state
        CSVFormat format = null;

        //Check to see if there is a header set and to skip or not
        if (ex.getMin() == ex.getInc()) {
            format = CSVFormat.DEFAULT.withHeader("x", "y");
        } else {
            format = CSVFormat.EXCEL.withHeader().withSkipHeaderRecord();
        }

        //Create path
        Path path = Paths.get( "output.csv");
        try {
            //New writer
            BufferedWriter writer = Files.newBufferedWriter(
                    path,
                    StandardOpenOption.APPEND,
                    StandardOpenOption.CREATE);

            //Given everything then create a printer
            CSVPrinter csvPrinter = new CSVPrinter(writer, format);

            //Print (append) the new records x,y
            csvPrinter.printRecord(ex.getInc()+"", ex.getResult()+"");
            csvPrinter.flush();

        } catch (IOException e) {
            System.out.println("Error: " + e.toString());
        }
    }
    
}