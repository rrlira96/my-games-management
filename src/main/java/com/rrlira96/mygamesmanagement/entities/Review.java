package com.rrlira96.mygamesmanagement.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Review {
    private int rating;
    private List<String> comments;

}
