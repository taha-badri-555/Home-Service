package ir.maktabsharif.final_project_taha_badri.util;

public class Utility {
    public static String formatName(String name) {
        if (name == null || name.isEmpty()) return name;

        String[] words = name.trim().split("\\s+");
        StringBuilder formatted = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                String first = word.substring(0, 1).toUpperCase();
                String rest = word.substring(1).toLowerCase();
                formatted.append(first).append(rest).append(" ");
            }
        }

        return formatted.toString().trim();
    }
}
