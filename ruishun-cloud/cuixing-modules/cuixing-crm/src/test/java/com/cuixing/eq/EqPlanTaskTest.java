package com.cuixing.eq;

import com.cuixing.common.core.constant.SecurityConstants;
import com.cuixing.common.core.domain.R;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.system.api.RemoteMesService;
import com.cuixing.system.api.domain.mes.MesOuputLog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EqPlanTaskTest {

    @Autowired
    private RemoteMesService remoteMesService;

    @Test
    public void test(){

        MesOuputLog ls001 = remoteMesService.getSpecification("LS001", SecurityConstants.INNER).getData();
        System.out.println(ls001.getItemSpecification());
    }
}
