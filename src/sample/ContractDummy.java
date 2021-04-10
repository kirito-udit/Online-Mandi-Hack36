package sample;
public class ContractDummy {
    private int contractId;
    private String sellerPhone;
    private String cropName;
    private int price;
    private String description;

    public ContractDummy(int contractId, String sellerPhone, String cropName, int price, String description) {
        this.contractId = contractId;
        this.sellerPhone = sellerPhone;
        this.cropName = cropName;
        this.price = price;
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public int getContractId() {
        return contractId;
    }

    public String getCropName() {
        return cropName;
    }

    public String getSellerPhone() {
        return sellerPhone;
    }

    public String getDescription() {
        return description;
    }

}
