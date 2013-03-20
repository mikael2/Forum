package no.hials.forum.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinTable(name="ARTICLE_LIST")
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
