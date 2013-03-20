package no.hials.forum.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author mikael
 */
public abstract class NamedType implements Serializable {
    @Id @GeneratedValue
    Long id;
    
    @Temporal(TemporalType.DATE)
    Date creationDate;
    
    String name;

    protected NamedType(String name) {
        this.name = name;
        creationDate = new Date();
    }

    public Long getId() {
        return id;
    }

    
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
