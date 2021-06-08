package study.datajpa.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class Member {

    @Id @GeneratedValue
    private Long id;
    private String username;

    protected Member() { // JPA 기본 스펙으로 기본 생성자가 필요함(protected로)
    }

    public Member(String username) {
        this.username = username;
    }
}
