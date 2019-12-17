package data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
public class AddressBusiness implements Serializable {
    @XmlAttribute
    private int addressId;

    private int houseNumber;
    private String street;
    private int streetNumber;
    private String zipCode;
    private String city;

    private TelephoneContactBusiness user;

    public AddressBusiness()
    {
        super();
    }

    public AddressBusiness(Address adr){
        this.addressId = adr.getAddressId();
        this.city = adr.getCity();
        this.houseNumber = adr.getHouseNumber();
        this.street = adr.getStreet();
        this.streetNumber = adr.getStreetNumber();
        this.zipCode = adr.getZipCode();
        //this.user = adr.getUser();
    }
    public AddressBusiness(String city, int houseNumber, String street, int streetNumber, String zipCode, TelephoneContactBusiness user){
        this.user = user;
        this.zipCode = zipCode;
        this.streetNumber = streetNumber;
        this.street = street;
        this.houseNumber = houseNumber;
        this.city = city;
    }


    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
