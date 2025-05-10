package com.gym.service;

import java.util.List;
import java.util.Optional;

import com.gym.entity.Member;
import com.gym.entity.Membership;

public interface MembershipService {

	boolean hasActiveMembership(Integer member_id);

	Membership createMembership(Membership membership);

	List<Membership> findByMemberId(Integer memberId, Boolean active);

	List<Membership> findByStatus(Boolean active);
	
	List<Membership> findAllMemberships();

	List<Membership> findByMemberId(Integer memberId);

	Optional<Membership> findByMembershipId(Integer membershipId);

	void updateMembership(Membership membership);
	
}
