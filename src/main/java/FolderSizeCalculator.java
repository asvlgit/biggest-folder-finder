import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class FolderSizeCalculator extends RecursiveTask<Long> {

    private final Node node;

    public FolderSizeCalculator(Node node) {
        this.node = node;
    }

    @Override
    protected Long compute() {

        File folder = node.getFolder();
        if (folder.isFile()) {
            long length = folder.length();
            node.setSize(length);
            return length;
        }

        List<FolderSizeCalculator> subTasks = new LinkedList<>();
        File[] files = folder.listFiles();
        try {
            Arrays.stream(Objects.requireNonNull(files)).map(Node::new).forEach(child -> {
                FolderSizeCalculator task = new FolderSizeCalculator(child);
                task.fork();
                subTasks.add(task);
                node.addChild(child);
            });
        } catch (NullPointerException e) {
            node.setSize(0);
            return 0L;
        }

        long sum = subTasks.stream().mapToLong(ForkJoinTask::join).sum();
        node.setSize(sum);
        return sum;
    }

}
