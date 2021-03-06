package org.mt4jx.examples;

import java.util.Timer;
import java.util.TimerTask;

import org.mt4j.MTApplication;
import org.mt4j.components.visibleComponents.font.IFont;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.components.visibleComponents.widgets.MTTextArea.ExpandDirection;
import org.mt4j.components.visibleComponents.widgets.keyboard.ITextInputListener;
import org.mt4j.components.visibleComponents.widgets.keyboard.MTKeyboard;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.input.inputProcessors.globalProcessors.CursorTracer;
import org.mt4j.sceneManagement.AbstractScene;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;
import org.mt4j.components.visibleComponents.font.FontManager;

public class FontSelectScene extends AbstractScene {

  private static final String DEMO_TEXT = "This version of the test program uses the regular FontManager";

  private MTTextArea demonstrationText;
  private Timer textUpdateTimer;
  
  public FontSelectScene(MTApplication mtApplication, String name) {
    
    super(mtApplication, name);
    
    setClearColor(new MTColor(126, 130, 168, 255));
    registerGlobalInputProcessor(new CursorTracer(mtApplication, this));
    
    FontManager fm = FontManager.getInstance();
    
    IFont font = fm.createFont(mtApplication, "Arial", 35, MTColor.WHITE);
    
    MTTextArea selectFontTA = new MTTextArea(mtApplication, font);
    selectFontTA.setText("Tap Here to Choose a Font");
    
    selectFontTA.setFillColor(MTColor.BLACK);
    selectFontTA.unregisterAllInputProcessors();
    selectFontTA.removeAllGestureEventListeners();
    
    selectFontTA.registerInputProcessor(new TapProcessor(mtApplication));
    selectFontTA.addGestureListener(TapProcessor.class, new IGestureEventListener() {
      @Override
      public boolean processGestureEvent(MTGestureEvent ge) {
        if (ge instanceof TapEvent && ge.getId() == MTGestureEvent.GESTURE_ENDED) {
          chooseFont();
        }
        return false;
      }
    });
  
    getCanvas().addChild(selectFontTA);
    
    // Place it in the lower middle.
    selectFontTA.setPositionGlobal(new Vector3D(mtApplication.width/2f, mtApplication.height/2f));
    
    demonstrationText = new MTTextArea(mtApplication, font);
    demonstrationText.setText(DEMO_TEXT);
    demonstrationText.setFillColor(MTColor.GRAY);
    demonstrationText.unregisterAllInputProcessors();
    demonstrationText.removeAllGestureEventListeners();
    
    getCanvas().addChild(demonstrationText);
    
    // Place it in the upper middle.
    demonstrationText.setPositionGlobal(new Vector3D(mtApplication.width/2f, 0.25f * mtApplication.height));
  }
  
  private void chooseFont() {
    
    final MTKeyboard keyboard = new MTKeyboard(this.getMTApplication());
    keyboard.setFillColor(new MTColor(30, 30, 30, 210));
    keyboard.setStrokeColor(MTColor.BLACK);
    
    IFont font = FontManager.getInstance().createFont(this.getMTApplication(),
        "Arial", 50, MTColor.BLACK);
    
    final MTTextArea textArea = new MTTextArea(this.getMTApplication(), font);
    textArea.setExpandDirection(ExpandDirection.UP);
    textArea.setFillColor(new MTColor(205, 200, 177, 255));
    textArea.unregisterAllInputProcessors();
    textArea.setEnableCaret(true);
    textArea.snapToKeyboard(keyboard);
    
    keyboard.addTextInputListener(textArea);
    
    // So that typing a return closes the keyboard.
    keyboard.addTextInputListener(new ITextInputListener() {

      @Override
      public void clear() {
        // TODO Auto-generated method stub
        
      }

      @Override
      public void appendText(String text) {
        // TODO Auto-generated method stub
        
      }

      @Override
      public void setText(String text) {
        // TODO Auto-generated method stub
        
      }

      @Override
      public void appendCharByUnicode(String unicode) {
        if (unicode.contains("\n")) {
          String fontName = textArea.getText().trim();
          keyboard.close();
          if (fontName.length() > 0) {
            setDemonstrationTextFont(fontName);
          }
        }
      }

      @Override
      public void removeLastCharacter() {
        // TODO Auto-generated method stub
        
      }
      
    });
    
    getCanvas().addChild(keyboard);
    keyboard.setPositionGlobal(new Vector3D(this.getMTApplication().width/2f,
        this.getMTApplication().height/2f));
  }
  
  private void setDemonstrationTextFont(String fontName) {
    
    try {
      
      FontManager fm = FontManager.getInstance();
      
      IFont oldFont = demonstrationText.getFont();
    
      IFont newFont = null;
      
      newFont = fm.createFont(this.getMTApplication(), 
          fontName, oldFont.getOriginalFontSize(), oldFont.getFillColor());
      
      if (newFont != null) {

        demonstrationText.setFont(newFont);          
        demonstrationText.setText("Font set to family " + newFont.getFontFamily() +
            " from file " + newFont.getFontFileName());
        
        if (textUpdateTimer != null) {
          textUpdateTimer.cancel();
        }
        
        final MTApplication app = this.getMTApplication();
        textUpdateTimer = new Timer();
        textUpdateTimer.schedule(new TimerTask() {
          @Override
          public void run() {
            app.invokeLater(new Runnable() {
              public void run() {
                demonstrationText.setText(DEMO_TEXT);
                textUpdateTimer = null;
              }
            });
          }
        }, 10000L);
        
      }
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
