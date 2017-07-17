package acl.test.domain;

import java.util.Date;

/**
 * Created by toni8810 on 15/07/17.
 */
public interface Post {

    public Long getId();

    public void setId(Long id);

    public Date getDate();

    public void setDate(Date date);

    public String getMessage();

    public void setMessage(String message);
}
