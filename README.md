# CocktailBar
Тестовое задание в рамках летней школы Surf 2023

Бриф: https://docs.google.com/document/d/16poPTQjJIaynjJpQzxlrNiPPSd_20PaDv9OXHKY9w6o/edit#heading=h.p56embp18xyh

Задание: https://docs.google.com/document/d/1n8jzC6oZc5-kDQeR50INa8AclKuyucq7icO7qBKfd20/edit

Apk-файл: [apk file](/cocktailApp.apk)

## Описание реализации приложения
### Реализовано:
-   Экран с коктелями пользователя:
  
    Реализован полностью в рамках основого задания;

    <img src="https://github.com/IlyaVolf/CocktailBar/assets/70796651/56fe4272-8b66-4675-a347-e5404df0637c" width="216" height="384">
    <img src="https://github.com/IlyaVolf/CocktailBar/assets/70796651/e4f6a5da-94e4-47e2-b3c5-b6b6fd36a1d5" width="216" height="384">
    <img src="https://github.com/IlyaVolf/CocktailBar/assets/70796651/0328c4a9-68be-40b8-a54b-bbb76f88b73b" width="216" height="384">
    
-   Экран “Деталка коктейля”:
  
    Реализован полностью в рамках основого задания;

    <img src="https://github.com/IlyaVolf/CocktailBar/assets/70796651/3d02217a-58b3-4550-92a3-0e48023fcd4f" width="216" height="384">
    <img src="https://github.com/IlyaVolf/CocktailBar/assets/70796651/55db9839-5ed9-4824-b835-718f8d91e403" width="216" height="384">
    <img src="https://github.com/IlyaVolf/CocktailBar/assets/70796651/f2de3d6d-5309-4d17-929c-b913685c81ce" width="216" height="384">
    <img src="https://github.com/IlyaVolf/CocktailBar/assets/70796651/65f9e8ed-fe54-4433-8824-405fa8c0cb55" width="216" height="384">
    
    
-   Экран “Создание коктейля”:
  
    Реализовано создание и редактирование коктеля без загрузки фотографии. Добавление фотографии и рецепта захардкожены, тогда как их хранение и отображение полностью поддерживается.

    <img src="https://github.com/IlyaVolf/CocktailBar/assets/70796651/3bf6af9a-dc87-477b-868a-8c94a68fb10d" width="216" height="384">
    <img src="https://github.com/IlyaVolf/CocktailBar/assets/70796651/f614b527-5ddb-4243-b907-f57188118b9a" width="216" height="384">
    <img src="https://github.com/IlyaVolf/CocktailBar/assets/70796651/b79f62f6-d0ee-4864-aa85-01d90e11ac2c" width="216" height="384">
    <img src="https://github.com/IlyaVolf/CocktailBar/assets/70796651/38caba01-5142-4154-b343-3e9f54912e06" width="216" height="384">

-   Доп:
    -   Удаление коктейля;
    -   Состояния экранов:
      
        Каждый экран имеет своё состояние (загрузка, успех и ошибка), в зависимости от которого отображается програссбар, успешно загруженные данные или сообщение об ошибке с кнопкой "повторить".

### Используемые технологии:
-   Room - Хранение коктелей;
-   Jetpack Navigation Component - Реализация навигации;
-   Glide - Работа с изображениями;
-   Hilt - Внедрение зависимостей;
-   Kotlin Coroutines - Асинхронность;
-   ViewBinding - Работа с вью;
-   RecyclerView + ListAdapter + DiffUtil - Реализация списков.

### Архитектура:
-   MVVM - Архитектурный шаблон;
-   Single Activity Pattern, Observer Pattern, Repository Pattern, Dependency Injection Pattern - Паттерны проектирования;
-   Presenation, Domain, Data - Слои приложения.

### Описание реализации:
ViewModel обращается к репозиторию, чтобы передать или получить данные. Методы репозитория - suspend-функции, которые выполняются с помощью Dispatchers.IO. Эти методы, в свою очередь, взаимодействуют с БД путём вызыва соответствующих методов DAO-класса.

Коммуникация ViewModel с фрагментов осуществляется посредством LiveData. В LiveData хранится состояние экрана. Фрагмент подписывается на LiveData и в дальнейшем обновляет экран в соответствии с полученным состоянием.

## Демонстрация работы приложения

- Флоу 1
  Продолжительность видео: 1:37
  
  ![флоу 1 сжат](https://github.com/IlyaVolf/CocktailBar/assets/70796651/b8ea55ca-da83-404f-9b00-c6f5151c420c)


- Флоу 2
  Продолжительность видео: 0:07
  
  ![флоу 2 сжат](https://github.com/IlyaVolf/CocktailBar/assets/70796651/7bfcaf9b-58be-44ab-a6d8-062ff2fa6251)


