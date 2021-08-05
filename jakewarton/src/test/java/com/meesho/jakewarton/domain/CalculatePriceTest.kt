package com.meesho.jakewarton.domain

import MockTestUtil
import com.google.common.truth.Truth
import com.meesho.jakewarton.data.db.Repository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class CalculatePriceTest {
    @MockK
    private lateinit var repository: Repository

    @Before
    fun setUp() {
        MockKAnnotations.init(this,relaxed = true)
    }


    @Test
    fun `test calculate price and adds to db`() = runBlocking {

        val expectedDuration = 2 // 2 minutes
        val expectedAmount = 11
        val startTime = System.currentTimeMillis()
        val endTime = startTime + 120000 // adding 2 minutes
        val expectedSession = MockTestUtil.expectedSession().copy(
            start_time = startTime,
            end_time = endTime
        )

        // When
        coEvery { repository.getSession() }
            .returns(expectedSession)

        val session = repository.getSession()

        val duration = (session.end_time - session.start_time) / 60000
        Truth.assertThat(duration).isEqualTo(expectedDuration)

        val amount = session.price_per_min * duration
        Truth.assertThat(amount).isEqualTo(expectedAmount)

        repository.updateAmount(amount)

        coVerify(exactly = 1) { repository.getSession() }
        coVerify(exactly = 1) { repository.updateAmount(amount) }
    }


    @Test
    fun `test should fail on incorrect duration`() = runBlocking {

        val expectedDuration = 3 // 3 minutes
        val startTime = System.currentTimeMillis()
        val endTime = startTime + 120000 // making session of 2 minutes
        val expectedSession = MockTestUtil.expectedSession().copy(
            start_time = startTime,
            end_time = endTime
        )

        // When
        coEvery { repository.getSession() }
            .returns(expectedSession)

        val session = repository.getSession()

        val duration = (session.end_time - session.start_time) / 60000
        Truth.assertThat(duration).isNotEqualTo(expectedDuration)

        coVerify(exactly = 1) { repository.getSession() }
    }

    @Test
    fun `test should fail on incorrect amount`() = runBlocking {

        val expectedDuration = 3 // 2 minutes
        val expectedAmount = 15
        val startTime = System.currentTimeMillis()
        val endTime = startTime + 120000 // making session of 2 minutes
        val expectedSession = MockTestUtil.expectedSession().copy(
            start_time = startTime,
            end_time = endTime
        )

        // When
        coEvery { repository.getSession() }
            .returns(expectedSession)

        val session = repository.getSession()

        val duration = (session.end_time - session.start_time) / 60000
        Truth.assertThat(duration).isNotEqualTo(expectedDuration)

        val amount = session.price_per_min * duration
        Truth.assertThat(amount).isNotEqualTo(expectedAmount)

        coVerify(exactly = 1) { repository.getSession() }
    }

}