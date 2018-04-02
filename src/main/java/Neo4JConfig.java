import domain.City;
import domain.Country;
import domain.Person;
import domain.PersonCity;
import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;

public class Neo4JConfig {
    private final String URL;
    public static final boolean CREATE = true;
    public static final boolean UPDATE = false;
    private final boolean type;

    public Neo4JConfig(boolean type, String login, String password) {
        this.type = type;
        URL = "http://"+login+":"+password+"@localhost:7474";
    }

    private Configuration configuration(){
        return new Configuration.Builder().uri(URL).build();
    }

    public SessionFactory sessionFactory(){
        if(type){
            SessionFactory sessionFactory = new SessionFactory(configuration(), "domain");
            Session session = sessionFactory.openSession();
            session.deleteAll(Person.class);
            session.deleteAll(City.class);
            session.deleteAll(Country.class);
            session.deleteAll(PersonCity.class);
            sessionFactory.close();
        }
        return new SessionFactory(configuration(), "domain");
    }






}
