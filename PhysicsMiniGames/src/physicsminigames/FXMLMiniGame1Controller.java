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
import javafx.application.Platform;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import physicsminigames.chargecannon.AssetManager;
import physicsminigames.chargecannon.Bullet;
import physicsminigames.chargecannon.Cannon;
import physicsminigames.chargecannon.CircleGameObject;
import physicsminigames.chargecannon.Generator;
import physicsminigames.chargecannon.GridRegion;
import physicsminigames.chargecannon.GroundGate;
import physicsminigames.chargecannon.GroundWall;
import physicsminigames.chargecannon.Physics;
import physicsminigames.chargecannon.RectangleGameObject;
import physicsminigames.chargecannon.Vector2D;


public class FXMLMiniGame1Controller implements Initializable {
    // GUI controls
    @FXML
    private AnchorPane pane;
    private Slider chargeSlider = new Slider();
    private Slider speedSlider = new Slider();
    private Slider angleSlider = new Slider();
    private Slider massSlider = new Slider();
    private Button nextLevelButton = new Button();
    private Button quitButton = new Button();
    private Button helpButton = new Button();
    private VBox controls = new VBox();
    
    // Other GUI components
    private static MediaPlayer soundtrackPlayer = null;
    private Label messageLabel = new Label();
    private Label speedLabel = new Label();
    private Label chargeLabel = new Label();
    private Label massLabel = new Label();
    private Label angleLabel = new Label();
    private Label levelLabel = new Label();
    private Label genLevelLabel = new Label();
    private Label shotsLabel = new Label();
    private Label velocityLabel = new Label();
    
    // Shooting related variables
    private Button fireButton = new Button();
    private double initialSpeed = 0.0;
    private double firingAngle = 0.0;
    private double bulletCharge = 0.0;
    private double bulletMass = 1.0;
    private double lastShotTimeDelta = 0.0;
    private boolean countShotTime = false;

    // Game object variables
    private ArrayList<CircleGameObject> circleObjectsList = new ArrayList<>();
    private ArrayList<RectangleGameObject> rectObjectsList = new ArrayList<>();
    private Cannon chargeCannon;
    private Generator generator;

    // Fixed charges (they determine the electric fields) 
    private final double sourceCharge1 = 2.0;
    private final double sourceCharge2 = -3.0;
    private final double sourceCharge3 = 7.0;
    private final double sourceCharge4 = -6.0;
    private final Vector2D chargePosition1 = new Vector2D(100, 550); 
    private final Vector2D chargePosition2 = new Vector2D(400, 50);
    private final Vector2D chargePosition3 = new Vector2D(450, 550); 
    private final Vector2D chargePosition4 = new Vector2D(200, 50);
    private ArrayList<Double> sourceChargesLevel2 = new ArrayList<>();
    private ArrayList<Double> sourceChargesLevel3 = new ArrayList<>();
    private ArrayList<Vector2D> sourcePositionsLevel2 = new ArrayList<>();
    private ArrayList<Vector2D> sourcePositionsLevel3 = new ArrayList<>();
    private ArrayList<Circle> iconsList = new ArrayList<>();
    
    // Other variables
    private double lastFrameTime = 0.0;
    private double currentKineticEnergy = 0.0;
    private int currentLevel = 1;
    private double[] levelRequirements = {1700, 3300, 3800};
    ArrayList<GridRegion> gridPoints = new ArrayList<>();
    private final int AMMO = 12;
    private int shotsRemaining = AMMO;
    
    
    // Methods for adding and removing objects from the pane
    public void addToPane(Node node) {
        pane.getChildren().add(node);
    }

    public void removeFromPane(Node node) {
        pane.getChildren().remove(node);
    }
    
    // Method for level setup
    public void setUpLevel(int levelNumber){
        // Level Number Label
        levelLabel.setText("Level " + currentLevel);
        if(currentLevel == 1){
            levelLabel.setLayoutX(10);
            levelLabel.setLayoutY(5);
            levelLabel.setFont(Font.font(14));
            addToPane(levelLabel);
        }
        
        // Generator Charge Level
        genLevelLabel.setText("Generator Charge Lvl: 0.00/" + levelRequirements[currentLevel-1]);
        
        // Reset Sliders
        angleSlider.setValue(0.0);
        massSlider.setValue(1.0);
        speedSlider.setValue(25.0);
        chargeSlider.setValue(0.0);
        
        // Level Specific Setups
        
        // Level 1 
        if(currentLevel == 1){
            
            // Source charge icons
            double charge = sourceCharge3;
            Circle icon = new Circle(chargePosition2.getX(), chargePosition2.getY(), 20.0);
            iconsList.add(icon);
            if(charge > 0.0){
                icon.setFill(AssetManager.getPositiveSourceImage());
            }
            else{
                icon.setFill(AssetManager.getNegativeSourceImage());
            }
            addToPane(icon);
            
            // Creating vector field grid 
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 9; j++) {
                    GridRegion block = new GridRegion(0 + j * 100, 0 + i * 100, 100);
                    block.setElectricFieldVector(sourceCharge3, chargePosition2, block.getCenter());  
                    gridPoints.add(block);
                    //System.out.println("X: " + block.getCornerPosition().getX() + "\tY: " + block.getCornerPosition().getY() + "\tEField X: " + block.getElectricFieldVector().getX() + "\tEField Y: " + block.getElectricFieldVector().getY());
                }
            }
            
            // Obstacles and generator and cannon
            
            // Positioning of cannon (constant)
            Vector2D cannonPosition = new Vector2D(10.0, pane.getPrefHeight() / 2 - 24);
            chargeCannon = new Cannon(cannonPosition);
            rectObjectsList.add(chargeCannon);
            addToPane(chargeCannon.getRectangle());
            
            // Generator position
            generator = new Generator(new Vector2D(800, 150));
            rectObjectsList.add(generator);
            addToPane(generator.getRectangle());
        
            // Ground Wall 
            GroundWall gw = new GroundWall(new Vector2D(300, 200)); 
            rectObjectsList.add(gw);
            addToPane(gw.getRectangle());
        
            // Ground Gate
            GroundGate gg = new GroundGate(new Vector2D(500, 300));
            rectObjectsList.add(gg);
            addToPane(gg.getRectangle());
        }
        
        if(currentLevel == 2){
            
            // Initializing ArrayLists for field 
            sourceChargesLevel2.add(sourceCharge1);
            sourceChargesLevel2.add(sourceCharge2);
            sourcePositionsLevel2.add(chargePosition1);
            sourcePositionsLevel2.add(chargePosition2);

            // Source Charge icons
            for(int i = 0; i < sourcePositionsLevel2.size(); i++){
                double charge = sourceChargesLevel2.get(i);
                Circle icon = new Circle(sourcePositionsLevel2.get(i).getX(), sourcePositionsLevel2.get(i).getY(), 20.0);
                iconsList.add(icon);
                if(charge > 0.0){
                    icon.setFill(AssetManager.getPositiveSourceImage());
                }
                else{
                    icon.setFill(AssetManager.getNegativeSourceImage());
                }
                addToPane(icon);
            }

            // Creating vector field grid 
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 9; j++) {
                    GridRegion block = new GridRegion(0 + j * 100, 0 + i * 100, 100);
                    block.setNetElectricFieldVector(sourceChargesLevel2, sourcePositionsLevel2, block.getCenter());  
                    gridPoints.add(block);
                    //System.out.println("X: " + block.getCornerPosition().getX() + "\tY: " + block.getCornerPosition().getY() + "\tEField X: " + block.getElectricFieldVector().getX() + "\tEField Y: " + block.getElectricFieldVector().getY());
                }
            }

            // Obstacles and generator and cannon
            
            // Positioning of cannon (constant)
            Vector2D cannonPosition = new Vector2D(10.0, pane.getPrefHeight() / 2 - 24);
            chargeCannon = new Cannon(cannonPosition);
            rectObjectsList.add(chargeCannon);
            addToPane(chargeCannon.getRectangle());
        
            // Generator position
            generator = new Generator(new Vector2D(800, 500));
            rectObjectsList.add(generator);
            addToPane(generator.getRectangle());

            // Ground Wall 
            GroundWall gw = new GroundWall(new Vector2D(600, 420));
            rectObjectsList.add(gw);
            addToPane(gw.getRectangle());

            // Ground Gate
            GroundGate gg = new GroundGate(new Vector2D(450, 250));
            rectObjectsList.add(gg);
            addToPane(gg.getRectangle());
            
            // Next Level Button
            nextLevelButton.setVisible(false);
            nextLevelButton.setDisable(true);
        }
        
        if(currentLevel == 3){
            
            // Initializing ArrayLists for field
            sourceChargesLevel3.add(sourceCharge4);
            sourceChargesLevel3.add(sourceCharge2);
            sourceChargesLevel3.add(sourceCharge3);
            sourcePositionsLevel3.add(chargePosition4);
            sourcePositionsLevel3.add(chargePosition1);
            sourcePositionsLevel3.add(chargePosition3);
            
            // Source Charge icons
            for(int i = 0; i < sourcePositionsLevel3.size(); i++){
                double charge = sourceChargesLevel3.get(i);
                Circle icon = new Circle(sourcePositionsLevel3.get(i).getX(), sourcePositionsLevel3.get(i).getY(), 20.0);
                iconsList.add(icon);
                if(charge > 0.0){
                    icon.setFill(AssetManager.getPositiveSourceImage());
                }
                else{
                    icon.setFill(AssetManager.getNegativeSourceImage());
                }
                addToPane(icon);
            }
            
            // Creating vector field grid 
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 9; j++) {
                    GridRegion block = new GridRegion(0 + j * 100, 0 + i * 100, 100);
                    block.setNetElectricFieldVector(sourceChargesLevel3, sourcePositionsLevel3, block.getCenter());  
                    gridPoints.add(block);
                    //System.out.println("X: " + block.getCornerPosition().getX() + "\tY: " + block.getCornerPosition().getY() + "\tEField X: " + block.getElectricFieldVector().getX() + "\tEField Y: " + block.getElectricFieldVector().getY());
                }
            }
            
            // Obstacles and generator and cannon
            
            // Positioning of cannon (constant)
            Vector2D cannonPosition = new Vector2D(10.0, pane.getPrefHeight() / 2 - 24);
            chargeCannon = new Cannon(cannonPosition);
            rectObjectsList.add(chargeCannon);
            addToPane(chargeCannon.getRectangle());
        
            // Generator position
            generator = new Generator(new Vector2D(800, 350));
            rectObjectsList.add(generator);
            addToPane(generator.getRectangle());

            // Ground Wall 
            GroundWall gw = new GroundWall(new Vector2D(350, 320));
            rectObjectsList.add(gw);
            addToPane(gw.getRectangle());

            // Ground Gate
            GroundGate gg1 = new GroundGate(new Vector2D(650, 320));
            rectObjectsList.add(gg1);
            addToPane(gg1.getRectangle());
            GroundGate gg2 = new GroundGate(new Vector2D(550, 450));
            rectObjectsList.add(gg2);
            addToPane(gg2.getRectangle());
            
            // Next Level Button
            nextLevelButton.setVisible(false);
            nextLevelButton.setDisable(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Load all assests
        AssetManager.preloadAllAssets();
        
        // Play soundtrack and set it to repeat
        soundtrackPlayer = new MediaPlayer(AssetManager.getBackgroundMusic());
        soundtrackPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                soundtrackPlayer.seek(Duration.ZERO);
            }
        });
        soundtrackPlayer.setVolume(0.4);
        soundtrackPlayer.play();

        // Window size and Background
        pane.setPrefSize(900, 600);
        pane.setBackground(AssetManager.getBackgroundImage());

        // Time variables
        lastFrameTime = 0.0f;
        long initialTime = System.nanoTime();

        // Controls
        controls.setLayoutX(900);
        controls.setLayoutY(0);
        controls.setPrefSize(200, 600);
        addToPane(controls);
        
        
        // Slider Controls
        // Charge Slider
        chargeSlider.setMajorTickUnit(1.0);
        chargeSlider.setMax(10.0);
        chargeSlider.setMin(-10.0);
        chargeSlider.setValue(0.0);

        // Speed Slider
        speedSlider.setMajorTickUnit(30.0);
        speedSlider.setMax(50.0);
        speedSlider.setMin(0.0);
        speedSlider.setValue(25.0);

        // Angle Slider
        angleSlider.setMajorTickUnit(10.0);
        angleSlider.setMax(Math.PI / 2);
        angleSlider.setMin(-Math.PI / 2);
        angleSlider.setValue(0.0);
        
        // Mass Slider
        massSlider.setMajorTickUnit(1.0);
        massSlider.setMax(5.0);
        massSlider.setMin(1.0);
        massSlider.setValue(0.5);

        addToPane(chargeSlider);
        addToPane(angleSlider);
        addToPane(speedSlider);
        addToPane(massSlider);

        // Fire Button Initialize & Action
        fireButton.setText("Fire!");
        fireButton.setLayoutX(controls.getPrefWidth()/2 - fireButton.getPrefWidth()/2);
        fireButton.setLayoutY(controls.getPrefHeight() - 5);
        fireButton.setPrefWidth(50.0);
        fireButton.setPrefHeight(10);
        fireButton.setVisible(true);
        fireButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(shotsRemaining > 0){
                    
                    // Change shots remaining
                    --shotsRemaining;
                    shotsLabel.setText("Bullets: " + shotsRemaining);
                    
                    // Animate Cannon
                    chargeCannon.setFiringImage();
                    AudioClip fireSound = AssetManager.getCannonShotSound();
                    fireSound.play();
                    countShotTime = true;
                    
                    // Calculate initial values for bullet based on inputs
                    initialSpeed = speedSlider.getValue();
                    firingAngle = -angleSlider.getValue();
                    Vector2D initialPosition = new Vector2D(34.0, pane.getPrefHeight() / 2);
                    Vector2D initialVelocity = new Vector2D(initialSpeed * Math.cos(firingAngle), initialSpeed * Math.sin(firingAngle));

                    bulletCharge = chargeSlider.getValue();
                    bulletMass = massSlider.getValue();
                    Bullet shot = new Bullet(initialPosition, initialVelocity, bulletCharge, bulletMass);
                    circleObjectsList.add(shot);
                    addToPane(shot.getCircle());
                }
            }
        });
        addToPane(fireButton);
        
        // Quit Button Initialize & Action
        quitButton.setText("Quit");
        quitButton.setLayoutX(pane.getPrefWidth() - 75);
        quitButton.setLayoutY(pane.getPrefHeight() - 475);
        quitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ((Stage)pane.getScene().getWindow()).close();
                soundtrackPlayer.stop();
            }
        });
        addToPane(quitButton);
        
        // Next Level Button Initialize & Action
        nextLevelButton.setText("Next Level");
        nextLevelButton.setVisible(false);
        nextLevelButton.setDisable(true);
        nextLevelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                
                // Clear level
                currentKineticEnergy = 0.0;
                gridPoints.clear();
                for(RectangleGameObject rgo: rectObjectsList){
                    removeFromPane(rgo.getRectangle());
                }
                for(CircleGameObject cgo: circleObjectsList){
                    removeFromPane(cgo.getCircle());
                }
                for(Circle c: iconsList){
                    removeFromPane(c);
                }
                iconsList.clear();
                rectObjectsList.clear();
                circleObjectsList.clear();
                messageLabel.setText("Choose your inputs and fire!");
                velocityLabel.setText("Velocity: X: 0.00, Y: 0.00");
                shotsRemaining = AMMO;
                shotsLabel.setText("Bullets: " + shotsRemaining);
                fireButton.setDisable(false);
                
                // Setup next level
                ++currentLevel;
                setUpLevel(currentLevel);
            }
        });
        addToPane(nextLevelButton);
        
        // Help Button Initialize & Action
        helpButton.setText("Help");
        helpButton.setLayoutX(pane.getPrefWidth() - 75);
        helpButton.setLayoutY(pane.getPrefHeight() - 450);
        helpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    Parent root = FXMLLoader.load(getClass().getResource("ChargeCannonHelp.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("User Documentation");
                    stage.setScene(new Scene(root));
                    stage.show();
                }
                catch(IOException io){
                    io.printStackTrace();
                }
            }
        });
        addToPane(helpButton);
        
        // Active Labels
        // Angle
        angleLabel.setText("Angle: 0.0");
        angleLabel.setLayoutX(pane.getPrefWidth() - 670);
        angleLabel.setLayoutY(pane.getPrefHeight() - 20);
        angleSlider.valueProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue a, Object o1, Object o2) {
                double angleInRad = angleSlider.getValue();
                double angleInDegrees = (angleInRad/Math.PI)*180;
                angleLabel.setText("Angle: " + String.format("%.1f", angleInDegrees));
            }
        });
        addToPane(angleLabel);
        
         // Mass
        massLabel.setText("Mass: 1.0");
        massLabel.setLayoutX(pane.getPrefWidth() - 670);
        massLabel.setLayoutY(pane.getPrefHeight() - 495);
        massSlider.valueProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue a, Object o1, Object o2) {
                massLabel.setText("Mass: " + String.format("%.1f", massSlider.getValue()));
            }
        });
        addToPane(massLabel);
        
        // Speed/Power
        speedLabel.setText("Power: 25.0");
        speedLabel.setLayoutX(pane.getPrefWidth() - 470);
        speedLabel.setLayoutY(pane.getPrefHeight() - 20);
        speedSlider.valueProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue a, Object o1, Object o2) {
                speedLabel.setText("Power: " + String.format("%.1f", speedSlider.getValue()));
            }
        });
        addToPane(speedLabel);
        
        // Charge
        chargeLabel.setText("Charge: 0.0");
        chargeLabel.setLayoutX(pane.getPrefWidth() - 270);
        chargeLabel.setLayoutY(pane.getPrefHeight() - 20);
        chargeSlider.valueProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue a, Object o1, Object o2) {
                chargeLabel.setText("Charge: " + String.format("%.1f", chargeSlider.getValue()));
            }
        });
        addToPane(chargeLabel);
        
        
        // Side Panel Controls
        
        // Side Panel Inputs
        // Title
        Label title = new Label("Inputs");
        title.setFont(Font.font(24));
        addToPane(title);
        controls.getChildren().add(title);
        // Labels and Sliders
        controls.getChildren().add(chargeLabel);
        controls.getChildren().add(chargeSlider);
        controls.getChildren().add(speedLabel);
        controls.getChildren().add(speedSlider);
        controls.getChildren().add(angleLabel);
        controls.getChildren().add(angleSlider);
        controls.getChildren().add(massLabel);
        controls.getChildren().add(massSlider);
        controls.getChildren().add(fireButton);
        
        // Side Panel Outputs
        // Title
        Label outputs = new Label("Outputs");
        outputs.setFont(Font.font(24));
        addToPane(outputs);
        controls.getChildren().add(outputs);
        
        // Output Information
        addToPane(genLevelLabel);
        controls.getChildren().add(genLevelLabel);
        velocityLabel.setText("Velocity: X: 0.00, Y: 0.00");
        addToPane(velocityLabel);
        controls.getChildren().add(velocityLabel);
        
        // Message Label
        messageLabel.setText("Choose your inputs and fire!");
        messageLabel.setFont(Font.font("Consolas"));
        messageLabel.setWrapText(true);
        addToPane(messageLabel);
        controls.getChildren().add(messageLabel);
        
        // Other buttons
        controls.getChildren().add(helpButton);
        controls.getChildren().add(quitButton);
        controls.getChildren().add(nextLevelButton);
       
        // Shots Remaining
        shotsLabel.setText("Bullets: " + shotsRemaining);
        shotsLabel.setLayoutX(70);
        shotsLabel.setLayoutY(5);
        shotsLabel.setFont(Font.font(14));
        addToPane(shotsLabel);
        
        controls.setSpacing(8);
        
        // Level Setup
        setUpLevel(currentLevel);

        
        // Animation process (game loop)
        new AnimationTimer() {
            @Override
            public void handle(long now) {

                // Calculation of time difference between frames
                double currentTime = (now - initialTime) / 1000000000.0;
                double frameDeltaTime = currentTime - lastFrameTime;
                lastFrameTime = currentTime;

                // Counter for shot animation
                if (countShotTime) {
                    lastShotTimeDelta += frameDeltaTime;
                }
                if (lastShotTimeDelta >= 1.5) {
                    chargeCannon.setIdleImage();
                    lastShotTimeDelta = 0.0;
                    countShotTime = false;
                }
                
                // Level Win Conditions
                if(currentKineticEnergy >= levelRequirements[currentLevel-1])
                {
                    if(currentLevel < 3){
                        nextLevelButton.setVisible(true);
                        nextLevelButton.setDisable(false);
                        fireButton.setDisable(true);
                        messageLabel.setText("WELL DONE! Press Next Level.");
                    }
                    else{
                        nextLevelButton.setVisible(true);
                        fireButton.setDisable(true);
                        messageLabel.setText("YOU WIN! PRESS QUIT.");
                    }
                }
                
                // Lose condition (out of bullets)
                if(shotsRemaining == 0 && currentKineticEnergy < levelRequirements[currentLevel-1]){
                    boolean endGame = false;
                    fireButton.setDisable(true);
                    messageLabel.setText("OUT OF BULLETS!");
                    for (CircleGameObject c : circleObjectsList){
                        if(c.isMoving()){
                            endGame = false;
                        }
                        else{
                            endGame = true;
                        }
                    }
                    if(endGame){
                        messageLabel.setText("YOU LOSE! PRESS QUIT TO RETURN TO MAIN MENU.");
                    }
                }

                // Update all Circle objects
                for (CircleGameObject c : circleObjectsList) {
                    if (c instanceof Bullet) {
                        // Check the bullet position in field
                        Vector2D bulletPosition = c.getPosition();
                        
                        // Collision detections
                        for(RectangleGameObject rgo: rectObjectsList){
                            //Vector2D directional = new Vector2D(rgo.getCenter().getX()-bulletPosition.getX(), rgo.getCenter().getY()-bulletPosition.getY());
                            Vector2D rectCorner = rgo.getPosition();
                            if(!(rgo instanceof Cannon) && bulletPosition.getX() > rectCorner.getX() && bulletPosition.getX() < rectCorner.getX() + rgo.getWidth() && bulletPosition.getY() > rectCorner.getY() && bulletPosition.getY() < rectCorner.getY() + rgo.getHeight()){
                                if(rgo instanceof GroundWall){
                                    if(!((Bullet) c).isDestroyed()){
                                        AudioClip fizzle = AssetManager.getGroundWallSound();
                                        fizzle.play();
                                    }
                                    ((Bullet) c).destroy();
                                }
                                else if(rgo instanceof GroundGate){
                                    AudioClip discharge = AssetManager.getGateDischargeSound();
                                    discharge.play();
                                    ((Bullet) c).neutralize();
                                }
                                else{
                                    if(!((Bullet) c).isDestroyed()){
                                        AudioClip genHit = AssetManager.getGeneratorSound();
                                        genHit.play();
                                        
                                        // Calculate kinetic energy & update label
                                        Vector2D velocity = ((Bullet) c).getVelocity();
                                        double mass = ((Bullet) c).getMass();
                                        double kineticEnergyTransferred = Physics.calcKineticEnergy(velocity, mass);
                                        currentKineticEnergy += kineticEnergyTransferred;
                                        genLevelLabel.setText("Generator Charge: " + String.format("%.1f",currentKineticEnergy) + "/" + levelRequirements[currentLevel-1]); 
                                    }
                                    ((Bullet) c).destroy();
                                }
                            }
                        }
                        
                        // Depending on bullet position, update with the right parameters
                        for (GridRegion gr : gridPoints) {  
                            Vector2D corner = gr.getCornerPosition();
                            if (!((Bullet) c).isDestroyed() && bulletPosition.getX() >= corner.getX() && bulletPosition.getX() < corner.getX() + gr.getSideLength() && bulletPosition.getY() >= corner.getY() && bulletPosition.getY() < corner.getY() + gr.getSideLength()) {
                                Vector2D eField = gr.getElectricFieldVector(); 
                                c.update(frameDeltaTime,((Bullet) c).getCharge(), eField, ((Bullet) c).getMass());
                                // Test
                                velocityLabel.setText("Velocity: X: "+ String.format("%.2f", c.getVelocity().getX()) + ", Y: " + String.format("%.2f", c.getVelocity().getY()));
                                break;
                            } 
                            else {
                                c.update(frameDeltaTime);
                            }
                        }
                    }

                }
            }
        }.start();
    }
    
    public static MediaPlayer getMediaPlayer(){
        return soundtrackPlayer;
    }
}
