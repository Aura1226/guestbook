package org.zerock.guestbook.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.guestbook.entity.Guestbook;
import org.zerock.guestbook.entity.QGuestbook;

import java.util.Arrays;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class GuestbookRepositoryTests {

    @Autowired
    private GuestbookRepository guestbookRepository;


    @Test
    public void insertDummies(){

        IntStream.rangeClosed(1, 300).forEach(i -> {
            Guestbook guestbook = Guestbook.builder().title("Title..."+ i)
                    .content("Content..."+ (i % 10))
                    .build();

            log.info(guestbookRepository.save(guestbook));
        });
    }

    @Test
    public void testUpdate(){

        Guestbook guestbook = guestbookRepository.findById(300L).get();

        log.info("BEFORE.........................");
        log.info(guestbook);

        guestbook.changeTitle("Update 300 Title");
        guestbook.changeContent("Update 300 Content");

        log.info("After.................");
        log.info(guestbook);

        guestbookRepository.save(guestbook);

    }

    @Test
    public void testQuery1(){ //검색

        Pageable pageable =
                PageRequest.of(0, 10, Sort.by("gno").descending());

        QGuestbook qGuestbook = QGuestbook.guestbook;

        String keyword = "1";

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        //title like?
        BooleanExpression expression =
                qGuestbook.title.contains(keyword);

        booleanBuilder.and(expression);

        Page<Guestbook> result = guestbookRepository.findAll(booleanBuilder, pageable);

        result.get().forEach(guestbook -> log.info(guestbook));
    }

    @Test
    public void testSearch(){

        Pageable pageable =
                PageRequest.of(0, 10, Sort.by("gno").descending());

        String keyword = "1";

        String[] arr = {"t", "c"};

        //Qdomain
        QGuestbook qGuestbook = QGuestbook.guestbook;

        //total
        BooleanBuilder total = new BooleanBuilder();

        //gno
        BooleanBuilder gno = new BooleanBuilder();

        //condition... 바깥 포장지가 필요
        BooleanBuilder condition = new BooleanBuilder();

        Arrays.stream(arr).forEach(type -> {
            log.info("type:" + type);
            switch (type){
                case "t":
                    condition.or(qGuestbook.title.contains(keyword));
                    break;
                case "c":
                    condition.or(qGuestbook.content.contains(keyword));
                    break;
                case "w":
                    condition.or(qGuestbook.writer.contains(keyword));
                    break;
            }


        });// loop

        total.and(condition);

        gno.and(qGuestbook.gno.gt(0L)); //  '<'

        total.and(gno);

        guestbookRepository.findAll(condition, pageable);
    }
}
