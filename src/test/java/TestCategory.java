import com.luxoft.dao.CategoryDao;
import com.luxoft.dao.ContactDao;
import com.luxoft.dao.MRDao;
import com.luxoft.model.Category;
import com.luxoft.model.Contact;
import org.junit.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by aeliseev on 15.04.2014
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/applicationContext.xml"})
public class TestCategory {

    @Autowired
    ContactDao cDao;

    @Autowired
    CategoryDao catDao;

    @Test
    @Ignore
    public void testCats() {
        List<Category> cats = catDao.getAllCats();
        Category catSpammers = cats.get(0);
        Category catGF = cats.get(1);

        System.out.println(catSpammers.getName() + ":");
        for (Contact c: cDao.getContactsByCat(catSpammers)) {
            System.out.println(c.getName());
        }

        System.out.println(catGF.getName() + ":");
        for (Contact c: cDao.getContactsByCat(catGF)) {
            System.out.println(c.getName());
        }
    }

    @Test
    @Ignore
    public void testCats2() {
        Category newCat = new Category("China red-green sheep");
        catDao.save(newCat);

        Category peopleCat = catDao.getCategoryByName("Sheep");

        catDao.setAncestor(peopleCat, newCat);

        System.out.println(newCat.getAncestors());
        System.out.println(newCat.getId());
    }

    @Test
    @Ignore
    public void testCatRename() {
        Category cat = catDao.getCategoryByName("People");
        catDao.updateCategoryName(cat, "Lying people");
    }

    @Test
    public void testChangeHierarchy() {
        Category cat = new Category("Animals");
        catDao.save(cat);

        catDao.changeHierarchy(cat, catDao.getCategoryByName("Lying people"));
    }
}