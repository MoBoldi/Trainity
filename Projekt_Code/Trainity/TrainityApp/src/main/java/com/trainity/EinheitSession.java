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
   public final class EinheitSession {

    public static EinheitSession instanceE;
    
    public int einheitId;
    

    public EinheitSession(int userId) {
        this.einheitId = userId;
    }

    public static EinheitSession getInstace(int userId) {
        if(instanceE == null || userId == -1) {
            instanceE = new EinheitSession(userId);
        }
        return instanceE;
    }

    public int getUserID() {
        return einheitId;
    }


    public void cleanUserSession() {
       einheitId = -1;
    }

    @Override
    public String toString() {
        return einheitId+"";
    }
   }