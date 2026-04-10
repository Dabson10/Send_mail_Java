package org.github.dabson10.sendmail.entity;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class TimeStamp {
    private String message;
    public TimeStamp(String message){
        this.message = message;
    }
    public TimeStamp(){}
}
