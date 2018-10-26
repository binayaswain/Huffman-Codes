package application;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class HuffmanCode {

    public static void main(String[] args) throws IOException {
        String fileDate = ReadWriteFile.readFile();

        if (fileDate == null) {
            exit("", 1);
        }

        Engine engine = new Engine();

        Map<String, AtomicInteger> inputCharMap = engine.countCharacters(fileDate);

        int numberOfNodes = inputCharMap.size();

        if (numberOfNodes == 0) {
            exit("Input file has not characters", 0);
        }

        CustomHeap<SymbolNode> forest = engine.createForest(inputCharMap);

        int totalCharacters = forest.peek().getFrequency();

        CustomHeap<SymbolNode> displayMaxHeap = new CustomHeap<>(numberOfNodes, false);

        engine.generateHuffmanCodes(forest.remove(), "", displayMaxHeap);

        if (ReadWriteFile.writeFile(displayMaxHeap, totalCharacters)) {
            exit("Output file generated", 0);
        }

        exit("Output file could not be generated", 1);

    }

    private static void exit(String message, int exitStatus) {
        System.out.println(message);
        System.out.println("Exiting");
        System.exit(exitStatus);
    }

}
