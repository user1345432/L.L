package org.example;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Sprite extends Rectangle {
    public String type;
    public Boolean isDead;

    public Sprite(String type, Integer w, Integer h, Color color, Integer x, Integer y)
    {
        super(w, h, color);
        this.type = type;
        this.isDead = false;
        this.setTranslateX(x);
        this.setTranslateY(y);
    }

    public void moveUp()
    {
        if(this.type.equals("player"))
        {
            if(this.getTranslateY()< 550)
            {
                setTranslateY(getTranslateY() - 5);
            }
        }
        else  {
            setTranslateY(getTranslateY() - 5);
        }
    }


    public void moveDown()
    {
        if(this.type.equals("player"))
        {
            if(this.getTranslateY()< 550)
            {
                setTranslateY(getTranslateY() + 5);
            }
        }
        else {
            setTranslateY(getTranslateY() + 5);
        }
    }

    public void moveRight()
    {
        setTranslateX(getTranslateX() + 5);
    }

    public void moveLeft()
    {
        setTranslateX(getTranslateX() - 5);
    }
}
