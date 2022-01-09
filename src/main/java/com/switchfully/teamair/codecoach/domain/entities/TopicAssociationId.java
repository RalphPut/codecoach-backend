package com.switchfully.teamair.codecoach.domain.entities;

import java.io.Serializable;

public class TopicAssociationId implements Serializable {


    private int topicIdFk;
    private int coachDetailsIdFk;

    public int hashCode() {
        return (int) (topicIdFk + coachDetailsIdFk);
    }

    public boolean equals(Object object) {
        if (object instanceof TopicAssociationId) {
            TopicAssociationId otherId = (TopicAssociationId) object;
            return (otherId.topicIdFk == this.topicIdFk) && (otherId.coachDetailsIdFk == this.coachDetailsIdFk);
        }
        return false;
    }

}

