package com.jiinfotech.restomanager.db.billing;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingRepo extends JpaRepository<Billing, Long> {

}
