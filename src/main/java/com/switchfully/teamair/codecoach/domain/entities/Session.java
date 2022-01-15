package com.switchfully.teamair.codecoach.domain.entities;


import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.*;

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
  @JoinColumn(name = "topic_id")
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
  @OneToOne(cascade= CascadeType.ALL)
  @JoinColumn(name = "feedback_id")
  Feedback feedback;

}
