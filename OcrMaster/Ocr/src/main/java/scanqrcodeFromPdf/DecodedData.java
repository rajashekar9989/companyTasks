package scanqrcodeFromPdf;

public class DecodedData {
	
	private String returnFields;
	
	private String publicKey;

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getReturnFields() {
		return returnFields;
	}

	public void setReturnFields(String returnFields) {
		this.returnFields = returnFields;
	}
}

