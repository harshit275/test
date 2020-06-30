package User;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream; 
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;

public class AESencryptor 
{
	
 public void encrypt(String fname,Key key) throws Exception
 {
   Cipher aesCipher = Cipher.getInstance("AES");  //getting cipher for AES
  aesCipher.init(Cipher.ENCRYPT_MODE, key);  //initializing cipher for encryption with key
   
  //creating file output stream to write to file
  try(FileOutputStream fos = new FileOutputStream(fname+".aes")){
   //creating object output stream to write objects to file
   ObjectOutputStream oos = new ObjectOutputStream(fos);
   oos.writeObject(key);
   //creating file input stream to read contents for encryption
   try(FileInputStream fis = new FileInputStream(fname)){
    //creating cipher output stream to write encrypted contents
    try(CipherOutputStream cos = new CipherOutputStream(fos, aesCipher)){
     int read;
     byte buf[] = new byte[4096];
     while((read = fis.read(buf)) != -1)  //reading from file
      cos.write(buf, 0, read);  //encrypting and writing to file
    }
   }
  }
   
 }
  
}