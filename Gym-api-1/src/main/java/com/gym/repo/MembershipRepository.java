package com.gym.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gym.entity.Membership;

public interface MembershipRepository extends JpaRepository<Membership, Integer>{
	
	List<Membership> findByMemberIdAndIsActive(Integer memberId, boolean isActive);

	@Query("SELECT m FROM Membership m WHERE :active IS NULL OR m.isActive = :active")
    List<Membership> findByActiveStatus(@Param("active") Boolean active);

	@Query("SELECT m from Membership m WHERE m.member.id = :memberId")
	List<Membership> findByMemberId(@Param("memberId")Integer memberId);
	

}
