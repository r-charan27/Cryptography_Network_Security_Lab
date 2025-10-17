import java.util.*;
class ceaserCipher{
  public static void main(String args[]){
  Scanner sc= new Scanner(System.in);
  System.out.println("Enter String to Encrypt:");
  String word=sc.nextLine();
  System.out.print("Enter K:");
  int k=sc.nextInt();
  String encryptedWord="";
  for(int i=0;i<word.length();i++){
  int r=(int)(word.charAt(i))-97;
  r=(r+k)%26;
  encryptedWord+=(char)(r+97);
  }
  System.out.println("Encrypted Text:"+encryptedWord);
  String decryptedWord="";
  for(int i=0;i<encryptedWord.length();i++){
    int r=(int)(encryptedWord.charAt(i))-97;
    r=(r-k)>=0?(r-k):26+(r-k);
    decryptedWord+=(char)(r+97);
  }
  System.out.println("Decrypted Text:"  +decryptedWord);

  }}

