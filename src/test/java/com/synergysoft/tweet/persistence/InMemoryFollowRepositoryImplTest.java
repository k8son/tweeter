package com.synergysoft.tweet.persistence;

import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Unit tests for InMemoryFollowRepositoryImpl.
 */
public class InMemoryFollowRepositoryImplTest {
    private InMemoryFollowRepositoryImpl inMemoryFollowRepository;

    @Before
    public void setup(){
        inMemoryFollowRepository = new InMemoryFollowRepositoryImpl();
    }

    @Test
    public void shouldCreateSingleFollowedUser(){
        inMemoryFollowRepository.createFollowedUser("john", "paul");
        Set<String> followingUsers = inMemoryFollowRepository.findUsersBeingFollowedByUserid("john");
        assertThat(followingUsers.size(), is(1));
        assertThat(followingUsers.contains("paul"), is(true));
    }

    @Test
    public void shouldCreateMultipleFollowedUser(){
        inMemoryFollowRepository.createFollowedUser("john", "paul");
        inMemoryFollowRepository.createFollowedUser("john", "steven");
        inMemoryFollowRepository.createFollowedUser("john", "bob");
        inMemoryFollowRepository.createFollowedUser("bob", "paul");
        inMemoryFollowRepository.createFollowedUser("bob", "steven");

        Set<String> followingUsers = inMemoryFollowRepository.findUsersBeingFollowedByUserid("john");
        assertThat(followingUsers.size(), is(3));
        assertThat(followingUsers.contains("paul"), is(true));
        assertThat(followingUsers.contains("steven"), is(true));
        assertThat(followingUsers.contains("bob"), is(true));
    }
}