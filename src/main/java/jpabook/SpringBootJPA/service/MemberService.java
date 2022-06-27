package jpabook.SpringBootJPA.service;

import jpabook.SpringBootJPA.domain.Member;
import jpabook.SpringBootJPA.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) //readOnly = true : JPA가 조회하는 곳에서 효율성을 높여줌.. 왜인가?
//@AllArgsConstructor             //생성자 injection을 생성해줌..
@RequiredArgsConstructor        //final이 존재하는 필드를 기준으로 생성자 injection을 만들어줌.
public class MemberService {

    private final MemberRepository memberRepository;


    /**회원 가입*/
    //메소드에 선언한 트랜잭션이 우선권을 가짐.
    @Transactional
    public Long join(Member member) {
        //중복 회원 검증
        validateDuplicateMember(member);

        //회원 가입
        memberRepository.save(member);
        return member.getId();
    }//end join()

    /**중복 회원 검증*/
    @Transactional
    private void validateDuplicateMember(Member member) {
        //이름으로?
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }//end if()
    }//end validateDuplicateMember()

    /**전체 회원 조회*/
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }//end findMembers()

    /**단건 회원 조회*/
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }//end findOne()

}//end class()
