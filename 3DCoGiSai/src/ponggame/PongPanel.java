/*
 * PONG GAME REQUIREMENTS
 * This simple "tennis like" game features two paddles and a ball, 
 * the goal is to defeat your opponent by being the first one to gain 3 point,
 *  a player gets a point once the opponent misses a ball. 
 *  The game can be played with two human players, one on the left and one on 
 *  the right. They use keyboard to start/restart game and control the paddles. 
 *  The ball and two paddles should be red and separating lines should be green. 
 *  Players score should be blue and background should be black.
 *  Keyboard requirements:
 *  + P key: start
 *  + Space key: restart
 *  + W/S key: move paddle up/down
 *  + Up/Down key: move paddle up/down
 *  
 *  Version: 0.5
 */
package ponggame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.font.ImageGraphicAttribute;
import java.util.Random;
import java.util.Vector;

import javax.jws.WebParam.Mode;
import javax.print.attribute.standard.Media;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.Timer;
import javax.swing.plaf.basic.BasicTreeUI.SelectionModelPropertyChangeHandler;

/**
 * 
 * @author Invisible Man
 *
 */
public class PongPanel extends JPanel implements ActionListener, KeyListener, MouseMotionListener {
	private static final long serialVersionUID = -1097341635155021546L;

	private boolean showTitleScreen = true;
	private boolean playing;
	private boolean gameOver;

	/** Background. */
	private Color backgroundColor = Color.BLACK;

	/** State on the control keys. */
	private boolean upPressed;
	private boolean downPressed;
	private boolean wPressed;
	private boolean sPressed;

	/** The ball: position, diameter */

	private int ballX = 250;
	private int ballY = 250;
	private int diameter = 50;
	private int ballDeltaX = -1;
	private int ballDeltaY = 3;
	JRadioButton rRadiO[] = new JRadioButton[4];
	String[] nameColor = { "Green", "Black", "Beuti", "Ball" };
	JLabel lbllCOlorball = new JLabel("Color Ball");
	int colorBall = 0;
	// khai bao paddle = hinh
	ImageIcon imagePaddle;
	Image imageA;
	ImageIcon imageBall;
	Image imageSet;
	ButtonGroup bgSelect = new ButtonGroup();

	/** Player 1's paddle: position and size */
	private int playerOneX = 0;
	private int playerOneY = 250;
	private int playerOneWidth = 11;
	private int playerOneHeight = 50;

	/** Player 2's paddle: position and size */
	private int playerTwoX = 483;
	private int playerTwoY = 250;
	private int playerTwoWidth = 13;
	private int playerTwoHeight = 50;

	/** Speed of the paddle - How fast the paddle move. */
	private int paddleSpeed = 5;

	/** Player score, show on upper left and right. */
	private int playerOneScore;
	private int playerTwoScore;
	/** BallRanDom */
	int rRan = 50;
	int iNewNumber = 0;
	int iNewNumber2 = 0;
	int iNewNumber3 = 0;
	int aNewNumber[] = new int[5];
	int aNewNumber2[] = new int[5];
	int aNewNumber3[] = new int[5];
	String[] imagesTouch = { "./Image/hightspeed.png", "./Image/slowspeed.png", "./Image/minus25.png",
			"./Image/plus25.png", };
	Image imageSetTouch[] = new Image[4];
	/** Ball Action **/
	int touchsign = 0;
	int numbertimer = 60;
	/** Construct a PongPanel. */
	ImageIcon bg1 = new  ImageIcon("Image/bg1.jpg");
	ImageIcon bg2 = new  ImageIcon("Image/bg2.jpg");
	ImageIcon bg3 = new  ImageIcon("Image/bg3.jpg");
	ImageIcon bg4 = new  ImageIcon("Image/bg4.gif");
	ImageIcon bg5 = new  ImageIcon("Image/bg5.gif");
	int reacBallX; // tam cua hinh tron
	int reacBallY;
	int reacRandomX; // tam cua hinh tron randoom;
	int reacRandomY;
	/** sau khi Over Banh quay ve **/
	int retur = 0;
	/** add Mouse moution **/
	ImageIcon MouseMove = new ImageIcon("./Image/la.gif");
	int MoveX;
	int MoveY;
	int widthMouse = 100;
	/** Score effect **/
	int countEffectPlayer1 = 0;
	int countEffectPlayer2 = 0;
	ImageIcon geteffect1 = new ImageIcon("./Image/raw.gif");
	ImageIcon geteffect2 = new ImageIcon("./Image/raw2.gif");
	int countPlay1 = 0;
	int countPlay2 = 0;
	private boolean PlayEffect1;
	private boolean PlayEffect2;
	ImageIcon paddleEffectLeft = new ImageIcon("./Image/dragon.gif");
	ImageIcon paddleEffectRight = new ImageIcon("./Image/dragon.gif");
	public PongPanel() {
		setBackground(backgroundColor);

		// listen to key presses
		setFocusable(true);
		addKeyListener(this);

		ScreenCOlorBall();
		// call step() 60 fps
		Timer timer = new Timer(1000 / numbertimer, this);
		timer.start();
		addMouseMotionListener(this);
	}

	/** Implement actionPerformed */
	public void actionPerformed(ActionEvent e) {
		step();
	}

	/** Repeated task */
	public void step() {

		if (playing) {

			/* Playing mode */

			// move player 1
			// Move up if after moving, paddle is not outside the screen
			if (wPressed && playerOneY - paddleSpeed > 0) {
				playerOneY -= paddleSpeed;
			}
			// Move down if after moving paddle is not outside the screen
			if (sPressed && playerOneY + playerOneHeight + paddleSpeed - 10 < getHeight()) {
				playerOneY += paddleSpeed;
			}

			// move player 2
			// Move up if after moving paddle is not outside the screen sdgagwd
			if (upPressed && playerTwoY - paddleSpeed > 0) {
				playerTwoY -= paddleSpeed;
			}
			// Move down if after moving paddle is not outside the screen
			if (downPressed && playerTwoY + playerTwoHeight + paddleSpeed - 10 < getHeight()) {
				playerTwoY += paddleSpeed;
			}

			/*
			 * where will the ball be after it moves? calculate 4 corners: Left,
			 * Right, Top, Bottom of the ball used to determine whether the ball
			 * was out yet
			 */
			int nextBallLeft = ballX + ballDeltaX;
			int nextBallRight = ballX + diameter + ballDeltaX;
			// FIXME Something not quite right here
			int nextBallTop = ballY + ballDeltaY;
			int nextBallBottom = ballY + diameter + ballDeltaY;

			// Player 1's paddle position
			int playerOneRight = playerOneX + playerOneWidth;
			int playerOneTop = playerOneY;
			int playerOneBottom = playerOneY + playerOneHeight;

			// Player 2's paddle position
			float playerTwoLeft = playerTwoX;
			float playerTwoTop = playerTwoY;
			float playerTwoBottom = playerTwoY + playerTwoHeight;

			// ball bounces off top and bottom of screen
			if (nextBallTop < 0 || nextBallBottom > getHeight()) {
				ballDeltaY *= -1;
				Sound.play("Sound/wall.wav");
			}

			// will the ball go off the left side?
			if (nextBallLeft < playerOneRight) {
				Sound.play("Sound/ball.wav");
				// is it going to miss the paddle?
				if (nextBallTop > playerOneBottom || nextBallBottom < playerOneTop) {
					playerTwoScore++;
					Sound.play("Sound/ball.wav");

					// Player 2 Win, restart the game

					if (playerTwoScore == 2) {

						playing = false;
						gameOver = true;
						countEffectPlayer2++;

						Sound.play("Sound/start_1_.wav");
					}

					ballX = 250;
					ballY = 250;
				} else {
					// If the ball hitting the paddle, it will bounce back
					// FIXME Something wrong here
					ballDeltaX *= -1;
					Sound.play("Sound/ball.wav");
					if (touchsign == 1) {
						if (aNewNumber3[0] == 0) {
							ballDeltaY = ballDeltaY + 2;
							ballDeltaX = ballDeltaX - 2;
						} else if (aNewNumber3[0] == 1) {
							ballDeltaY = ballDeltaY - 1;
						} else if (aNewNumber3[0] == 2) {
							playerOneHeight = playerOneHeight - playerOneHeight * 25 / 100;
						} else if (aNewNumber3[0] == 3) {
							playerOneHeight = playerOneHeight + playerOneHeight * 25 / 100;
						}
					}
				}
			}

			// will the ball go off the right side?
			if (nextBallRight > playerTwoLeft) {
				Sound.play("Sound/ball.wav");
				// is it going to miss the paddle?
				if (nextBallTop > playerTwoBottom || nextBallBottom < playerTwoTop) {
					playerOneScore++;
					Sound.play("Sound/ball.wav");

					// Player 1 Win, restart the game

					if (playerOneScore == 2) {

						playing = false;
						gameOver = true;

						countEffectPlayer1++;

						Sound.play("Sound/start_1_.wav");

					}

					ballX = 250;
					ballY = 250;
				} else {

					// If the ball hitting the paddle, it will bounce back
					// FIXME Something wrong here
					ballDeltaX *= -1;
					Sound.play("Sound/ball.wav");
					if (touchsign == 1) {
						if (aNewNumber3[0] == 0) {
							ballDeltaY = ballDeltaY + 2;
							ballDeltaX = ballDeltaX - 2;
						} else if (aNewNumber3[0] == 1) {
							ballDeltaY = ballDeltaY - 1;
						} else if (aNewNumber3[0] == 2) {
							playerTwoHeight = playerOneHeight - playerTwoHeight * 25 / 100;
						} else if (aNewNumber3[0] == 3) {
							playerTwoHeight = playerOneHeight + playerTwoHeight * 25 / 100;
						}
					}
				}
			}

			// move the ball
			ballX += ballDeltaX;
			ballY += ballDeltaY;
		}

		// stuff has moved, tell this JPanel to repaint itself
		repaint();
	}

	/** Paint the game screen. */
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		if (showTitleScreen) {

			/* Show welcome screen */

			// Draw game title and start message
			g.drawImage(bg1.getImage(), 0, 0, 500, 500, null);
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 40));
			g.setColor(Color.RED);
			g.drawString("PONG GAME", 125, 150);
			g.drawImage(MouseMove.getImage(), MoveX - widthMouse / 2, MoveY - widthMouse / 2, widthMouse, widthMouse,
					this);
			// FIXME Wellcome message below show smaller than game title
			g.drawString("Press 'P'", 10, 400);
			g.drawString("to play", 40, 435);
		} else if (playing) {

			g.drawImage(bg2.getImage(), 0, 0, 500, 500, null);
			/* Game is playing */

			// set the coordinate limit
			int playerOneRight = playerOneX + playerOneWidth;
			int playerTwoLeft = playerTwoX;

			// draw dashed line down center
			for (int lineY = 0; lineY < getHeight(); lineY += 50) {
				g.setColor(Color.RED);
				g.drawLine(250, lineY, 250, lineY + 25);
			}

			// draw "goal lines" on each side
			g.drawLine(playerOneRight, 0, playerOneRight, getHeight());
			g.drawLine(playerTwoLeft, 0, playerTwoLeft, getHeight());
			// effect Score
			if (countEffectPlayer1 == 1) {
				g.setColor(Color.BLUE);
				g.fillRect(80, 30, 10, 40);
			} else if (countEffectPlayer1 == 2) {
				g.setColor(Color.BLUE);
				g.fillRect(80, 30, 10, 40);
				g.setColor(Color.GREEN);
				g.fillRect(100, 30, 10, 40);
			} else if (countEffectPlayer1 == 3) {
				g.setColor(Color.BLUE);
				g.fillRect(80, 30, 10, 40);
				g.setColor(Color.GREEN);
				g.fillRect(100, 30, 10, 40);
				g.setColor(Color.RED);
				g.fillRect(100 + 20, 30, 10, 40);
			} else if (countEffectPlayer1 == 0) {
				g.fillRect(0, 0, 0, 0);
			}
			if (countEffectPlayer2 == 1) {
				g.setColor(Color.BLUE);
				g.fillRect(380, 30, 10, 40);
			} else if (countEffectPlayer2 == 2) {
				g.setColor(Color.BLUE);
				g.fillRect(380, 30, 10, 40);
				g.setColor(Color.GREEN);
				g.fillRect(400, 30, 10, 40);
			} else if (countEffectPlayer2 == 3) {
				g.setColor(Color.BLUE);
				g.fillRect(380, 30, 10, 40);
				g.setColor(Color.GREEN);
				g.fillRect(400, 30, 10, 40);
				g.setColor(Color.RED);
				g.fillRect(400 + 20, 30, 10, 40);
			} else if (countEffectPlayer2 == 0) {
				g.fillRect(0, 0, 0, 0);
			}

			// draw the scores
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
			g.setColor(Color.BLUE);
			g.drawString(String.valueOf(playerOneScore), 100, 100); // Player 1
																	// score
			g.drawString(String.valueOf(playerTwoScore), 400, 100); // Player 2
																	// score

			// draw the ball
			for (int i = 0; i < 4; i++) {
				imageBall = new ImageIcon(imagesTouch[i]);
				imageSetTouch[i] = imageBall.getImage();
			}
			// g.setColor(Color.red);
			// g.fillOval(aNewNumber[0] - 30, aNewNumber2[0], rRan, rRan);
			if (aNewNumber[0] == 0) {
				rRan = 0;
				g.drawImage(imageSetTouch[aNewNumber3[0]], aNewNumber[0], aNewNumber2[0], rRan, rRan, this);
			} else if (aNewNumber[0] != 0) {
				rRan = 50;
				g.drawImage(imageSetTouch[aNewNumber3[0]], aNewNumber[0], aNewNumber2[0], rRan, rRan, this);
			}
			reacBallX = ballX + diameter / 2;
			reacBallY = ballY + diameter / 2;
			reacRandomX = aNewNumber[0] + rRan / 2;
			reacRandomY = aNewNumber2[0] + rRan / 2;
			double Dai = Math.max(reacBallX, reacRandomX) - Math.min(reacBallX, reacRandomX);
			double Rong = Math.max(reacBallY, reacRandomY) - Math.min(reacBallY, reacRandomY);
			double aveDR = Math.sqrt(Dai * Dai + Rong * Rong);
			if (aveDR <= (rRan / 2 + diameter / 2)) {
				aNewNumber[0] = 0;
				aNewNumber2[0] = 0;
				g.fillOval(aNewNumber[0] - 30, aNewNumber2[0], rRan, rRan);
				touchsign = 1;
			}
			if (colorBall == 0) {
				g.setColor(Color.GREEN);
				g.fillOval(ballX, ballY, diameter, diameter);

			} else if (colorBall == 1) {
				g.setColor(Color.BLACK);
				g.fillOval(ballX, ballY, diameter, diameter);

				// g.fillOval(aNewNumber[0],aNewNumber2[0],30,30);

			} else if (colorBall == 2) {
				imageBall = new ImageIcon("./Image/color.gif");
				imageSet = imageBall.getImage();
				g.drawImage(imageSet, ballX, ballY, diameter +6, diameter +6, this);

				// g.fillOval(aNewNumber[0],aNewNumber2[0],30,30);

			} else if (colorBall == 3) {
				imageBall = new ImageIcon("./Image/ball.gif");
				imageSet = imageBall.getImage();
				g.drawImage(imageSet, ballX, ballY, diameter+6, diameter+6 , this);

				// g.fillOval(aNewNumber[0],aNewNumber2[0],30,30);

			}

			// draw the paddles
			if(countPlay1==0){
				g.setColor(Color.BLUE);
				imagePaddle = new ImageIcon("./Image/C.gif");
				imageA = imagePaddle.getImage();
				g.drawImage(imageA, playerOneX, playerOneY - 8, playerOneWidth, playerOneHeight, this);
			}else if(countPlay1==1){
				g.drawImage(paddleEffectLeft.getImage(), playerOneX, playerOneY - 8, playerOneWidth, playerOneHeight+20, this);
			}
			if(countPlay2==0){
			g.setColor(Color.GREEN);
			imagePaddle = new ImageIcon("./Image/C.gif");
			imageA = imagePaddle.getImage();
			g.drawImage(imageA, playerTwoX, playerTwoY - 8, playerTwoWidth, playerTwoHeight, this);
			}else if(countPlay2==1){
			g.drawImage(paddleEffectRight.getImage(), playerTwoX, playerTwoY - 8, playerTwoWidth, playerTwoHeight+20, this);
			}
			
			
			g.drawImage(MouseMove.getImage(), MoveX - widthMouse / 2, MoveY - widthMouse / 2, widthMouse, widthMouse,
					this);
			
			if (PlayEffect1 == true) {
				g.drawImage(geteffect1.getImage(), 0, 0, 500, 500, this);
			} else if (PlayEffect2 == true) {
				g.drawImage(geteffect2.getImage(), 0, 0, 500, 500, this);
			}
			if (PlayEffect1 == false) {
				g.drawImage(geteffect1.getImage(), 0, 0, 0, 0, this);
			} 
			if (PlayEffect2 == false) {
				g.drawImage(geteffect2.getImage(), 0, 0, 0, 0, this);
			}
		} else if (gameOver) {

			/* Show End game screen with winner name and score */

			// Draw scores
			// TODO Set Blue color

			g.drawImage(bg3.getImage(), 0, 0, 500, 500, null);
			g.drawImage(bg4.getImage(), 150, 150, 200, 200, null);
			g.drawImage(bg5.getImage(), 300, 300, 200, 200, null);
			g.setFont(new Font(Font.DIALOG, Font.BOLD,45));
			g.setColor(Color.WHITE);
			g.drawString(String.valueOf(playerOneScore), 100, 100);
			g.drawString(String.valueOf(playerTwoScore), 400, 100);


			// Draw the winner name
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 50));
			g.setColor(Color.RED);
			if (playerOneScore > playerTwoScore) {

				retur = 1;

				g.drawString("Player 1 Wins!", 85, 190);
				Sound.play("Sound/start_1_.wav");

			} else {

				retur = 2;
				g.drawString("Player 2 Wins!", 85, 190);
				Sound.play("Sound/start_1_.wav");

			}
			g.drawImage(MouseMove.getImage(), MoveX - widthMouse / 2, MoveY - widthMouse / 2, widthMouse, widthMouse,
					this);

			// Draw Restart message
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 25));	
			g.setColor(Color.RED);
			g.drawString("Press 'Space' to replay", 10, 435);
			// TODO Draw a restart message
		}
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		if (showTitleScreen) {
			if (e.getKeyCode() == KeyEvent.VK_P) {
				Sound.play("Sound/start_1_.wav");
				showTitleScreen = false;
				playing = true;
				for (int i = 0; i < 4; i++) {
					rRadiO[i].setVisible(false);
				}
				lbllCOlorball.setVisible(false);
			}
		} else if (playing) {
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				upPressed = true;
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				downPressed = true;
			} else if (e.getKeyCode() == KeyEvent.VK_W) {
				wPressed = true;
			} else if (e.getKeyCode() == KeyEvent.VK_S) {
				sPressed = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_V) {
				if (countEffectPlayer1 == 3) {
					PlayEffect1 = true;
					countEffectPlayer1 = 0;
					countPlay1=1;
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_N) {
				if (countEffectPlayer2 == 3) {
					PlayEffect2 = true;
					countEffectPlayer2 = 0;
					countPlay2=1;
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_G) {
				PlayEffect1 = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_J) {
				PlayEffect2 = false;
			}
		}
		if (gameOver && e.getKeyCode() == KeyEvent.VK_SPACE) {
			gameOver = false;
			showTitleScreen = true;
			Sound.play("Sound/win2.wav");
			if (retur == 1) {
				ballDeltaX = -1;
			} else if (retur == 2) {
				ballDeltaX = 1;
			}
			playerOneScore = 0;
			playerTwoScore = 0;
			ballX = 250;
			ballY = 250;

		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			upPressed = false;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			downPressed = false;
		} else if (e.getKeyCode() == KeyEvent.VK_W) {
			wPressed = false;
		} else if (e.getKeyCode() == KeyEvent.VK_S) {
			sPressed = false;
		}
	}

	public void ScreenCOlorBall() {
		lbllCOlorball = new JLabel("Color Ball");
		add(lbllCOlorball);
		lbllCOlorball.setForeground(Color.RED);
		for (int i = 0; i < 4; i++) {
			rRadiO[i] = new JRadioButton(nameColor[i]);
			add(rRadiO[i]);
			bgSelect.add(rRadiO[i]);
			rRadiO[i].setBackground(Color.BLACK);
			rRadiO[i].setForeground(Color.WHITE);
		}
		rRadiO[0].setSelected(true);
		KeyListener kACtion = new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == KeyEvent.VK_P) {
					Sound.play("Sound/win2.wav");
					showTitleScreen = false;
					playing = true;
					for (int i = 0; i < 4; i++) {
						rRadiO[i].setVisible(false);
					}
					lbllCOlorball.setVisible(false);

				}
			}
		};
		ActionListener bAc = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (e.getSource() == rRadiO[0]) {
					colorBall = 0;
				} else if (e.getSource() == rRadiO[1]) {
					colorBall = 1;
				} else if (e.getSource() == rRadiO[2]) {
					colorBall = 2;
				} else if (e.getSource() == rRadiO[3]) {
					colorBall = 3;
				}
			}
		};
		for (int i = 0; i < 4; i++) {
			rRadiO[i].addKeyListener(kACtion);
			rRadiO[i].addActionListener(bAc);
		}

		timerMinh();
	}

	public void ranDoomPoint() {
		Random rd = new Random();
		Vector v = new Vector();
		Vector v2 = new Vector();
		Vector v3 = new Vector();
		int maxNumberOccur = 200;
		for (int i = 0; i < 1; i++) {
			do {
				iNewNumber = rd.nextInt(480);// 480
			} while (v.contains(iNewNumber));
			if (!v.isEmpty() && v.size() >= maxNumberOccur)
				v.remove(0);
			v.add(iNewNumber);

		}

		for (int i = 0; i < 1; i++) {
			do {
				iNewNumber2 = rd.nextInt(450);
			} while (v2.contains(iNewNumber));
			if (!v2.isEmpty() && v2.size() >= maxNumberOccur)
				v2.remove(0);
			v2.add(iNewNumber2);

		}
		for (int i = 0; i < 1; i++) {
			do {
				iNewNumber3 = rd.nextInt(4);
			} while (v3.contains(iNewNumber));
			if (!v3.isEmpty() && v3.size() >= maxNumberOccur)
				v3.remove(0);
			v3.add(iNewNumber3);
		}

	}

	public void timerMinh() {
		Timer acTime = new Timer((6000 + 10 * (iNewNumber + iNewNumber)), new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent g) {
				// TODO Auto-generated method stub
				ranDoomPoint();
				aNewNumber[0] = iNewNumber;
				aNewNumber2[0] = iNewNumber2;
				aNewNumber3[0] = iNewNumber3;

			}
		});
		acTime.start();
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {

	}

	@Override
	public void mouseMoved(MouseEvent g) {
		MoveX = g.getX();
		MoveY = g.getY();

	}
}
