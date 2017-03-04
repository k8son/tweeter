package com.synergysoft.tweet.web;

import com.synergysoft.tweet.domain.Tweet;
import com.synergysoft.tweet.service.TweetService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Controller providing endpoints servicing Tweet requests.
 */
@RestController
@RequestMapping(value="/user")
public class TweetController {
    private final TweetService tweetService;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public TweetController(TweetService twitterService) {
        this.tweetService = twitterService;
    }

    // Add tweet
    @ApiOperation(value = "Add tweet", notes = "Allows the user to tweet a message", consumes = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "user id", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "tweet", value = "message text", required = true, paramType = "body")
    })
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Success"), @ApiResponse(code = 500, message = "Failure")})

    @RequestMapping(value = "/{userId}/tweet", method = RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(value= HttpStatus.CREATED)
    public void addTweet(@RequestBody @Valid Tweet tweet, @PathVariable String userId){
        LOGGER.info("Received request to add tweet for user {}. Tweet body: {}", userId, tweet);
        tweetService.addTweet(userId, tweet);
    }

    // Get User tweets
    @ApiOperation(value = "Get tweets", notes = "Returns the list of user tweets in reverse chronological order",
                    produces = "application/json", response = Tweet.class, responseContainer = "List")
    @ApiImplicitParams({@ApiImplicitParam(name = "userId", value = "user id", required = true, dataType = "string", paramType = "path")})
    @RequestMapping(value = "/{userId}/tweet", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(value= HttpStatus.OK)
    public List<Tweet> getTweet(@PathVariable String userId){
        LOGGER.info("Received request to get tweets for user {}", userId);
        return tweetService.getUserTweets(userId);
    }

    // Get User timeline
    @ApiOperation(value = "Get timeline", notes = "Returns the list of tweets from all users being followed in reverse chronological order",
            produces = "application/json", response = Tweet.class, responseContainer = "List")
    @ApiImplicitParams({@ApiImplicitParam(name = "userId", value = "user id", required = true, dataType = "string", paramType = "path")})
    @RequestMapping(value = "/{userId}/timeline", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(value= HttpStatus.OK)
    public List<Tweet> getTimeline(@PathVariable String userId){
        LOGGER.info("Received request to get timeline for user {}", userId);
        return tweetService.getTimeLine(userId);
    }
}
