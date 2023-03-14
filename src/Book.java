import java.util.Arrays;
import java.util.List;

public class Book {
    private String title;
    private String contents;
    private List<String> pageInfo;
    private double rating;
    private int usersRated;

    public List<String> getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(List<String> pageInfo) {
        this.pageInfo = pageInfo;
    }


    public int getUsersRated() {
        return usersRated;
    }

    public void setUsersRated(int usersRated) {
        this.usersRated = usersRated;
    }

    public Book(String title, String contents) {
        this.title = title;
        this.contents = contents;
        String contentsArray[] = contents.split(" ");
        this.pageInfo = Arrays.stream(contentsArray).toList();
        this.rating = 0;
        usersRated = 0;
    }


    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }


    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String toString() {
        return title + ", has rating " + rating + "/10";
    }



}

