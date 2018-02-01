public class Town {
    private int id;
    private String region;

    public Town(int id,String region) {
        this.id = id;
        this.region = region;
    }

    public int getId() {
        return id;
    }

    public String getRegion() {
        return region;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{ Town : ").append("id = ").append(id).append(", ");
        stringBuilder.append("region = ").append(region).append(" }");
        return stringBuilder.toString();
    }
}