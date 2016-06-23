package top.kass.pocketoa.bean;

import java.io.Serializable;

import top.kass.pocketoa.R;

public class CustomerBean implements Serializable {

    private Integer customerId;

    private String customerName;

    private String profile;

    private Integer customerType;

    private Integer customerStatus;

    private Integer regionId;

    private Integer parentCustomerId;

    private String customerSource;

    private Integer size;

    private String telephone;

    private String email;

    private String website;

    private String address;

    private String zipcode;

    private Integer staffId;

    private String createDate;

    private String customerRemarks;

    private StaffBean staff;

    public CustomerBean() {};

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public Integer getCustomerType() {
        return customerType;
    }

    public void setCustomerType(Integer customerType) {
        this.customerType = customerType;
    }

    public Integer getCustomerStatus() {
        return customerStatus;
    }

    public void setCustomerStatus(Integer customerStatus) {
        this.customerStatus = customerStatus;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public Integer getParentCustomerId() {
        return parentCustomerId;
    }

    public void setParentCustomerId(Integer parentCustomerId) {
        this.parentCustomerId = parentCustomerId;
    }

    public String getCustomerSource() {
        return customerSource;
    }

    public void setCustomerSource(String customerSource) {
        this.customerSource = customerSource;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCustomerRemarks() {
        return customerRemarks;
    }

    public void setCustomerRemarks(String customerRemarks) {
        this.customerRemarks = customerRemarks;
    }

    public StaffBean getStaff() {
        return staff;
    }

    public void setStaff(StaffBean staff) {
        this.staff = staff;
    }

    public static String getTypeString(Integer type) {
        if (type == null) return "";
        switch (type) {
            case 1: return "重要客户";
            case 2: return "一般客户";
            case 3: return "低价值客户";
        }
        return "";
    }

    public static String getStatusString(Integer status) {
        if (status == null) return "";
        switch (status) {
            case 1: return "初访";
            case 2: return "意向";
            case 3: return "报价";
            case 4: return "成交";
            case 5: return "暂时搁置";
        }
        return "";
    }

}
