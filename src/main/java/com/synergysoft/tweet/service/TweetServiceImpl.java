package com.synergysoft.tweet.service;

import com.synergysoft.tweet.domain.Tweet;
import com.synergysoft.tweet.persistence.FollowRepository;
import com.synergysoft.tweet.persistence.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * {@inheritDoc}
 */
@Service("twitterService")
public class TweetServiceImpl implements TweetService {
    private final FollowRepository followRepository;
    private final TweetRepository tweetRepository;

    @Autowired
    public TweetServiceImpl(FollowRepository followRepository, TweetRepository tweetRepository) {
        this.followRepository = followRepository;
        this.tweetRepository = tweetRepository;
    }

    @Override
    public void addTweet(String userId, Tweet tweet) {
        tweetRepository.createTweet(userId, tweet);

    }

    @Override
    public List<Tweet> getUserTweets(String userId) {
        return tweetRepository.findTweetsByUsers(userId);
    }

    @Override
    public void followUser(String userId, String followingUserId) {
        followRepository.createFollowedUser(userId, followingUserId);
    }

    @Override
    public Set<String> getUsersBeingFollowed(String userId) {
        return followRepository.findUsersBeingFollowedByUserid(userId);
    }

    @Override
    public List<Tweet> getTimeLine(String userId) {
        Set<String> following = followRepository.findUsersBeingFollowedByUserid(userId);
        return tweetRepository.findTweetsByUsers(following.toArray(new String[following.size()]));
    }
}
