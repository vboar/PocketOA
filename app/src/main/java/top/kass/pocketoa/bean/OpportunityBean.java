package top.kass.pocketoa.bean;

import java.io.Serializable;

public class OpportunityBean implements Serializable {

    private Integer opportunityId;

    private String opportunityTitle;

    private Integer customerId;

    private Double estimatedAmount;

    private Integer successRate;

    private String expectedDate;

    private Integer opportunityStatus;

    private String channel;

    private Integer businessType;

    private String acquisitionDate;

    private String opportunitiesSource;

    private Integer staffId;

    private String opportunityRemarks;

    private CustomerBean customer;

    public OpportunityBean() {}

    public Integer getOpportunityId() {
        return opportunityId;
    }

    public void setOpportunityId(Integer opportunityId) {
        this.opportunityId = opportunityId;
    }

    public String getOpportunityTitle() {
        return opportunityTitle;
    }

    public void setOpportunityTitle(String opportunityTitle) {
        this.opportunityTitle = opportunityTitle;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Double getEstimatedAmount() {
        return estimatedAmount;
    }

    public void setEstimatedAmount(Double estimatedAmount) {
        this.estimatedAmount = estimatedAmount;
    }

    public Integer getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(Integer successRate) {
        this.successRate = successRate;
    }

    public String getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(String expectedDate) {
        this.expectedDate = expectedDate;
    }

    public Integer getOpportunityStatus() {
        return opportunityStatus;
    }

    public void setOpportunityStatus(Integer opportunityStatus) {
        this.opportunityStatus = opportunityStatus;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public String getAcquisitionDate() {
        return acquisitionDate;
    }

    public void setAcquisitionDate(String acquisitionDate) {
        this.acquisitionDate = acquisitionDate;
    }

    public String getOpportunitiesSource() {
        return opportunitiesSource;
    }

    public void setOpportunitiesSource(String opportunitiesSource) {
        this.opportunitiesSource = opportunitiesSource;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public String getOpportunityRemarks() {
        return opportunityRemarks;
    }

    public void setOpportunityRemarks(String opportunityRemarks) {
        this.opportunityRemarks = opportunityRemarks;
    }

    public CustomerBean getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerBean customer) {
        this.customer = customer;
    }

}
