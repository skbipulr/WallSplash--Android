package wallsplash.itechnotion.com.bean;

/**
 * Created by admin on 3/14/2018.
 */

public class ExploreBean {
    private String id;
    private String description;
    private String regular;




    public ExploreBean(String id, String title) {
        this.id = id;
        this.description = title;

    }
    public ExploreBean(String id,String title, String regular) {
        this.id = id;
        this.description = title;
        this.regular = regular;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return description;
    }

    public void setTitle(String title) {
        this.description = title;
    }

    public String getRegular() {
        return regular;
    }

    public void setRegular(String regular) {
        this.regular = regular;
    }
}
