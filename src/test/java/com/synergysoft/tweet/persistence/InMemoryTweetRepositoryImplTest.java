package com.synergysoft.tweet.persistence;

import com.synergysoft.tweet.domain.Tweet;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Unit tests for InMemoryTweetRepositoryImpl.
 */
public class InMemoryTweetRepositoryImplTest {
    private InMemoryTweetRepositoryImpl inMemoryTweetRepository;

    @Before
    public void setup(){
        inMemoryTweetRepository = new InMemoryTweetRepositoryImpl();
    }

    @Test
    public void shouldReturnEmptySetWhenUserTweetsNoFound(){
        inMemoryTweetRepository.createTweet("john", new Tweet("my first tweet"));
        // Get tweets for a user that doesn't exist
        List<Tweet> tweets = inMemoryTweetRepository.findTweetsByUsers("paul");
        assertThat(tweets.size(), is(0));
    }

    @Test
    public void shouldAddTweetToEmptyRepository(){
        inMemoryTweetRepository.createTweet("john", new Tweet("my first tweet"));
        List<Tweet> tweets = inMemoryTweetRepository.findTweetsByUsers("john");
        assertThat(tweets.size(), is(1));
        assertThat(tweets.iterator().next().getMessage(), is("my first tweet"));
    }

    @Test
    public void shouldReturnTweetsInReverseChronologicalOrder(){
        inMemoryTweetRepository.createTweet("john", new Tweet("my first tweet"));
        inMemoryTweetRepository.createTweet("john", new Tweet("my second tweet"));
        List<Tweet> tweets = inMemoryTweetRepository.findTweetsByUsers("john");
        assertThat(tweets.size(), is(2));
        assertThat(tweets.get(0).getMessage(), is("my second tweet"));
        assertThat(tweets.get(1).getMessage(), is("my first tweet"));
    }

    @Test
    public void shouldReturnTimelineTweetsInReverseChronologicalOrder() throws InterruptedException {
        // Setup a number of tweets by different users
        createTweet("john", "john tweet 1");
        createTweet("paul", "paul tweet 1");
        createTweet("steven", "steven tweet 1");
        createTweet("bob", "bob tweet 1");

        createTweet("john", "john tweet 2");
        createTweet("paul", "paul tweet 2");
        createTweet("steven", "steven tweet 2");
        createTweet("bob", "bob tweet 2");

        createTweet("john", "john tweet 3");
        createTweet("paul", "paul tweet 3");
        createTweet("steven", "steven tweet 3");
        createTweet("bob", "bob tweet 3");

        List<Tweet> tweets = inMemoryTweetRepository.findTweetsByUsers("john", "paul", "bob");
        assertThat(tweets.size(), is(9));

        assertThat(tweets.get(0).getMessage(), is("bob tweet 3"));
        assertThat(tweets.get(1).getMessage(), is("paul tweet 3"));
        assertThat(tweets.get(2).getMessage(), is("john tweet 3"));

        assertThat(tweets.get(3).getMessage(), is("bob tweet 2"));
        assertThat(tweets.get(4).getMessage(), is("paul tweet 2"));
        assertThat(tweets.get(5).getMessage(), is("john tweet 2"));

        assertThat(tweets.get(6).getMessage(), is("bob tweet 1"));
        assertThat(tweets.get(7).getMessage(), is("paul tweet 1"));
        assertThat(tweets.get(8).getMessage(), is("john tweet 1"));


    }

    private void createTweet(String userId, String msg) throws InterruptedException {
        inMemoryTweetRepository.createTweet(userId, new Tweet(msg));
        Thread.sleep(100);
    }
}