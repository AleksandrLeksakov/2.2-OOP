import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import ru.netology.*

class WallServiceTest {
    private lateinit var service: WallService

    @Before
    fun setUp() {
        service = WallService()
    }

    @Test
    fun `add should return new post with id different from 0`() {
        val post = Post(text = "Test post")
        val newPost = service.add(post)
        assertNotEquals(0, newPost.id)
    }

    @Test
    fun `update should return true when post with existing id is updated`() {
        val post1 = service.add(Post(text = "Post 1")) // Добавляем post1 в список
        val post2 = service.add(Post(text = "Post 2")) // Добавляем post2 в список
        val updatedPost = post1.copy(text = "Updated Post 1")
        assertTrue(service.update(updatedPost))

        // Проверяем, что post1 и post2 успешно добавлены в список
        assertEquals(2, service.posts.size) // Проверяем количество постов в списке
        assertEquals(post1.id, service.posts[0].id) // Проверяем, что post1 находится в списке постов
        assertEquals(post2.id, service.posts[1].id) // Проверяем, что post2 находится в списке постов
    }

    @Test
    fun `update should return false when post with non-existing id is updated`() {
        service.add(Post(text = "Post")) // Добавляем пост в список
        assertFalse(service.update(Post(id = 999, text = "Non-existing post")))

        // Проверяем, что пост с id 999 не добавлен в список
        assertEquals(1, service.posts.size) // Проверяем, что в списке постов только один элемент
    }

    @Test
    fun addPostWithAudioAttachment() {
        val audio = Audio(1, 1, "Artist", "Title", 180)
        val audioAttachment = AudioAttachment(audio = audio)
        val postWithAttachment = Post(text = "Post with audio", attachments = arrayOf(audioAttachment))
        val newPost = service.add(postWithAttachment)
        assertEquals(1, newPost.attachments.size)
        assertEquals("audio", newPost.attachments[0].type)
    }

    @Test
    fun addPostWithDocumentAttachment() {
        val document = Document(1, 1, "Document Title", 100)
        val documentAttachment = DocumentAttachment(document = document)
        val postWithAttachment = Post(text = "Post with document", attachments = arrayOf(documentAttachment))
        val newPost = service.add(postWithAttachment)
        assertEquals(1, newPost.attachments.size)
        assertEquals("doc", newPost.attachments[0].type)
    }

    @Test
    fun addPostWithLinkAttachment() {
        val link = Link("https://vk.com/", "VK", "Social Network")
        val linkAttachment = LinkAttachment(link = link)
        val postWithAttachment = Post(text = "Post with link", attachments = arrayOf(linkAttachment))
        val newPost = service.add(postWithAttachment)
        assertEquals(1, newPost.attachments.size)
        assertEquals("link", newPost.attachments[0].type)
    }
}
