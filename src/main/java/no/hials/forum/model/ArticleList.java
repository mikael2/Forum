package no.hials.forum.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 *
 * @author mikael
 */
@Entity
public class ArticleList implements Serializable {
    @Id @GeneratedValue
    Long id;
    
    String name;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="ARTICLE_ID")
    List<Article> articles = new ArrayList<>();
    
    protected ArticleList() {    
    }

    
    public ArticleList(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    public void addArticle(Article article) {
        getArticles().add(article);
    }
    
    public boolean removeArticle(Article article) {
        return getArticles().remove(article);
    }
    
    public List<Article> getArticles() {
        if(articles == null) {
            articles = new ArrayList<>();
        }
        
        return articles;
    }
}
