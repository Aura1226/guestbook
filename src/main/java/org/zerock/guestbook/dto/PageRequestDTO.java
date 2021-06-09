package org.zerock.guestbook.dto;


import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


@Data
public class PageRequestDTO { //모든 페이징을 할 때 다 이걸 쓸 수 있게...범용성있게 만들어보자

    private int page;
    private int size;
    private String type;
    private String keyword;

    public PageRequestDTO(){//초기화를 위해...Default와 같은
        this.page = 1;
        this.size = 10;
    }

    // parameter를 sort로 받아야한다
    public Pageable getPageable(Sort sort){
        return PageRequest.of(page-1,size, sort);
    }
}
