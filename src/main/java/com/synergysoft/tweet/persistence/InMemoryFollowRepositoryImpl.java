package com.synergysoft.tweet.persistence;

import org.springframework.stereotype.Repository;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * {@inheritDoc}
 */
@Repository("followRepository")
public class InMemoryFollowRepositoryImpl implements FollowRepository{
    private ConcurrentMap<String, Set<String>> following = new ConcurrentHashMap<>();;

    @Override
    public void createFollowedUser(String userId, String followingUserId) {
        following.computeIfAbsent(userId, k -> new HashSet<>()).add(followingUserId);
    }

    @Override
    public Set<String> findUsersBeingFollowedByUserid(String userId) {
        return Optional.ofNullable(following.get(userId)).orElse(new HashSet<>());
    }
}
