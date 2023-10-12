package scanqrcodeFromPdf;

public class ResObj {

	private String rawData;
	private String decodedData;
	private boolean esignValid;
	private DecodeResponseData decodeResponseData;
	private boolean isQRContainsJWT;

	public boolean isQRContainsJWT() {
		return isQRContainsJWT;
	}

	public void setQRContainsJWT(boolean isQRContainsJWT) {
		this.isQRContainsJWT = isQRContainsJWT;
	}

	public DecodeResponseData getDecodeResponseData() {
		return decodeResponseData;
	}

	public void setDecodeResponseData(DecodeResponseData decodeResponseData) {
		this.decodeResponseData = decodeResponseData;
	}

	public String getRawData() {
		return rawData;
	}

	public void setRawData(String rawData) {
		this.rawData = rawData;
	}

	public String getDecodedData() {
		return decodedData;
	}

	public void setDecodedData(String decodedData) {
		this.decodedData = decodedData;
	}

	public boolean isEsignValid() {
		return esignValid;
	}

	public void setEsignValid(boolean esignValid) {
		this.esignValid = esignValid;
	}

}
