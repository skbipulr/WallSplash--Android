package wallsplash.itechnotion.com.bean;

/**
 * Created by admin on 3/14/2018.
 */

public class RelatedBean {
    private String id;
    private String regular;
    private String large;



    public RelatedBean(String id, String regular, String medium) {
        this.id = id;
        this.regular = regular;
        this.large = medium;

    }
 public RelatedBean(String id, String regular) {
        this.id = id;
        this.regular = regular;


    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegular() {
        return regular;
    }

    public void setRegular(String regular) {
        this.regular = regular;
    }

    public String getMedium() {
        return large;
    }

    public void setMedium(String medium) {
        this.large = medium;
    }
}
