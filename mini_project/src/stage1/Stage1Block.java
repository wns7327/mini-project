package stage1;

import javax.swing.JPanel;

import stage2.Stage2Constant;

/**
 * Stage1Block - 스테이지1에 대한 블럭 설정
 * <ol>
 * <li>블럭 기본설정</li>
 * <li>블럭 생성자</li>
 * <li>블럭의 충돌을 상시 체크할 수 있는 스레드 생성</li>
 * <li>블럭의 충돌 설정</li>
 *
 */
@SuppressWarnings("serial")
public class Stage1Block extends JPanel implements Stage1Constant, Runnable {
	Stage1MainFrame rootComponent;

	int x = 0; 
	int y = 0;
	int i, j;
	int width = BLOCK_WIDTH;
	int height = BLOCK_HEIGHT;
	int color = 0; 
	boolean isHidden; 

	/**
	 * 
	 * @param i	- 블럭의 특정 행
	 * @param j	- 블럭의 특정 열</br>
	 * 
	 * <li> 블럭의 x,y위치와 블럭의 색 설정</li>
	 * 
	 * 
	 * 
	 */
	public Stage1Block(int i, int j, Stage1Ball[] balls) {
		this.i = i;
		this.j = j;
		rootComponent.balls = balls;
		x = BLOCK_WIDTH * j + BLOCK_GAP * j; //
		y = 100 + i * BLOCK_HEIGHT + BLOCK_GAP * i;
		width = BLOCK_WIDTH;
		height = BLOCK_HEIGHT;
		color = 4 - i; 

	}
	/**
	 * 	<li>블럭이 사라지지 않았을 때 </br>
	 * 	블럭과 공의 충돌을 상시 체크함</li>
	 * 	<li>게임 오버되면 종료함</li>
	 */
	@Override
	public void run() {  
		try {
			while(!isHidden) {
				checkCollisionWithBall();
				Thread.sleep(20);
				
				
				if(rootComponent.isGameOver()) {
	                break;
	            }

	
				
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 
	 * 공과 블럭이 충돌했을 때 </br>
	 * 블럭이 사라짐과 동시에 공이 블럭에 반사되도록 설정
	 * 
	 */
	private void checkCollisionWithBall() { 
		
		for (Stage1Ball b : rootComponent.balls) {
			if (b.isCollideWithBlock(this)) {
				for (int i = 0; i < BLOCK_ROWS; i++) { 
					for (int j = 0; j < BLOCK_COLUMNS; j++) {
						Stage1Block block1 = rootComponent.blocks[i][j];
					
						if (isHidden == false) {
							if (b.y > block1.y + 2 &&  b.y < block1.y + BLOCK_HEIGHT - 2) {		// x방향 변환	
								b.x_direction *= -1;
								isHidden = true;
								rootComponent.score += (color + 1) * 10;
							} 

							if (b.x >= block1.x + 2 &&  b.x <= block1.x + BLOCK_WIDTH - 2) {		 // y방향 변환	
								b.y_direction *= -1;
								isHidden = true;
								rootComponent.score += (color + 1) * 10;
							} 

						}
					}
				}

			}

		}
	}

}
