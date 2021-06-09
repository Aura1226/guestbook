package org.zerock.guestbook.entity;

import lombok.*;

import javax.persistence.*;

@Entity //@Entity하면 @Id가 필수
@Getter
@Builder //@Builder하면 @AllArg... + @NoArg...
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Guestbook extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //값이 자동으로 생성되는 방식
    private Long gno;

    private String title;

    private String content;

    private String writer;

    public void changeTitle(String title){
        this.title = title;
    }

    public void changeContent(String content){
        this.content = content;
    }
}
