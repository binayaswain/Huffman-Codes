package application;

import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Engine {

    private static final Logger LOG = Logger.getLogger(HuffmanCode.class.getCanonicalName());

    public Map<String, AtomicInteger> countCharacters(String fileDate) {
        String[] inputDatas = fileDate.split("");

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        Map<String, AtomicInteger> inputCharMap = new Hashtable<>();

        for (String inputData : inputDatas) {
            CountCharacterJob job = new CountCharacterJob(inputData, inputCharMap);
            executorService.submit(job);
        }

        shutdownAndAwaitTermination(executorService);

        fileDate = null;

        return inputCharMap;
    }

    public void shutdownAndAwaitTermination(ExecutorService executorService) {
        executorService.shutdown();
        try {
            while (!executorService.isTerminated()) {
                TimeUnit.MILLISECONDS.sleep(100);
            }
        } catch (InterruptedException e) {
            LOG.log(Level.SEVERE, "Error in processing", e);
        }
    }

    public CustomHeap<SymbolNode> createForest(Map<String, AtomicInteger> inputCharMap) {
        CustomHeap<SymbolNode> minHeapForest = new CustomHeap<>(inputCharMap.size(), true);

        for (Entry<String, AtomicInteger> entrySet : inputCharMap.entrySet()) {
            SymbolNode node = new SymbolNode(entrySet.getKey(), entrySet.getValue().get());
            minHeapForest.insert(node);
        }

        inputCharMap = null;

        reduceForest(minHeapForest, minHeapForest.size());

        return minHeapForest;
    }

    private void reduceForest(CustomHeap<SymbolNode> forest, int numberOfNodes) {
        if (forest.size() < 2) {
            return;
        }

        SymbolNode node1 = forest.remove();
        SymbolNode node2 = forest.remove();

        String nodeCharacter = "node" + numberOfNodes + 1;
        Integer nodeFrequency = node1.getFrequency() + node2.getFrequency();

        SymbolNode newNode = new SymbolNode(nodeCharacter, nodeFrequency);
        newNode.setLeftNode(node1);
        newNode.setRightNode(node2);

        forest.insert(newNode);

        reduceForest(forest, ++numberOfNodes);
    }

    public void generateHuffmanCodes(SymbolNode root, String rootCode, CustomHeap<SymbolNode> maxHeap) {
        if (root.getLeftNode() == null && root.getRightNode() == null) {
            root.setHuffmanCode(rootCode);
            maxHeap.insert(root);
            return;
        }

        if (root.getLeftNode() != null) {
            generateHuffmanCodes(root.getLeftNode(), rootCode + "0", maxHeap);
        }

        if (root.getRightNode() != null) {
            generateHuffmanCodes(root.getRightNode(), rootCode + "1", maxHeap);
        }
    }

}
