package com.semba.geodistance.ui

import com.semba.geodistance.data.models.UserData
import com.semba.geodistance.utils.FileManager
import com.semba.geodistance.utils.InstantTaskExecutorRule
import com.semba.geodistance.utils.rx.TestSchedulerProvider
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.*


/**
 * Created by SeMbA on 2019-10-10.
 */

@ExtendWith(
    InstantTaskExecutorRule::class
)
class TestViewModel {

    lateinit var navigator: MainNavigator
    lateinit var fileManager: FileManager
    lateinit var scheduler: TestSchedulerProvider

    private val user1 = UserData(1,"Ian Kehoe",53.2451022,-6.238335,50)
    private val user2 = UserData(2,"Ian McArdle",51.8856167,-10.4240951,400)

    private lateinit var viewModel: MainViewModel

    @BeforeEach
    fun setUp()
    {
        navigator = mock(MainNavigator::class.java)
        fileManager = mock(FileManager::class.java)
        scheduler = TestSchedulerProvider()

        viewModel = MainViewModel(fileManager, scheduler)
        viewModel.setNavigator(navigator)
    }

    @AfterEach
    fun after()
    {

    }

    @Test
    fun `Execute Logic`()
    {
        val array = arrayListOf(user1)
        val spy = spy(viewModel)
        val order = inOrder(spy)

        `when`(spy.loadUsersData()).thenReturn(array)
        `when`(fileManager.checkFileExists()).thenReturn(true)
        spy.executeLogic()

        order.verify(spy).loadUsersData()
        order.verify(spy).performGCDCalculation(array)
    }

    @Test
    fun `Logic Sequence`()
    {
        val array = arrayListOf(user1)
        val spy = spy(viewModel)
        val order = inOrder(spy)

        spy.performGCDCalculation(array)
        order.verify(spy).calculateDistanceFromOffice(user1.latitude,user1.longitude)
        order.verify(spy).checkUserDistance(user1,array)
        order.verify(spy).onCalculationComplete(array)
        order.verify(spy).sortUsersData(array)
        assert(spy.usersLiveData.value == array)
    }

    @Test
    fun `Load Users Data`()
    {
        val returnedText = "text"
        doReturn(returnedText).`when`(fileManager).readFile()

        viewModel.loadUsersData()

        verify(fileManager).readFile()
        verify(fileManager).deserializeJsonArray(returnedText)
    }

    @Test
    fun `Distance Is Within 100 KM`()
    {
        val result = viewModel.calculateDistanceFromOffice(user1.latitude,user1.longitude)
        print(result)
        assertTrue { result <= 100}
    }

    @Test
    fun `Distance Is Outta 100 KM`()
    {
        val result = viewModel.calculateDistanceFromOffice(user2.latitude,user2.longitude)
        print(result)
        assertTrue { result > 100}
    }

    @Test
    fun `Filter Valid Users`()
    {
        val result = viewModel.checkUserDistance(user1, arrayListOf(user1, user2))

        assertFalse { result }
    }

    @Test
    fun `Filter Invalid Users`()
    {
        val array = arrayListOf(user1, user2)
        val result = viewModel.checkUserDistance(user2, array)

        assertTrue { result }
        assertTrue { array.size == 1 }
    }

    @Test
    fun `Refresh UI`()
    {
        viewModel.showResult(arrayListOf(user1,user2))

        verify(navigator).validateRecyclerView(arrayListOf(user1,user2))
    }

    @Test
    fun `Sort Users`()
    {
        val array = arrayListOf(user2,user1)
        viewModel.sortUsersData(array)

        assert(array[0].id == 1)
        assert(array[1].id == 2)
    }

    @Test
    fun `Complete Calculations`()
    {
        val array = arrayListOf(user1,user2)
        val spy = spy(viewModel)
        spy.onCalculationComplete(array)

        verify(spy).sortUsersData(array)
    }

}