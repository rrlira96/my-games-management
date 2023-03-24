package com.rrlira96.mygamesmanagement.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Document(collection = "games")
@CompoundIndex(def = "{'name': 1, 'releasedDate': 1}", unique = true)
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String name;
    private List<Platform> platforms;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date releasedDate;
    private String genre;

}


