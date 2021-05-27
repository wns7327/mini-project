package stage2;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

import javax.sql.rowset.Joinable;
import javax.swing.JButton;
import javax.swing.JPanel;

import main.MainGUI;
import main.FinishGUI;
import stage1.Stage1MainFrame;
import stage3.Stage3MainFrame;



/**
 * Stage2Ball - 스테이지2에 대한 공 기본 설정
 * <ol>
 * <li> 공의 상하좌우 4개의 포인트 설정 </li>
 * <li> 공 스텟 </li>
 * <li> 공이 멱에 충돌할 때 설정 </li>
 * <li> 공의 움직임 설정</li>
 * <ol>
 *
 */
@SuppressWarnings("serial")
public class Stage2Ball extends JPanel implements Stage2Constant, Runnable {
	Stage2MainFrame rootComponent;


	int x = CANVAS_WIDTH / 2 - BALL_WIDTH / 2 + 20; // 공 x축 위치 (바의 위에 위치하게끔) 
	int y = CANVAS_HEIGHT - BALL_WIDTH / 2 - 110; // 공 y축 위치 (바의 위에 위치하게끔)
	int width = BALL_WIDTH;  //공 너비
	int height = BALL_HEIGHT; // 공 높이
	int ballSpeed; // 공속도
	int x_direction, y_direction; // 공의 x방향, y방향

	Stage2Bar bar;
	/**
	 * 
	 * 볼의 중앙
	 */
	Point getCenter() { // 볼의 가운데
		return new Point(x + (BALL_WIDTH / 2), y + (BALL_HEIGHT / 2)); // 볼의 중간
	}
	/**
	 * 
	 * 볼의 하단
	 */
	Point getBottomCenter() { // 볼의 밑부분
		return new Point(x + (BALL_WIDTH / 2), y + (BALL_HEIGHT)); // 볼의 밑부분

	}
	/**
	 * 
	 * 볼의 상단
	 */
	Point getTopCenter() { // 볼의 윗부분
		return new Point(x + (BALL_WIDTH / 2), y);
	}
	/**
	 * 
	 * 볼의 좌
	 */
	Point getLeftCenter() { // 볼의 왼쪽
		return new Point(x, y + (BALL_HEIGHT / 2));
	}

	/**
	 * 
	 * 볼의 우
	 */
	Point getRightCenter() { // 볼의 오른쪽
		return new Point(x + (BALL_WIDTH), y + (BALL_HEIGHT / 2));
	}

	/**
	 * 
	 * 
	 * @param rootComponent - 스테이지2의 프레임
	 * <li>게임 시작시 x방향 50%확률로 공이 우 아니면 좌로 가게끔 설정</li>
	 * <li>게임 시작시 y방향 공이 위로 향하게함. 
	 */
	Stage2Ball(Stage2MainFrame rootComponent) {
		this.rootComponent = rootComponent;
		x_direction = Math.random() > 0.5 ? 1 : -1; // 50% 확률로 공이 우 아니면 좌로 가게끔 설정
		y_direction = -1; // 게임 시작했을때 공이 위로 향하게함.
		ballSpeed = (int) (Math.random() * 3) + 5; // 공속도를 랜덤으로 설정함.
	}

	
	/**
	 * 
	 *	충돌 여부를 체크하는 메서드
	 *	<li>공이 왼쪽 벽이나 오른쪽 벽에 맞닿을 때 반사</li>
	 *	<li>공이 천장에 닿았을 때 반사됨</li>	
	 *	<li>공의 밑부분이 바의 윗부분과 맞닿을 때 반사됨</li>
	 * 
	 */
	private void checkCollision() { // 충돌 여부

		if (x <= 0 || x + BALL_WIDTH >= CANVAS_WIDTH) { // 왼쪽 벽, 오른쪽 벽 맞닿았을 때 반사됨
			x_direction *= -1;
		}

		if (y <= 0) { // 공이 천장에 닿았을때 반사됨
			y_direction *= -1;
		}

		if (getBottomCenter().y >= Stage2Bar.y) { // 공의 밑부분이 바의 윗부분과 맞닿았을 때 
			if (isCollideWithBar(new Rectangle(x, y, width, height),
					new Rectangle(bar.x, bar.y, bar.width, bar.height))) {
				y_direction *= -1;
			}
		}

	}

	/**
	 * 
	 * @param x - (공속도 * x축 방향)만큼 움직임
	 * @param y - (공속도 * y축 방향)만큼 움직임 </br>
	 * 
	 * 공이 움직이면서 충돌 여부를 상시 확인
	 */
	private void movement() { // 공의 움직임

		x += (ballSpeed * x_direction); // Ball x축
		y += (ballSpeed * y_direction); // Ball y축

		checkCollision(); // 공이 움직이면서 충돌 여부를 상시 확인함.

	}

	/**
	 * 
	 * @param rect1	- 사각형 
	 * @param rect2	- 사각형
	 * @return	두 사각형이 서로 겹치는가에 대해 리턴
	 */
	private boolean isCollideWithBar(Rectangle rect1, Rectangle rect2) {		// 공이 바랑 충돌했을 때

		return rect1.intersects(rect2);					// 두 사각형이 서로 겹치는가?

	}

	/**
	 * 
	 * @param block - 블럭
	 * @return - 두 사각형이 서로 겹치는가에 대한 조건 리턴
	 */
	boolean isCollideWithBlock(Stage2Block block) {					// 공이 블럭이랑 충돌했을 때
		Rectangle rect1 = new Rectangle(x, y, width, height);
		Rectangle rect2 = new Rectangle(block.x, block.y, block.width, block.height);
		return rect1.intersects(rect2); // 두 사각형이 서로 겹치는가?
	}


	

	/**
	 * <li>공의 움직임</li>
	 * <li>무효화</li>
	 * <li>게임 오버되거나 게임 클리어 됐을 때 이 스레드의 무한루프에서 빠져나감</li>
	 */
	@Override
	public void run() {

		
		try {
			while (true) {
				movement();				// 공 움직임
				invalidate();  // 무효화
			
		
				if (rootComponent.isGameOver()||rootComponent.gameClear()) {
					break;
				}
				Thread.sleep(20);
				
				
		}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
			
		}
		
	}

	
	

	

	
	

}