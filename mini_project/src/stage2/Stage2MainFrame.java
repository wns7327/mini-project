package stage2;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import javax.swing.JOptionPane;

import javax.swing.Timer;

import main.FinishGUI;


/**
 * 
 * Stage2MainFrame
 * <li>스테이지2 창 생성자</li>
 * <li>블럭과 공 생성</li>
 * <li>시작 타이머 생성</li>
 * <li>키를 눌렀을 때 바가 움직이는 리스너 생성</li>
 * <li>게임 클리어 설정</li>
 * <li>게임 오버 설정</li>
 *
 */
@SuppressWarnings("serial")
public class Stage2MainFrame extends JFrame implements Stage2Constant { // 창

	public static int score = 0;					//점수 0점 초기화

	Stage2MyPanel myPanel;									// 패널 선언
	private Timer timer = null;								// 시작 타이머 선언 및 초기화
	
	static Stage2Block[][] blocks = new Stage2Block[BLOCK_ROWS][BLOCK_COLUMNS]; // static(을 안쓰면 오류가 무조건 일어남 사유)
																				// nullpointer

	static Stage2Bar bar;// static(을 안쓰면 오류가 무조건 일어남 사유) nullpointer
	boolean isGameFinish = false;

	static Stage2Ball[] balls = new Stage2Ball[2];// static(을 안쓰면 오류가 무조건 일어남 사유) nullpointer

	int nextBar = bar.x; // 바가 키를 눌렀을 때 움직이게끔 설정

	/**
	 * 
	 * @param title - "벽돌 깨기"
	 * 
	 * <li>창 설정</li>
	 * <li>블럭, 공 생성</li>
	 * <li>스테이지2에 대한 패널 생성 및 추가</li>
	 * <li>키 리스너, 시작타이머 생성</li>
	 */
	public Stage2MainFrame(String title) { // 창 생성자
		super(title);

		this.setVisible(true); // 보이게함
		this.setSize(CANVAS_WIDTH, CANVAS_HEIGHT); // 창 크기
		this.setResizable(false);				// 창 크기를 함부로 조절 못하게끔 방지
		this.setLocationRelativeTo(null);		// 창 중앙에 배치
		this.setLayout(new GridLayout()); // 레이아웃 방식
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);	// 창 종료 기본 설정

		initData();								// 블럭, 공 생성

		myPanel = new Stage2MyPanel();				// 스테이지2의 패널 생성
		add(myPanel);		// 패널을 창에 추가

		setKeyListener();					// 리스너 생성
		startTimer();						// 시작타이머 생성

	}

	
	/**
	 * 
	 * 공과 블럭 생성
	 */
	private void initData() {

		for (int i = 0; i < balls.length; ++i) { // 볼 생성
			balls[i] = new Stage2Ball(this);
		}

		for (int i = 0; i < BLOCK_ROWS; i++) { // 블럭 생성
			for (int j = 0; j < BLOCK_COLUMNS; j++) {
				blocks[i][j] = new Stage2Block(i, j);
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

		for (Stage2Ball b : balls) { // 설정된 공들을 실행시킴

			new Thread(b).start();

		}

		for (int i = 0; i < BLOCK_ROWS; i++) { // 설정된 블럭을 실행시킴
			for (int j = 0; j < BLOCK_COLUMNS; j++) {
				new Thread(blocks[i][j]).start();
			}
		}

		timer = new Timer(10, new ActionListener() { // 게임 시작 후 실행되는 동안
														//repaint()에 의 공, 블럭, 바의 그래픽스들이 상시 호출되도록 설정 (설정안하면 끊김)
			@Override

			public void actionPerformed(ActionEvent e) { // Timer Event

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
	void setKeyListener() { // 눌렀을때 반응
		this.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {	//

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
				Stage2Block block = blocks[i][j];
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
		return (balls[0].y >= CANVAS_HEIGHT && balls[1].y >= CANVAS_HEIGHT);
	}
	public static void main(String[] args) {
		new Stage2MainFrame(null);
	}
}
