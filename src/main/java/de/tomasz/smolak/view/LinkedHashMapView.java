package de.tomasz.smolak.view;

import javax.swing.*;
import java.awt.*;

/**
 * Die {@code LinkedHashMapView} stellt das GUI-Fenster für die Cache-Verwaltung bereit.
 * <p>
 * Sie enthält Eingabefelder, Buttons und eine Liste zur Anzeige der gespeicherten Einträge.
 * </p>
 *
 * @author Tomasz
 * @version 1.0
 */
public class LinkedHashMapView extends JFrame {

    // Eingabefelder für Schlüssel und Wert
    public final JTextField keyField = new JTextField(15);
    public final JTextField valueField = new JTextField(15);

    // Buttons für Aktionen
    public final JButton putButton = new JButton("Eintrag hinzufügen");
    public final JButton getButton = new JButton("Eintrag abrufen");
    public final JButton removeButton = new JButton("Eintrag entfernen");
    public final JButton saveButton = new JButton("Cache speichern");

    // Datenmodell und Liste zur Anzeige der Cache-Einträge
    public final DefaultListModel<String> cacheListModel = new DefaultListModel<>();
    public final JList<String> cacheList = new JList<>(cacheListModel);

    /**
     * Konstruktor: Initialisiert das GUI-Fenster und dessen Komponenten.
     */
    public LinkedHashMapView() {
        setTitle("Cache-Verwaltung mit LinkedHashMap");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        initComponents();
        setVisible(true);
    }

    /**
     * Baut das Layout des Fensters auf und fügt alle GUI-Elemente hinzu.
     */
    private void initComponents() {
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Eintrag"));

        inputPanel.add(new JLabel("Schlüssel:"));
        inputPanel.add(keyField);
        inputPanel.add(new JLabel("Wert:"));
        inputPanel.add(valueField);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 5, 5));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        buttonPanel.add(putButton);
        buttonPanel.add(getButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(saveButton);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(inputPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane(cacheList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Gespeicherte Einträge"));

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}
