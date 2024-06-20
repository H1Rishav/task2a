//package com.example.tomandjerry;
//
//import android.content.Context;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.graphics.Rect;
//import android.view.SurfaceHolder;
//import android.view.SurfaceView;
//import android.view.WindowManager;
//
//public class GameView extends SurfaceView implements Runnable {
//
//    int Sc_width,Sc_height;
//    Canvas canvas;
//    SurfaceHolder holder;
//    Jerry jerry;
//    Tom tom;
//    int left=10,right=40,top=300,bottom=400;
//    Paint stroke;
//
//
//    Obstacles snake,cheese,fence,block,stone;
//    public GameView(Context context) {
//        super(context);
//        holder=getHolder();
//        calculateHandW();
//        jerry=new Jerry();
//        tom=new Tom();
//        stroke=new Paint();
//        stroke.setColor(Color.BLUE);
//        //stroke.setStrokeWidth(5);
//        stroke.setStyle(Paint.Style.FILL);
//    }
//
//    @Override
//    public void run() {
//    canvas=holder.lockCanvas();
//    //we write the code for the game
//       // onCollision();
//        Rect r=new Rect();
//        r .set(left,top ,right,bottom);
//        canvas.drawRect(r,stroke);
//    holder.unlockCanvasAndPost(canvas);
//    top+=2;bottom+=2;
//
//    }
//
//    private void onCollision(Obstacles obstacle) {
//
//    }
//
//    void calculateHandW()
//    {
//        Sc_height=getResources().getDisplayMetrics().heightPixels;
//        Sc_width=getResources().getDisplayMetrics().widthPixels;
//
//    }
//
//}


package com.example.tomandjerry;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView implements  Runnable {

     int Sc_width, Sc_height;
     Canvas canvas;
     SurfaceHolder holder;
    Thread gameThread;
     boolean running;
     Jerry jerry;
     Tom tom;
     int left=100, right = left+80, top , bottom ,bgtop=0,bgtop1=0;
    int ix,fx,iy,fy;
    private Paint stroke;
    long score;
    GameActivity i;
    TextView pause;
    Bitmap road,rroad,sc;
    AllBitMaps bitMaps;
    int diffheight;

    List<Obstacles> obs_list;
    List<Traps> traps_list;

    int object_velocity=10,jerry_velocity=10,tom_velocity=20;

    private Obstacles snake, cheese, fence, block, stone;

    public GameView(Context context) {
        super(context);
        init(context);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    //when you have created a custom view it is necessary to use
    //Attributeset as all the attributes such as height ,width of the xml file are passed to the view's constructor

//    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        init(context);
//    }

    private void init(Context context) {
        holder = getHolder();
        //holder.addCallback(this);
        calculateHandW();
        top=Sc_height-100;
        bottom=Sc_height;
        jerry = new Jerry();
        tom = new Tom();
        stroke = new Paint();
        stroke.setColor(Color.BLUE);
        stroke.setStyle(Paint.Style.FILL);
        gameThread = new Thread(this);
        running = true;
        gameThread.start();
        road= BitmapFactory.decodeResource(getResources(),R.drawable.roadimg);
        rroad=BitmapFactory.decodeResource(getResources(),R.drawable.roadimg);
        sc=BitmapFactory.decodeResource(getResources(),R.drawable.jerryrunning);
        jerry.img=BitmapFactory.decodeResource(getResources(),R.drawable.jerryrunning);

        obs_list=new ArrayList<>();
        traps_list=new ArrayList<>();
        bitMaps=new AllBitMaps(context);

    }

    @Override
    public void run() {

        while (running) {
            if (!holder.getSurface().isValid()) {
                continue;
            }

            canvas = holder.lockCanvas();
            if (canvas != null) {
                synchronized (holder) {
                    update();
                    updateBackground();
                    drawGame(canvas);
                    //will have to do this in a random time from
                    drawingObstacles();
                }
                holder.unlockCanvasAndPost(canvas);
            }

            try {
                Thread.sleep(16); // ~60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

     void updateBackground() {
         Bitmap road1=Bitmap.createScaledBitmap(road,Sc_width,(road.getHeight()/road.getWidth())*Sc_width,true);
//         if(-bgtop+Sc_height==road1.getHeight()) {
//             bgtop=Sc_height;
//         if(jerry.img!=null)
//             Toast.makeText(getContext(), "Not empty", Toast.LENGTH_SHORT).show();
             canvas.drawBitmap(road1,0,bgtop,null);
             //will have to make her go up and down
//         }
//         else
//
//         canvas.drawBitmap(road1,0,bgtop,null);
//         bgtop-=8;


    }

    private void update() {
        // Update game state (e.g., position of game objects)
        //keep track of time increase;
        if(top>=Sc_height*4/7) {
            top -= jerry_velocity;
            bottom -= jerry_velocity;
        }


    }

    private void drawGame(Canvas canvas) {
        // Clear the screen
        // Draw game objects
// bitmap is an immutable data type thus the dimensions can't be changed once a bitmap is created
        canvas.drawBitmap(jerry.getImg(), jerry.getX(),jerry.getY(),null);


    }

    void drawingObstacles()
    {
        //finding the number of obstacles/traps to be drawn
        Random random=new Random();
        int no_of_obstacles,no_of_traps;
        String track_name;
        //we want making 3 tracks full have a very low chance


        no_of_obstacles = random.nextInt(4);//0,1,2,3

        no_of_traps=random.nextInt(3-no_of_obstacles+1);

        if(no_of_obstacles==3)
            specialCase(no_of_obstacles);
        else
            objectCreation(no_of_obstacles,no_of_traps);

    }
//after object creationi,we need to access the avaialable objects,increase their index by a given value and remove the
    //objects that are out of the page
    void objectCreation(int obstacles,int traps)
    {
        Bitmap b;
        List<String> array=new ArrayList<>();
        Random random=new Random();
        //for the obstacles we will select the bitmap then their track
        for(int i=0;i<obstacles;i++)
        {
            String track;
            int flag=0;
            //we have 3 types of obstacles
            //we select random bitmaps and on top of that their tracks should be different
            //so we store the track already used in a string array
            switch(random.nextInt(3))
            {

                case 0:
                    //b=img1;
                     flag=0;
                    while(true) {
                         track = togetRandomTrack();
                        for (String element:array) {
                            if (track == element){
                                flag=1;
                                break;
                            }
//ill use array list better than array as it is dynamic in nature and can store objects more easily
                        }
                        if(flag==0) {
                            array.add(track);
                            break;
                        }
                    }
                    //track is set
                    //now the random bitmap generator
                    b=bitMaps.getRock();
                    obs_list.add(new Obstacles(togetX(track,b),0-b.getHeight(),b,track,0));
                    break;
                case 1:
                   // flag=0;
                    while(true) {
                        track = togetRandomTrack();
                        for (String element:array) {
                            if (track == element){
                                flag=1;
                                break;
                            }
//ill use array list better than array as it is dynamic in nature and can store objects more easily
                        }
                        if(flag==0) {
                            array.add(track);
                            break;
                        }
                    }
                    //track is set
                    //now the random bitmap generator
                    b=bitMaps.getHurdle();
                    obs_list.add(new Obstacles(togetX(track,b),0-b.getHeight(),b,track,1));
                    break;

                case 2:
                    while(true) {
                        track = togetRandomTrack();
                        for (String element:array) {
                            if (track == element){
                                flag=1;
                                break;
                            }
//ill use array list better than array as it is dynamic in nature and can store objects more easily


                        }
                        if(flag==0) {
                            array.add(track);
                            break;
                        }
                    }
                    //track is set
                    //now the random bitmap generator
                    b=bitMaps.getSnake();
                    obs_list.add(new Obstacles(togetX(track,b),0-b.getHeight(),b,track,2));
                    break;

            }



        }
        for(int i=0;i<traps;i++)
        {
            String track;
            int flag=0;
            //we have 3 types of traps
            //we select random bitmaps and on top of that their tracks should be different
            //so we store the track already used in a string array
            switch(random.nextInt(3))
            {

                case 0:
                    //b=img1;
                    flag=0;
                    while(true) {
                        track = togetRandomTrack();
                        for (String element:array) {
                            if (track == element){
                                flag=1;
                                break;
                            }
//ill use array list better than array as it is dynamic in nature and can store objects more easily
                        }
                        if(flag==0) {
                            array.add(track);
                            break;
                        }
                    }
                    //track is set
                    //now the random bitmap generator
                    b=bitMaps.getMushroom();
                    traps_list.add(new Traps(togetX(track,b),0-b.getHeight(),b,track,random.nextInt(3)));
                    //for each number(in the last argument) a punishment/reward will be selected
                    break;
                case 1:
                    // flag=0;
                    while(true) {
                        track = togetRandomTrack();
                        for (String element:array) {
                            if (track == element){
                                flag=1;
                                break;
                            }
//ill use array list better than array as it is dynamic in nature and can store objects more easily
                        }
                        if(flag==0) {
                            array.add(track);
                            break;
                        }
                    }
                    //track is set
                    //now the random bitmap generator
                    b=bitMaps.getPuddle();
                    traps_list.add(new Traps(togetX(track,b),0-b.getHeight(),b,track,random.nextInt(3)));
                    break;

                case 2:
                    while(true) {
                        track = togetRandomTrack();
                        for (String element:array) {
                            if (track == element){
                                flag=1;
                                break;
                            }
//ill use array list better than array as it is dynamic in nature and can store objects more easily


                        }
                        if(flag==0) {
                            array.add(track);
                            break;
                        }
                    }
                    //track is set
                    //now the random bitmap generator
                    b=bitMaps.getGrass();
                    traps_list.add(new Traps(togetX(track,b),0-b.getHeight(),b,track,random.nextInt(3)));
                    break;

            }



        }
        objectUpdation();


    }
    int togetX(String track,Bitmap b)
    {
        switch (track)
        {
            case "left":
                return (Sc_width/3)*(1/2)-b.getWidth()/2;

            case "middle":
                return (Sc_width/2)-b.getWidth()/2;

            case "right":
                return(Sc_width)*5/6-b.getWidth()/2;
        }

        return 0;
    }
    void objectUpdation()

    {
        objectDrawing();
        List<Obstacles> remove=new ArrayList<>();
        List<Traps> removeT=new ArrayList<>();
//iterate through all the obstacles and traps and powerups
        //increase their indexes
        for(Obstacles obstacle:obs_list)
        {
            onCollisionObs(obstacle);
            if(obstacle.getY()>Sc_height||jerry.getY()==obstacle.getY())
            {
                remove.add(obstacle);
            }

            obstacle.setY(obstacle.getY()+object_velocity);

        }

        for(Traps traps:traps_list)
        {
            onCollisionTraps(traps);
            if(traps.getY()>Sc_height)
            {
                removeT.add(traps);
            }
            traps.setY(traps.getY()+10);

        }
        obs_list.remove(remove);
        traps_list.remove(removeT);


    }

    private void onCollisionTraps(Traps traps) {
        if(jerry.isJumping)
        {
            //no effect
        }
        else {

        }

    }

    private void onCollisionObs(Obstacles obstacle) {
        // Handle collision
        //hurdles  jumpable
        if(obstacle.getType()==0)
        {
            //doesnt matter if jerry is jumping it will cause damage
        }
        else
        {
            //if jerry is jumping then no impact

        }


    }

    void objectDrawing()
    {
        for(Obstacles obstacle:obs_list)
        {
            canvas.drawBitmap(obstacle.getImg(),obstacle.getX(),obstacle.getY(),null);
        }
        for(Traps traps:traps_list)
        {
            canvas.drawBitmap(traps.getImg(),traps.getX(),traps.getY(),null);
        }
    }


    String togetRandomTrack()
    {
        Random random=new Random();
        switch(random.nextInt(3))
        {
            case 0:
                return "left";

            case 1:
                return "middle";

            case 2:
                return "right";
        }

        return null;
    }

    void specialCase(int obstacles)
    {
        //if all obstacles
        if(obstacles==3)
        {

        }

    }



    private void calculateHandW() {
        Sc_height = getResources().getDisplayMetrics().heightPixels;
        Sc_width = getResources().getDisplayMetrics().widthPixels;
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x=(int)event.getX();
        int y=(int)event.getY();

        if(event.getAction()==MotionEvent.ACTION_DOWN) {
            ix=x;
            iy=y;
               // Toast.makeText(getContext(), "Touched!", Toast.LENGTH_SHORT).show();
        }
        if(event.getAction()==MotionEvent.ACTION_MOVE)
        {
            fx=(int)event.getX();
            fy=(int)event.getY();
//                left=Sc_width-180;
        }
        if(event.getAction()==MotionEvent.ACTION_UP)
        {
            if(fx-ix>0) {
                if(jerry.getTrack()=="left"){
                    left = Sc_width / 2 - jerry.getImg().getWidth()/2;
                    jerry.setTrack("middle");

                }
                else if(jerry.getTrack()=="middle")
                {
                    left=Sc_width-100-jerry.getImg().getWidth();
                    jerry.setTrack("right");
                }

            }

            else if(fx-ix<0)
            {
                if(jerry.getTrack()=="middle"){
                    left = 100;
                    jerry.setTrack("left");
                }
                else if(jerry.getTrack()=="right")
                {
                    left=Sc_width / 2 - jerry.getImg().getWidth()/2;;
                    jerry.setTrack("middle");
                }


            }
        }

//        if(fy-iy>0)
//        {
//            jerry.isJumping=true;
//            jumpingActionOnJerry();
//        }



        return true;
    }
    void jumpingActionOnJerry()
    {

    }
//    void onPause(){
//        pause.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Dialog d=new Dialog(getContext());//we set the context of the current activity
//                d.setContentView(R.layout.pause_dialog);
//                d.show();
//
//            }
//        });
//    }

    public void sleep()
    {

    }

}
