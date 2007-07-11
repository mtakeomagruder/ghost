package com.truelogic.ghost.client;

import com.google.gwt.user.client.ui.*;

public class Prefetch 
{
	private int iPreTotal = 0;
	private int iTotal = 0;
	private PrefetchListener oListener;
	private VerticalPanel oPanel = new VerticalPanel();

	LoadListener PrefetchEvent = new LoadListener()
	{
		public void onError(Widget sender) 
		{
			onLoad(sender);
		}
		
		public void onLoad(Widget sender)
		{
			iPreTotal--;
			
			if (iPreTotal == 0)
			{			
				oListener.beginPrefetch();
			}
		}
	};

	LoadListener FetchEvent = new LoadListener()
	{
		public void onError(Widget sender) 
		{
			onLoad(sender);
		}
		
		public void onLoad(Widget sender)
		{
			iTotal--;
			
			if (iTotal == 0)
			{			
				RootPanel.get().remove(oPanel);
				oListener.endPrefetch();
			}
		}
	};
	
	public Prefetch(PrefetchListener oListener)
	{
		this.oListener = oListener;
		
		oPanel.setVisible(false);
		RootPanel.get().add(oPanel);
	}
	
	public void prefetch(String strURL)
	{
		iPreTotal++;
		Image oImage = new Image();
		oImage.addLoadListener(PrefetchEvent);
		oImage.setUrl(strURL);
		
		oPanel.add(oImage);
	}

	public void fetch(String strURL)
	{
		iTotal++;
		Image oImage = new Image();
		oImage.addLoadListener(FetchEvent);
		oImage.setUrl(strURL);
		oPanel.add(oImage);
	}
}
