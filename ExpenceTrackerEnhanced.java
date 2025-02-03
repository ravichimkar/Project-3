import java.io.*;
import java.util.*;

public class ExpenceTrackerEnhanced {
    private Map<String, Double> expenses;
    private String username;
    private static final String FILE_PATH = "expenses.txt";

    public ExpenceTrackerEnhanced(String username) {
        this.username = username;
        this.expenses = new HashMap<>();
        loadExpenses();
    }

    public void addExpense(String category, double amount) {
        expenses.put(category, expenses.getOrDefault(category, 0.0) + amount);
        saveExpenses();
    }

    public void listExpenses() {
        expenses.forEach((category, amount) -> 
            System.out.println("Category: " + category + ", Amount: " + amount));
    }

    public void showCategorySum() {
        expenses.forEach((category, amount) -> 
            System.out.println("Total for " + category + ": " + amount));
    }

    private void saveExpenses() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Map.Entry<String, Double> entry : expenses.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving expenses: " + e.getMessage());
        }
    }

    private void loadExpenses() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    expenses.put(parts[0], Double.parseDouble(parts[1]));
                }
            }
        } catch (IOException e) {
            System.out.println("No existing expense file found. Starting fresh.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your username:");
        String username = scanner.nextLine();
        ExpenceTrackerEnhanced tracker = new ExpenceTrackerEnhanced(username);

        while (true) {
            System.out.println("1. Add Expense  2. List Expenses  3. Category Sum  4. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("Enter category:");
                    String category = scanner.nextLine();
                    System.out.println("Enter amount:");
                    double amount = scanner.nextDouble();
                    tracker.addExpense(category, amount);
                    break;
                case 2:
                    tracker.listExpenses();
                    break;
                case 3:
                    tracker.showCategorySum();
                    break;
                case 4:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
