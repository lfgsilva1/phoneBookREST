package data;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@XmlRootElement(name = "PhoneBook")
public class PhoneBookBusiness implements Serializable {

    private int phoneBookId;
    @XmlElementWrapper(name = "contacts")
    @XmlElement(name = "contact")
    private Set<TelephoneContactBusiness> contacts;

    public PhoneBookBusiness(){
        this.contacts = new HashSet<>();
    }

    public void addContact(TelephoneContactBusiness contact){this.contacts.add(contact);}

    public int getPhoneBookId() {
        return phoneBookId;
    }

    public void setPhoneBookId(int phoneBookId) {
        this.phoneBookId = phoneBookId;
    }

    public Set<TelephoneContactBusiness> getContacts() {
        return contacts;
    }

    /*public void setContacts(Set<TelephoneContactBusiness> contacts) {
        this.contacts = contacts;
    }*/
}
