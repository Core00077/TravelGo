package cn.corechan.travel.vo;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private String orderId;
    private String goodId;
    private String phoneNumber;
    private String ordertime;
    private String gotime;
    private int people=0;
    private String note;
    private int status = 0;//-1为取消订单；0为未支付；1为已支付未出行；2为过了出行时间已出行待评价；3为已评价
    private boolean customCancel = false;
    private boolean sellerCancel = false;
    private List<Contact> userContacts=new ArrayList<>();

    public Order(String goodId, String phoneNumber, String gotime, int people, String note, List<Contact> userContacts) {
        this.goodId = goodId;
        this.phoneNumber = phoneNumber;
        this.gotime = gotime;
        this.people = people;
        this.note = note;
        this.ordertime=String.valueOf(System.currentTimeMillis());
        this.orderId=phoneNumber.substring(7)+this.ordertime.substring(0,11);
        this.status=0;
        this.customCancel=false;
        this.sellerCancel=false;
        this.userContacts=userContacts;
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

    public List<Contact> getUserContacts() {
        return userContacts;
    }
}
