import java.io.File;
import java.util.ArrayList;

public class Node {
    private final File folder;
    private final ArrayList<Node> children;
    private long size;
    private int level = 0;
    private long limit;

    public Node(File folder, long limit) {
        this(folder);
        this.limit = limit;
    }

    public Node(File folder){
        this.folder = folder;
        children = new ArrayList<>();
    }

    private void setLimit(long limit) {
        this.limit = limit;
    }

    public File getFolder() {
        return folder;
    }

    public void addChild(Node node) {
        node.setLevel(level + 1);
        node.setLimit(limit);
        children.add(node);
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    private void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();
        String size = SizeCalculator.byteToDisplaySize(getSize());

        builder.append(folder.isDirectory() ? "+ " : "- ").append(folder.getName()).append(" - ").append(size).append("\n");
        getChildren().stream()
                .filter(c -> c.getSize() >= limit)
                .forEach(c -> builder.append(" ".repeat(level + 1)).append(c));

        return builder.toString();
    }

}
