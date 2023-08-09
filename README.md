# CocktailBar
Тестовое задание в рамках летней школы Surf 2023
Apk-файл: [apk file](/app-debug.apk)

## Описание реализации приложения
### Реализовано:
-   Экран с коктелями пользователя:
    Реализован полностью в рамках основого задания;

    <img src="https://github.com/IlyaVolf/CocktailBar/assets/70796651/56fe4272-8b66-4675-a347-e5404df0637c" width="270" height="480">
    <img src="https://github.com/IlyaVolf/CocktailBar/assets/70796651/e4f6a5da-94e4-47e2-b3c5-b6b6fd36a1d5" width="270" height="480">
    <img src="https://github.com/IlyaVolf/CocktailBar/assets/70796651/0328c4a9-68be-40b8-a54b-bbb76f88b73b" width="270" height="480">
    
-   Экран “Деталка коктейля”:
    Реализован полностью в рамках основого задания;

    <img src="https://github.com/IlyaVolf/CocktailBar/assets/70796651/9b8373e4-3323-4e27-9baa-1da141cf147d" width="270" height="480">
    <img src="https://github.com/IlyaVolf/CocktailBar/assets/70796651/9bf1d3fc-806b-427e-9781-4540a0da43f4" width="270" height="480">
    <img src="https://github.com/IlyaVolf/CocktailBar/assets/70796651/75256635-cd84-492c-a840-1fa0b9284227" width="270" height="480">
    
-   Экран “Создание коктейля”:
    Реализовано создание коктеля без загрузки фотографии и рецепта. Добавление фотографии и рецепта захардкожены, тогда как их хранение и отображение полностью поддерживается.

    <img src="https://github.com/IlyaVolf/CocktailBar/assets/70796651/552dbe38-e9c1-4dd3-8038-f0a87ed70477" width="270" height="480">
    <img src="https://github.com/IlyaVolf/CocktailBar/assets/70796651/7c8ca45e-9a8f-4a04-8b1d-2ac8e20fcdee" width="270" height="480">

-   Доп:
    -   Состояния экранов:
        Каждый экран имеет своё состояние (загрузка, успех и ошибка), в зависимости от которого отображается програссбар, успешно загруженные данные или сообщение об ошибке с кнопкой "повторить".

### Не реализовано:
-   В экране “Создание коктейля”:
    Ввод ингредиентов, валидация введённого текста на заполненность обязательных полей.
-   Дополнительные задания.

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

Коммуникация ViewModel с фрагментов осуществляется посредством LiveData. В LiveData хранятся контейнеры сущностей, которые позволяют указать для сущности одно из следующих состояний: Init, Loading, Refresh, Ready, Error. Фрагмент подписывается на LiveData и в дальнейшем обновляет экран в соответствии с полученным состоянием объекта.

### Крайне __глупые(!)__ баги, которые заметил слишком поздно:
-   На странице с описанием коктеля описание коктеля отображается вместо рецепта: `binding.recipeTv.text = cocktail.description`;
-   После переименования id кнопки добавления нового коктейля на странице с коктейлями у RecyclerView поломан нижний констреинт;
-   Остальное реализовать просто не успел =(

## Демонстрация работы приложения
