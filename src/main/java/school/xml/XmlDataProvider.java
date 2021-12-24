package school.xml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Constants;
import school.IDataProvider;
import school.School;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class XmlDataProvider implements IDataProvider {
    private static final Logger logger = LogManager.getLogger(XmlDataProvider.class);
    @Override
    public School getById(long id) {
        List<School> schoolList = selectSchools(); // выбираем все школы
        School school = null;
        try {
            school = schoolList.stream().filter(a -> a.getId() == id).collect(Collectors.toList()).get(0);// выбираем нужный id
        } catch (IndexOutOfBoundsException e) {
            logger.catching(e);
        }
        return school;
    }

    @Override
    public List<School> selectSchools() {
        Schools schools = new Schools();
        try {
            JAXBContext context = JAXBContext.newInstance(Schools.class);
            schools = read(context, schools);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return schools.getSchools();
    }

    @Override
    public void insert(School school) {
        File file = new File(Constants.XML);
        JAXBContext context;
        Schools schools = new Schools();
        List<School> schoolList = new ArrayList<>();
        Marshaller marshaller;
        if(file.exists()){ //если файл есть, значит в нем есть данные
            schoolList = selectSchools();
        }
        schoolList.add(school); //в List добавляется новый элемент
        schools.setSchools(schoolList);
        save(schools);
    }

    @Override
    public void delete(long id) {
        School byId = getById(id);
        if (byId == null){
            return;
        }
        JAXBContext context;
        Schools schools = new Schools();
        Marshaller marshaller = null;
        List<School> schoolList = selectSchools(); //выбираем все данные
        schoolList.removeIf(a -> (a.getId() == id)); //удаляем по нужному id
        schools.setSchools(schoolList); //запаковываем в файл
        save(schools);
    }

    @Override
    public void update(School school) {
        School byId = getById(school.getId());
        if (byId == null){
            return;
        }
        delete(school.getId());
        insert(school);
    }

    public void save(Schools schools){
        try {
            JAXBContext context = JAXBContext.newInstance(Schools.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(schools, new File(Constants.XML));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public Schools read(JAXBContext context, Schools schools){
        try {
            schools = (Schools) context.createUnmarshaller().unmarshal(new File(Constants.XML));//открываем файл и берем из него всю инфу
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return schools;
    }
}
