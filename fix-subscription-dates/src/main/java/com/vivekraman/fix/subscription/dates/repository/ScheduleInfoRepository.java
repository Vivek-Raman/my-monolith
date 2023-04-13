package com.vivekraman.fix.subscription.dates.repository;

import com.vivekraman.fix.subscription.dates.entity.ScheduleInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleInfoRepository extends CrudRepository<ScheduleInfo, String> {
  @Query("SELECT s FROM schedule_info s ORDER BY s.subscriptionId ASC, s.orderProcessingDate ASC")
  List<ScheduleInfo> findAll();
}
