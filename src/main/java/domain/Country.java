package domain;

import org.neo4j.ogm.annotation.*;

import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class Country {

    @Id @GeneratedValue
    private Long id;

    @Property(name = "name")
    private String name;

    @Property(name = "language")
    private String language;

    @Property(name = "population")
    private Long population;

    @Property(name = "area")
    private Double area;

    @Relationship(type = "IS_IN", direction = Relationship.INCOMING)
    private Set<City> cities;

    @Relationship(type = "CITIZEN_OF", direction = Relationship.INCOMING)
    private Set<Person> citizens;

    public Country() {
    }

    public Country(String name, String language, Long population, Double area) {
        this.name = name;
        this.language = language;
        this.population = population;
        this.area = area;
    }

    public void addCity(City city){
        if(cities == null){
            cities = new HashSet<City>();
        }
        cities.add(city);
        if(city.getCountry() != this) city.isIn(this);
    }

    public void addCitizen(Person p){
        if(this.citizens == null){
            citizens = new HashSet<Person>();
        }
        citizens.add(p);
    }

    public void removeCity(City city) throws NullPointerException{
        cities.remove(city);
        if(city.getCountry() == this) city.removeCountry();
    }

    public void removeCitizen(Person p) throws NullPointerException{
        citizens.remove(p);
        if(p.getCountries().contains(this)) p.removeCountry(this);
    }

    @Override
    public String toString() {
        return "Country{" +
                "name='" + name + '\'' +
                ", language='" + language + '\'' +
                ", population=" + population +
                '}';
    }

    public Long getId() {
        return id;
    }

    public Set<City> getCities() {
        return cities;
    }

    public Set<Person> getCitizens() {
        return citizens;
    }
}
