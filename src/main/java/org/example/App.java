package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import school.csv.DataProviderCSV;
import school.School;

import java.util.List;

public class App {
    private static final Logger logger = LogManager.getLogger(App.class);

    public App() {
        logger.debug("App()[0]: starting application.........");
    }

    public static void main( String[] args ) {
//        String key = "qwerty";
//        String properties = null;
//        try {
//            properties = ConfigurationUtil.getConfigurationEntry(key);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        logger.info(properties);
        //Вписать норм названия
        School school1 = new School(1, "Jack", "math");
        School school2 = new School(2, "Thomas", "art");
        School school3 = new School(3, "Riley", "English");
        DataProviderCSV dataProviderCSV = new DataProviderCSV();
        //Пример insert
        dataProviderCSV.insert(school1);
        dataProviderCSV.insert(school2);
        dataProviderCSV.insert(school3);
        //Пример  selectSchools
        List<School> schoolList = dataProviderCSV.selectSchools();
        logger.info("\nSelect example");
        schoolList.forEach(logger::info);
        //Пример  getById
        School schoolById = dataProviderCSV.getById(2);
        logger.info("\ngetById example\n" + schoolById);
        //Пример  delete
        dataProviderCSV.delete(2);
        schoolList = dataProviderCSV.selectSchools();
        logger.info("\ndelete example");
        schoolList.forEach(logger::info);
        //Пример  update
        School schoolUpdate = new School(4, "Emily", "physics");
        dataProviderCSV.update(schoolUpdate);
        schoolList = dataProviderCSV.selectSchools();
        logger.info("\nupdate example");
        schoolList.forEach(logger::info);
    }
    private static void logBasicSystemInfo() {
        logger.info("Launching the application...");
        logger.info(
                "Operating System: " + System.getProperty("os.name") + " "
                        + System.getProperty("os.version")
        );
        logger.info("JRE: " + System.getProperty("java.version"));
        logger.info("Java Launched From: " + System.getProperty("java.home"));
        logger.info("Class Path: " + System.getProperty("java.class.path"));
        logger.info("Library Path: " + System.getProperty("java.library.path"));
        logger.info("User Home Directory: " + System.getProperty("user.home"));
        logger.info("User Working Directory: " + System.getProperty("user.dir"));
        logger.info("Test INFO logging.");

    }
}