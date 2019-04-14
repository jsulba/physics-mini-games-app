package physicsminigames.mirrorsoflife;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.paint.ImagePattern;

public class AssetManager {

    static private Background backgroundImage = null;
    //will have 3 different backgrounds, for 3 different levels. maybe

    static private ImagePattern lightPhoton = null;
    static private ImagePattern lightSource = null;
    static private ImagePattern lightTarget = null;
    static private Image helpMenu1 = null;
    static private Image helpMenu2 = null;
    static private Image helpMenu3 = null;
    static private ArrayList<ImagePattern> objects = new ArrayList<>();
    static private ArrayList<Background> objectBackground = new ArrayList<>();
    static private ImagePattern wall = null;

    static private Media backgroundMusic = null;

    static int selectedObject = 0;
    static int levelsPassed = 0;

    static private String fileURL(String relativePath) {
        return new File(relativePath).toURI().toString();
    }

    static public void preloadAllAssets() {
        // Preload all images
        Image background = new Image(fileURL("./assets/mirrors_of_life/images/other/background.jpg"));
        backgroundImage = new Background(
                new BackgroundImage(background,
                        BackgroundRepeat.REPEAT,
                        BackgroundRepeat.REPEAT,
                        BackgroundPosition.CENTER,
                        BackgroundSize.DEFAULT));
        Image mirror = new Image(fileURL("./assets/mirrors_of_life/images/objects/mirror.png"), 30, 80, false, false);
        Image lens = new Image(fileURL("./assets/mirrors_of_life/images/objects/lens.png"), 5, 70, false, false);
        Image diamond = new Image(fileURL("./assets/mirrors_of_life/images/objects/diamond.png"), 50, 50, false, false);
        Image amber = new Image(fileURL("./assets/mirrors_of_life/images/objects/amber.png"), 50, 50, false, false);

        objectBackground.add(new Background(new BackgroundImage(mirror,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT)));
        objectBackground.add(new Background(new BackgroundImage(lens,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT)));
        objectBackground.add(new Background(new BackgroundImage(diamond,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT)));
        objectBackground.add(new Background(new BackgroundImage(amber,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT)));

        wall = new ImagePattern(new Image(fileURL("./assets/mirrors_of_life/images/obstacle/wall.png")));
        lightPhoton = new ImagePattern(new Image(fileURL("./assets/mirrors_of_life/images/other/photon.png")));
        lightSource = new ImagePattern(new Image(fileURL("./assets/mirrors_of_life/images/other/source.png")));
        lightTarget = new ImagePattern(new Image(fileURL("./assets/mirrors_of_life/images/other/target.png")));
        objects.add(new ImagePattern(new Image(fileURL("./assets/mirrors_of_life/images/objects/mirror.png"))));
        objects.add(new ImagePattern(new Image(fileURL("./assets/mirrors_of_life/images/objects/lens.png"))));
        objects.add(new ImagePattern(new Image(fileURL("./assets/mirrors_of_life/images/objects/diamond.png"))));
        objects.add(new ImagePattern(new Image(fileURL("./assets/mirrors_of_life/images/objects/amber.png"))));

        backgroundMusic = new Media(fileURL("./assets/mirrors_of_life/sound/background.mp3"));

        helpMenu1 = new Image(fileURL("./assets/mirrors_of_life/images/help_menu/top.PNG"));
        helpMenu2 = new Image(fileURL("./assets/mirrors_of_life/images/help_menu/2.PNG"));
        helpMenu3 = new Image(fileURL("./assets/mirrors_of_life/images/help_menu/3.PNG"));
    }

    static public Background getObjectBackground(int index) {
        return objectBackground.get(index);
    }

    static public Background getBackgroundImage() {
        return backgroundImage;
    }

    static public Media getBackgroundMusic() {
        return backgroundMusic;
    }

    static public ImagePattern getWallImage() {
        return wall;
    }

    static public ImagePattern getLightPhotonImage() {
        return lightPhoton;
    }

    static public ImagePattern getLightSourceImage() {
        return lightSource;
    }

    static public ImagePattern getLightTargetImage() {
        return lightTarget;
    }

    static public ImagePattern getMirrorImage() {
        return objects.get(0);
    }

    static public ImagePattern getLensImage() {
        return objects.get(1);
    }

    static public ImagePattern getDiamondImage() {
        return objects.get(2);
    }

    static public ImagePattern getAmberImage() {
        return objects.get(3);
    }

    static public Image getHelpMenu1() {
        return helpMenu1;
    }

    static public Image getHelpMenu2() {
        return helpMenu2;
    }

    static public Image getHelpMenu3() {
        return helpMenu3;
    }

}
