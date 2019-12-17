package data;

import com.sun.xml.bind.XmlAccessorFactory;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@XmlAccessorType(XmlAccessType.FIELD)
public class TelephoneContactBusiness implements Serializable {
    @XmlAttribute
    private int telephoneContactId;

    private String name;
    private Date dayBirth;

    @XmlElementWrapper(name = "numbers")
    @XmlElement(name = "number")
    private Set<OperatorBusiness> numbers;
    @XmlElement(name = "address")
    private AddressBusiness homeLocation;

    private PhoneBookBusiness phonebook;

    public TelephoneContactBusiness(){
        super();
    }

    public TelephoneContactBusiness(TelephoneContact contact){
        this.telephoneContactId = contact.getTelephoneContactId();
        this.name = contact.getName();
        this.dayBirth = contact.getDayBirth();
        this.numbers = new HashSet<>();
        this.homeLocation = null;
        //this.phonebook = null;
    }
    public TelephoneContactBusiness(String name, Date dayBirth, Set<OperatorBusiness> numbers, AddressBusiness homeLocation, PhoneBookBusiness phonebook){
        this.name = name;
        this.dayBirth = dayBirth;
        this.numbers = numbers;
        this.homeLocation = homeLocation;
        this.phonebook = phonebook;
    }

    public void addOperatorNumber(OperatorBusiness op){
        this.numbers.add(op);
    }

    public int getTelephoneContactId() {
        return telephoneContactId;
    }

    public void setTelephoneContactId(int telephoneContactId) {
        this.telephoneContactId = telephoneContactId;
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

    public Set<OperatorBusiness> getNumbers() {
        return numbers;
    }

    public void setNumbers(Set<OperatorBusiness> numbers) {
        this.numbers = numbers;
    }

    public AddressBusiness getHomeLocation() {
        return homeLocation;
    }

    public void setHomeLocation(AddressBusiness homeLocation) {
        this.homeLocation = homeLocation;
    }

    public PhoneBookBusiness getPhonebook() {
        return phonebook;
    }

    public void setPhonebook(PhoneBookBusiness phonebook) {
        this.phonebook = phonebook;
    }
}
