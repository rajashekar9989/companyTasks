package scanqrcodeFromPdf;

import java.io.InputStream;

import scanqrcodeFromPdf.Enums.DocType;
import scanqrcodeFromPdf.Enums.FileExtensionType;
import scanqrcodeFromPdf.Enums.InputType;
import scanqrcodeFromPdf.Enums.ScanData;
import scanqrcodeFromPdf.Enums.ScanMode;

public class RequestPayload {

	private FileExtensionType fileExtensionType;
	private ScanMode scanMode;
	private DocType docType;
	private ScanData scanData;
	private InputType inputType;
	private boolean validateSign;
	private byte[] byteArray;
	private InputStream inputStream;
	private DecodedData decodedData;

	public ScanMode getScanMode() {
		return scanMode;
	}

	public void setScanMode(ScanMode scanMode) {
		this.scanMode = scanMode;
	}

	public DocType getDocType() {
		return docType;
	}

	public void setDocType(DocType docType) {
		this.docType = docType;
	}

	public ScanData getScandata() {
		return scanData;
	}

	public void setScandata(ScanData scandata) {
		this.scanData = scandata;
	}

	public InputType getInputType() {
		return inputType;
	}

	public void setInputType(InputType inputType) {
		this.inputType = inputType;
	}

		public DecodedData getDecodedData() {
		return decodedData;
	}

		
	public boolean isValidateSign() {
			return validateSign;
		}

		public void setValidateSign(boolean validateSign) {
			this.validateSign = validateSign;
		}

	public void setDecodedData(DecodedData decodedData) {
		this.decodedData = decodedData;
	}

	public ScanData getScanData() {
		return scanData;
	}

	public void setScanData(ScanData scanData) {
		this.scanData = scanData;
	}

	public byte[] getByteArray() {
		return byteArray;
	}

	public void setByteArray(byte[] byteArray) {
		this.byteArray = byteArray;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream fileInputstream) {
		this.inputStream = fileInputstream;
	}

	public FileExtensionType getFileExtensionType() {
		return fileExtensionType;
	}

	public void setFileExtensionType(FileExtensionType fileExtensionType) {
		this.fileExtensionType = fileExtensionType;
	}

}
