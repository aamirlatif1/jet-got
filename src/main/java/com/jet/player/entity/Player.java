package com.jet.player.entity;

import com.jet.player.valueobject.Status;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@Document
public class Player {
    @Id
    private String id;
    private String fullName;
    private Status status;
    private boolean autoPlay;
}
