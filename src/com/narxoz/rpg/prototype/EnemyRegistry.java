package com.narxoz.rpg.prototype;

import com.narxoz.rpg.enemy.Enemy;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Prototype Registry: stores base enemy templates (prototypes)
 * and creates new instances by cloning.
 *
 * CRITICAL RULE:
 * Registry MUST return clones, never the original template.
 */
public class EnemyRegistry {

    private final Map<String, Enemy> templates = new HashMap<>();

    /**
     * Register a prototype template under a key.
     * Example: registerTemplate("goblin", goblinTemplate);
     */
    public void registerTemplate(String key, Enemy template) {
        if (key == null || key.trim().isEmpty()) {
            throw new IllegalArgumentException("Template key cannot be null/empty");
        }
        if (template == null) {
            throw new IllegalArgumentException("Template cannot be null");
        }
        templates.put(key, template);
    }

    /**
     * Create a new Enemy from a registered template by CLONING it.
     * Never return the original stored prototype.
     */
    public Enemy createFromTemplate(String key) {
        Enemy template = templates.get(key);
        if (template == null) {
            throw new IllegalArgumentException("Unknown template: " + key);
        }
        return template.clone(); // PROTOTYPE: return clone (deep copy inside clone())
    }

    /**
     * List all registered template keys.
     */
    public Set<String> listTemplates() {
        return Collections.unmodifiableSet(templates.keySet());
    }

    /**
     * Optional: view templates map (read-only).
     */
    public Map<String, Enemy> viewTemplates() {
        return Collections.unmodifiableMap(templates);
    }
}
