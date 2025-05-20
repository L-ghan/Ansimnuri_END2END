package com.end2end.ansimnuri.admin.domain.repository;

import com.end2end.ansimnuri.member.domain.entity.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DashboardRepository extends JpaRepository<Member, Long> {

    @Query("SELECT m.address AS address, COUNT(m) AS count " +
            "FROM Member m " +
            "GROUP BY m.address " +
            "ORDER BY COUNT(m) DESC")
    List<Object[]> findTopAddresses(Pageable pageable);

    @Query(value =
            "SELECT question, COUNT(*) AS count FROM crimesupport GROUP BY question " +
                    "UNION ALL " +
                    "SELECT question, COUNT(*) AS count FROM crimeguide GROUP BY question",
            nativeQuery = true)
    List<Object[]> findKeywordStats();
}
