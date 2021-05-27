package stage3;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import main.FinishGUI;
import stage2.Stage2Block;
import stage2.Stage2Constant;
/**
 * 
 * Stage3MainFrame
 * <li>스테이지2 창 생성자</li>
 * <li>블럭과 공 생성</li>
 * <li>시작 타이머 생성</li>
 * <li>키를 눌렀을 때 바가 움직이는 리스너 생성</li>
 * <li>게임 클리어 설정</li>
 * <li>게임 오버 설정</li>
 *
 */
@SuppressWarnings("serial")
public class Stage3MainFrame extends JFrame implements Stage3Constant { 

	public int score = 0;

	Stage3MyPanel myPanel;
	private Timer timer = null;
	private int isHiddenCount;

	static Stage3Block[][] blocks = new Stage3Block[BLOCK_ROWS][BLOCK_COLUMNS]; 

	static Stage3Bar bar;
	boolean isGameFinish = false;
	
	
	static Stage3Ball[] balls = new Stage3Ball[3];

	static int nextBar = bar.x; // 
	/**
	 * 
	 * @param title - "벽돌 깨기"
	 * 
	 * <li>창 설정</li>
	 * <li>블럭, 공 생성</li>
	 * <li>스테이지2에 대한 패널 생성 및 추가</li>
	 * <li>키 리스너, 시작타이머 생성</li>
	 */
	public Stage3MainFrame(String title) { 
		super(title);

		this.setVisible(true); 
		this.setSize(CANVAS_WIDTH, CANVAS_HEIGHT); 
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setLayout(new GridLayout()); 
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		initData();

		myPanel = new Stage3MyPanel();
		add(myPanel);

		setKeyListener();
		startTimer();

	}
	/**
	 * 
	 * 공과 블럭 생성
	 */
	private void initData() {

		for (int i = 0; i < balls.length; ++i) { 
			balls[i] = new Stage3Ball(this);
		}

		for (int i = 0; i < BLOCK_ROWS; i++) { 
			for (int j = 0; j < BLOCK_COLUMNS; j++) {
				blocks[i][j] = new Stage3Block(i, j, balls);
			}
		}

	}
	/**
	 * <ol>
	 * <li>설정된 공,블럭을 실행시킴</li>
	 * <li>시작 타이머 실행시킴</li>
	 * </ol>
	 * <li>repaint()를 불러와 타이머가 실행되는 동안 </br>
	 * 공,블럭,바의 그래픽스들이 상시 호출되도록 설정(설정 안하면 끊김)
	 * <li>게임오버 됐을 때 게임 진행 멈춤</li>
	 * <li>게임 클리어 됐을 때 게임 진행 멈춤</li>
	 * 
	 * 
	 */
	public void startTimer() {

		JOptionPane.showMessageDialog(null, "확인을 누르면 바로 게임이 시작됩니다");

		for (Stage3Ball b : balls) { // 

			new Thread(b).start();
			repaint();

		}

		for (int i = 0; i < BLOCK_ROWS; i++) { 
			for (int j = 0; j < BLOCK_COLUMNS; j++) {
				new Thread(blocks[i][j]).start();
			}
		}

		timer = new Timer(10, new ActionListener() {
														
			@Override

			public void actionPerformed(ActionEvent e) { 

				repaint();
				

				if (isGameOver()) {
					
					JOptionPane.showMessageDialog(null, "공이 모두 떨어졌습니다...");
					gameStop();
					

				}
				if (gameClear()) {
					JOptionPane.showMessageDialog(null, "축하합니다 게임을 클리어하셨습니다!");
					gameStop();
				

				}

			}

		});

		timer.start();

	}
	/**
	 * 게임 진행 멈추는 메서드 생성
	 */
	void gameStop() {
		timer.stop();
		setVisible(false);
		new FinishGUI(getName());
	}
	/**
	 * 
	 * 바를 움직이게끔 하는 키 리스너 생성
	 * <li> 왼쪽 키를 눌렀을 때 바x가 -20만큼 움직임</li>
	 * <li> 오른쪽 키를 눌렀을 때 바x가 +20만큼 움직임</li>
	 */
	void setKeyListener() {
		this.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
					

					if (bar.x > 0) {
						bar.x -= 20;
					}

				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
				

					if (bar.x + BAR_WIDTH < CANVAS_WIDTH) {
						bar.x += 20;
					}
				}
				nextBar = bar.x;
			}

			
		});

	}

	/**
	 * <li>게임 클리어 됐을 때 조건을 리턴하는 메서드 생성 </li>
	 * @return - 게임 클리어 됐을 때 조건 리턴
	 */
	public boolean gameClear() {

		int isHiddenCount = 0;
		for (int i = 0; i < BLOCK_ROWS; i++) {
			for (int j = 0; j < BLOCK_COLUMNS; j++) {
				Stage3Block block = blocks[i][j];
				if (block.isHidden) {
					isHiddenCount++;
				}
			}
		}
	
		if (isHiddenCount == BLOCK_ROWS * BLOCK_COLUMNS) {

			isGameFinish = true;

		}
		return isGameFinish;
	

	}


	/**
	 * <li>게임 오버되는 조건 메서드 생성</li>
	 * @return - 공이 창 밑으로 다 떨어졌을 때 조건 리턴
	 */
	public static boolean isGameOver() {
		return (balls[0].y >= CANVAS_HEIGHT && balls[1].y >= CANVAS_HEIGHT 
				&& balls[2].y >= CANVAS_HEIGHT);
	}

	public static void main(String[] args) {
		new Stage3MainFrame(null);
	}
}
