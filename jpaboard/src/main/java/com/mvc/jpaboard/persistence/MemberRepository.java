package com.mvc.jpaboard.persistence;

import com.mvc.jpaboard.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {
}
