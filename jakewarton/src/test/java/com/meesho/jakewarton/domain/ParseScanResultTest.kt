package com.meesho.jakewarton.domain

import MockTestUtil
import com.google.common.truth.Truth.assertThat
import com.meesho.jakewarton.data.entity.QRScanResult
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ParseScanResultTest {

    @MockK
    private lateinit var parseScanResult: ParseScanResult

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `test returns Qr scan object on parsing string`() {
        val givenQrScanData = MockTestUtil.qrScanStartRawData()

        // expected
        val expectedQrScanResult = QRScanResult(
            location_id = "ButterKnifeLib-1234",
            location_details = "ButterKnife Lib, 80 Feet Rd, Koramangala 1A Block, Bangalore",
            price_per_min = 5.50F
        )
        // When
        coEvery { parseScanResult.parse(givenQrScanData) }
            .returns(expectedQrScanResult)


        // Invoke
        val scanResult = parseScanResult.parse(givenQrScanData)

        verify(exactly = 1) { parseScanResult.parse(givenQrScanData) }

        // Then
        assertThat(scanResult).isEqualTo(expectedQrScanResult)

    }

}