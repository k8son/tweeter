package com.synergysoft.tweet.persistence;

import com.synergysoft.tweet.domain.Tweet;

import java.util.List;
import java.util.Set;

/**
 * Tweet repository. Allows for the creation and retrieval of tweets.
 */
public interface TweetRepository {

    /**
     * Creates a Tweet for the given user.
     * @param userId
     * @param tweet
     */
    void createTweet(String userId, Tweet tweet);

    /**
     * Returns the list of tweets made by the given user(s)
     * @param userId
     * @return
     */
    List<Tweet> findTweetsByUsers(String... userId);
}
