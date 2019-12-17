package ejb;

import data.*;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface BusinessBeanInterface {
    //put the methods here
    public String createCall(String test);

    public PhoneBookBusiness getContacts();
    public PhoneBookBusiness getContact(int id);
    public Boolean removeContact(int id);

    //to update a contact
    public int updateContactOperator(int userID, int operatorNumID, OperatorBusiness op);
    public int updateContactAddress(int userID, AddressBusiness adr);
    public int updateContact(int userID, TelephoneContactBusiness usr);

    //to create a contact
    public int addContactOperator(int id, OperatorBusiness op);
    public int addContactAddress(int id, AddressBusiness adr);
    public int addContact(TelephoneContactBusiness contact);


}
