package kg.ktmu.staj.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.Entity;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Photo extends AbstractEntity {

    private String path;
}
