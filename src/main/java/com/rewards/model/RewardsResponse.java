package com.rewards.model;

import java.util.Map;

public class RewardsResponse {
    private String customerId;
    private Map<String, Integer> monthlyPoints;
    private int totalPoints;

    public String getCustomerId() { 
    	return customerId; 
    	}
    public void setCustomerId(String customerId) { 
    	this.customerId = customerId; 
    	}
    public Map<String, Integer> getMonthlyPoints() { 
    	return monthlyPoints; 
    	}
    public void setMonthlyPoints(Map<String, Integer> monthlyPoints) { 
    	this.monthlyPoints = monthlyPoints; 
    	}
    public int getTotalPoints() { 
    	return totalPoints; 
    	}
    public void setTotalPoints(int totalPoints) { 
    	this.totalPoints = totalPoints; 
    	}
}
