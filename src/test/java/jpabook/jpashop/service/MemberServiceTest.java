package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.AssertionsForClassTypes.fail;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)   // JUnit에 스프링 엮어서 사용할래..
@SpringBootTest // 스프링을 띄운상태에서 테스트를 한다.
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;
    @Test
    public void 회원가입() throws Exception{

        //given
        Member member = new Member();
        member.setName("kim");

        // when
        Long savedId = memberService.join(member);

        //then
        em.flush();
        assertEquals(member, memberRepository.findOne(savedId));
    }


    @Test(expected=IllegalStateException.class)
    public void 중복_회원_예외() throws Exception{
        //given
        Member m = new Member();
        m.setName("kim");

        Member m2 = new Member();
        m2.setName("kim");

        //when
        memberService.join(m);
        memberService.join(m2);

        //then
        fail("예외가 발생해야한다.");
    }


}