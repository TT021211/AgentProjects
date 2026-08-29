package com.cuixing.wms.service;


import com.cuixing.common.core.web.domain.BaseEntity;
import com.cuixing.wms.utils.CallBack;
import com.cuixing.wms.utils.SecondaryNodeUtil;

import java.util.ArrayList;
import java.util.List;

public abstract class SecondaryNodeService<T extends BaseEntity> {
   public abstract void uploadSecondaryNode(T var1);

   public void deleteSecondaryNode(Long[] ids) {
      List handleList = new ArrayList();
      Long[] var3 = ids;
      int var4 = ids.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Long id = var3[var5];
         BaseEntity tmsWaybill = this.selectById(id);
         if (tmsWaybill != null) {
           // handleList.add(tmsWaybill.getSnidCode());
         }
      }

      SecondaryNodeUtil.delete(handleList, (CallBack)null);
   }

   public abstract T selectById(Long var1);
}
