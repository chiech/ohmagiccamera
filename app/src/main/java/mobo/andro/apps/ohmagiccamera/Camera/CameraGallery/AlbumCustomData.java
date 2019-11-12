package mobo.andro.apps.ohmagiccamera.Camera.CameraGallery;

public class AlbumCustomData {

    String path,year,month;
    int date;
    boolean is_selected;

    public AlbumCustomData(String path,String year,String month,int date,boolean is_selected) {
        this.path=path;
        this.year=year;
        this.month=month;
        this.date=date;
        this.is_selected=is_selected;
    }
    public int getDate() {
        return date;
    }
    public String getMonth() {
        return month;
    }
    public String getPath() {
        return path;
    }
    public String getYear() {
        return year;
    }
    public boolean get_is_selected()
    {
        return this.is_selected;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public void setIs_selected(boolean is_selected) {
        this.is_selected = is_selected;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setYear(String year) {
        this.year = year;
    }


}
