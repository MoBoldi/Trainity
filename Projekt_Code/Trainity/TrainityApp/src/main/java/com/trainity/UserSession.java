/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trainity;


/**
 *
 * @author benjamingolic
 */
   public final class UserSession {

    public static UserSession instance;
    
    public int userId;
    

    public UserSession(int userId) {
        this.userId = userId;
    }

    public static UserSession getInstace(int userId) {
        if(instance == null) {
            instance = new UserSession(userId);
        }
        return instance;
    }

    public int getUserID() {
        return userId;
    }


    public void cleanUserSession() {
        userId = -1;
    }

    @Override
    public String toString() {
        return userId+"";
    }
}