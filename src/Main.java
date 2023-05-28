public class Main {
    public static void main(String[] args) {
        HashTable<String, Integer> hashTable = new HashTable<>();

        // Добавление элементов
        hashTable.add("one", 1);
        hashTable.add("two", 2);
        hashTable.add("three", 3);

        // Получение элементов
        System.out.println(hashTable.get("one"));    // Ожидаемый результат: 1
        System.out.println(hashTable.get("two"));    // Ожидаемый результат: 2
        System.out.println(hashTable.get("three"));  // Ожидаемый результат: 3
        System.out.println(hashTable.get("four"));   // Ожидаемый результат: null

        // Удаление элемента
        hashTable.remove("two");
        System.out.println(hashTable.get("two"));    // Ожидаемый результат: null
    }

    }
}