# CocktailBar
Тестовое задание в рамках летней школы Surf 2023
Apk-файл: [apk file](/app-debug.apk)

## Описание реализации приложения
### Реализовано:
-   Экран с коктелями пользователя:
    Реализован полностью в рамках основого задания;
    
    <img src="https://github.com/IlyaVolf/CocktailBar/assets/70796651/fa3c1ab6-0eda-45e0-912f-dcb763738cba" width="270" height="480">
    <img src="https://github.com/IlyaVolf/CocktailBar/assets/70796651/4ff4bd69-34aa-4be6-b9f4-e6900ec52aeb" width="270" height="480">
    <img src="https://github.com/IlyaVolf/CocktailBar/assets/70796651/6da07b47-969f-40f7-9925-dcd2dea96a16" width="270" height="480">
    
-   Экран “Деталка коктейля”:
    Реализован полностью в рамках основого задания;

    <img src="https://github.com/IlyaVolf/CocktailBar/assets/70796651/0ce98b99-b730-48c8-8296-7ccbd39b6511" width="270" height="480">
    <img src="https://github.com/IlyaVolf/CocktailBar/assets/70796651/af9ad29b-2408-42ee-aee4-9d4fee0afc4f" width="270" height="480">
    <img src="https://github.com/IlyaVolf/CocktailBar/assets/70796651/d5affa27-959f-4f47-88b2-76905cee812e" width="270" height="480">
    
-   Экран “Создание коктейля”:
    Реализовано создание коктеля без загрузки фотографии и рецепта. Добавление фотографии и рецепта захардкожены, тогда как их хранение и отображение полностью поддерживается.

    <img src="https://github.com/IlyaVolf/CocktailBar/assets/70796651/9dcd5bc8-5e7d-4460-ad73-a9e0fd07ef46" width="270" height="480">
    <img src="https://github.com/IlyaVolf/CocktailBar/assets/70796651/2d40854a-bd38-4ac3-94b9-7dc654c88682" width="270" height="480">

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
