package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.ProblemLog;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProblemLogRepository {
    private final EntityManager em;

    public List<ProblemLog> findAll() {
        /*// 빈 리스트 반환
        return new ArrayList<>();*/

        return em.createQuery("select m from ProblemLog m", ProblemLog.class)
                .getResultList();
    };

    // 예시이므로 JPA 또는 MyBatis가 아니라 직접 구현한다고 가정
    // Spring Data JPA라면 extends JpaRepository<ProblemLog, Long> 형태가 될 수 있습니다.

    public void save(ProblemLog problemLog) {
        em.persist(problemLog);
    }

    public int count() {
        // 날짜 기준으로 고유한 문제 풀이 로그의 수를 계산
        return em.createQuery("select count(distinct p.solvedDate) from ProblemLog p", int.class)
                .getSingleResult();
    }
}

