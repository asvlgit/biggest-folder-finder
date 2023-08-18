import org.apache.commons.io.FileUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SizeCalculator {
    private static Map<String, Long> dimensionSize;

    static {
        dimensionSize = new HashMap<>();
        dimensionSize.put("EB", FileUtils.ONE_EB);
        dimensionSize.put("PB", FileUtils.ONE_PB);
        dimensionSize.put("TB", FileUtils.ONE_TB);
        dimensionSize.put("GB", FileUtils.ONE_GB);
        dimensionSize.put("MB", FileUtils.ONE_MB);
        dimensionSize.put("KB", FileUtils.ONE_KB);
    }

    public static String byteToDisplaySize(long size) {
        return FileUtils.byteCountToDisplaySize(size);
    }

    public static long displaySizeToByte(String displaySize) throws NumberFormatException {
        String regexWithoutNumber = "[0-9\\s+]";
        String regexWithNumber = "[^0-9]";
        String typeDimension = displaySize.replaceAll(regexWithoutNumber, "");
        long dimension = Optional.ofNullable(dimensionSize.get(typeDimension.toUpperCase())).orElse(1L);
        long size = Long.parseLong(displaySize.replaceAll(regexWithNumber, "")) * dimension;
        return size;
    }


}
