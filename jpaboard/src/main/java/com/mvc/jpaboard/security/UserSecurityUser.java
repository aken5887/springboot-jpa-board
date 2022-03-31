package com.mvc.jpaboard.security;

import com.mvc.jpaboard.domain.Member;
import com.mvc.jpaboard.domain.MemberRole;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class UserSecurityUser extends User {

    private static final String ROLE_PREFIX = "ROLE_";

    private Member member;

    public UserSecurityUser(Member member){
      super(member.getUid(), member.getUpw(), makeGrantedAuthority(member.getMemberRoles()));
      this.member = member;
    }

    private static List<GrantedAuthority> makeGrantedAuthority(List<MemberRole> memberRoles) {
        List<GrantedAuthority> list = new ArrayList<>();
        memberRoles.forEach(
                role -> list.add(new SimpleGrantedAuthority(ROLE_PREFIX+role.getRoleName()))
        );
        return list;
    }
}
