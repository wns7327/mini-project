package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import stage2.Stage2Constant;

/**
 *
 * <ol>
 * <li>프로그램 시작</li>
 * <li>리스너 객체 생성</li>
 * <li>Menu 객체 생성</li>
 * </ol>
 * 
 */

@SuppressWarnings("serial")
public class LoggedInMenu extends JFrame implements Stage2Constant {

	private static final String GAME_START = "게임 시작"; // 게임 시작 버튼 문자열
	private static final String EXIT = "게임 종료";
	private static final String SCORE = "점수 보기";
	private static final String LOGOUT= "로그아웃";
	
	

	private static final String[] MAIN_START_BUTTON = { GAME_START ,SCORE, EXIT,LOGOUT}; // 두 문자열 배열에 담기
	
	private JButton[] button; // 게임 시작 버튼 선언
	
	MemberDAO dao = MemberDAO.getInstance();
	MemberDTO dto = new MemberDTO();
//	String nickName = dao.findbyEmail(log.email);
	

	/**
	 * @param listener - 버튼 눌렀을 때 수행할 작업 생성
	 * 
	 */
	public ActionListener listener = new ActionListener() { // 버튼 눌렀을 때 수행할 작업 생성

		@Override
		public void actionPerformed(ActionEvent e) {

			JButton button = (JButton) e.getSource(); // 버튼을 가져옴
			switch (button.getText()) {
			case EXIT: { // 게임 종료

				setVisible(false);

				System.exit(0);

				break;
			}
			case GAME_START:
			{

				setVisible(false);
				new MainGUI("벽돌 깨기");

				break;

			}
			case SCORE:{
				
				setVisible(false);
				new ScoreGUI(getTitle());
				break;
				
				
			}
			
			case LOGOUT:{
				int reply = JOptionPane.showConfirmDialog(null,"정말로 나가실건가요?","로그아웃",JOptionPane.YES_NO_OPTION);
				if(reply == JOptionPane.YES_NO_OPTION) {
					setVisible(false);
					new UnloginMenu(getTitle());
				}else {
					break;
				}
				
			}

		}
		}
	};
	

	/**
	 * 제목, 배경화면, 시작 버튼이 부착되어 있는 메뉴
	 * @param title - 게임 제목 </br>
	 *             
	 * 
	 */
	public LoggedInMenu(String title) { // 메뉴 생성자

		super("벽돌 깨기"); // Menu의 타이틀 선정
		
		
		JPanel panel = new JPanel(); // 이미지를 부착할 패널 인스턴스 생성

		Image image1 = null; // 이미지를 가져오는 작업
		try {
			File sourceimage = new File("C:/Users/herom/PARK/java.project/src/image/background3.jpg");
			image1 = ImageIO.read(sourceimage);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Image changeImg = image1.getScaledInstance(800, 800, image1.SCALE_SMOOTH); // 수정할 이미지 세부 설정
		ImageIcon changeIcon = new ImageIcon(changeImg); // 수정된 이미지를 생성

		panel = new JPanel() { // 패널에 이미지를 붙여줌
			public void paint(Graphics g) {	

				g.drawImage(changeIcon.getImage(), 0, 0, null); // 이미지를 그려줌

			}

		};

		button = new JButton[MAIN_START_BUTTON.length]; // 버튼 배열 공간 만들기
		int k = 130;
		
		for (int i = 0; i < MAIN_START_BUTTON.length; ++i) {
			button[i] = new JButton(MAIN_START_BUTTON[i]); // 버튼 생성
			button[i].setFont(new Font("고딕체", Font.CENTER_BASELINE, 30)); // 버튼 폰트 설정

			button[i].setForeground(new Color(33, 33, 33)); // 버튼 글자색
			button[i].setBackground(new Color(216, 216, 216)); // 버튼 배경색

			button[i].addActionListener(listener); // 버튼 동작 추가

			button[i].setLayout(null); // 버튼 배치 방식 자유롭게
			button[i].setBounds(280,k += 120,250,100);
			this.add(button[i]); // 버튼 패널에 추가
			
		}
		
		
		
		int acc = 150;
		JPanel panel1 = new JPanel();
		for(int i = 4; i< MAIN_START_BUTTON.length; ++i) {
			button[i] = new JButton(MAIN_START_BUTTON[i]); // 버튼 생성
			button[i].setFont(new Font("고딕체", Font.CENTER_BASELINE, 15)); // 버튼 폰트 설정

			button[i].setForeground(new Color(33, 33, 33)); // 버튼 글자색
			button[i].setBackground(new Color(216, 216, 216)); // 버튼 배경색

			button[i].addActionListener(listener); // 버튼 동작 추가

			
			button[i].setLayout(null); // 버튼 배치 방식 자유롭게
			
			button[i].setBounds(acc += 120,20,120,40);
		
			this.add(button[i]);
		
			
		}
		
		

		// 제목 붙이기
		JLabel j = new JLabel("벽돌깨기", SwingConstants.CENTER); // "벽돌깨기"라는 제목을 라벨로 생성
		j.setFont(new Font("궁서", Font.BOLD, 80)); // 제목의 폰트를 설
		j.setForeground(new Color(250, 98, 3)); // 제목의 글자색 설정
		j.setBounds(210, 100, 400, 80); // 제목 위치
		this.add(j); // 제목 붙이기

		// 창 세부 설정
		setSize(800, 800); // 창 크기 설정
		setDefaultCloseOperation(EXIT_ON_CLOSE); // 창 닫기 기본 설정
		setLocationRelativeTo(null); // 창을 중앙에 배치
		add(panel); // 패널(이미지 붙이기)을 프레임에 부착
		setVisible(true); // 보이게끔 설정

	}

}
