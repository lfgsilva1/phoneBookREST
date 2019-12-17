
package data;

import javax.persistence.*;
import java.io.Serializable;


@Entity
public class Address implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int addressId;

    private int houseNumber;
    private String street;
    private int streetNumber;
    private String zipCode;
    private String city;

    @OneToOne @MapsId
    private TelephoneContact user;

    public Address(){
        this.city = null;
        this.houseNumber = 0;
        this.street = null;
        this.streetNumber = 0;
        this.zipCode = null;
        this.user = null;
    }
    public Address(String city, int houseNumber, String street, int streetNumber, String zipCode, TelephoneContact user){
        this.user = user;
        this.zipCode = zipCode;
        this.streetNumber = streetNumber;
        this.street = street;
        this.houseNumber = houseNumber;
        this.city = city;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public int getAddressId() {
        return addressId;
    }

    public TelephoneContact getUser() {
        return user;
    }

    public void setUser(TelephoneContact user) {
        this.user = user;
    }

    public String toString(){
        return this.user.getName()+" lives on city "+this.city+
                " at street "+this.street+" with number "+this.streetNumber+" at house number "+this.houseNumber;
    }
}
