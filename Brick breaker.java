import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BrickBreaker extends JFrame implements KeyListener {
    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    private final int PADDLE_WIDTH = 100;
    private final int PADDLE_HEIGHT = 20;
    private final int BALL_DIAMETER = 20;
    private final int BRICK_WIDTH = 80;
    private final int BRICK_HEIGHT = 30;
    private final int NUM_BRICKS = 50;
    private final int PADDLE_SPEED = 10;
    private final int BALL_SPEED = 5;

    private int paddleX = WIDTH / 2 - PADDLE_WIDTH / 2;
    private int paddleY = HEIGHT - 100;
    private int ballX = WIDTH / 2 - BALL_DIAMETER / 2;
    private int ballY = HEIGHT / 2 - BALL_DIAMETER / 2;
    private int ballXDir = 1;
    private int ballYDir = -1;
    private boolean isRunning = true;
    private boolean isGameOver = false;

    private Rectangle paddle = new Rectangle(paddleX, paddleY, PADDLE_WIDTH, PADDLE_HEIGHT);
    private Rectangle ball = new Rectangle(ballX, ballY, BALL_DIAMETER, BALL_DIAMETER);
    private Rectangle[] bricks = new Rectangle[NUM_BRICKS];

    public BrickBreaker() {
        setTitle("Brick Breaker");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        addKeyListener(this);
        setVisible(true);

        for (int i = 0; i < NUM_BRICKS; i++) {
            bricks[i] = new Rectangle((i % 10) * (BRICK_WIDTH + 5), (i / 10) * (BRICK_HEIGHT + 5) + 50, BRICK_WIDTH, BRICK_HEIGHT);
        }

        Timer timer = new Timer(10, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!isGameOver) {
                    ball.x += ballXDir * BALL_SPEED;
                    ball.y += ballYDir * BALL_SPEED;

                    if (ball.intersects(paddle)) {
                        ballYDir = -1;
                    }

                    for (int i = 0; i < NUM_BRICKS; i++) {
                        if (ball.intersects(bricks[i])) {
                            bricks[i] = new Rectangle();
                            ballYDir *= -1;
                        }
                    }

                    if (ball.x <= 0 || ball.x + BALL_DIAMETER >= WIDTH) {
                        ballXDir *= -1;
                    }

                    if (ball.y <= 0) {
                        ballYDir *= -1;
                    }

                    if (ball.y + BALL_DIAMETER >= HEIGHT) {
                        isGameOver = true;
                    }

                    if (paddle.x <= 0 || paddle.x + PADDLE_WIDTH >= WIDTH) {
                        paddleX = WIDTH / 2 - PADDLE_WIDTH / 2;
                    }

                    repaint();
                }
            }
        });

        timer.start();
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.setColor(Color.WHITE);
        g.fillRect(paddleX, paddleY, PADDLE_WIDTH, PADDLE_HEIGHT);
        g.fillOval(ball.x, ball.y, BALL_DIAMETER, BALL_DIAMETER);

        for (int i = 0; i < NUM_BRICKS; i++) {
            if (bricks[i].width > 0) {
                g.fillRect(bricks[i].x, bricks[i].y, BRICK_WIDTH, BRICK_HEIGHT);
            }
        }

        if (isGameOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.drawString("Game Over", WIDTH / 2 - 100, HEIGHT / 2);
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            paddleX -= PADDLE_SPEED;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            paddleX += PADDLE_SPEED;
        }

        if (paddleX < 0) {
            paddleX = 0;
        } else if (paddleX + PADDLE_WIDTH > WIDTH) {
            paddleX = WIDTH - PADDLE_WIDTH;
        }

        paddle.setLocation(paddleX, paddleY);
    }

    public void keyReleased(KeyEvent e) {}

    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        new BrickBreaker();
    }
}


