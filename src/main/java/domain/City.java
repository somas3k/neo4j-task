package domain;

import org.neo4j.ogm.annotation.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class City {

    @Id @GeneratedValue
    private Long id;

    @Property(name = "name")
    private String name;

    @Property(name = "population")
    private Long population;

    @Property(name = "area")
    private Double area;

    @Relationship(type = "IS_IN")
    private Country country;

    @Relationship(type = "LIVES_IN", direction = Relationship.INCOMING)
    private Set<PersonCity> residents;

    @Relationship(type = "BIRTH_IN", direction = Relationship.INCOMING)
    private Set<Person> birthsHere;

    public City() {
    }

    public City(String name, Long population, Double area) {
        this.name = name;
        this.population = population;
        this.area = area;
    }

    void resident(PersonCity pc){
        if(residents == null){
            residents = new HashSet<PersonCity>();
        }
        residents.add(pc);
    }

    public void birthsHere(Person p){
        if(birthsHere == null){
            birthsHere = new HashSet<Person>();
        }
        birthsHere.add(p);
        if(p.getBirthCity() != this) p.birthIn(this);
    }

    public void isIn(Country country){
        if(this.country == null){
            this.country = country;
            country.addCity(this);
        }
        else{
            this.country.removeCity(this);
            this.country = country;
            country.addCity(this);
        }
    }

    public void removeCountry(){
        Country tmp = country;
        country = null;
        if(tmp.getCities().contains(this)) tmp.removeCity(this);
    }

    public void removeBirthHere(Person p) throws NullPointerException{
        birthsHere.remove(p);
        if(p.getBirthCity()==this) p.removeBirthCity();
    }

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                ", population=" + population +
                ", area=" + area +
                '}';
    }

    public Long getId() {
        return id;
    }

    public Country getCountry() {
        return country;
    }

    public Set<PersonCity> getResidents() {
        return residents;
    }

    public Set<Person> getBirthsHere() {
        return birthsHere;
    }
}
