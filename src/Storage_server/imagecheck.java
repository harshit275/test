package Storage_server;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class imagecheck 
{
	public static double p,pr;
	public static Boolean compareTwoImages(File fileOne, File filetwo) 
	{
		Boolean isTrue = true;
		try
		{
			System.out.println("File one="+fileOne);
			System.out.println("File two="+filetwo);
			BufferedImage bufImgOne = ImageIO.read(fileOne);
			BufferedImage bufImgTwo=ImageIO.read(filetwo);
			int imgOneHt = bufImgOne.getHeight();
			int imgTwoHt = bufImgTwo.getHeight();
			int imgOneWt = bufImgOne.getWidth();
			int imgTwoWt = bufImgTwo.getWidth();
			
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
			p=(100*i)/(imght*imhwt);
			
			pr=(100*j)/(imght*imhwt);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return isTrue;
	}
}
