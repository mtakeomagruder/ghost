package com.truelogic.ghost.generate;

import java.awt.*;

import java.io.*;
import java.util.*;

public class Main 
{
	public static void main(String[] stryArgs) throws IOException, AWTException, Exception
	{
		String strPath = "c:\\code\\ghost\\src\\com\\truelogic\\ghost\\public\\";
		String strImagePath = strPath + "image\\";
		
		IniFile oIni = new IniFile(strPath + "ghost.ini");

		int iWidth = oIni.IntGet("code", "width", 16);
		int iHeight = oIni.IntGet("code", "height", 16);
		int iBorder = oIni.IntGet("code", "border", 0);
		double dfScale = oIni.DoubleGet("code", "scale", 1);
		int iRotation = oIni.IntGet("code", "rotation", 0);

		Vector oColors = new Vector();
		oColors.add(readColor(oIni.StringGet("code", "color1", "000000")));
		oColors.add(readColor(oIni.StringGet("code", "color2", "000000")));
		oColors.add(readColor(oIni.StringGet("code", "color3", "000000")));
		oColors.add(readColor(oIni.StringGet("code", "color4", "000000")));
		oColors.add(readColor(oIni.StringGet("code", "color5", "000000")));
		
		Object oInterpolation = RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR;
		String strInterpolation = oIni.StringGet("code", "interpolation", "none");
		
		if (strInterpolation.equals("bilinear"))
			oInterpolation = RenderingHints.VALUE_INTERPOLATION_BILINEAR;

		if (strInterpolation.equals("bicubic"))
			oInterpolation = RenderingHints.VALUE_INTERPOLATION_BICUBIC;

		double dfScaleInterpolation = oIni.DoubleGet("code", "interpolation_scale", 0);
		
		Code oCode = new Code(iWidth, iHeight, iBorder, dfScale, iRotation, oInterpolation, dfScaleInterpolation, false, oColors);
		//                                       123456789012345678901234567890123456789012345678901234567890		
		oCode.write(strImagePath + "code1.png", "if (x - 1 >= 0 && y - 1 >= 0) x = 0x10; y = 0x10; return((x << 16) + y);", 1);
		oCode.write(strImagePath + "code2.png", "if (y - 1 >= 0) y = 0x10; return(y);", 2);
		oCode.write(strImagePath + "code3.png", "if (x + 1 < w && y - 1 >= 0) x = 0x01; y = 0x10; return((x << 16) + y);", 3);
		oCode.write(strImagePath + "code4.png", "if (x + 1 < w) x = 0x01; return(x << 16);", 4);
		oCode.write(strImagePath + "code5.png", "if (x + 1 < w && y + 1 < h) x = 0x01; y = 0x01; return((x << 16) + y);", 5);
		oCode.write(strImagePath + "code6.png", "if (y + 1 < h) y = 0x01; return(y);", 6);
		oCode.write(strImagePath + "code7.png", "if (x - 1 >= 0 && y + 1 < h) x = 0x10; y = 0x01; return((x << 16) + y);", 7);
		oCode.write(strImagePath + "code8.png", "if (x - 1 >= 0) x = 0x10; return(x << 16);", 8);
	}
	
	private static Color readColor(String strColor)
	{
		int iRed = readChannel(strColor.substring(0, 2));
		int iGreen = readChannel(strColor.substring(2, 4));
		int iBlue = readChannel(strColor.substring(4, 6));
		
		return(new Color(iRed, iGreen, iBlue));
	}
	
	private static String randomCode(int iLength)
	{
		StringBuffer strCode = new StringBuffer();
		
		for (int iIndex = 0; iIndex < iLength; iIndex++)
		{
			strCode.append((char)((new Random()).nextInt(95) + 32));
		}
		
		return(strCode.toString());
	}
	
	private static int readChannel(String strChannel)
	{
		strChannel = strChannel.toUpperCase();
		String strHex = "0123456789ABCDEF";
		
		int iChannel = 0;
		
		for (int iIndex = 0; iIndex < 16; iIndex++)
			if (strHex.charAt(iIndex) == strChannel.charAt(0))
				iChannel += 16 * iIndex;

		for (int iIndex = 0; iIndex < 16; iIndex++)
			if (strHex.charAt(iIndex) == strChannel.charAt(1))
				iChannel += iIndex;
			
		return(iChannel);
	}
}