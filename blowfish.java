import java.util.*;
import java.security.GeneralSecurityException;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

public class blowfish {
  public static void main(String args[]) throws GeneralSecurityException {
    Scanner sc = new Scanner(System.in);

    System.out.print("Enter the message to encrpt:");
    String msg = sc.nextLine();
    byte[] message = msg.getBytes();

    System.out.print("Enter custom key:");
    String key = sc.nextLine();
    byte[]keyData = key.getBytes();

    SecretKeySpec SecretKey = new SecretKeySpec(keyData, "BlowFish");
    Cipher cipher = Cipher.getInstance("BlowFish");
    cipher.init(Cipher.ENCRYPT_MODE,SecretKey);
    byte[] encrypted = cipher.doFinal(message);

    cipher.init(Cipher.DECRYPT_MODE,SecretKey);
    byte[] decrypted = cipher.doFinal(encrypted);
    String decryptedMsg = new String(decrypted);

      System.out.print("Message   :"+msg);
      System.out.print("Encrypted :"+encrypted);
      System.out.print("Decrypted :"+decryptedMsg);
    }
}

