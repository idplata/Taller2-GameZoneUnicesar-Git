package com.gamezone.persistence;

import com.gamezone.model.Accesory;
import com.gamezone.model.Cable;
import com.gamezone.model.Controller;
import com.gamezone.model.Memory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AccesoryRepository {

    private static final String DATA_DIRECTORY = "data";
    private static final String FILE_PATH = DATA_DIRECTORY + "/accessories.csv";
    private static final String SEPARATOR = ";";
    private static final String CONSOLE_LIST_DELIMITER = "\\|";
    private static final String CONSOLE_LIST_JOINER = "|";

    public AccesoryRepository() {
        try {
            Files.createDirectories(Paths.get(DATA_DIRECTORY));
        } catch (IOException e) {
            throw new RuntimeException("Could not create data directory", e);
        }
    }

    public void saveAll(List<Accesory> accesories) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Accesory accesory : accesories) {
                writer.write(toCsvLine(accesory));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error saving accessories", e);
        }
    }

    public List<Accesory> loadAll() {
        List<Accesory> accesories = new ArrayList<>();
        Path path = Paths.get(FILE_PATH);
        if (!Files.exists(path)) {
            return accesories;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }
                Accesory accesory = fromCsvLine(line);
                if (accesory != null) {
                    accesories.add(accesory);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error loading accessories", e);
        }
        return accesories;
    }

    private String toCsvLine(Accesory accesory) {
        String consoleIds = String.join(CONSOLE_LIST_JOINER, accesory.getCompatibleConsoles());
        StringBuilder line = new StringBuilder();
        line.append(accesory.getAccessoryType()).append(SEPARATOR)
                .append(accesory.getId()).append(SEPARATOR)
                .append(accesory.getTitle()).append(SEPARATOR)
                .append(accesory.getPrice()).append(SEPARATOR)
                .append(accesory.getQuantity()).append(SEPARATOR)
                .append(consoleIds).append(SEPARATOR);

        if (accesory instanceof Controller controller) {
            line.append(controller.getConnectionType());
        } else if (accesory instanceof Cable cable) {
            line.append(cable.getLength()).append(SEPARATOR).append(cable.getConnectorType());
        } else if (accesory instanceof Memory memory) {
            line.append(memory.getGigabytes()).append(SEPARATOR).append(memory.getMemoryType());
        }

        return line.toString();
    }

    private Accesory fromCsvLine(String line) {
        String[] fields = line.split(SEPARATOR, -1);

        String type = fields[0];
        String id = fields[1];
        String title = fields[2];
        double price = Double.parseDouble(fields[3]);
        int quantity = Integer.parseInt(fields[4]);
        String consoleIdsRaw = fields[5];

        List<String> compatibleConsoles = new ArrayList<>();
        if (!consoleIdsRaw.isBlank()) {
            for (String consoleId : consoleIdsRaw.split(CONSOLE_LIST_DELIMITER)) {
                compatibleConsoles.add(consoleId);
            }
        }

        Accesory accesory = switch (type) {
            case "CONTROLLER" -> new Controller(id, title, price, quantity, fields[6]);
            case "CABLE" -> new Cable(id, title, price, quantity,
                    Double.parseDouble(fields[6]), fields[7]);
            case "MEMORY" -> new Memory(id, title, price, quantity,
                    Integer.parseInt(fields[6]), fields[7]);
            default -> null;
        };

        if (accesory != null) {
            accesory.setCompatibleConsoles(compatibleConsoles);
        }

        return accesory;
    }
}