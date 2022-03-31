package com.mvc.jpaboard.persistence;

import com.mvc.jpaboard.domain.WebBoard;
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

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Commit
class WebBoardRepositoryTest {

    @Autowired
    WebBoardRepository repo;

    @Test
    public void insertBoardDummies() {
        IntStream.range(0, 300).forEach( i -> {
            WebBoard wb = new WebBoard();
            wb.setTitle("Sample Board Title "+i);
            wb.setContent("Content Sample .. "+i+" of Board ");
            wb.setWriter("user0"+(i%10));
            repo.save(wb);
        });
    }

    @Test
    public void testList1(){
        Pageable pageable = PageRequest.of(0, 20, Sort.Direction.DESC, "bno");
        Page<WebBoard> result = repo.findAll(repo.makePredicate("t", "10"), pageable);

        System.out.println("PAGE : "+ result.getPageable());
        result.getContent().forEach(System.out::println);
    }
}