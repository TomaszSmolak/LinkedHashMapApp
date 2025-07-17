package de.tomasz.smolak.model;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Die Klasse {@code LinkedHashMapService} verwaltet einen einfachen Cache,
 * der nur die letzten fünf Einträge speichert.
 * <p>
 * Die Daten werden in einer {@link LinkedHashMap} gespeichert und beim
 * Programmstart aus einer Datei geladen sowie beim Speichern persistiert.
 * </p>
 *
 * @author Tomasz
 * @version 1.0
 */
public class LinkedHashMapService {

    /**
     * Interner Cache, der maximal 5 Schlüssel-Wert-Paare speichert.
     * Ältere Einträge werden automatisch entfernt.
     */
    private final Map<String, String> cache = new LinkedHashMap<>(16, 0.75f, false) {
        @Override
        protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
            return size() > 5;
        }
    };

    /** Pfad zur Datei für das Speichern und Laden des Caches */
    private final String filePath;

    /**
     * Konstruktor: Erstellt ein neues Cache-Objekt und lädt vorhandene Daten.
     *
     * @param filePath Pfad zur Datei, in der der Cache gespeichert wird
     */
    public LinkedHashMapService(String filePath) {
        this.filePath = filePath;
        loadCache();
    }

    /**
     * Fügt einen neuen Eintrag zum Cache hinzu oder überschreibt den alten.
     *
     * @param key   Schlüssel des Eintrags
     * @param value Wert des Eintrags
     */
    public void put(String key, String value) {
        cache.put(key, value);
    }

    /**
     * Gibt den Wert für einen bestimmten Schlüssel zurück.
     *
     * @param key Schlüssel
     * @return Wert oder {@code null}, falls nicht vorhanden
     */
    public String get(String key) {
        return cache.get(key);
    }

    /**
     * Entfernt einen Eintrag anhand seines Schlüssels.
     *
     * @param key Schlüssel des Eintrags
     * @return {@code true}, wenn ein Eintrag entfernt wurde, sonst {@code false}
     */
    public boolean remove(String key) {
        return cache.remove(key) != null;
    }

    /**
     * Gibt alle Cache-Einträge als Map zurück.
     *
     * @return Aktueller Zustand des Caches
     */
    public Map<String, String> getAllEntries() {
        return cache;
    }

    /**
     * Speichert alle Einträge im Format {@code key:value} in die Datei.
     */
    public void saveCache() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, String> entry : cache.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Fehler beim Speichern: " + e.getMessage());
        }
    }

    /**
     * Lädt Cache-Einträge aus der Datei in die LinkedHashMap.
     */
    private void loadCache() {
        File file = new File(filePath);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":", 2);
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    cache.put(key, value);
                }
            }
        } catch (IOException e) {
            System.err.println("Fehler beim Laden: " + e.getMessage());
        }
    }
}
