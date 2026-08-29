package com.cuixing.mold.utils;

import com.cuixing.mold.entity.MoldScheduledTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA 数据库操作
 */
@Repository
public interface ScheduledTaskRepository extends JpaRepository<MoldScheduledTask, String> {
}
