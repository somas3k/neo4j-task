package domain;

import org.neo4j.ogm.annotation.*;
import org.neo4j.ogm.annotation.typeconversion.DateString;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class Person {

    @Id @GeneratedValue
    private Long id;

    @Property(name = "name")
    private String name;

    @Property(name = "surname")
    private String surname;

    @DateString("dd/MM/yy")
    private Date birthday;

    @Relationship(type = "LIVES_IN")
    private PersonCity livesIn;

    @Relationship(type = "BIRTH_IN")
    private City birthCity;

    @Relationship(type = "LIKES")
    private Set<Person> likedPeople;

    @Relationship(type = "LIKES", direction = Relationship.INCOMING)
    private Set<Person> likesMe;

    @Relationship(type = "CITIZEN_OF")
    private Set<Country> countries;

    public Person() {
    }

    public Person(String name, String surname, Date birthday) {
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
    }

    public void citizenOf(Country c){
        if(countries == null){
            countries = new HashSet<>();
        }
        countries.add(c);
        c.addCitizen(this);
    }

    public void like(Person p){
        if(likedPeople == null){
            likedPeople = new HashSet<>();
        }
        likedPeople.add(p);
        p.likesMe(this);
    }

    private void likesMe(Person p){
        if(likesMe == null){
            likesMe = new HashSet<>();
        }
        likesMe.add(p);
    }


    public void birthIn(City city){
        if(birthCity == null) {
            birthCity = city;
            city.birthsHere(this);
        }
        else{
            birthCity.removeBirthHere(this);
            birthCity = city;
            city.birthsHere(this);
        }
    }

    void livesIn(PersonCity pc){
        livesIn = pc;
    }

    public void removeBirthCity(){
        City tmp = birthCity;
        birthCity = null;
        if(tmp.getBirthsHere().contains(this)) tmp.removeBirthHere(this);
    }

    public void removeCountry(Country c) throws NullPointerException{
        countries.remove(c);
        if(c.getCitizens().contains(this)) c.removeCitizen(this);
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthday=" + sdf.format(birthday) +
                '}';
    }

    public PersonCity getLivesIn() {
        return livesIn;
    }

    public City getBirthCity() {
        return birthCity;
    }

    public Set<Person> getLikesMe() {
        return likesMe;
    }

    public Set<Person> getLikedPeople() {
        return likedPeople;
    }

    public Set<Country> getCountries() {
        return countries;
    }

    public Long getId() {
        return id;
    }

}
