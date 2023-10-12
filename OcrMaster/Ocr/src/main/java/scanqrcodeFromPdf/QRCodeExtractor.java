package scanqrcodeFromPdf;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.json.JSONObject;
import com.google.gson.Gson;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

public class QRCodeExtractor {

	public static void main(String[] args) {
		try {

			File sourceFile = new File("C:\\Users\\admn\\Downloads\\Invoice_sample (2)[68].pdf");
			InputStream fileInputstream = new FileInputStream(sourceFile);
			byte[] arr = Files.readAllBytes(sourceFile.toPath());
			File imageFile = new File("C:\\Users\\admn\\Downloads\\myqrcode.png");// myqrcode.png //Raj.JPG
			byte[] img = FileUtils.readFileToByteArray(imageFile);
			RequestPayload requestPayload = new RequestPayload();
			requestPayload.setFileExtensionType(Enums.FileExtensionType.PDF);
			requestPayload.setScanMode(Enums.ScanMode.QRCODE);
			requestPayload.setDocType(Enums.DocType.E_INVOICE);
			requestPayload.setScandata(Enums.ScanData.DECODED_DATA);
			requestPayload.setInputType(Enums.InputType.BYTEARRAY);
			requestPayload.setValidateSign(true);
			requestPayload.setInputStream(fileInputstream);
		    requestPayload.setByteArray(arr);
			//requestPayload.setByteArray(img);
			
             DecodedData decodedData = new DecodedData();
			decodedData.setPublicKey(
					"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA35ECb3t4DSCq3Wo4LONh3/jF+1k/S0MEareXFww+mm4AgymHheAWqhIGFQV6fbw2t/ZnG4TULXgYwBUCUffj+bqYAsvo2QyF9hbieGY2SDSieToUmyh8gtJZn0MG04d0NgFjunmnM/7ROkEAOXFvNDizO1NiKiOPbuLIOCrDvQdu48HNUR8Yg0pcMaOnBGVwasv4UIfMZXbBzjwngbwzK8M9jhp4y6xqsmF+wmFZhVpRhIa6nRDkedgroU5IlR/ntqDAhzk/p7zu2btAzA47HltfsGratEqFIBsj/ug+YFZh+8QOzbp2fizOo6DUdbYyJL7lxtEOdOI7ibKccnvlnQIDAQAB");
			requestPayload.setDecodedData(decodedData);

			// QR Code Response DATA
			ResponseData responseData = inputDataFromUser(requestPayload);
			Gson gson = new Gson();
			String json = gson.toJson(responseData);
			System.out.println("Response Data => \n" + json);
			} 
			catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static String decodeQRCode(BufferedImage image) {
		try {

			LuminanceSource source = new BufferedImageLuminanceSource(image);
			BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
			Result result = new MultiFormatReader().decode(bitmap);
			return result.getText();

		} catch (NotFoundException e) {
			// QR code not found in the image
			e.printStackTrace();
			return null;
		}

	}

	private static ResponseData inputDataFromUser(RequestPayload reqpayload) {

		ResponseData responseData = new ResponseData();

		if (Enums.ScanMode.QRCODE.equals(reqpayload.getScanMode())) {

			responseData = scanData(reqpayload, reqpayload.isValidateSign());
		}

		else {
			// Barcode Case
			responseData.setStatus(1);
			responseData.setMessage("Barcode validation is not imlplemented yet!");
		}
		return responseData;
	}

	private static ResponseData scanData(RequestPayload requestPayload, boolean signValidate) {
		ResponseData responseData = new ResponseData();
		if (Enums.FileExtensionType.PNG.equals(requestPayload.getFileExtensionType())
				|| (Enums.FileExtensionType.JPEG.equals(requestPayload.getFileExtensionType()))) {

			if (Enums.InputType.BYTEARRAY.equals(requestPayload.getInputType())) {
				try {
					BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(requestPayload.getByteArray()));
					String imgQrData = decodeQRCode(bufferedImage);
					System.out.println("Image QR Data  -->" + imgQrData);
                    if (imgQrData == null) {
						responseData.setMessage("Not A valid QR Code");
						responseData.setStatus(1);
						return responseData;
					}

					ResObj obj = new ResObj();
					List<ResObj> objs = new ArrayList<>();
					// Decode QR code from the image
					boolean isJwtString = JwtDataUtilityClass.isJWTString(imgQrData);
					obj.setQRContainsJWT(isJwtString);
					String rawData = imgQrData;
					obj.setRawData(rawData);
					objs.add(obj);
					responseData.setData(objs);
					responseData.setStatus(0);
					responseData.setMessage(" Png Byte array extracted data successfully!!");
					return responseData;
				} catch (Exception e) {
					e.printStackTrace();
					responseData.setMessage(e.getMessage());
					responseData.setStatus(1);
				}
				return responseData;
			}
		}
		if (!Enums.FileExtensionType.PDF.equals(requestPayload.getFileExtensionType())) {
			responseData.setStatus(1);
			responseData.setMessage("Provided file extension was not supported!!");
			return responseData;
		}

		if (Enums.InputType.INPUTSTRAM.equals(requestPayload.getInputType())
				&& Enums.FileExtensionType.PDF.equals(requestPayload.getFileExtensionType())) {
			try {
				InputStream fileInputstream = requestPayload.getInputStream();
				byte[] byteArray = fileInputstream.readAllBytes();
				responseData = getDataFromPDFFile(byteArray, signValidate, requestPayload.getDecodedData());
				responseData.setMessage(" PDF FILE Inputstream Data extracted Successfully!!");
				return responseData;
			} catch (Exception e) {
				e.printStackTrace();
				responseData.setStatus(1);
				responseData.setMessage(e.getMessage());
				return responseData;
			}
		} else if (Enums.InputType.BYTEARRAY.equals(requestPayload.getInputType())) {
			return getDataFromPDFFile(requestPayload.getByteArray(), signValidate, requestPayload.getDecodedData());
		}

		return null;
	}

	static ResponseData getDataFromPDFFile(byte[] data, boolean isValidate, DecodedData decodedData) {
		ResponseData responseData = new ResponseData();
	    List<ResObj> resObjs = new ArrayList<ResObj>();
	    try {
			PDDocument document = Loader.loadPDF(data);
			PDFRenderer pdfRenderer = new PDFRenderer(document);
			// Loop through each page in the PDF
			for (int page = 0; page < document.getNumberOfPages(); ++page) {
				// Render the page as an image
				ResObj resObj = new ResObj();
				BufferedImage image = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
				// Decode QR code from the image
				String qrCodeData = decodeQRCode(image);
				if (qrCodeData == null) {
                   responseData.setMessage("Not A valid QR Code");
					responseData.setStatus(1);
					return responseData;
				}
				resObj.setRawData(qrCodeData);
				System.out.println("QR  code EXtracted data From Pdf File : " + qrCodeData);
				boolean isJwtString = JwtDataUtilityClass.isJWTString(qrCodeData);
				resObj.setQRContainsJWT(isJwtString);
				if (isJwtString && isValidate) {
					String[] qrData = qrCodeData.split("\\.");
					byte[] mydata = Base64.getDecoder().decode(qrData[1]);
					String content = new String(mydata, "UTF-8");
					JSONObject jsonObject = new JSONObject(content);
					Gson gson = new Gson();
					DecodeResponseData decodeResponseData = gson.fromJson(jsonObject.getString("data"),
							DecodeResponseData.class);
					resObj.setDecodeResponseData(decodeResponseData);
					RSAPublicKey key = JwtDataUtilityClass.generateKey(decodedData.getPublicKey());
					resObj.setEsignValid(JwtDataUtilityClass.validateToken(key, qrCodeData));

				}
				resObjs.add(resObj);
			}
			// Close the PDF document
			document.close();
			responseData.setData(resObjs);
			responseData.setStatus(0);
			responseData.setMessage("Byte array extracted data successfully!!");
			return responseData;
		} catch (Exception e) {
			e.printStackTrace();
			responseData.setMessage(e.getMessage());
			responseData.setStatus(1);
		}
		return responseData;
	}

}
