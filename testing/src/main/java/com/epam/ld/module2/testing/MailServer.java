package com.epam.ld.module2.testing;

import lombok.NoArgsConstructor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Mail server class.
 */
@NoArgsConstructor
public class MailServer {

    /**
     * Send notification.
     *
     * @param addresses      the addresses
     * @param messageContent the message content
     */
    public void send(String addresses, String messageContent) {
        File file = new File("src/main/resources/file.txt");
        if (file.exists()) {
            try {
                BufferedWriter bw = Files.newBufferedWriter(Path.of("src/main/resources/file.txt"));
                String result = spamText(addresses, messageContent);
                bw.write(result);
            } catch (IOException e) {
                System.out.println("IO File Error Occurred");
            }
        } else
            System.out.println(spamText(addresses, messageContent));
    }

    private String spamText(String addresses, String messageContent) {
        return "MessageContent: " + messageContent + "\nAddresses: " + addresses;
    }
}
