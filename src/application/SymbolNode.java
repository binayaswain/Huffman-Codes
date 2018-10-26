package application;

public class SymbolNode implements Comparable<SymbolNode> {

    private final String symbol;

    private final Integer frequency;

    private String huffmanCode;

    private SymbolNode leftNode;

    private SymbolNode rightNode;

    public SymbolNode(String symbol, Integer frequency) {
        this.symbol = symbol;
        this.frequency = frequency;
    }

    public String getSymbol() {
        return symbol;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public SymbolNode getLeftNode() {
        return leftNode;
    }

    public void setLeftNode(SymbolNode leftNode) {
        this.leftNode = leftNode;
    }

    public SymbolNode getRightNode() {
        return rightNode;
    }

    public void setRightNode(SymbolNode rightNode) {
        this.rightNode = rightNode;
    }

    public String getHuffmanCode() {
        return huffmanCode;
    }

    public void setHuffmanCode(String huffmanCode) {
        this.huffmanCode = huffmanCode;
    }

    @Override
    public int compareTo(SymbolNode otherSymbolInfo) {
        if (otherSymbolInfo == null) {
            return 1;
        }

        return frequency.compareTo(otherSymbolInfo.getFrequency());
    }

    @Override
    public String toString() {
        return symbol + " - " + frequency;
    }

}
