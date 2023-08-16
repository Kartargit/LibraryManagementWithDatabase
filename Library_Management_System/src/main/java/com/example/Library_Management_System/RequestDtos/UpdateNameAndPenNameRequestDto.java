package com.example.Library_Management_System.RequestDtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateNameAndPenNameRequestDto {
    private Integer authorId;
    private String newName;
    private String penName;
}
