package com.LibreGainz.gainzserver.model;

/**
 * @author Evan L
 * This object manages time
 */
public class TimeObj{
    public int minutes;
    public int seconds;
    public int hours;
    public TimeObj(int minutes, int seconds) 
    {
    	
    	if (seconds < 0) {
    		seconds = seconds * -1;
    		//throw new IllegalArgumentException("Seconds cannot be less than 0");
    	}
    	if (minutes < 0) {
    		minutes = minutes * -1;
    		//throw new IllegalArgumentException("Minutes cannot be less than 0");
    	}
    	
    	if (seconds > 59){
    		minutes ++;
    		seconds = seconds - 60;
    	}
    	
        this.minutes = minutes;
        this.seconds = seconds;
     
    }
    public TimeObj(int seconds) {
    	this.seconds = seconds;
    	if (seconds > 59) {
    		minutes ++;
    		seconds = seconds -60;
    	}
    }
    
    	public TimeObj(int hours, int minutes, int seconds) {
    		this.hours = hours;
    		this.minutes = minutes;
    		this.seconds = seconds;
    	 	
        	if (seconds < 0) {
        		seconds = seconds * -1;
        		//throw new IllegalArgumentException("Seconds cannot be less than 0");
        	}
        	if (minutes < 0) {
        		minutes = minutes * -1;
        		//throw new IllegalArgumentException("Minutes cannot be less than 0");
        	}
        	
        	if (seconds > 59){
        		minutes ++;
        		seconds = seconds - 60;
        	}
        	if (minutes > 59) {
        		hours ++;
        		minutes = minutes - 60;
        	}
    		
    			
    	}
    
    
    /**
     * @return timeStr 
     */
    public String toString(){
    	
    	if (seconds < 10) {
    		return minutes + ":0" + seconds;
    	}
    	
    	else
        return minutes + ":" + seconds;
    
    	}
    
}

