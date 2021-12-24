package org.example;

import org.junit.Before;
import org.junit.Test;
import school.DB.DataProviderSql;
import school.School;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotEquals;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    DataProviderSql dataProvider = new DataProviderSql();
    School school = new School( 1, "Jack", "math");
    School school1 = new School( 2, "Thomas", "art");
    School school2 = new School( 3, "Riley", "English");
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }


    @Before//этот метод вызывается перед каждым тестом
    public void initTestData(){
        dataProvider.insert(school1);
        dataProvider.insert(school);
        dataProvider.insert(school2);
    }

    @Test
    public void positiveGetById(){//все правильно вводим
        School byId = dataProvider.getById(school1.getId());
        assertEquals(school1.getId(), byId.getId());
    }

    @Test
    public void negativeGetById(){//пытаемся получить по несуществующему Id
        School byId = dataProvider.getById(10);
        assertNotEquals(school1.getId(), byId.getId());
    }

    @Test
    public void positiveSelect(){//выбираем все и проверяем размерность
        List<School> schools;
        schools = dataProvider.selectSchools();
        assertEquals(3, schools.size());
    }


    @Test
    public void positiveUpdate(){//вводим все правильно и обновляем
        School newSchool = new School(3,"Vika", "RUS");
        dataProvider.update(newSchool);
        assertEquals(newSchool, dataProvider.getById(newSchool.getId()));
    }

    @Test
    public void negativeUpdate(){//пытаемся обновить несуществующий Id
        School newSchool = new School(4, "Kiti", "Football");
        dataProvider.update(newSchool);
        assertNotEquals(newSchool, dataProvider.getById(newSchool.getId()));
    }

    @Test
    public void positiveDelete(){//удачное удаление
        School deleted = new School();
        dataProvider.delete(2);
        assertEquals(deleted, dataProvider.getById(2));
    }


   // @Test
    public void clear(){
        dataProvider.clear();
    }//очистка

}
