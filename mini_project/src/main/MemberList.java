package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MemberList extends JFrame {

	JLabel idLabel;
	JLabel passwordLabel;
	JLabel nickNameLabel;

	JTextField emailText; // id 적을 공간
	JTextField passwordText; // 패스워드 적을 공간
	JTextField nickNameText; // 닉네임 적을 공간

	JPanel panel; // 패널 선언
	MemberDAO dao = MemberDAO.getInstance();
	MemberDTO dto = new MemberDTO();

	private ActionListener listener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton jButton = (JButton) e.getSource();
			if (jButton.getText() != null) {

				String email = emailText.getText();
				String password = passwordText.getText();
				String nickName = nickNameText.getText();

				dto.setEmail(email); // 이메일 추가 , db에 추가
				dto.setPassword(password); // 패스워드 추가, db에 추가
				dto.setNickName(nickName); // 닉네임 추가, db에 추가

				dao.insertMember(dto);

				while (true) {
					String emailRegex = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}$";
					// 이메일 체크 정규식
					Pattern emailP = Pattern.compile(emailRegex);
					Matcher emailM = emailP.matcher(email);

					String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,15}$";
					// 특수문자 / 문자 / 숫자 포함 형태의 8~15자리 이내의 암호 정규식
					Pattern passwordP = Pattern.compile(passwordRegex);
					Matcher passwordM = passwordP.matcher(password);

					if (nickName.isEmpty() || nickName.isEmpty() || nickName.isEmpty()) {
						JOptionPane.showMessageDialog(null, "비어있는 칸이 있습니다.");
						dao.delete(email);
						break;
					}

					if (!passwordM.matches() || !emailM.matches()) {
						JOptionPane.showMessageDialog(null, "아이디 비밀번호 중에 조건을 충족하지 않은 문장이 있습니다.");
						dao.delete(email);
						break;
					}
					
					int resultEmail = dao.confirmEmail(email);
					
					if(resultEmail == 1) {
						JOptionPane.showMessageDialog(null, "아이디가 이미 존재");
						break;
					}else {
						
					}
						
					
						
						
					if (passwordM.matches() && emailM.matches() && !nickName.isEmpty()) {
						JOptionPane.showMessageDialog(null, "가입 완료!");
						setVisible(false);
						new LoggedInMenu(getTitle());
						break;
					}

				}
			}
		}

	};

	public MemberList(String title) {

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
		
		
		
		
		

		JButton button = new JButton("가입");

		button.setFont(new Font("고딕체", Font.CENTER_BASELINE, 25)); // 버튼 폰트 설정

		button.setForeground(new Color(33, 33, 33)); // 버튼 글자색
		button.setBackground(new Color(216, 216, 216)); // 버튼 배경색

		button.addActionListener(listener); // 버튼 동작 추가

		button.setLayout(null); // 버튼 배치 방식 자유롭게
		button.setBounds(280, 650, 250, 60); // 버튼 위치 설정
		this.add(button); // 버튼 패널에 추가

		idLabel = new JLabel("아이디 : "); // id라벨 생성
		idLabel.setFont(new Font("고딕체", Font.BOLD, 30));
		idLabel.setForeground(new Color(7, 235, 216));
		idLabel.setBounds(100, 130, 150, 100);
		this.add(idLabel);

		passwordLabel = new JLabel("패스워드 : ");// 패스워드 라벨 생성
		passwordLabel.setFont(new Font("고딕체", Font.BOLD, 30));
		passwordLabel.setForeground(new Color(7, 235, 216));
		passwordLabel.setBounds(70, 330, 150, 100);
		this.add(passwordLabel);

		nickNameLabel = new JLabel("닉네임 : ");// 닉네임 라벨 생성
		nickNameLabel.setFont(new Font("고딕체", Font.BOLD, 30));
		nickNameLabel.setForeground(new Color(7, 235, 216));
		nickNameLabel.setBounds(100, 530, 150, 100);
		this.add(nickNameLabel);

		emailText = new JTextField();// 아이디 적을공간 생성
		emailText.setBounds(260, 160, 400, 40);
		this.add(emailText);

		passwordText = new JTextField();// 패스워드 적을공간 생성
		passwordText.setBounds(260, 360, 400, 40);
		this.add(passwordText);

		nickNameText = new JTextField();// 닉네임 적을 공간 생성
		nickNameText.setBounds(260, 560, 400, 40);
		this.add(nickNameText);

		setSize(800, 800);
		setDefaultCloseOperation(EXIT_ON_CLOSE);// 창 닫기 기본 설정
		setLocationRelativeTo(null);// 창을 중앙에 배치

		add(panel);// 패널(이미지 붙이기)을 프레임에 부착

		setVisible(true);// 보이게끔 설정

	}

}
