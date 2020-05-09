package kg.ktmu.staj.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Report extends AbstractEntity {

    private Integer money;

    @Column(columnDefinition = "TEXT")
    private String text;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime created;

    public Report() {
        this.created = LocalDateTime.now();
    }
}
