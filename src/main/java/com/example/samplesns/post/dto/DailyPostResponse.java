package com.example.samplesns.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Date;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class DailyPostResponse {
    private String email;
    private String nickname;
    private Date createDate;
    private Long postCount;

}
