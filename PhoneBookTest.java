import java.util.*;
import java.util.stream.Collectors;

class DuplicateNumberException extends Exception {
    DuplicateNumberException(String msg) {
        super(msg);
    }
}

class Contact {
    private  String name;
    private  String number;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Contact(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String toString() {
        return name+" "+number;
    }
}

class PhoneBook {
    private Map<String, Contact> contacts;

    public PhoneBook() {
        this.contacts = new HashMap<>();
    }

    public void addContact(String name, String number) throws  DuplicateNumberException{

        if(contacts.get(number)!=null) {
            throw new DuplicateNumberException("Duplicate number: " + number);
        }

        this.contacts.put(number, new Contact(name, number));
    }

    void contactsByNumber(String number) {
        List<Contact> list = contacts.values().stream().filter(c -> c.getNumber().contains(number))
                .sorted(Comparator.comparing(Contact::getName).thenComparing(Contact::getNumber))
                .collect(Collectors.toList());
        if(list.size() == 0) {
            System.out.println("NOT FOUND");
        } else {
            list.stream().forEach(System.out::println);
        }
    }

    void contactsByName(String name) {
        List<Contact> list = contacts.values().stream().filter(c -> c.getName().equals(name))
                .sorted(Comparator.comparing(Contact::getName).thenComparing(Contact::getNumber))
                .collect(Collectors.toList());
        if(list.size() == 0) {
            System.out.println("NOT FOUND");
        } else {
            list.stream().forEach(System.out::println);
        }
    }
}







public class PhoneBookTest {

    public static void main(String[] args) {
        PhoneBook phoneBook = new PhoneBook();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            try {
                phoneBook.addContact(parts[0], parts[1]);
            } catch (DuplicateNumberException e) {
                System.out.println(e.getMessage());
            }
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            System.out.println(line);
            String[] parts = line.split(":");
            if (parts[0].equals("NUM")) {
                phoneBook.contactsByNumber(parts[1]);
            } else {
                phoneBook.contactsByName(parts[1]);
            }
        }
    }

}

// Вашиот код овде

