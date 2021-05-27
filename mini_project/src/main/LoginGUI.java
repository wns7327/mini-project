package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Menu;
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
import javax.swing.JTextField;

public class LoginGUI extends JFrame {
	

	
	MemberDAO dao = MemberDAO.getInstance();
	MemberDTO dto = new MemberDTO();
	
	
	JLabel emailLabel;
	JLabel passwordLabel;
	
	JTextField emailText; // id 적을 공간
	JTextField passwordText; // 패스워드 적을 공간
	
	JPanel panel = new JPanel();
	public String email;
	String password;
	
	
	
	private ActionListener listener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			email = emailText.getText();
			password = passwordText.getText();
			
			
			JButton button = (JButton)e.getSource();
			while(true) {
			if(button.getText() != null) {
				int result = dao.login(email, password);
				if(result == 1) {
					JOptionPane.showMessageDialog(null, "로그인 되었습니다.");
					setVisible(false);
					new LoggedInMenu(getTitle());
					break;
					
				}
				if(result == 0 || result == -1) {
					JOptionPane.showMessageDialog(null, "아이디나 비밀번호를 다시 입력해주세요");
					break;
					
				}
				if(result == -2) {
					JOptionPane.showMessageDialog(null, "오류가 났습니다. 다시 시작해주세요.");
					System.exit(0);
				
				
				
				
			}
			}
			
			
		}
	}
	};
	
	
	public LoginGUI() {
		
		super("벽돌 깨기");
//		this.nickName = nickName;
		
		
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
		
		
		JButton button = new JButton("확인");
		
		button.setFont(new Font("고딕체", Font.CENTER_BASELINE, 25)); // 버튼 폰트 설정

		button.setForeground(new Color(33, 33, 33)); // 버튼 글자색
		button.setBackground(new Color(216, 216, 216)); // 버튼 배경색

		button.addActionListener(listener); // 버튼 동작 추가

		button.setLayout(null); // 버튼 배치 방식 자유롭게
		button.setBounds(280,650 , 250, 60); // 버튼 위치 설정
		this.add(button); // 버튼 패널에 추가

		
		JLabel registerTitle = new JLabel();
		
		
		
		
		emailLabel = new JLabel("이메일 : ");				// id라벨 생성
		emailLabel.setFont(new Font("고딕체",Font.BOLD,30));
		emailLabel.setForeground(new Color(7, 235, 216));
		emailLabel.setBounds(100,190,150,100);
		this.add(emailLabel);
		
		passwordLabel = new JLabel("패스워드 : ");// 패스워드 라벨 생성
		passwordLabel.setFont(new Font("고딕체",Font.BOLD,30));
		passwordLabel.setForeground(new Color(7, 235, 216));
		passwordLabel.setBounds(70,330,150,100);
		this.add(passwordLabel);
		
		
		
		
	
		
		
		
		emailText = new JTextField();// 아이디 적을공간 생성
		emailText.setBounds(260,220, 400,40);
		this.add(emailText);
		
		passwordText = new JTextField();//패스워드 적을공간 생성
		passwordText.setBounds(260,360, 400,40);
		this.add(passwordText);

		
		
		setSize(800,800);
		setDefaultCloseOperation(EXIT_ON_CLOSE);// 창 닫기 기본 설정
		setLocationRelativeTo(null);// 창을 중앙에 배치

		add(panel);// 패널(이미지 붙이기)을 프레임에 부착

		setVisible(true);// 보이게끔 설정
	}
	
	
	
}
