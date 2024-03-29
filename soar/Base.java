package soar;

import java.io.File;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import soar.utils.FontUtils;
import soar.utils.mouse.MouseUtils;

public class Base {

	private String title;
	private int width, height, maxFPS;
	private boolean vsync;
	public boolean threaded;
	
	public void setup() {
		this.title = "Base";
		this.width = 1280;
		this.height = 720;
		this.maxFPS = 60;
		this.vsync = false;
	}
	
	public void setupOpenGL() {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, width, height, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}
	
	public void createDisplay() {
		
		System.setProperty("org.lwjgl.librarypath", new File("assets/natives/").getAbsolutePath());
		
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setTitle(title);
			Display.create();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		this.setupOpenGL();

		while(!Display.isCloseRequested()) {
			
			int mouseX = Mouse.getX();
			int mouseY = Display.getHeight() -  Mouse.getY();
			
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
			
			FontUtils.setup();
			
			this.drawScreen(mouseX, mouseY);
			
			if(MouseUtils.isClicked()) {
				this.mouseClicked(mouseX, mouseY);
			}
			
			if(MouseUtils.isReleased()) {
				this.mouseReleased(mouseX, mouseY);
			}
			
			Display.update();
			
			if(vsync == true) {
				Display.setVSyncEnabled(vsync);
			}else {
				Display.sync(maxFPS);
				Display.setVSyncEnabled(false);
			}
			
			if(Display.isCloseRequested()) {
				Soar.instance.logger.save();
				Soar.instance.logger.info("Closed Soar Launcher!");
				System.exit(0);
			}
		}
		
		Display.destroy();
	}
	
	public void drawScreen(int mouseX, int mouseY) {}
	
	public void mouseClicked(int mouseX, int mouseY) {}
	
	public void mouseReleased(int mouseX, int mouseY) {}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getWidth() {
		return Display.getWidth();
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getHeight() {
		return Display.getHeight();
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public void setMaxFPS(int maxFPS) {
		this.maxFPS = maxFPS;
	}
	
	public void setVSync(boolean vsync) {
		this.vsync = vsync;
	}
}