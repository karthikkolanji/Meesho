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

class ValidateEndQrScanTest {

    @MockK
    private lateinit var repository: Repository

    @MockK
    private lateinit var parseScanResult: ParseScanResult


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `test returns true when active session in db & end Qr scan is same`() = runBlocking {

        val givenActiveSession = MockTestUtil.activeSession()
        val givenSameEndQRScanData = MockTestUtil.qrScanStartRawData()

        // When
        coEvery { repository.getSession() }
            .returns(givenActiveSession)

        coEvery { parseScanResult.parse(givenSameEndQRScanData) }
            .returns(givenActiveSession)

        // Invoke
        val activeSession =repository.getSession()
        val endSession =parseScanResult.parse(givenSameEndQRScanData)

        coVerify(exactly = 1) { repository.getSession() }
        coVerify(exactly = 1) { parseScanResult.parse(givenSameEndQRScanData) }

        Truth.assertThat(activeSession.location_id).isEqualTo(endSession.location_id)
    }

    @Test
    fun `test returns false when active session in db & end Qr scan is not same`() = runBlocking {

        val givenActiveSession = MockTestUtil.activeSession()
        val givenDifferentEndQRScanRawData = MockTestUtil.qrScanEndRawData()
        val givenEndSession = MockTestUtil.endSessionQrScan()

        // When
        coEvery { repository.getSession() }
            .returns(givenActiveSession)

        coEvery { parseScanResult.parse(givenDifferentEndQRScanRawData) }
            .returns(givenEndSession)

        // Invoke
        val activeSession =repository.getSession()
        val endSession =parseScanResult.parse(givenDifferentEndQRScanRawData)

        coVerify(exactly = 1) { repository.getSession() }
        coVerify(exactly = 1) { parseScanResult.parse(givenDifferentEndQRScanRawData) }

        Truth.assertThat(activeSession.location_id).isNotEqualTo(endSession.location_id)
    }
}