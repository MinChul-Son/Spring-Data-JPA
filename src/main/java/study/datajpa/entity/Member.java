package study.datajpa.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA 기본 스펙으로 기본 생성자가 필요함(protected로)
@ToString(of = {"id", "username", "age"}) // 연관 관계인 team은 출력하면 안됨. 무한 루프의 가능성이 존재함.
@NamedQuery(
        name = "Member.findByUsername",
        query = "select m from Member m where m.username = :username"
)
public class Member extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "member_id") // 테이블명 + id로 매칭해주는게 젤 좋음
    private Long id;
    private String username;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id") // 연관관계의 주인
    private Team team;

    public Member(String username) {
        this.username = username;
    }

    public Member(String username, int age) {
        this.username = username;
        this.age = age;
    }

    public Member(String username, int age, Team team) {
        this.username = username;
        this.age = age;
        if (team != null){
            changeTeam(team);
        }
    }

    public void changeTeam(Team team) { // 멤버는 팀을 변경가능
        this.team = team;
        team.getMembers().add(this); // 확실하게 하려면 기존에 팀 컬렉션도 찾아서 삭제해줘야함. 하지만 Team.members는 연관관계의 주인이 아님, 따라서 동작에 영향을 주지 않음.
    }
}
