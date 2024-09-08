package ru.netology


// Data-класс для представления поста
data class Post(
    val id: Int = 0, // ID поста (автоматически генерируется)
    val fromId: Int = 0, // ID автора поста
    val ownerId: Int = 0, // ID владельца стены, на которой опубликован пост
    val date: Int = 0, // Дата публикации поста (в секундах с начала эпохи Unix)
    val text: String = "", // Текст поста
    val markedAsAds: Boolean = false, // Флаг, указывающий, является ли пост рекламой
    val isPinned: Boolean = false, // Флаг, указывающий, закреплен ли пост
    val canPin: Boolean = false, // Флаг, указывающий, может ли пользователь закрепить пост
    val canDelete: Boolean = false, // Флаг, указывающий, может ли пользователь удалить пост
    val canEdit: Boolean = false, // Флаг, указывающий, может ли пользователь редактировать пост
    val isFavorite: Boolean = false, // Флаг, указывающий, является ли пост избранным
    val postponedId: Int = 0, // ID отложенной публикации
    val views: Int? = null, // Число просмотров поста (может быть null)
    val comments: Comments = Comments(), // Комментарии к посту
    val likes: Likes = Likes(),// Лайки к посту
    val attachments: Array<Attachment> = emptyArray() // Массив для хранения вложений
)


interface Attachment {
    val type: String
}

// Фото
data class Photo(val id: Int, val ownerId: Int, val photo130: String, val photo604: String)

// Видео
data class Video(val id: Int, val ownerId: Int, val title: String, val duration: Int)

// Аудио
data class Audio(val id: Int, val ownerId: Int, val artist: String, val title: String, val duration: Int)

// Документ
data class Document(val id: Int, val ownerId: Int, val title: String, val size: Int)

// Ссылка
data class Link(val url: String, val title: String, val description: String)

// Фото вложение
data class PhotoAttachment(override val type: String = "photo", val photo: Photo) : Attachment

// Видео вложение
data class VideoAttachment(override val type: String = "video", val video: Video) : Attachment

// Аудио вложение
data class AudioAttachment(override val type: String = "audio", val audio: Audio) : Attachment

// Документ вложение
data class DocumentAttachment(override val type: String = "doc", val document: Document) : Attachment

// Ссылка вложение
data class LinkAttachment(override val type: String = "link", val link: Link) : Attachment


// Data-класс для представления комментариев к посту
data class Comments(
    val count: Int = 0, // Количество комментариев
    val canPost: Boolean = false // Флаг, указывающий, может ли пользователь оставлять комментарии
)

// Data-класс для представления лайков к посту
data class Likes(
    val count: Int = 0, // Количество лайков
    val userLikes: Boolean = false // Флаг, указывающий, поставил ли пользователь лайк
)

// Класс для работы со стеной
class WallService {
    private var lastId = 0 // Счетчик ID для новых постов
    val posts = mutableListOf<Post>() // Список постов

    // Метод для добавления нового поста
    fun add(post: Post): Post {
        val newPost = post.copy(
            id = ++lastId,
            comments = post.comments.copy(),
            likes = post.likes.copy()
        ) // Создается копия поста с новым ID, комментариями и лайками
        posts.add(newPost) // Добавляем копию поста в список
        return newPost // Возвращаем добавленный пост
    }

    // Метод для обновления поста
    fun update(post: Post): Boolean {
        val index = posts.indexOfFirst { it.id == post.id } // Находим индекс поста по его ID
        if (index != -1) { // Если пост найден
            posts[index] = post.copy() // Заменяем пост в списке копией переданного поста
            return true // Возвращаем true, если обновление прошло успешно
        }
        return false // Возвращаем false, если пост не найден
    }

    // Метод для получения поста по его ID
    fun getById(id: Int): Post? {
        return posts.find { it.id == id } // Возвращаем пост с указанным ID, если он есть, иначе null
    }

    // Метод для очистки списка постов
    fun clear() {
        posts.clear() // Очищаем список постов
    }
}

// Основная функция для запуска приложения
fun main() {
    val service = WallService() // Создаем экземпляр WallService
    val post1 = service.add(Post(text = "Hello, world!")) // Добавляем два тестовых поста
    val post2 = service.add(Post(text = "This is my second post", views = 100))

    println("Post 1: ${post1}") // Выводим информацию о постах
    println("Post 2: ${post2}")

    val updatedPost = post1.copy(text = "Updated post!") // Создаем копию первого поста с обновленным текстом
    service.update(updatedPost) // Обновляем пост
    println("Updated Post 1: ${service.getById(post1.id)}") // Выводим информацию об обновленном посте

    val post3 = service.getById(999) // Пытаемся получить пост с несуществующим ID
    println("Post with ID 999: ${post3}") // Выводим результат (должно быть null)

    // Создаем фото-вложение
    val photo = Photo(1, 1, "https://vk.com/some_photo_link", "https://vk.com/another_photo_link")
    val photoAttachment = PhotoAttachment(photo = photo)

    // Создаем видео-вложение
    val video = Video(1, 1, "Funny Video", 30)
    val videoAttachment = VideoAttachment(video = video)

    // Создаем пост с вложениями
    val post = Post(
        text = "Мой пост с фото и видео",
        attachments = arrayOf(photoAttachment, videoAttachment) // Добавляем вложения в массив
    )
}