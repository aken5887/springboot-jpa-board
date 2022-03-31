package com.mvc.jpaboard.controller;

import com.mvc.jpaboard.dao.WebBoardDao;
import com.mvc.jpaboard.domain.WebBoard;
import com.mvc.jpaboard.domain.WebReply;
import com.mvc.jpaboard.persistence.WebReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/replies")
@RequiredArgsConstructor
@Slf4j
public class WebReplyController {

    private final WebReplyRepository webReplyRepository;
    private final WebBoardDao webBoardDao;

    @GetMapping("/{bno}")
    public ResponseEntity<List<WebReply>> getRepiles(@PathVariable Long bno){
        log.info("get All replies");
        WebBoard webBoard = new WebBoard();
        webBoard.setBno(bno);
        return new ResponseEntity<>(getWebReplyList(webBoard), HttpStatus.OK);
    }

    @Secured(value={"ROLE_BASIC", "ROLE_MANAGER", "ROLE_ADMIN"})
    @PostMapping("/{bno}")
    public ResponseEntity<List<WebReply>> addReply(@PathVariable("bno")Long bno, @RequestBody WebReply webReply){
       log.info("add Reply() bno : {}, webReply : {}", bno, webReply);
       WebBoard board = new WebBoard();
       board.setBno(bno);
       webReply.setWebBoard(board);
       webReplyRepository.save(webReply);
       return new ResponseEntity<>(getWebReplyList(board), HttpStatus.CREATED);
    }

    private List<WebReply> getWebReplyList(WebBoard board){
        List<WebReply> result = webBoardDao.getRepliesOfBoard(board);
        log.info("replyList : {}", result);
        return result;
    }

    @Secured(value={"ROLE_BASIC", "ROLE_MANAGER", "ROLE_ADMIN"})
    @DeleteMapping("/{bno}/{rno}")
    public ResponseEntity<List<WebReply>> delete(@PathVariable Long bno, @PathVariable Long rno) {
        log.info("delete bno : {}, rno : {}", bno, rno);
        webReplyRepository.deleteById(rno);

        WebBoard webBoard = new WebBoard();
        webBoard.setBno(bno);

        return new ResponseEntity<>(getWebReplyList(webBoard), HttpStatus.OK);
    }

    @Secured(value={"ROLE_BASIC", "ROLE_MANAGER", "ROLE_ADMIN"})
    @PutMapping("/{bno}")
    public ResponseEntity<List<WebReply>> modify(@PathVariable Long bno, @RequestBody WebReply webReply){
        log.info("modfiy bno: {}, webReply: {}", bno, webReply);

        webReplyRepository.findById(webReply.getRno()).ifPresent(reply -> {
            reply.setReplyer(webReply.getReplyer());
            reply.setReplyText(webReply.getReplyText());
            webReplyRepository.save(reply);
        });

        WebBoard board = new WebBoard();
        board.setBno(bno);

        return new ResponseEntity<>(getWebReplyList(board), HttpStatus.CREATED);
    }
}
