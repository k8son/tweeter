package com.synergysoft.tweet.service;

import com.synergysoft.tweet.domain.Tweet;
import com.synergysoft.tweet.persistence.FollowRepository;
import com.synergysoft.tweet.persistence.TweetRepository;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for TweetServiceImpl. Uses Mockito for mocking dependant services.
 */
@RunWith(MockitoJUnitRunner.class)
public class TweetServiceImplTest {
    @Mock
    private FollowRepository followRepository;
    @Mock
    private TweetRepository tweetRepository;

    @InjectMocks
    private TweetServiceImpl tweetService;

    @Test
    public void addTweet() throws Exception {
        Tweet tweet = new Tweet("sample tweet");
        tweetService.addTweet("john", tweet);
        verify(tweetRepository, times(1)).createTweet("john", tweet);
    }

    @Test
    public void getUserTweets() throws Exception {
        when(tweetRepository.findTweetsByUsers("john")).thenReturn(
                                    Arrays.asList(new Tweet("msg3"), new Tweet("msg2"), new Tweet("msg1")));
        List<Tweet> userTweets = tweetService.getUserTweets("john");
        assertThat(userTweets.size(), is(3));
        verify(tweetRepository, times(1)).findTweetsByUsers("john");
    }

    @Test
    public void followUser() throws Exception {
        tweetService.followUser("john", "paul");
        verify(followRepository, times(1)).createFollowedUser("john", "paul");
    }

    @Test
    public void getAllFollowedUsers() throws Exception {
        List<String> names = Arrays.asList("paul", "bob", "steven");
        Set<String> followed = new HashSet<>(names);
        when(followRepository.findUsersBeingFollowedByUserid("john")).thenReturn(followed);

        Set<String> users = tweetService.getUsersBeingFollowed("john");
        assertThat(users.size(), is(3));
        assertThat(users.contains("paul"), is(true));
        assertThat(users.contains("steven"), is(true));
        assertThat(users.contains("bob"), is(true));
    }


    @Test
    public void getTimeLine() throws Exception {
        // Set up the list of followers
        List<String> names = Arrays.asList("paul", "bob");
        Set<String> followed = new HashSet<>(names);
        when(followRepository.findUsersBeingFollowedByUserid("john")).thenReturn(followed);

        // Now set up the list of messages that should be returned if we ask for tweet repository for all messages for the
        // given names.
        // The arguments into the method findTweetsByUsers() can be either bob or paul in any order. Therefore define a custom
        // Argument matcher that satisfies that criteris
        ArgMatcher argMatcher = new ArgMatcher(names);

        // Now set up the list of messages for these users
        List<Tweet> tweets = Arrays.asList(new Tweet("msg3"), new Tweet("msg2"), new Tweet("msg1"));
        when(tweetRepository.findTweetsByUsers(Mockito.argThat(argMatcher), Mockito.argThat(argMatcher))).thenReturn(tweets);

        List<Tweet> timeLine = tweetService.getTimeLine("john");
        assertThat(timeLine.size(), is(3));

        verify(followRepository, times(1)).findUsersBeingFollowedByUserid("john");
        verify(tweetRepository, times(1)).findTweetsByUsers(Mockito.argThat(argMatcher), Mockito.argThat(argMatcher));
    }

    static class ArgMatcher extends BaseMatcher<String>{
        private List<String> names;

        public ArgMatcher(List<String> names) {
            this.names = names;
        }

        @Override
        public boolean matches(Object o) {
            return names.contains(o.toString());
        }

        @Override
        public void describeTo(Description description) {

        }
    }

}