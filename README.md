# Cryptography Project

<img src="Cryptography Project\\src\\assets\\gui.png" alt="Image of GUI" width="400px" aling="center">

### Description
This project can **encrypt** and **decrypt** message and file (with extension: .txt).

It uses **two different methods** of encryptio / decryption witch are **RSA** and **AES**.

>#### RSA
>RSA (Rivest-Shamir-Adleman) algorithm is an **asymmetric cryptography algorithm**. Asymmetric means that it **works on two different keys**. _Public Key_ and _Private Key_. As the name describes the Public Key is given to everyone and the Private key is kept private.
>
>An example of asymmetric cryptography: 
>
>1. A client (for example browser) sends its public key to the server and requests some data.
>2. The server encrypts the data using the client’s public key and sends the encrypted data.
>3. The client receives this data and decrypts it.
>
>Since this is asymmetric, nobody else except the browser can decrypt the data even if a third party has the public key of the browser.

>#### AES
>The Advanced Encryption Standard (AES) is a **symmetric block cipher** chosen by the U.S. government to protect classified information.
>
>Today, AES is one of the most popular symmetric key cryptography algorithms for a wide range of encryption applications for both government and commercial use.
>
>- Electronic communication apps.
>- Programming libraries.
>- Internet browsers.
>- File and disk compression.
>- Wireless networks.
>- Databases.

## Installation
There are several methods to install it.

1. Go to the URL of the page. Change **github.com** with **github.dev**. This way you will use **VS Code web**.

2. Download the project and the **Java compiler** from browser. You install a **Java IDE**(programming environment) and run it.<br>
Example of Java IDE:
    * [Eclipse](https://www.eclipse.org/eclipseide/)
    * [IntelliJ](https://www.jetbrains.com/idea/)
    * [NetBeans](https://netbeans.apache.org/)
    * [Visual Studio Code](https://code.visualstudio.com/)

## Usage
How to use this project correctly.

This project provides the encryption and decryption of messages or text files.<br>
It has two encryption methods (RSA, AES) with their respective keys.

To make it work correctly you need to decide whether you want to **encrypt** or **decrypt** button from the gui. As in the figure.

<img src="Cryptography Project\\src\\assets\\encrypt-decrypt.png" alt="Chose Encryption or Decryption" width="200em">

Then decide on the **encryption algorithm.**
If you have chosen AES you must enter the **Secret Key** and **IV**, whereas if you have chosen RSA write the **Public Key to encrypt** or the **Private Key to decrypt.**

<div>
    <img src="Cryptography Project\\src\\assets\\encryption gui.png" alt="Encryption GUI" width="500em">
    <img src="Cryptography Project\\src\\assets\\decryption gui.png" alt="Encryption GUI" width="500em">
</div>

You can decide between encrypting:
* Message to another message
* Message with creation of an encrypted file
* File with creation of an encrypted file

You have two methods for importing a text file.
1. With the first one click on **"Open File"** and choose the file, which will be **loaded on the gui**.
2. With the second click on **"File Path"** and choose a file whose **path will be stored**.
it is advisable for the method "File with creation of an encrypted file" and with **long messages**.

You can also **print Keys** on a file.

---

### WARNING
If you **choose the RSA** method you will only be able to encrypt **messages shorter** than **117 characters.**

---

## Credits
This project was **created by me.**<br>
I took inspiration from videos on YouTube to implement the encryption.

For RSA I used a tutorial by [WhiteBatCodes](https://youtu.be/R9eerqP78PE?si=1epeC5rTr1-xWqvA)<br>
For AES I used a tutorial by [WhiteBatCodes](https://youtube.com/playlist?list=PLtgomJ95NvbPDMQClkBZPijLdEFyo0VHa&si=LJBLCXXmUtNLXQ4X)

#### To see my projects then visit my profile: [tommy-210](https://github.com/tommy-210).
