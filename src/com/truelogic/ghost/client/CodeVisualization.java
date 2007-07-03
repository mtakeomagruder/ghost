package com.truelogic.ghost.client;

import com.google.gwt.user.client.ui.*;

public class CodeVisualization extends AbsolutePanel 
{
	private static final int CODE_MAX = 8;
	
	private Image oImages[] = new Image[CODE_MAX];
	private int iVisibleIdx = -1;
	
	private Image oDot = null;
	
	public CodeVisualization(int iWidth, int iHeight, int iImageWidth, int iImageHeight)
	{
		for (int iIndex = 0; iIndex < CODE_MAX; iIndex++)
		{
			oImages[iIndex] = new Image("image/code" + (iIndex + 1) + Main.getCodeVersion() + ".png");
			oImages[iIndex].setPixelSize(iImageWidth, iImageHeight);
			
			oImages[iIndex].setVisible(false);
			add(oImages[iIndex], (iWidth - iImageWidth) / 2, (iHeight / iImageHeight) / 2);
		}

		oDot = new Image("image/dot.png");
		add(oDot, -100, -100);

		setPixelSize(iWidth, iHeight);
		setVisible(0);
	}
	
	public void setVisible(int iVisibleIdx)
	{
		if (iVisibleIdx < 0 || iVisibleIdx >= CODE_MAX || iVisibleIdx == this.iVisibleIdx)
			return;
			
		oImages[iVisibleIdx].setVisible(true);

		if (this.iVisibleIdx != -1)
			oImages[this.iVisibleIdx].setVisible(false);
		
		this.iVisibleIdx = iVisibleIdx;
		
		Worm.XY oXY = Worm.function(iVisibleIdx, 1, 1, 3, 3);
		setWidgetPosition(oDot,  83 + (oXY.iX * 17) - 7,  83 + (oXY.iY * 17) - 7);
	}
}