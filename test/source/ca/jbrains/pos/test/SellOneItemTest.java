package ca.jbrains.pos.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SellOneItemTest {
	public static class Display {
		private String text;

		public String getText() {
			return text;
		}

		public void displayPrice(final String priceAsText) {
			this.text = priceAsText;
		}

		public void displayProductNotFoundMessage(String barcode) {
			this.text = "No product found for " + barcode;
		}

		public void displayEmptyBarcodeMessage() {
			this.text = "Scanning error: empty barcode";
		}
	}

	public static class Sale {
		private final Display display;
		private final Catalog catalog;

		public Sale(Catalog catalog, Display display) {
			this.catalog = catalog;
			this.display = display;
		}

		public void onBarcode(String barcode) {
			if ("".equals(barcode)) {
				display.displayEmptyBarcodeMessage();
				return;
			}

			if (catalog.knowsBarcode(barcode))
				display.displayPrice(catalog.findPrice(barcode));
			else
				display.displayProductNotFoundMessage(barcode);
		}
	}

	@Test
	public void knownProduct() throws Exception {
		Display display = new Display();
		Sale sale = new Sale(new Catalog(new HashMap<String, String>() {
			{
				put("12345", "EUR 12,00");
				put("23456", "EUR 4,95");
			}
		}), display);

		sale.onBarcode("12345");

		assertEquals("EUR 12,00", display.getText());
	}

	@Test
	public void anotherKnownProduct() throws Exception {
		Display display = new Display();
		Sale sale = new Sale(new Catalog(new HashMap<String, String>() {
			{
				put("12345", "EUR 12,00");
				put("23456", "EUR 4,95");
			}
		}), display);

		sale.onBarcode("23456");

		assertEquals("EUR 4,95", display.getText());
	}

	@Test
	public void unknownProduct() throws Exception {
		Display display = new Display();
		Sale sale = new Sale(new Catalog(new HashMap<String, String>() {
			{
				put("12345", "EUR 12,00");
				put("23456", "EUR 4,95");
			}
		}), display);

		sale.onBarcode("99999");

		assertEquals("No product found for 99999", display.getText());
	}

	@Test
	public void emptyBarcode() throws Exception {
		Display display = new Display();
		Sale sale = new Sale(new Catalog(new HashMap<String, String>() {
			{
				put("12345", "EUR 12,00");
				put("23456", "EUR 4,95");
			}
		}), display);

		sale.onBarcode("");

		assertEquals("Scanning error: empty barcode", display.getText());
	}
}
