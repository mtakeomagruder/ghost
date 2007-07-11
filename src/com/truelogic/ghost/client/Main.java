package com.truelogic.ghost.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.*;

public class Main implements EntryPoint, WindowResizeListener, PrefetchListener
{
	private AbsolutePanel oScreen = new AbsolutePanel();
	private Board oBoard;
	private int iIndex = 0;
    private Prefetch oPrefetch;
    private boolean bLoading = false;
    private boolean bDisplayed = false;
	
	public void onModuleLoad() 
	{
        Window.enableScrolling(false);

        bLoading = false;
        bDisplayed = false;
        
        oPrefetch = new Prefetch(this);

        oPrefetch.prefetch("image/bk.png");
		oPrefetch.prefetch("image/grid.png");
		oPrefetch.prefetch("image/code1" + getCodeVersion() + ".png");
		oPrefetch.prefetch("image/code2" + getCodeVersion() + ".png");
		oPrefetch.prefetch("image/code3" + getCodeVersion() + ".png");
		oPrefetch.prefetch("image/code4" + getCodeVersion() + ".png");
		oPrefetch.prefetch("image/code5" + getCodeVersion() + ".png");
		oPrefetch.prefetch("image/code6" + getCodeVersion() + ".png");
		oPrefetch.prefetch("image/code7" + getCodeVersion() + ".png");
		oPrefetch.prefetch("image/code8" + getCodeVersion() + ".png");
		oPrefetch.prefetch("image/dot.png");
		oPrefetch.prefetch("image/worm.png");

		oPrefetch.prefetch("image/worm0001.png");
		oPrefetch.prefetch("image/worm0010.png");
		oPrefetch.prefetch("image/worm0011.png");
		oPrefetch.prefetch("image/worm0100.png");
		oPrefetch.prefetch("image/worm0101.png");
		oPrefetch.prefetch("image/worm0110.png");
		oPrefetch.prefetch("image/worm0111.png");
		oPrefetch.prefetch("image/worm1000.png");
		oPrefetch.prefetch("image/worm1001.png");
		oPrefetch.prefetch("image/worm1010.png");
		oPrefetch.prefetch("image/worm1011.png");
		oPrefetch.prefetch("image/worm1100.png");
		oPrefetch.prefetch("image/worm1101.png");
		oPrefetch.prefetch("image/worm1110.png");
		oPrefetch.prefetch("image/worm1111.png");
		
        Timer oTimer = new Timer() 
        {
            public void run() 
            {
                if (bLoading == false)
                {
                    endPrefetch();
                }
            } 
        };      
        
        oTimer.schedule(1000);
	}
	
	public static native String getCodeVersion() /*-{
		return($wnd.getCodeVersion());
	}-*/;

	public static native int getInterval() /*-{
	  	return($wnd.getInterval());
	}-*/;

	public static native int getSegmentsPerInterval() /*-{
	  return($wnd.getSegmentsPerInterval());
	}-*/;
	
    public void onWindowResized(int iWidth, int iHeight)
    {
    	oScreen.setPixelSize(iWidth, iHeight);
    	
    	int iLeft = (iWidth - 525) / 2;
    	int iTop = (iHeight - 525) / 2;
    	
    	oScreen.setWidgetPosition(oBoard, iLeft < 0 ? 0 : iLeft, iTop < 0 ? 0 : iTop); 
    }
    
    public void beginPrefetch()
    {
        bLoading = true;
    }
    
    public void endPrefetch()
    {
        if (bDisplayed == true)
            return;
        
        bDisplayed = true;
        
        oBoard = new Board(167, 167, 167, 167, 6, 6);
        oScreen.add(oBoard);

        Window.addWindowResizeListener(this);

        if (!(getSegmentsPerInterval() == 1 || getSegmentsPerInterval() == 2 ||
                getSegmentsPerInterval() == 4 || getSegmentsPerInterval() == 8))
            {
                Window.alert("SegmentsPerInterval must be 1, 2, 4 or 8!");
                return;
            }

        if (getInterval() < 1)
        {
            Window.alert("Interval must be a positive integer!");
            return;
        }

        RootPanel.get().add(oScreen);
        onWindowResized(Window.getClientWidth(), Window.getClientHeight());
    
        Timer t = new Timer() 
        {
            public void run() 
            {
                if (iIndex == 0)
                    oBoard.shift();
            
                for (int iSegIdx = 0; iSegIdx < getSegmentsPerInterval(); iSegIdx++)
                {
                    oBoard.animate(iIndex);
                    iIndex++;
                }

                if (iIndex > 7)
                    iIndex = 0;
            }
        };

        t.scheduleRepeating(getInterval()); 
    }
    
    
}