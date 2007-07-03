package com.truelogic.ghost.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.*;

public class Main implements EntryPoint, WindowResizeListener
{
	private AbsolutePanel oScreen = new AbsolutePanel();
	private Board oBoard;
	private int iIndex = 0;
	
	public void onModuleLoad() 
	{
		Image.prefetch("image/bk.png");
		Image.prefetch("image/grid.png");
		Image.prefetch("image/code1" + getCodeVersion() + ".png");
		Image.prefetch("image/code2" + getCodeVersion() + ".png");
		Image.prefetch("image/code3" + getCodeVersion() + ".png");
		Image.prefetch("image/code4" + getCodeVersion() + ".png");
		Image.prefetch("image/code5" + getCodeVersion() + ".png");
		Image.prefetch("image/code6" + getCodeVersion() + ".png");
		Image.prefetch("image/code7" + getCodeVersion() + ".png");
		Image.prefetch("image/code8" + getCodeVersion() + ".png");
		Image.prefetch("image/dot.png");
		Image.prefetch("image/worm.png");

		Image.prefetch("image/worm0001.png");
		Image.prefetch("image/worm0010.png");
		Image.prefetch("image/worm0011.png");
		Image.prefetch("image/worm0100.png");
		Image.prefetch("image/worm0101.png");
		Image.prefetch("image/worm0110.png");
		Image.prefetch("image/worm0111.png");
		Image.prefetch("image/worm1000.png");
		Image.prefetch("image/worm1001.png");
		Image.prefetch("image/worm1010.png");
		Image.prefetch("image/worm1011.png");
		Image.prefetch("image/worm1100.png");
		Image.prefetch("image/worm1101.png");
		Image.prefetch("image/worm1110.png");
		Image.prefetch("image/worm1111.png");
		
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
}