package ru.nsu.ccfit.chumak.factory.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.Scanner;

public class ConfigReader {
    private final static Logger logger = LogManager.getLogger(ConfigReader.class);

    private int bodyworkStorageVolume = 100;
    private int engineStorageVolume = 100;
    private int accessoryStorageVolume = 100;
    private int carStorageVolume = 100;
    private int countAccessoryProducers = 5;
    private int countWorkers = 10;
    private int countDealers = 20;
    private boolean isLoggingSales = true;

    private final long engineProducerStartCooldown = 100;
    private final long bodyworkProducerStartCooldown = 100;
    private final long accessoryProducerStartCooldown = 100;
    private final long carConsumerStartCooldown = 100;

    public ConfigReader() {
        try(Scanner scanner = new Scanner(new File("config.txt"))){
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if(line.startsWith("BodyworkStorageVolume")) {
                    bodyworkStorageVolume = Integer.parseInt(line.split("=")[1]);
                } else if(line.startsWith("EngineStorageVolume")) {
                    engineStorageVolume = Integer.parseInt(line.split("=")[1]);
                } else if(line.startsWith("AccessoryStorageVolume")) {
                    accessoryStorageVolume = Integer.parseInt(line.split("=")[1]);
                } else if(line.startsWith("CarStorageVolume")) {
                    carStorageVolume = Integer.parseInt(line.split("=")[1]);
                } else if(line.startsWith("CountAccessoryProducers")) {
                    countAccessoryProducers = Integer.parseInt(line.split("=")[1]);
                } else if(line.startsWith("CountWorkers")) {
                    countWorkers = Integer.parseInt(line.split("=")[1]);
                } else if(line.startsWith("CountDealers")) {
                    countDealers = Integer.parseInt(line.split("=")[1]);
                } else if(line.startsWith("LogSale")) {
                    isLoggingSales = Boolean.parseBoolean(line.split("=")[1]);
                }
            }
        } catch (Exception e) {
            logger.error(e);
        }
    }

    public int getBodyworkStorageVolume() {
        return bodyworkStorageVolume;
    }

    public int getEngineStorageVolume() {
        return engineStorageVolume;
    }

    public int getAccessoryStorageVolume() {
        return accessoryStorageVolume;
    }

    public int getCarStorageVolume() {
        return carStorageVolume;
    }

    public int getCountAccessoryProducers() {
        return countAccessoryProducers;
    }

    public int getCountWorkers() {
        return countWorkers;
    }

    public int getCountDealers() {
        return countDealers;
    }

    public boolean isLoggingSales() {
        return isLoggingSales;
    }

    public long getEngineProducerStartCooldown() {
        return engineProducerStartCooldown;
    }

    public long getBodyworkProducerStartCooldown() {
        return bodyworkProducerStartCooldown;
    }

    public long getAccessoryProducerStartCooldown() {
        return accessoryProducerStartCooldown;
    }

    public long getCarConsumerStartCooldown() {
        return carConsumerStartCooldown;
    }
}
