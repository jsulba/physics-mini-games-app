/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physicsminigames;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import physicsminigames.mirrorsoflife.*;

public class FXMLMiniGame3Controller implements Initializable {

    //GUI controls
    //buttons
    @FXML
    Button startBtn;
    @FXML
    Button quitBtn;
    @FXML
    Button helpBtn;
    @FXML
    Button resetBtn;
    @FXML
    Button nextLevelBtn;
    @FXML
    Button restartGameBtn;
    @FXML
    Button mirrorBtn;
    @FXML
    Button lensBtn;
    @FXML
    Button diamondBtn;
    @FXML
    Button amberBtn;
    //slider
    @FXML
    Slider chosenFocalLength;

    //other GUI elements
    //labels
    @FXML
    Label focalLength = new Label();
    Label mirrorLabel = new Label();
    Label lensLabel = new Label();
    Label amberLabel = new Label();
    Label diamondLabel = new Label();
    @FXML
    Label levelLabel;
    @FXML
    Label statusLabel;
    //pane
    @FXML
    AnchorPane pane;

    //Game Objects
    LightPhoton photon = null;
    LightSource source = null;
    LightTarget target = null;
    Amber amber = null;
    Diamond diamond = null;
    Lens lens = null;
    Mirror mirror = null;

    //Variable to keep track of the photon's initial velocity
    private Vector2D initialPhotonVelocity = null;

    //Arrays to keep track of different game objects
    private ArrayList<Wall> obstacles = new ArrayList<Wall>();
    private ArrayList<Mirror> mirrorsArray = new ArrayList<Mirror>();
    private ArrayList<Lens> lensArray = new ArrayList<Lens>();
    private ArrayList<Diamond> diamondArray = new ArrayList<Diamond>();
    private ArrayList<Amber> amberArray = new ArrayList<Amber>();

    //Variables to keep track of player input
    private static int objectChosen;
    private int objectsChosenAmount = 0;

    //Variable to keep track of game progress
    private boolean isWon = false;
    private int levelsPassed = 0;

    //Other variables
    private static double pickedFocalLength = 40;
    private static double xCoord = 0.0;
    private static double yCoord = 0.0;
    private double lastFrameTime = 0.0;
    private double distanceTravelled = 0;
    private static MediaPlayer mediaPlayer;

    //Methods for adding and removing to/from pane
    public void addToPane(Node node) {
        pane.getChildren().add(node);
    }

    public void removeFromPane(Node node) {
        pane.getChildren().remove(node);
    }

    //Action events for the buttons
    public void handleStartBtn(ActionEvent event) {
        
        if (photon.getVelocity().getX()==0 && photon.getVelocity().getY()==0)
        {photon.setVelocity(new Vector2D(150, 0));
        distanceTravelled = 0;

        for (Diamond x : diamondArray) {
            x.resetCalcCondition();
        }

        for (Amber x : amberArray) {
            x.resetCalcCondition();
        }

        for (Lens x : lensArray) {
            x.resetCalcCondition();
        }
        
        statusLabel.setText ("Pick an object and place it on the screen");}
        
        else
        {
            statusLabel.setText(("Wait until the photon stops moving!"));
        }
    }

    public void handleNextLevelBtn(ActionEvent event) {
        ++levelsPassed;
        objectChosen = 0;
        //remove inputs
        removeObjectsPlaced();
        resetFocalLength();
        //remove obstacles
        removeWalls();
        obstacles.clear();
        setUpLevel(levelsPassed);
    }

    public void handleRestartGame(ActionEvent event) {
        levelsPassed = 0;
        objectChosen = 0;
        //remove inputs
        removeObjectsPlaced();
        resetFocalLength();
        //remove obstacles
        removeWalls();
        obstacles.clear();
        setUpLevel(levelsPassed);
    }

    public void handleQuitBtn(ActionEvent event) {
        ((Stage) pane.getScene().getWindow()).close();
        mediaPlayer.stop();
    }

    public void handleResetBtn(ActionEvent event) {
        objectChosen = 0;
        photon.setVelocity(new Vector2D(0, 0));
        removeObjectsPlaced();
        resetFocalLength();
        photon.setPosition(new Vector2D(source.getPosition().getX() + 20, source.getPosition().getY()));
    }

    //Handle user picked objects
    public void handleMirror(ActionEvent event) {
        statusLabel.setText("Place the object anywhere on the screen");
        objectChosen = 1;
    }

    public void handleLens(ActionEvent event) {
        statusLabel.setText("Place the object anywhere on the screen");
        objectChosen = 2;
    }

    public void handleDiamond(ActionEvent event) {
        statusLabel.setText("Place the object anywhere on the screen");
        objectChosen = 3;
    }

    public void handleAmber(ActionEvent event) {
        statusLabel.setText("Place the object anywhere on the screen");
        objectChosen = 4;
    }

    //Setter for the focal length
    static public void setFocalLength(double f) {
        pickedFocalLength = f;
    }

    //Accessor for the focal length
    static public double getFocalLength() {
        return pickedFocalLength;
    }

    //Method to remove all obstacles
    public void removeWalls() {
        for (Wall x : obstacles) {
            removeFromPane(x.getRectangle());
        }
    }

    //Method to reset the focal length back to its initial value
    public void resetFocalLength() {
        setFocalLength(40.0);
        chosenFocalLength.setValue(40.0);
        focalLength.setText("Focal Length: 40");
    }

    //Method to keep track of mouse coordinates
    public void MouseMoved(MouseEvent e) {
        xCoord = e.getX();
        yCoord = e.getY();
    }

    //Accessor for the xcomp
    static public double getXcomp() {
        return xCoord;
    }

    //Accessor for the ycomp
    static public double getYcomp() {
        return yCoord;
    }

    //Method to place the object chosen depending on the mouse location
    //set the max amount of objects chosen to 5
    public void dropObject(MouseEvent event) {
        //remind user to select an object
        if (objectChosen == 0) {
            statusLabel.setText("Select an object first!");
        }

        if (objectChosen == 1 && objectsChosenAmount < 5) {
            ++objectsChosenAmount;
            mirror = new Mirror(getXcomp(), getYcomp());
            addToPane(mirror.getRectangle());
            mirrorsArray.add(mirror);
            statusLabel.setText("Pick an object and place it on the screen");
        }
        if (objectChosen == 2 && objectsChosenAmount < 5) {
            ++objectsChosenAmount;
            lens = new Lens(getXcomp(), getYcomp());
            addToPane(lens.getRectangle());
            lensArray.add(lens);
            statusLabel.setText("Pick an object and place it on the screen");
        }
        if (objectChosen == 3 && objectsChosenAmount < 5) {
            ++objectsChosenAmount;
            diamond = new Diamond(getXcomp(), getYcomp());
            addToPane(diamond.getCircle());
            diamondArray.add(diamond);
            statusLabel.setText("Pick an object and place it on the screen");
        }
        if (objectChosen == 4 && objectsChosenAmount < 5) {
            ++objectsChosenAmount;
            amber = new Amber(getXcomp(), getYcomp());
            addToPane(amber.getCircle());
            amberArray.add(amber);
            statusLabel.setText("Pick an object and place it on the screen");
        }

        if (objectsChosenAmount == 5) {
            statusLabel.setText("Max amount of objects reached! Try resetting the objects");
        }
    }

    //Method to remove all objects placed by player
    public void removeObjectsPlaced() {
        for (Mirror x : mirrorsArray) {
            removeFromPane(x.getRectangle());
        }

        for (Lens x : lensArray) {
            removeFromPane(x.getRectangle());
        }

        for (Diamond x : diamondArray) {
            removeFromPane(x.getCircle());
        }

        for (Amber x : amberArray) {
            removeFromPane(x.getCircle());
        }

        mirrorsArray.clear();
        lensArray.clear();
        diamondArray.clear();
        amberArray.clear();
        objectsChosenAmount = 0;
        statusLabel.setText("Pick an object and place it on the screen");
    }

    //Method to set up 3 different levels
    public void setUpLevel(int levelsPassed) {
        if (levelsPassed == 0) {
            //if game has been won once, delete the previous source,target and photon
            if (isWon) {
                restartGameBtn.setDisable(true);
                removeFromPane(source.getCircle());
                removeFromPane(target.getCircle());
                removeFromPane(photon.getCircle());
            }

            source = new LightSource(10, 400);
            photon = new LightPhoton(30, 400);
            target = new LightTarget(600, 100);
            initialPhotonVelocity = photon.getVelocity();
            for (int i = 0; i < 2; i++) {
                obstacles.add(new Wall(500, 200 + 200 * i));
            }
            obstacles.forEach((x) -> {
                addToPane(x.getRectangle());
            });
            restartGameBtn.setDisable(true);
            nextLevelBtn.setDisable(true);
            levelLabel.setText("Level: 1");
        }

        if (levelsPassed == 1) {
            restartGameBtn.setDisable(true);
            removeFromPane(source.getCircle());
            removeFromPane(target.getCircle());
            removeFromPane(photon.getCircle());
            source = new LightSource(10, 200);
            photon = new LightPhoton(30, 200);
            target = new LightTarget(600, 100);
            initialPhotonVelocity = photon.getVelocity();
            for (int i = 0; i < 4; i++) {
                obstacles.add(new Wall(300, 50 + 200 * i));
            }
            obstacles.forEach((x) -> {
                addToPane(x.getRectangle());
            });
            nextLevelBtn.setDisable(true);
            levelLabel.setText("Level: 2");
        }

        if (levelsPassed == 2) {
            restartGameBtn.setDisable(true);
            removeFromPane(source.getCircle());
            removeFromPane(target.getCircle());
            removeFromPane(photon.getCircle());
            source = new LightSource(10, 500);
            photon = new LightPhoton(30, 500);
            target = new LightTarget(600, 400);
            initialPhotonVelocity = photon.getVelocity();
            for (int i = 0; i < 2; i++) {
                obstacles.add(new Wall(400, 150 + 200 * i));
            }
            obstacles.forEach((x) -> {
                addToPane(x.getRectangle());
            });
            nextLevelBtn.setDisable(true);
            levelLabel.setText("Level: 3");
        }

        // add photon,source,target to the pane
        addToPane(photon.getCircle());
        addToPane(source.getCircle());
        addToPane(target.getCircle());

        //set backgrounds for the buttons
        mirrorBtn.setBackground((AssetManager.getObjectBackground(0)));
        lensBtn.setBackground((AssetManager.getObjectBackground(1)));
        diamondBtn.setBackground((AssetManager.getObjectBackground(2)));
        amberBtn.setBackground((AssetManager.getObjectBackground(3)));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lastFrameTime = 0.0f;
        long initialTime = System.nanoTime();

        //set size of pane containing game
        pane.setPrefSize(700, 700);
        //pane.setPrefSize(700, 700);

        helpBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("HelpMenu.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Help Menu");
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException io) {
                    io.printStackTrace();
                }
            }
        });

        chosenFocalLength.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue a, Object o1, Object o2) {
                setFocalLength(chosenFocalLength.getValue());
                focalLength.setText("Focal Length: " + String.format("%.2f", getFocalLength()));
            }
        });

        //load all assets
        AssetManager.preloadAllAssets();
        pane.setBackground(AssetManager.getBackgroundImage());
        //Start the media and loop it
        mediaPlayer = new MediaPlayer(AssetManager.getBackgroundMusic());
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.seek(Duration.ZERO);
            }
        });
        mediaPlayer.play();
        //Set up Level
        setUpLevel(levelsPassed);

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Time calculation  
                double currentTime = (now - initialTime) / 1000000000.0;
                double frameDeltaTime = currentTime - lastFrameTime;
                lastFrameTime = currentTime;

                //if the photon leaves the screen, move it to its initial position and put its velocity to 0
                if (photon.getPosition().getX() > 700) {
                    photon.setPosition(new Vector2D(source.getPosition().getX() + 20, source.getPosition().getY()));
                    photon.setVelocity(initialPhotonVelocity);
                }

                if (photon.getPosition().getX() < 30) {
                    photon.setPosition(new Vector2D(source.getPosition().getX() + 20, source.getPosition().getY()));
                    photon.setVelocity(initialPhotonVelocity);
                }

                if (photon.getPosition().getY() > 700) {
                    photon.setPosition(new Vector2D(source.getPosition().getX() + 20, source.getPosition().getY()));
                    photon.setVelocity(initialPhotonVelocity);
                }

                if (photon.getPosition().getY() < 0) {
                    photon.setPosition(new Vector2D(source.getPosition().getX() + 20, source.getPosition().getY()));
                    photon.setVelocity(initialPhotonVelocity);
                }

                //Detect wall collision
                for (Wall x : obstacles) {
                    if ((photon.getPosition().getX() + photon.getCircle().getRadius() >= x.getPosition().getX())
                            && (photon.getPosition().getX() - photon.getCircle().getRadius() <= (x.getPosition().getX() + x.getRectangle().getWidth()))
                            && (photon.getPosition().getY() + photon.getCircle().getRadius() >= x.getPosition().getY())
                            && (photon.getPosition().getY() - photon.getCircle().getRadius() <= (x.getPosition().getY() + x.getRectangle().getHeight()))) {
                        //Move the photon to its initial position
                        photon.setPosition(new Vector2D(source.getPosition().getX() + 20, source.getPosition().getY()));
                        //Reset the photon's velocity
                        photon.setVelocity(initialPhotonVelocity);
                       
                    }
                }

                for (Mirror x : mirrorsArray) {
                    //Detect mirror collision
                    if ((photon.getPosition().getX() + photon.getCircle().getRadius() >= x.getPosition().getX())
                            && (photon.getPosition().getX() - photon.getCircle().getRadius() <= (x.getPosition().getX() + x.getRectangle().getWidth()))
                            && (photon.getPosition().getY() + photon.getCircle().getRadius() >= x.getPosition().getY())
                            && (photon.getPosition().getY() - photon.getCircle().getRadius() <= (x.getPosition().getY() + x.getRectangle().getHeight()))) {
                        //Change the photon's velocity
                        photon.setVelocity(Mirror.returnReflectedVector(photon.getVelocity()));
                        
                        //if it hits the same object twice, make it calc again
                        resetDiaCal();
                        resetAmberCal();
                        resetLensCal();
                    }
                }

                for (Lens x : lensArray) {
                    //Detect lens collision
                    //if its inside x and y wise
                    if ((photon.getPosition().getX() + photon.getCircle().getRadius() >= x.getPosition().getX())
                            && (photon.getPosition().getX() - photon.getCircle().getRadius() <= (x.getPosition().getX() + x.getRectangle().getWidth()))
                            && (photon.getPosition().getY() + photon.getCircle().getRadius() >= x.getPosition().getY())
                            && (photon.getPosition().getY() - photon.getCircle().getRadius() <= (x.getPosition().getY() + x.getRectangle().getHeight()))
                            //if it hasn't been calculated yet   
                            && x.getCalcCondition()) {
                        //Calculate the distance travelled by the photon once it's inside the lens
                        distanceTravelled += frameDeltaTime * photon.getVelocity().magnitude();
                        //Once its past the width, change its velocity
                        if (distanceTravelled >= x.getRectangle().getWidth()) {

                            //Separate the lens into 3 areas.
                            //The upperPart and the lowerPart delimitate the lens' middle
                            double upperPart = (x.getPosition().getY() + ((4.0 / 10) * (x.getRectangle().getHeight())));
                            double lowerPart = (x.getPosition().getY() + (6.0 / 10) * (x.getRectangle().getHeight()));

                            //If the photon isn't in the middle
                            if (((photon.getPosition().getY() < upperPart)
                                    || ((photon.getPosition().getY() > lowerPart)))) {
                                //if the photon isn't moving perpendicularly to the lens
                                if (!(photon.getVelocity().getY() == 0)) {

                                    photon.setVelocity(x.returnReflectedVector(photon.getVelocity()));
                                    upperPart = 0;
                                    lowerPart = 0;
                                    x.switchCalcAgain();
                                } else if (photon.getVelocity().getY() == 0) {
                                    double lensHeight = x.getRectangle().getHeight();
                                    Vector2D lensPosition = x.getPosition();
                                    photon.setVelocity(x.returnReflectedVector(photon.getVelocity(), x.getPosition(), photon.getPosition(), getFocalLength(), x.getRectangle().getHeight()));
                                    x.switchCalcAgain();
                                }
                             
                            }
                            resetDiaCal();
                            resetAmberCal();
                            distanceTravelled = 0;
                        }
                    }
                }

                //if photon touches diamond
                for (Diamond x : diamondArray) {
                    if (Math.abs(photon.getPosition().getX() - x.getPosition().getX()) < photon.getCircle().getRadius() + x.getCircle().getRadius()
                            //if the distance between the two middles is less than the radius
                            && Math.abs(photon.getPosition().getY() - x.getPosition().getY()) < photon.getCircle().getRadius() + x.getCircle().getRadius()
                            //if the calculation hasn't been done yet
                            && x.getCalcCondition()) {

                        //calculate the distance travelled
                        distanceTravelled += frameDeltaTime * photon.getVelocity().magnitude();
                        //once its past the diamond, change its velocity
                        if (distanceTravelled >= x.getCircle().getRadius() * 2) {
                            photon.setVelocity(x.returnReflectedVector(photon.getVelocity()));
                            x.switchCalcAgain();
                            distanceTravelled = 0;
                        }
                        resetAmberCal();
                        resetLensCal();
                    }
                }

                //if photon touches amber
                for (Amber x : amberArray) {
                    if (Math.abs(photon.getPosition().getX() - x.getPosition().getX()) < photon.getCircle().getRadius() + x.getCircle().getRadius()
                            //if the distance between the two middles is less than the radius
                            && Math.abs(photon.getPosition().getY() - x.getPosition().getY()) < photon.getCircle().getRadius() + x.getCircle().getRadius()
                            //if the calculation hasn't been done yet
                            && x.getCalcCondition()) {
                        //calculate distance travelled
                        distanceTravelled += frameDeltaTime * photon.getVelocity().magnitude();
                        //once the photon has moved past the amber, change its velocity
                        if (distanceTravelled >= x.getCircle().getRadius() * 2) {
                            photon.setVelocity(x.returnReflectedVector(photon.getVelocity()));
                            x.switchCalcAgain();
                            distanceTravelled = 0;
                        }
                        resetDiaCal();
                        resetLensCal();
                    }
                }

                //detect target collision
                if (Math.abs(photon.getPosition().getX() - target.getPosition().getX()) < photon.getCircle().getRadius() + target.getCircle().getRadius()
                        //if the distance between the two middles is less than the radius
                        && Math.abs(photon.getPosition().getY() - target.getPosition().getY()) < photon.getCircle().getRadius() + target.getCircle().getRadius()) {
                    //if the photon hits the target and 2 levels have been passed
                    //the game is finished
                    if (levelsPassed == 2) {
                        statusLabel.setText("Congrats! You saved Earth!");
                        photon.setVelocity(new Vector2D(0, 0));
                        photon.setPosition(new Vector2D(source.getPosition().getX() + 20, source.getPosition().getY()));
                        //give the user the option to restart the game
                        restartGameBtn.setDisable(false);
                        isWon = true;
                    } else {
                        photon.setVelocity(new Vector2D(0, 0));
                        photon.setPosition(new Vector2D(source.getPosition().getX() + 20, source.getPosition().getY()));
                        nextLevelBtn.setDisable(false);
                    }
                }

                photon.update(frameDeltaTime);

            }
        }.start();
    }
    
    
    public void resetAmberCal()
    {
        for (Amber x:amberArray)
        {
            x.resetCalcCondition();
        }
    }
    
    public void resetDiaCal()
    {
        for (Diamond x:diamondArray)
        {
            x.resetCalcCondition();
        }}
   
    public void resetLensCal()
    {
        for (Lens x:lensArray)
        {
            x.resetCalcCondition();
        }
    }
    
    
    

    public static MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
}
