package com.example.meritstack.event;

import com.example.meritstack.model.User;
import org.springframework.context.ApplicationEvent;

public class OTPEvent extends ApplicationEvent  {

    private User user;

    public OTPEvent(
      User user) {
        super(user);
        
        this.user = user;
    }

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
