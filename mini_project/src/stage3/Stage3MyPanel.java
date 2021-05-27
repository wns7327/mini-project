package stage3;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import stage2.Stage2Constant;
/**
 * 
 * Stage3MyPanel - 스테이지2 패널에 대한 설정
 * <ol>
 * <li> 스테이지2 패널 생성자 설정</li>
 * <li> 공,블럭,바를 그리기 위한 그래픽스 설정</li>
 * </ol>
 *
 */
@SuppressWarnings("serial")
public class Stage3MyPanel extends JPanel implements Stage2Constant {

	Stage3MainFrame rootComponent;
	ImageIcon changeIcon3;
	/**
	 * 창 크기와 배경 설정
	 */
	public Stage3MyPanel() {
		this.setSize(CANVAS_WIDTH, CANVAS_HEIGHT);
		JPanel panel = new JPanel(); // 이미지를 부착할 패널 인스턴스 생성

		Image image1 = null; // 이미지를 가져오는 작업
		try {
			File sourceimage = new File("C:/Users/herom/PARK/java.project/src/image/background3.jpg");
			image1 = ImageIO.read(sourceimage);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Image changeImg = image1.getScaledInstance(CANVAS_WIDTH, CANVAS_HEIGHT, image1.SCALE_SMOOTH); // 수정할 이미지 세부 설정
		changeIcon3 = new ImageIcon(changeImg); // 수정된 이미지를 생성

		
		
		
	}
	
	/**
	 * 
	 * 	UI를 그리기 위한 paint 설정
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(changeIcon3.getImage(), 0, 0, null); // 이미지를 그려줌
		Graphics2D g2d = (Graphics2D) g;
		drawUI(g2d);
	}
	/**
	 * 
	 * 블럭 첫번째 - 하얀색</br>
	 * 블럭 두번째 - 노란색</br>
	 * 블럭 세번째 - 파란색</br>
	 * 블럭 네번째 - 심홍색</br>
	 * 블럭 다섯번째 - 빨간색</br></br>
	 * 
	 * <li>점수 - 점수색, 글씨 폰트 설정</li>
	 * <li>공 2개 설정</li>
	 * <li>바 설정</li>
	 * 
	 */
	public void drawUI(Graphics2D g2d) {


		for (int i = 0; i < BLOCK_ROWS; i++) {
			for (int j = 0; j < BLOCK_COLUMNS; j++) {

				if (rootComponent.blocks[i][j].isHidden) {
					continue;
				} else if (rootComponent.blocks[i][j].color == 0) {
					g2d.setColor(Color.WHITE);
				} else if (rootComponent.blocks[i][j].color == 1) {
					g2d.setColor(Color.YELLOW);
				} else if (rootComponent.blocks[i][j].color == 2) {
					g2d.setColor(Color.BLUE);
				} else if (rootComponent.blocks[i][j].color == 3) {
					g2d.setColor(Color.MAGENTA);
				} else if (rootComponent.blocks[i][j].color == 4) {
					g2d.setColor(Color.RED);
				}
				g2d.fillRect(rootComponent.blocks[i][j].x, rootComponent.blocks[i][j].y,
						rootComponent.blocks[i][j].width, rootComponent.blocks[i][j].height);


			}


			g2d.setColor(Color.getHSBColor(78, 100, 95));
			g2d.setFont(new Font("고딕체", Font.BOLD, 20));
			g2d.drawString("score : " + rootComponent.score, CANVAS_WIDTH / 2 - 50, 20);

			
			g2d.setColor(Color.BLACK); 
			g2d.fillOval(rootComponent.balls[0].x, rootComponent.balls[0].y, BALL_WIDTH, BALL_HEIGHT); // Ÿ�� �׸���

			
			g2d.setColor(Color.WHITE); 
			g2d.fillOval(rootComponent.balls[1].x, rootComponent.balls[1].y, BALL_WIDTH, BALL_HEIGHT); // Ÿ�� �׸���

			
			g2d.setColor(Color.RED); 
			g2d.fillOval(rootComponent.balls[2].x, rootComponent.balls[2].y, BALL_WIDTH, BALL_HEIGHT); // Ÿ�� �׸���
			
			
			
			g2d.setColor(Color.LIGHT_GRAY);
			g2d.fillRect(rootComponent.bar.x, rootComponent.bar.y, BAR_WIDTH, BAR_HEIGHT);



		}
	}
}
