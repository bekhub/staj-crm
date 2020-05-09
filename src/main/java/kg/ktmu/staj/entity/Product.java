package kg.ktmu.staj.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Product extends AbstractEntity {

    @NotNull
    @NotEmpty
    private String title;

    @ManyToOne(targetEntity = Brand.class)
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    private Brand brand;

    @ManyToOne(targetEntity = Category.class)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @ManyToOne(targetEntity = Photo.class, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "photo_id", referencedColumnName = "id")
    private Photo photo;

    @ManyToOne(targetEntity = Measurement.class)
    @JoinColumn(name = "measurement_id", referencedColumnName = "id")
    private Measurement measurementType;

    private Integer quantity;

    private Integer quantityInStock;

    private Float addedValue;

    private Float grossWeight;

    private Float price;

    private Float addedPrice;

    @Column(unique = true)
    private String barcode;

    public Product() {
        this.quantity = 1;
        this.quantityInStock = 0;
    }
}
