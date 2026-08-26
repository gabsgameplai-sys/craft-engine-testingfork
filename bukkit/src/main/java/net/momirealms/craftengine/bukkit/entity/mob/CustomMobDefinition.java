package net.momirealms.craftengine.bukkit.entity.mob;

import org.bukkit.entity.EntityType;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public record CustomMobDefinition(
        String id,
        EntityType type,
        String displayName,
        double health,
        double movementSpeed,
        boolean glowing,
        boolean persistent,
        List<String> tags,
        Map<String, String> metadata
) {
    public CustomMobDefinition {
        tags = tags == null ? List.of() : List.copyOf(tags);
        metadata = metadata == null ? Map.of() : Collections.unmodifiableMap(metadata);
    }
}
