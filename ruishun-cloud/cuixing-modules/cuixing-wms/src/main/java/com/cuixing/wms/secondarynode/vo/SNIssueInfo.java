package com.cuixing.wms.secondarynode.vo;

import com.cuixing.common.core.utils.DateUtils;

import java.util.Date;

public class SNIssueInfo extends SNBaseInfo {
   private String issueCode;
   private String issueName;
   private String issueDate;
   private String fromBillCode;
   private String type;
   private String itemNameGroup;
   private String itemCount;
   private String itemWeight;
   private String createByNick;
   private String warehouseName;

   public String getHandle() {
      return this.issueCode;
   }

   public String getIssueCode() {
      return this.issueCode;
   }

   public void setIssueCode(String issueCode) {
      this.issueCode = issueCode;
   }

   public String getIssueName() {
      return this.issueName;
   }

   public void setIssueName(String issueName) {
      this.issueName = issueName;
   }

   public String getIssueDate() {
      return this.issueDate;
   }

   public void setIssueDate(Date issueDate) {
      if (issueDate != null) {
         this.issueDate = DateUtils.dateTime(issueDate);
      }

   }

   public String getFromBillCode() {
      return this.fromBillCode;
   }

   public void setFromBillCode(String fromBillCode) {
      this.fromBillCode = fromBillCode;
   }

   public String getType() {
      return this.type;
   }

   public void setType(String type) {
      this.type = type;
   }

   public String getItemNameGroup() {
      return this.itemNameGroup;
   }

   public void setItemNameGroup(String itemNameGroup) {
      this.itemNameGroup = itemNameGroup;
   }

   public String getItemCount() {
      return this.itemCount;
   }

   public void setItemCount(String itemCount) {
      this.itemCount = itemCount;
   }

   public String getItemWeight() {
      return this.itemWeight;
   }

   public void setItemWeight(String itemWeight) {
      this.itemWeight = itemWeight;
   }

   public String getCreateByNick() {
      return this.createByNick;
   }

   public void setCreateByNick(String createByNick) {
      this.createByNick = createByNick;
   }

   public String getWarehouseName() {
      return this.warehouseName;
   }

   public void setWarehouseName(String warehouseName) {
      this.warehouseName = warehouseName;
   }
}
