package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {


    List<Problem> findAllByCategoryAndLanguageAndDifficulty(
            @Param("category") String category,
            @Param("language") String language,
            @Param("difficulty") String difficulty
    );

    // problemId로 문제 검색
    Optional<Problem> findById(Long Id);
}

