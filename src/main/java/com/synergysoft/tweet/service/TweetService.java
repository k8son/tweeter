package com.synergysoft.tweet.service;

import com.synergysoft.tweet.domain.Tweet;

import java.util.List;
import java.util.Set;

/**
 * Service class responsible for creation and fetching of user tweets.
 */
public interface TweetService {
    /**
     * Creates a tweet for a user.
     * @param userId
     * @param tweet
     */
    void addTweet(String userId, Tweet tweet);

    /**
     * Return the list of tweets for a given user in reverse chronological order.
     * @param userId
     * @return
     */
    List<Tweet> getUserTweets(String userId);

    /**
     * Allows a user to follow another user.
     * @param userId
     * @param followingUserId
     */
    void followUser(String userId, String followingUserId);

    /**
     * Returns set of all users being followed.
     * @param userId
     */
    Set<String> getUsersBeingFollowed(String userId);

    /**
     * Returns the Timeline for a given user in reverse chronological order.
     * A timeline consists of all the tweets posted by all the users being followed by this user.
     * @param userId
     * @return
     */
    List<Tweet> getTimeLine(String userId);
}
