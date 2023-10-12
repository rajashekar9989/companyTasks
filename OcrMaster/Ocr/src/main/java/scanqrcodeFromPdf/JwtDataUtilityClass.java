package scanqrcodeFromPdf;

import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.json.JSONObject;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JwtDataUtilityClass {
	
	
	public static boolean isJwtString;

	static RSAPublicKey generateKey(String keyData) {
		byte[] decoded = Base64.getDecoder().decode(keyData);
		X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
		KeyFactory kf;
		try {
			kf = KeyFactory.getInstance("RSA");
			RSAPublicKey generatePublic = (RSAPublicKey) kf.generatePublic(spec);
			return generatePublic;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	

	static boolean validateToken(RSAPublicKey publicKey, String jwtToken) {
		try {
			Claims claims = Jwts.parserBuilder().setSigningKey(publicKey) // Use the RSA public key you obtained earlier
					.build().parseClaimsJws(jwtToken).getBody();

			// You can access the claims in the JWT token here

			System.out.println("JWT claims: " + claims);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	static boolean isJWTString(String key) {
		String[] jswSplitted = key.split("\\.");
		if (jswSplitted.length != 3) {
			return false;
		}
		try {
			String jsonFirstString = new String(Base64.getDecoder().decode(jswSplitted[0]));
			JSONObject firstPart = new JSONObject(jsonFirstString);
			if (!firstPart.has("alg")) {
				return false;
			}
			// validate if required parameters check if any on second part
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
