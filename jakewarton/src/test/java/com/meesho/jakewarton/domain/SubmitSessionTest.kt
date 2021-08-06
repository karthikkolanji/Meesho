package com.meesho.jakewarton.domain

import MockTestUtil
import com.meesho.base.utils.NetworkError
import com.meesho.jakewarton.data.db.Repository
import com.meesho.jakewarton.utils.ScanError
import com.meesho.jakewarton.utils.Utils
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class SubmitSessionTest {
    @MockK
    private lateinit var repository: Repository

    @MockK
    private lateinit var calculatePrice: CalculatePrice

    @MockK
    private lateinit var sessionTimer: SessionTimer

    @MockK
    private lateinit var bookSeat: BookSeat

    @MockK
    private lateinit var validateEndQrScan: ValidateEndQrScan

    @MockK
    private lateinit var utils: Utils

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
    }

    @Test
    fun `test ends session successfully`() = runBlocking {
        val givenStartTime = System.currentTimeMillis()

        val usecase = SubmitSession(
            repository,
            calculatePrice,
            sessionTimer,
            bookSeat,
            validateEndQrScan,
            utils
        )

        val givenScanEndRawData = MockTestUtil.qrScanEndRawData()

        // When
        coEvery { utils.isNetworkConnected() }
            .returns(true)

        coEvery { validateEndQrScan.isValid(givenScanEndRawData) }
            .returns(true)

        usecase.submit(givenScanEndRawData,givenStartTime)

        coVerify(exactly = 1) { utils.isNetworkConnected() }
        coVerify(exactly = 1) { validateEndQrScan.isValid(givenScanEndRawData) }
        coVerify(exactly = 1) { sessionTimer.stopTimer() }
        coVerify(exactly = 1) { bookSeat.cancel() }
        coVerify(exactly = 1) { repository.endSession(givenStartTime) }
        coVerify(exactly = 1) { calculatePrice.calculate() }
        coVerify(exactly = 1) { repository.submitSession() }
    }


    @Test(expected = NetworkError::class)
    fun `test network exception when no internet`() = runBlocking {
        val givenStartTime = System.currentTimeMillis()
        val usecase = SubmitSession(
            repository,
            calculatePrice,
            sessionTimer,
            bookSeat,
            validateEndQrScan,
            utils
        )
        val givenScanEndRawData = MockTestUtil.qrScanEndRawData()

        // When
        coEvery { utils.isNetworkConnected() }
            .returns(false)

        usecase.submit(givenScanEndRawData,givenStartTime)

        coVerify(exactly = 1) { utils.isNetworkConnected() }
    }


    @Test(expected = ScanError::class)
    fun `test scan exception when scanned different QR code to end session`() = runBlocking {

        val givenStartTime = System.currentTimeMillis()

        val usecase = SubmitSession(
            repository,
            calculatePrice,
            sessionTimer,
            bookSeat,
            validateEndQrScan,
            utils
        )
        val givenScanEndRawData = MockTestUtil.qrScanEndRawData()

        // When
        coEvery { utils.isNetworkConnected() }
            .returns(true)

        coEvery { validateEndQrScan.isValid(givenScanEndRawData) }
            .returns(false)

        usecase.submit(givenScanEndRawData,givenStartTime)

        coVerify(exactly = 1) { usecase.submit(givenScanEndRawData,givenStartTime) }
        coVerify(exactly = 1) { utils.isNetworkConnected() }
        coVerify(exactly = 1) { validateEndQrScan.isValid(givenScanEndRawData) }
    }
}