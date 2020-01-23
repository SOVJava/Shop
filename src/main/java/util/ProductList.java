package util;

import java.util.Arrays;

public class ProductList {
    private Product[] listOfProduct = new Product[0];

    public boolean add(Product prod){
        if (listOfProduct.length == 5)
            return false;
        Product[] res = Arrays.copyOf(this.listOfProduct, this.listOfProduct.length + 1);
        res[res.length-1] = prod;
        this.listOfProduct = res;
        return true;
    }

    public Product get(int index){
        if (index < 0 || index > listOfProduct.length-1)
            return null;
        return listOfProduct[index];
    }

    public int length(){
        return listOfProduct.length;
    }

    public boolean isDuplicated(String name){
        if (listOfProduct.length == 0)
            return false;
        for (int i = 0; i < listOfProduct.length; i++) {
            if (listOfProduct[i].getName().equals(name))
                return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "ProductList { " +
                Arrays.toString(listOfProduct) +
                "}";
    }
}
