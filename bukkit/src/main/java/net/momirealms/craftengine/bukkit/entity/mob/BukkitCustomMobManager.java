package net.momirealms.craftengine.bukkit.entity.mob;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.momirealms.craftengine.bukkit.plugin.BukkitCraftEngine;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class BukkitCustomMobManager {
    private static final Map<BukkitCraftEngine, BukkitCustomMobManager> INSTANCES = new WeakHashMap<>();

    public static synchronized BukkitCustomMobManager getOrCreate(BukkitCraftEngine plugin) {
        return INSTANCES.computeIfAbsent(plugin, BukkitCustomMobManager::new);
    }

    private final BukkitCraftEngine plugin;
    private final JavaPlugin javaPlugin;
    private final Map<String, CustomMobDefinition> definitions = new ConcurrentHashMap<>();
    private final NamespacedKey mobIdKey;

    private BukkitCustomMobManager(BukkitCraftEngine plugin) {
        this.plugin = plugin;
        this.javaPlugin = plugin.javaPlugin();
        this.mobIdKey = new NamespacedKey(javaPlugin, "custom_mob_id");
        load();
    }

    public void load() {
        definitions.clear();
        File folder = new File(javaPlugin.getDataFolder(), "mobs");
        if (!folder.exists() && !folder.mkdirs()) {
            plugin.logger().warn("Could not create mobs directory: " + folder.getAbsolutePath());
            return;
        }
        saveExampleIfMissing();
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".yml") || name.endsWith(".yaml"));
        if (files == null) return;
        for (File file : files) {
            try {
                loadFile(file);
            } catch (Exception e) {
                plugin.logger().warn("Failed to load custom mob file " + file.getName(), e);
            }
        }
        plugin.logger().info("Loaded " + definitions.size() + " custom mob definitions.");
    }

    private void loadFile(File file) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection root = config.getConfigurationSection("mobs");
        if (root == null) root = config;
        for (String id : root.getKeys(false)) {
            ConfigurationSection section = root.getConfigurationSection(id);
            if (section == null) continue;
            String typeName = section.getString("type", "ZOMBIE");
            EntityType type = EntityType.fromName(typeName);
            if (type == null || !type.isAlive()) {
                plugin.logger().warn("Invalid living entity type '" + typeName + "' for mob '" + id + "'.");
                continue;
            }
            double health = Math.max(1.0, section.getDouble("health", 20.0));
            double speed = Math.max(0.0, section.getDouble("movement-speed", 0.23));
            String displayName = section.getString("display-name");
            boolean glowing = section.getBoolean("glowing", false);
            boolean persistent = section.getBoolean("persistent", true);
            List<String> tags = section.getStringList("tags");
            Map<String, String> metadata = new HashMap<>();
            ConfigurationSection metadataSection = section.getConfigurationSection("metadata");
            if (metadataSection != null) {
                for (String key : metadataSection.getKeys(false)) {
                    metadata.put(key, metadataSection.getString(key, ""));
                }
            }
            definitions.put(id.toLowerCase(Locale.ROOT), new CustomMobDefinition(
                    id.toLowerCase(Locale.ROOT), type, displayName, health, speed, glowing, persistent, tags, metadata
            ));
        }
    }

    public Optional<CustomMobDefinition> definition(String id) {
        return Optional.ofNullable(definitions.get(id.toLowerCase(Locale.ROOT)));
    }

    public Set<String> ids() {
        return Collections.unmodifiableSet(new TreeSet<>(definitions.keySet()));
    }

    public Optional<LivingEntity> spawn(String id, org.bukkit.Location location) {
        CustomMobDefinition definition = definitions.get(id.toLowerCase(Locale.ROOT));
        if (definition == null || location.getWorld() == null) return Optional.empty();
        Entity entity = location.getWorld().spawnEntity(location, definition.type());
        if (!(entity instanceof LivingEntity living)) {
            entity.remove();
            return Optional.empty();
        }
        applyDefinition(living, definition);
        return Optional.of(living);
    }

    public void applyDefinition(LivingEntity entity, CustomMobDefinition definition) {
        entity.getPersistentDataContainer().set(mobIdKey, PersistentDataType.STRING, definition.id());
        entity.setPersistent(definition.persistent());
        entity.setGlowing(definition.glowing());
        if (definition.displayName() != null && !definition.displayName().isBlank()) {
            entity.customName(MiniMessage.miniMessage().deserialize(definition.displayName()));
            entity.setCustomNameVisible(true);
        }
        AttributeInstance health = entity.getAttribute(Attribute.MAX_HEALTH);
        if (health != null) {
            health.setBaseValue(definition.health());
            entity.setHealth(Math.min(definition.health(), health.getValue()));
        }
        AttributeInstance speed = entity.getAttribute(Attribute.MOVEMENT_SPEED);
        if (speed != null) speed.setBaseValue(definition.movementSpeed());
        for (String tag : definition.tags()) entity.addScoreboardTag("ce_mob_" + tag);
        for (Map.Entry<String, String> entry : definition.metadata().entrySet()) {
            entity.getPersistentDataContainer().set(
                    new NamespacedKey(javaPlugin, "mob_" + entry.getKey().toLowerCase(Locale.ROOT)),
                    PersistentDataType.STRING,
                    entry.getValue()
            );
        }
    }

    public Optional<String> mobId(Entity entity) {
        return Optional.ofNullable(entity.getPersistentDataContainer().get(mobIdKey, PersistentDataType.STRING));
    }

    public boolean isCustomMob(Entity entity) {
        return mobId(entity).isPresent();
    }

    public void saveExampleIfMissing() {
        File folder = new File(javaPlugin.getDataFolder(), "mobs");
        if (!folder.exists() && !folder.mkdirs()) return;
        File example = new File(folder, "example.yml");
        if (example.exists()) return;
        YamlConfiguration config = new YamlConfiguration();
        ConfigurationSection mob = config.createSection("mobs.example_goblin");
        mob.set("type", "ZOMBIE");
        mob.set("display-name", "<green>Goblin");
        mob.set("health", 40.0);
        mob.set("movement-speed", 0.28);
        mob.set("glowing", false);
        mob.set("persistent", true);
        mob.set("tags", List.of("goblin"));
        try {
            config.save(example);
        } catch (IOException e) {
            plugin.logger().warn("Failed to create example mob configuration", e);
        }
    }
}
