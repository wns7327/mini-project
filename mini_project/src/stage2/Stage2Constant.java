package stage2;

/**
 * 
 * Stage2의 상수
 * <li> 볼 상수 </li>
 *<li> 블럭 상수 </li>
 *<li> 바의 상수 </li>
 *<li> 창 크기 상수 </li>
 */
public interface Stage2Constant {
	// 볼,블럭,바의 상수 -- 인터페이스로 빼도됨
	static int BALL_WIDTH = 20; // 볼의 폭
	static int BALL_HEIGHT = 20; // 볼의 높이
	static int BLOCK_ROWS = 5; // 블록 행
	static int BLOCK_COLUMNS = 10; // 블록 열
	static int BLOCK_WIDTH = 40; // 블록 너비
	static int BLOCK_HEIGHT = 20; // 블록 높이
	static int BLOCK_GAP = 3; // 블록 사이의 갭
	static int BAR_WIDTH = 80; // 바의 너비
	static int BAR_HEIGHT = 20; // 바의 높이
	static int CANVAS_WIDTH = 400 + (BLOCK_GAP * BLOCK_COLUMNS) + 10;
	static int CANVAS_HEIGHT = 600;
}
