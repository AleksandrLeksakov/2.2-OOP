import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import ru.netology.Post
import ru.netology.WallService

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
        val post1 = service.add(Post(text = "Post 1"))
        val post2 = service.add(Post(text = "Post 2"))
        val updatedPost = post1.copy(text = "Updated Post 1")
        assertTrue(service.update(updatedPost))
    }

    @Test
    fun `update should return false when post with non-existing id is updated`() {
        val post = service.add(Post(text = "Post"))
        assertFalse(service.update(Post(id = 999, text = "Non-existing post")))
    }
}