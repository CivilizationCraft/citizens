package civcraft.org.example.citizens.misc;

import java.util.Map;

public class Langage {
    private Map<String, String> translations;

    public void LangageLoader(String langCode) {
        /*
        // Charger le fichier JSON correspondant à la langue
        String fileName = "/lang/" + langCode + ".json";
        try (InputStream inputStream = getClass().getResourceAsStream(fileName);
             InputStreamReader reader = new InputStreamReader(inputStream)) {

            // Utiliser Gson pour lire le fichier JSON dans une map
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, String>>() {}.getType();
            translations = gson.fromJson(reader, type);

        } catch (Exception e) {
            e.printStackTrace();
            // Gérer l'absence ou les erreurs de fichier de langue
        }

         */
    }
}
