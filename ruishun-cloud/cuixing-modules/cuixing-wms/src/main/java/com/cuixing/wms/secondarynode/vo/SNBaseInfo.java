package com.cuixing.wms.secondarynode.vo;

public abstract class SNBaseInfo {
   protected String handle;

   public abstract String getHandle();

   public void setHandle(String handle) {
      this.handle = handle;
   }
}
