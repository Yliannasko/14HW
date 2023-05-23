package org.example.product;

public class ShopRepository {
    private Product[] products = new Product[0];//создаем пустой массив

    /**
     * Вспомогательный метод для имитации добавления элемента в массив
     *
     * @param current — массив, в который мы хотим добавить элемент
     * @param product — элемент, который мы хотим добавить
     * @return — возвращает новый массив, который выглядит, как тот, что мы передали,
     * но с добавлением нового элемента в конец
     */
    private Product[] addToArray(Product[] current, Product product) {// метод для добавления элемента в массив
        Product[] tmp = new Product[current.length + 1]; //создаем новый массив на элемент больше
        for (int i = 0; i < current.length; i++) { //копируем все элементы из старого массива в новый
            tmp[i] = current[i];
        }
        tmp[tmp.length - 1] = product; //добавляем новый элемент в конец из нового массива
        return tmp; //возвращаем новый массив
    }

    /**
     * Метод добавления товара в репозиторий
     *
     * @param product — добавляемый товар
     */
    public void add(Product product) {
        if (findById(product.getId()) != null) {
            throw new AlreadyExistsException(
                    "Продукт с  ID: " + product.getId() + " уже существует");
        }
        products = addToArray(products, product); //вызываем метод addToArray и присваиваем результат переменной products
    }

    public Product[] findAll() { //метод для получения всех товаров из репозитория
        return products; //возвращаем массив товаров
    }

    public void remove(int id) {
        Product[] tmp = new Product[products.length - 1];
        int copyToIndex = 0;
        for (Product product : products) {
            if (product.getId() != id) {
                tmp[copyToIndex] = product;
                copyToIndex++;
            }
        }
        products = tmp;
    }

    public Product findById(int id) { //метод поиска товра по идентификатору
        for (Product product : products) {//перебираем все товары в массиве
            if (product.getId() == id) { //если нашли товар с нужным идентификатором
                //throw new NotFoundException("ID не найден: "+ id);
                return product; //возвращаем этот товар
            }
        }
        return null;//если не нашли товар с нужным идентификатором возврашаем null
    }

    public void removeById(int id) { //проверяем метод для удаления товаров по идентификатору
        Product productToRemove = findById(id); //ищем товар по идентификатору
        if (productToRemove == null) { //если товар не найден
            throw new NotFoundException(
                    "Element with id: " + id + " not found"); //выбрасываем исключение
        }
        Product[] tmp = new Product[products.length - 1];//оздаем новый массив на 1 элекмент меньше
        int copyToIndex = 0;//индекс для копирования элементов из старого в новый
        for (Product product : products) { //перебираем все товары в массиве
            if (product.getId() != id) {//если это не товар который нужно удалить
                tmp[copyToIndex] = product; //копируем его в новый массив
                copyToIndex++; //увеличиваем индекс для копирования
            }
        }
        products = tmp; //присваиваем переменной products новый массив
    }
}
