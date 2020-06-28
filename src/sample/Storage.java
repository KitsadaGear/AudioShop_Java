package sample;


import java.util.ArrayList;

public class Storage {
    ArrayList<String> IdMC = new ArrayList<>();
    ArrayList<String> productMC = new ArrayList();
    ArrayList<String> priceMC = new ArrayList<>();
    ArrayList<String> IdPM = new ArrayList<>();
    ArrayList<String> productPM = new ArrayList();
    ArrayList<String> pricePM = new ArrayList<>();
    ArrayList<String> IdSC = new ArrayList<>();
    ArrayList<String> productSC = new ArrayList();
    ArrayList<String> priceSC = new ArrayList<>();
    ArrayList<String> IdDVD = new ArrayList<>();
    ArrayList<String> productDVD = new ArrayList();
    ArrayList<String> priceDVD = new ArrayList<>();
    ArrayList<String> IdCC = new ArrayList<>();
    ArrayList<String> productCC = new ArrayList();
    ArrayList<String> priceCC = new ArrayList<>();
    ArrayList<String> IdOther = new ArrayList<>();
    ArrayList<String> productOther = new ArrayList();
    ArrayList<String> priceOther = new ArrayList<>();
    ArrayList<String> assignP = new ArrayList<>();

    ArrayList<String> assigneds = new ArrayList<>();

    ArrayList<Double> sumPrice = new ArrayList<>();

    ArrayList<Double> deposit = new ArrayList<>();

    ArrayList<Double> price = new ArrayList<>();

    ArrayList<Integer> amount = new ArrayList<>();
    
    public ArrayList<String> getProductMC() {
        return productMC;
    }

    public void setProductMC(ArrayList<String> productMC) {
        this.productMC = productMC;
    }

    public ArrayList<String> getPriceMC() {
        return priceMC;
    }

    public void setPriceMC(ArrayList<String> priceMC) {
        this.priceMC = priceMC;
    }

    public ArrayList<String> getProductDVD() {
        return productDVD;
    }

    public void setProductDVD(ArrayList<String> productDVD) {
        this.productDVD = productDVD;
    }

    public ArrayList<String> getPriceDVD() {
        return priceDVD;
    }

    public void setPriceDVD(ArrayList<String> priceDVD) {
        this.priceDVD = priceDVD;
    }

    public ArrayList<String> getProductCC() {
        return productCC;
    }

    public void setProductCC(ArrayList<String> productCC) {
        this.productCC = productCC;
    }

    public ArrayList<String> getPriceCC() {
        return priceCC;
    }

    public void setPriceCC(ArrayList<String> priceCC) {
        this.priceCC = priceCC;
    }

    public ArrayList<String> getProductOther() {
        return productOther;
    }

    public void setProductOther(ArrayList<String> productOther) {
        this.productOther = productOther;
    }

    public ArrayList<String> getPriceOther() {
        return priceOther;
    }

    public void setPriceOther(ArrayList<String> priceOther) {
        this.priceOther = priceOther;
    }

    public ArrayList<String> getProductSC() {
        return productSC;
    }

    public void setProductSC(ArrayList<String> productSC) {
        this.productSC = productSC;
    }

    public ArrayList<String> getPriceSC() {
        return priceSC;
    }

    public void setPriceSC(ArrayList<String> priceSC) {
        this.priceSC = priceSC;
    }

    public ArrayList<String> getProduct() {
        return productMC;
    }

    public ArrayList<String> getProductPM() {
        return productPM;
    }

    public void setProductPM(ArrayList<String> productPM) {
        this.productPM = productPM;
    }

    public ArrayList<String> getPricePM() {
        return pricePM;
    }

    public void setPricePM(ArrayList<String> pricePM) {
        this.pricePM = pricePM;
    }

    public void setProduct(ArrayList<String> product) {
        this.productMC = product;
    }

    public ArrayList<String> getPrice() {
        return priceMC;
    }

    public void setPrice(ArrayList<String> price) {
        this.priceMC = price;
    }

    public Storage() {
    }
}
