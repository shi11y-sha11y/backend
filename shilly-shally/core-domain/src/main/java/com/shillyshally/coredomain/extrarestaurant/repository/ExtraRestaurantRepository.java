package com.shillyshally.coredomain.extrarestaurant.repository;

import com.shillyshally.coredomain.extrarestaurant.ExtraRestaurant;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExtraRestaurantRepository extends JpaRepository<ExtraRestaurant, Long> {

    Slice<ExtraRestaurant> findAllByOrderByIdDesc(Pageable pageable);

    @Query(value = "SELECT * FROM EXTRA_RESTAURANT WHERE APPROVAL_STATUS = 'UNCHECKED' ORDER BY ID DESC", nativeQuery = true)
    Slice<ExtraRestaurant> findAllApprovalStatusUncheckedOrderByIdDesc(Pageable pageable);

    @Query(value = "UPDATE ExtraRestaurant e SET e.approvalStatus = 'APPROVED' WHERE e.id in :ids")
    void updateStatusApprovedByIds(List<Long> ids);
}
