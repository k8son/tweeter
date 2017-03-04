package com.synergysoft.tweet.web;

import com.synergysoft.tweet.service.TweetService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * Controller providing endpoints servicing user follow requests.
 */
@RestController
@RequestMapping(value="/user")
public class FollowController {
    private final TweetService tweetService;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public FollowController(TweetService twitterService) {
        this.tweetService = twitterService;
    }

    // Add a user to be followed
    @ApiOperation(value = "Follow user", notes = "Allows a user to follow another user")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "id of user who is following", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "followingUserId", value = "id of user being followed", required = true, dataType = "string", paramType = "path")
    })
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Success")})

    @RequestMapping(value = "/{userId}/follow/{followingUserId}", method = RequestMethod.POST)
    @ResponseStatus(value= HttpStatus.CREATED)
    public void addFollow(@PathVariable String userId, @PathVariable String followingUserId){
        tweetService.followUser(userId, followingUserId);
    }

    // Get list of users being followed
    @ApiOperation(value = "Get all followed users", notes = "Returns the list of all users being followed",
            produces = "application/json", response = String.class, responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success")})
    @RequestMapping(value = "/{userId}/follow", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(value= HttpStatus.OK)
    public Set<String> getFollowedUsers(@PathVariable String userId){
        return tweetService.getUsersBeingFollowed(userId);
    }
}
