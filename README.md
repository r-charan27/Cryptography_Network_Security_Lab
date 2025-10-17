## CNS_Using_Java
Cryptography and Network Security

CNS Algorithms in Java
This project implements various cryptographic algorithms using Java, showcasing encryption, key exchange, and analysis tools. The implementations are purely educational and aim to help understand how these algorithms work under the hood.

## 📁 List of Implemented Algorithms
No. Algorithm Name Description 1 Diffie-Hellman (DH) A key exchange protocol that allows two parties to securely share a secret key over an insecure channel. 2 Blowfish Encryption A symmetric key block cipher known for its speed and effectiveness. 3 Secret Constant Finder A utility to analyze or generate cryptographic constants used in algorithms (custom logic). 4 RSA Encryption An asymmetric cryptographic algorithm widely used for secure data transmission. 5 RC4 Encryption A stream cipher known for simplicity but now considered insecure (used here for educational purposes only). 6 DHA (Diffie-Hellman Alternative) Another variation or demonstration of Diffie-Hellman algorithm (if implemented differently).

## 🛠️ Requirements
Java JDK 8 or above

Text editor or IDE (like VSCode, IntelliJ)

Terminal or Command Prompt

## 🚀 How to Run
Clone the repository or copy the files locally.

Compile the Java files:

javac .java

Run the Java programs:

java

Example:
javac RSAEncryption.java java RSAEncryption

## 📂 File Structure
CNS-Java-Algorithms/ │ ├── DiffieHellmanKeyExchange.java ├── BlowfishEncryption.java ├── SecretConstantFinder.java ├── RSAEncryption.java ├── RC4Encryption.java ├── DHA.java ├── README.md

## ⚠️ Disclaimer
This project is for educational purposes only. Algorithms like RC4 are outdated and insecure. Do not use these implementations in production systems without proper review and cryptographic standards.
