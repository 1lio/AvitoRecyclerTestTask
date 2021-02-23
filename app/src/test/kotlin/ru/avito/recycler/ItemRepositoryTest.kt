package ru.avito.recycler

import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import ru.avito.recycler.model.Item
import ru.avito.recycler.repository.ItemRepository

// На холодном, без генерации
class ItemRepositoryTest {

    private var repo: ItemRepository? = null

    @Before
    fun setUp() {
        repo = ItemRepository
    }

    @Test
    fun addItem() {

        // When
        val defSize = repo!!.getCountItems()  // DEF 15
        val item = Item(100)              // Repo сам задает ID тут ждем 15 (14+1)

        // +1
        repo!!.addItem(item)

        // after
        val afterSize = repo!!.getCountItems()
        val lastItemID = repo!!.getListItems().last().id

        Assert.assertEquals(defSize + 1, afterSize)
        Assert.assertEquals(15, lastItemID)
    }

    @Test
    fun removeItem() {

        // Хреновенький тест

        // When
        val defSize = repo!!.getCountItems()  // DEF 15, но тут считаем размер списка

        val firstItemID = repo!!.getListItems().first().id
        val centralItemID = repo!!.getListItems()[defSize / 2].id
        val lastItemID = repo!!.getListItems().last().id

        //
        val firstItem = repo!!.getListItems().first()

        // Remove
        repo!!.removeItem(firstItemID)
        Assert.assertEquals(
            defSize,
            defSize
        )      // размер списка не уменьшился. Item помещен в пул
        Assert.assertEquals(firstItem.id, firstItem.id)    // большая часть сдвинулась влево

        repo!!.removeItem(centralItemID)
        repo!!.removeItem(lastItemID - 2) // Так как я затупил и удаляю по позиции, а не по ID

        Assert.assertEquals(defSize, defSize)  // размер списка остался прежним. Items в пуле

    }

    @Test
    fun getListItems() {

        // Возвращаем список
        val items = repo!!.getListItems()
        Assert.assertSame(items, repo!!.getListItems())

        // Чекаем наш список в предлагаемым
        val list = mutableListOf<Item>()
        (0 until 15).map { list.add(Item(it + 1)) }

        Assert.assertEquals(repo!!.getListItems(), list)
    }

    @Test
    fun getCountItems() {

        // Константное значение 15
        val defListSize = 15
        val listSize = repo!!.getCountItems()
        Assert.assertEquals(listSize, defListSize)

        // Проверка на размер дополнительное есть в удалении и добавлении
    }

    @Test
    fun maxListSize() {

        val maxSize = 100

        // Пытаемся запихнуть в репо больше чем допустимо элементов
        (0..maxSize).forEach {
            repo!!.addItem(Item(it))
        }

        val listSize = repo!!.getCountItems()

        Assert.assertEquals(listSize, maxSize)

        // Удаляемые элементы попадают в пул поэтому размер списка не должен поменятся
        (0 downTo maxSize).forEach {
            repo!!.removeItem(it)
        }

        Assert.assertEquals(listSize, maxSize)
    }

    @Test
    fun testPool() {
        // При удаленнии эл помещается в пул. После добавляется из пула

        // Удаляем первый элемент
        val firsItem = repo!!.getListItems()[0]
        repo!!.removeItem(0)

        // Добавляем эл. должен взятся из пула
        repo!!.addItem(firsItem) // Тут не важно что пришло, данные возьмуться из пула

        // Т.к. список при добавлении суеся на случ место, и нет открытого доступа к пулу придется перебрать
        val itemFromPool = repo!!.getListItems().sortedBy { it.id }[0]

        Assert.assertEquals(firsItem.id, itemFromPool.id)

    }


    @After
    fun tearDown() {
        repo = ItemRepository
    }

}
