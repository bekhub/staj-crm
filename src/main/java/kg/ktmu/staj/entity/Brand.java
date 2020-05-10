package kg.ktmu.staj.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Brand extends AbstractEntity {

    @NotNull
    @NotEmpty
    private String title;

    private String photo;
}
