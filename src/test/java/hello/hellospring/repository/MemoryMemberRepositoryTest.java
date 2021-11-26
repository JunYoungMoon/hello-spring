package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryMemberRepositoryTest {

    /* 여기서는 기능을 먼저 개발하고 테스트 케이스를 작성했지만 어떤걸 구현하기 위해서
    * 먼저 틀을 만드는 것. 즉 테스트 케이스를 우선 만든다음 구현을 하는것을 TDD 개발이라고 한다.*/

    MemoryMemberRepository repository = new MemoryMemberRepository();

    // 테스트 메서드가 끝날때마다 어떤 동작을 하게 만들수 있다. 콜백메서드
    // 테스트는 서로 의존관계가 없이 설계가 되어야 한다.
    // 그러기 위해선 하나의 테스트가 끝날때 마다 저장소나 공용 데이터를 깔끔하게 지워줘야한다.
    @AfterEach
    public void aterEach() {
        repository.clearStore();
    }

    @Test
    public void save() {
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        Member result = repository.findById(member.getId()).get();

        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();

        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }
}
