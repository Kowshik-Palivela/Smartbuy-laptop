package laptopcompare.in;

//Represents the specifications and prices of a single laptop model across platforms
class LaptopSpecification {
 private String specification;
 private double priceAmazon;
 private double priceFlipkart;
 private double priceTataNeu;

 /**
  * Constructor for LaptopSpecification.
  * @param specification A descriptive string of the laptop's specs.
  * @param priceAmazon Price on Amazon.
  * @param priceFlipkart Price on Flipkart.
  * @param priceTataNeu Price on Tata Neu.
  */
 public LaptopSpecification(String specification, double priceAmazon, double priceFlipkart, double priceTataNeu) {
     this.specification = specification;
     this.priceAmazon = priceAmazon;
     this.priceFlipkart = priceFlipkart;
     this.priceTataNeu = priceTataNeu;
 }

 // Getters for laptop specification properties
 public String getSpecification() {
     return specification;
 }

 public double getPriceAmazon() {
     return priceAmazon;
 }

 public double getPriceFlipkart() {
     return priceFlipkart;
 }

 public double getPriceTataNeu() {
     return priceTataNeu;
 }

 /**
  * Calculates the cheapest price among the three platforms.
  * @return The lowest price.
  */
 public double getCheapestPrice() {
     return Math.min(priceAmazon, Math.min(priceFlipkart, priceTataNeu));
 }

 /**
  * Determines which platform offers the cheapest price.
  * @return The name of the platform with the cheapest price.
  */
 public String getCheapestPlatform() {
     double cheapest = getCheapestPrice();
     if (cheapest == priceAmazon) {
         return "Amazon";
     } else if (cheapest == priceFlipkart) {
         return "Flipkart";
     } else {
         return "Tata Neu";
     }
 }
}