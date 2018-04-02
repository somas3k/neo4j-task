import domain.City;
import domain.Country;
import domain.Person;
import domain.PersonCity;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class Solution {
    private final SessionFactory sessionFactory = new Neo4JConfig(Neo4JConfig.CREATE, "neo4j", "Sabina95").sessionFactory();

    private void populateData(){
        Session session = sessionFactory.openSession();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Person p1 = new Person("Kamil", "Wróbel", sdf.parse("19/01/1995"));
            Person p2 = new Person("Wiktoria", "Mardyło", sdf.parse("23/10/1995"));
            Person p3 = new Person("Cristiano", "Ronaldo", sdf.parse("05/02/1985"));
            Person p4 = new Person("Dawid", "Krysiński", sdf.parse("18/07/1995"));
            Person p5 = new Person("Tom", "Hanks", sdf.parse("09/07/1956"));

            City c1 = new City("Sosnowiec", 206516L, 91.06D);
            City c2 = new City("Rome", 2869461L, 1287D);
            City c3 = new City("Funchal", 111892L, 76.15D);
            City c4 = new City("Concord", 122067L, 79114D);
            City c5 = new City("Lublin", 340466L, 147.5D);
            City c6 = new City("Madrid", 3215633L, 607D);
            City c7 = new City("Ookland", 420005L, 201.7);

            Country cy1 = new Country("Poland", "polish", 37950000L, 312679D);
            Country cy2 = new Country("Portugal", "portuguese", 10833816L , 92391D);
            Country cy3 = new Country("Italy", "italian", 60788140L, 301230D);
            Country cy4 = new Country("USA", "english", 326079000L, 9373967D);
            Country cy5 = new Country("Spain", "spanish", 48563476L, 504645D);

            PersonCity pc1 = new PersonCity(sdf.parse("19/01/1995"), p1, c1);
            PersonCity pc2 = new PersonCity(sdf.parse("29/09/2017"), p2, c5);
            PersonCity pc3 = new PersonCity(sdf.parse("24/08/2009"), p3, c6);
            PersonCity pc4 = new PersonCity(sdf.parse("13/03/2011"), p5, c7);
            PersonCity pc5 = new PersonCity(sdf.parse("18/07/1995"), p4, c1);

            p1.like(p2);
            p2.like(p1);
            p1.like(p3);
            p1.like(p4);
            p2.like(p5);
            p3.like(p5);

            p1.citizenOf(cy1);
            p2.citizenOf(cy1);
            p3.citizenOf(cy2);
            p4.citizenOf(cy1);
            p4.citizenOf(cy3);
            p5.citizenOf(cy4);

            p1.birthIn(c1);
            p2.birthIn(c1);
            p3.birthIn(c3);
            p4.birthIn(c2);
            p5.birthIn(c4);

            c1.isIn(cy1);
            c2.isIn(cy3);
            c3.isIn(cy2);
            c4.isIn(cy4);
            c5.isIn(cy1);
            c6.isIn(cy5);
            c7.isIn(cy4);

            session.save(p1); session.save(p2); session.save(p3); session.save(p4); session.save(p5);

            session.save(c1); session.save(c2); session.save(c3); session.save(c4); session.save(c5); session.save(c6);
            session.save(c7);

            session.save(cy1); session.save(cy2); session.save(cy3); session.save(cy4); session.save(cy5);

            session.save(pc1); session.save(pc2); session.save(pc3); session.save(pc4); session.save(pc5);
        }
        catch (ParseException e){
            e.printStackTrace();
        }
    }

    public Solution(){
        populateData();
    }

    public Person loadPersonByProperties(String name, String surname){
        Session session = sessionFactory.openSession();

        Map<String, String> params = new HashMap<>();
        params.put("NAME", name);
        params.put("SURNAME", surname);
        Person p = session.queryForObject(Person.class,"MATCH (p:Person {name:{NAME}, surname:{SURNAME}}) return p", params);
        return session.load(Person.class, p.getId());
    }

    public Country loadCountryByName(String countryName){
        Session session = sessionFactory.openSession();

        Map<String, String> params = new HashMap<>();
        params.put("NAME", countryName);;
        Country p = session.queryForObject(Country.class,"MATCH (c:Country {name:{NAME}}) return c", params);
        return session.load(Country.class, p.getId());
    }

    public City loadCityByName(String cityName){
        Session session = sessionFactory.openSession();

        Map<String, String> params = new HashMap<>();
        params.put("NAME", cityName);
        City p = session.queryForObject(City.class,"MATCH (c:City {name:{NAME}}) return c", params);
        return session.load(City.class, p.getId());
    }

    public String getAllLikedPeople(String name, String surname){
        Person p = loadPersonByProperties(name, surname);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name).append(" ").append(surname).append(" likes:\n");

        for(Person x : p.getLikedPeople()){
            stringBuilder.append(x.toString());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public String getPeopleWhoLikes(String name, String surname){
        Person p = loadPersonByProperties(name, surname);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("People who likes ").append(name).append(" "). append(surname).append(":\n");
        for (Person p1 : p.getLikesMe()){
            stringBuilder.append(p1.toString()).append("\n");
        }
        return stringBuilder.toString();
    }

    public String getCitizenships(String name, String surname){
        Person p = loadPersonByProperties(name, surname);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name).append(" ").append(surname).append(" is citizen of:\n");
        for(Country x : p.getCountries()){
            stringBuilder.append(x.toString());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public String getLivedCity(String name, String surname){
        Person p = loadPersonByProperties(name, surname);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name).append(" ").append(surname).append(" lives in:\n");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String x = p.getLivesIn().getCity() + " from " + sdf.format(p.getLivesIn().getFromDate());
        stringBuilder.append(x);
        return stringBuilder.toString();
    }

    public String getBirthCity(String name, String surname){
        Person p = loadPersonByProperties(name, surname);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name).append(" ").append(surname).append(" birth in:\n").append(p.getBirthCity().toString());
        return stringBuilder.toString();
    }

    public String getAllCitizens(String countryName){
        Country c = loadCountryByName(countryName);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Citizens of ").append(countryName).append(":\n");
        for(Person p : c.getCitizens()){
            stringBuilder.append(p.toString());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public String getAllCities(String countryName){
        Country c = loadCountryByName(countryName);
        StringBuilder  stringBuilder = new StringBuilder();
        stringBuilder.append("Cities in ").append(countryName).append(":\n");
        for(City cy : c.getCities()){
            stringBuilder.append(cy.toString());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public String getCountryOfCity(String cityName){
        City c = loadCityByName(cityName);
        return cityName + " is in:\n" + c.getCountry().toString();
    }

    public String getBirthsInCity(String cityName){
        City c = loadCityByName(cityName);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("People births in ").append(cityName).append(" :\n");
        for(Person p : c.getBirthsHere()){
            stringBuilder.append(p.toString());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public String getLivesInCity(String cityName){
        City c = loadCityByName(cityName);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        StringBuilder builder = new StringBuilder();
        builder.append("Residents of ").append(cityName).append(":\n");
        for(PersonCity pc : c.getResidents()){
            builder.append(pc.getPerson().toString());
            builder.append(" from ");
            builder.append(sdf.format(pc.getFromDate()));
            builder.append("\n");
        }
        return builder.toString();
    }

}
