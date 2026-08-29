package com.cuixing.wms.secondarynode.vo;


import com.cuixing.common.core.utils.DateUtils;

import java.math.BigDecimal;
import java.util.Date;

public class SNBoxInfo extends SNBaseInfo {
   private String containerCode;
   private String boxSerialCode;
   private String batchCode;
   private String quantity;
   private String weightJy;
   private String itemName;
   private String itemCode;
   private String itemCode2;
   private String specification;
   private String totalWeight;
   private String recptCode;
   private String createTime;

   public String getCreateTime() {
      return this.createTime;
   }

   public void setCreateTime(Date createTime) {
      if (createTime != null) {
         this.createTime = DateUtils.dateTime(createTime);
      }

   }

   public String getHandle() {
      return this.containerCode;
   }

   public String getContainerCode() {
      return this.containerCode;
   }

   public void setContainerCode(String containerCode) {
      this.containerCode = containerCode;
   }

   public String getBoxSerialCode() {
      return this.boxSerialCode;
   }

   public void setBoxSerialCode(String boxSerialCode) {
      this.boxSerialCode = boxSerialCode;
   }

   public String getBatchCode() {
      return this.batchCode;
   }

   public void setBatchCode(String batchCode) {
      this.batchCode = batchCode;
   }

   public String getQuantity() {
      return this.quantity;
   }

   public void setQuantity(String quantity) {
      this.quantity = quantity;
   }

   public String getWeightJy() {
      return this.weightJy;
   }

   public void setWeightJy(String weightJy) {
      this.weightJy = weightJy;
   }

   public String getItemName() {
      return this.itemName;
   }

   public void setItemName(String itemName) {
      this.itemName = itemName;
   }

   public String getItemCode() {
      return this.itemCode;
   }

   public void setItemCode(String itemCode) {
      this.itemCode = itemCode;
   }

   public String getSpecification() {
      return this.specification;
   }

   public void setSpecification(String specification) {
      this.specification = specification;
   }

   public String getTotalWeight() {
      return this.totalWeight;
   }

   public void setTotalWeight(BigDecimal totalWeight) {
      this.totalWeight = totalWeight == null ? "0" : totalWeight.toString();
   }

   public String getRecptCode() {
      return this.recptCode;
   }

   public void setRecptCode(String recptCode) {
      this.recptCode = recptCode;
   }

   public String getItemCode2() {
      return this.itemCode2;
   }

   public void setItemCode2(String itemCode2) {
      this.itemCode2 = itemCode2;
   }
}
