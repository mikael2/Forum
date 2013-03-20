package no.hials.forum.model;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 *
 * @author Mikael
 */
public class JPATest {
    private static final String PU_NAME = "FORUM";
    
    static EntityManagerFactory factory;
    static EntityManager em;
    
    public JPATest() {
    }

    @BeforeClass
    public static void initTestFixtures() throws Exception {
        factory = Persistence.createEntityManagerFactory(PU_NAME);
        em = factory.createEntityManager();
    }
    
    @AfterClass
    public static void closeTestFixture() {
        em.close();
        factory.close();
    }
    
    @Before
    public void createTestData() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        ForumUser user = new ForumUser("Mikael");
        em.persist(user);
        
        em.persist(new Message(user, "Message #1"));
        em.persist(new Message(user, "Message #2"));
        em.persist(new Message(user, "Message #3"));
        
        Message master = new Message(user, "Master");
        master.addReply(new Message(user,"Sub"));
        em.persist(master);
        
        Article articleA = new Article("ArticleA");
        Article articleB = new Article("ArticleB");
        Article articleC = new Article("ArticleC");
        Article articleD = new Article("ArticleD");
        em.persist(articleA);
        em.persist(articleB);
        em.persist(articleC);
        em.persist(articleD);
        
        
        ArticleList listA = new ArticleList("Fun stuff");
        listA.addArticle(articleA);
        listA.addArticle(articleB);
        em.persist(listA);
        
        ArticleList listB = new ArticleList("Fun stuff");
        listB.addArticle(articleA);
        listB.addArticle(articleB);
        listB.addArticle(articleC);
        listB.addArticle(articleD);
        em.persist(listB);
        
        tx.commit();
    }
    
    @Test
    public void testRemoveMessage() {
        List<Message> list = em.createQuery("select m from Message m where m.forumUser.name = 'Mikael'", Message.class).getResultList();
        assertEquals(5, list.size());

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Message sub = em.createQuery("select m from Message m where m.message = 'Sub'", Message.class).getSingleResult();
        System.out.println("Message id is " + sub.getId());
        em.remove(sub);

        tx.commit();
    
        list = em.createQuery("select m from Message m where m.forumUser.name = 'Mikael'", Message.class).getResultList();
        assertEquals(4, list.size());
    }

    @Test
    public void testRemoveArticle() {
        List<Article> list = em.createQuery("select a from Article a", Article.class).getResultList();
        assertEquals(4, list.size());

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Article articleA = em.createQuery("select a from Article a where a.name = 'ArticleA'", Article.class).getSingleResult();
        Article articleB = em.createQuery("select a from Article a where a.name = 'ArticleB'", Article.class).getSingleResult();
        em.remove(articleA);
        em.remove(articleB);

        tx.commit();
    
        list = em.createQuery("select a from Article a", Article.class).getResultList();
        assertEquals(2, list.size());
    }
}