package com.ream.core.repository.voting;

import com.ream.core.domain.baseInfo.City;
import com.ream.core.domain.voting.Member;
import com.ream.core.repository.voting.extra.MemberExtraRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MemberRepository extends JpaRepository<Member, UUID>, MemberExtraRepository {

    List<Member> findByMasterAndPermissionSelectUnitAndIdCodeIsNotNullOrderBySelectionOrderAsc(Boolean master, Boolean permissionSelectUnit);

}
