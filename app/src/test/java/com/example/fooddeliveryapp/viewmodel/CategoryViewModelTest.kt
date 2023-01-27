package com.example.fooddeliveryapp.viewmodel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.fooddeliveryapp.model.local.data.category.CategoryRepository
import com.example.fooddeliveryapp.model.local.data.category.CategoryResponse
import com.example.fooddeliveryapp.viewmodel.Constants.CATEGORY_SUCCESS_RESPONSE
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.mockito.Mock
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.verify
import retrofit2.Response


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
internal class CategoryViewModelTest {

    @get:Rule
    val InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var categoryResponseObserver: Observer<CategoryResponse>

    @Mock
    lateinit var processingObserver: Observer<Boolean>

    @Mock
    lateinit var errorObserver: Observer<String>

    @Mock
    lateinit var repository: CategoryRepository

    lateinit var viewModel: CategoryViewModel

    @Before
    fun setUp() {
        viewModel = CategoryViewModel(repository)
    }

    @Test
    fun `load categories for success scenario`() {
        runBlocking {
            val dummyResponse = Response.success(
                Gson().fromJson(CATEGORY_SUCCESS_RESPONSE, CategoryResponse::class.java)
            )

            doReturn(dummyResponse).`when`(repository).getAllCategory()

            viewModel.categoryLiveData.observeForever(categoryResponseObserver)
            viewModel.processing.observeForever(processingObserver)
            viewModel.error.observeForever(errorObserver)

            viewModel.getAllCategory()

            val expectedResult = Gson().fromJson(CATEGORY_SUCCESS_RESPONSE,CategoryResponse::class.java)
            verify(categoryResponseObserver).onChanged(expectedResult)
            viewModel.categoryLiveData.removeObserver(categoryResponseObserver)
            viewModel.processing.removeObserver(processingObserver)
        }
    }

    @Test
    fun `load categories for failure scenario`() {
        runBlocking {
            val dummyResponse = Response.error<String>(
                500,
                "Internal server error".toResponseBody(("text/plain".toMediaType()))
            )

            doReturn(dummyResponse).`when`(repository).getAllCategory()

            viewModel.categoryLiveData.observeForever(categoryResponseObserver)
            viewModel.processing.observeForever(processingObserver)
            viewModel.error.observeForever(errorObserver)

            viewModel.getAllCategory()

            verify(processingObserver).onChanged(true)
            verify(repository).getAllCategory()
            verify(processingObserver).onChanged(false)

            val expectedResult = "Empty result from server."

            verify(errorObserver).onChanged(expectedResult)

            viewModel.error.removeObserver(errorObserver)
            viewModel.processing.removeObserver(processingObserver)
        }
    }

    @Test
    fun `load categories for exception scenario`() {
        runBlocking {
            val exception = RuntimeException("Internet is not available")

            doThrow(exception).`when`(repository).getAllCategory()

            viewModel.processing.observeForever(processingObserver)
            viewModel.error.observeForever(errorObserver)

            viewModel.getAllCategory()

            verify(processingObserver).onChanged(true)
            verify(repository).getAllCategory()
            verify(processingObserver).onChanged(false)

            verify(errorObserver).onChanged("$exception")

            viewModel.error.removeObserver(errorObserver)
            viewModel.processing.removeObserver(processingObserver)
        }
    }
}