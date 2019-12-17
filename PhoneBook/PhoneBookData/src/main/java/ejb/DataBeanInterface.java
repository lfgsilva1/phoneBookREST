package ejb;

import data.Address;
import data.Operator;
import data.TelephoneContact;

import javax.ejb.Remote;
import java.util.Date;
import java.util.List;

@Remote
public interface DataBeanInterface {
    //put the methods here
    String createCallData(String test);

    public List<TelephoneContact> getContacts();
    public List<Address> getAddresses();
    public List<Operator> getOperators();

    public TelephoneContact getContact(int userID);
    public Address getAddress(int userID);
    public List<Operator> getOperatorsByUser(int userID);

    public boolean updateOperator( int operatorNumID, String nameOperator, String number);
    public boolean updateAddress(int userID, String ZipCode, int streetNumber, String street, int houseNumber, String city);
    public boolean updateContact(int userID, Date birthDay, String name);

    public boolean createContactOperator(int userID, String operatorName, String number);
    public boolean createContactAddress(int userID, String city, int houseNumber, String street, int streetNumber, String ZipCode);
    public boolean createContact(Date birthDay, String name);

    public boolean removeContact(int userID);

}
