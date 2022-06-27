package jpabook.SpringBootJPA.service;

import jpabook.SpringBootJPA.domain.Member;
import jpabook.SpringBootJPA.repository.MemberRepository;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)  //spring과 같이 실행하겠다.
@SpringBootTest                     //springboot를 띄운 상태로 테스트를 하겠다.
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Rollback(false)
    public void 회원가입() throws Exception {
        //given 이런게 주어졌을 때
        Member member = new Member();
        member.setName("신형철");

        //when 이렇게 하면
        Long saveId = memberService.join(member);

        //then 이렇게 된다.
        assertEquals(member, memberRepository.findOne(saveId));
    }

    @Test
    public void 중복_회원_예외() throws Exception {
        //given
        Member member = new Member();
        member.setName("신형철2");

        Member member2 = new Member();
        member2.setName("신형철3");

        //when
        memberService.join(member);
        memberService.join(member2);

//        try {
//            memberService.join(member2);
//        } catch (IllegalStateException e){
//            return;
//        }

        //then
        //fail("예외가 발생해야 한다.");
    }

}