package dev.arhimedes.global.service.contract;

public interface EncryptionService {

    String encrypt(String text);

    String decrypt(String text);
}
