/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physicsminigames;

import java.awt.Point;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import physicsminigames.projectileshooter.*;

/**
 *
 * @author cstuser
 */
public class FXMLMiniGame2Controller implements Initializable {

    private double lastFrameTime = 0.0;

    @FXML
    private AnchorPane pane;

    @FXML
    private Label coordinates;

    @FXML
    private ImageView gunImage;

    @FXML
    private ImageView character;

    @FXML
    private RadioButton radioButtonFireGun;
    @FXML
    private Label labelFireGun;
    @FXML
    public ImageView imageViewFireGun = new ImageView();
    
    @FXML
    private RadioButton radioButtonIceGun;
    @FXML
    private Label labelIceGun;
    @FXML
    public ImageView imageViewIceGun = new ImageView();
    
    @FXML
    private RadioButton radioButtonAGGun;
    @FXML
    private Label labelAntiGravityGun;
    @FXML
    public  ImageView imageViewAntiGravityGun = new ImageView();
    
    @FXML
    private RadioButton radioButtonPortalGun;
    @FXML
    private Label labelPortalGun;
    @FXML
    public ImageView imageViewPortalGun = new ImageView();

    @FXML
    private Button buttonClosePortals;
    @FXML
    private Button buttonCloseAntiGravity;

    @FXML
    private Button nextLevelButton1;
    @FXML
    private Button nextLevelButton2;
    @FXML
    private Button nextLevelButton3;
    
    @FXML
    public Button buttonMainMenu;
       
    @FXML
    public Button buttonHelp;
    
    @FXML
    private Label congratsLabel;
    
    //ArrayLists used for tracking all the object that exist in the game
    public ArrayList<GameObject> objectList = new ArrayList<>();
    private ArrayList<Projectile> arrListProjectiles = new ArrayList<>();
    private ArrayList<Barrier> arrListBarriers = new ArrayList<>();
    private ArrayList<Enemy> arrListEnemies = new ArrayList<>();
    private ArrayList<Wall> arrListWalls = new ArrayList<>();
    
    private int levelNumber = 1;

    private int counterProjectiles;
    private int counterBounces;

    //rectangles for detecting collisions with the edges of the game environment
    private Edge edgeRoof;
    private Edge edgeFloor;
    private Edge edgeLeftWall;
    private Edge edgeRightWall;

    private AntiGravity agLeftSide;
    private AntiGravity agRightSide;

    Bounds boundPortalIn;
    Bounds boundPortalOut;

    //Boolean to check if the projectile is within the bounds of an antigravity region
    private boolean isWithinAntiGravity = false;

    //Boolean values to track what kinds of portals are open on which surfaces
    private Portal portalIn;
    private Portal portalOut;

    public boolean portalInIsActive = false;
    public boolean portalOutIsActive = false;

    public boolean portalInIsRoof = false;
    public boolean portalOutIsRoof = false;

    public boolean portalInIsFloor = false;
    public boolean portalOutIsFloor = false;

    public boolean portalInIsLeftWall = false;
    public boolean portalOutIsLeftWall = false;

    public boolean portalInIsRightWall = false;
    public boolean portalOutIsRightWall = false;

    public boolean antiGravityIsActive = false;

    //Point variables used in the calculation of the mouse's location on the screen 
    public Point mouseAim;
    public Point gunPivot;

    //Variable for the angle created between the gun and the X-Axis
    double theta;

    //Variables for the mouse's position within the game environment
    private double mouseX;
    private double mouseY;

    //variable of acceleration to be used when creating projectiles
    private Vector acceleration;

    //class variable projectile
    private Projectile projectile;

    public double deathAnimDelayTime = 0;
    public boolean enemy_Hit = false;

    //Final variables
    private final double PROJECTILE_RADIUS = 10; // Radius of the projectiles
    private final double PROJECTILE_VELOCITY = 500; //Magnitude of the projectile's velocity
    private final double GRAVITY = 50; //Magnitude of gravity

    private final int MAX_NUMBER_OF_BOUNCES = 6; //The maximum number of bounces allowed before a projectile is deleted

    //Values used by portals and antigravity
    private final double PORTAL_WIDTH = 30; //The width of a portal
    private final double PORTAL_HEIGHT = 100; // THe height of a portal
    private final double AG_BORDERTHICKNESS = 40; // The thickness of one antigravity border
    private final double AG_DELTA_X = 150; //half of the seperation between two antigravity borders

    //private final double PORTAL_DELTA = 50; //Half the height of a portal
    private final double ICE_WIDTH = 30; //The width of an ice barrier
    private final double ICE_HEIGHT = 150; //the height of an ice barrier

    private final double FIRE_WIDTH = 60; // the width of a fire barrier
    private final double FIRE_HEIGHT = 150; // the height of a fire barrier

    private final double ENEMY_WIDTH = 95;
    private final double ENEMY_HEIGHT = 91;
    private final double ENEMY_DISPLACEMENT = 50;
    private final double ENEMY_VELOCITY = 20;
    private final double ENEMY_ANIMATION_DURATION = 1.2;    

    private final double WALL_THICKNESS = 20;

    //---Correction values---
    //Portal rotations
    private final double ROTATION_PORTAL_ROOF = 270;
    private final double ROTATION_PORTAL_LEFT = 180;
    private final double ROTATION_PORTAL_FLOOR = 90;

    //portal displacements to put them in the desired location
    private final double CORRECTION_PORTAL = -40;

    //Values used for the location  of the character's shoulder for shooting location
    private final double SHOULDER_X = 100;
    private final double SHOULDER_Y = 750;

    //Value used in calculating the point from which the gun will pivot
    private final double GUN_PIVOT_X = 100;
    private final double GUN_PIVOT_Y = 100;

    //Temporary audioclip file for shooting sounds
    AudioClip tempShot;

    //Random numbers used for calculating random start positions of enemies    
    Random rand;
    int randomPositionX;
    int randomPositionY;

    //Method to add nodes to the pane
    public void addToPane(Node node) {
        pane.getChildren().add(node);
    }

    //method to remove nodes from the pane
    public void removeFromPane(Node node) {
        pane.getChildren().remove(node);
    }
    
    
    
    /*
    @FXML
    public void buttonHelpClicked(MouseEvent event){
        
        
        
        scrollPaneHelp.setVisible(true);
        scrollPaneHelp.setDisable(false);
        buttonCloseHelp.setVisible(true);
        buttonCloseHelp.setDisable(false);
    }
    
    @FXML
    public void buttonCloseHelpClicked(MouseEvent event){
        
        
        
        scrollPaneHelp.setVisible(false);
        scrollPaneHelp.setDisable(true);
        buttonCloseHelp.setVisible(false);
        buttonCloseHelp.setDisable(true);
    }
    */
    
   
    
    //This method will point the gun at the mouse when it is moved
    @FXML
    public void mouseMoved(MouseEvent event) {
        mouseX = event.getSceneX();
        mouseY = (pane.getHeight() - event.getSceneY());

        mouseAim = new Point((int) mouseX, (int) mouseY);
        gunRotationAngle(mouseAim, gunPivot);

        //coordinates.setText("MouseX: " + mouseX + "MouseY: " + mouseY);
    }
    
    @FXML
    public void buttonHelp(MouseEvent event)
    {
        try{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HelpWindow.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.show();
        }
        catch(Exception e)
        {
            System.out.println("Cant load new window");
        }

    }

    //This method handles shooting and the creating of projectiles
    @FXML
    public void mouseClicked(MouseEvent event) {

        acceleration = new Vector(0, GRAVITY);

        //Checks if the projectile is within the bounds of an antigravity region and inverts the gravity if it is. ---------------PROBABLY REMOVE---------------------
        if (isWithinAntiGravity) {
            acceleration = new Vector(0, -GRAVITY);
        }

        //Supplies the method with values for the mouse's "x" and "y" coordinates
        mouseX = event.getSceneX();
        mouseY = (pane.getHeight() - event.getSceneY());

        //---------Changes the type of projectile depending on the gun--------
        if (radioButtonFireGun.isSelected()) {
            projectile = new Projectile(new Vector(SHOULDER_X, SHOULDER_Y), new Vector(Math.cos(theta) * PROJECTILE_VELOCITY, -Math.sin(theta) * PROJECTILE_VELOCITY), acceleration, PROJECTILE_RADIUS, "fire");
            tempShot = AssetManager.getShotFire();
        }

        if (radioButtonIceGun.isSelected()) {
            projectile = new Projectile(new Vector(SHOULDER_X, SHOULDER_Y), new Vector(Math.cos(theta) * PROJECTILE_VELOCITY, -Math.sin(theta) * PROJECTILE_VELOCITY), acceleration, PROJECTILE_RADIUS, "ice");
            tempShot = AssetManager.getShotIce();
        }

        if (radioButtonAGGun.isSelected()) {
            tempShot = AssetManager.getShotAntiGravity();
            if (antiGravityIsActive == true) {
                closeAntiGravity();
                projectile = new Projectile(new Vector(SHOULDER_X, SHOULDER_Y), new Vector(Math.cos(theta) * PROJECTILE_VELOCITY, -Math.sin(theta) * PROJECTILE_VELOCITY), acceleration, PROJECTILE_RADIUS, "antigravity");
            } else {
                projectile = new Projectile(new Vector(SHOULDER_X, SHOULDER_Y), new Vector(Math.cos(theta) * PROJECTILE_VELOCITY, -Math.sin(theta) * PROJECTILE_VELOCITY), acceleration, PROJECTILE_RADIUS, "antigravity");
            }

        }

        if (radioButtonPortalGun.isSelected()) {
            tempShot = AssetManager.getShotPortal();
            if (portalInIsActive && portalOutIsActive) {
                closePortal();
                projectile = new Projectile(new Vector(SHOULDER_X, SHOULDER_Y), new Vector(Math.cos(theta) * PROJECTILE_VELOCITY, -Math.sin(theta) * PROJECTILE_VELOCITY), acceleration, PROJECTILE_RADIUS, "portalIn");
            }
            if (portalInIsActive == false) {
                projectile = new Projectile(new Vector(SHOULDER_X, SHOULDER_Y), new Vector(Math.cos(theta) * PROJECTILE_VELOCITY, -Math.sin(theta) * PROJECTILE_VELOCITY), acceleration, PROJECTILE_RADIUS, "portalIn");
            } else {
                projectile = new Projectile(new Vector(SHOULDER_X, SHOULDER_Y), new Vector(Math.cos(theta) * PROJECTILE_VELOCITY, -Math.sin(theta) * PROJECTILE_VELOCITY), acceleration, PROJECTILE_RADIUS, "portalOut");
            }
        }

        //This ensures that only one projectile can exist at one time and if it can exist, it adds it to the scene
        if (arrListProjectiles.isEmpty()) {
            tempShot.play();
            addToPane(projectile.getCircle());
            objectList.add(projectile);
            arrListProjectiles.add(projectile);
        }
    }

    @FXML
    public void nextLevelButton1Clicked(MouseEvent event){
        loadLevelOne();
        hideCongratsScreen();
    }
    
    @FXML
    public void nextLevelButton2Clicked(MouseEvent event){
        loadLevelTwo();
        hideCongratsScreen();
    }
    
    @FXML
    public void nextLevelButton3Clicked(MouseEvent event){
        loadLevelThree();
        hideCongratsScreen();
    }
    
    //Calculates 
    public void gunRotationAngle(Point mouseAim, Point gunPivot) {
        theta = Math.atan2(mouseAim.y - gunPivot.y, mouseAim.x - gunPivot.x);

        double angle = Math.toDegrees(theta);
        gunImage.setRotate(-angle);
    }

    //----------PORTAL AND ANTIGRAVITY BEHAVIOR METHODS------------
    //PORTALS
    public void openPortal() {
        AudioClip tempActivatePortal = AssetManager.getActivatePortal();
        tempActivatePortal.play();
        if (portalInIsActive == false) {
            portalIn = new Portal(new Vector(projectile.getPosition().getX(), projectile.getPosition().getY() + CORRECTION_PORTAL), PORTAL_WIDTH, PORTAL_HEIGHT);
            boundPortalIn = portalIn.getBounds();
            objectList.add(portalIn);
            addToPane(portalIn.getRectangle());
            portalIn.getRectangle().setFill(AssetManager.getPortalIn());
            portalInIsActive = true;
        } else {
            buttonClosePortals.setDisable(false);
            portalOut = new Portal(new Vector(projectile.getPosition().getX(), projectile.getPosition().getY() + CORRECTION_PORTAL), PORTAL_WIDTH, PORTAL_HEIGHT);
            boundPortalOut = portalOut.getBounds();
            /*
            if(boundPortalOut.intersects(boundPortalIn))
            {
                if(portalInIsFloor)
                {
                    if(portalIn.getRectangle().getX() + PORTAL_HEIGHT >= edgeRightWall.getRectangle().getX())
                    {
                        
                    }
                }
            } 
             */

            objectList.add(portalOut);
            addToPane(portalOut.getRectangle());
            portalOut.getRectangle().setFill(AssetManager.getPortalOut());
            portalOutIsActive = true;
        }
    }

    //Method that closes portals and should only be called when two portals are active
    public void closePortal() {
        if(portalInIsActive && portalOutIsActive){
        AudioClip tempRemoveProjectile = AssetManager.getRemoveProjectile();
        tempRemoveProjectile.play();
        buttonClosePortals.setDisable(true);
        portalInIsActive = false;
        portalIn.getRectangle().setFill(Color.TRANSPARENT);
        objectList.remove(portalIn);
        removeFromPane(portalIn.getRectangle());

        portalOutIsActive = false;
        portalOut.getRectangle().setFill(Color.TRANSPARENT);
        objectList.remove(portalOut);
        removeFromPane(portalOut.getRectangle());

        //Resetting booleans for which side the portals are on        
        portalInIsFloor = false;
        portalOutIsFloor = false;

        portalInIsRoof = false;
        portalOutIsRoof = false;

        portalInIsLeftWall = false;
        portalOutIsLeftWall = false;

        portalInIsRightWall = false;
        portalOutIsRightWall = false;
        }
    }

    //Method that teleports projectile from portalIn to portalOut
    public void teleportInToOut(Projectile proj) {
        AudioClip tempTeleport = AssetManager.getTeleport();
        tempTeleport.play();
        proj.setPosition(new Vector(portalOut.getPosition().getX(), portalOut.getPosition().getY()));
        //-----portalIn is on the Roof-----

        //portalOut = roof (portalIn is roof)
        if (portalInIsRoof && portalOutIsRoof) {
            proj.setVelocity(new Vector(proj.getVelocity().getX(), -proj.getVelocity().getY()));
        }

        //portalOut = floor (portalIn is roof)
        if (portalInIsRoof && portalOutIsFloor) {
            //just set the position                                    
        }

        //portalOut = LeftWall (portalIn is roof)        
        if (portalInIsRoof && portalOutIsLeftWall) {
            if (proj.getVelocity().getX() < 0) {
                proj.setVelocity(new Vector(-proj.getVelocity().getX(), proj.getVelocity().getY()));
            }
        }

        //portalOut = RightWall (portalIn is roof)
        if (portalInIsRoof && portalOutIsRightWall) {
            if (proj.getVelocity().getX() > 0) {
                proj.setVelocity(new Vector(-proj.getVelocity().getX(), proj.getVelocity().getY()));
            }
        }

        //-----portalIn is on the Floor-----
        //portalOut = roof (portalIn is floor)
        if (portalInIsFloor && portalOutIsRoof) {
            //just set the position
        }

        //portalOut = floor (portalIn is floor)
        if (portalInIsFloor && portalOutIsFloor) {
            proj.setVelocity(new Vector(proj.getVelocity().getX(), -proj.getVelocity().getY()));
        }

        //portalOut = leftWall (portalIn is floor)
        if (portalInIsFloor && portalOutIsLeftWall) {
            if (proj.getVelocity().getX() < 0) {
                proj.setVelocity(new Vector(-proj.getVelocity().getX(), proj.getVelocity().getY()));
            }
        }

        //portalOut = rightWall (portalIn is floor)
        if (portalInIsFloor && portalOutIsRightWall) {
            if (proj.getVelocity().getX() > 0) {
                proj.setVelocity(new Vector(-proj.getVelocity().getX(), proj.getVelocity().getY()));
            }
        }

        //--------portalIn is on Left Wall-------
        //portalOut = roof (portalIn is leftWall)
        if (portalInIsLeftWall && portalOutIsRoof) {
            if (proj.getVelocity().getY() < 0) {
                proj.setVelocity(new Vector(proj.getVelocity().getX(), -proj.getVelocity().getY()));
            }
        }

        //portalOut = floor (portalIn is leftWall)
        if (portalInIsLeftWall && portalOutIsFloor) {
            if (proj.getVelocity().getY() > 0) {
                proj.setVelocity(new Vector(proj.getVelocity().getX(), -proj.getVelocity().getY()));
            }
        }

        //portalOut = leftWall (portalIn is leftWall)
        if (portalInIsLeftWall && portalOutIsLeftWall) {
            proj.setVelocity(new Vector(-proj.getVelocity().getX(), proj.getVelocity().getY()));
        }

        //portalOut = rightWall (portalIn is leftWall)
        if (portalInIsLeftWall && portalOutIsRightWall) {
            //Just set the position
        }

        //--------portalIn is on Right Wall-------
        //portalOut = roof (portalIn is rightWall)
        if (portalInIsRightWall && portalOutIsRoof) {
            if (proj.getVelocity().getY() < 0) {
                proj.setVelocity(new Vector(proj.getVelocity().getX(), -proj.getVelocity().getY()));
            }
        }
        //portalOut = floor (portalIn is rightWall)
        if (portalInIsRightWall && portalOutIsFloor) {
            if (proj.getVelocity().getY() > 0) {
                proj.setVelocity(new Vector(proj.getVelocity().getX(), -proj.getVelocity().getY()));
            }
        }
        //portalOut = leftWall (portalIn is rightWall)
        if (portalInIsRightWall && portalOutIsLeftWall) {
            //just set the position
        }

        //portalOut = rightWall (portalIn is rightWall)
        if (portalInIsRightWall && portalOutIsRightWall) {
            proj.setVelocity(new Vector(-proj.getVelocity().getX(), proj.getVelocity().getY()));
        }
    }
    
    //ANTIGRAVITY
    public void openAntiGravity() {
        //Creating and playing the audio clip for activating antigravity
        AudioClip tempAntiGravityActivation = AssetManager.getActivateAntiGravity();
        tempAntiGravityActivation.play();

        buttonCloseAntiGravity.setDisable(false);
        antiGravityIsActive = true;
        agLeftSide = new AntiGravity(new Vector(projectile.getCircle().getCenterX() - AG_DELTA_X, 0), AG_BORDERTHICKNESS, pane.getHeight());
        agLeftSide.getRectangle().setFill(AssetManager.getAntiGravitySide());
        objectList.add(agLeftSide);
        addToPane(agLeftSide.getRectangle());

        agRightSide = new AntiGravity(new Vector(projectile.getCircle().getCenterX() + AG_DELTA_X, 0), AG_BORDERTHICKNESS, pane.getHeight());
        agRightSide.getRectangle().setFill(AssetManager.getAntiGravitySide());
        objectList.add(agRightSide);
        addToPane(agRightSide.getRectangle());
    }

    public void closeAntiGravity() {
        if(antiGravityIsActive){
        buttonCloseAntiGravity.setDisable(true);
        antiGravityIsActive = false;
        objectList.remove(agLeftSide);
        removeFromPane(agLeftSide.getRectangle());

        objectList.remove(agRightSide);
        removeFromPane(agRightSide.getRectangle());
        }
    }

    //----Barrier Methods-----
    //Method that places Ice Barriers within the pane
    public void placeIceBarrier(Vector pos) {
        Barrier tempIceBarrier;

        tempIceBarrier = new Barrier(pos, ICE_HEIGHT, ICE_WIDTH, "icefull");
        arrListBarriers.add(tempIceBarrier);
        objectList.add(tempIceBarrier);
        addToPane(tempIceBarrier.getRectangle());
    }

    //Method that place Fire Barriers within the pane (upright)
    public void placeFireBarrier(Vector pos) {
        Barrier tempFireBarrier;

        tempFireBarrier = new Barrier(pos, FIRE_HEIGHT, FIRE_WIDTH, "fire");
        arrListBarriers.add(tempFireBarrier);
        objectList.add(tempFireBarrier);
        addToPane(tempFireBarrier.getRectangle());
    }
    
    //Method that places fire barriers but upsidedown
    public void placeFireBarrierFlipped(Vector pos){
        Barrier tempFireBarrier;

        tempFireBarrier = new Barrier(pos, FIRE_HEIGHT, FIRE_WIDTH, "fire");
        tempFireBarrier.getRectangle().setRotate(180);
        
        arrListBarriers.add(tempFireBarrier);
        objectList.add(tempFireBarrier);
        addToPane(tempFireBarrier.getRectangle());
        
    }

    //Method that detects collisions between barriers and projectiles
    public void barrierCollision(Barrier bar, Projectile proj) {
        Bounds barrierBound = bar.getBounds();

        if (proj.getCircle().getBoundsInParent().intersects(barrierBound)) {
            //Collisions with Fire barriers
            if (bar.getType().equalsIgnoreCase("fire")) {
                if (proj.getType().equalsIgnoreCase("ice")) {
                    AssetManager.getIceHitsFireBarrier().play();
                    removeFromPane(bar.getRectangle());
                    objectList.remove(bar);
                    arrListBarriers.remove(bar);
                }
            }

            //Collisions with Ice barriers
            if (bar.getType().equalsIgnoreCase("iceHit")) {
                if (proj.getType().equalsIgnoreCase("fire")) {
                    AssetManager.getFireHitsIceBarrier().play(0.1);
                    removeFromPane(bar.getRectangle());
                    objectList.remove(bar);
                    arrListBarriers.remove(bar);
                }
            }

            //Collisions with Ice barriers
            if (bar.getType().equalsIgnoreCase("icefull")) {
                if (proj.getType().equalsIgnoreCase("fire")) {
                    AssetManager.getFireHitsIceBarrier().play(0.1);
                    bar.getRectangle().setFill(AssetManager.getBarrierIce1());
                    bar.setType("iceHit");
                }
            }
            removeFromPane(proj.getCircle());
            objectList.remove(proj);
            arrListProjectiles.remove(proj);
        }

    }

    //Method that creates and places enemies within the pane
    //Method that creates and places enemies within the pane
    public void placeEnemy(Vector pos, String upOrDown) {
        rand = new Random();

        Enemy tempEnemy = new Enemy(pos, new Vector(ENEMY_VELOCITY, 0), new Vector(0, 0), ENEMY_HEIGHT, ENEMY_WIDTH);

        //This formula allows for the control of the range from which random numbers will be taken
        //((max - min) + 1) + min        
        randomPositionX = rand.nextInt((((int) pos.getX() + (int) ENEMY_DISPLACEMENT - ((int) pos.getX() - (int) ENEMY_DISPLACEMENT)) + 1)) + (int) pos.getX() - (int) ENEMY_DISPLACEMENT;

        tempEnemy.setPosition(new Vector(randomPositionX, pos.getY()));
        
        if(upOrDown.equalsIgnoreCase("down")){
            tempEnemy.getRectangle().setRotate(180);
        }
        

        addToPane(tempEnemy.getRectangle());
        arrListEnemies.add(tempEnemy);
        objectList.add(tempEnemy);

        tempEnemy.setInitialPosition(pos);
    }
    
    //Method that creates and places enemies within the pane
    public void placeEnemyVertical(Vector pos, String leftOrRight) {
        rand = new Random();

        Enemy tempEnemy = new Enemy(pos, new Vector(0, ENEMY_VELOCITY), new Vector(0, 0), ENEMY_HEIGHT, ENEMY_WIDTH);

        //This formula allows for the control of the range from which random numbers will be taken
        //((max - min) + 1) + min        
        randomPositionY = rand.nextInt((((int) pos.getY() + (int) ENEMY_DISPLACEMENT - ((int) pos.getY() - (int) ENEMY_DISPLACEMENT)) + 1)) + (int) pos.getY() - (int) ENEMY_DISPLACEMENT;

        tempEnemy.setPosition(new Vector(pos.getX(), randomPositionY));
        
        if(leftOrRight.equalsIgnoreCase("left")){
            tempEnemy.getRectangle().setRotate(90);
        }else{
            tempEnemy.getRectangle().setRotate(-90);
        }
        
        addToPane(tempEnemy.getRectangle());
        arrListEnemies.add(tempEnemy);
        objectList.add(tempEnemy);

        tempEnemy.setInitialPosition(pos);
    }
    
    /*
    public void burnEnemy(Enemy enemy){
        enemy.getRectangle().setFill(AssetManager.getEnemyBurning());
    }
    */

    public void enemyCollision(Enemy e, Projectile proj) {
        //case when a fireball hits an enemy
        if (proj.getType().equalsIgnoreCase("fire")) {
            enemy_Hit = true;
            
            /*
                removeFromPane(proj.getCircle());
                objectList.remove(proj);
                arrListProjectiles.remove(proj);            
            */  
            
            AudioClip tempBurning = AssetManager.getHitFire();
            tempBurning.play();
            
            e.getRectangle().setFill(AssetManager.getEnemyBurning());
            e.setVelocity(new Vector(0, 0));
            
            e.setIsBurning(true);
        }

        //When an iceball hits an enemy
        if (proj.getType().equalsIgnoreCase("ice")) {
            removeFromPane(proj.getCircle());
            arrListProjectiles.remove(proj);
            
            AudioClip tempFreeze = AssetManager.getHitIce();
            tempFreeze.play();

            // "freezes" the enemy
            e.setVelocity(new Vector(0, 0));
            e.getRectangle().setFill(AssetManager.getEnemyFrozen());
        }        
        
        //If it's not a fire ball or an ice ball
        if (proj.getType().equalsIgnoreCase("portalIn") || proj.getType().equalsIgnoreCase("portalOut") || proj.getType().equalsIgnoreCase("antigravity")) {
            removeFromPane(proj.getCircle());
            arrListProjectiles.remove(proj);
        }
    }

    public void placeWall(Vector pos, double width, double height) {

        Wall wall = new Wall(pos, width, height);
        addToPane(wall.getRectangle());
        objectList.add(wall);
        arrListWalls.add(wall);
    }

    public void wallCollisionTop(Wall w, Projectile proj) {
        proj.setPosition(new Vector(proj.getPosition().getX(), proj.getPosition().getY() - PROJECTILE_RADIUS));
        proj.setVelocity(new Vector(proj.getVelocity().getX(), -proj.getVelocity().getY()));
        ++counterBounces;
    }

    public void wallCollisionBottom(Wall w, Projectile proj) {
        proj.setPosition(new Vector(proj.getPosition().getX(), proj.getPosition().getY() + PROJECTILE_RADIUS));
        proj.setVelocity(new Vector(proj.getVelocity().getX(), -proj.getVelocity().getY()));
        ++counterBounces;
    }

    public void wallCollisionLeft(Wall w, Projectile proj) {
        proj.setPosition(new Vector(proj.getPosition().getX() - PROJECTILE_RADIUS, proj.getPosition().getY()));
        proj.setVelocity(new Vector(-proj.getVelocity().getX(), proj.getVelocity().getY()));
        ++counterBounces;
    }

    public void wallCollisionRight(Wall w, Projectile proj) {
        proj.setPosition(new Vector(proj.getPosition().getX() + PROJECTILE_RADIUS, proj.getPosition().getY()));
        proj.setVelocity(new Vector(-proj.getVelocity().getX(), proj.getVelocity().getY()));
        ++counterBounces;
    }

    public void loadLevelOne() {
        
        buttonCloseAntiGravity.setDisable(true);
        buttonClosePortals.setDisable(true);
        //-----------ENVIRONMENT------------
        //The order of the walls is approximately how they appear in the pane from left to right        
        //"A"
        placeWall(new Vector(0, 300), ENEMY_DISPLACEMENT * 5, WALL_THICKNESS);

        //"B"
        placeWall(new Vector(500, 400), ENEMY_DISPLACEMENT * 5, WALL_THICKNESS);

        //"C"
        placeWall(new Vector(500, 0), WALL_THICKNESS, 240);

        //"D"        
        placeWall(new Vector(1250, 100), ENEMY_DISPLACEMENT * 5, WALL_THICKNESS);

        //"E"
        placeWall(new Vector(1100, 380), ENEMY_DISPLACEMENT * 8, WALL_THICKNESS);

        //"F"
        placeWall(new Vector(950, 550), ENEMY_DISPLACEMENT * 11, WALL_THICKNESS);

        //"G"
        placeWall(new Vector(950, 550), WALL_THICKNESS, ENEMY_DISPLACEMENT * 4);

        //----------BARRIERS----------
        //"Y"
        placeIceBarrier(new Vector(1100, 400));

        //"Z"
        placeFireBarrier(new Vector(900, 550 + ENEMY_DISPLACEMENT * 3));

        //----------ENEMIES-------------
        //"I"
        placeEnemy(new Vector(100, 205), "");

        //"II"
        placeEnemy(new Vector(550, 305), "");

        //"III"
        placeEnemy(new Vector(1400, 5), "");

        //"IV"
        placeEnemy(new Vector(1250, 455), "");

        //"V"
        placeEnemy(new Vector(1300, 750), "");
    }

    public void loadLevelTwo() {
        
        buttonCloseAntiGravity.setDisable(true);
        buttonClosePortals.setDisable(true);
        //Wall placement
        //"A"
        placeWall(new Vector(280, 0), WALL_THICKNESS, ENEMY_DISPLACEMENT * 3);
        
        //"B"
        placeWall(new Vector(420, 150), ENEMY_DISPLACEMENT * 6, WALL_THICKNESS);        

        //"C"
        placeWall(new Vector(900, 0), WALL_THICKNESS, ENEMY_DISPLACEMENT * 3);
        
        //"D"
        placeWall(new Vector(1050, 150), ENEMY_DISPLACEMENT * 6 + 10, WALL_THICKNESS);
        
        //"E"
        placeWall(new Vector(1340, 0), WALL_THICKNESS, ENEMY_DISPLACEMENT * 3);
        
        //"F"
        
        
        //"G"
        placeWall(new Vector(900, 350), ENEMY_DISPLACEMENT * 12, WALL_THICKNESS);
        
        //"H"
        placeWall(new Vector(900, 350 + ICE_HEIGHT + WALL_THICKNESS), ENEMY_DISPLACEMENT * 12, WALL_THICKNESS);
        
        //"I"
        placeWall(new Vector(900, 350 + 2*ICE_HEIGHT + 2*WALL_THICKNESS), WALL_THICKNESS, ENEMY_DISPLACEMENT * 3);
        
        //Enemy Placement
        //I
        placeEnemy(new Vector(550, 60), "");
        
        //II
        placeEnemy(new Vector(1160, 60), "");
        
        //III        
        placeEnemy(new Vector(1350, 260), "");
        
        //IV
        placeEnemy(new Vector(1400, 425), "");
        
        //V
        placeEnemy(new Vector(1120, 700), "");
        
        //Barrier placement
        //Z
        placeIceBarrier(new Vector(1250, 200));
        
        //Y
        placeIceBarrier(new Vector(900, 350 + WALL_THICKNESS));
        
        //X
        placeIceBarrier(new Vector(1050, 350 + WALL_THICKNESS));       
        
        //W
        placeIceBarrier(new Vector(1200, 350 + WALL_THICKNESS));
        
        //V
        placeIceBarrier(new Vector(900, 350 + 2 * WALL_THICKNESS + ICE_HEIGHT));
        
        //U
        placeFireBarrier(new Vector(970, 350 + WALL_THICKNESS));
        
        //T
        placeFireBarrier(new Vector(1110, 350 + WALL_THICKNESS));        
    }

    public void loadLevelThree() {
        
        buttonCloseAntiGravity.setDisable(true);
        buttonClosePortals.setDisable(true);
        
        //------------WALLS------------
        //"A"
        placeWall(new Vector(270, FIRE_HEIGHT), ENEMY_DISPLACEMENT * 5, WALL_THICKNESS );
        
        //"B"
        placeWall(new Vector(900, 0), WALL_THICKNESS, ENEMY_DISPLACEMENT * 3);
        
        //"C"
        placeWall(new Vector(1120, FIRE_HEIGHT), ENEMY_DISPLACEMENT * 6, WALL_THICKNESS);
        
        //"D"
        placeWall(new Vector(1400, 0), WALL_THICKNESS, 60+ENEMY_HEIGHT);
        
        //"E"
        placeWall(new Vector(550, 450), ENEMY_DISPLACEMENT * 7, WALL_THICKNESS);
                
        //"F"
        placeWall(new Vector(550, 450), WALL_THICKNESS, ENEMY_DISPLACEMENT * 2.5);
                
        //"G"
        placeWall(new Vector(550, 670), ENEMY_DISPLACEMENT * 7, WALL_THICKNESS);
        
        //"H"
        placeWall(new Vector(550, 670), WALL_THICKNESS, ENEMY_DISPLACEMENT * 3);
        
        //"I"
        //placeWall(new Vector(1250, 450), WALL_THICKNESS, ENEMY_DISPLACEMENT * 12);
        
        //----------ENEMIES------------
        //"I"
        placeEnemy(new Vector(270 + ENEMY_DISPLACEMENT, 60), "");
        
        //"II"
        placeEnemy(new Vector(1250, 55), "");
        
        //"III"
        placeEnemy(new Vector(1250, FIRE_HEIGHT+WALL_THICKNESS), "down");
        
        //"IV"
        placeEnemy(new Vector(640, 450-ENEMY_HEIGHT), "");
        
        //"V"
        placeEnemy(new Vector(650, 450+WALL_THICKNESS), "down");
        
        //"VI"
        placeEnemy(new Vector(670, 670 + WALL_THICKNESS), "down");
        
        //"VII"
        placeEnemyVertical(new Vector(1150+ICE_WIDTH, 600), "left");
        
        //"VIII"
        placeEnemyVertical(new Vector(1150+ICE_WIDTH+ENEMY_HEIGHT, 500), "right");
        
        //-------BARRIERS-------
        //"Z"
        placeFireBarrier(new Vector(1120, 0));
        
        //"Y"
        placeFireBarrierFlipped(new Vector(1120, FIRE_HEIGHT+WALL_THICKNESS));
        
        //"U"
        placeIceBarrier(new Vector(1150, 80 + 2*FIRE_HEIGHT));
        
        //"X"
        placeIceBarrier(new Vector(1150, 80 + 2*FIRE_HEIGHT + ICE_HEIGHT));
        
        //"T"
        placeIceBarrier(new Vector(1150, 80 + 2*FIRE_HEIGHT + 2*ICE_HEIGHT));
        
        //"W"
        placeIceBarrier(new Vector(550 + ENEMY_DISPLACEMENT * 7, 700));
    }

    public void displayCongratsScreen(){
        
        //Hiding the gun selection buttons
        radioButtonAGGun.setDisable(true);
        radioButtonAGGun.setVisible(false);
        labelAntiGravityGun.setVisible(false);
        
        
        radioButtonPortalGun.setDisable(true);
        radioButtonPortalGun.setVisible(false);
        labelPortalGun.setVisible(false);
        
        radioButtonIceGun.setDisable(true);
        radioButtonIceGun.setVisible(false);
        labelIceGun.setVisible(false);
                
        
        radioButtonFireGun.setDisable(true);
        radioButtonFireGun.setVisible(false);
        labelFireGun.setVisible(false);
                
        
        
        
        //Hiding the Close-Antigravity and Close-Portals buttons
        buttonCloseAntiGravity.setVisible(false);
        buttonCloseAntiGravity.setDisable(true);
        
        buttonClosePortals.setVisible(false);
        buttonClosePortals.setDisable(true);
        
        //Displaying the Level selection buttons and congrats label
        congratsLabel.setVisible(true);
        nextLevelButton1.setVisible(true);
        nextLevelButton1.setDisable(false);
        
        nextLevelButton2.setVisible(true);
        nextLevelButton2.setDisable(false);        
        
        nextLevelButton3.setVisible(true);
        nextLevelButton3.setDisable(false);
    }
    
     public void hideCongratsScreen(){
         
        //Displaying the gun selection buttons
        radioButtonAGGun.setDisable(false);
        radioButtonAGGun.setVisible(true);
        labelAntiGravityGun.setVisible(true);
        
        radioButtonPortalGun.setDisable(false);
        radioButtonPortalGun.setVisible(true);
        labelPortalGun.setVisible(true);
        
        radioButtonIceGun.setDisable(false);
        radioButtonIceGun.setVisible(true);
        labelIceGun.setVisible(true);
        
        radioButtonFireGun.setDisable(false);
        radioButtonFireGun.setVisible(true);
        labelFireGun.setVisible(true);
        
        
        //Displaying the Close-Antigravity and Close-Portals buttons
        buttonCloseAntiGravity.setVisible(true);
        buttonCloseAntiGravity.setDisable(false);
        
        buttonClosePortals.setVisible(true);
        buttonClosePortals.setDisable(false);  
        
         
        //Hiding the Level selection buttons and congrats label
        congratsLabel.setVisible(false);
        nextLevelButton1.setVisible(false);
        nextLevelButton1.setDisable(true);
        
        nextLevelButton2.setVisible(false);
        nextLevelButton2.setDisable(true);        
        
        nextLevelButton3.setVisible(false);
        nextLevelButton3.setDisable(true);
    }
    
    public void clearLevel(/*ArrayList<GameObject> gameObjects, ArrayList<Enemy> enemies, ArrayList<Projectile> projectiles, ArrayList<Wall> walls, ArrayList<Barrier> barriers*/) // also include our other arraylists in the parameters so that when we call it, we can pass them all as paramters
    {
        closeAntiGravity();
        closePortal();
        
        //Loop for game objects
        for (int i = 0; i < objectList.size(); i++) {
            if(objectList.get(i) != null){
                removeFromPane(objectList.get(i).getRectangle());
                objectList.remove(i);
            }
        }
        
        //Loop for enemies
        for (int i = 0; i < arrListEnemies.size(); i++) {
            if(arrListEnemies.get(i) != null){
                removeFromPane(arrListEnemies.get(i).getRectangle());
                arrListEnemies.remove(i);
            }
        }
        
        for (int i = 0; i < arrListProjectiles.size(); i++) {
            if(arrListProjectiles.get(i) != null)
                removeFromPane(arrListProjectiles.get(i).getCircle());
                arrListProjectiles.remove(i);
        }
        
        for (int i = 0; i < arrListBarriers.size(); i++) {
            if(arrListBarriers.get(i) != null){                
                removeFromPane(arrListBarriers.get(i).getRectangle());
                arrListBarriers.remove(i);
            }
        }
        
        for (int i = 0; i < arrListWalls.size(); i++) {
            if(arrListWalls.get(i) != null){
                removeFromPane(arrListWalls.get(i).getRectangle());
                arrListWalls.remove(i);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lastFrameTime = 0.0f;
        long initialTime = System.nanoTime();

        AssetManager.preloadAllAssets();

        pane.setBackground(AssetManager.getBackground());      
        
        
        hideCongratsScreen();

        ToggleGroup group = new ToggleGroup();
        radioButtonFireGun.setToggleGroup(group);
        //
        
        radioButtonIceGun.setToggleGroup(group);
        //imageViewIceGun.setImage(AssetManager.getGunIce_Img());
        
        radioButtonAGGun.setToggleGroup(group);
        //imageViewAntiGravityGun.setImage(AssetManager.getGunAntiGravity_Img());        
        
        radioButtonPortalGun.setToggleGroup(group);
        //imageViewPortalGun.setImage(AssetManager.getGunPortalIn_Img());

        buttonCloseAntiGravity.setDisable(true);
        buttonClosePortals.setDisable(true);
        
        buttonMainMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ((Stage)pane.getScene().getWindow()).close();
            }
        });
       
        
        /*
        //Hiding the button in the help menu
        buttonCloseHelp.setVisible(false);
        buttonCloseHelp.setDisable(true);        
        buttonHelp.setVisible(true);
        scrollPaneHelp.setVisible(false);        
        buttonReturnToMainMenu.setVisible(true);
        
        */

        //buttonRemoveProjectiles.setDisable(true);
        //Image of MainCharacter
        character.setImage(AssetManager.getCharacterImage());

        //Image Gun
        //if(selectedGunType == "fire")
        gunImage.setImage(AssetManager.getGunFire_Img());
        radioButtonFireGun.setSelected(true);
        gunPivot = new Point();
        gunPivot.setLocation(GUN_PIVOT_X, GUN_PIVOT_Y);

        //Creating Edge objects
        edgeFloor = new Edge(new Vector(0, pane.getPrefHeight() + 50), pane.getPrefWidth() + 50, 1);
        edgeRoof = new Edge(new Vector(0, 0), pane.getPrefWidth() + 50, 1);
        edgeLeftWall = new Edge(new Vector(0, 0), 1, pane.getPrefHeight() + 50);
        edgeRightWall = new Edge(new Vector(pane.getPrefWidth() + 50, 0), 1, pane.getPrefHeight() + 50);

        //Adding Edges to the pane so that collisions can be detected with the edge
        addToPane(edgeFloor.getRectangle());
        addToPane(edgeRoof.getRectangle());
        addToPane(edgeLeftWall.getRectangle());
        addToPane(edgeRightWall.getRectangle());

        //Adding edges to the objectList so that their existance within the program can be monitored
        objectList.add(edgeFloor);
        objectList.add(edgeRoof);
        objectList.add(edgeLeftWall);
        objectList.add(edgeRightWall);

        loadLevelOne();

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                try {
                    double currentTime = (now - initialTime) / 1000000000.0;
                    double frameDeltaTime = currentTime - lastFrameTime;
                    lastFrameTime = currentTime;

                    for (GameObject obj : objectList) {
                        if (obj != null) {
                            obj.updateRectangle(frameDeltaTime);
                            obj.updateCircle(frameDeltaTime);
                        }
                    }
                    
                    //Burning enemies that have been hit
                    for (int i = 0; i < arrListEnemies.size(); i++) {
                        if(arrListEnemies.get(i).getIsBurning() == true)
                        {
                            deathAnimDelayTime += frameDeltaTime;
                            if(!arrListEnemies.get(i).getRectangle().getFill().isOpaque()){
                                if(deathAnimDelayTime >= ENEMY_ANIMATION_DURATION)
                                {
                                        removeFromPane(arrListEnemies.get(i).getRectangle());
                                        arrListEnemies.remove(i);
                                        objectList.remove(i);
                                        deathAnimDelayTime = 0;
                                }    
                            }
                        }                            
                    }
                    
                    
                /*          
                while(enemy_Hit){
                    deathAnimDelayTime +=frameDeltaTime;
                    
                    if(deathAnimDelayTime >= 1.4){
                        
                        for (int i = 0; i < arrListEnemies.size(); i++) {
                            if(arrListEnemies.get(i).getIsBurning() == true)
                            {
                                removeFromPane(arrListEnemies.get(i).getRectangle());
                                arrListEnemies.remove(i);
                                objectList.remove(i);                                   
                            }
                        }
                        enemy_Hit = false;
                    }
                }
                */
              

                } catch (Exception e) {
                }

                if (group.getSelectedToggle() == radioButtonFireGun) {
                    gunImage.setImage(AssetManager.getGunFire_Img());
                }

                if (group.getSelectedToggle() == radioButtonIceGun) {
                    gunImage.setImage(AssetManager.getGunIce_Img());
                }

                if (group.getSelectedToggle() == radioButtonAGGun) {
                    gunImage.setImage(AssetManager.getGunAntiGravity_Img());
                }

                if (group.getSelectedToggle() == radioButtonPortalGun) {
                    gunImage.setImage(AssetManager.getGunPortalIn_Img());
                }

                for (int i = 0; i < arrListProjectiles.size(); i++) {                    
                    AudioClip tempBounce = AssetManager.getBounce();
                    Projectile tempProjectile = arrListProjectiles.get(i);

                    //coordinates.setText("Projectile X: " + projectile.getCircle().getCenterX() + "Projectile Y: " + projectile.getCircle().getCenterY());

                    Circle projectileCircle = tempProjectile.getCircle();
                    Bounds boundProjectileCircle = projectileCircle.getBoundsInParent();

                    //SETTING BOUNDS
                    //Bound Floor
                    Rectangle rectangleEdgeFloor = edgeFloor.getRectangle();
                    Bounds boundRectangleEdgeFloor = rectangleEdgeFloor.getBoundsInParent();

                    //Bound Roof
                    Rectangle rectangleEdgeRoof = edgeRoof.getRectangle();
                    Bounds boundRectangleEdgeRoof = rectangleEdgeRoof.getBoundsInParent();

                    //Bound LeftWall
                    Rectangle rectangleEdgeLeftWall = edgeLeftWall.getRectangle();
                    Bounds boundRectangleEdgeLeftWall = rectangleEdgeLeftWall.getBoundsInParent();

                    //Bound RightWall
                    Rectangle rectangleEdgeRightWall = edgeRightWall.getRectangle();
                    Bounds boundRectangleEdgeRightWall = rectangleEdgeRightWall.getBoundsInParent();

                    //----------COLLISIONS--------
                    //FLOOR
                    if (boundProjectileCircle.intersects(boundRectangleEdgeFloor)) {

                        //Check for fire and ice (floor)
                        if (tempProjectile.getType().equalsIgnoreCase("fire") || tempProjectile.getType().equalsIgnoreCase("ice")) {
                            if (counterBounces != MAX_NUMBER_OF_BOUNCES) {
                                tempBounce.play();
                            }
                            ++counterBounces;

                            //Moves projectile away from edge by 1 radius
                            tempProjectile.setPosition(new Vector(tempProjectile.getPosition().getX(), tempProjectile.getPosition().getY() - PROJECTILE_RADIUS));

                            //Inverts the projectile's velocity
                            tempProjectile.setVelocity(new Vector(tempProjectile.getVelocity().getX(), -tempProjectile.getVelocity().getY()));
                        }

                        //Check for antigravity (floor)
                        if (tempProjectile.getType().equalsIgnoreCase("antigravity")) {
                            openAntiGravity();

                            tempProjectile.setVelocity(new Vector(0, 0));
                            objectList.remove(tempProjectile);
                            removeFromPane(tempProjectile.getCircle());
                            arrListProjectiles.remove(0);
                            //------TODO-------
                        }

                        //Check for portalIn (floor)
                        if (tempProjectile.getType().equalsIgnoreCase("portalIn")) {
                            openPortal();
                            portalInIsFloor = true;

                            portalIn.getRectangle().setRotate(ROTATION_PORTAL_FLOOR);

                            tempProjectile.setVelocity(new Vector(0, 0));
                            objectList.remove(tempProjectile);
                            removeFromPane(tempProjectile.getCircle());
                            arrListProjectiles.remove(0);
                        }

                        //Check for PortalOut (floor)
                        if (tempProjectile.getType().equalsIgnoreCase("portalOut")) {
                            openPortal();
                            portalOutIsFloor = true;

                            portalOut.getRectangle().setRotate(ROTATION_PORTAL_FLOOR);

                            tempProjectile.setVelocity(new Vector(0, 0));
                            objectList.remove(tempProjectile);
                            removeFromPane(tempProjectile.getCircle());
                            arrListProjectiles.remove(0);
                        }
                    }

                    //ROOF
                    if (boundProjectileCircle.intersects(boundRectangleEdgeRoof)) {

                        //Check fire and ice (roof)
                        if (tempProjectile.getType().equalsIgnoreCase("fire") || tempProjectile.getType().equalsIgnoreCase("ice")) {
                            if (counterBounces != MAX_NUMBER_OF_BOUNCES) {
                                tempBounce.play();
                            }
                            ++counterBounces;

                            //Moves the projectile away from edge by 1 radius
                            tempProjectile.setPosition(new Vector(tempProjectile.getPosition().getX(), tempProjectile.getPosition().getY() + PROJECTILE_RADIUS));

                            //Inverts the projectile's velocity
                            tempProjectile.setVelocity(new Vector(tempProjectile.getVelocity().getX(), -tempProjectile.getVelocity().getY()));
                        }

                        //Check for antigravity (roof)
                        if (tempProjectile.getType().equalsIgnoreCase("antigravity")) {
                            openAntiGravity();

                            tempProjectile.setVelocity(new Vector(0, 0));
                            objectList.remove(tempProjectile);
                            removeFromPane(tempProjectile.getCircle());
                            arrListProjectiles.remove(0);
                            //------TODO-------
                        }

                        //Check for portalIn (roof)
                        if (tempProjectile.getType().equalsIgnoreCase("portalIn")) {
                            openPortal();
                            portalInIsRoof = true;

                            portalIn.getRectangle().setRotate(ROTATION_PORTAL_ROOF);

                            tempProjectile.setVelocity(new Vector(0, 0));
                            objectList.remove(tempProjectile);
                            removeFromPane(tempProjectile.getCircle());
                            arrListProjectiles.remove(0);
                        }

                        //Check for PortalOut (roof)
                        if (tempProjectile.getType().equalsIgnoreCase("portalOut")) {
                            openPortal();
                            portalOutIsRoof = true;

                            portalOut.getRectangle().setRotate(ROTATION_PORTAL_ROOF);

                            tempProjectile.setVelocity(new Vector(0, 0));
                            objectList.remove(tempProjectile);
                            removeFromPane(tempProjectile.getCircle());
                            arrListProjectiles.remove(0);
                        }
                    }

                    //LEFT WALL
                    if (boundProjectileCircle.intersects(boundRectangleEdgeLeftWall)) {

                        //Check for fire, ice and antigravity (left wall)
                        if (tempProjectile.getType().equalsIgnoreCase("fire") || tempProjectile.getType().equalsIgnoreCase("ice") || tempProjectile.getType().equalsIgnoreCase("antigravity")) {
                            if (counterBounces != MAX_NUMBER_OF_BOUNCES) {
                                tempBounce.play();
                            }
                            ++counterBounces;

                            //Moves the projectile away from edge by 1 radius
                            tempProjectile.setPosition(new Vector(tempProjectile.getPosition().getX() + PROJECTILE_RADIUS, tempProjectile.getPosition().getY()));

                            //Inverts the projectile's velocity
                            tempProjectile.setVelocity(new Vector(-tempProjectile.getVelocity().getX(), tempProjectile.getVelocity().getY()));
                        }

                        //Check for portalIn (left wall)
                        if (tempProjectile.getType().equalsIgnoreCase("portalIn")) {
                            openPortal();
                            portalInIsLeftWall = true;

                            portalIn.getRectangle().setRotate(ROTATION_PORTAL_LEFT);

                            tempProjectile.setVelocity(new Vector(0, 0));
                            objectList.remove(tempProjectile);
                            removeFromPane(tempProjectile.getCircle());
                            arrListProjectiles.remove(0);
                        }

                        //Check for PortalOut (left wall)
                        if (tempProjectile.getType().equalsIgnoreCase("portalOut")) {
                            openPortal();
                            portalOutIsLeftWall = true;

                            portalOut.getRectangle().setRotate(ROTATION_PORTAL_LEFT);

                            tempProjectile.setVelocity(new Vector(0, 0));
                            objectList.remove(tempProjectile);
                            removeFromPane(tempProjectile.getCircle());
                            arrListProjectiles.remove(0);
                        }
                    }
                    //RIGHT WALL
                    if (boundProjectileCircle.intersects(boundRectangleEdgeRightWall)) {

                        //check for fire, ice and antigravity (right wall)
                        if (tempProjectile.getType().equalsIgnoreCase("fire") || tempProjectile.getType().equalsIgnoreCase("ice") || tempProjectile.getType().equalsIgnoreCase("antigravity")) {

                            if (counterBounces != MAX_NUMBER_OF_BOUNCES) {
                                tempBounce.play();
                            }
                            ++counterBounces;

                            //Moves the projectile away from edge by 1 radius
                            tempProjectile.setPosition(new Vector(tempProjectile.getPosition().getX() - PROJECTILE_RADIUS, tempProjectile.getPosition().getY()));

                            //Inverts the projectile's velocity
                            tempProjectile.setVelocity(new Vector(-tempProjectile.getVelocity().getX(), tempProjectile.getVelocity().getY()));
                        }

                        //Check for portalIn (right wall)
                        if (tempProjectile.getType().equalsIgnoreCase("portalIn")) {
                            openPortal();
                            portalIn.setPosition(new Vector(portalIn.getPosition().getX() - 2 * PORTAL_WIDTH, portalIn.getPosition().getY()));
                            portalInIsRightWall = true;

                            tempProjectile.setVelocity(new Vector(0, 0));
                            objectList.remove(tempProjectile);
                            removeFromPane(tempProjectile.getCircle());
                            arrListProjectiles.remove(0);
                        }

                        //Check for PortalOut (right wall)
                        if (tempProjectile.getType().equalsIgnoreCase("portalOut")) {
                            openPortal();
                            portalOut.setPosition(new Vector(portalOut.getPosition().getX() - 2 * PORTAL_WIDTH, portalOut.getPosition().getY()));
                            portalOutIsRightWall = true;

                            tempProjectile.setVelocity(new Vector(0, 0));
                            objectList.remove(tempProjectile);
                            removeFromPane(tempProjectile.getCircle());
                            arrListProjectiles.remove(0);
                        }

                    }

                    //------------------------TELEPORTATION-----------------------//
                    if (portalInIsActive && portalOutIsActive) {                  //
                        if (boundProjectileCircle.intersects(boundPortalIn)) {    //
                            teleportInToOut(tempProjectile);                      //
                        }                                                         //
                        if (boundProjectileCircle.intersects(boundPortalOut)) {   //
                            //teleportOutToIn(tempProjectile);                    //
                        }                                                         //
                    }                                                             //
                    //------------------------------------------------------------//

                    //--------------------------------------------------ANTIGRAVITY--------------------------------------------------------------------------------------//
                    if (antiGravityIsActive) {                                                                                                                           //
                        if (projectileCircle.getCenterX() > agLeftSide.getRectangle().getX() && projectileCircle.getCenterX() < agRightSide.getRectangle().getX()) {     //
                            projectile.setAcceleration(new Vector(0, -10 * GRAVITY));                                                                                    //
                        } else {                                                                                                                                         //
                            projectile.setAcceleration(new Vector(0, GRAVITY));                                                                                          //
                        }                                                                                                                                                //
                    }                                                                                                                                                    //
                    //---------------------------------------------------------------------------------------------------------------------------------------------------//

                    //--------------------------------------WALL COLLISIONS---------------------------------//                     
                    for (int j = 0; j < arrListWalls.size(); j++) {                                         //
                        if (arrListWalls.get(j).getBottomBound().intersects(boundProjectileCircle)) {       //
                            wallCollisionBottom(arrListWalls.get(j), tempProjectile);                       //
                        }                                                                                   //
                        if (arrListWalls.get(j).getTopBound().intersects(boundProjectileCircle)) {          //
                            wallCollisionTop(arrListWalls.get(j), tempProjectile);                          //
                        }                                                                                   //
                        if (arrListWalls.get(j).getLeftBound().intersects(boundProjectileCircle)) {         //
                            wallCollisionLeft(arrListWalls.get(j), tempProjectile);                     //
                        }                                                                               //
                        if (arrListWalls.get(j).getRightBound().intersects(boundProjectileCircle)) {      //
                            wallCollisionRight(arrListWalls.get(j), tempProjectile);                    //
                        }                                                                               //
                    }                                                                                       //
                    //--------------------------------------------------------------------------------------//

                    //--------------------BARRIER COLLISIONS----------------------//
                    for (int j = 0; j < arrListBarriers.size(); j++) {            //            
                        barrierCollision(arrListBarriers.get(j), tempProjectile); //                       
                    }                                                             //
                    //------------------------------------------------------------//

                    //-----------------------ENEMY COLLISIONS-----------------------//
                    for (int x = 0; x < arrListEnemies.size(); x++) {               //
                        Bounds tempBound = arrListEnemies.get(x).getBounds();       //               
                        if (boundProjectileCircle.intersects(tempBound)) //
                        {                                                           //
                            enemyCollision(arrListEnemies.get(x), tempProjectile);  //
                        }                                                           //
                    }                                                               //
                    //--------------------------------------------------------------//

                    //This will remove the projectile if it goes outside the bounds of the game
                    if (tempProjectile.getCircle().getCenterX() >= 1.1 * pane.getPrefWidth() || tempProjectile.getCircle().getCenterX() <= -1.1 * pane.getPrefWidth()) {
                        counterBounces = 0;
                        AudioClip remove = AssetManager.getRemoveProjectile();
                        remove.play();
                        objectList.remove(arrListProjectiles.get(0));
                        arrListProjectiles.remove(0);
                        removeFromPane(tempProjectile.getCircle());
                    }

                    if (tempProjectile.getCircle().getCenterY() >= 1.1 * pane.getPrefHeight() || tempProjectile.getCircle().getCenterY() <= -1.1 * pane.getPrefHeight()) {
                        counterBounces = 0;
                        AudioClip remove = AssetManager.getRemoveProjectile();
                        remove.play();
                        objectList.remove(arrListProjectiles.get(0));
                        arrListProjectiles.remove(0);
                        removeFromPane(tempProjectile.getCircle());

                    }

                    //------------Case for when the ball bounces the max number of times allowed-------
                    if (counterBounces >= MAX_NUMBER_OF_BOUNCES) {
                        counterBounces = 0;
                        AudioClip remove = AssetManager.getRemoveProjectile();
                        remove.play();
                        objectList.remove(arrListProjectiles.get(0));
                        arrListProjectiles.remove(0);
                        removeFromPane(tempProjectile.getCircle());
                    }

                    projectile = tempProjectile;

                }//for (int i = 0; i < arrayListProjectiles.size(); i++)
                
                if(arrListEnemies.isEmpty())
                {
                    clearLevel();
                    displayCongratsScreen();
                }

                //This makes the enemies move back and forth
                for (int j = 0; j < arrListEnemies.size(); j++) {
                    if (arrListEnemies.get(j).getRectangle().getX() < arrListEnemies.get(j).getInitialPosition().getX() - ENEMY_DISPLACEMENT) {
                        arrListEnemies.get(j).setVelocity(new Vector(ENEMY_VELOCITY, 0));
                    }

                    if (arrListEnemies.get(j).getRectangle().getX() > arrListEnemies.get(j).getInitialPosition().getX() + ENEMY_DISPLACEMENT) {
                        arrListEnemies.get(j).setVelocity(new Vector(-ENEMY_VELOCITY, 0));
                    }
                    
                    if (arrListEnemies.get(j).getRectangle().getY() < arrListEnemies.get(j).getInitialPosition().getY() - ENEMY_DISPLACEMENT) {
                        arrListEnemies.get(j).setVelocity(new Vector(0, ENEMY_VELOCITY));
                    }

                    if (arrListEnemies.get(j).getRectangle().getY() > arrListEnemies.get(j).getInitialPosition().getY() + ENEMY_DISPLACEMENT) {
                        arrListEnemies.get(j).setVelocity(new Vector(0, -ENEMY_VELOCITY));
                    }
                    
                }

            }//public void handle(long now)

        }.start();

    }

}