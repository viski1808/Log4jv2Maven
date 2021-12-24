package school;

import java.util.List;

public interface IDataProvider {
    School getById(long id);
    List<School> selectSchools();
    void insert(School school);
    void delete(long id);
    void update(School school);

}
