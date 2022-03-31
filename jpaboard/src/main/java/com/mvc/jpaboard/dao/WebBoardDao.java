package com.mvc.jpaboard.dao;

import com.mvc.jpaboard.domain.QWebBoard;
import com.mvc.jpaboard.domain.QWebReply;
import com.mvc.jpaboard.domain.WebBoard;
import com.mvc.jpaboard.domain.WebReply;
import com.mvc.jpaboard.persistence.WebBoardRepository;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class WebBoardDao {

    private final JPAQueryFactory jpaQueryFactory;

        public Page<Object[]> getCustomPage(String type, String keyword, Pageable page){
            QWebBoard b = QWebBoard.webBoard;
            QWebReply r = QWebReply.webReply;

            QueryResults<Tuple> list = jpaQueryFactory.select(b.bno, b.title, r.count(), b.writer, b.regdate)
                    .from(b)
                    .leftJoin(r)
                    .on(b.bno.eq(r.webBoard.bno))
                    .where(b.bno.gt(0L)
                            .and(eqTypeAndKeyword(b, type, keyword)))
                    .groupBy(b.bno)
                    .orderBy(b.bno.desc())
                    .offset(page.getOffset())
                    .limit(page.getPageSize())
                    .fetchResults();


            List<Object[]> result = new ArrayList<>();
            list.getResults().forEach(board->{
                result.add(board.toArray());
            });

            return new PageImpl<Object[]>(result, page, list.getTotal());
        }

        public BooleanExpression eqTypeAndKeyword(QWebBoard b, String type, String keyword){
            if("t".equals(type)) {
                return b.title.like("%" + keyword + "%");
            }else if("c".equals(type)) {
                return b.content.like("%"+keyword+"%");
            }else if("w".equals(type)) {
                return b.writer.like("%"+keyword+"%");
            }else{
                return null;
            }
        }

    public List<WebReply> getRepliesOfBoard(WebBoard board) {
        QWebReply webReply = QWebReply.webReply;
        List<WebReply> result = jpaQueryFactory.select(webReply)
                .from(webReply)
                .where(webReply.webBoard.eq(board)
                .and(webReply.rno.gt(0)))
                .orderBy(webReply.rno.asc())
                .fetch();

        return result;
    }
}
