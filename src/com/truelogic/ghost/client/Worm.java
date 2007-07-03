package com.truelogic.ghost.client;

import com.google.gwt.user.client.ui.*;

import java.util.*;

public class Worm extends AbsolutePanel 
{
	private Vector oSegments = new Vector();

	static public class XY
	{
		int iX = 0;
		int iY = 0;
	}
	
	private class Segment
	{
		int iX;
		int iY;
		Image oImage = null;
		
		public Segment(int iX, int iY)
		{
			this.iX = iX;
			this.iY = iY;
			oImage = new Image("image/worm.png");
		}
	}

	private class Space
	{
		int iCount = 0;
		Image oImage = null;
		String strImage = null;
	}

	private int iX = 7;
	private int iY = 7;
	
	private int iWidth = 15;
	private int iSize = 13;
	private int iLength = 32;

	private Space[][] oySpaces = new Space[iWidth][iWidth]; 
	
	public Worm()
	{
		setPixelSize(195, 195);
		
		for (int iX = 0; iX < iWidth; iX++)
			for (int iY = 0; iY < iWidth; iY++)
				oySpaces[iX][iY] = new Space();

		moveAbsolute(7, 7);
	}
	
	static public XY function(int iIndex, int iX, int iY, int iWidth, int iHeight)
	{
		int iPos = 0;
		
		switch(iIndex)
		{
			case 0:
				iPos = function1(iX, iY, iWidth, iHeight);
				break;
			case 1:
				iPos = function2(iX, iY, iWidth, iHeight);
				break;
			case 2:
				iPos = function3(iX, iY, iWidth, iHeight);
				break;
			case 3:
				iPos = function4(iX, iY, iWidth, iHeight);
				break;
			case 4:
				iPos = function5(iX, iY, iWidth, iHeight);
				break;
			case 5:
				iPos = function6(iX, iY, iWidth, iHeight);
				break;
			case 6:
				iPos = function7(iX, iY, iWidth, iHeight);
				break;
			case 7:
				iPos = function8(iX, iY, iWidth, iHeight);
				break;
		}
		
		XY oXY = new XY();

		if ((iPos & 0xFFFF0000) >> 16 == 0x01)
			oXY.iX = 1;
		if ((iPos & 0xFFFF0000) >> 16 == 0x10)
			oXY.iX = -1;

		if ((iPos & 0x0000FFFF) == 0x01)
			oXY.iY = 1;
		if ((iPos & 0x0000FFFF) == 0x10)
			oXY.iY = -1;

		return(oXY);
	}
	
	public void move(int iIndex)
	{
		XY oXY = function(iIndex, iX, iY, iWidth, iWidth);
		moveDelta(oXY.iX, oXY.iY);
	}
	
	private void moveDelta(int iDeltaX, int iDeltaY)
	{
		if ((iX + iDeltaX >= 1) && (iY + iDeltaY >= 1) &&
			(iX + iDeltaX < iWidth - 1) && (iY + iDeltaY < iWidth - 1))
		{
			moveAbsolute(iX + iDeltaX, iY + iDeltaY);
		}
	}

	private void moveAbsolute(int iAbsoluteX, int iAbsoluteY)
	{
		iX = iAbsoluteX;
		iY = iAbsoluteY;
		
		Segment oSegment = new Segment(iX, iY);
		
		add(oSegment.oImage, iSize * iX, iSize * iY);
		oSegments.add(oSegment);
		
		oySpaces[iX][iY].iCount++;
		
		if (oySpaces[iX][iY].oImage != null)
		{
			remove(oySpaces[iX][iY].oImage);
			oySpaces[iX][iY].oImage = null;
			oySpaces[iX][iY].strImage = null;
		}

		if (oSegments.size() > iLength)
		{
			oSegment = (Segment)oSegments.get(0);
			remove(oSegment.oImage);
			oySpaces[oSegment.iX][oSegment.iY].iCount--;
			oSegments.remove(0);
		}
	}
	
	public void fill()
	{
		for (int iIdxX = 0; iIdxX < iWidth; iIdxX++)
			for (int iIdxY = 0; iIdxY < iWidth; iIdxY++)
			{
				Space oSpace = oySpaces[iIdxX][iIdxY];
				
				if (oSpace.iCount == 0)
				{
					String strImage = getImage(hasSegment(iIdxX - 1, iIdxY - 0),
											   hasSegment(iIdxX - 0, iIdxY - 1),
											   hasSegment(iIdxX + 1, iIdxY - 0),
											   hasSegment(iIdxX - 0, iIdxY + 1));
					
					if (strImage == null && oSpace.oImage != null)
					{
						remove(oSpace.oImage);
						oSpace.oImage = null;
						oSpace.strImage = null;
					}
					
					if ((strImage != null && oSpace.strImage != null && !strImage.equals(oSpace.strImage)) || (strImage != null && oSpace.strImage == null))
					{
						if (oSpace.oImage != null)
							remove(oSpace.oImage);
						
						oSpace.strImage = strImage;
						oSpace.oImage = new Image("image/" + strImage);
						
						add(oSpace.oImage, iSize * iIdxX, iSize * iIdxY);
					}
					
				}
			}
	}
	
	private String getImage(int iLeft, int iTop, int iRight, int iBottom)
	{
		if ((iLeft + iTop + iRight + iBottom) == 0)
			return(null);
		
		return("worm" + iLeft + "" + iTop + "" + iRight + "" + iBottom + ".png");
	}
	
	private int hasSegment(int iX, int iY)
	{
		if (iX >= 0 && iY >= 0 && iX < iWidth && iY < iWidth)
			if (oySpaces[iX][iY].iCount != 0)
				return(1);
		
		return(0);
	}
	
	public static native int function1(int iX, int iY, int iWidth, int iHeight) /*-{
		return($wnd.function1(iX, iY, iWidth, iHeight));
	}-*/;
	
	public static native int function2(int iX, int iY, int iWidth, int iHeight) /*-{
	  	return($wnd.function2(iX, iY, iWidth, iHeight));
	}-*/;
	
	public static native int function3(int iX, int iY, int iWidth, int iHeight) /*-{
	  	return($wnd.function3(iX, iY, iWidth, iHeight));
	}-*/;
	
	public static native int function4(int iX, int iY, int iWidth, int iHeight) /*-{
	  	return($wnd.function4(iX, iY, iWidth, iHeight));
	}-*/;
	
	public static native int function5(int iX, int iY, int iWidth, int iHeight) /*-{
	  	return($wnd.function5(iX, iY, iWidth, iHeight));
	}-*/;
	
	public static native int function6(int iX, int iY, int iWidth, int iHeight) /*-{
	  	return($wnd.function6(iX, iY, iWidth, iHeight));
	}-*/;
	
	public static native int function7(int iX, int iY, int iWidth, int iHeight) /*-{
	  	return($wnd.function7(iX, iY, iWidth, iHeight));
	}-*/;
	
	public static native int function8(int iX, int iY, int iWidth, int iHeight) /*-{
	  	return($wnd.function8(iX, iY, iWidth, iHeight));
	}-*/;

}
