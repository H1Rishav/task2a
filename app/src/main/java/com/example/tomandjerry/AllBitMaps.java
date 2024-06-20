package com.example.tomandjerry;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class AllBitMaps {
    //obstacles
    Bitmap rock1,snake1,hurdle1;
    Context context;
    Bitmap mushroom1,puddle1,grass1;//traps

    //powerups
    AllBitMaps(Context context)
    {
        this.context=context;
        rock1=BitmapFactory.decodeResource(context.getResources(),R.drawable.stone);
        snake1=BitmapFactory.decodeResource(context.getResources(),R.drawable.stone);
        hurdle1=BitmapFactory.decodeResource(context.getResources(),R.drawable.stone);




        mushroom1=BitmapFactory.decodeResource(context.getResources(),R.drawable.mushroom);
        puddle1=BitmapFactory.decodeResource(context.getResources(),R.drawable.puddle);
        grass1=BitmapFactory.decodeResource(context.getResources(),R.drawable.grass);
         width = context.getResources().getDisplayMetrics().widthPixels / 3 - 100;



    }
    int width = context.getResources().getDisplayMetrics().widthPixels / 3 - 100;
    Bitmap rock=Bitmap.createScaledBitmap(rock1,width
            ,(rock1.getHeight() * width) / rock1.getWidth()
            ,true);

    Bitmap snake=Bitmap.createScaledBitmap(snake1,width
            ,(snake1.getHeight() * width) / snake1.getWidth()
            ,true);

    Bitmap hurdle=Bitmap.createScaledBitmap(hurdle1,width
            ,(hurdle1.getHeight() * width) / hurdle1.getWidth()
            ,true);
    Bitmap mushroom=Bitmap.createScaledBitmap(mushroom1,width
            ,(mushroom1.getHeight() * width) / mushroom1.getWidth()
            ,true);

    Bitmap puddle=Bitmap.createScaledBitmap(puddle1,width
            ,(puddle1.getHeight() * width) / puddle1.getWidth()
            ,true);

    Bitmap grass=Bitmap.createScaledBitmap(grass1,width
            ,(grass1.getHeight() * width) /grass1.getWidth()
            ,true);

    public Bitmap getMushroom() {
        return mushroom;
    }

    public Bitmap getGrass() {
        return grass;
    }

    public Bitmap getHurdle() {
        return hurdle;
    }

    public Bitmap getPuddle() {
        return puddle;
    }

    public Bitmap getRock() {
        return rock;
    }

    public Bitmap getSnake() {
        return snake;
    }
}
