package sonnet.reference.playwright.utils.dtogenerator;

import sonnet.reference.playwright.utils.StringUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Generates a Lombok-annotated DTO class from a text file of field names.
 * <p>
 * Input:  Text file with field names separated by | , or newlines.
 *         Lines starting with # or // are ignored.
 * <p>
 * Output: A .java file written alongside the input file.
 * <p>
 * Usage from the project root (after mvn compile):
 * <pre>
 *   java -cp target/classes sonnet.reference.playwright.utils.dtogenerator.DtoGenerator inputFile [subPackage]
 * </pre>
 * Class name is derived from the filename. Sub-package defaults to the root dto package.
 */
public class DtoGenerator {

    private static final String INDENT = "    ";
    private static final String DOUBLE_INDENT = INDENT + INDENT;

    public static void main(String[] args) {
        if (args.length < 1) {
            throw new IllegalArgumentException(
                    "Usage: java -cp target/classes "
                    + "sonnet.reference.playwright.utils.dtogenerator.DtoGenerator "
                    + "<input-file> [sub-package]");
        }

        String filePath = args[0];
        String subPackage = args.length > 1 ? args[1].toLowerCase().trim() : null;

        Path path = validatePath(filePath);

        String fileName = path.getFileName().toString();
        String className = deriveClassName(fileName);

        if (StringUtils.isNullOrEmpty(subPackage)) {
            subPackage = null;
        }

        List<Map.Entry<String, String>> fields = parseFields(path);
        if (fields.isEmpty()) {
            throw new IllegalArgumentException("No fields found in file: " + filePath);
        }

        String dtoCode = generateDto(className, subPackage, fields);
        Path outputPath = validateOutputPath(path.getParent(), className + ".java");
        writeFile(outputPath, dtoCode);
    }

    static String deriveClassName(String fileName) {
        String name = fileName.contains(".")
                ? fileName.substring(0, fileName.lastIndexOf('.'))
                : fileName;

        if (name.isEmpty()) {
            throw new IllegalArgumentException("Cannot derive class name from filename: " + fileName);
        }

        if (name.contains("-") || name.contains("_")) {
            String[] parts = name.split("[-_]");
            StringBuilder sb = new StringBuilder();
            for (String part : parts) {
                if (!part.isEmpty()) {
                    sb.append(Character.toUpperCase(part.charAt(0)));
                    if (part.length() > 1) {
                        sb.append(part.substring(1));
                    }
                }
            }
            return sb.toString();
        }

        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }

    static List<Map.Entry<String, String>> parseFields(Path filePath) {
        List<Map.Entry<String, String>> fields = new ArrayList<>();
        Set<String> seenFieldNames = new LinkedHashSet<>();
        String content;

        try {
            content = Files.readString(filePath).trim();
        } catch (IOException e) {
            throw new RuntimeException("Could not read file: " + filePath, e);
        }

        if (content.isEmpty()) {
            return fields;
        }

        String[] lines = content.split("\\r?\\n");
        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.isEmpty() || trimmed.startsWith("#") || trimmed.startsWith("//")) {
                continue;
            }

            String[] parts;
            if (trimmed.contains("|")) {
                parts = trimmed.split("\\|");
            } else if (trimmed.contains(",")) {
                parts = trimmed.split(",");
            } else {
                parts = new String[]{trimmed};
            }

            for (String part : parts) {
                String rawKey = part.trim();
                String fieldName = sanitizeFieldName(rawKey);
                if (!fieldName.isEmpty() && !seenFieldNames.contains(fieldName)) {
                    seenFieldNames.add(fieldName);
                    fields.add(new AbstractMap.SimpleEntry<>(rawKey, fieldName));
                }
            }
        }

        return fields;
    }

    static String sanitizeFieldName(String raw) {
        String cleaned = raw.replaceAll("[^a-zA-Z0-9_]", "");
        if (cleaned.isEmpty()) {
            return "";
        }
        // Prefix with underscore if it starts with a digit
        if (Character.isDigit(cleaned.charAt(0))) {
            cleaned = "_" + cleaned;
        }
        return Character.toLowerCase(cleaned.charAt(0)) + cleaned.substring(1);
    }

    static String generateDto(String className, String subPackage, List<Map.Entry<String, String>> fields) {
        StringBuilder sb = new StringBuilder();

        sb.append("package dto");
        if (subPackage != null) {
            sb.append(".").append(subPackage);
        }
        sb.append(";\n");
        sb.append("\n");
        sb.append("import dto.DTOMap;\n");
        sb.append("import lombok.AllArgsConstructor;\n");
        sb.append("import lombok.Getter;\n");
        sb.append("import lombok.NoArgsConstructor;\n");
        sb.append("import lombok.Setter;\n");
        sb.append("\n");
        sb.append("@NoArgsConstructor\n");
        sb.append("@AllArgsConstructor\n");
        sb.append("@Getter\n");
        sb.append("@Setter\n");
        sb.append("public class ").append(className).append(" {\n");
        sb.append("\n");

        for (Map.Entry<String, String> field : fields) {
            sb.append(INDENT).append("private String ").append(field.getValue()).append(";\n");
        }
        sb.append("\n");

        sb.append(INDENT).append("public static ").append(className)
                .append(" decode(DTOMap row) {\n");
        sb.append(DOUBLE_INDENT).append("return new ").append(className).append("(\n");

        for (int i = 0; i < fields.size(); i++) {
            sb.append(DOUBLE_INDENT).append(DOUBLE_INDENT)
                    .append("row.get(\"").append(escapeJavaString(fields.get(i).getKey())).append("\")");            if (i < fields.size() - 1) {
                sb.append(",");
            }
            sb.append("\n");
        }

        sb.append(DOUBLE_INDENT).append(");\n");
        sb.append(INDENT).append("}\n");
        sb.append("}\n");

        return sb.toString();
    }

    static void writeFile(Path outputPath, String content) {
        try (BufferedWriter writer = Files.newBufferedWriter(outputPath, StandardCharsets.UTF_8)) {
            writer.write(content);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write file: " + outputPath, e);
        }
    }

    static String escapeJavaString(String raw) {
        return raw.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    /**
     * Validates and normalizes the input file path, rejecting path traversal attempts.
     */
    static Path validatePath(String filePath) {
        Path cwd = Paths.get("").toAbsolutePath().normalize();
        Path resolved = cwd.resolve(filePath).normalize();

        if (!resolved.startsWith(cwd)) {
            throw new SecurityException("Path traversal detected — input file must be within the working directory: " + filePath);
        }
        if (!Files.exists(resolved)) {
            throw new IllegalArgumentException("File not found: " + resolved);
        }
        if (!Files.isRegularFile(resolved)) {
            throw new IllegalArgumentException("Not a regular file: " + resolved);
        }
        try {
            Path realPath = resolved.toRealPath(LinkOption.NOFOLLOW_LINKS);
            if (!realPath.startsWith(cwd)) {
                throw new SecurityException("Symlink traversal detected — resolved path is outside the working directory: " + filePath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not resolve real path: " + resolved, e);
        }
        return resolved;
    }

    /**
     * Validates and normalizes the output file path, rejecting path traversal attempts.
     */
    static Path validateOutputPath(Path parentDir, String fileName) {
        Path cwd = Paths.get("").toAbsolutePath().normalize();
        Path resolved = parentDir.resolve(fileName).normalize();

        if (!resolved.startsWith(cwd)) {
            throw new SecurityException("Path traversal detected — output must be within the working directory: " + resolved);
        }
        return resolved;
    }
}
