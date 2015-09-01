package tes.volley.gsonparser;

/**
 * Created by Kucing Imut on 8/21/15.
 */


public class Berita {

    public String id = "";
    public String slug = "";
    public String title = "";
    public String excerpt = "";
    public String date = "";
    public String date_clear = "";
    public String link = "";
    public String thumbnail_small = "";
    public String thumbnail_medium = "";


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate_clear() {
        return date_clear;
    }

    public void setDate_clear(String date_clear) {
        this.date_clear = date_clear;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getThumbnail_small() {
        return thumbnail_small;
    }

    public void setThumbnail_small(String thumbnail_small) {
        this.thumbnail_small = thumbnail_small;
    }

    public String getThumbnail_medium() {
        return thumbnail_medium;
    }

    public void setThumbnail_medium(String thumbnail_medium) {
        this.thumbnail_medium = thumbnail_medium;
    }
}
