package org.example;

public class Constants {
    public static final String PACKAGE = "org.example";
    public static final String PROPERTIES_PATH = "pathToProperties";
    public static final String ID = "id";
    public static final String TEACHER = "teacher";
    public static final String SUBJECT = "subject";
    public static final String CSV = "schools.csv";
    public static final String XML = "schools.xml";
    public static final String SCHOOL = "school";
    public static final String SCHOOLS = "schools";

    public static final String ACTOR = "system";


    public static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver"; //драйвер для соединения Java и DataBase
    public static final String ADDRESS = "jdbc:mysql://localhost:3306/"; //где DataBase
    public static final String DB_NAME = "laba";
    public static final String USER = "root";
    public static final String PASSWORD = "qwerty";

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS Schools (" +
            "id SERIAL, " +
            "teacher TEXT, " +
            "subject TEXT);";
    public static final String INSERT = "INSERT INTO Schools (id, teacher, subject) VALUES ('%s', '%s', '%s');";//создать
    public static final String GET_BY_ID = "SELECT * FROM Schools WHERE id = %d;";//выбор по id
    public static final String SELECT_ALL = "SELECT * FROM Schools;";
    public static final String DELETE = "DELETE FROM Schools WHERE id = %d;";
    public static final String UPDATE = "UPDATE Schools SET teacher = '%s', subject = '%s' WHERE id = %d;";
    public static final String CLEAR = "TRUNCATE TABLE Schools;";//удалить все данные
}
