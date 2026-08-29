package com.cuixing.wms.secondarynode.vo;

import com.cuixing.common.core.utils.DateUtils;

import java.util.Date;

public class SNItemRecptInfo extends SNBaseInfo {
   private String recptCode;
   private String recptName;
   private String recptDate;
   private String fromBillCode;
   private String type;
   private String itemNameGroup;
   private String itemCount;
   private String itemWeight;
   private String createByNick;
   private String warehouseName;

   public String getHandle() {
      return this.recptCode;
   }

   public String getRecptCode() {
      return this.recptCode;
   }

   public void setRecptCode(String recptCode) {
      this.recptCode = recptCode;
   }

   public String getRecptName() {
      return this.recptName;
   }

   public void setRecptName(String recptName) {
      this.recptName = recptName;
   }

   public String getRecptDate() {
      return this.recptDate;
   }

   public void setRecptDate(Date recptDate) {
      if (recptDate != null) {
         this.recptDate = DateUtils.dateTime(recptDate);
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
