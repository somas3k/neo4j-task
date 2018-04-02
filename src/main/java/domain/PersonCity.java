package domain;

import org.neo4j.ogm.annotation.*;
import org.neo4j.ogm.annotation.typeconversion.DateString;

import java.util.Date;

@RelationshipEntity(type = "LIVES_IN")
public class PersonCity {
    @Id @GeneratedValue
    private Long relationshipId;

    @DateString("dd/MM/yy")
    private Date fromDate;

    @StartNode
    private Person person;

    @EndNode
    private City city;

    public PersonCity() {
    }

    public PersonCity(Date fromDate, Person person, City city) {
        this.fromDate = fromDate;
        this.person = person;
        this.city = city;

        person.livesIn(this);
        city.resident(this);
    }

    public Date getFromDate() {
        return fromDate;
    }

    public Person getPerson() {
        return person;
    }

    public City getCity() {
        return city;
    }
}
