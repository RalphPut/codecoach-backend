package com.switchfully.teamair.codecoach.domain.entities;

public enum SessionStatus {
    REQUESTED,
    ACCEPTED,
    DECLINED,
    DONE_WAITING_FOR_FEEDBACK,
    CANCELLED_BY_COACH,
    CANCELLED_BY_COACHEE,
    ARCHIVED,
    FINISHED
}
