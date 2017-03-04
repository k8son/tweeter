package com.synergysoft.tweet.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Value object to represent a simple tweet created by a user.
 */
@ApiModel("Tweet")
public class Tweet{
    @Size(max = 140)
    private String message;
    @JsonIgnore
    private LocalDateTime createdTime = LocalDateTime.now();

    @JsonCreator
    public Tweet(@JsonProperty("message") String message) {
        this.message = message;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tweet tweet = (Tweet) o;

        if (message != null ? !message.equals(tweet.message) : tweet.message != null) return false;
        return createdTime != null ? createdTime.equals(tweet.createdTime) : tweet.createdTime == null;
    }

    @Override
    public int hashCode() {
        int result = message != null ? message.hashCode() : 0;
        result = 31 * result + (createdTime != null ? createdTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "message='" + message + '\'' +
                ", createdTime=" + createdTime +
                '}';
    }
}
