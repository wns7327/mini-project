package stage3;

import java.awt.event.KeyEvent;

import stage2.Stage2Constant;

/**
 * 
 * Stage3Bar - 바에 대한 기본 설정 
 * <li>바의 스텟</li>
 *
 */
public class Stage3Bar implements Stage3Constant { 

	Stage3MainFrame rootComponent;

	public static int x = CANVAS_WIDTH / 2 - BAR_WIDTH / 2;
	public static int y = CANVAS_HEIGHT - 100;
	static int width = BAR_WIDTH;
	static int height = BAR_HEIGHT;
}
