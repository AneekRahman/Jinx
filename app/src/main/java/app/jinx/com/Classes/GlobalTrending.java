package app.jinx.com.Classes;

public class GlobalTrending {

    String hashTag;
    int hashCount;

    public GlobalTrending(String hashTag, int hashCount){

        this.hashTag = hashTag;
        this.hashCount = hashCount;

    }

    public String getHashTag() {
        return hashTag;
    }

    public int getHashCount() {
        return hashCount;
    }
}
