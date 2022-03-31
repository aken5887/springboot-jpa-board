package com.mvc.jpaboard.persistence;

import com.mvc.jpaboard.domain.Member;
import com.mvc.jpaboard.domain.MemberRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testInsert() {

        IntStream.range(0, 100).forEach( i -> {
            Member member = new Member();
            member.setUid("user"+i);
            member.setUpw("pw"+i);
            member.setUname("사용자"+i);

            MemberRole memberRole = new MemberRole();
            if(i<80){
                memberRole.setRoleName("BASIC");
            }else if(i <= 90){
                memberRole.setRoleName("MANAGER");
            }else {
                memberRole.setRoleName("ADMIN");
            }

            member.setMemberRoles(Arrays.asList(memberRole));
            repo.save(member);
        });
    }

    @Test
    public void readTest() {
        Optional<Member> result = repo.findById("user77");
        result.ifPresent(m -> System.out.println(m.toString()));
    }

    @Test
    public void testUpdateOldMember(){
        List<String> ids = new ArrayList<>();

        for(int i=0; i<=100; i++){
            ids.add("user"+i);
        }

        repo.findAllById(ids).forEach(member -> {
            member.setUpw(passwordEncoder.encode(member.getUpw()));
            repo.save(member);
        });
    }
}