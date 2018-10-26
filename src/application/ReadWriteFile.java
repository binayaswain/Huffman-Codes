package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReadWriteFile {

    private static final Logger LOG = Logger.getLogger(HuffmanCode.class.getCanonicalName());

    public static String readFile() {
        StringBuilder inputDataBuilder = new StringBuilder();

        try (BufferedReader reader = Files.newBufferedReader(new File("infile.dat").toPath())) {
            String line = reader.readLine();
            while (line != null) {
                inputDataBuilder.append(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Error in reading file", e);
            return null;
        }

        return inputDataBuilder.toString();
    }

    public static boolean writeFile(CustomHeap<SymbolNode> displayMaxHeap, int numberOfCharacters) {
        String lineFeed = System.lineSeparator();
        String lineSeparator = "---------------------------------";
        long numberOfBits = 0;
        
        StringBuilder frequencyBuilder = new StringBuilder("CHARACTER STATISTIC");
        frequencyBuilder.append(lineFeed).append(lineFeed)
                .append(lineSeparator).append(lineFeed)
                .append("SYMBOL | FREQUENCY").append(lineFeed)
                .append(lineSeparator).append(lineFeed);
        
        StringBuilder encodingBuilder = new StringBuilder("ENCODING LEGEND");
        encodingBuilder.append(lineFeed).append(lineFeed)
                .append(lineSeparator).append(lineFeed)
                .append("SYMBOL | HUFFMAN CODE").append(lineFeed)
                .append(lineSeparator).append(lineFeed);
        
        while (displayMaxHeap.size() > 0) {
            SymbolNode node = displayMaxHeap.remove();
            String symbol = node.getSymbol();
            String code = node.getHuffmanCode();
            int count = node.getFrequency();
            
            double frequency = ((double) count) * 100 / numberOfCharacters;
            
            frequencyBuilder.append("  ").append(symbol)
                    .append("    | ").append(frequency).append(" %")
                    .append(lineFeed).append(lineSeparator).append(lineFeed);
            
            encodingBuilder.append("  ").append(symbol)
                    .append("    | ").append(code)
                    .append(lineFeed).append(lineSeparator).append(lineFeed);

            numberOfBits += code.length() * count;
        }

        frequencyBuilder.append(lineFeed)
                .append("TOTAL CHARACTERS = ").append(numberOfCharacters).append(lineFeed)
                .append(lineFeed).append(lineFeed).append(encodingBuilder.toString())
                .append(lineFeed).append("TOTAL BITS = ").append(numberOfBits);

        try (BufferedWriter writer = Files.newBufferedWriter(new File("outfile.dat").toPath())) {
            writer.write(frequencyBuilder.toString());
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Error in writing file", e);
            return false;
        }

        return true;
    }
}
