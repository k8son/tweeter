package com.synergysoft.tweet.persistence;

import java.util.Set;

/**
 * Repository for following users and fetching list of users being followed.
 */
public interface FollowRepository {
    /**
     * Allows a user to follow another user.
     * @param userId
     * @param followingUserId
     */
    void createFollowedUser(String userId, String followingUserId);

    /**
     * Returns the set of userids being followed by the given user.
     * @param userId
     * @return
     */
    Set<String> findUsersBeingFollowedByUserid(String userId);
}
