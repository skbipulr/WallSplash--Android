package wallsplash.itechnotion.com.bean;

/**
 * Created by admin on 3/14/2018.
 */

public class FavouriteBean {
    private String id;
    private String url;



    public FavouriteBean() {

    }
   public FavouriteBean(String id,String url) {
        this.id=id;
        this.url=url;

    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
