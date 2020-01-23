package util;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Sale {

    private ProductList list;
    private SaleTransaction listTransaction;

    public Sale() {
        this.list = new ProductList();
        this.listTransaction = new SaleTransaction(RandomNumberGenerator.genInt(1000, 9999));
    }

    public void start(){
        Scanner scanner = new Scanner(System.in);
        while (true){

            System.out.println("\n=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=");
            System.out.println("   Welcome to the Simple Inventory Management System");
            System.out.println("=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=");
            System.out.println("\n   Please Select from the following options:\n" +
                    "   Press 1 to Register a Product for Sale\n" +
                    "   Press 2 to Buy a Product to the Sale\n" +
                    "   Press 3 to Remove a Product from the Card\n" +
                    "   Press 4 to View all Available Products\n" +
                    "   Press 5 to Check out\n" +
                    "   Press 6 to Get Help\n" +
                    "   Press 7 to Exit\n\n" +
                    "   Pleas Enter your Choice:");
            int num;
            while (true) {
                try {
                    num = scanner.nextInt();
                    if (num<0 || num>7){
                        System.out.println("try again: ");
                        continue;
                    }
                    break;
                } catch (InputMismatchException e){
                    System.out.println("try again: ");
                    scanner.next();
                }
            }
            switch (num) {
                case 1:
                    registerProduct(scanner);
                    break;
                case 2:
                    putBasket(scanner);
                    break;
                case 3:
                    removeBasket(scanner);
                    break;
                case 4:
                    getProduct(scanner);
                    break;
                case 5:
                    buy(scanner);
                    break;
                case 6:
                    help(scanner);
                    break;
                case 7:
                    return;
            }
        }
    }

    private void registerProduct(Scanner scanner){

        if (list.length() == 5) {
            System.out.println("The stock is full, maximum 5 Products");
            System.out.println("Press any number to exit main menu");
            scanner.next();
            return;
        }
        System.out.println("Input name: ");
        scanner.nextLine();
        String name = scanner.nextLine();
        boolean hasName = true;
        while (hasName) {

            if (name.length() < 3 || name.length() > 25) {
                System.out.println("Invalid input, enter a NAME between 3 and 25 characters");
                name = scanner.nextLine();
                continue;
            }
            if (list.length() == 0) {
                hasName = false;
            } else {
                if (list.isDuplicated(name)) {
                    System.out.println("incorrect, duplicated");
                    name = scanner.nextLine();
                } else {
                    hasName = false;
                }
                /*for (int i = 0; i < list.length(); i++) {                   //вывести в отдельный метод дпринимающий на вход продукт
                    if (name.equalsIgnoreCase(list.get(i).getName())) {     //принимает на вход только имя
                        System.out.println("incorrect, duplicated");
                        name = scanner.nextLine();
                    } else {
                        hasName = false;
                    }
                }*/
            }
        }
        System.out.println("Input desc: ");
        String desc = scanner.nextLine();
        while (true) {
            if (desc.length()<1 || desc.length()>50){
                System.out.println("Invalid input, enter a DESC between 1 and 50 characters");
                desc = scanner.nextLine();
            }
            else {
                break;
            }
        }
        System.out.println("Input price: ");
        double price;
        while (true){
            try{
                price = scanner.nextDouble();
                if (price<0){
                    System.out.println("Incorrect input, PRICE < 0");
                    continue;
                }
                break;
            } catch (InputMismatchException e){
                System.out.println("try again: ");
                scanner.next();
            }
        }
        scanner.nextLine();
        int qtyOnHand = RandomNumberGenerator.genInt(0,10);
        int minOrderQty = RandomNumberGenerator.genInt(1,5);
        Product product = new Product(name,desc,qtyOnHand,price,minOrderQty);
        list.add(product);
    }

    private void putBasket(Scanner scanner){

        if (list.length() == 0) {
            System.out.println("Products is not");
            System.out.println("Press any number to exit main menu");
            scanner.next();
            return;
        }

        if (listTransaction.length() == 3) {
            System.out.println("Cast maximum 3 items");
            System.out.println("Press any number to exit main menu");
            scanner.next();
            return;
        }

        System.out.println("Please select from the following products which are available:");
        for (int i = 0; i < list.length(); i++) {
            System.out.println("Select Product " + (i+1) + ":");
            System.out.println("   Name: " + list.get(i).getName());
            System.out.println("   Description: " + list.get(i).getDesc());
            System.out.println("   Quantity: " + list.get(i).getQtyOnHand());
            System.out.println("   Price: " + list.get(i).getPrice());
            System.out.println("   Min Order Quantity: " + list.get(i).getMinOrderQty());
            System.out.println();
        }
        System.out.println("Select " + (list.length()+1) + " to exit purchase menu");
        System.out.println("Pleas Enter Selected Product: ");

        boolean hasST = true;
        while (hasST){
            label1:
            {
                if (listTransaction.length() == 3) {
                    System.out.println("Cast maximum 3 items");
                    hasST = false;
                    System.out.println("Press any number to exit main menu");
                    scanner.next();
                    break label1;
                }
                int select2;
                while (true) {
                    try {
                        select2 = scanner.nextInt();
                        if (select2 < 1 || select2 > list.length() + 1) {
                            System.out.println("try again: ");
                            continue;
                        }
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("try again: ");
                        scanner.next();
                    }
                }
                if ((list.length() + 1) == select2) {
                    return;
                }
                if (listTransaction.length() != 0) {
                    int count = 1;

                    for (int j = 0; j < listTransaction.length(); j++) {
                        if (list.get(select2 - 1).getName().equals(listTransaction.get(j).getName())) {
                            count++;
                        }
                    }
                    if (list.get(select2 - 1).getQtyOnHand() < (list.get(select2 - 1).getMinOrderQty() * (count))) {
                        System.out.println("Sorry, not enough products to buy");
                        break label1;
                    } else {
                        listTransaction.add(list.get(select2 - 1));
                        System.out.println("Product added");
                        break label1;
                    }
                }
                if (list.get(select2 - 1).getQtyOnHand() < list.get(select2 - 1).getMinOrderQty()) {
                    System.out.println("Sorry, not enough products to buy");
                    break;
                } else {
                    listTransaction.add(list.get(select2 - 1));
                    System.out.println("Product added");
                    break;
                }
            }
        }
    }

    private void removeBasket(Scanner scanner){
        if (listTransaction == null || listTransaction.length()==0) {
            System.out.println("Basket is empty");
            System.out.println("Press any number to exit main menu");
            scanner.next();
            return;
        }
        System.out.println("Please select from the following products which have been added to the cart:");
        for (int i = 0; i < listTransaction.length(); i++) {
            System.out.println("Select Added Item " + (i+1) + ":");
            System.out.println("   Name: " + listTransaction.get(i).getName());
            System.out.println("   Description: " + listTransaction.get(i).getDesc());
            System.out.println("   Quantity: " + listTransaction.get(i).getQtyOnHand());
            System.out.println("   Price: " + listTransaction.get(i).getPrice());
            System.out.println("   Min Order Quantity: " + listTransaction.get(i).getMinOrderQty());
            System.out.println();
        }
        System.out.println("Select Added Item " + (listTransaction.length()+1) + " to exit remove menu");
        System.out.println("Pleas Enter Added Item: ");


        while (true){
            if (listTransaction.length()==0){
                System.out.println("Basket is empty");
                System.out.println("Press any number to exit main menu");
                scanner.next();
                break;
            }
            int select3;
            while (true){
                try {
                    select3 = scanner.nextInt();
                    if (select3<1 || select3>(listTransaction.length()+1)){
                        System.out.println("try again: ");
                        continue;
                    }
                    break;
                } catch (InputMismatchException e){
                    System.out.println("try again: ");
                    scanner.next();
                }
            }
            if (select3 == (listTransaction.length() + 1)) {
                break;
            }
            listTransaction.remove(select3 - 1);
            System.out.println("Item remove.");
            System.out.println("Press any number to exit main menu");
            scanner.next();
            break;

        }
    }

    private void getProduct(Scanner scanner){
        if (list == null) {
            System.out.println("Products is null");
            System.out.println("Press any number to exit main menu");
            scanner.next();
            return;
        }
        for (int i = 0; i < list.length(); i++) {
            System.out.println("Select Product " + (i + 1) + ":");
            System.out.println("   Name: " + list.get(i).getName());
            System.out.println("   Description: " + list.get(i).getDesc());
            System.out.println("   Quantity: " + list.get(i).getQtyOnHand());
            System.out.println("   Price: " + list.get(i).getPrice());
            System.out.println("   Min Order Quantity: " + list.get(i).getMinOrderQty());
            System.out.println();
        }
        System.out.println("Press any number to exit main menu");
        scanner.next();
    }

    private void buy(Scanner scanner){
        if (listTransaction == null || listTransaction.length() == 0) {
            System.out.println("Basket is empty");
            System.out.println("Press any number to exit main menu");
            scanner.next();
            return;
        }

        for (int i = 0; i < listTransaction.length(); i++) {
            System.out.println("Mame: " + listTransaction.get(i).getName());
            System.out.println("   Desc: " + listTransaction.get(i).getDesc());
            System.out.println("   Price: " + listTransaction.get(i).getPrice());
            System.out.println("   Quantity: " + listTransaction.get(i).getQtyOnHand());
            System.out.println("   Min Order Quantity: " + listTransaction.get(i).getMinOrderQty());
            System.out.println();
        }
        System.out.println("Total cost: " + listTransaction.getTotalCost() + " money");

        System.out.println("Press 1 to buy\nPress 2 to exit Buy menu");
        int select5;
        while (true) {
            try {
                select5 = scanner.nextInt();
                if (select5<1 || select5>2){
                    System.out.println("try again: ");
                    continue;
                }
                break;

            } catch (InputMismatchException e) {
                System.out.println("try again: ");
                scanner.next();
            }
        }
        if (select5 == 2)
            return;
        else{
            System.out.println("Thank you for your purchase\n" +
                    "Code Transaction: " + listTransaction.getSaleCode() +
                    "\nTotal Cost: " + listTransaction.getTotalCost());
            Sale.startTransaction(list, listTransaction);
            System.out.println();

            listTransaction.clean();
            System.out.println("Press any number to exit main menu");
            scanner.next();
        }
    }

    private void help(Scanner scanner){
        System.out.println("This is help\n\nPress any number to exit");
        scanner.next();
    }

    private static void startTransaction(ProductList listPr, SaleTransaction saleTR){
        for (int i = 0; i < listPr.length(); i++) {
            for (int j = 0; j < saleTR.getItem().length; j++) {
                if (listPr.get(i).getName().equals(saleTR.get(j).getName())) {
                    listPr.get(i).setQtyOnHand(listPr.get(i).getQtyOnHand()-saleTR.get(j).getMinOrderQty());
                }
            }
        }
    }

}
