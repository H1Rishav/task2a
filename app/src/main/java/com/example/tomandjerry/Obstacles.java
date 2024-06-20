package com.example.tomandjerry;

import android.graphics.Bitmap;

public class Obstacles {
    //we have stone,hurdle,snake
    boolean does_damage;
    Bitmap img;
    String dodged_move;

    int x,y,type;
    String track;

    //here type 0-rock ,1-hurdle,2-snake
     Obstacles(int x,int y,Bitmap img,String track,int type)
    {
        this.x=x;
        this.y=y;
        this.img=img;
        this.track=track;
        this.type=type;
          does_damage=true;
    }

    public void setDoes_damage(boolean does_damage) {
        this.does_damage = does_damage;
    }
    public boolean getDoes_damage()
    {
        return does_damage;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public String getTrack() {
        return track;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public Bitmap getImg() {
        return img;
    }

    public String getDodged_move() {
        return dodged_move;
    }

    public int getType() {
        return type;
    }
}
