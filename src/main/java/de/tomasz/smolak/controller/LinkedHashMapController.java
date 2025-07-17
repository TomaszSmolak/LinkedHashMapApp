package de.tomasz.smolak.controller;

import de.tomasz.smolak.model.LinkedHashMapService;
import de.tomasz.smolak.view.LinkedHashMapView;

import javax.swing.*;

/**
 * Der {@code LinkedHashMapController} steuert die Interaktion zwischen
 * Benutzeroberfläche (View) und Geschäftslogik (Service) der Cache-Anwendung.
 *
 * <p>Er reagiert auf Benutzeraktionen (z. B. Button-Klicks), ruft Methoden im Service auf
 * und aktualisiert die View entsprechend.</p>
 *
 * @author Tomasz
 * @version 1.0
 */
public class LinkedHashMapController {

    // Referenz auf die Logikschicht
    private final LinkedHashMapService service;

    // Referenz auf die View (GUI)
    private final LinkedHashMapView view;

    /**
     * Konstruktor: Initialisiert Controller mit Service und View,
     * registriert Event-Handler und zeigt den aktuellen Cache.
     *
     * @param service Die Geschäftslogik (Cache-Verwaltung)
     * @param view    Die Benutzeroberfläche
     */
    public LinkedHashMapController(LinkedHashMapService service, LinkedHashMapView view) {
        this.service = service;
        this.view = view;

        initController();      // Event-Handler registrieren
        updateCacheList();     // GUI mit bestehendem Cache füllen
    }

    /**
     * Registriert ActionListener für die Buttons in der View.
     */
    private void initController() {
        view.putButton.addActionListener(_ -> putEntry());
        view.getButton.addActionListener(_ -> getEntry());
        view.removeButton.addActionListener(_ -> removeEntry());
        view.saveButton.addActionListener(_ -> saveCache());
    }

    /**
     * Liest Schlüssel und Wert aus den Textfeldern und speichert sie im Cache.
     * Aktualisiert anschließend die Anzeige und leert die Eingabefelder.
     */
    private void putEntry() {
        String key = view.keyField.getText().trim();
        String value = view.valueField.getText().trim();

        if (key.isEmpty() || value.isEmpty()) {
            showMessage("Bitte Schlüssel und Wert eingeben.");
            return;
        }

        service.put(key, value);
        showMessage("Eintrag gespeichert.");
        updateCacheList();
        clearInput();
    }

    /**
     * Holt den Wert für den eingegebenen Schlüssel aus dem Cache.
     * Gibt das Ergebnis im Textfeld und per Dialog aus.
     */
    private void getEntry() {
        String key = view.keyField.getText().trim();

        if (key.isEmpty()) {
            showMessage("Bitte Schlüssel eingeben.");
            return;
        }

        String value = service.get(key);
        if (value != null) {
            view.valueField.setText(value);
            showMessage("Eintrag gefunden.");
        } else {
            showMessage("Kein Eintrag für diesen Schlüssel.");
        }
    }

    /**
     * Entfernt einen Eintrag anhand des eingegebenen Schlüssels.
     * Aktualisiert die Anzeige und leert die Eingabefelder.
     */
    private void removeEntry() {
        String key = view.keyField.getText().trim();

        if (key.isEmpty()) {
            showMessage("Bitte Schlüssel eingeben.");
            return;
        }

        boolean removed = service.remove(key);
        if (removed) {
            showMessage("Eintrag entfernt.");
            updateCacheList();
            clearInput();
        } else {
            showMessage("Eintrag nicht gefunden.");
        }
    }

    /**
     * Speichert den aktuellen Cache in eine Datei.
     */
    private void saveCache() {
        service.saveCache();
        showMessage("Cache gespeichert.");
    }

    /**
     * Aktualisiert die visuelle Liste der gespeicherten Cache-Einträge.
     * Holt alle Einträge aus dem Service und schreibt sie ins ListModel.
     */
    private void updateCacheList() {
        view.cacheListModel.clear();  // alte Inhalte löschen
        for (var entry : service.getAllEntries().entrySet()) {
            view.cacheListModel.addElement(entry.getKey() + " → " + entry.getValue());
        }
    }

    /**
     * Setzt die Eingabefelder (Schlüssel und Wert) auf leer.
     */
    private void clearInput() {
        view.keyField.setText("");
        view.valueField.setText("");
    }

    /**
     * Zeigt eine einfache Nachricht in einem Dialogfenster.
     *
     * @param msg Die anzuzeigende Nachricht
     */
    private void showMessage(String msg) {
        JOptionPane.showMessageDialog(view, msg);
    }
}
