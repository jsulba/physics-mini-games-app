/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physicsminigames.projectileshooter;

/**
 *
 * @author cstuser
 */
import java.io.File;
import javafx.beans.property.ObjectProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
 * @author Family Desktop
 */
public class AssetManager {
    
    static public Image imageHelpTop;
    static public Image imageHelpBottom;
    
    static public  Image gunFire_Img;
    static public  Image gunIce_Img;
    static public  Image gunAntiGravity_Img;
    static public  Image gunPortalIn_Img;
    static public  Image gunPortalOut_Img;

    //Barriers
    static private ImagePattern barrierIce2;
    static private ImagePattern barrierIce1;
    static private ImagePattern barrierFire;

    //Projectiles
    static private ImagePattern ballIce;
    static private ImagePattern ballFire;
    static private ImagePattern ballPortalIn;
    static private ImagePattern ballPortalOut;
    static private ImagePattern ballAntiGravity;


    //Portals
    static private ImagePattern portalIn;
    static private ImagePattern portalOut;
    
    //AntiGravity
    static private ImagePattern antiGravitySide;

    //Player
    static private Image characterImage;

    //Enemy
    static private ImagePattern enemyPattern;
    static private ImagePattern enemyFrozen;
    static private ImagePattern enemyBurning;
    
    //miscellaneous
    static private ImagePattern edge;

    //--------END SPRITES--------

    //-------START SOUNDS--------
    
    //--------Music

    static private Media backgroundMusic = null;

    //--------Sound Effects

    //Shot sound effects
    static private AudioClip shotFire;
    static private AudioClip shotIce;
    static private AudioClip shotPortal;
    static private AudioClip shotAntiGravity;

    //Enemy collision sound effects
    static private AudioClip hitFire;
    static private AudioClip hitIce;
    static private AudioClip hitPortal;
    static private AudioClip hitAntiGravity;

    //Enviornment collision sound effects
    static private AudioClip bounce;
    static private AudioClip iceHitsFireBarrier;
    static private AudioClip iceHitsIceBarrier;
    static private AudioClip fireHitsIceBarrier;
    static private AudioClip activateAntiGravity;
    static private AudioClip activatePortal;
    static private AudioClip teleport;


    //Barrier Destruction sound effects
    static private AudioClip destroyIceBarrier;
    static private AudioClip destroyFireBarrier;
    
    //Miscellaneous sound effects
    static private AudioClip removeProjectile = null;

    //-------END SOUNDS-------


    //Background
    static private Background backgroundImage = null;

    static private String fileURL(String relativePath)
    {
        return new File(relativePath).toURI().toString();
    }

    static public void preloadAllAssets()
    {
        imageHelpTop = new Image(fileURL("./assets/projectile_shooter/HelpMenuImages/page1.png"));
        imageHelpBottom = new Image(fileURL("./assets/projectile_shooter/HelpMenuImages/page2.png"));
        
        Image background = new Image(fileURL("./assets/projectile_shooter/Backgrounds/backgroundImage.gif"));

        backgroundImage = new Background(
                                    new BackgroundImage(background,
                                    BackgroundRepeat.NO_REPEAT,
                                    BackgroundRepeat.NO_REPEAT,
                                    BackgroundPosition.DEFAULT,
                                    new BackgroundSize(1, 1, true, true, true, true)));

        //-----------IMAGES-----------------
        //Preload all gun image assets
        gunFire_Img = new Image(fileURL("./assets/projectile_shooter/Guns/gunFire.gif"));
        gunIce_Img = new Image(fileURL("./assets/projectile_shooter/Guns/gunIce.gif"));
        gunAntiGravity_Img = new Image(fileURL("./assets/projectile_shooter/Guns/gunAntiGravity.gif"));
        gunPortalIn_Img = new Image(fileURL("./assets/projectile_shooter/Guns/gunPortalIn.gif"));
        gunPortalOut_Img = new Image(fileURL("./assets/projectile_shooter/Guns/gunPortalOut.gif"));

        //Preload all barrier assets
        barrierFire = new ImagePattern(new Image(fileURL("./assets/projectile_shooter/Barriers/barrierFire.gif")));
        barrierIce2 = new ImagePattern(new Image(fileURL("./assets/projectile_shooter/Barriers/barrierIce2.gif"))); //full heath
        barrierIce1 = new ImagePattern(new Image(fileURL("./assets/projectile_shooter/Barriers/barrierIce1.gif"))); //hit once    

        //Preload all porjectile assets
        ballFire = new ImagePattern(new Image(fileURL("./assets/projectile_shooter/Projectiles/ballFire.gif")));
        ballIce = new ImagePattern(new Image(fileURL("./assets/projectile_shooter/Projectiles/ballIce.gif")));
        ballPortalOut = new ImagePattern(new Image(fileURL("./assets/projectile_shooter/Projectiles/ballPortalOut.gif")));
        ballPortalIn = new ImagePattern(new Image(fileURL("./assets/projectile_shooter/Projectiles/ballPortalIn.gif")));
        ballAntiGravity = new ImagePattern(new Image(fileURL("./assets/projectile_shooter/Projectiles/ballAntiGravity.gif")));

        //Preload all portal assets
        portalIn = new ImagePattern(new Image(fileURL("./assets/projectile_shooter/Portals/portalIn.gif")));
        portalOut = new ImagePattern(new Image(fileURL("./assets/projectile_shooter/Portals/portalOut.gif")));
        
        //Preload antiGravitySide asset
        antiGravitySide = new ImagePattern(new Image(fileURL("./assets/projectile_shooter/AntiGravity/antiGravitySide.png")));

        //Preload character asset
        characterImage = new Image(fileURL("./assets/projectile_shooter/Characters/character.gif"));

        //Preload Enemy asset
        enemyPattern = new ImagePattern(new Image(fileURL("./assets/projectile_shooter/Enemies/enemy.gif")));
        enemyBurning = new ImagePattern(new Image(fileURL("./assets/projectile_shooter/Enemies/enemyBurning.gif")));
        enemyFrozen = new ImagePattern(new Image(fileURL("./assets/projectile_shooter/Enemies/enemyFrozen.png")));

        //Preload music
        backgroundMusic = new Media(fileURL("./assets/projectile_shooter/Music/background_music.mp3"));
        
        //preload miscellaneous assets
        edge = new ImagePattern(new Image(fileURL("./assets/projectile_shooter/Misc/edgeTest.png")));       
        
        
        //------------SOUNDS--------------
        
        //Preload Shooting sounds
        shotFire = new AudioClip(fileURL("./assets/projectile_shooter/Sound Effects/Fire/shotFire.wav"));        
        shotIce = new AudioClip(fileURL("./assets/projectile_shooter/Sound Effects/Ice/shotIce.wav"));
        shotAntiGravity = new AudioClip(fileURL("./assets/projectile_shooter/Sound Effects/AntiGravity/shotAntiGravity.wav"));
        shotPortal = new AudioClip(fileURL("./assets/projectile_shooter/Sound Effects/Portal/shotPortal.wav"));
        
        
        //Preload projectile hitting enemy sounds
        hitFire = new AudioClip(fileURL("./assets/projectile_shooter/Sound Effects/Fire/fireHitsEnemy.wav"));
        hitIce = new AudioClip(fileURL("./assets/projectile_shooter/Sound Effects/Ice/IceHitsEnemy.wav"));
        hitPortal = new AudioClip(fileURL("./assets/projectile_shooter/Sound Effects/Misc/removeProjectile.wav"));
        hitAntiGravity = new AudioClip(fileURL("./assets/projectile_shooter/Sound Effects/Misc/removeProjectile.wav"));
        
        //Preload portal sounds
        activatePortal = new AudioClip(fileURL("./assets/projectile_shooter/Sound Effects/Portal/portalOpening.wav"));
        teleport = new AudioClip(fileURL("./assets/projectile_shooter/Sound Effects/Portal/portalTeleport.wav"));
        
        //Preload antigravity and portal activation sounds
        activatePortal = new AudioClip(fileURL("./assets/projectile_shooter/Sound Effects/Portal/portalOpening.wav"));
        activateAntiGravity = new AudioClip(fileURL("./assets/projectile_shooter/Sound Effects/AntiGravity/antigravityActivating.wav"));
        
        //Preload Miscellaneous Sound Effects
        removeProjectile = new AudioClip(fileURL("./assets/projectile_shooter/Sound Effects/Misc/removeProjectile.wav"));
        bounce = new AudioClip(fileURL("./assets/projectile_shooter/Sound Effects/Misc/bounce.wav"));
        
        //Preload barrier/projectile collision sound effects
        iceHitsFireBarrier = new AudioClip(fileURL("./assets/projectile_shooter/Sound Effects/Ice/IceHitsFireBarrier.wav"));
        iceHitsIceBarrier = new AudioClip(fileURL("./assets/projectile_shooter/Sound Effects/Ice/IceHitsIceBarrier.wav"));
        
        fireHitsIceBarrier = new AudioClip(fileURL("./assets/projectile_shooter/Sound Effects/Fire/fireHitsIceBarrier.wav"));        
    }

    static public Image getImageHelpTop() {return imageHelpTop;}

    static public Image getImageHelpBottom() {return imageHelpBottom;}
    
    static public Background getBackground(){return backgroundImage;}
    
    //Getters for all barrier assets
    static public ImagePattern getBarrierFire(){return barrierFire;}
    static public ImagePattern getBarrierIce1(){return barrierIce1;}
    static public ImagePattern getBarrierIce2(){return barrierIce2;}

    //Getters for all projectile assets
    static public ImagePattern getBallFire(){return ballFire;}
    static public ImagePattern getBallIce(){return ballIce;}
    static public ImagePattern getBallPortalOut(){return ballPortalOut;}
    static public ImagePattern getBallPortalIn(){return ballPortalIn;}
    static public ImagePattern getBallAntiGravity(){return ballAntiGravity;}

    //Getters for portal assets
    static public ImagePattern getPortalIn(){return portalIn;}
    static public ImagePattern getPortalOut(){return portalOut;}
    
    //Getter for antigravity Asset
    static public ImagePattern getAntiGravitySide() {return antiGravitySide;}

    //Getter for Character asset
    static public Image getCharacterImage(){return characterImage;}

    //Getter for Enemy asset
    static public ImagePattern getEnemyPattern(){return enemyPattern;}
    static public ImagePattern getEnemyBurning() {return enemyBurning;}
    static public ImagePattern getEnemyFrozen() {return enemyFrozen;}
    
    //Getter for miscellaneous
    static public ImagePattern getEdgePattern() {return edge;}

    //Getters for Sound Files
    static public Media getBackgroundMusic(){return backgroundMusic;}
    static public AudioClip getBounce() {return bounce;}
    
    //Getters for Images for ImageView of gun    
    public static Image getGunFire_Img(){return gunFire_Img;}
    public static Image getGunIce_Img() {return gunIce_Img;}    
    public static Image getGunAntiGravity_Img(){return gunAntiGravity_Img;}   
    public static Image getGunPortalIn_Img(){return gunPortalIn_Img;}    
    public static Image getGunPortalOut_Img(){return gunPortalOut_Img;}
    
    //Getters for Shooting sounds
    public static AudioClip getShotFire(){return shotFire;}
    public static AudioClip getShotIce(){return shotIce;}
    public static AudioClip getShotPortal(){return shotPortal;}
    public static AudioClip getShotAntiGravity(){return shotAntiGravity;}

    //Getters for projectile impact sounds
    public static AudioClip getHitFire(){return hitFire;}
    public static AudioClip getHitIce(){return hitIce;}
    public static AudioClip getHitPortal(){return hitPortal;}
    public static AudioClip getHitAntiGravity(){return hitAntiGravity;}

    //Getters for collisions with barriers
    public static AudioClip getIceHitsFireBarrier(){return iceHitsFireBarrier;}
    public static AudioClip getIceHitsIceBarrier(){return iceHitsIceBarrier;}
    public static AudioClip getFireHitsIceBarrier(){return fireHitsIceBarrier;}

    //Getters for AntiGravity and Portal sounds
    public static AudioClip getActivateAntiGravity(){return activateAntiGravity;}
    public static AudioClip getActivatePortal(){return activatePortal;}
    public static AudioClip getTeleport(){return teleport;}

    //Getters for barrier destruction sounds
    public static AudioClip getDestroyIceBarrier(){return destroyIceBarrier;}
    public static AudioClip getDestroyFireBarrier(){return destroyFireBarrier;}
    public static AudioClip getRemoveProjectile(){return removeProjectile;}    
}
