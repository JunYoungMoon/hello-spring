package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository {

    //실무에선 동시성 문제가 있을수 있어서 공유되는 변수에서는 ConcurrentHashMap 사용
    private static Map<Long, Member> store = new HashMap<>();
    //마찬가지로 실무에선 동시성 문제를 고려해서 AtomicLong atomic = new AtomicLong();을 사용
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        //반환되는 값이 null일때가 있으므로 Optional로 감싸서 주면 클라이언트에서 어떤 작업을 해줄수있다.
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        //루프를 돌리면서 람다를 돌리는데, 파라미터로 들어온 name과 getName이 같은 경우에만 필터링되고 그중에서 찾으면 반환을 한다.
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        //자바 실무에선 List를 많이 사용한다 루프 돌리기 편함.
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }
}
