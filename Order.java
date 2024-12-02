import java.util.ArrayList;

class Order {
    ArrayList<String> items;
    ArrayList<Double> prices;

    public Order(Food[] menu) {
        items = new ArrayList<>();
        prices = new ArrayList<>();
    }

    public void addItem(String itemName, double itemPrice) {
        items.add(itemName);
        prices.add(itemPrice);
    }

    public boolean hasItem(String itemName) {
        return items.contains(itemName);
    }

    public double calculateTotal() {
        double total = 0;
        for (double price : prices) {
            total += price;
        }
        return total;
    }
}

