## Tske

- `src/main/java/com/example/cache/Cache.java`: Реализация кеша с алгоритмами LRU и LFU.
- `src/main/java/com/example/service/ProductService.java`: Сервис для обработки продуктов.
- `src/main/java/com/example/dao/ProductDao.java`: DAO (Data Access Object) для работы с продуктами.
- `src/main/java/com/example/entity/Product.java`: Класс, представляющий сущность "Продукт".
- `src/main/java/com/example/proxy/ProductProxy.java`: Прокси для синхронизации DAO с кешем через AspectJ.
- `src/main/java/com/example/util/YmlReader.java`: Чтение конфигурации из файла `application.yml`.
- `src/main/java/com/example/dto/ProductDto.java`: DTO (Data Transfer Object) для валидации данных.
- `src/test/java/com/example/cache/LFUCacheTest.java`: Тесты для LFU кеша.
- `src/test/java/com/example/cache/LRUCacheTest.java`: Тесты для LRU кеша.
