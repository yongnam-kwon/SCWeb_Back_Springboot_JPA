package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

                                // 두 Annotation :
@RunWith(SpringRunner.class)    // Memory 모드로 DB까지 함께 엮어서 테스트할 수 있음
@SpringBootTest                 // → Spring과 Integration해서 테스트 할 수 있음

@Transactional  // 이 어노테이션이 테스트코드에 있으면 기본적으로 rollback해버림. @Test 부분에 @Rollback(false) 하면 롤백 안함
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    //@Rollback(value = false)    // 콘솔에서 insert 문을 볼 수 있음!! && h2 DB에도 들어감
    public void 회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setName("Kwon");

        // when
        Long saveId = memberService.join(member);

        // then
        Assert.assertEquals(member, memberRepository.findOne(saveId));
        // 통과하면, 즉 같은 member라면 회원가입이 정상적으로 잘 됐다라는 의미!
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("Min");

        Member member2 = new Member();
        member2.setName("Min");

        // when : 똑같은 이름 넣었을 때 어떻게 될까?
        Long joinId1 = memberService.join(member1);
        Long joinId2 = memberService.join(member2);

        // @Test(expected = IllegalStateException.class) 사용으로 생략 가능
//        try {
//            Long joinId2 = memberService.join(member2);
//        } catch (IllegalStateException e) {
//            return; // 중복 이름으로 에러 발생하면 끝;
//        }
//
        // then
        Assert.fail("예외가 발생해야 합니당!!");
    }
}