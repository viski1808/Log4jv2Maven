package school;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import org.example.Constants;

import java.util.Date;
import java.util.Objects;

public class School {
    @CsvBindByName(column = Constants.ID)
    long id;
    @CsvBindByName(column = Constants.TEACHER)
    String teacher;
    @CsvBindByName(column = Constants.SUBJECT)
    String subject;

    public School(long id, String teacher, String subject) {
        this.id = id;
        this.teacher = teacher;
        this.subject = subject;
    }

    public School(String teacher, String subject) {
        Date date = new Date();
        this.id = date.getTime();
        this.teacher = teacher;
        this.subject = subject;
    }

    public School() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "School{" +
                "id=" + id +
                ", teacher='" + teacher + '\'' +
                ", subject='" + subject + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        School school = (School) o;
        return id == school.id && Objects.equals(teacher, school.teacher) && Objects.equals(subject, school.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, teacher, subject);
    }
}
