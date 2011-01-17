package org.mt4jx.components.visibleComponents.widgets.toolbar.example;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import org.mt4j.MTApplication;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.sceneManagement.AbstractScene;
import org.mt4j.util.MTColor;
import org.mt4jx.components.visibleComponents.widgets.toolbar.MTLayoutContainer;
import org.mt4jx.components.visibleComponents.widgets.toolbar.MTToolbarButton;
import org.mt4jx.components.visibleComponents.widgets.toolbar.MTToolbarListItem;

/**
 * @author Alexander Phleps
 *
 */
public class TestScene extends AbstractScene {
	private MTApplication app;

	public TestScene(MTApplication mtApplication, String name) {
		super(mtApplication, name);
		this.app = mtApplication;		
		this.getCanvas().setFrustumCulling(false);
		this.setClearColor(new MTColor(230, 230, 230));
		
		//path to image files
		String imagePath = System.getProperty("user.dir") + File.separator + "icons" + File.separator;
		
		//create one MTLayoutContainer for the main icons		
		final MTLayoutContainer buttonContainer = new MTLayoutContainer(MTLayoutContainer.ALIGN_BOTTOM, MTLayoutContainer.LAYOUT_HORIZONTAL, app);

		//add a button with  action listener assigned
		final MTToolbarButton tbb1 = new MTToolbarButton(app.loadImage(imagePath + "project.png"), app);
		tbb1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				switch(event.getID()) {
					case TapEvent.TAPPED:
						System.out.println("click on button with action listener");
					break;
				}
			}
        });
		
		//create three more with no action listeners		
		final MTToolbarButton tbb2 = new MTToolbarButton(app.loadImage(imagePath + "variants.png"), app);
		final MTToolbarButton tbb3 = new MTToolbarButton(app.loadImage(imagePath + "library.png"), app);
		final MTToolbarButton tbb4 = new MTToolbarButton(app.loadImage(imagePath + "help.png"), app);
		
		//add those four buttons to the buttonContainer we created before		
		buttonContainer.addChild(tbb1);
		buttonContainer.addChild(tbb2);
		buttonContainer.addChild(tbb3);
		buttonContainer.addChild(tbb4);
		
		
		//create another container called listContainer		
		//TODO: wenn nicht ALIGN_NONE bei listen funktioniert positionierung nicht 
		final MTLayoutContainer listContainer = new MTLayoutContainer(MTLayoutContainer.ALIGN_NONE, MTLayoutContainer.LAYOUT_VERTICAL, app);

		//create a listItem with action listener
		final MTToolbarListItem item1 = new MTToolbarListItem("test ...", app, 24);
		item1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				switch(event.getID()) {
					case TapEvent.TAPPED:
//						menu.align(MTToolbar.TOP); //just temporary
						System.out.println("click on label");
					break;
				}
			}
        });
		
		//create two more list items with no actions
		final MTToolbarListItem item2 = new MTToolbarListItem("test ... item 2", app, 24);
		final MTToolbarListItem item3 = new MTToolbarListItem("3 3 3", app, 24);
		
		//add items to list container
		listContainer.addChild(item1);
		listContainer.addChild(item2);
		listContainer.addChild(item3);
		
		//add listContainer to button2
		tbb2.addSubmenu(listContainer);
		
		
		
		//PT. 4
		//let's create another container
		final MTLayoutContainer submenuContainer = new MTLayoutContainer(MTLayoutContainer.ALIGN_NONE, MTLayoutContainer.LAYOUT_HORIZONTAL, app);
		
		//some buttons
		final MTToolbarButton submenuButton1 = new MTToolbarButton(app.loadImage(imagePath + "breakroom.png"), app);
		final MTToolbarButton submenuButton2 = new MTToolbarButton(app.loadImage(imagePath + "conference.png"), app);
		final MTToolbarButton submenuButton3 = new MTToolbarButton(app.loadImage(imagePath + "norm.png"), app);
		
		//add the buttons to the new container
		submenuContainer.addChild(submenuButton1);
		submenuContainer.addChild(submenuButton2);
		submenuContainer.addChild(submenuButton3);
		
		//and this time we add the container as submenu to the toolbarButton3
		tbb3.addSubmenu(submenuContainer);		
		//FINALLY!!! we add buttonContainer to canvas
		this.getCanvas().addChild(buttonContainer);
	}
	@Override
	public void init() {
	}

	@Override
	public void shutDown() {
	}

}
