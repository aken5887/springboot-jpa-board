package com.mvc.jpaboard.persistence;

import com.mvc.jpaboard.domain.WebBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomCrudRepository extends JpaRepository<WebBoard, Long>, CustomWebBoard {
}
