package top.kass.pocketoa.bean;

import java.io.Serializable;

public class ContactBean implements Serializable {

    private Integer contactsId;

    private Integer customerId;

    private String contactsName;

    private Integer contactsAge;

    private String contactsGender;

    private String contactsMobile;

    private String contactsTelephone;

    private String contactsEmail;

    private String contactsAddress;

    private String contactsZipcode;

    private String contactsQq;

    private String contactsWechat;

    private String contactsWangwang;

    private String contactsDeptName;

    private Integer contactsPosition;

    private String contactsRemarks;

    private CustomerBean customer;

    public ContactBean() {}

    public Integer getContactsId() {
        return contactsId;
    }

    public void setContactsId(Integer contactsId) {
        this.contactsId = contactsId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getContactsName() {
        return contactsName;
    }

    public void setContactsName(String contactsName) {
        this.contactsName = contactsName;
    }

    public Integer getContactsAge() {
        return contactsAge;
    }

    public void setContactsAge(Integer contactsAge) {
        this.contactsAge = contactsAge;
    }

    public String getContactsGender() {
        return contactsGender;
    }

    public void setContactsGender(String contactsGender) {
        this.contactsGender = contactsGender;
    }

    public String getContactsMobile() {
        return contactsMobile;
    }

    public void setContactsMobile(String contactsMobile) {
        this.contactsMobile = contactsMobile;
    }

    public String getContactsTelephone() {
        return contactsTelephone;
    }

    public void setContactsTelephone(String contactsTelephone) {
        this.contactsTelephone = contactsTelephone;
    }

    public String getContactsEmail() {
        return contactsEmail;
    }

    public void setContactsEmail(String contactsEmail) {
        this.contactsEmail = contactsEmail;
    }

    public String getContactsAddress() {
        return contactsAddress;
    }

    public void setContactsAddress(String contactsAddress) {
        this.contactsAddress = contactsAddress;
    }

    public String getContactsZipcode() {
        return contactsZipcode;
    }

    public void setContactsZipcode(String contactsZipcode) {
        this.contactsZipcode = contactsZipcode;
    }

    public String getContactsQq() {
        return contactsQq;
    }

    public void setContactsQq(String contactsQq) {
        this.contactsQq = contactsQq;
    }

    public String getContactsWechat() {
        return contactsWechat;
    }

    public void setContactsWechat(String contactsWechat) {
        this.contactsWechat = contactsWechat;
    }

    public String getContactsWangwang() {
        return contactsWangwang;
    }

    public void setContactsWangwang(String contactsWangwang) {
        this.contactsWangwang = contactsWangwang;
    }

    public String getContactsDeptName() {
        return contactsDeptName;
    }

    public void setContactsDeptName(String contactsDeptName) {
        this.contactsDeptName = contactsDeptName;
    }

    public Integer getContactsPosition() {
        return contactsPosition;
    }

    public void setContactsPosition(Integer contactsPosition) {
        this.contactsPosition = contactsPosition;
    }

    public String getContactsRemarks() {
        return contactsRemarks;
    }

    public void setContactsRemarks(String contactsRemarks) {
        this.contactsRemarks = contactsRemarks;
    }

    public CustomerBean getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerBean customer) {
        this.customer = customer;
    }

}
