package ru.netology


data class Post(
    val id: Int = 0, // Идентификатор поста
    val fromId: Int = 0, // ID автора поста
    val ownerId: Int = 0, // ID владельца стены
    val date: Int = 0, // Дата публикации поста
    val text: String = "", // Текст поста
    val markedAsAds: Boolean = false, // Флаг, является ли пост рекламой
    val isPinned: Boolean = false, // Флаг, закреплен ли пост
    val canPin: Boolean = false, // Флаг, может ли пользователь закрепить пост
    val canDelete: Boolean = false, // Флаг, может ли пользователь удалить пост
    val canEdit: Boolean = false, // Флаг, может ли пользователь редактировать пост
    val isFavorite: Boolean = false, // Флаг, добавлен ли пост в избранное
    val postponedId: Int = 0, // ID отложенного поста (если пост отложен)
    val views: Int = 0, // Количество просмотров поста
    val comments: Comments = Comments(), // Информация о комментариях
    val likes: Likes = Likes() // Информация о лайках

)

data class Comments(
    val count: Int = 0, // Количество комментариев
    val canPost: Boolean = false // Флаг, может ли пользователь оставлять комментарии
)

data class Likes(
    val count: Int = 0, // Количество лайков
    val userLikes: Boolean = false // Флаг, поставил ли пользователь лайк
)

class WallService {
    private var lastId = 0
    private val posts = mutableListOf<Post>() // Список всех постов

    fun add(post: Post): Post {
        lastId++
        val newPost = post.copy(id = lastId) // 👈 Изменен конструктор, id добавлена как параметр
        posts.add(newPost)
        return newPost
    }

    fun getById(id: Int): Post? { // Метод для поиска поста по ID
        return posts.find { it.id == id } // Ищем пост в списке по ID
    }

    fun update(post: Post): Boolean { // Метод для обновления поста
        val index = posts.indexOfFirst { it.id == post.id } // Ищем индекс поста по ID
        if (index != -1) { // Если пост найден (индекс не -1)
            posts[index] = post // Обновляем пост на соответствующий индексе
            return true // Возвращаем true, если обновление прошло успешно
        }
        return false // Возвращаем false, если пост не найден
        }
    }
fun main() {
    val wallService = WallService() // Создание объекта сервиса

    val post1 = Post(fromId = 1, ownerId = 1, date = 1678870400, text = "Привет!") // Создание первого поста
    val post2 = Post(fromId = 2, ownerId = 2, date = 1678870500, text = "Как дела?") // Создание второго поста

    val addedPost1 = wallService.add(post1) // Добавление первого поста в сервис
    val addedPost2 = wallService.add(post2) // Добавление второго поста в сервис

    println("Пост 1: ${addedPost1}") // Вывод информации о первом посте
    println("Пост 2: ${addedPost2}") // Вывод информации о втором посте

    val updatedPost = post1.copy(text = "Привет! Как дела?") // Создание копии первого поста с обновленным текстом
    wallService.update(updatedPost) // Обновление первого поста в сервисе

    println(wallService.getById(post1.id)) // Вывод информации об обновленном первом посте
}

