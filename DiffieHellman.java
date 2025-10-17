import java.math.BigInteger;
import java.security.SecureRandom;

public class DiffieHellman {

    public static void main(String[] args) {
        SecureRandom random = new SecureRandom();

        BigInteger p = BigInteger.probablePrime(64, random); 
        BigInteger g = new BigInteger("5"); 

        System.out.println("Publicly Shared Prime (p): " + p);
        System.out.println("Publicly Shared Base (g): " + g);

        BigInteger a = new BigInteger(64, random);
        BigInteger A = g.modPow(a, p);
        System.out.println("\nAlice's Private Key (a): " + a);
        System.out.println("Alice's Public Key (A): " + A);

        BigInteger b = new BigInteger(64, random);
        BigInteger B = g.modPow(b, p); 
        System.out.println("\nBob's Private Key (b): " + b);
        System.out.println("Bob's Public Key (B): " + B);

        BigInteger sharedSecretAlice = B.modPow(a, p);

        BigInteger sharedSecretBob = A.modPow(b, p);

        System.out.println("\nShared Secret computed by Alice: " + sharedSecretAlice);
        System.out.println("Shared Secret computed by Bob:   " + sharedSecretBob);

        if (sharedSecretAlice.equals(sharedSecretBob)) {
            System.out.println("\n Key Exchange Successful! Shared secret is: " + sharedSecretAlice);
        } else {
            System.out.println("\n Key Exchange Failed.");
        }
    }
}
