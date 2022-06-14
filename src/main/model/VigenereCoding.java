package main.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class VigenereCoding implements Coding {
    
    private String crypt;
    public void setCrypt(String crypt) {
        this.crypt = crypt;
    }

    public VigenereCoding(String crypt) {
        this.crypt = crypt;
    }
    
    public String codeString(String string) {

        StringBuilder codedString = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) > 32 && string.charAt(i) < 127) {
                codedString.append(codeEngSym(string.charAt(i), crypt.charAt(i % crypt.length())));
            }
            else codedString.append(string.charAt(i));
        }
        return codedString.toString();
    }
    
    public String encodeString(String string) {
        StringBuilder encodedString = new StringBuilder();
        for(int i = 0; i < string.length(); i++){
            if (string.charAt(i) > 32 && string.charAt(i) < 127)
                encodedString.append(unCodeEngSym(string.charAt(i), crypt.charAt(i % crypt.length())));
            else encodedString.append(string.charAt(i));
        }
        return encodedString.toString();
    }

    private char codeEngSym(char p, char k) {
        return (char) ((p - 33 + k - 33) % 94 + 33);
    }

    private char unCodeEngSym(char p, char k) {
        return (char) ((p - k + 94) % 94 + 33);
    }
}
