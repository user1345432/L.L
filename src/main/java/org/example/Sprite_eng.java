package org.example;

import javafx.scene.image.Image;

public class Sprite_eng {

   public Integer width;
    public Integer height;
    public Integer posX;
    public Integer posY;

    public Image img;

    public Sprite_eng(Integer width, Integer height, Integer posX, Integer posY, Image img) {
        this.width = width;
        this.height = height;
        this.posX = posX;
        this.posY = posY;
        this.img = img;
    }
}
