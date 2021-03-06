package com.kopo.Project.Controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieForm {

    private Long id;
    //데이터베이스에는 date형식으로 입력 ex)'2020-01-01'
    private String date;
    //문자열 형태로 ex) '235959'
    private String start_time;
    private int running_time;
    private String movie_title;
    private String company_type;
    private String region;
    private int hall;
}