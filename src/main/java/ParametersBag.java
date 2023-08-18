import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ParametersBag {
    private final long limit;
    private final String path;

    public ParametersBag(String[] args) {
        Map<String, String> parameters = new HashMap<>();
        for (int i = 0; i < args.length; i += 2) {
            parameters.put(args[i], args[i + 1]);
        }

        path = Optional.ofNullable(parameters.get("-d"))
                .orElseThrow(() -> new IllegalArgumentException("Не указана папка"));

        try {
            limit = SizeCalculator.displaySizeToByte(Optional
                    .ofNullable(parameters.get("-l"))
                    .orElseThrow(() -> new IllegalArgumentException("Не указан лимит объема папки")));
            if (limit < 0) {
                throw new IllegalArgumentException("Лимит объема папки должен быть положительным");
            }
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Не указан лимит объема папки");
        }
    }

    public long getLimit() {
        return limit;
    }

    public String getPath() {
        return path;
    }
}
