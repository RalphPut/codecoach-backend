package com.switchfully.teamair.codecoach.domain.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity
@Table(name = "coach_details")
public class CoachDetails {

    @Id
    @GeneratedValue
    @Column(name = "coach_details_id")
    Integer id;
    @Column(name = "available_time")
    String availableTime;
    @Column(name = "coach_xp")
    Integer coachXp;
    @Column(name = "introduction")
    String introduction;
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "coachDetail")
    private Set<TopicAssociation> topics;

    public CoachDetails setAvailableTime(String availableTime) {
        if(availableTime == null){
            return this;
        }
        this.availableTime = availableTime;
        return this;
    }

    public CoachDetails setCoachXp(Integer coachXp) {
        if(coachXp == null){
            return this;
        }
        this.coachXp = coachXp;
        return this;
    }

    public CoachDetails setIntroduction(String introduction) {
        if(introduction == null){
            return this;
        }
        this.introduction = introduction;
        return this;
    }

    public void addTopic(Topic topic, int knowledgeLevel) {
        TopicAssociation association = new TopicAssociation();
        association.setTopic(topic);
        association.setCoachDetail(this);
        association.setTopicIdFk(topic.getId());
        association.setCoachDetailsIdFk(this.getId());
        association.setCoachKnowledgeLevel(knowledgeLevel);
        if(this.topics == null)
            this.topics = new HashSet<>();

        this.topics.add(association);
        // Also add the association object to the employee.
        topic.getCoachDetail().add(association);
    }

    public void removeTopicAssociations(){
        this.topics.clear();
    }



}
