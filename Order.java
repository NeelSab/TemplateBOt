class Order {
    private String[] items;
    private double[] prices;
    private int itemCount;

    public Order(Food[] menu) {
        items = new String[10];  // Set a limit of 10 items for the order.
        prices = new double[10];
        itemCount = 0;
    }

    public void addItem(String itemName, double itemPrice) {
        if (itemCount < items.length) {
            items[itemCount] = itemName;
            prices[itemCount] = itemPrice;
            itemCount++;
        } else {
            System.out.println("Sorry, you can only order up to " + items.length + " items.");
        }
    }

    public boolean hasItem(String itemName) {
        for (int i = 0; i < itemCount; i++) {
            if (items[i].equalsIgnoreCase(itemName)) {
                return true;
            }
        }
        return false;
    }

    public double calculateTotal() {
        double total = 0;
        for (int i = 0; i < itemCount; i++) {
            total += prices[i];
        }
        return total;
    }
}
