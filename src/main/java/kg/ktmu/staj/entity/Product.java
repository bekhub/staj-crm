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
    private Brand brand;

    @ManyToOne(targetEntity = Category.class)
    private Category category;

    @Lob
    private byte[] photo;

    @ManyToOne(targetEntity = Measurement.class)
    private Measurement measurementType;

    private Integer quantity;

    private Integer quantityInStock;

    private Double addedValue;

    private Double grossWeight;

    private Double price;

    private Double addedPrice;

    @Column(unique = true)
    private String barcode;

    public Product() {
        this.quantity = 1;
        this.quantityInStock = 0;
    }
}
