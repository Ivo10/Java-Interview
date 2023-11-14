package com.buaa.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Player {
    private Integer id;
    private String name;
    private LocalDateTime birthday;
    private Integer price;
    private String image;
    private Integer positionId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer createUser;
    private Integer appearNum;
    private Integer goalNum;
    private Integer assistNum;
}
