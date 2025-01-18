package jpabook.jpashop.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
public class ProblemLog {

    @Id @GeneratedValue
    private Long id;


    private Instant solvedAt;
    private String problemId;   // 문제 ID
    private String result;      // 결과 (O, X)
}

