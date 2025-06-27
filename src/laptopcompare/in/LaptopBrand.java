package laptopcompare.in;

class LaptopBrand {
    private String name;
    private LaptopSpecification[] specifications;

    /**
     * Constructor for LaptopBrand.
     * @param name The name of the laptop brand (e.g., Dell, Lenovo).
     * @param specifications An array of LaptopSpecification objects for this brand.
     */
    public LaptopBrand(String name, LaptopSpecification[] specifications) {
        this.name = name;
        this.specifications = specifications;
    }

    // Getters for laptop brand properties
    public String getName() {
        return name;
    }

    public LaptopSpecification[] getSpecifications() {
        return specifications;
    }
}
