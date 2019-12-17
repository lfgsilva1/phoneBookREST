package ejb;

import data.Address;
import data.Operator;
import data.PhoneBook;
import data.TelephoneContact;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Stateless
public class DataBean implements DataBeanInterface{
    @PersistenceContext(name = "PhoneBook")
    private EntityManager em;


    @Override
    public String createCallData(String test) {
        System.out.println("DATA LAYER!!");
        return test+"_DATA";
    }



    @Override
    public TelephoneContact getContact(int userID){
        Query q = em.createQuery("from TelephoneContact t where t.id = :userID");
        q.setParameter("userID", userID);
        TelephoneContact contact = null;
        List results = q.getResultList();
        //http://sysout.be/2011/03/09/why-you-should-never-use-getsingleresult-in-jpa/
        if (!results.isEmpty()){// isEmpty(): true if the list contains no elements, false otherwise
            contact = (TelephoneContact) results.get(0);
        }
        return contact;
    }

    @Override
    public Address getAddress(int userID) {
        Query q = em.createQuery("from Address a where a.user.id = :userID");
        q.setParameter("userID", userID);
        Address adr = null;
        List results = q.getResultList();
        if (!results.isEmpty()){
            adr = (Address) results.get(0);
        }
        return adr;
    }

    @Override
    public List<Operator> getOperatorsByUser(int userID) {
        Query q = em.createQuery("from Operator o where o.user.id = :userID");
        q.setParameter("userID", userID);
        List<Operator> OpNumbers = q.getResultList();
        return OpNumbers;
    }

    @Override
    public boolean updateOperator(int operatorNumID, String nameOperator, String number){
        //Query q = em.createQuery("from Operator o where o.user.id = :userID");
        Query q = em.createQuery("from Operator o where o.operatorId = :id");
        q.setParameter("id", operatorNumID);
        List results = q.getResultList();
        Operator o = null;
        if (!results.isEmpty()){// isEmpty(): true if the list contains no elements, false otherwise
            o = (Operator) results.get(0);
            o.setNumber(number);
            o.setOperatorName(nameOperator);

            em.persist(o);
            return true;
        }//is empty
        return false;
    }
    @Override
    public boolean updateAddress(int userID, String zipCode, int streetNumber, String street, int houseNumber, String city){
        Query q = em.createQuery("from Address a where a.user.id = :userID");
        q.setParameter("userID", userID);
        List results = q.getResultList();
        Address adr = null;
        if (!results.isEmpty()){
            adr = (Address) results.get(0);
            adr.setZipCode(zipCode);
            adr.setStreetNumber(streetNumber);
            adr.setStreet(street);
            adr.setHouseNumber(houseNumber);
            adr.setCity(city);

            em.persist(adr);
            return true;
        }
        return false;
    }
    @Override
    public boolean updateContact(int userID, Date birthDay, String name){
        TelephoneContact contact = getContact(userID);
        if (contact != null){
            contact.setDayBirth(birthDay);
            contact.setName(name);

            em.persist(contact);
            return true;
        }
        return false;
    }
    @Override
    public boolean createContactOperator(int userID, String operatorName, String number){
        TelephoneContact contact = getContact(userID);
        Operator op = null;
        if (contact != null){
            op = new Operator(operatorName, number, contact);

            em.persist(op);
            return true;
        }
        return false;
    }
    @Override
    public boolean createContactAddress(int userID, String city, int houseNumber, String street, int streetNumber, String ZipCode){
        TelephoneContact contact = getContact(userID);
        Address adr = null;
        if (contact != null){
            adr = new Address(city, houseNumber, street, streetNumber, ZipCode, contact);
            em.persist(adr);
            return true;
        }
        return false;
    }
    @Override
    public boolean createContact(Date birthDay, String name){
        TelephoneContact contact = new TelephoneContact();
        contact.setName(name);
        contact.setDayBirth(birthDay);

        em.persist(contact);
        return true;
    }


    @Override
    public boolean removeContact(int userID) {
        TelephoneContact contact = getContact(userID);
        if (contact !=null){
            em.remove(contact);
            return true;
        }
        return false; //contact do not exist
    }

    @Override
    public List<Address> getAddresses(){
        Query q = em.createQuery("from Address ");
        List<Address> adr = q.getResultList();
        return adr;
    }
    @Override
    public List<TelephoneContact> getContacts() {
        List<TelephoneContact> contacts = getTelephoneContacts();
        if (!contacts.isEmpty())
            return contacts;
        else{
            //IF the DB is empty we introduce new data
            //System.out.println("DB IS EMPTY!!");
            populate();
            return getTelephoneContacts();
        }
    }
    private List<TelephoneContact> getTelephoneContacts() {
        Query q = em.createQuery("from TelephoneContact");
        List<TelephoneContact> c = q.getResultList();
        return c;
    }
    @Override
    public List<Operator> getOperators() {
        Query q = em.createQuery("from Operator ");
        List<Operator> o = q.getResultList();
        return o;
    }

    private void populate(){
        //first i will create the PhoneBook that is inserted into telephone contacts
        PhoneBook phonebook = new PhoneBook();

        //then i will create contacts... and insert them into the phonebook
        //(String name, Date dayBirth, Set<Operator> numbers, Address homeLocation, PhoneBook phonebook)
        TelephoneContact contact1 = new TelephoneContact("Luis", getDate(11,1,1990), null, null, phonebook);
        //(String city, int houseNumber, String street, int streetNumber, String zipCode, TelephoneContact user)
        Address homelocation1 = new Address("LISBON", 54, "Street XYZ", 42, "9220-225", contact1);
        TelephoneContact contact2 = new TelephoneContact("Filipe", getDate(1,1,1988), null, null, phonebook);
        Address homelocation2 = new Address("COIMBRA", 54, "Street XYZ", 42, "9220-225", contact2);

        Operator[] numbers = {
                //String (operatorName, String number, TelephoneContact telephoneContact)
                new Operator("MEO", "351967491511", contact1),
                new Operator("VODAFONE", "351917491511", contact1),
                new Operator("ZON", "351937491511", contact1),

                new Operator("MEO", "351967491512", contact2),
                new Operator("MEO", "351967491513", contact2),
                new Operator("MEO", "351967491514", contact2),
                new Operator("ZON", "351937491512", contact2),
        };

        em.persist(phonebook);
        em.persist(contact1);
        em.persist(contact2);
        em.persist(homelocation1);
        em.persist(homelocation2);
        for(Operator o : numbers)
            em.persist(o);


        //lets remove a address
        /*Query q = em.createQuery("from Address a where a.city = :t");
        q.setParameter("t", "COIMBRA");
        Address a = (Address) q.getSingleResult();
        System.out.println(a.getAddressId()+" WITH: "+a);

        trx.begin();
        em.remove(a);
        trx.commit();*/

        //lets remove a contact
        /*Query q = em.createQuery("from TelephoneContact c where c.name = :t");
        q.setParameter("t", "Filipe");

        TelephoneContact contact = (TelephoneContact) q.getSingleResult();
        System.out.println(contact.getTelephoneContactId()+" _WITH:_ "+contact);
        trx.begin();
        em.remove(contact);
        trx.commit();*/
    }
    private Date getDate(int day, int month, int year){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month-1);
        cal.set(Calendar.DAY_OF_MONTH, day);

        Date d = cal.getTime();
        return d;
    }
}
