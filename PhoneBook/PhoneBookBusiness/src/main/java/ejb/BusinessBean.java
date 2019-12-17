package ejb;
import data.*;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Stateless
public class BusinessBean implements BusinessBeanInterface{
    DataBeanInterface dataEjb;

    @Resource
    SessionContext ctx;

    public BusinessBean(){
        InitialContext ctx = null;
        try {
            ctx = new InitialContext();
        } catch (NamingException e) {
            //e.printStackTrace();
        }
        try {
            dataEjb = (DataBeanInterface)ctx.lookup("global/EAR/org.example-PhoneBookData-1.0-SNAPSHOT/DataBean!ejb.DataBeanInterface");
        } catch (NamingException e) {
            //e.printStackTrace();
        }
    }

    @Override
    public PhoneBookBusiness getContacts(){
        PhoneBookBusiness phonebook = new PhoneBookBusiness();
        TelephoneContactBusiness contact;
        AddressBusiness adrbusiness;
        OperatorBusiness operator;

        //i got the contacts
        List<TelephoneContact> contacts =  dataEjb.getContacts();
        List<Operator> OpNumbers;
        Address adr;
        for (TelephoneContact c: contacts){
            //i parse the atual contact from data layer
            contact = new TelephoneContactBusiness(c);
            //i got the numbers which a contact have
            OpNumbers = dataEjb.getOperatorsByUser(contact.getTelephoneContactId());
            if (!OpNumbers.isEmpty()) {
                for (Operator o : OpNumbers) {
                    //Then i parse these numbers from data layer to this layer
                    operator = new OperatorBusiness(o);
                    //and add them to the contact
                    contact.addOperatorNumber(operator);
                }
            }
            //the same applies to the address
            adr = dataEjb.getAddress( contact.getTelephoneContactId() );
            if (adr != null) {
                adrbusiness = new AddressBusiness(adr);
                contact.setHomeLocation(adrbusiness);
            }
            phonebook.addContact(contact);
        }

        return phonebook;
    }
    @Override
    public PhoneBookBusiness getContact(int id){
        PhoneBookBusiness pc = new PhoneBookBusiness();
        TelephoneContact ct = dataEjb.getContact(id);
        if (ct != null){
            TelephoneContactBusiness contact = new TelephoneContactBusiness( ct );
            contact.setHomeLocation( new AddressBusiness( dataEjb.getAddress(id) ) );
            List<Operator> numbers = dataEjb.getOperatorsByUser(id);

            for (Operator o: numbers){
                contact.addOperatorNumber( new OperatorBusiness(o) );
            }

            pc.addContact(contact);
            return pc;
        }
        return null;
    }


    public Boolean removeContact(int id){
        boolean status =  dataEjb.removeContact(id);// true - all done; false - contact not exist
        return status;
    }

    //############################ TO UPDATE A CONTACT########################################
    public int updateContactOperator(int userID, int operatorNumID, OperatorBusiness op){
        TelephoneContact ct = dataEjb.getContact(userID);

        boolean status;
        if (ct != null ){
            status = dataEjb.updateOperator(operatorNumID, op.getOperatorName(), op.getNumber());
            if (status == false)
                return 2;
            return 0;
        }
        return 1;
    }
    public int updateContactAddress(int userID, AddressBusiness adr){
        TelephoneContact ct = dataEjb.getContact(userID);
        boolean status;
        if (ct != null){
            status = dataEjb.updateAddress(userID, adr.getZipCode(),adr.getStreetNumber(),adr.getStreet(),adr.getHouseNumber(),adr.getCity());
            if (status == false)
                return 2;
            return 0;
        }
        return 1;
    }
    public int updateContact(int userID, TelephoneContactBusiness usr){
        boolean status = dataEjb.updateContact(userID, usr.getDayBirth(), usr.getName());
        if (status == false)
            return 1;
        return 0;
    }

    //############################ TO CREATE A CONTACT########################################
    public int addContactOperator(int id, OperatorBusiness op){
        boolean status = dataEjb.createContactOperator(id, op.getOperatorName(), op.getNumber());
        if (status == true)
            return 0;
        return 1; // contact do not exist
    }
    public int addContactAddress(int userID, AddressBusiness adr){
        Address adress = dataEjb.getAddress(userID);
        boolean status;
        if (adress == null){
            status = dataEjb.createContactAddress(userID, adr.getCity(), adr.getHouseNumber(), adr.getStreet(),
                    adr.getStreetNumber(), adr.getZipCode());
            if (status == true)
                return 0;
            return 2; //user do not exist...
        }
        return 1; //address exists...
    }
    public int addContact(TelephoneContactBusiness contact){
        boolean status = dataEjb.createContact(contact.getDayBirth(), contact.getName());
        if (status == true)
            return 0;
        return 1;
    }

    @Override
    public String createCall(String test) {
        System.out.println("Business LAYER");
        return test+"_BUSINESS" + dataEjb.createCallData("PING2");
    }

}
