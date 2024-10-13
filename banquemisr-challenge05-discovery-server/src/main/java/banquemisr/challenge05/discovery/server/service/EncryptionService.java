package banquemisr.challenge05.discovery.server.service;

import lombok.RequiredArgsConstructor;
import org.jasypt.util.text.AES256TextEncryptor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EncryptionService {

    private final AES256TextEncryptor encryptor;

    private static final String PASSWORD = "Bangue#Misr#Challenge#05";

    public EncryptionService() {
        this.encryptor = new AES256TextEncryptor();
        encryptor.setPassword(PASSWORD); // Set the password here
    }

    public String encrypt(String decryptedData) {
        return encryptor.encrypt(decryptedData);
    }

    public String decrypt(String encryptedData) {
        return encryptor.decrypt(encryptedData);
    }
}
