package ca.jbrains.pos.test;

import java.util.Map;



public class Catalog {
	public Map<String, String> pricesByBarcode;

	public Catalog(Map<String, String> pricesByBarcode) {
		this.pricesByBarcode = pricesByBarcode;
	}

	public boolean knowsBarcode(String barcode) {
		return pricesByBarcode.containsKey(barcode);
	}

	public String findPrice(String barcode) {
		return pricesByBarcode.get(barcode);
	}
}