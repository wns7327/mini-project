package stage2;


/**
 * 
 * Stage2Bar - 바에 대한 기본 설정 
 * <li>바의 스텟</li>
 *
 */
public class Stage2Bar implements Stage2Constant { //  바 모양잡기

	Stage2MainFrame rootComponent;

	public static int x = CANVAS_WIDTH / 2 - BAR_WIDTH / 2;
	public static int y = CANVAS_HEIGHT - 100;
	static int width = BAR_WIDTH;
	static int height = BAR_HEIGHT;
}
