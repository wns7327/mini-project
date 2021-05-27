package stage2;

import javax.swing.JPanel;


/**
 * Stage2Block - 스테이지2에 대한 블럭 설정
 * <ol>
 * <li>블럭 기본설정</li>
 * <li>블럭 생성자</li>
 * <li>블럭의 충돌을 상시 체크할 수 있는 스레드 생성</li>
 * <li>블럭의 충돌 설정</li>
 *
 */
@SuppressWarnings("serial")
public class Stage2Block extends JPanel implements Stage2Constant, Runnable { // 블록
	Stage2MainFrame rootComponent;

	int x = 0; // static 을 쓰면 벽돌이 사라진다. 초기값
	int y = 0;
	int i, j;
	int width = BLOCK_WIDTH;
	int height = BLOCK_HEIGHT;
	int color = 0; // 0:white 1:yellow 2:blue 3:mazanta 4:red
	boolean isHidden; // 충돌 후에, 블록이 사라지는 식

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
	public Stage2Block(int i, int j) {
		this.i = i;
		this.j = j;
		x = BLOCK_WIDTH * j + BLOCK_GAP * j; // 특정 블럭의 x위치
		y = 100 + i * BLOCK_HEIGHT + BLOCK_GAP * i;		// 특정 블럭의 y위치
		color = 4 - i; 				// 0:white 1:yellow 2:blue 3:mazanta 4:red

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
	private void checkCollisionWithBall() { // 공과 블럭이 충돌했을 때 블럭이 사라짐과 동시에 공이 블럭에 반사되도록 설정
		
		for (Stage2Ball b : rootComponent.balls) {
			if (b.isCollideWithBlock(this)) {
				for (int i = 0; i < BLOCK_ROWS; i++) { // 블럭 생성
					for (int j = 0; j < BLOCK_COLUMNS; j++) {
						Stage2Block block1 = rootComponent.blocks[i][j];
					
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
