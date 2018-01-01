package cn.corechan.travel.vo;

import java.util.HashMap;
import java.util.List;

public class Order {
    private String orderId;
    private String goodId;
    private String phoneNumber;
    private String ordertime;
    private String gotime;
    private int people;
    private String note;
    private int status;//-1为取消订单；0为未支付；1为已支付未出行；2为过了出行时间已出行待评价；3为已评价
    private boolean customCancel;
    private boolean sellerCancel;
    private List<Contact> contacts;
    private HashMap<String,String> seller;
    private HashMap<String,String> good;

    //创建订单构造方法
    public Order(String goodId, String phoneNumber, String gotime, int people, String note, List<Contact> contacts) {
        this.goodId = goodId;
        this.phoneNumber = phoneNumber;
        this.gotime = gotime;
        this.people = people;
        this.note = note;
        this.ordertime = String.valueOf(System.currentTimeMillis());
        this.orderId = phoneNumber.substring(5) + this.ordertime.substring(0, 12);
        this.status = 0;
        this.customCancel = false;
        this.sellerCancel = false;
        this.contacts = contacts;
    }
    //获得订单构造方法

    public Order(String orderId, String goodId, String phoneNumber, String ordertime, String gotime, int people, String note, int status, boolean customCancel, boolean sellerCancel, List<Contact> contacts, HashMap<String, String> seller, HashMap<String, String> good) {
        this.orderId = orderId;
        this.goodId = goodId;
        this.phoneNumber = phoneNumber;
        this.ordertime = ordertime;
        this.gotime = gotime;
        this.people = people;
        this.note = note;
        this.status = status;
        this.customCancel = customCancel;
        this.sellerCancel = sellerCancel;
        this.contacts = contacts;
        this.seller=seller;
        this.good=good;
    }

    public void setGotime(String gotime) {
        this.gotime = gotime;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setCustomCancel(boolean customCancel) {
        this.customCancel = customCancel;
    }

    public void setSellerCancel(boolean sellerCancel) {
        this.sellerCancel = sellerCancel;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getGoodId() {
        return goodId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getOrdertime() {
        return ordertime;
    }

    public String getGotime() {
        return gotime;
    }

    public int getPeople() {
        return people;
    }

    public String getNote() {
        return note;
    }

    public int getStatus() {
        return status;
    }

    public boolean isCustomCancel() {
        return customCancel;
    }

    public boolean isSellerCancel() {
        return sellerCancel;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public HashMap<String, String> getSeller() {
        return seller;
    }

    public HashMap<String, String> getGood() {
        return good;
    }
}
