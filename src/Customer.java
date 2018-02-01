public class Customer {
    private int id;
    private Town town;
    private String type;
    private int bonusLevel;
    private boolean hasSmartTechnology;
    private int energyConsumption0To6;
    private int energyConsumption6To12;
    private int energyConsumption12To18;
    private int energyConsumption18To24;

    public Customer(int id, Town town, String type, int bonusLevel, boolean hasSmartTechnology,
                    int energyConsumption0To6, int energyConsumption6To12,
                    int energyConsumption12To18, int energyConsumption18To24) {
        this.id = id;
        this.town = town;
        this.type = type;
        this.bonusLevel = bonusLevel;
        this.hasSmartTechnology = hasSmartTechnology;
        this.energyConsumption0To6 = energyConsumption0To6;
        this.energyConsumption6To12 = energyConsumption6To12;
        this.energyConsumption12To18 = energyConsumption12To18;
        this.energyConsumption18To24 = energyConsumption18To24;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(id).append(";").append(town.getId()).append(";").append(type).append(";");
        stringBuilder.append(bonusLevel).append(";").append(hasSmartTechnology).append(";").append(town.getRegion()).append(";");
        stringBuilder.append(energyConsumption0To6).append(";").append(energyConsumption6To12).append(";");
        stringBuilder.append(energyConsumption12To18).append(";").append(energyConsumption18To24);
        return stringBuilder.toString();
    }
}
