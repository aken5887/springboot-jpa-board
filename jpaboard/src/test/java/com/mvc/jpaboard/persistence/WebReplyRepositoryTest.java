package com.mvc.jpaboard.persistence;

import com.mvc.jpaboard.domain.WebBoard;
import com.mvc.jpaboard.domain.WebReply;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Commit
class WebReplyRepositoryTest {

    @Autowired
    WebReplyRepository repo;

    @Test
    public void testInsertReplies() {
        Long[] arr = {303L, 300L, 299L};

        Arrays.stream(arr).forEach( a -> {
            WebBoard board = new WebBoard();
            board.setBno(a);

            IntStream.range(0,10).forEach(i->{
                WebReply wr = new WebReply();
                wr.setReplyText("REPLY .."+i);
                wr.setReplyer("replyr0"+i);
                wr.setWebBoard(board);
                repo.save(wr);
            });
        });

        Assertions.assertThat(repo.findAll().size()).isEqualTo(30);
    }
}