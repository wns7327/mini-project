package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import stage1.Stage1Bar;
import stage1.Stage1MainFrame;
import stage2.Stage2Bar;
import stage2.Stage2Constant;
import stage2.Stage2MainFrame;
import stage2.Stage2MyPanel;
import stage3.Stage3Bar;
import stage3.Stage3MainFrame;

/**
 * 
 * MainGUI - 스테이지를 선택할 수 있는 창을 만든다.
 * <ol>
 * <li>스테이지1,2,3버튼을 생성하기위한 변수 선언</li>
 * <li>스테이지1,2,3 키 리스너 생성</li>
 *	<li>메인 GUI 생성자 </li>
 */
public class MainGUI extends JFrame implements Stage2Constant {

	private static final String STAGE1 = "STAGE1"; // 스테이지 1
	private static final String STAGE2 = "STAGE2";// 스테이지2
	private static final String STAGE3 = "STAGE3";// 스테이지3
	private static final String BACK = "뒤로 가기";
	
	private static final String[] STAGE_ARRAY = { STAGE1, STAGE2, STAGE3 }; // 3개의 문자열을 배열에 저장
	private JPanel panel; // 패널 선언

	private JButton[] buttons; // 버튼 선언

	private Stage1Bar bar1; // 3개의 바를 선언
	private Stage2Bar bar2;
	private Stage3Bar bar3;
	private Stage1MainFrame rootComponent1; // 3개의 게임 실행창 선언
	private Stage2MainFrame rootComponent2;
	private Stage3MainFrame rootComponent3;


	MemberDAO dao = MemberDAO.getInstance();
	MemberDTO dto = new MemberDTO();
//	String nickName = dao.findbyEmail(dto.email);
	
	/**
	 * 
	 * @param listener
	 *                 <li>3개의 스테이지 중 하나를 선택하면</br>
	 *                 수행할 작업들
	 */
	public ActionListener listener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton) e.getSource(); // 버튼을 불러옴
			switch (button.getText()) { // 버튼의 문자열로 조건을 받음
			case STAGE1:

				bar1.x = CANVAS_WIDTH / 2 - BAR_WIDTH / 2; // bar를 원래 위치로 되돌려 놓는다.
				bar1.y = CANVAS_HEIGHT - 100;
				setVisible(false); // MainGUI의 창을 종료시킴
				rootComponent1.score = 0; // 점수 초기화 (게임이 끝나고 스테이지를 고를 때 매번 초기화해준다.)
				new Stage1MainFrame("벽돌 깨기"); // Stage1을 실행시킴

				break;

			case STAGE2: // Stage1과 비슷한 구조

				bar2.x = CANVAS_WIDTH / 2 - BAR_WIDTH / 2;
				bar2.y = CANVAS_HEIGHT - 100;
				setVisible(false);
				rootComponent2.score = 0;
				new Stage2MainFrame("벽돌 깨기");

				break;

			case STAGE3: // Stage1과 비슷한 구조

				bar3.x = CANVAS_WIDTH / 2 - BAR_WIDTH / 2;
				bar3.y = CANVAS_HEIGHT - 100;
				setVisible(false);
				rootComponent3.score = 0;
				new Stage3MainFrame("벽돌 깨기");

				break;
			case BACK:{
				setVisible(false);
				new LoggedInMenu(getTitle());
			}
			}

		}
	};

	/**
	 * 
	 * 이미지, 3개의 Stage 버튼, 게임 제목을 창에 부착하는 작업을 수행한다.
	 * 
	 * @param title - 게임 제목</br>
	 * 
	 * 
	 */
	public MainGUI(String title) {

		super("벽돌 깨기");
		
		// 이미지 가져오기
		Image image1 = null; // 이미지 초기화
		try {
			File sourceimage = new File("C:/Users/herom/PARK/java.project/src/image/background3.jpg"); // 경로 불러옴
			image1 = ImageIO.read(sourceimage); // 불러온 경로를 받아 이미지를 받아옴
		} catch (IOException e) {
			e.printStackTrace();
		}
		Image changeImg = image1.getScaledInstance(800, 800, image1.SCALE_SMOOTH); // 이미지 수정 세부 설정
		ImageIcon changeIcon = new ImageIcon(changeImg); // 수정된 이미지를 생성

		
		// 패널에 이미지를 붙여줌
		panel = new JPanel() { 
			public void paint(Graphics g) {
			
				g.drawImage(changeIcon.getImage(), 0, 0, null); // 이미지를 그려줌
				
			}

		};

		// 제목 설정
		JLabel j = new JLabel("스테이지 선택", SwingConstants.CENTER);			// "벽돌깨기"라는 제목을 라벨로 생성
		j.setFont(new Font("궁서", Font.BOLD, 40));					// 제목의 폰트를 설
		j.setForeground(new Color(250, 98, 3));						// 제목의 글자색 설정
		j.setBounds(260, 50, 280, 100);								// 제목의 위치
		this.add(j);												// 제목을 붙임

		
		// 버튼 설정
		buttons = new JButton[STAGE_ARRAY.length];					

		for (int i = 0; i < STAGE_ARRAY.length; ++i) {
			buttons[i] = new JButton(STAGE_ARRAY[i]);			// Stage 버튼 생성
			buttons[i].setFont(new Font("고딕체", Font.CENTER_BASELINE, 40)); // 글씨 폰트 설정
			buttons[i].setForeground(new Color(33, 33, 33));			// 버튼 글씨 색깔
			buttons[i].setBackground(new Color(216, 216, 216));		// 버튼 배경 색깔

			buttons[i].addActionListener(listener);				// 버튼 리스너 추가
			buttons[i].setBounds(250, 90 * 2 * (i + 1), 300, 100);		// 버튼 위치
			this.add(buttons[i]);							//버튼 부착

		}
		
		JButton backButton = new JButton(BACK);
		backButton.setFont(new Font("고딕체", Font.CENTER_BASELINE, 20)); // 글씨 폰트 설정
		backButton.setForeground(new Color(33, 33, 33));			// 버튼 글씨 색깔
		backButton.setBackground(new Color(216, 216, 216));		// 버튼 배경 색깔

		backButton.addActionListener(listener);				// 버튼 리스너 추가
		backButton.setBounds(20,700,150,50);		// 버튼 위치
		this.add(backButton);							//버튼 부착

		

		// 창 세부 설정
		setSize(800, 800);			// 창 크기 설정

		setDefaultCloseOperation(EXIT_ON_CLOSE);// 창 닫기 기본 설정
		setLocationRelativeTo(null);// 창을 중앙에 배치

		add(panel);// 패널(이미지 붙이기)을 프레임에 부착

		setVisible(true);// 보이게끔 설정


	}

}
