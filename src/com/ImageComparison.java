package com;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class ImageComparison
{
	public Boolean compareTwoImages(File fileOne, BufferedImage bufImgTwo) 
	{
		Boolean isTrue = true;
		try
		{
			System.out.println(fileOne.getAbsolutePath());
			BufferedImage bufImgOne = ImageIO.read(fileOne);
			int imgOneHt = bufImgOne.getHeight();
			int imgTwoHt = bufImgTwo.getHeight();
			int imgOneWt = bufImgOne.getWidth();
			int imgTwoWt = bufImgTwo.getWidth();
			
			System.out.println("imgOneHt:"+imgOneHt +"    "+"imgTwoHt:"+imgTwoHt);
			System.out.println("imgOneWt:"+imgOneWt +"    "+"imgTwoWt:"+imgTwoWt);
			int imght=Math.min(imgOneHt, imgTwoHt);
			int imhwt=Math.min(imgOneWt, imgTwoWt);
			
			int i=0,j=0;
			for(int x =0; x < imhwt ; x++ )
			{
				for(int y =0; y < imght ; y++)
				{
					if(bufImgOne.getRGB(x, y) != bufImgTwo.getRGB(x, y) )
					{
						isTrue = false;
						j++;
					}
					else
					{
						isTrue=true;
						i++;
					}
					
				}
			}
			double p=(100*i)/(imght*imhwt);
			System.out.println("match="+p);
			
			double pr=(100*j)/(imght*imhwt);
			System.out.println("not match="+pr);
			
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		System.out.println("Image match isTrue"+isTrue);
		return isTrue;
	}


	public static void main(String[] softwareEngineer) throws IOException 
	{
		File OracleJava = new File("C:\\Users\\user\\Desktop\\Images\\sign_up_button.png");
		File javaOracle = new File("C:\\Users\\user\\Desktop\\Images\\SignUp-Logo.gif");
		BufferedImage bi=ImageIO.read(javaOracle);
		ImageComparison imgComp = new ImageComparison();
		boolean cc=imgComp.compareTwoImages(OracleJava, bi);
		System.out.println("cc:"+cc);
	}
}

