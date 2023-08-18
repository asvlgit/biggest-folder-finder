import java.io.File;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {

        ParametersBag bag = new ParametersBag(args);

        String folderPath = bag.getPath();
        long sizeLimit = bag.getLimit();

        File file = new File(folderPath);

        long timestamp = System.currentTimeMillis();

        Node root = new Node(file, sizeLimit);
        FolderSizeCalculator calculator = new FolderSizeCalculator(root);
        ForkJoinPool pool = new ForkJoinPool();
        long sizeFolder = pool.invoke(calculator);

        System.out.println((System.currentTimeMillis() - timestamp) + " мс");

        System.out.println(root);


    }


}