import java.util.Scanner;

public class Chatbot {

    Food[] menu = {
        new Food("veggie burger", 5.99, "burger"),
        new Food("cheeseburger", 6.99, "burger"),
        new Food("small fries", 2.49, "side"),
        new Food("medium fries", 2.99, "side"),
        new Food("large fries", 3.49, "side"),
        new Food("small drink", 1.49, "drink"),
        new Food("medium drink", 1.99, "drink"),
        new Food("large drink", 2.49, "drink")
    };

    void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello, welcome to Regal Burgers. What would you like to do today? You can: Order food and drink, book a catering (up to two weeks early), or you can ask about our nutrition (calories):");
        while (true) {
            String input = scanner.nextLine().toLowerCase();
            if (input.contains("order") || input.contains("food") || input.contains("drink")) {
                System.out.println("Sure!");
                takeOrder();
            } else if (input.contains("cater") || input.contains("catering") || input.contains("book")) {
                bookCatering();
            } else if (input.contains("nutrition") || input.contains("calories")) {
                provideNutritionInfo();
            } else if (input.contains("no")) {
                System.out.println("Okay! Have a good day and hope to see you soon.");
                break;
            } else {
                System.out.println(randomMisunderstanding());
            }
        }        
    }

    void takeOrder() {
        Scanner scanner = new Scanner(System.in);
        Order order = new Order(menu);
    
        System.out.println("Here’s our menu:");
        for (Food item : menu) {
            System.out.println("- " + item.name + " ($" + item.price + ")");
        }
        System.out.println("What would you like to order? (Menu: veggie burger, cheeseburger, small/med/large fries, small/med/large drink)");
    
        long startTime = System.currentTimeMillis();
    
        while (true) {
            String orderInput = scanner.nextLine().toLowerCase();
            boolean addedSomething = false;
            int quantity = 1; 
    
            int i = 0;
            while (i < orderInput.length() && Character.isDigit(orderInput.charAt(i))) {
                i++;
            }
    
            if (i > 0) {
                quantity = Integer.parseInt(orderInput.substring(0, i));
                orderInput = orderInput.substring(i).trim();  
            }
    
            for (Food item : menu) {
                if (orderInput.contains(item.name)) {
                    for (int j = 0; j < quantity; j++) {
                        if (order.hasItem(item.name)) {
                            System.out.println("You’ve already added " + item.name + " to your order.");
                        } else {
                            order.addItem(item.name, item.price);
                            System.out.println("Added " + item.name + " for $" + item.price);
                            addedSomething = true;
                        }
                    }
                    break; 
                }
            }
    
            if (!addedSomething) {
                System.out.println("Sorry, we don’t have that.");
            }
    
            System.out.println("Your current total is: $" + String.format("%.2f", order.calculateTotal()));
    
            System.out.println("Would you like to add more items? If no, type 'done'.");
            String moreItems = scanner.nextLine().toLowerCase();
            if (moreItems.equals("done")) break;
        }
    
        long endTime = System.currentTimeMillis();
        long elapsedSeconds = (endTime - startTime) / 1000;
    
        double totalCost = order.calculateTotal();
        if (elapsedSeconds <= 90) {
            totalCost *= 0.9;
            System.out.println("Congratulations! You completed your order within 90 seconds and earned a 10% discount.");
        }
    
        System.out.println("Your final total is: $" + String.format("%.2f", totalCost) + ". Is there anything else I can help you with?");
    }
    

    void bookCatering() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Okay, in how many days? It has to be less than 14. Answer with a number.");
        int days = -1;
        while (true) {
            try {
                days = Integer.parseInt(scanner.nextLine());
                if (days > 0 && days <= 14) {
                    System.out.println("Okay, I will book you for " + days + " days from today.");
                    break;
                } else {
                    System.out.println("Sorry, that is too far away! Come back tomorrow or choose another number within 14 days.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number for the days.");
            }
        }
        System.out.println("What is your full name?");
        String name = scanner.nextLine();
        System.out.println("Okay, " + name + ", how many people are you expecting? Please state a numerical answer.");
        int numGuests = -1;
        while (true) {
            String input = scanner.nextLine();
            try {
                numGuests = Integer.parseInt(input);
                if (numGuests <= 0) {
                    System.out.println("Sorry, that is an invalid number. Please provide a positive whole number.");
                } else {
                    System.out.println("Got it! You’re expecting " + numGuests + " guests.");
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Sorry, that is an invalid number. Please provide a positive whole number.");
            }
        }
    
        System.out.println("How many vegetarians? Answer with a number.");
        int numVegetarians = -1;
        while (true) {
            String input = scanner.nextLine();
            try {
                numVegetarians = Integer.parseInt(input);
                if (numVegetarians <= numGuests) {
                    System.out.println("Ok, so that’s " + numVegetarians + " veggie burgers and " + (numGuests - numVegetarians) + " cheeseburgers.");
                    break;
                } else {
                    System.out.println("The number of vegetarians can't be more than the total guests. Please enter a valid number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number for vegetarians.");
            }
        }
    
        System.out.println("We also include " + numGuests + " medium drinks and " + numGuests + " medium fries with a 25% discount for catering. Would you like that? (yes/no)");
    
        boolean includeDiscount = false;
        while (true) {
            String discountResponse = scanner.nextLine().toLowerCase();
            if (discountResponse.equals("yes")) {
                includeDiscount = true;
                break;
            } else if (discountResponse.equals("no")) {
                includeDiscount = false;
                break;
            } else {
                System.out.println("Sorry, I didn’t get that… can you please try again? Answer with yes or no.");
            }
        }
    
        double veggieCost = 5.99;
        double cheeseburgerCost = 6.99;
        double drinkCost = 1.99;
        double friesCost = 2.99;
    
        double burgerTotal = (numVegetarians * veggieCost) + ((numGuests - numVegetarians) * cheeseburgerCost);
        double sidesTotal = includeDiscount ? (numGuests * (drinkCost + friesCost) * 0.75) : (numGuests * (drinkCost + friesCost));
        double totalCost = burgerTotal + sidesTotal;
    
        System.out.println("Okay, your total is $" + totalCost + ".");
        System.out.println("Is there anything else I can help you with?(You can: order food/ask about nutrition(calories))");
    }

    void provideNutritionInfo() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What would you like the calories for? (Menu: veggie burger, cheeseburger, small fries, medium fries, large fries, drinks)");
        String item = scanner.nextLine().toLowerCase();
        if (item.contains("veggie burger")) {
            System.out.println("The veggie burger has 550 calories.");
        } else if (item.contains("cheeseburger")) {
            System.out.println("The cheeseburger has 800 calories.");
        } else if (item.contains("small fries")) {
            System.out.println("The small fries have 150 calories.");
        } else if (item.contains("medium fries")) {
            System.out.println("The medium fries have 250 calories.");
        } else if (item.contains("large fries")) {
            System.out.println("The large fries have 450 calories.");
        } else if (item.contains("drink")) {
            System.out.println("Our drinks are 25 calories per ounce. How many ounces would you like to know about?");
            String ouncesStr = scanner.nextLine();
            System.out.println("The drink with " + ouncesStr + " ounces has " + (Integer.parseInt(ouncesStr) * 25) + " calories.");
        } else {
            System.out.println("Sorry, I didn’t quite get that. Please choose from the menu.");
        }
    }

    String randomMisunderstanding() {
        String[] misunderstandings = {
            "I didn’t quite catch that. Could you please rephrase?",
            "Oops, could you try that again?",
            "Hmm, I didn't understand. Can you rephrase?",
            "Sorry, I don't know what that is. Please try again.",
            "I misunderstood you, can you please try again?"
        };
        int randomIndex = (int) (Math.random() * misunderstandings.length);
        return misunderstandings[randomIndex];
    }
}
