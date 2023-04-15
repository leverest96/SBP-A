package com.ssafit.repository;

import com.ssafit.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(final String email);
    Optional<Member> findByNickname(final String nickname);
    Optional<Member> findByStudentId(final String studentId);
}
