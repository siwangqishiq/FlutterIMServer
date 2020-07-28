package xyz.panyi.imserver.dao;

public class Section {
    private int sid;
    private String content;
    private String link;
    private String refer;
    private String image;
    private long updateTime;
    private int imageCount;
    private String extra;

    /**
     *
     * @param sec
     * @return
     */
    public boolean isSame(Section sec){
        if(sec == null || link == null || content == null || image == null)
            return false;

        return link.equals(sec.link) && content.equals(sec.content) && image.equals(sec.image);
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public int getImageCount() {
        return imageCount;
    }

    public void setImageCount(int imageCount) {
        this.imageCount = imageCount;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getRefer() {
        return refer;
    }

    public void setRefer(String refer) {
        this.refer = refer;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Section{" +
                "content='" + content + '\'' +
                ", link='" + link + '\'' +
                ", refer='" + refer + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
