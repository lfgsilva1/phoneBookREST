package data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
public class OperatorBusiness implements Serializable {
    @XmlAttribute
    private int operatorId;
    private String operatorName;
    private String number;

    private TelephoneContactBusiness user;

    public OperatorBusiness(){
        super();
    }
    public OperatorBusiness(Operator op){
        super();
        this.operatorId = op.getOperatorId();
        this.number = op.getNumber();
        this.operatorName = op.getOperatorName();
        //this.user = null;
    }
    public OperatorBusiness(String operatorName, String number, TelephoneContactBusiness telephoneContact){
        super();
        this.user = telephoneContact;
        this.operatorName = operatorName;
        this.number = number;
    }

    public int getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(int operatorId) {
        this.operatorId = operatorId;
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

    public TelephoneContactBusiness getUser() {
        return user;
    }

    public void setUser(TelephoneContactBusiness user) {
        this.user = user;
    }
}
