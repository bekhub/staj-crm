package kg.ktmu.staj.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Measurement extends AbstractEntity {

    @NotNull
    @NotEmpty
    private String title;

    @NotNull
    @NotEmpty
    private String symbol;
}
