package bricker.main;

import bricker.brick_strategies.BrickedStrategyFactory;
import bricker.gameobjects.*;
import bricker.ui.Life;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.Camera;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * The `BrickerGameManager` class is the main class managing the Bricker game. It extends
 * the `GameManager` class and handles the initialization, updating, and checking for game end
 * conditions. This class creates and manages various game objects such as the ball, paddles,
 * bricks, borders, and life elements.
 * @author Shay Abbou & Shaked Hartal
 */
public class BrickerGameManager extends GameManager {
    private static final int BORDER_WIDTH = 10;
    private static final int PADDLE_HEIGHT = 15;
    private static final int PADDLE_WIDTH = 100;
    private static final int BALL_RADIUS = 20;
    private static final float BALL_SPEED = 250;
    private static final Renderable BORDER_RENDERABLE = null;
    private static final float BRICK_HEIGHT = 15;
    private static final int START_LIFE = 3;
    private static final int MAX_LIFE = 4;
    private static final int  ROWS_OF_BRICKS = 7;
    private static final int BRICKS_IN_ROW = 8;
    private static final Vector2 HEART_DIMENSIONS = new Vector2(20, 15);
    private static final int WINDOW_WIDTH = 700;
    private static final int WINDOW_HEIGHT = 500;
    private int rowsOfBricks, bricksInRow;
    private Ball ball;
    private Vector2 windowDimensions;
    private WindowController windowController;
    private Life life;
    private Counter counter = new Counter();
    private UserInputListener inputListener;
    private int cameraCollisionCounter;

    /**
     * Constructs a new `BrickerGameManager` with the specified window title, window dimensions,
     * rows of bricks, and bricks in a row.
     * @param windowTitle      The title of the game window.
     * @param windowDimensions The dimensions of the game window.
     * @param rowsOfBricks     The number of rows of bricks in the game.
     * @param bricksInRow      The number of bricks in each row.
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions, int rowsOfBricks,
                              int bricksInRow) {
        super(windowTitle, windowDimensions);
        this.rowsOfBricks = rowsOfBricks;
        this.bricksInRow = bricksInRow;
    }

    /**
     * Initializes the game by creating and configuring various game objects.
     * @param imageReader      The image reader for loading game images.
     * @param soundReader      The sound reader for loading game sounds.
     * @param inputListener    The input listener for handling user input.
     * @param windowController The window controller for managing the game window.
     */
    @Override
    public void initializeGame(ImageReader imageReader,
                               SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {
        this.windowController = windowController;
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        windowDimensions = windowController.getWindowDimensions();
        this.inputListener = inputListener;
        createBackground(imageReader);
        createBall(imageReader, soundReader, windowController);
        Renderable paddleImage = imageReader.readImage(
                "assets/paddle.png", true);
        createUserPaddle(paddleImage, inputListener, windowDimensions);
        createBorders(windowDimensions);
        Renderable heartImage = imageReader.readImage(
                "assets/heart.png", true);
        life = new Life(new Vector2(BORDER_WIDTH, windowDimensions.y()-20),
                HEART_DIMENSIONS, heartImage,gameObjects(), START_LIFE, MAX_LIFE);
        BrickedStrategyFactory brickedStrategyFactory = new BrickedStrategyFactory
                (gameObjects(), counter, ball.getDimensions(),imageReader, soundReader,
                        BALL_SPEED,windowDimensions, PADDLE_WIDTH, PADDLE_HEIGHT, inputListener,
                this,ball,HEART_DIMENSIONS,life);
        createBrick(imageReader, brickedStrategyFactory);
    }

    /**
     * Overrides the update method to include additional logic for checking the game's end condition.
     * @param deltaTime The time passed since the last update,
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        checkForGameEnd();
        if(ball.getCollisionCounter() - cameraCollisionCounter > 4){
            setCamera(null);
        }
    }

    /**
     * The main method for starting the Bricker game. It initializes the game manager with the specified
     * window title, window dimensions, rows of bricks, and bricks in a row. If no command-line arguments
     * are provided, default values are used.
     *
     * @param args Command-line arguments specifying the number of bricks in a row and rows of bricks.
     *             If not provided, default values are used.
     */
    public static void main(String[] args) {
        int bricksInRow;
        int rowsOfBricks;
        if (args.length == 0){
            bricksInRow = ROWS_OF_BRICKS;
            rowsOfBricks = BRICKS_IN_ROW;
        }
        else{
            bricksInRow = Integer.parseInt(args[0]);
            rowsOfBricks = Integer.parseInt(args[1]);
        }
        new BrickerGameManager(
                "Bouncing Ball",
                new Vector2(WINDOW_WIDTH, WINDOW_HEIGHT), rowsOfBricks, bricksInRow).run();
    }

    @Override
    public void setCamera(Camera camera) {
        super.setCamera(camera);
        if(ball!=null) {
            this.cameraCollisionCounter = ball.getCollisionCounter();
        }
    }

    private void checkForGameEnd() {
        float ballHeight = ball.getCenter().y();
        String prompt = "";
        if(ballHeight > windowDimensions.y()) {
            if (life.getRemainingLives() == 1) {
                //we lost
                prompt = "You Lose!";
            }
            else {
                ball.setCenter(windowDimensions.mult(0.5f));
                setBallDirections();
                life.removeLife();
            }
        }
        if(counter.value() <= 0 || inputListener.isKeyPressed(KeyEvent.VK_W)){
            //we won
            prompt = "You Win!";
        }
        if (!prompt.isEmpty()) {
            prompt += " Play again?";
            if (windowController.openYesNoDialog(prompt)) {
                counter.reset();
                windowController.resetGame();
            }
            else
                windowController.closeWindow();
        }
    }
    private void setBallDirections(){
            float ballVelX = BALL_SPEED;
            float ballVelY = BALL_SPEED;
            Random rand = new Random();
            if(rand.nextBoolean())
                ballVelX *= -1;
            if(rand.nextBoolean())
                ballVelY *= -1;
            ball.setVelocity(new Vector2(ballVelX, ballVelY));
    }
    private void createBall(ImageReader imageReader, SoundReader soundReader,
                            WindowController windowController) {
        Renderable ballImage =
                imageReader.readImage("assets/ball.png", true);
        Sound collisionSound = soundReader.readSound("assets/blop.wav");
        ball = new Ball(
                Vector2.ZERO, new Vector2(BALL_RADIUS, BALL_RADIUS), ballImage, collisionSound);

        Vector2 windowDimensions = windowController.getWindowDimensions();
        ball.setCenter(windowDimensions.mult(0.5f));
        gameObjects().addGameObject(ball);
        setBallDirections();
    }

    private void createUserPaddle(Renderable paddleImage, UserInputListener inputListener,
                                  Vector2 windowDimensions) {
        GameObject userPaddle = new Paddle(
                Vector2.ZERO,
                new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT),
                paddleImage,
                inputListener, windowDimensions);
        userPaddle.setCenter(
                new Vector2(windowDimensions.x()/2, (int)windowDimensions.y()-30));
        gameObjects().addGameObject(userPaddle);
    }

    private void createBorders(Vector2 windowDimensions) {
        gameObjects().addGameObject(
                new GameObject(
                        Vector2.ZERO,
                        new Vector2(BORDER_WIDTH, windowDimensions.y()),
                        BORDER_RENDERABLE), Layer.STATIC_OBJECTS);
        gameObjects().addGameObject(
                new GameObject(
                        new Vector2(windowDimensions.x()-BORDER_WIDTH, 0),
                        new Vector2(BORDER_WIDTH, windowDimensions.y()),
                        BORDER_RENDERABLE), Layer.STATIC_OBJECTS);
        gameObjects().addGameObject(
                new GameObject(
                        Vector2.ZERO,
                        new Vector2(windowDimensions.x(), BORDER_WIDTH),
                        BORDER_RENDERABLE), Layer.STATIC_OBJECTS);
    }

    private void createBackground(ImageReader imageReader){
        Renderable backgroundImage = imageReader.readImage(
                "assets/DARK_BG2_small.jpeg", false);
        GameObject background = new GameObject(Vector2.ZERO, windowDimensions, backgroundImage);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(background, Layer.BACKGROUND);
    }

    private void createBrick(ImageReader imageReader, BrickedStrategyFactory brickedStrategyFactory){
        Renderable brickImage = imageReader.readImage("assets/brick.png", true);
        float brickWidth = (windowDimensions.x() - (BORDER_WIDTH*2)) / bricksInRow;
        Vector2 topLeftCorner = new Vector2(BORDER_WIDTH, BORDER_WIDTH);
        for (int i = 0; i < rowsOfBricks; i++) {
            for (int j = 0; j < bricksInRow; j++) {
                GameObject brick = new Brick(topLeftCorner, new Vector2(brickWidth, BRICK_HEIGHT),
                        brickImage, brickedStrategyFactory.getStrategy());
                gameObjects().addGameObject(brick,Layer.STATIC_OBJECTS);
                topLeftCorner = new Vector2(topLeftCorner.x() + brickWidth + 1, topLeftCorner.y());
                counter.increment();
            }
            topLeftCorner = new Vector2(BORDER_WIDTH, topLeftCorner.y() + BRICK_HEIGHT + 1);
        }
    }
}
