package com.synergysoft.tweet.web;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.synergysoft.tweet.service.TweetService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest
public class FollowControllerTest {
    private MockMvc mvc;
    @Mock
    private TweetService tweetService;
    @InjectMocks
    private FollowController followController;

    private ObjectMapper mapper;

    @Before
    public void setup(){
        mvc = MockMvcBuilders.standaloneSetup(followController).build();
        mapper = new ObjectMapper();
    }

    @Test
    public void testSuccessfulFollowRequest() throws Exception{
        mvc.perform(post("/user/kason/follow/bob")).andExpect(status().is(201));
        verify(tweetService, times(1)).followUser("kason", "bob");
    }

    @Test
    public void testGetListOfFollowedUsers() throws Exception{
        when(tweetService.getUsersBeingFollowed("kason")).thenReturn(new HashSet<>(Arrays.asList("paul", "bob")));
        MvcResult result = mvc.perform(get("/user/kason/follow")).andExpect(status().is(200)).andReturn();
        List<String> users = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<String>>() {});
        assertThat(users.size(), is(2));
        assertThat(users.contains("paul"), is(true));
        assertThat(users.contains("bob"), is(true));

        verify(tweetService, times(1)).getUsersBeingFollowed("kason");
    }

    @Test
    public void testEmptyListWhenNoUsersBeingFollowed() throws Exception{
        when(tweetService.getUsersBeingFollowed("kason")).thenReturn(new HashSet<>());
        MvcResult result = mvc.perform(get("/user/kason/follow")).andExpect(status().is(200)).andReturn();
        List<String> users = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<String>>() {});
        assertThat(users.size(), is(0));
        verify(tweetService, times(1)).getUsersBeingFollowed("kason");
    }

}