package com.mvc.jpaboard.dao;

import com.mvc.jpaboard.domain.WebBoard;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class WebBoardDaoTest {

    @Autowired
    private WebBoardDao dao;

    @Test
    void selectOneTest(){
    }
}