package com.synergysoft.tweet.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.synergysoft.tweet.domain.Tweet;
import com.synergysoft.tweet.service.TweetService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest
public class TweetControllerTest {
    private MockMvc mvc;
    @Mock
    private TweetService tweetService;
    @InjectMocks
    private TweetController tweetController;

    private ObjectMapper mapper;

    @Before
    public void setup(){
        mvc = MockMvcBuilders.standaloneSetup(tweetController).build();
        mapper = new ObjectMapper();
    }


    @Test
    public void testSuccessfulTweetCreated() throws Exception{
        mvc.perform(post("/user/kason/tweet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"message\":\"my first tweet\"}"))
                        .andExpect(status().is(201));
        verify(tweetService, times(1)).addTweet(eq("kason"), any(Tweet.class));
    }

    @Test
    public void testBadRequestWhenTweetMsgTooLong() throws Exception{
        mvc.perform(post("/user/kason/tweet")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"message\":\"a very long message a very long message a very long message" +
                        "a very long message a very long message a very long message" +
                        "a very long message a very long message a very long message" +
                        "a very long message a very long message a very long message" +
                        "a very long message a very long message a very long message" +
                        "a very long message a very long message a very long message\"}"))
                .andExpect(status().is(400));
        verify(tweetService, times(0)).addTweet(eq("kason"), any(Tweet.class));
    }

    @Test
    public void testGetAllTweetsForUser() throws Exception{
        when(tweetService.getUserTweets("kason")).thenReturn(Arrays.asList(new Tweet("msg 3"), new Tweet("msg 2"), new Tweet("msg 1")));
        MvcResult result = mvc.perform(get("/user/kason/tweet")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200)).andReturn();

        List<Tweet> tweets = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Tweet>>() {});
        assertThat(tweets.stream().filter(t -> t.getMessage().equals("msg 3")).findAny().isPresent(), is(true));
        assertThat(tweets.stream().filter(t -> t.getMessage().equals("msg 2")).findAny().isPresent(), is(true));
        assertThat(tweets.stream().filter(t -> t.getMessage().equals("msg 1")).findAny().isPresent(), is(true));

        verify(tweetService, times(1)).getUserTweets(eq("kason"));
    }

    @Test
    public void testGetUserTimeline() throws Exception{
        when(tweetService.getTimeLine("kason")).thenReturn(Arrays.asList(new Tweet("msg 3"), new Tweet("msg 2"), new Tweet("msg 1")));
        MvcResult result = mvc.perform(get("/user/kason/timeline")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200)).andReturn();

        List<Tweet> tweets = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Tweet>>() {});
        assertThat(tweets.stream().filter(t -> t.getMessage().equals("msg 3")).findAny().isPresent(), is(true));
        assertThat(tweets.stream().filter(t -> t.getMessage().equals("msg 2")).findAny().isPresent(), is(true));
        assertThat(tweets.stream().filter(t -> t.getMessage().equals("msg 1")).findAny().isPresent(), is(true));

        verify(tweetService, times(1)).getTimeLine(eq("kason"));
    }

}