package com.buaa.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Position {
    private Integer id;
    private String positionName;
    private Integer createUser;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
