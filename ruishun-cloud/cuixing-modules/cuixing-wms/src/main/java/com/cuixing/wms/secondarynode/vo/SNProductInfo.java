package com.cuixing.wms.secondarynode.vo;

import com.cuixing.common.core.utils.DateUtils;

import java.math.BigDecimal;
import java.util.Date;

public class SNProductInfo extends SNBaseInfo {
   private String itemCode;
   private String itemName;
   private String specification;
   private String batchCode;
   private String containerCode;
   private String boxSerialCode;
   private String weight;
   private String totalWeight;
   private String otherWeight;
   private String createTime;
   private String weightJy;
   private String boxSerialChildCode;
   private String recptCode;
   private String recptAreaName;
   private String recptDate;
   private String recptCreateBy;
   private String issueCode;
   private String issueAreaName;
   private String issueDate;
   private String issueCreateBy;
   private String itemCode2;

   public String getHandle() {
      return this.boxSerialChildCode;
   }

   public String getItemCode() {
      return this.itemCode;
   }

   public String getItemName() {
      return this.itemName;
   }

   public String getSpecification() {
      return this.specification;
   }

   public String getBatchCode() {
      return this.batchCode;
   }

   public String getContainerCode() {
      return this.containerCode;
   }

   public String getBoxSerialCode() {
      return this.boxSerialCode;
   }

   public String getWeight() {
      return this.weight;
   }

   public String getCreateTime() {
      return this.createTime;
   }

   public String getWeightJy() {
      return this.weightJy;
   }

   public String getBoxSerialChildCode() {
      return this.boxSerialChildCode;
   }

   public String getRecptCode() {
      return this.recptCode;
   }

   public String getRecptAreaName() {
      return this.recptAreaName;
   }

   public String getRecptDate() {
      return this.recptDate;
   }

   public String getRecptCreateBy() {
      return this.recptCreateBy;
   }

   public String getIssueCode() {
      return this.issueCode;
   }

   public String getIssueAreaName() {
      return this.issueAreaName;
   }

   public String getIssueDate() {
      return this.issueDate;
   }

   public String getIssueCreateBy() {
      return this.issueCreateBy;
   }

   public void setItemCode(String itemCode) {
      this.itemCode = itemCode;
   }

   public void setItemName(String itemName) {
      this.itemName = itemName;
   }

   public void setSpecification(String specification) {
      this.specification = specification;
   }

   public void setBatchCode(String batchCode) {
      this.batchCode = batchCode;
   }

   public void setContainerCode(String containerCode) {
      this.containerCode = containerCode;
   }

   public void setBoxSerialCode(String boxSerialCode) {
      this.boxSerialCode = boxSerialCode;
   }

   public void setWeight(BigDecimal weight) {
      this.weight = weight == null ? "0" : weight.toString();
   }

   public void setCreateTime(Date createTime) {
      if (createTime != null) {
         this.createTime = DateUtils.dateTime(createTime);
      }

   }

   public void setWeightJy(String weightJy) {
      this.weightJy = weightJy;
   }

   public void setBoxSerialChildCode(String boxSerialChildCode) {
      this.boxSerialChildCode = boxSerialChildCode;
   }

   public void setRecptCode(String recptCode) {
      this.recptCode = recptCode;
   }

   public void setRecptAreaName(String recptAreaName) {
      this.recptAreaName = recptAreaName;
   }

   public void setRecptDate(Date recptDate) {
      if (recptDate != null) {
         this.recptDate = DateUtils.dateTime(recptDate);
      }

   }

   public void setRecptCreateBy(String recptCreateBy) {
      this.recptCreateBy = recptCreateBy;
   }

   public void setIssueCode(String issueCode) {
      this.issueCode = issueCode;
   }

   public void setIssueAreaName(String issueAreaName) {
      this.issueAreaName = issueAreaName;
   }

   public void setIssueDate(Date issueDate) {
      if (issueDate != null) {
         this.issueDate = DateUtils.dateTime(issueDate);
      }

   }

   public void setIssueCreateBy(String issueCreateBy) {
      this.issueCreateBy = issueCreateBy;
   }

   public String getTotalWeight() {
      return this.totalWeight;
   }

   public void setTotalWeight(BigDecimal totalWeight) {
      this.totalWeight = totalWeight == null ? "0" : totalWeight.toString();
   }

   public String getOtherWeight() {
      return this.otherWeight;
   }

   public void setOtherWeight(String otherWeight) {
      this.otherWeight = otherWeight;
   }

   public void setWeight(String weight) {
      this.weight = weight;
   }

   public String getItemCode2() {
      return this.itemCode2;
   }

   public void setItemCode2(String itemCode2) {
      this.itemCode2 = itemCode2;
   }
}
