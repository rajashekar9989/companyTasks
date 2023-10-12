package scanqrcodeFromPdf;

public class Enums {

	enum InputType {
		INPUTSTRAM, BYTEARRAY
	}

	enum ScanMode {
		QRCODE, Barcode
	}

	enum DocType {
		E_INVOICE, GRN,
	}

	enum ScanData {
		RAW, DECODED_DATA
	}

	enum FileExtensionType {
		PDF, JPEG, PNG
	}

}
