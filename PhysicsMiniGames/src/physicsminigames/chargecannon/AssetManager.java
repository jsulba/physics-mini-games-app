/*
 *Authors: Jose Fernandez et Lin Xiao Zheng
 */
package physicsminigames.chargecannon;

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

/**
 *
 * @author thund
 */
public class AssetManager {
    static private Background backgroundImage = null;
    private static ImagePattern gameplay = null;
    static private ArrayList<ImagePattern> bullets = new ArrayList<>();
    static private ImagePattern chargeCannonShooting = null;
    static private ImagePattern chargeCannonIdle = null;
    static private ImagePattern groundGate = null;
    static private ImagePattern bulletCollision = null;
    static private ImagePattern groundWall = null;
    static private ImagePattern generator = null;
    static private ImagePattern positiveSource = null;
    static private ImagePattern negativeSource = null;
    
    static private Media backgroundMusic = null;
    static private AudioClip cannonShot = null;
    static private AudioClip groundWallFizzle = null;
    static private AudioClip generatorHit = null;
    static private AudioClip gateDischarge = null;
    
    
    static private String fileURL(String relativePath)
    {
        return new File(relativePath).toURI().toString();
    }
    
    static public void preloadAllAssets()
    {
        // Preload all images
        Image background = new Image(fileURL("./assets/charge_cannon/backgrounds/metal.png"));
        backgroundImage = new Background(
                            new BackgroundImage(background, 
                                                BackgroundRepeat.SPACE, 
                                                BackgroundRepeat.SPACE, 
                                                BackgroundPosition.DEFAULT,
                                                BackgroundSize.DEFAULT));
        
        gameplay = new ImagePattern(new Image(fileURL("./assets/charge_cannon/backgrounds/gameplay.png")));
        
        bullets.add(new ImagePattern(new Image(fileURL("./assets/charge_cannon/sprites/neutra.png"))));
        bullets.add(new ImagePattern(new Image(fileURL("./assets/charge_cannon/sprites/posit.png"))));
        bullets.add(new ImagePattern(new Image(fileURL("./assets/charge_cannon/sprites/tegan.png"))));
        
        
        generator = new ImagePattern(new Image(fileURL("./assets/charge_cannon/sprites/generator.gif")));
        chargeCannonShooting = new ImagePattern(new Image(fileURL("./assets/charge_cannon/sprites/charge_cannon.gif")));
        chargeCannonIdle = new ImagePattern(new Image(fileURL("./assets/charge_cannon/sprites/charge_cannon_idle.png")));
        groundGate = new ImagePattern (new Image(fileURL("./assets/charge_cannon/sprites/ground_portal.gif")));
        bulletCollision = new ImagePattern (new Image(fileURL("./assets/charge_cannon/sprites/bullet_collision.png")));
        groundWall = new ImagePattern (new Image(fileURL("./assets/charge_cannon/sprites/ground_wall.gif")));
        positiveSource = new ImagePattern (new Image(fileURL("./assets/charge_cannon/sprites/positive_source.png")));
        negativeSource = new ImagePattern (new Image(fileURL("./assets/charge_cannon/sprites/negative_source.png")));
   
        
        // Preload all music tracks
        backgroundMusic = new Media(fileURL("./assets/charge_cannon/soundtracks/Roses.mp3"));

        // Preload all sound effects
        cannonShot = new AudioClip(fileURL("./assets/charge_cannon/sound_fx/cannon_shot.wav"));
        generatorHit =  new AudioClip(fileURL("./assets/charge_cannon/sound_fx/generator_hit.wav"));
        groundWallFizzle = new AudioClip(fileURL("./assets/charge_cannon/sound_fx/ground_wall_fizzle.wav"));
        gateDischarge = new AudioClip(fileURL("./assets/charge_cannon/sound_fx/portal_discharge.wav"));
    }
    
    static public Background getBackgroundImage()
    {
        return backgroundImage;        
    }
    
    static public ImagePattern getGameplayScreenShot(){
        return gameplay;
    }
    
    static public ImagePattern getChargeCannonIdleImage(){
        return chargeCannonIdle;
    }
    
    static public ImagePattern getPositiveSourceImage(){
        return positiveSource;
    }
    
    static public ImagePattern getNegativeSourceImage(){
        return negativeSource;
    }
    
    static public ImagePattern getBulletImage(int index)
    {
        return bullets.get(index);
    }
    
    static public ImagePattern getGeneratorImage(){        
        return generator;
    }
    
    static public ImagePattern getChargeCannonShootingImage()
    {
        return chargeCannonShooting;
    }
    
    static public ImagePattern getGroundGateImage(){
        return groundGate;
    }
    
    static public ImagePattern getGroundWallImage(){
        return groundWall;
    }
    
    static public ImagePattern getBulletCollisionImage(){
        return bulletCollision;
    }

    static public Media getBackgroundMusic()
    {
        return backgroundMusic;
    }
    
    static public AudioClip getCannonShotSound()
    {
        return cannonShot;
    }
    
    static public AudioClip getGeneratorSound()
    {
        return generatorHit;
    }
    
    static public AudioClip getGroundWallSound()
    {
        return groundWallFizzle;
    }
    
    static public AudioClip getGateDischargeSound()
    {
        return gateDischarge;
    }
}
