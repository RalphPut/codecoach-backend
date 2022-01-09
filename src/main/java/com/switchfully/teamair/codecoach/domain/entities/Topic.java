package com.switchfully.teamair.codecoach.domain.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Entity
@Table(name = "topic")
public class Topic {
    @Id
    @Column(name = "topic_id")
    @GeneratedValue
    int id;
    @Column(name = "name")
    String name;

    @OneToMany(mappedBy="topic", cascade = CascadeType.ALL)
    Set<TopicAssociation> coachDetail = new HashSet<>();

    public Topic(String name){
        this.name = name;
    }
}
