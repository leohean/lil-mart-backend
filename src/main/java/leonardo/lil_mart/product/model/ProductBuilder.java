package leonardo.lil_mart.product.model;

import leonardo.lil_mart.market.model.Market;

import java.time.LocalDateTime;

public class ProductBuilder {
    private Product product;

    private ProductBuilder() {
        product = new Product();
    }

    public static ProductBuilder builder(){
        return new ProductBuilder();
    }

    public ProductBuilder name(String name){
        this.product.setName(name);
        return this;
    }

    public ProductBuilder category(String category){
        this.product.setCategory(category);
        return this;
    }

    public ProductBuilder description(String description){
        this.product.setDescription(description);
        return this;
    }

    public ProductBuilder price(Double price){
        this.product.setPrice(price);
        return this;
    }

    public ProductBuilder unitMeasurement(String unitMeasurement){
        this.product.setUnitMeasurement(unitMeasurement);
        return this;
    }

    public ProductBuilder stockQuantity(Double stockQuantity){
        this.product.setStockQuantity(stockQuantity);
        return this;
    }

    public ProductBuilder isActive(Boolean isActive){
        this.product.setIsActive(isActive);
        return this;
    }

    public ProductBuilder image(String image){
        this.product.setImage(image);
        return this;
    }

    public ProductBuilder market(Market market){
        this.product.setMarket(market);
        return this;
    }

    public ProductBuilder createdAt(LocalDateTime createdAt){
        this.product.setCreatedAt(createdAt);
        return this;
    }

    public ProductBuilder lastUpdateAt(LocalDateTime lastUpdateAt){
        this.product.setLastUpdateAt(lastUpdateAt);
        return this;
    }

    public Product build(){
        return product;
    }
}
