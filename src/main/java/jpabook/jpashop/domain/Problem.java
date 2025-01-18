package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PROBLEMS") // DB 테이블 이름
@Getter
@NoArgsConstructor
public class Problem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;         // 문제 ID

    @Column
    private String category; // 문제 분류

    @Column
    private String language; // 언어

    @Column
    private String difficulty; // 난이도

    @Column(columnDefinition = "TEXT")
    private String description; // 문제 설명 (JSON 형식으로 저장)
}

