package com.cuixing.md;

import com.cuixing.common.core.web.page.TableDataInfo;
import com.cuixing.system.api.domain.SysUser;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@SpringBootTest
public class mdWorkgroupTest {
	@Test
	@GetMapping("/{userIds}")
	public TableDataInfo unallocatedList(@PathVariable Long[] userIds, SysUser user) {

		System.out.println(userIds.toString());
		System.out.println(user);
		// List<SysUser> userList = mdWorkgroupService.selectUnallocatedList(userIds, user);
		return null;
	}

}
