package top.kass.pocketoa.bean;

import java.io.Serializable;

public class ContractBean implements Serializable {

    private Integer contractId;

    private String contractTitle;

    private Integer opportunityId;

    private Integer customerId;

    private Double totalAmount;

    private String startDate;

    private String endDate;

    private Integer contractStatus;

    private String contractNumber;

    private Integer contractType;

    private String payMethod;

    private String clientContractor;

    private String ourContractor;

    private Integer staffId;

    private String signingDate;

    private String attachment;

    private String contractRemarks;

    private CustomerBean customer;

    private OpportunityBean opportunityBean;

    public ContractBean() {}

    public Integer getContractId() {
        return contractId;
    }

    public void setContractId(Integer contractId) {
        this.contractId = contractId;
    }

    public String getContractTitle() {
        return contractTitle;
    }

    public void setContractTitle(String contractTitle) {
        this.contractTitle = contractTitle;
    }

    public Integer getOpportunityId() {
        return opportunityId;
    }

    public void setOpportunityId(Integer opportunityId) {
        this.opportunityId = opportunityId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(Integer contractStatus) {
        this.contractStatus = contractStatus;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public Integer getContractType() {
        return contractType;
    }

    public void setContractType(Integer contractType) {
        this.contractType = contractType;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getClientContractor() {
        return clientContractor;
    }

    public void setClientContractor(String clientContractor) {
        this.clientContractor = clientContractor;
    }

    public String getOurContractor() {
        return ourContractor;
    }

    public void setOurContractor(String ourContractor) {
        this.ourContractor = ourContractor;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public String getSigningDate() {
        return signingDate;
    }

    public void setSigningDate(String signingDate) {
        this.signingDate = signingDate;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getContractRemarks() {
        return contractRemarks;
    }

    public void setContractRemarks(String contractRemarks) {
        this.contractRemarks = contractRemarks;
    }

    public CustomerBean getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerBean customer) {
        this.customer = customer;
    }

    public OpportunityBean getOpportunityBean() {
        return opportunityBean;
    }

    public void setOpportunityBean(OpportunityBean opportunityBean) {
        this.opportunityBean = opportunityBean;
    }

}
