package pl.mcmodstudio.pp;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import pl.mcmodstudio.pp.managers.PluginManager;
import pl.mcmodstudio.pp.listeners.PlayerListener;

public final class ProxyPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Zapisuje config.yml jeśli nie istnieje (wygeneruje się automatycznie w folderze pluginu)
        saveDefaultConfig();

        // Twoja inicjalizacja managerów i listenerów
        PluginManager.getInstance().initialize();
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        // Rejestracja komendy BEZ tworzenia nowego pliku/klasy
        if (getCommand("proxyplugin") != null) {
            getCommand("proxyplugin").setExecutor(new CommandExecutor() {
                @Override
                public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
                    if (!(sender instanceof Player)) {
                        sender.sendMessage(ChatColor.RED + "Ta komenda jest tylko dla graczy!");
                        return true;
                    }

                    Player player = (Player) sender;
                    String targetAddress;

                    if (args.length > 0) {
                        targetAddress = args[0];
                    } else {
                        // Pobiera IP z config.yml (jeśli go nie ma, użyje domyślnego 127.0.0.1:25565)
                        targetAddress = getConfig().getString("default-ip", "127.0.0.1:25565");
                    }

                    player.sendMessage(ChatColor.GREEN + "Przekierowywanie na adres: " + targetAddress + "...");

                    String host = targetAddress;
                    int port = 25565;

                    if (targetAddress.contains(":")) {
                        String[] parts = targetAddress.split(":");
                        host = parts[0];
                        try {
                            port = Integer.parseInt(parts[1]);
                        } catch (NumberFormatException e) {
                            player.sendMessage(ChatColor.RED + "Błędny format portu w adresie IP!");
                            return true;
                        }
                    }

                    // Przekierowanie gracza na inne IP (Natywna funkcja 1.21)
                    player.transfer(host, port);
                    return true;
                }
            });
        }

        getLogger().info(getDescription().getName() + "&azostal wlaczony");
    }

    @Override
    public void onDisable() {
        getLogger().info(getDescription().getName() + "&czostal wylaczony! UWAGA! Plugin juz nie bedze teleportowal na serwery!");
    }
}