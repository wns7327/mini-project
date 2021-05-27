package stage3;
/**
 * 
 * Stage3의 상수
 * <li> 볼 상수 </li>
 *<li> 블럭 상수 </li>
 *<li> 바의 상수 </li>
 *<li> 창 크기 상수 </li>
 */
public interface Stage3Constant {

	static int BALL_WIDTH = 20; 
	static int BALL_HEIGHT = 20;
	static int BLOCK_ROWS = 5;
	static int BLOCK_COLUMNS = 10; 
	static int BLOCK_WIDTH = 40; 
	static int BLOCK_HEIGHT = 20; 
	static int BLOCK_GAP = 3; 
	static int BAR_WIDTH = 80; 
	static int BAR_HEIGHT = 20; 
	static int CANVAS_WIDTH = 400 + (BLOCK_GAP * BLOCK_COLUMNS) + 10;
	static int CANVAS_HEIGHT = 600;
}


