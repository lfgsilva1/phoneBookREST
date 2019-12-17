package data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
public class TelephoneContact implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int telephoneContactId;

    private String name;
    @Temporal(TemporalType.DATE)
    private Date dayBirth;

    //cascade = CascadeType.ALL - when a user is removed, also it is removed all data associated with him
    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Operator> numbers;
    @OneToOne(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private Address homeLocation;
    @ManyToOne//many contacts belong to the same phonebook
    private PhoneBook phonebook;

    public TelephoneContact(){
        this.name = null;
        this.dayBirth = null;
        this.numbers = new ArrayList<>();
        //this.homeLocation = null;
        this.phonebook = null;
    }

    public TelephoneContact(String name, Date dayBirth, List<Operator> numbers, Address homeLocation, PhoneBook phonebook){
        this.name = name;
        this.dayBirth = dayBirth;
        this.numbers = numbers;
        //this.homeLocation = homeLocation;
        this.phonebook = phonebook;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDayBirth() {
        return dayBirth;
    }

    public void setDayBirth(Date dayBirth) {
        this.dayBirth = dayBirth;
    }

    public List<Operator> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<Operator> numbers) {
        this.numbers = numbers;
    }

    public Address getHomeLocation() {
        return homeLocation;
    }

    public void setHomeLocation(Address homeLocation) {
        this.homeLocation = homeLocation;
    }

    public int getTelephoneContactId() {
        return telephoneContactId;
    }

    public PhoneBook getPhonebook() {
        return phonebook;
    }

    public void setPhonebook(PhoneBook phonebook) {
        this.phonebook = phonebook;
    }
}

