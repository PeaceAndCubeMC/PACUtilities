package fr.peaceandcube.pacutilities.file;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class EventsFile extends YamlFile {

    public EventsFile(String name, Plugin plugin) {
        super(name, plugin);
    }

    public List<String> getEventNames() {
        return new ArrayList<>(config.getKeys(false));
    }

    public List<String> getEventPages(String event) {
        ConfigurationSection eventSection = config.getConfigurationSection(event);
        if (eventSection != null) {
            return eventSection.getKeys(false).stream().toList();
        }
        return List.of();
    }

    public List<String> getContent(String event, String page) {
        ConfigurationSection eventSection = config.getConfigurationSection(event);
        if (eventSection != null) {
            return eventSection.getStringList(page);
        }
        return List.of();
    }
}
