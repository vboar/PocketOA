package top.kass.pocketoa.bean;

import java.io.Serializable;

public class FollowUpBean implements Serializable {

    private Integer followUpId;

    private Integer sourceId;

    private Integer SourceType;

    private Integer followUpType;

    private String createTime;

    private Integer creatorId;

    private String content;

    private String followUpRemarks;

    private Integer customerId;

    private CustomerBean customer;

    private StaffBean staff;

    public FollowUpBean() {}

    public Integer getFollowUpId() {
        return followUpId;
    }

    public void setFollowUpId(Integer followUpId) {
        this.followUpId = followUpId;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public Integer getSourceType() {
        return SourceType;
    }

    public void setSourceType(Integer sourceType) {
        SourceType = sourceType;
    }

    public Integer getFollowUpType() {
        return followUpType;
    }

    public void setFollowUpType(Integer followUpType) {
        this.followUpType = followUpType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFollowUpRemarks() {
        return followUpRemarks;
    }

    public void setFollowUpRemarks(String followUpRemarks) {
        this.followUpRemarks = followUpRemarks;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public CustomerBean getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerBean customer) {
        this.customer = customer;
    }

    public StaffBean getStaff() {
        return staff;
    }

    public void setStaff(StaffBean staff) {
        this.staff = staff;
    }

}
