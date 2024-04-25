/**
 * Package bricker.brick_strategies contains classes related to
 * different collision strategies for bricks.
 */
package bricker.brick_strategies;

import bricker.gameobjects.Ball;
import bricker.ui.Life;
import danogl.GameManager;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.util.Counter;
import danogl.util.Vector2;
import java.util.Random;

/**
 * The BrickedStrategyFactory class is responsible for creating
 * various collision strategies for bricks based on game conditions.
 * @author Shay Abbou & Shaked Hartal
 */
public class BrickedStrategyFactory {
    private static final int MAX_SPECIAL_STRATEGY = 5;
    private static final int MIN_SPECIAL_STRATEGY = 4;
    private static final int MAX_DOUBLE_STRATEGIES = 2;
    private Counter counter;
    private Random rand;
    private CollisionStrategy basic;
    private CollisionStrategy puck;
    private CollisionStrategy paddle;
    private CollisionStrategy camera;
    private CollisionStrategy heart;
    private CollisionStrategy doubleStrategyObject;
    private  CollisionStrategy[] strategiesObjects;
    private int doubleStrategiesCounter;

    /**
     * Constructs a BrickedStrategyFactory with the specified parameters.
     * @param gameObjects        The collection of GameObjects in the game.
     * @param counter            The counter to keep track on the number of bricks
     * @param ballDimensions     The dimensions of the ball.
     * @param imageReader        The image reader for loading images.
     * @param soundReader        The sound reader for loading sounds.
     * @param ballSpeed          The speed of the ball.
     * @param windowDimensions   The dimensions of the game window.
     * @param paddleWidth        The width of the paddle.
     * @param paddleHeight       The height of the paddle.
     * @param inputListener      The user input listener.
     * @param gameManager        The game manager.
     * @param ball               The ball GameObject.
     * @param heartDimensions    The dimensions of the heart GameObject.
     * @param life               The life GameObject.
     */
    public BrickedStrategyFactory(GameObjectCollection gameObjects, Counter counter,
                                  Vector2 ballDimensions, ImageReader imageReader, SoundReader soundReader,
                                  float ballSpeed, Vector2 windowDimensions, int paddleWidth,
                                  int paddleHeight, UserInputListener inputListener,
                                  GameManager gameManager, Ball ball,
                                  Vector2 heartDimensions, Life life) {
        basic = new BasicCollisionStrategy(gameObjects, counter);
        puck = new PuckStrategy(gameObjects,ballDimensions,
                imageReader,soundReader,ballSpeed,windowDimensions,basic);
        paddle = new ExtraPaddleStrategy(gameObjects,imageReader, windowDimensions,
                paddleWidth, paddleHeight, inputListener,basic);
        camera = new CameraStrategy(gameManager , windowDimensions ,ball, basic);
        heart = new HeartStrategy(gameObjects,heartDimensions
                ,imageReader,windowDimensions,life,basic);
        this.counter = counter;
        this.rand = new Random();
        this.strategiesObjects = new CollisionStrategy [] {puck,paddle,camera,heart};

    }
    /**
     * Gets a random collision strategy based on game conditions.
     * Each special strategy (Camera, Puck, Double, Extra Paddle, Heart) has a 10% probability
     * of being chosen,while the Basic strategy has a 50% probability.
     * @return A CollisionStrategy based on the game conditions.
     */
    public CollisionStrategy getStrategy(){
        int i = rand.nextInt(10);
        switch (i){
            case 0:
                return puck;
            case 1:
                return paddle;
            case 2:
                return camera;
            case 3:
                return heart;
            case 4:
                doubleStrategiesCounter = 0;
                doubleStrategyObject = createDouble();
                return doubleStrategyObject;
            default:
                return basic;
        }
    }

    private DoubleBehaviorStrategy createDouble(){
        doubleStrategiesCounter++;
        CollisionStrategy collisionStrategy1 = randomDoubleStrategy();
        CollisionStrategy collisionStrategy2 = randomDoubleStrategy();
        return new DoubleBehaviorStrategy
                (counter,collisionStrategy1,collisionStrategy2);
    }

    private CollisionStrategy randomDoubleStrategyOutOfFour(){
        int randomIndex = rand.nextInt(MIN_SPECIAL_STRATEGY);
        return strategiesObjects[randomIndex];
    }

    private CollisionStrategy randomDoubleStrategy(){
        // Check if double strategies are not allowed
        if(doubleStrategiesCounter >= MAX_DOUBLE_STRATEGIES){
            return randomDoubleStrategyOutOfFour();
        }
        // Check if double strategies are allowed, and the probability of getting double is 1/5
        else if (rand.nextInt(MAX_SPECIAL_STRATEGY) < 1){
            return createDouble();
        }
        // Return a random strategy out of the four basic strategies
        else{
            return randomDoubleStrategyOutOfFour();
        }
    }

}
