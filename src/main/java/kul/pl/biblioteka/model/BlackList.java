package kul.pl.biblioteka.model;


import kul.pl.biblioteka.utils.BlackListStatus;
import lombok.Data;
import lombok.Value;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;

@Data
public class BlackList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private UUID userId;
    private Date dateFrom;
    private Date dateTo;
    private String comment;
    private BlackListStatus status;

    public BlackList(UUID userId, Date dateFrom, Date dateTo, String comment, BlackListStatus status) {
        this.userId = userId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.comment = comment;
        this.status = status;
    }
}
