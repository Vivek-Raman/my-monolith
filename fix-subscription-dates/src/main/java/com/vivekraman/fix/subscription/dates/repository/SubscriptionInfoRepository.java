package com.vivekraman.fix.subscription.dates.repository;

import com.vivekraman.fix.subscription.dates.entity.SubscriptionInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionInfoRepository extends CrudRepository<SubscriptionInfo, String> {
  SubscriptionInfo findBySubscriptionId(String subscriptionId);
}
