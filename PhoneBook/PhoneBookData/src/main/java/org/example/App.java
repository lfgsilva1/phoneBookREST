package org.example;

import data.Address;
import data.Operator;
import data.PhoneBook;
import data.TelephoneContact;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

/**
 * Hello world!
 *
 */
public class App 
{
    public static Date getDate(int day, int month, int year){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month-1);
        cal.set(Calendar.DAY_OF_MONTH, day);

        Date d = cal.getTime();
        return d;
    }
    public static void main( String[] args )
    {
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

        //contact1.setPhonebook(phonebook);
        //contact2.setPhonebook(phonebook);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhoneBook");
        EntityManager em = emf.createEntityManager();
        EntityTransaction trx = em.getTransaction();

        trx.begin();
        em.persist(phonebook);
        em.persist(contact1);
        em.persist(contact2);
        em.persist(homelocation1);
        em.persist(homelocation2);
        for(Operator o : numbers)
            em.persist(o);
        trx.commit();

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


        //System.out.println( "Hello World!" );
    }
}
