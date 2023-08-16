import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class FolderSizeCalculator extends RecursiveTask<Long> {

    private File folder;

    public FolderSizeCalculator(File file) {
        folder = file;
    }

    @Override
    protected Long compute() {

        if (folder.isFile()) {
            return folder.length();
        }

        List<FolderSizeCalculator> subTasks = new LinkedList<>();
        File[] files = folder.listFiles();
        Arrays.stream(files).map(FolderSizeCalculator::new).forEachOrdered(task -> {
            task.fork();
            subTasks.add(task);
        });

        return subTasks.stream().mapToLong(ForkJoinTask::join).sum();
    }

    public String getHumanReadableSize(long size) {
        return FileUtils.byteCountToDisplaySize(size);
    }
}
