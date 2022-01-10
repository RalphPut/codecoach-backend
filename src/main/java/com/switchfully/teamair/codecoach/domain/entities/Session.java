package com.switchfully.teamair.codecoach.domain.entities;


import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity @Table(name = "sessions")
@AllArgsConstructor @NoArgsConstructor
@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Session {
  @Id @GeneratedValue
  UUID id;
  @OneToOne
  @JoinColumn(name = "coach_id" )
  User coach;
  @OneToOne
  @JoinColumn(name = "coachee_id" )
  User coachee;

  @OneToOne
  @JoinColumn(name = "topic_Id")
  Topic topic;

  @Column(name = "session_date_time")
  LocalDateTime dateTime;
  @Enumerated(EnumType.STRING)
  @Column(name = "session_type")
  SessionType sessionType;
  @Column(name = "remarks")
  String remarks;
  @Enumerated(EnumType.STRING)
  @Column(name = "session_status")
  SessionStatus sessionStatus;
//  @OneToOne
//  @JoinColumn(name = "feedback_id")
//  Feedback feedback;

}
