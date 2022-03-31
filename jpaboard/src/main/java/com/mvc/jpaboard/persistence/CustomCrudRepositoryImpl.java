package com.mvc.jpaboard.persistence;

import com.mvc.jpaboard.domain.QWebBoard;
import com.mvc.jpaboard.domain.QWebReply;
import com.mvc.jpaboard.domain.WebBoard;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CustomCrudRepositoryImpl extends QuerydslRepositorySupport implements CustomWebBoard {

    public CustomCrudRepositoryImpl(){
        super(WebBoard.class);
    }

    @Override
    public Page<Object[]> getCustomPage(String type, String keyword, Pageable page) {
        log.info("type : {}, keyword : {}, page : {} ",type, keyword, page );
        QWebBoard b = QWebBoard.webBoard;
        QWebReply r = QWebReply.webReply;

        JPQLQuery<WebBoard> query = from(b);

        JPQLQuery<Tuple> tuple = query.select(b.bno, b.title, r.count(), b.writer , b.regdate);

        tuple.leftJoin(r);
        tuple.on(b.bno.eq(r.webBoard.bno));
        tuple.where(b.bno.gt(0L));

        if(type != null){
            switch(type){
                case "t":
                    tuple.where(b.title.like("%"+keyword+"%"));
                    break;
                case "c":
                    tuple.where(b.content.like("%"+keyword+"%"));
                    break;
                case "w":
                    tuple.where(b.writer.like("%"+keyword+"%"));
                    break;
            }
        }

        tuple.groupBy(b.bno);
        tuple.orderBy(b.bno.desc());

        tuple.offset(page.getOffset());
        tuple.limit(page.getPageSize());

        List<Tuple> list = tuple.fetch();

        List<Object[]> resultList = new ArrayList<>();

        list.forEach(t -> {
            resultList.add(t.toArray());
        });

        long total = tuple.fetchCount();

        return new PageImpl<>(resultList, page, total);
    }
}
