package com.mvc.jpaboard.persistence;

import com.mvc.jpaboard.dao.WebBoardDao;
import groovy.util.logging.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Commit
class CustomCrudRepositoryTest {

    @Autowired
    CustomCrudRepository repo;
    @Autowired
    WebBoardDao dao;

    @Test
    public void test1(){
        Pageable pageable = PageRequest.of(0, 10, Sort.Direction.DESC, "bno");

        String type="t";
        String keyword = "10";

        Page<Object[]> result = repo.getCustomPage(type, keyword, pageable);
        System.out.println(""+result);
    }

    @Test
    public void testWriter() {
        Pageable pageabe = PageRequest.of(0, 10, Sort.Direction.DESC, "bno");

        String type = "w";
        String keyword = "user09";

        Page<Object[]> result = dao.getCustomPage(type, keyword, pageabe);
        result.getContent().forEach(arr->{
            System.out.println(Arrays.toString(arr));
        });
    }
}