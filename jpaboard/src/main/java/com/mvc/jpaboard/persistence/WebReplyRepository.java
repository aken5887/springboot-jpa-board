package com.mvc.jpaboard.persistence;

import com.mvc.jpaboard.domain.WebReply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WebReplyRepository extends JpaRepository<WebReply, Long> {
}
