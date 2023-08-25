package org.example;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.text.Font;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Main extends Application {

    Pane root = new Pane();
    Sprite player = new Sprite("player", 30, 40, Color.DARKGOLDENROD, 200, 500);
    int lives = 9;
    Text lives_text = new Text(380, 580,Integer.toString(lives));
    Font font = new Font("Serif", 25);


    private Parent createContent()
    {
        root.setPrefSize(400, 600);
        root.setStyle("-fx-background-color: #000020;");
        root.getChildren().add(player);

        Sprite enemy_1 = new Sprite("enemy", 40, 40, Color.ALICEBLUE, 100, 150);
        Sprite enemy_2 = new Sprite("enemy", 40, 40, Color.ALICEBLUE, 200, 150);
        Sprite enemy_3 = new Sprite("enemy", 40, 40, Color.ALICEBLUE, 300, 150);

        root.getChildren().add(enemy_1);
        root.getChildren().add(enemy_2);
        root.getChildren().add(enemy_3);

        lives_text.setFont(font);
        lives_text.setFill(Color.WHITE);
        root.getChildren().add(lives_text);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                update();
            }
        };
        timer.start();
        return root;
    }

    private List<Sprite> sprites()
    {
        List<Node> childrenList = new ArrayList<>();
        List<Sprite> spriteList = new ArrayList<>();

        childrenList = root.getChildren();

        for (Node node : childrenList)
        {
            if(node instanceof Sprite)
            {
                spriteList.add((Sprite) node);
            }
        }
        return spriteList;
    }

    private void update()
    {
        for (Sprite s: sprites())
        {
            switch (s.type)
            {
                case "rocket":
                    s.moveUp();

                    List<Sprite> enemies = new ArrayList<>();
                    enemies = sprites().stream().filter(new Predicate<Sprite>() {
                        @Override
                        public boolean test(Sprite sprite) {
                            return sprite.type.equals("enemy");
                        }
                    }).collect(Collectors.toList());

                    for (Sprite enemy : enemies)
                    {
                        if(s.getBoundsInParent().intersects(enemy.getBoundsInParent()))
                        {
                            s.isDead = true;
                            enemy.isDead = true;
                        }
                    }
                    break;

                case "enemy_rocket":
                    s.moveDown();
                    if(player.isDead == false)
                    {
                        if(s.getBoundsInParent().intersects(player.getBoundsInParent()))
                        {
                            s.isDead = true;
                            player.isDead = true;
                            lives -= 1;
                            lives_text.setText(Integer.toString(lives));
                        }
                    }

                    break;
                case "enemy":
                    if(Math.random() > 0.9f)
                    {
                        shoot(s);
                    }
                    break;
            }
            if(s.isDead)
            {
                root.getChildren().remove(s);
            }
        }

        int enemyNumber = 0;
        for(Sprite s: sprites())
        {
            if(s.type.equals("enemy"))
            {
                enemyNumber += 1;
            }
        }
        if(enemyNumber == 0)
        {
            Sprite enemy_1 = new Sprite("enemy", 40, 40, Color.ALICEBLUE, 100, 150);
            Sprite enemy_2 = new Sprite("enemy", 40, 40, Color.ALICEBLUE, 200, 150);
            Sprite enemy_3 = new Sprite("enemy", 40, 40, Color.ALICEBLUE, 300, 150);

            root.getChildren().add(enemy_1);
            root.getChildren().add(enemy_2);
            root.getChildren().add(enemy_3);
        }
    }

    private void shoot(Sprite who)
    {
        switch (who.type)
        {
            case "player":
                Sprite rocket = new Sprite("rocket", 5, 10, Color.HONEYDEW,
                        (int)who.getTranslateX(), (int)who.getTranslateY());
                root.getChildren().add(rocket);
                break;
            case "enemy":
                Sprite enemy_rocket = new Sprite("enemy_rocket", 5, 10, Color.HONEYDEW,
                        (int)who.getTranslateX(), (int)who.getTranslateY());
                root.getChildren().add(enemy_rocket);
                break;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(createContent());

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode())
                {
                    case W:
                        player.moveUp();
                        break;
                    case S:
                        player.moveDown();
                        break;
                    case A:
                        player.moveLeft();
                        break;
                    case D:
                        player.moveRight();
                        break;
                    case SHIFT:
                        shoot(player);
                        break;
                    case E:
                        if (lives>0)
                        {
                            player.isDead=false;
                            root.getChildren().add(player);
                        }
                        break;
                    default:
                        break;
                }
            }
        });

        stage.setScene(scene);
        stage.show();
    }
}
