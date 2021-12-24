package school.DB;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Constants;
import school.DataProvider;
import school.HistoryContent;
import school.School;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataProviderSql extends DataProvider {
    private static final Logger logger = LogManager.getLogger(DataProviderSql.class);
    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;
    @Override
    public School getById(long id) {
        School school = new School();
        statement = getStatement();//подключаемся к датабазе
        try {
            resultSet = statement.executeQuery(String.format(Constants.GET_BY_ID, id));//выполяем запрос на get_by_id
            if (resultSet.next()){
                school.setId(resultSet.getLong(1));
                school.setTeacher(resultSet.getString(2));
                school.setSubject(resultSet.getString(3));
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return school;
    }

    @Override
    public List<School> selectSchools() {
        List<School> schools = new ArrayList<>();
        statement = getStatement();
        try {
            resultSet = statement.executeQuery(Constants.SELECT_ALL);
            while (resultSet.next()){//здесь мы получаем все строки
                School school = new School(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3));//тоже самое что и resultSet
                schools.add(school);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return schools;
    }

    @Override
    public void insert(School school) {
        statement = getStatement();
        try {
            statement.execute(String.format(Constants.INSERT, school.getId(), school.getTeacher(), school.getSubject()));
            saveHistory(getClass().getName(), "insert", HistoryContent.Status.SUCCESS, school);
        } catch (SQLException e) {
            logger.error(e);
            saveHistory(getClass().getName(), "insert", HistoryContent.Status.FAULT, school);
        }
    }

    @Override
    public void delete(long id) {//удаляем по id
        statement = getStatement();
        try {
            statement.execute(String.format(Constants.DELETE, id));
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    /**
     *
     * @param school
     */
    @Override
    public void update(School school) {//обновляем наш school
        statement = getStatement();
        try {
            statement.execute(String.format(Constants.UPDATE, school.getTeacher(), school.getSubject(), school.getId()));
            saveHistory(getClass().getName(), "update", HistoryContent.Status.SUCCESS, school);
        } catch (SQLException e) {
            logger.error(e);
            saveHistory(getClass().getName(), "update", HistoryContent.Status.FAULT, school);
        }
    }

    public static Statement getStatement() {//подключается к датабазе
        Statement stmt = null;
        try {
            try {
                Class.forName(Constants.JDBC_DRIVER);
            } catch (ClassNotFoundException e) {
                logger.error(e);
            }
            connection = DriverManager.getConnection(Constants.ADDRESS + Constants.DB_NAME, Constants.USER, Constants.PASSWORD);
            stmt = connection.createStatement();
            stmt.execute(Constants.CREATE_TABLE);//создаем таблицу
        } catch (SQLException e) {
            logger.error(e);
        }
        return stmt;//возвращаем
    }

    public void clear(){
        statement = getStatement();
        try {
            statement.execute(Constants.CLEAR);
        } catch (SQLException e) {
            logger.error(e);
        }
    }
}
