package com.truelogic.ghost.generate;

import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;

public class Code 
{
	private int iWidth;
	private int iHeight;
//	private double dfScale;
//	private int iRotation;
//	private Object oInterpolation;
//	private boolean bInvertOdd;
	private Vector oColors;
//	private int iBorder;
//	private double dfScaleInterpolation;
	
	private BufferedImage oImage = null;
	
	public Code(int iWidth, int iHeight, int iBorder, double dfScale, int iRotation, Object oInterpolation, double dfScaleInterpolation, boolean bInvertOdd, Vector oColors)
	{
		this.iWidth = iWidth;
		this.iHeight = iHeight;
//		this.dfScale = dfScale;
//		this.iRotation = iRotation;
//		this.oInterpolation = oInterpolation;
//		this.bInvertOdd = bInvertOdd;
		this.oColors = oColors;
//		this.iBorder = iBorder;
//		this.dfScaleInterpolation = dfScaleInterpolation;
	}
	
	public Image generate(String strCode, int iDir) throws Exception
	{
		if (iWidth != iHeight)
			throw new Exception("Height and width must be equal!");

		int iBytes = 72;
		
		if (strCode.length() < iBytes)
		{
			String strWhitespace = "\n\t \r";
			int iMissing = iBytes - strCode.length();
			
			int iStart = (new Random()).nextInt(iMissing);
			int iEnd = iMissing - iStart;
			
			for(int iIndex = 0; iIndex < iStart; iIndex++)
				strCode = strCode + strWhitespace.charAt((new Random()).nextInt(4));

			for(int iIndex = 0; iIndex < iEnd; iIndex++)
				strCode = strWhitespace.charAt((new Random()).nextInt(4)) + strCode;
		}
		
		int iSize = 53 * 3 + 2 * 4;
		
		BufferedImage oImageStart = new BufferedImage(iSize, iSize, BufferedImage.TYPE_INT_ARGB);
		Graphics2D oGraphicsStart = oImageStart.createGraphics();
		
		encode_ring(oGraphicsStart, strCode);
		
/*		int iScaleX = (int)(iSize * dfScale);
		int iScaleY = (int)(iSize * dfScale);
		
		BufferedImage oImageScale = new BufferedImage(iScaleX, iScaleY, BufferedImage.TYPE_INT_RGB);
        Graphics2D oGraphicsScale = oImageScale.createGraphics();
		
        AffineTransform oTransform = new AffineTransform();
        oTransform.scale(dfScale, dfScale);
        oTransform.rotate(Math.toRadians(iRotation), iSize / 2, iSize / 2);
        
        oGraphicsScale.drawImage(oImageStart, oTransform, null);

        if (dfScaleInterpolation != 0)
        {
    		iScaleX = (int)(iSize * dfScale * dfScaleInterpolation);
    		iScaleY = (int)(iSize * dfScale * dfScaleInterpolation);

    		oImage = new BufferedImage(iScaleX, iScaleY, BufferedImage.TYPE_INT_RGB);
        	Graphics2D oGraphicsInterpolate = oImage.createGraphics();
        	oGraphicsInterpolate.setRenderingHint(RenderingHints.KEY_INTERPOLATION, oInterpolation);
            oGraphicsInterpolate.drawImage(oImageScale, 0, 0, iScaleX, iScaleY, null);
        }
        else
        	oImage = oImageScale;
        */
		
		oImage = oImageStart;
        
        return(oImage);
	}

	private void encode_ring(Graphics2D oImage, String strCode)
	{
		int iSquare = 9;
		int iSize = 53;
		int iBorder = 4;
		
		encode_square(oImage, 2 + 0 * (iSize + iBorder), 2 + 0 * (iSize + iBorder), iSquare, strCode.substring(0 * iSquare, 1 * iSquare));
		encode_square(oImage, 2 + 1 * (iSize + iBorder), 2 + 0 * (iSize + iBorder), iSquare, strCode.substring(1 * iSquare, 2 * iSquare));
		encode_square(oImage, 2 + 2 * (iSize + iBorder), 2 + 0 * (iSize + iBorder), iSquare, strCode.substring(2 * iSquare, 3 * iSquare));

		encode_square(oImage, 2 + 2 * (iSize + iBorder), 2 + 1 * (iSize + iBorder), iSquare, strCode.substring(3 * iSquare, 4 * iSquare));
		encode_square(oImage, 2 + 2 * (iSize + iBorder), 2 + 2 * (iSize + iBorder), iSquare, strCode.substring(4 * iSquare, 5 * iSquare));

		encode_square(oImage, 2 + 1 * (iSize + iBorder), 2 + 2 * (iSize + iBorder), iSquare, strCode.substring(5 * iSquare, 6 * iSquare));
		encode_square(oImage, 2 + 0 * (iSize + iBorder), 2 + 2 * (iSize + iBorder), iSquare, strCode.substring(6 * iSquare, 7 * iSquare));

		encode_square(oImage, 2 + 0 * (iSize + iBorder), 2 + 1 * (iSize + iBorder), iSquare, strCode.substring(7 * iSquare, 8 * iSquare));
	}

	private void encode_square(Graphics2D oImage, int iOffsetX, int iOffsetY, int iSubWidth, String strCode)
	{
		byte[] tyCode = strCode.getBytes();
		int iScale = 5;
		
		oImage.setColor((Color)oColors.get(2));
		
		for (int iIndex = 0; iIndex < strCode.length(); iIndex++)
		{
			int iXOR = 0;
			
/*			switch (iIndex % 4)
			{
				case 0:
					iXOR = 0x88;
					break;
				case 1:
					iXOR = 0x44;
					break;
				case 2:
					iXOR = 0x22;
					break;
				case 3:
					iXOR = 0x11;
					break;
			}*/

/*			switch (iIndex % 2)
			{
				case 0:
					iXOR = 0xAA;
					break;
				case 1:
					iXOR = 0x55;
					break;
			}*/
			
			byte tByte = (byte)((int)tyCode[iIndex] ^ iXOR);
			
			int iY = iIndex / 3;
			int iX = iIndex % 3;
			
			if ((tByte & 0x80) != 0)
				square(oImage,  0 + (iX * 17) + iOffsetX,  0 + (iY * 17) + iOffsetY, iScale);

			if ((tByte & 0x40) != 0)
				square(oImage,  5 + (iX * 17) + iOffsetX,  0 + (iY * 17) + iOffsetY, iScale);

			if ((tByte & 0x20) != 0)
				square(oImage, 10 + (iX * 17) + iOffsetX,  0 + (iY * 17) + iOffsetY, iScale);

			if ((tByte & 0x10) != 0)
				square(oImage,  0 + (iX * 17) + iOffsetX,  5 + (iY * 17) + iOffsetY, iScale);

			if ((tByte & 0x08) != 0)
				square(oImage, 10 + (iX * 17) + iOffsetX,  5 + (iY * 17) + iOffsetY, iScale);
			
			if ((tByte & 0x04) != 0)
				square(oImage,  0 + (iX * 17) + iOffsetX, 10 + (iY * 17) + iOffsetY, iScale);

			if ((tByte & 0x02) != 0)
				square(oImage,  5 + (iX * 17) + iOffsetX, 10 + (iY * 17) + iOffsetY, iScale);

			if ((tByte & 0x01) != 0)
				square(oImage, 10 + (iX * 17) + iOffsetX, 10 + (iY * 17) + iOffsetY, iScale);
		}
	}
	
	private void square(Graphics2D oImage, int iOffsetX, int iOffsetY, int iSize)
	{
		for (int iX = 0; iX < iSize; iX++)
			for (int iY = 0; iY < iSize; iY++)
				oImage.drawRect(iOffsetX + iX,  iOffsetY + iY, 0, 0);
	}

	public void write(String strFileName, String strCode, int iDir) throws AWTException, IOException, Exception
	{
		generate(strCode, iDir);
		write(strFileName);
	}
	
	public void write(String strFileName) throws AWTException, IOException
	{
		ImageIO.write(oImage, "PNG", new File(strFileName));
	}
}