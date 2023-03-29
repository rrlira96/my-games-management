package com.rrlira96.mygamesmanagement.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MyGame {
    @EqualsAndHashCode.Include
    private Game game;
    private Review review;
    private boolean finished;
    private boolean allAchievements;

}


