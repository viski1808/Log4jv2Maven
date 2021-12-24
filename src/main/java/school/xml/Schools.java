package school.xml;

import school.School;
import org.example.Constants;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
@XmlRootElement(name = Constants.SCHOOLS)
public class Schools {
    private List<School> schools;

    public List<School> getSchools() {
        return schools;
    }
    @XmlElement(name = Constants.SCHOOL)
    public void setSchools(List<School> schools) {
        this.schools = schools;
    }
    @Override
    public String toString() {
        return schools.toString();
    }

}
