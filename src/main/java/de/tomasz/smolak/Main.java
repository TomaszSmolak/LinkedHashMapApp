package de.tomasz.smolak;

import de.tomasz.smolak.controller.LinkedHashMapController;
import de.tomasz.smolak.model.LinkedHashMapService;
import de.tomasz.smolak.view.LinkedHashMapView;

/**
 * Einstiegspunkt der Anwendung zur Cache-Verwaltung mit LinkedHashMap.
 * <p>
 * Initialisiert Service, View und Controller und startet die GUI.
 * </p>
 *
 * @author Tomasz
 * @version 1.0
 */
public class Main {

    /**
     * Main-Methode: Startet die Anwendung.
     *
     * @param args nicht verwendet
     */
    public static void main(String[] args) {
        // Pfad zur Datei, in der der Cache gespeichert wird
        String filePath = "cache.txt";

        // Initialisierung der Komponenten
        LinkedHashMapService service = new LinkedHashMapService(filePath);
        LinkedHashMapView view = new LinkedHashMapView();
        new LinkedHashMapController(service, view); // verbindet alles
    }
}
