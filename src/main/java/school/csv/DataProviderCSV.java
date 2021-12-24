package school.csv;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Constants;
import school.DataProvider;
import school.HistoryContent;
import school.School;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class DataProviderCSV extends DataProvider {
    private static final Logger logger = LogManager.getLogger(DataProviderCSV.class);

    public DataProviderCSV() {
    }

    @Override
    public School getById(long id) {
        List<School> schools = selectSchools();
        School school = null;
        try {
            school = schools.stream().filter(a -> a.getId() == id).collect(Collectors.toList()).get(0);
        } catch (IndexOutOfBoundsException e) {
            logger.throwing(e);
        }
        return school;
    }

    @Override
    public List<School> selectSchools() {
        List<School> schools = null;
        try {
            schools = new CsvToBeanBuilder<School>(new FileReader(Constants.CSV))
                    .withType(School.class).build().parse();
        } catch (FileNotFoundException e) {
            logger.throwing(e);
        }
        return schools;
    }

    @Override
    public void insert(School school) {
        Writer writer = null;
        File file = new File(Constants.CSV);
        if (file.exists()){
            try {
                writer = new FileWriter(file, true);
            } catch (IOException e) {
                logger.throwing(e);
            }
            ColumnPositionMappingStrategy<School> mappingStrategy = new ColumnPositionMappingStrategy<>();
            mappingStrategy.setType(School.class);
            mappingStrategy.setColumnMapping(Constants.ID, Constants.SUBJECT, Constants.TEACHER);
            StatefulBeanToCsv<School> beanToCsv = new StatefulBeanToCsvBuilder<School>(writer).withMappingStrategy(mappingStrategy).build();
            try {
                beanToCsv.write(school);
            } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
                logger.throwing(e);
            }
        } else {
            try {
                writer = new FileWriter(file, true);
            } catch (IOException e) {
                logger.throwing(e);
            }
            StatefulBeanToCsv<School> beanToCsv = new StatefulBeanToCsvBuilder<School>(writer).build();
            try {
                beanToCsv.write(school);
            } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
                logger.throwing(e);
            }
        }
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            logger.throwing(e);
        }
        saveHistory(getClass().getName(),"insert", HistoryContent.Status.SUCCESS, school);
    }

    @Override
    public void delete(long id) {
        School school1 = getById(id);
        if (school1 == null){
            return;
        }
        List<School> schools = selectSchools();
        schools.removeIf(school -> (school.getId() == id));
        Writer writer = null;
        try {
            writer = new FileWriter(Constants.CSV);
        } catch (IOException e) {
            logger.throwing(e);
        }
        StatefulBeanToCsv<School> beanToCsv = new StatefulBeanToCsvBuilder<School>(writer).build();
        try {
            beanToCsv.write(schools);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            logger.throwing(e);
        }
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            logger.throwing(e);
        }
    }

    @Override
    public void update(School school) {
        School school1 = getById(school.getId());
        if (school1 == null){
            return;
        }
        delete(school.getId());
        insert(school);
    }
}
