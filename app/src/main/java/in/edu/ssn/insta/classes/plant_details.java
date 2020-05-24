package in.edu.ssn.insta.classes;

public class plant_details {
    String product_name1;
    String product_name2, key1, key2;
    int pic1;
    int pic2;

    public plant_details(String product_name1, String product_name2, String key1, String key2, int pic1, int pic2) {
        this.product_name1 = product_name1;
        this.product_name2 = product_name2;
        this.key1 = key1;
        this.key2 = key2;
        this.pic1 = pic1;
        this.pic2 = pic2;
    }

    public String getProduct_name1() {
        return product_name1;
    }

    public void setProduct_name1(String product_name1) {
        this.product_name1 = product_name1;
    }

    public String getProduct_name2() {
        return product_name2;
    }

    public void setProduct_name2(String product_name2) {
        this.product_name2 = product_name2;
    }

    public String getKey1() {
        return key1;
    }

    public void setKey1(String key1) {
        this.key1 = key1;
    }

    public String getKey2() {
        return key2;
    }

    public void setKey2(String key2) {
        this.key2 = key2;
    }

    public int getPic1() {
        return pic1;
    }

    public void setPic1(int pic1) {
        this.pic1 = pic1;
    }

    public int getPic2() {
        return pic2;
    }

    public void setPic2(int pic2) {
        this.pic2 = pic2;
    }
}
