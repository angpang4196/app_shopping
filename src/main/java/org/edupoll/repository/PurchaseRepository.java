package org.edupoll.repository;

import java.util.List;

import org.edupoll.model.entity.Purchase;
import org.edupoll.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
	List<Purchase> findByUser(User user);
}