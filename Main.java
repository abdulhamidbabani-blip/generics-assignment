import java.util.ArrayList;
import java.util.List;

// ---------- Class hierarchy ----------
class Animal {
    String name;
    Animal(String name) { this.name = name; }
    @Override public String toString() { return getClass().getSimpleName() + "(" + name + ")"; }
}

class Bird extends Animal {          // الطيور
    Bird(String name) { super(name); }
}

class Canary extends Bird {          // كناري
    Canary(String name) { super(name); }
}

class Zebra extends Animal {         // زيبرا (مش طائر)
    Zebra(String name) { super(name); }
}

class Crocodile extends Animal {     // تمساح (مش طائر)
    Crocodile(String name) { super(name); }
}

// ---------- class Cage<T> ----------
class Cage<T> {
    private List<T> items = new ArrayList<>();

    public void add(T item) { items.add(item); }
    public T get(int i)     { return items.get(i); }
    public List<T> getItems() { return items; }
}

// ---------- class BirdCage<T extends Bird> extends Cage<T> ----------
// upper bound: بيقبل الطيور بس
class BirdCage<T extends Bird> extends Cage<T> {
}

public class Main {

    // ---------- Lower bound with Number ----------
    // بيقبل List<Integer> أو List<Number> أو List<Object>
    static void addNumbers(List<? super Integer> list) {
        list.add(10);
        list.add(20);
    }

    // ---------- Lower bound with Cage ----------
    // بيقبل Cage<Canary> أو Cage<Bird> أو Cage<Animal>
    static void addCanary(Cage<? super Canary> cage) {
        cage.add(new Canary("Tweety"));
    }

    public static void main(String[] args) {

        // 1) BirdCage بيقبل الطيور بس
        BirdCage<Canary> canaryCage = new BirdCage<>();
        canaryCage.add(new Canary("Kenary"));
        System.out.println("BirdCage<Canary>: " + canaryCage.getItems());

        // BirdCage<Zebra> zebraCage = new BirdCage<>();      // ERROR: Zebra مش Bird
        // BirdCage<Crocodile> crocCage = new BirdCage<>();   // ERROR: Crocodile مش Bird

        // 2) Cage العادي بيقبل أي حاجة
        Cage<Animal> zoo = new Cage<>();
        zoo.add(new Zebra("Zebra1"));
        zoo.add(new Crocodile("Croc1"));
        zoo.add(new Bird("Bird1"));
        System.out.println("Cage<Animal>: " + zoo.getItems());

        // 3) Lower bound (super) مع Number
        List<Number> numbers = new ArrayList<>();
        addNumbers(numbers);
        System.out.println("List<Number>: " + numbers);

        List<Object> objects = new ArrayList<>();
        addNumbers(objects);
        System.out.println("List<Object>: " + objects);

        // 4) Lower bound (super) مع Cage
        addCanary(zoo);   // Cage<Animal> OK لأن Animal super لـ Canary
        System.out.println("After addCanary: " + zoo.getItems());
    }
}
