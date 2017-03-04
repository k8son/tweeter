package com.synergysoft.tweet.persistence;

import com.synergysoft.tweet.domain.Tweet;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * {@inheritDoc}
 */
@Repository("tweetRepository")
public class InMemoryTweetRepositoryImpl implements TweetRepository{
    private ConcurrentMap<String, List<Tweet>> tweets = new ConcurrentHashMap<>();

    @Override
    public void createTweet(String userId, Tweet tweet) {
        tweets.computeIfAbsent(userId, k -> new ArrayList<>()).add(tweet);
    }

    @Override
    public List<Tweet> findTweetsByUsers(String... userId) {
        // Get all tweets for all users sorted in reverse chronological order
        return Arrays.stream(userId).parallel()
                            .filter(user -> tweets.get(user) != null)
                            .map(user -> tweets.get(user))
                            .flatMap(Collection::stream)
                            .sorted((Tweet t1, Tweet t2) -> t2.getCreatedTime().compareTo(t1.getCreatedTime()))
                            .collect(Collectors.toList());
    }
}
