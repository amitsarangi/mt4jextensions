package org.mt4j.input.inputSources.test;
import org.mt4j.MTApplication;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.input.inputProcessors.globalProcessors.CursorTracer;
import org.mt4j.sceneManagement.AbstractScene;
import org.mt4j.util.MTColor;
import org.mt4j.util.font.FontManager;
import org.mt4j.util.font.IFont;
import org.mt4j.util.math.Vector3D;

/**
 * See license.txt for license information.
 * @author Uwe Laufs
 * @version 1.0
 */
public class Scene extends AbstractScene {
	public Scene(MTApplication mtApplication, String name) {
		super(mtApplication, name);
		//Show touches
		this.registerGlobalInputProcessor(new CursorTracer(mtApplication, this));
		
		MTColor white = new MTColor(255,255,255);
		this.setClearColor(new MTColor(146, 150, 188, 255));
		//Show touches
		this.registerGlobalInputProcessor(new CursorTracer(mtApplication, this));
		
		IFont fontArial = FontManager.getInstance().createFont(mtApplication, "arial.ttf", 
				50, 	//Font size
				white,  //Font fill color
				white);	//Font outline color
		//Create a textfield
		MTTextArea textField = new MTTextArea(mtApplication, fontArial); 
		
		textField.setNoStroke(true);
		textField.setNoFill(true);
		
		textField.setText("run a TUIO device\nor the simulator");
		//Center the textfield on the screen
		textField.setPositionGlobal(new Vector3D(mtApplication.width/2f, mtApplication.height/2f));
		//Add the textfield to our canvas
		this.getCanvas().addChild(textField);
	}
	@Override
	public void init() {}
	@Override
	public void shutDown() {}
}
