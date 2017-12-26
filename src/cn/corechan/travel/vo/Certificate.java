package cn.corechan.travel.vo;

public class Certificate {
    private String phoneNumber;
    private String ID;
    private String realname;
    private String address;
    private String IDpicURL;
    private int status = 0;
    private String msg;

    public Certificate(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Certificate(String phoneNumber, String ID, String realname, String address, String IDpicURL, int status, String msg) {
        this.phoneNumber = phoneNumber;
        this.ID = ID;
        this.realname = realname;
        this.address = address;
        this.IDpicURL = IDpicURL;
        this.status = status;
        this.msg = msg;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIDpicURL() {
        return IDpicURL;
    }

    public void setIDpicURL(String IDpicURL) {
        this.IDpicURL = IDpicURL;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
