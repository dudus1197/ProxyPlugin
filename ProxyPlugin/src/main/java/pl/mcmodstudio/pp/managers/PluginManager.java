package pl.mcmodstudio.pp.managers;

public class PluginManager {

    private static PluginManager instance;

    // Prywatny konstruktor (wzorzec Singleton)
    private PluginManager() {}

    public static PluginManager getInstance() {
        if (instance == null) {
            instance = new PluginManager();
        }
        return instance;
    }

    public void initialize() {
        // Tutaj możesz w przyszłości ładować dodatkowe rzeczy, 
        // na ten moment zostawiamy puste lub z prostym info
    }
}