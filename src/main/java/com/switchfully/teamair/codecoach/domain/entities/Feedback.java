package com.switchfully.teamair.codecoach.domain.entities;


import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Entity
@Table(name = "feedback")
public class Feedback {

    @Id
    @GeneratedValue
    @Column(name = "feedback_id")
    UUID feedbackId;
    @Column(name = "feedback_coach")
    String feedbackCoach;
    @Column(name= "feedback_coachee")
    String feedbackCoachee;
}
