public class HashTable {
    private static final int DEFAULT_CAPACITY = 10; // Начальная емкость таблицы
    private static final double LOAD_FACTOR = 0.7; // Пороговое значение коэффициента загрузки

    private HashCell<K, V>[] table; // Массив элементов хеш-таблицы
    private int size; // Количество элементов в таблице

    private static class HashCell<K, V> {
        private K key;
        private V value;
        private boolean deleted;

        public HashCell(K key, V value) {
            this.key = key;
            this.value = value;
            this.deleted = false;
        }
    }

    public HashTable() {
        table = new HashCell[DEFAULT_CAPACITY];
        size = 0;
    }

    private int hash(K key) {
        return key.hashCode() % table.length;
    }

    /**
     * Добавление
     * @param key
     * @param value
     */
    public void add(K key, V value) {
        // Проверяем, необходимо ли увеличить емкость таблицы
        if ((double) size / table.length >= LOAD_FACTOR) {
            resize();
        }
        int index = hash(key);
        // Поиск свободного места для вставки
        while (table[index] != null && !table[index].deleted) {
            // Если ключ уже существует, обновляем значение
            if (table[index].key.equals(key)) {
                table[index].value = value;
                return;
            }
            index = (index + 1) % table.length; // Смещаем индекс для элемента
        }
        table[index] = new HashCell<>(key, value);
        size++;
    }

    /**
     * Получить значение элемента
     * @param key
     * @return HashCell or NULL
     */
    public V get(K key) {
        int index = hash(key);

        // Поиск элемента по ключу
        while (table[index] != null) {
            if (!table[index].deleted && table[index].key.equals(key)) {
                return table[index].value;
            }
            index = (index + 1) % table.length;
        }

        return null; // Ключ не найден
    }

    /**
     * Удаление элемента
     * @param key
     */
    public void remove(K key) {
        // Проверяем, необходимо ли увеличить емкость таблицы
        if ((double) size / table.length <= (1-LOAD_FACTOR)) {
            resizeShrink();
        }
        int index = hash(key);
        // Поиск элемента по ключу и пометка его как удаленного
        while (table[index] != null) {
            if (!table[index].deleted && table[index].key.equals(key)) {
                table[index].deleted = true;
                size--;
                return;
            }
            index = (index + 1) % table.length;
        }
    }

    /**
     * Адаптирует размер таблицы на увеличение
     */
    private void resize() {
        int newCapacity = table.length * 2;
        HashCell<K, V>[] newTable = new HashCell[newCapacity];

        // Перехеширование всех элементов в новую таблицу
        for (HashCell<K, V> hashCell : table) {
            if (hashCell != null && !hashCell.deleted) {
                int index = hash(hashCell.key);
                while (newTable[index] != null) {
                    index = (index + 1) % newTable.length;
                }
                newTable[index] = hashCell;
            }
        }
        table = newTable;
    }

    /**
     * Адаптирует размер таблицы на уменьшение
     */
    private void resizeShrink() {
        int newCapacity = table.length / 2;
        HashCell<K, V>[] newTable = new HashCell[newCapacity];
        // Перехеширование всех элементов в новую таблицу
        for (HashCell<K, V> hashCell : table) {
            if (hashCell != null && !hashCell.deleted) {
                int index = hash(hashCell.key);
                while (newTable[index] != null) {
                    index = (index + 1) % newTable.length;
                }
                newTable[index] = hashCell;
            }
        }
        table = newTable;
    }
}
