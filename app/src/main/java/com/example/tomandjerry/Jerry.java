package com.example.tomandjerry;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Jerry {
    private int lives;
     Bitmap img;
    private int x,y;
    private String track;
    boolean isJumping;

    Jerry()
    {
       // img= BitmapFactory.decodeResource(Resources.getSystem(),R.drawable.jerryrunning);
        //wrong declaration ...why?
        lives=1;
        track="left";
        isJumping=false;
    }


    public Bitmap getImg() {
        Bitmap ig=Bitmap.createScaledBitmap(img,200,200,true);
        return ig;
    }

    public int getLives() {
        return lives;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }



    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public String getTrack() {
        return track;
    }

    public boolean getIsJumping() {
        return isJumping;
    }

    public void  setIsJumping(boolean isJumping) {
        this.isJumping=isJumping;
    }
}
