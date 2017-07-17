package acl.test.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by toni8810 on 14/07/17.
 */
@Entity
@Table(name = "public_post")
public class PublicPost implements Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date date;
    private String message;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
