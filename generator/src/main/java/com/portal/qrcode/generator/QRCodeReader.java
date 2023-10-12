package com.portal.qrcode.generator;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class QRCodeReader {

    public static void main(String[] args) {
        String qrCodeImagePath = "path_to_qr_code.png"; // Replace with the path to your QR code image

        try {
            BufferedImage image = ImageIO.read(new File(qrCodeImagePath));

            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));

            MultiFormatReader reader = new MultiFormatReader();
            Result result = reader.decode(binaryBitmap);

            // Convert the result to plain text
            String plainText = result.getText();
            System.out.println("QR Code Content (Plain Text):");
            System.out.println(plainText);

            // Optionally, convert the result to a JSON format
            String jsonResult = "{\"qr_code_content\": \"" + plainText + "\"}";
            System.out.println("QR Code Content (JSON):");
            System.out.println(jsonResult);

        } catch (IOException | NotFoundException e) {
            e.printStackTrace();
        }
    }
}

