import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {

        String folderPath = "p:\\";

        File file = new File(folderPath);

        long timestamp = System.currentTimeMillis();

        FolderSizeCalculator calculator = new FolderSizeCalculator(file);
        ForkJoinPool pool = new ForkJoinPool();
        long sizeFolder = pool.invoke(calculator);

        System.out.println("размер папки:" + calculator.getHumanReadableSize(sizeFolder) + " за " + (System.currentTimeMillis() - timestamp) + " мс");

    }


}