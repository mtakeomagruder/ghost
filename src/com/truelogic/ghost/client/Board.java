package com.truelogic.ghost.client;

import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.*;

public class Board extends AbsolutePanel 
{
	private CodeVisualization oCodes[] = new CodeVisualization[8];
	private Worm oWorm;
	private int[] iyCodes = new int[8];
	
	public Board(int iWidth, int iHeight, int iImageWidth, int iImageHeight, int iMargin, int iBorder)
	{
		Image oImage = new Image("image/grid.png");
		add(oImage, 0, 0);
		
		oCodes[0] = new CodeVisualization(iWidth, iHeight, iImageWidth, iImageHeight);
		add(oCodes[0], iBorder, iBorder);

		oCodes[1] = new CodeVisualization(iWidth, iHeight, iImageWidth, iImageHeight);
		add(oCodes[1], iBorder + ((iWidth + iMargin) * 1), iBorder);

		oCodes[2] = new CodeVisualization(iWidth, iHeight, iImageWidth, iImageHeight);
		add(oCodes[2], iBorder + ((iWidth + iMargin) * 2), iBorder);

		oCodes[3] = new CodeVisualization(iWidth, iHeight, iImageWidth, iImageHeight);
		add(oCodes[3], iBorder + ((iWidth + iMargin) * 0), iBorder + ((iWidth + iMargin) * 1));

		oCodes[4] = new CodeVisualization(iWidth, iHeight, iImageWidth, iImageHeight);
		add(oCodes[4], iBorder + ((iWidth + iMargin) * 2), iBorder + ((iWidth + iMargin) * 1));

		oCodes[5] = new CodeVisualization(iWidth, iHeight, iImageWidth, iImageHeight);
		add(oCodes[5], iBorder + ((iWidth + iMargin) * 0), iBorder + ((iWidth + iMargin) * 2));

		oCodes[6] = new CodeVisualization(iWidth, iHeight, iImageWidth, iImageHeight);
		add(oCodes[6], iBorder + ((iWidth + iMargin) * 1), iBorder + ((iWidth + iMargin) * 2));

		oCodes[7] = new CodeVisualization(iWidth, iHeight, iImageWidth, iImageHeight);
		add(oCodes[7], iBorder + ((iWidth + iMargin) * 2), iBorder + ((iWidth + iMargin) * 2));
		
		oWorm = new Worm();
		add(oWorm, iBorder + ((iWidth + iMargin) * 1) - 14, iBorder + ((iWidth + iMargin) * 1) - 14);
		
		setPixelSize((iWidth + iMargin) * 3 + iMargin, (iHeight + iMargin) * 3 + iMargin); 
	}
	
	public void shift()
	{
		for (int iIndex = 0; iIndex < 8; iIndex++)
		{
			int iPos = Random.nextInt(8);
			
			oCodes[iIndex].setVisible(iPos);
			iyCodes[iIndex] = iPos;
		}
	}

	public void animate(int iIndex)
	{
		oWorm.move(iyCodes[iIndex]);
		oWorm.fill();
	}
}