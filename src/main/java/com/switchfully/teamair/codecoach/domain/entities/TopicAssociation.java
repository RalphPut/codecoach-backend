package com.switchfully.teamair.codecoach.domain.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Table(name = "coach_topic")
@Entity
@Setter
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@IdClass(TopicAssociationId.class)
public class TopicAssociation {

  @Id
  @Column(name = "topic_id_fk")
  int topicIdFk;
  @Column(name = "coach_details_id_fk")
  int coachDetailsIdFk;
  @Column(name = "coach_knowledge_level")
  Integer coachKnowledgeLevel;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "topic_id_fk", updatable = false, insertable = false)
  private Topic topic;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "coach_details_id_fk", updatable = false, insertable = false)
  private CoachDetails coachDetail;

  public TopicAssociation setCoachKnowledgeLevel(Integer coachKnowledgeLevel) {
    if (coachKnowledgeLevel == null) {
      return this;
    }
    this.coachKnowledgeLevel = coachKnowledgeLevel;
    return this;
  }

  public TopicAssociation setTopic(Topic topic) {
    if (topic == null) {
      return this;
    }
    this.topic = topic;
    return this;
  }
}
