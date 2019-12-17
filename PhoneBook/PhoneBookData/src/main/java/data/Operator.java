
package data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Operator implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int operatorId;
    private String operatorName;
    private String number;

    @ManyToOne
    private TelephoneContact user;

    public Operator(){
        super();
        this.number = null;
        this.operatorName = null;
        this.user = null;
    }
    public Operator(String operatorName, String number, TelephoneContact telephoneContact){
        super();
        this.user = telephoneContact;
        this.operatorName = operatorName;
        this.number = number;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getOperatorId() {
        return operatorId;
    }

    public TelephoneContact getTelephonecontact() {
        return user;
    }

    public void setTelephonecontact(TelephoneContact telephonecontact) {
        this.user = telephonecontact;
    }

    @Override
    public String toString(){
        return this.operatorName + " ID = " +this.operatorId +
                "with number = "+this.number + " belongs to this contact: "+this.user.getName();
    }
}
