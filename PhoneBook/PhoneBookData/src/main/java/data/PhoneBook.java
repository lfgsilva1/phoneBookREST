package data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class PhoneBook implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int phoneBookId;
    @OneToMany(mappedBy = "phonebook", fetch = FetchType.LAZY, orphanRemoval = true)//LAZY to fetch elements when they are needed
    private List<TelephoneContact> contacts;

    public PhoneBook(){
        this.contacts = new ArrayList<>();
    }

    public void addContact(TelephoneContact contact){
        this.contacts.add(contact);
    }


    public List<TelephoneContact> getContacts() {
        return contacts;
    }

    public void setContacts(List<TelephoneContact> contacts) {
        this.contacts = contacts;
    }
}
